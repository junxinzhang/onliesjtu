package com.onlinesjtu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class ReadByLine {
	private String fileName;
	private  ArrayList<String> arrayList = new ArrayList<String>();

	// 初始化
	public ReadByLine(String fileName) {
		super();
		this.fileName = fileName;
	}

	public void toArray() {
		try {
			File file = new File(fileName);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String str;

			while ((str = br.readLine()) != null) {
				arrayList.add(str);
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
		for (String[] s : array) {
			for (String ss : s) {
				System.out.print("000");  // 左对齐
				System.out.printf("%-4s", ss);  // 左对齐
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		ReadByLine a = new ReadByLine("C:\\Users\\Mathartsys\\Desktop\\code4.txt");
		a.toArray();
		String[][] array = a.dealArray();
		a.printfArray(array);
	}

}