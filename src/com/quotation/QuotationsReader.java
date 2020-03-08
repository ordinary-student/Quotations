package com.quotation;

import java.io.File;
import java.net.URLEncoder;

import com.api.BaiDuAPI;
import com.player.AudioPlayer;

/*
 * 语录朗读器
 */
public class QuotationsReader
{
	// 音频文件
	public File audioFile;
	// 创建播放器
	AudioPlayer player;
	// 静音标志
	public static boolean soundFlag = true;
	// 自动清除标志
	public static boolean autoDelete = true;

	/*
	 * 构造方法
	 */
	public QuotationsReader()
	{

	}

	/**
	 * 朗读音频文件
	 * 
	 * @param file
	 */
	public void read()
	{
		// 朗读上一个音频文件
		read(audioFile);
	}

	/**
	 * 朗读音频文件
	 * 
	 * @param file
	 */
	public void read(File file)
	{
		if ((file != null) && (soundFlag))
		{
			// 播放器线程
			new Thread()
			{
				public void run()
				{
					// 创建播放器
					player = new AudioPlayer(file);
					// 播放
					player.play();
				}
			}.start();
		}
	}

	/**
	 * 朗读语录
	 * 
	 * @param text
	 */
	public void read(String text)
	{
		try
		{
			// 转码
			String urlStr = URLEncoder.encode(text, "utf-8");

			// 创建API
			BaiDuAPI api = new BaiDuAPI();
			// 转为音频文件
			File file = api.text2Audio(urlStr);
			// 不为空
			if (file != null)
			{
				// 自动清除
				if ((audioFile != null) && (autoDelete))
				{
					// 删除前一个音频文件
					delete(audioFile);
				}

				// 保存为上一个音频文件
				audioFile = file;
				// 朗读
				read(file);
			}

		} catch (Exception e)
		{
			e.printStackTrace();
			return;
		}
	}

	/**
	 * 停止朗读
	 */
	public void stop()
	{
		player.stop();
	}

	/**
	 * 删除音频文件
	 */
	public void delete(File file)
	{
		if (file.isFile() && file.exists())
		{
			file.delete();
		}
	}

	/**
	 * @return audioFile
	 */
	public File getAudioFile()
	{
		return audioFile;
	}

	/**
	 * @param audioFile
	 *            要设置的 audioFile
	 */
	public void setAudioFile(File audioFile)
	{
		this.audioFile = audioFile;
	}

}
