package com.onlinesjtu;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 导出作业
 */
public class OnlinesjtuRepit {

	private static ExecutorService ex = Executors.newFixedThreadPool(10);

	public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

		//		String url = "http://218.1.73.51/mod/quiz/review.php?attempt=1773753";
				String url = "http://218.1.73.51/course/view.php?id=536&term=2021_1";
//		String url = "http://218.1.73.51:82/course/view.php?id=44&term=2020_3";

		final String DEFAULT_USER = "xxx";
		final String DEFAULT_PASS = "xxx";

		HttpGet httpGet = new HttpGet(url);
		String auth = DEFAULT_USER + ":" + DEFAULT_PASS;
		byte[] encodedAuth = Base64.encodeBase64(auth.getBytes());
		String authHeader = "Basic " + new String(encodedAuth);
		httpGet.addHeader(HttpHeaders.AUTHORIZATION, authHeader);
		String value = "MoodleSessionmoodle286=ckg2oma3v7ke7bcs0pb3d9jj26; MoodleSessionmoodle286last=tdbh22pkv8vbg4cqhi4d793qm7";
		httpGet.addHeader("Cookie", value);

//		CookieStore cookieStore = new BasicCookieStore();
//		BasicClientCookie cookie = new BasicClientCookie("MoodleSessionmoodle286", "ckg2oma3v7ke7bcs0pb3d9jj26");
//		cookie.setDomain("218.1.73.51:82");
//		cookie.setPath("/");
//		cookieStore.addCookie(cookie);

		CloseableHttpClient client = HttpClients.custom()
				//				.setDefaultCookieStore(cookieStore)
				//				.setDefaultRequestConfig(RequestConfig.custom().build())
				//				.setRedirectStrategy(new LaxRedirectStrategy())
				.disableRedirectHandling()
				.setDefaultConnectionConfig(ConnectionConfig.custom().setCharset(StandardCharsets.UTF_8).build())
				.build();
		CloseableHttpResponse httpResponse = client.execute(httpGet);
		String htmlTxt = EntityUtils.toString(httpResponse.getEntity(), "utf-8");

		Document doc = Jsoup.parse(htmlTxt);
		System.err.println(doc);
		Elements ele = doc.getElementsByClass("activityinstance");
		PrintWriter printWriter = new PrintWriter(new File("sjwajue.txt"));

		for (Element element : ele) {
			String text = element.text();
			String attrHref = element.select("a").first().attr("href");
			String x = text + "\t" + attrHref;
			System.err.println(x);
			printWriter.println(x);

			HttpGet request = new HttpGet(attrHref);
			request.addHeader("Cookie", value);
			client = HttpClients.custom()
//					.setDefaultCookieStore(cookieStore)
					.disableRedirectHandling()
					.setDefaultConnectionConfig(ConnectionConfig.custom().setCharset(StandardCharsets.UTF_8).build())
					.build();
			CloseableHttpResponse res = client.execute(request);
			if (res.getStatusLine().getStatusCode() == 303) {
				String redirectUrl = res.getFirstHeader("location").getValue();
				System.err.println("redirectUrl: " + redirectUrl);

				downloadFromUrl(client, redirectUrl, "F:\\onlinesjtu\\2020秋\\01可视化计算及应用\\课件ppt", URLDecoder
						.decode(redirectUrl.substring(redirectUrl.lastIndexOf("/") + 1), StandardCharsets.UTF_8.name()));
			}

			printWriter.flush();
			printWriter.close();


			client.close();
		}
	}

	private static void downloadFromUrl(CloseableHttpClient client, String url, String saveDir, String fileName) {
		try {
			CloseableHttpResponse response = client.execute(new HttpGet(url));
			InputStream in = response.getEntity().getContent();


			File file = new File(mkdirs(saveDir) + "\\" + fileName);

			FileOutputStream fos = new FileOutputStream(file);
			int len;
			byte[] tmp = new byte[1024];
			while ((len = in.read(tmp)) != -1) {
				fos.write(tmp, 0, len);
			}
			fos.flush();
			fos.close();
			in.close();
			//			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.err.println(String.format("%s 处理完成", url));
	}

	public static String mkdirs(String path){
		File file = new File(path);
		if(!file.exists()){
			file.mkdirs();
		}
		return path;
	}

}
