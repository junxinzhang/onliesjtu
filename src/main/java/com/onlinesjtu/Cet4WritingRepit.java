package com.onlinesjtu;

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
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;

public class Cet4WritingRepit {

	public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

		String url = "https://www.zhihu.com/question/47968288";

		HttpGet httpGet = new HttpGet(url);

		CloseableHttpClient client = HttpClients.custom()
				.disableRedirectHandling()
				.setDefaultConnectionConfig(ConnectionConfig.custom().setCharset(StandardCharsets.UTF_8).build())
				.build();
		CloseableHttpResponse httpResponse = client.execute(httpGet);
		String htmlTxt = EntityUtils.toString(httpResponse.getEntity(), "utf-8");

		Document doc = Jsoup.parse(htmlTxt);
		System.err.println(doc);
		Elements ele = doc.getElementsByClass("RichText ztext CopyrightRichText-richText");
		PrintWriter printWriter = new PrintWriter(new File("cet4.txt"));
		
		for (Element element : ele) {
			String text = element.text();
			System.err.println(text);
			String s = text.replaceAll("(\\s*\\d*)(．)", "\r\n$1$2");
			s = s.replaceAll("(\\s*[一|二|三|四|五|六|七|八|九|十])(、)", "\r\n$1$2");
			s = s.replaceAll("(\\s*\\d+\\.\\s*)", "\r\n$1");
			printWriter.println(s);
		}

	}
}
