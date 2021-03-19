package com.onlinesjtu;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownloadVideo {

	private static String _数据挖掘 = "http://218.1.73.42/mooc/2021_1/computer/2951/%s.mp4";
	private static String _C = "http://218.1.73.42/mooc/2020_3/guawang/2839/%s.mp4";
	private static String _嵌入式系统及应用 = "http://218.1.73.42/mooc/2020_3/common/2719/%s.mp4";
	private static String _网络信息与安全 = "http://218.1.73.42/mooc/2020_3/computer/2501/%s.mp4";
	private static String _数据库系统管理与维护 = "http://218.1.73.42/mooc/2020_3/guawang/2305/%s.mp4";
	private static String _创新与创业管理 = "http://218.1.73.42/mooc/2020_3/manage/1224/%s.mp4";
	private static String _可视化计算及应用 = "http://218.1.73.42/mooc/2020_3/guawang/758/%s.mp4";
	private static String systemUrl = "http://218.1.73.42/mooc/2020_1/computer/1172/%s.mp4";
	private static String javaUrl = "http://218.1.73.42/mooc/2020_1/computer/2469/%s.mp4";
	private static String dataBaseUrl = "http://218.1.73.42/mooc/2020_1/computer/2323/%s.mp4";
	private static String english3Url = "http://218.1.73.42/mooc/2020_1/english/2064/%s.mp4";
	private static String maogaiUrl = "http://218.1.73.42/mooc/2020_1/guawang/1767/%s.mp4";
	private static String xuewei_system_url = "http://218.1.73.42/mooc/2019_3/xuewei/723/%s.mp4";
	private static String doc_system_url = "http://218.1.73.10/bachelor/download/%s.doc";
	private static final int MAX_BUFFER_SIZE = 1000000;
	private static ExecutorService ex = Executors.newFixedThreadPool(10);

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		for (int i = 1; i <= 15; i++) {
			final int finalI = i;
			ex.submit(() -> downloadVideo("F:\\onlinesjtu\\2021春\\01数据挖掘", String.format(_数据挖掘, finalI)));
		}
	}

	private static void downloadVideo(String filePath, String videoUrl) {
		HttpURLConnection connection = null;
		InputStream inputStream = null;
		RandomAccessFile randomAccessFile = null;
		try {
			// 1.获取连接对象
			URL url = new URL(videoUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("Range", "bytes=0-");
			connection.connect();
			if (connection.getResponseCode() / 100 != 2) {
				System.out.println("连接失败... " + videoUrl);
				downloadVideo(filePath, videoUrl);
				return;
			}
			// 2.获取连接对象的流
			inputStream = connection.getInputStream();
			//已下载的大小
			int downloaded = 0;
			//总文件的大小
			int fileSize = connection.getContentLength();
			String fileName = url.getFile();
			fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
			fileName = filePath.concat(File.separator).concat(fileName);
			// 3.把资源写入文件
			randomAccessFile = new RandomAccessFile(fileName, "rw");
			while (downloaded < fileSize) {
				// 3.1设置缓存流的大小
				byte[] buffer = null;
				if (fileSize - downloaded >= MAX_BUFFER_SIZE) {
					buffer = new byte[MAX_BUFFER_SIZE];
				} else {
					buffer = new byte[fileSize - downloaded];
				}
				// 3.2把每一次缓存的数据写入文件
				int read = -1;
				int currentDownload = 0;
				long startTime = System.currentTimeMillis();
				while (currentDownload < buffer.length) {
					read = inputStream.read();
					buffer[currentDownload++] = (byte) read;
				}
				long endTime = System.currentTimeMillis();
				double speed = 0.0;
				if (endTime - startTime > 0) {
					speed = currentDownload / 1024.0 / ((double) (endTime - startTime) / 1000);
				}
				randomAccessFile.write(buffer);
				downloaded += currentDownload;
				randomAccessFile.seek(downloaded);
				System.out.printf(Thread.currentThread().getName() + " [" + fileName
								+ "]下载了进度:%.2f%%,下载速度：%.1fkb/s(%.1fM/s)%n", downloaded * 1.0 / fileSize * 10000 / 100, speed,
						speed / 1000);
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.disconnect();
				inputStream.close();
				randomAccessFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void demo() {
		try {
			URL url = new URL(systemUrl);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}