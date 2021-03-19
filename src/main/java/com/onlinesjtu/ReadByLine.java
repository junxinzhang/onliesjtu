package com.onlinesjtu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ReadByLine {
	private String fileName;
	private static   ArrayList<String> arrayList = new ArrayList<String>();
	private static   ArrayList<String> arrayList2 = new ArrayList<String>();
	private static   ArrayList<String> arrayList3 = new ArrayList<String>();

	// 初始化
	public ReadByLine(String fileName) {
		super();
		this.fileName = fileName;
	}

	public void toArray(List list) {
		try {
			File file = new File(fileName);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String str;

			while ((str = br.readLine()) != null) {
				list.add(str);
			}
			br.close();
			fr.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String[][] dealArray() {
		int length = arrayList.size();
		int width = arrayList.get(0).split(",").length;
		String[][] array = new String[length][width];
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				array[i][j] = arrayList.get(i).split(",")[j];
			}
		}
		return array;
	}

	public void printfArray(String[][] array) {
		StringBuilder sb = new StringBuilder();
		for (String[] s : array) {
			for (String ss : s) {
				System.out.print(",\"");  // 左对齐
				System.out.printf("%-4s", ss);  // 左对齐
				System.out.print("\"");  // 左对齐
			}
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		ReadByLine a = new ReadByLine("C:\\Users\\Mathartsys\\Desktop\\mobile.txt");
		ReadByLine distance = new ReadByLine("C:\\Users\\Mathartsys\\Desktop\\distance.txt");
		ReadByLine address = new ReadByLine("C:\\Users\\Mathartsys\\Desktop\\address.txt");
		a.toArray(arrayList);
		distance.toArray(arrayList2);
		address.toArray(arrayList3);
//		String[][] array = a.dealArray();
//		a.printfArray(array);

		PrintWriter pw = new PrintWriter(new File("deqin-call-tag.txt"));
		for (int i = 0; i < arrayList.size(); i++) {
//				String ss = arrayList.get(i);
//				Integer dd = Integer.valueOf(arrayList2.get(i));
//				String addr = arrayList3.get(i);
//			System.out.print("put(\"");  // 左对齐
//			System.out.printf("%-4s", ss+"-"+addr);  // 左对齐
//			System.out.println("\", new Struct(\"" + addr + "\", " + dd + "));");  // 左对齐
			String str = "( ( ( ( sal.full_result :: json ->> 'data' ) :: json -> 0 ) :: json ->> 'tags' ) :: json ->> '%s') %s";
			System.err.println(String.format(str, arrayList2.get(i), arrayList2.get(i)+"__"+arrayList.get(i)) +",");

//				String key = ss + "_moth";
//				Integer value = 0;
//				pw.append("set " + key + " " + value + "\r\n");
				//				sb.append("EXPIRE " + key + " " + (30 * 24 * 3600) + "\r\n");
//			pw.append(",'" + ss + "'");
//			pw.flush();
			//				System.err.println(sb.toString());
		}
	}

}