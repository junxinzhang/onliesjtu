package com.onlinesjtu;

import org.apache.commons.io.FileUtils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownloadVideo {

	private static String _数据挖掘_pptx = "http://218.1.73.51/pluginfile.php/97298/mod_resource/content/4/%E7%AC%AC1%E7%AB%A0%20%E7%BB%AA%E8%AE%BA--%E6%95%B0%E6%8D%AE%E6%8C%96%E6%8E%98%E8%AF%BE%E7%A8%8B%E5%AF%BC%E5%AD%A6.pptx";
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
		for (int i = 51657; i <= 51657; i++) {
			final int finalI = i;
			ex.submit(() -> downloadVideo("F:\\onlinesjtu\\2021春\\01数据挖掘", String.format(_数据挖掘, finalI)));
//			ex.submit(() -> downLoadFromUrl(_数据挖掘_pptx, "F:\\onlinesjtu\\2021春\\01数据挖掘", finalI + ".pptx"));
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

	public static void downloadByCommonIO(String url, String saveDir, String fileName) {
		try {
			FileUtils.copyURLToFile(new URL(url), new File(saveDir, fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 使用NIO下载文件，需要 jdk 1.4+
	 *
	 * @param url
	 * @param saveDir
	 * @param fileName
	 */
	public static void downloadByNIO(String url, String saveDir, String fileName) {
		ReadableByteChannel rbc = null;
		FileOutputStream fos = null;
		FileChannel foutc = null;
		try {
			rbc = Channels.newChannel(new URL(url).openStream());
			File file = new File(saveDir, fileName);
			file.getParentFile().mkdirs();
			fos = new FileOutputStream(file);
			foutc = fos.getChannel();
			foutc.transferFrom(rbc, 0, Long.MAX_VALUE);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (rbc != null) {
				try {
					rbc.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (foutc != null) {
				try {
					foutc.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 使用NIO下载文件，需要 jdk 1.7+
	 *
	 * @param url
	 * @param saveDir
	 * @param fileName
	 */
	public static void downloadByNIO2(String url, String saveDir, String fileName) {
		try (InputStream ins = new URL(url).openStream()) {
			Path target = Paths.get(saveDir, fileName);
			Files.createDirectories(target.getParent());
			Files.copy(ins, target, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 使用传统io stream下载文件
	 *
	 * @param url
	 * @param saveDir
	 * @param fileName
	 */
	public static void downloadByIO(String url, String saveDir, String fileName) {
		BufferedOutputStream bos = null;
		InputStream is = null;
		try {
			byte[] buff = new byte[8192];
			is = new URL(url).openStream();
			File file = new File(saveDir, fileName);
			file.getParentFile().mkdirs();
			bos = new BufferedOutputStream(new FileOutputStream(file));
			int count = 0;
			while ((count = is.read(buff)) != -1) {
				bos.write(buff, 0, count);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 使用Byte Array获得stream下载文件
	 *
	 * @param urlStr
	 * @param fileName
	 * @param savePath
	 * @throws IOException
	 */
	public static void downLoadFromUrl(String urlStr, String savePath, String fileName) {
		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			String toString = conn.getURL().toString();
			fileName = toString.substring(toString.lastIndexOf('/') + 1);


			//设置超时间为5秒
			conn.setConnectTimeout(5 * 1000);
			//防止屏蔽程序抓取而返回403错误
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

			//得到输入流
			InputStream input = conn.getInputStream();
			//获取自己数组
			byte[] getData = readInputStream(input);

			//文件保存位置
			File saveDir = new File(savePath);
			if (!saveDir.exists()) {
				saveDir.mkdir();
			}
			File file = new File(saveDir + File.separator + fileName);
			FileOutputStream output = new FileOutputStream(file);
			output.write(getData);
			if (output != null) {
				output.close();
			}
			if (input != null) {
				input.close();
			}
			System.out.println("download success!!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static  byte[] readInputStream(InputStream inputStream) throws IOException {
		byte[] buffer = new byte[10240];
		int len = 0;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while((len = inputStream.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		bos.close();
		return bos.toByteArray();
	}
}