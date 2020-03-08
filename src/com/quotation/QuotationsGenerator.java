package com.quotation;

import com.http.HttpClient;

/*
 * 语录产生器
 */
public class QuotationsGenerator
{
	// 类型 0-社会语录 1-情话说
	public int type = 0;
	// 地址
	public String url = "https://cdn.ipayy.net/says/api.php";

	/*
	 * 构造方法
	 */
	public QuotationsGenerator()
	{

	}

	/*
	 * 构造方法
	 */
	public QuotationsGenerator(int type)
	{
		this.type = type;
	}

	/**
	 * 产生语录
	 * 
	 * @return
	 */
	public String generate()
	{
		return generate(this.type);
	}

	/**
	 * 产生语录
	 * 
	 * @param type
	 * @return
	 */
	public String generate(int type)
	{
		// 浏览器客户端
		HttpClient httpClient = new HttpClient();
		// 类型
		String typeParam = "shehui";
		// 判断类型
		if (type == 0)
		{
			// 社会语录
			typeParam = "shehui";

		} else
		{
			// 情话
			typeParam = "qinghua";
		}

		// 结果
		String result = httpClient.get(url, "type=" + typeParam);
		// 返回结果
		return result.trim().replaceAll(" ", "，");
	}

}
