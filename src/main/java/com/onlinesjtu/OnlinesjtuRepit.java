package com.onlinesjtu;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHeaders;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 导出作业
 */
public class OnlinesjtuRepit {

	public static void main(String[] args) throws IOException {

		String url = "http://course.onlinesjtu.com/mod/quiz/review.php?attempt=1169917";
		final String DEFAULT_USER = "xxxx";
		final String DEFAULT_PASS = "xxx";

		HttpGet httpGet = new HttpGet(url);
		String auth = DEFAULT_USER + ":" + DEFAULT_PASS;
		byte[] encodedAuth = Base64.encodeBase64(auth.getBytes());
		String authHeader = "Basic " + new String(encodedAuth);
		httpGet.addHeader(HttpHeaders.AUTHORIZATION, authHeader);

		CookieStore cookieStore = new BasicCookieStore();
		BasicClientCookie cookie = new BasicClientCookie("MoodleSessionmoodle286",
				"6il10id9h9nnl2l2gto7htr6b2");
		cookie.setDomain("course.onlinesjtu.com");
		cookie.setPath("/");
		cookieStore.addCookie(cookie);

		CloseableHttpClient client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		CloseableHttpResponse httpResponse = client.execute(httpGet);
		String htmlTxt = EntityUtils.toString(httpResponse.getEntity(), "utf-8");

		Document doc = Jsoup.parse(htmlTxt);
		System.err.println(doc);
		Elements ele = doc
				.getElementsByClass("que multichoice deferredfeedback correct");
		PrintWriter printWriter = new PrintWriter(new File("homework.txt"));
		for (Element element : ele) {
			printWriter.println(element.text());
			System.out.println(element.text());
		}
		printWriter.flush();
		printWriter.close();
	}
}
