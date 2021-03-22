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
		String urlParam = "sid=468570DA12124F77A219B72C93B81CD6&c_sStr=310022_1&__RequestVerificationToken=x_UH7UNy5l6jvrZJq0-dfo9Hh_Z4XchbsbPo2gCD7MR0KDuntTR4TkQ3MTJgZ12FGROKF4y9RNZ90YYEWvAo-Cq-eWRvHYQMzqmVV1KYASw1";

		JSONObject headers = new JSONObject();
		headers.put("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.90 Safari/537.36");
		headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		headers.put("Accept-Encoding", "gzip, deflate");
		headers.put("Accept-Language", "zh-CN,zh;q=0.9,zh-TW;q=0.8,en-US;q=0.7,en;q=0.6");
		headers.put("Cookie",
				"oKn5ajIUA1e8S=58JcSgVr5.heQ6oKsXDz0xNgmP5oSs.OpiMJL5LtNrFXhBDf04xjCQPL54fJzPw7oqRSiLH66C9o4TCYhzH.KvG; oKn5ajIUA1e8T=53l4d7Crid4AqqqmgJxM6vGQd7hA6f7O.6Q0WDzRo6yG8QK9iEJUekhEM9L_F1Biv7V94xY4LAG1o8ZQ_dUc_Oq38GcRTv9n1LqIVqUZI6UfkFqkUYKiJL4KBitsvuigNS.oR9sGI06MNjqFronr0xbEuXqbIXtceTO6BBrL_.LMeQM4bvvopviti4m.NtJIJUU8ZG2kT_u9aHrQpO4ucquUtug0EdVjxvzfT7gOxhwACiVkWNECjJPrr80W37eEgmKdUZFtRuDMJKHFcGqnXoYPQREkEXmcoSUO9TQVtG2cfybqX5kB2P.qTieamFHXhl; Hm_lvt_db4f828728fa6ba91665a269d396f166=1615360815,1615536997,1616033586; Hm_lvt_c9c0128aec42c22340930c8062591842=1615360815,1615536997,1616033587; Hm_lvt_2c27f6e0da87610221a9d3a549f876bc=1615360815,1615536997,1616033588; Hm_lvt_dc1d69ab90346d48ee02f18510292577=1615360751,1616381148; Hm_lpvt_dc1d69ab90346d48ee02f18510292577=1616381148; BIGipServercache_pool=1996539914.20480.0000; Hm_lpvt_db4f828728fa6ba91665a269d396f166=1616392720; ASP.NET_SessionId=n0gxy45mpck0xdw2w44ms5pm; .ASPXAUTH=4C04303D8990852A165018AAC81777E40431A3811B847C83A22863D01E1A8061E22AFB447019393F3EC8ABB598E8E756FFA027A5C919349D51D1A5D00EF01A5D944E57331B948F53D273D3ACC1F55CD41767C610A09A3DA3A9EC02133C876BCE; BIGipServercet_pool=762431498.22016.0000; __RequestVerificationToken=7BkZHu1Tr_Hifud_fu0jBrU5cHC2aF0scaGThlnsFQWYIkW6ZFprdlFj8_l3C1BKCtHZQ5Jlmr9lokFEYZmKhEWMF9bk8llugeaNidFFLCg1; Hm_lpvt_2c27f6e0da87610221a9d3a549f876bc=1616392753; Hm_lpvt_c9c0128aec42c22340930c8062591842=1616392753");
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
