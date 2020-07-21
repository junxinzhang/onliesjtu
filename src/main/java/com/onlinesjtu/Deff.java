package com.onlinesjtu;

import com.alibaba.fastjson.JSON;

import java.util.Map;

public class Deff {

	public static void main(String[] args) {
		//
		//        Scanner scanner = new Scanner(System.in);
		//        System.err.println("请输入x ");
		//        int i = scanner.nextInt();
		//        System.err.println("请输入x ： " + i);
		//
		//        System.err.println("请输入y ：");
		//        int j = scanner.nextInt();
		//        System.err.println("请输入y ： " + j);
		//
		//        System.err.println("最大公约数= " + deff(i, j));
		//        System.err.println("最小公倍数= " + (i * j) / deff(i, j));
		//
		//        System.err.println(72 >> 1);
		//        String s = " 0123456789";
		//        String s1, s2;
		//        s1 = s.substring(2);
		//        s2 = s.substring(2, 5);
		//        System.out.println(s1 + s2);

		String sss = "{\"account\":\"leads_catalyst_prod\",\"id\":\"18018616088\",\"seq\":\"12480\",\"type\":\"mobile\",\"infoJson\":\"{\\\"mobile\\\":\\\"...\\\",\\\"lead_time\\\":\\\"...\\\",\\\"name\\\":\\\"...\\\"}\"}";

		System.err.println(JSON.parseObject(sss, Map.class).get("infoJson"));

		int i = 6;
		System.err.println("===" + (i += i - 1));
	}

	static int deff(int x, int y) {
		if (x == y) {
			return x;
		} else if (x < y) {
			int t = x;
			x = y;
			y = t;
		}
		for (int k = x % y; k != 0; ) {
			k = x % y;
			x = y;
			y = k;
		}
		//        int k = x % y;
		//        while (k != 0) {
		//            k = x % y;
		//            x = y;
		//            y = k;
		//        }
		return x;
	}


}
