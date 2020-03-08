package com.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/*
 * HTTP客户端请求类
 */
public class HttpClient
{
	// 代理头
	private String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.25 Safari/537.36 Core/1.70.3741.400 QQBrowser/10.5.3863.400";

	/*
	 * 构造方法
	 */
	public HttpClient()
	{

	}

	/**
	 * get方法
	 * 
	 * @param httpUrl
	 * @param param
	 * @return
	 */
	public String get(String httpUrl, String param)
	{
		try
		{
			// 判断参数
			if (!param.equals(""))
			{
				// 封装地址
				httpUrl = httpUrl + "?" + param;
			}

			// 封装地址
			URL url = new URL(httpUrl);

			// 打开连接
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			// 设置GET方法
			conn.setRequestMethod("GET");
			// 设置代理头
			conn.setRequestProperty("Accept", "*/*");
			conn.setRequestProperty("Connection", "keep-alive");
			conn.setRequestProperty("User-Agent", userAgent);
			// 设置连接主机服务器的超时时间：15秒
			conn.setConnectTimeout(15000);
			// 设置读取远程返回数据的超时时间：60秒
			conn.setReadTimeout(60000);
			// 设置是否向httpUrlConnection输出
			// conn.setDoOutput(true);
			// 设置是否从httpUrlConnection读入
			// conn.setDoInput(true);

			// 发送GET方法请求
			conn.connect();

			// 获取URLConnection对象对应的输入流
			InputStream is = conn.getInputStream();
			// 构造一个字符流缓存
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));

			String line = "";
			StringBuffer stringBuffer = new StringBuffer();
			// 循环接收结果
			while ((line = br.readLine()) != null)
			{
				stringBuffer.append(line);
			}

			// 关闭流
			br.close();
			is.close();
			// 断开连接
			conn.disconnect();

			// 返回结果
			return stringBuffer.toString();

		} catch (MalformedURLException e)
		{
			// 返回错误信息
			return e.getMessage();

		} catch (IOException e)
		{
			// 返回错误信息
			return e.getMessage();
		}

	}

	/**
	 * Post方法
	 * 
	 * @param httpUrl
	 * @param param
	 * @return
	 */
	public String post(String httpUrl, String param)
	{
		try
		{
			// 封装地址
			URL url = new URL(httpUrl);
			// 打开和url之间的连接
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			// 设置POST方法
			conn.setRequestMethod("POST");
			// 设置代理头
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("User-Agent", userAgent);
			// 设置连接主机服务器的超时时间：15秒
			conn.setConnectTimeout(15000);
			// 设置读取远程返回数据的超时时间：60秒
			conn.setReadTimeout(60000);
			// 设置是否向httpUrlConnection输出
			conn.setDoOutput(true);
			// 设置是否从httpUrlConnection读入
			conn.setDoInput(true);
			// 设置传入参数的格式:请求参数应该是 name1=value1&name2=value2 的形式。
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			// 设置鉴权信息：Authorization: Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0
			conn.setRequestProperty("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");

			// 连接
			conn.connect();

			// 通过连接对象获取一个输出流
			OutputStream os = conn.getOutputStream();
			// 通过输出流对象将参数写出去/传输出去,它是通过字节数组写出的
			os.write(param.getBytes());

			// 获取URLConnection对象对应的输入流
			InputStream is = conn.getInputStream();
			// 构造一个字符流缓存
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));

			String line = "";
			StringBuffer stringBuffer = new StringBuffer();
			// 循环接收结果
			while ((line = br.readLine()) != null)
			{
				stringBuffer.append(line);
			}

			// 关闭流
			br.close();
			is.close();
			os.close();
			// 断开连接
			conn.disconnect();

			// 返回结果
			return stringBuffer.toString();

		} catch (MalformedURLException e)
		{
			// 返回错误信息
			return e.getMessage();

		} catch (IOException e)
		{
			// 返回错误信息
			return e.getMessage();
		}

	}

	/**
	 * @return userAgent
	 */
	public String getUserAgent()
	{
		return userAgent;
	}

	/**
	 * @param userAgent
	 *            要设置的 userAgent
	 */
	public void setUserAgent(String userAgent)
	{
		this.userAgent = userAgent;
	}

}
