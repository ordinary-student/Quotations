package com.player;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/***
 * 音乐播放器类
 */
public class AudioPlayer
{
	// 播放器
	public Player player;
	// 音频文件
	public File audioFile;

	/*
	 * 构造方法
	 */
	public AudioPlayer(File audioFile)
	{
		this.audioFile = audioFile;
	}

	/**
	 * 播放方法
	 */
	public void play()
	{
		try
		{
			// 缓冲流
			BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(audioFile));
			// 创建播放器
			player = new Player(buffer);
			// 播放
			player.play();

		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
			return;

		} catch (JavaLayerException e)
		{
			e.printStackTrace();
			return;
		}
	}

	/**
	 * 停止方法
	 */
	public void stop()
	{
		try
		{
			if (player != null)
			{
				// 关闭
				player.close();
			}

		} catch (Exception e)
		{
			e.printStackTrace();
			return;
		}
	}

}
