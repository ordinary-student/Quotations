package com.api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/*
 * 百度API
 */
public class BaiDuAPI
{
	// 语速
	public int speed = 4;
	// 前缀
	public String head = "https://fanyi.baidu.com/gettts?lan=zh&text=";
	// 音频文件存放目录
	public static String dir = "E:\\temp\\yulu\\";

	/*
	 * 构造方法
	 */
	public BaiDuAPI()
	{

	}

	/*
	 * 文字转语音
	 */
	public File text2Audio(String text)
	{
		return text2Audio(text, speed);
	}

	/*
	 * 文字转语音
	 */
	public File text2Audio(String text, int speed)
	{
		// 后缀
		String tail = "&spd=" + speed + "&source=web";
		// 整条请求
		String request = head + text + tail;

		try
		{
			// 封装成url
			URL url = new URL(request);

			// 获取连接
			URLConnection conn = url.openConnection();

			// 文件名
			String fileName = dir + System.currentTimeMillis() + ".mp3";

			// 获取输入流
			InputStream in = conn.getInputStream();
			// 文件输出流
			FileOutputStream fos = new FileOutputStream(new File(fileName));

			// 缓冲区大小
			byte[] buffer = new byte[1024];

			// 读取数据长度
			int len = 0;

			// 循环读取数据
			while ((len = in.read(buffer)) != -1)
			{
				// 保存文件流
				fos.write(buffer, 0, len);
			}

			// 关闭流
			fos.close();
			in.close();

			// 返回结果
			return new File(fileName);

		} catch (MalformedURLException e)
		{
			e.printStackTrace();
			return null;

		} catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}

}
