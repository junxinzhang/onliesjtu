package com.onlinesjtu;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpStatus;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;

@Slf4j
public class HttpClientUtils {
    private static RequestConfig requestConfig;
    private static PoolingHttpClientConnectionManager cm;
    private static HttpRequestRetryHandler httpRequestRetryHandler;

    static {
        //dns 60s缓存清除
        java.security.Security.setProperty("networkaddress.cache.ttl", "60");
        // 设置请求和传输超时时间
        requestConfig = RequestConfig.custom().setSocketTimeout(6000).setConnectTimeout(6000)
            .setExpectContinueEnabled(true).setConnectionRequestTimeout(15000).setStaleConnectionCheckEnabled(true)
            .build();
        httpRequestRetryHandler = (exception, retryTimes, context) -> {
            log.debug("retryRequest retryTimes:{}", retryTimes);
            if (retryTimes >= 3) {
                return false;
            }
            if (exception instanceof NoHttpResponseException || (exception instanceof InterruptedIOException)
                || (exception instanceof UnknownHostException)) {
                return true;
            }
            if (exception instanceof SSLException) {
                return false;
            }
            HttpClientContext clientContext = HttpClientContext.adapt(context);
            HttpRequest request = clientContext.getRequest();
            boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
            if (idempotent) {
                // 如果请求被认为是幂等的，那么就重试。即重复执行不影响程序其他效果的
                return true;
            }
            return false;
        };
        cm = new PoolingHttpClientConnectionManager();
        // 连接池最大生成连接数20
        cm.setMaxTotal(50);
        // 默认设置route最大连接数
        cm.setDefaultMaxPerRoute(50);
    }

    public static CloseableHttpClient createSSLClientDefault(Boolean bool) {
        try {
            if (bool) {
                // 信任所有
                SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null,
                    (TrustStrategy) (chain, authType) -> true).build();
                HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
                SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
                return HttpClients.custom().setConnectionManager(cm).setRetryHandler(httpRequestRetryHandler)
                    .setConnectionManagerShared(true).setSSLSocketFactory(sslsf).build();
            }
        } catch (Exception e) {
            log.error("{}", e.getLocalizedMessage(), e);
        }
        return HttpClients.custom().setConnectionManager(cm).setRetryHandler(httpRequestRetryHandler)
            .setConnectionManagerShared(true).build();
    }

    /**
     * post请求发送json数据
     *
     * @param url
     * @param jsonParam
     * @return
     */
    public static String doPost(String url, JSONObject jsonParam) {
        CloseableHttpResponse response = null;
        HttpPost post = new HttpPost(url);
        try {
            CloseableHttpClient client = createSSLClientDefault(StringUtils.isNotBlank(url) && url.startsWith("https"));
            post.setConfig(requestConfig);
            if (null != jsonParam) {
                // 解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam.toString(), "UTF-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                post.setEntity(entity);
            }
            response = client.execute(post);
            // 请求发送成功，并得到响应
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                try {
                    // 读取服务器返回过来的json字符串数据
                    return EntityUtils.toString(response.getEntity(), "UTF-8");
                } catch (Exception e) {
                    log.error("post请求提交失败:" + url, e);
                }
            }
        } catch (Exception ex) {
            log.error("sendPost error: 【url】:" + url, ex);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                post.releaseConnection();
            } catch (Exception e) {
                log.error("sendPost error", e);
            }
        }
        return null;
    }

	public static String doPost(String url, String string, JSONObject headers) {
		CloseableHttpResponse response = null;
		HttpPost post = new HttpPost(url);
		if (headers != null && !headers.isEmpty()) {
			for (String key : headers.keySet()) {
				post.setHeader(key, headers.getString(key));
			}
		}
		try {
			CloseableHttpClient client = createSSLClientDefault(StringUtils.isNotBlank(url) && url.startsWith("https"));
			post.setConfig(requestConfig);
			if (null != string) {
				// 解决中文乱码问题
				StringEntity entity = new StringEntity(string, "UTF-8");
				entity.setContentEncoding("UTF-8");
				entity.setContentType("application/x-www-form-urlencoded");
				post.setEntity(entity);
			}
			response = client.execute(post);
			// 请求发送成功，并得到响应
			String result = EntityUtils.toString(response.getEntity(), "UTF-8");
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				try {
					// 读取服务器返回过来的json字符串数据
					return result;
				} catch (Exception e) {
					log.error("post请求提交失败:" + url, e);
				}
			}
			log.warn("sendPost warn: url: {} result: {}",  url, result);
		} catch (Exception ex) {
			log.error("sendPost error: 【url】:" + url, ex);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				post.releaseConnection();
			} catch (Exception e) {
				log.error("sendPost error", e);
			}
		}
		return null;
	}
}
