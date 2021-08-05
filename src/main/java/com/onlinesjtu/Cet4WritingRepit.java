package com.onlinesjtu;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class Cet4WritingRepit {

	public static void main(String[] args) throws Exception {

				String url = "https://www.zhihu.com/question/47968288";
//		String url = "https://www.theguardian.com/society/2016/jul/13/obesity-causes-premature-death-concludes-study-studies";
//		String url = "https://theconversation.com/obesity-second-to-smoking-as-the-most-preventable-cause-of-us-deaths-needs-new-approaches-129317";

		HttpGet httpGet = new HttpGet(url);

		CloseableHttpClient client = HttpClients.custom()
				.disableRedirectHandling()
				.setDefaultConnectionConfig(ConnectionConfig.custom().setCharset(StandardCharsets.UTF_8).build())
				.setSSLSocketFactory(new SSLConnectionSocketFactory(
						new SSLContextBuilder().loadTrustMaterial(null, (TrustStrategy) (chain, authType) -> true)
								.build(), NoopHostnameVerifier.INSTANCE))
				.build();
		CloseableHttpResponse httpResponse = client.execute(httpGet);
		String htmlTxt = EntityUtils.toString(httpResponse.getEntity(), "utf-8");

		Document doc = Jsoup.parse(htmlTxt);
		System.err.println(doc);
		Elements ele = doc.getElementsByClass("RichText ztext CopyrightRichText-richText");


//		Elements ele = doc.getElementsByClass("css-1yqigsj");
//		Elements title = doc.getElementsByClass("css-7g0r1e");
//		Elements describe = doc.getElementsByClass("css-8fmrzx");

//		Elements ele = doc.getElementsByClass("grid-twelve large-grid-eleven");
//		Elements title = doc.getElementsByClass("legacy entry-title instapaper_title");
//		Elements describe = doc.getElementsByClass("wrapper");


		PrintWriter printWriter = new PrintWriter(new File("cet4.txt"));

//		System.out.println(title.text());
//		System.out.println(describe.text());
		for (Element element : ele) {
			String text = element.wholeText();
			System.out.println(text);
			String s = text.replaceAll("(\\s*\\d*)(．)", "\r\n$1$2");
			s = s.replaceAll("(\\s*[一|二|三|四|五|六|七|八|九|十])(、)", "\r\n$1$2");
			s = s.replaceAll("(\\s*\\d+\\.\\s*)", "\r\n$1");
			printWriter.println(s);
		}

	}
}
