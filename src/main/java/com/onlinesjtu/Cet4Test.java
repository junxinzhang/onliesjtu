package com.onlinesjtu;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import wiremock.com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Cet4Test {

	public static ExecutorService executorService = new ThreadPoolExecutor(10, 50, 0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(10240),
            new ThreadFactoryBuilder().setDaemon(true).setNameFormat("fetch-pool-%d").build(),
            new ThreadPoolExecutor.CallerRunsPolicy());

	public static void main(String[] args) {

		String url = "http://cet-bm.neea.edu.cn/Student/SaveRW";

//		String urlParam = "sid=468570DA12124F77A219B72C93B81CD6&c_sStr=310022_1&__RequestVerificationToken=lBn2KyrDTXTYoDkEiVAqXz8LRCoae6Et56f3TEdrUjCxJ49X1aDYyAsZADBqY_qcjGFp5XrGCvHa9qR8Qhu65DZRG7zaIPRCv6k4eFBYIO41";
//		String urlParam = "sid=468570DA12124F77A219B72C93B81CD6&c_sStr=310022_1&__RequestVerificationToken=x_UH7UNy5l6jvrZJq0-dfo9Hh_Z4XchbsbPo2gCD7MR0KDuntTR4TkQ3MTJgZ12FGROKF4y9RNZ90YYEWvAo-Cq-eWRvHYQMzqmVV1KYASw1";
//		String urlParam = "sid=468570DA12124F77A219B72C93B81CD6&c_sStr=310022_1&__RequestVerificationToken=6e-ooObYL_DQvWN3Ot48CzatGQPfPzzHURJDp6CQ1qOO2rC-nCkVysLENOsjLAl23So2gCcknOV0U73HE0rqIGNgb4pCrGrVHEDq29P1V-M1";
		String urlParam = "sid=468570DA12124F77A219B72C93B81CD6&c_sStr=310022_1&__RequestVerificationToken=YubmTnPsOO9v0NzZ9z75Cx2gj1aD3s7xDQu6hh_tF65YTlrq48KsQtHyGuZf_zcOWB-i1qKun49revbXk166DI_oo2fg78nRhARDbNEpmaE1";

		JSONObject headers = new JSONObject();
		headers.put("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.90 Safari/537.36");
		headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		headers.put("Accept-Encoding", "gzip, deflate");
		headers.put("Accept-Language", "zh-CN,zh;q=0.9,zh-TW;q=0.8,en-US;q=0.7,en;q=0.6");
		headers.put("Cookie",
				"oKn5ajIUA1e8S=58JcSgVr5.heQ6oKsXDz0xNgmP5oSs.OpiMJL5LtNrFXhBDf04xjCQPL54fJzPw7oqRSiLH66C9o4TCYhzH.KvG; Hm_lvt_db4f828728fa6ba91665a269d396f166=1615360815,1615536997,1616033586; Hm_lvt_c9c0128aec42c22340930c8062591842=1615360815,1615536997,1616033587; Hm_lvt_2c27f6e0da87610221a9d3a549f876bc=1615360815,1615536997,1616033588; Hm_lvt_dc1d69ab90346d48ee02f18510292577=1615360751,1616381148; Hm_lpvt_dc1d69ab90346d48ee02f18510292577=1616381148; Hm_lpvt_db4f828728fa6ba91665a269d396f166=1616463469; ASP.NET_SessionId=kbi0yjtrsohv0x1adpmnvy4w; .ASPXAUTH=90842A1448A88A12F10051DD20D2C5BF7C00A2C38FF651606089BFCAE52F7316448293589CDE9E0BAECCB4DB9C6F06853D603AF8D562E0E67408DB89A1AD918CBF96F97EDA4A0DB35ECFC4BD77EFA9B7167BCE3B21C236A15B70F8A90963ADD0; BIGipServercet_pool=930203658.22016.0000; BIGipServercache_pool=2013317130.20480.0000; __RequestVerificationToken=jZMS3OwuyZCgmig2QN-WNjNG747pJzHAIlhFSeqsEtkp0D8SB-BtozEw-HzN1CW-RxfUbBziOpBlrxBMBxkiwVigOKUTK9C7Gn-loO19qOA1; Hm_lpvt_2c27f6e0da87610221a9d3a549f876bc=1616463508; Hm_lpvt_c9c0128aec42c22340930c8062591842=1616463508");
		for (; ; ) {
			String s = HttpClientUtils.doPost(url, urlParam, headers);
			log.info("time: [{}] \t response: {}", DateTimeFormatter.ofPattern("yyyy-HH-dd HH:mm:ss").format(LocalDateTime.now()), s);
			try {
				Thread.sleep(new Random().nextInt(10) * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
