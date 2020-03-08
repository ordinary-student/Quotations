package com.main;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.ui.QuotationsFrame;

/*
 * 语录
 */
public class Quotations
{

	public Quotations()
	{

	}

	public static void main(String[] args)
	{
		try
		{
			// 设置本地系统窗口风格样式
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "加载本地系统窗口样式失败！");
		} finally
		{
			new QuotationsFrame().setVisible(true);
		}

	}

}
