package com.ui;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KButton;
import javax.swing.KFrame;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileSystemView;
import javax.util.WindowUtil;

import com.api.BaiDuAPI;
import com.quotation.QuotationsGenerator;
import com.quotation.QuotationsReader;

/**
 * 窗口类
 */
public class QuotationsFrame extends KFrame implements ItemListener
{
	private static final long serialVersionUID = -4686185099375444926L;

	// 头部面板
	private JPanel panel;
	// 类型列表框
	private JComboBox<String> type_box;
	// 单条结果框
	private JTextField textField;
	// 获取按钮
	private KButton kButton;
	// 结果记录框
	private JTextArea textArea;

	// 右键弹出菜单
	private JPopupMenu popupMenu;
	// 右键菜单项-播放声音
	private JMenuItem playSound_item;
	// 右键菜单项-静音
	private JCheckBoxMenuItem mute_item;
	// 右键菜单项-清空结果
	private JMenuItem clearResult_item;
	// 右键菜单项-导出结果
	private JMenuItem exportResult_item;

	// 朗读器
	private QuotationsReader reader;
	// 上一个音频文件
	// private File lastAudioFile;

	/*
	 * 构造方法
	 */
	public QuotationsFrame()
	{
		// 初始化界面
		initUI();
		// 朗读器
		reader = new QuotationsReader();
	}

	/*
	 * 初始化界面
	 */
	private void initUI()
	{
		// 设置标题
		setTitle("语录");
		// 设置大小
		setSize(600, 400);
		// 设置最小的大小
		setMinimumSize(new Dimension(500, 400));
		// 设置居中
		WindowUtil.center(this);
		// 设置布局
		getContentPane().setLayout(new BorderLayout(5, 5));
		// 设置边框
		((JComponent) getContentPane()).setBorder(BorderFactory.createTitledBorder(""));
		// 设置关闭
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		// 不显示界面
		setVisible(false);

		// 头部面板
		panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));

		// 类型-下拉列表组件
		type_box = new JComboBox<String>();
		type_box.setPreferredSize(new Dimension(100, 30));
		type_box.setFont(new Font("Microsoft YaHei", Font.PLAIN, 15));
		type_box.setFocusable(false);
		type_box.addItem("社会语录");
		type_box.addItem("情话说");
		panel.add(type_box, BorderLayout.WEST);

		// 输入框
		textField = new JTextField();
		textField.setPreferredSize(new Dimension(350, 30));
		textField.setFont(new Font("Microsoft YaHei", Font.PLAIN, 15));
		// 添加键盘监听
		textField.addKeyListener(this);
		panel.add(textField, BorderLayout.CENTER);

		// 按钮
		kButton = new KButton("获取", 1);
		kButton.setPreferredSize(new Dimension(100, 30));
		kButton.setFont(new Font("Microsoft YaHei", Font.PLAIN, 15));
		kButton.setFocusPainted(false);
		kButton.addActionListener(this);
		panel.add(kButton, BorderLayout.EAST);

		// 添加头部面板
		getContentPane().add(panel, BorderLayout.NORTH);

		// 输出域
		textArea = new JTextArea();
		textArea.setFont(new Font("Microsoft YaHei", Font.PLAIN, 15));
		textArea.setLineWrap(true);
		// 添加鼠标监听
		textArea.addMouseListener(this);
		// 添加键盘监听
		textArea.addKeyListener(this);

		// 创建带滚动条的面板
		JScrollPane output_scroller = new JScrollPane(textArea);
		output_scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		output_scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		output_scroller.setBorder(BorderFactory.createTitledBorder("记录"));
		// output_scroller.setPreferredSize(new Dimension(100, 150));
		// 添加输出域
		getContentPane().add(output_scroller, BorderLayout.CENTER);

		// 右键菜单
		popupMenu = new JPopupMenu();

		// 播放声音-右键菜单项
		playSound_item = new JMenuItem("朗读");
		playSound_item.addActionListener(this);
		popupMenu.add(playSound_item);

		// 分隔线
		popupMenu.addSeparator();

		// 静音-右键菜单项
		mute_item = new JCheckBoxMenuItem("静音");
		mute_item.addItemListener(this);
		popupMenu.add(mute_item);

		// 分隔线
		popupMenu.addSeparator();

		// 清空记录-右键菜单项
		clearResult_item = new JMenuItem("清空记录");
		clearResult_item.addActionListener(this);
		popupMenu.add(clearResult_item);

		// 分隔线
		popupMenu.addSeparator();

		// 清空记录-右键菜单项
		exportResult_item = new JMenuItem("导出记录");
		exportResult_item.addActionListener(this);
		popupMenu.add(exportResult_item);

		// 排版
		validate();
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		// 判断是否触发弹出菜单事件
		if (e.isPopupTrigger())
		{
			// 显示弹出菜单
			popupMenu.show(e.getComponent(), e.getX(), e.getY());
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e)
	{
		// 勾选框状态发生改变
		if (mute_item.isSelected())
		{
			// 停止朗读
			reader.stop();
			// 静音
			QuotationsReader.soundFlag = false;

		} else
		{
			// 声音标志
			QuotationsReader.soundFlag = true;
		}

	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// 判断来源
		if (e.getSource() == kButton)
		{
			// 获取语录
			String result = getQuotations();
			// 输出
			textField.setText(result);
			// 记录
			textArea.setText(result + "\r\n" + textArea.getText());

			// 朗读语录
			readQuotations(result);

		} else if (e.getSource() == playSound_item)
		{
			// 朗读上一条
			reader.read();

		} else if (e.getSource() == clearResult_item)
		{
			// 清空记录
			textArea.setText("");

		} else if (e.getSource() == exportResult_item)
		{
			// 导出记录
			export();
		}

	}

	/**
	 * 开始获取语录
	 */
	private String getQuotations()
	{
		// 创建语录生成器
		QuotationsGenerator quotationsGenerator = new QuotationsGenerator();
		// 结果
		String result = quotationsGenerator.generate(type_box.getSelectedIndex());
		// 返回结果
		return result;
	}

	/**
	 * 朗读语录
	 */
	private void readQuotations(String text)
	{
		reader.read(text);
		// lastAudioFile = reader.getAudioFile();
	}

	/**
	 * 导出
	 */
	private void export()
	{
		try
		{
			// 当前用户桌面路径
			String desktopPath = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
			// 文件路径
			String path = desktopPath + "\\yulu.txt";
			// 封装文件
			File file = new File(path);
			// 封装流
			FileWriter fileWriter = new FileWriter(file);
			// 写入
			fileWriter.write(textArea.getText());
			//
			fileWriter.flush();
			// 关闭流
			fileWriter.close();

			JOptionPane.showMessageDialog(this, "导出完成！", "导出完成！", JOptionPane.INFORMATION_MESSAGE);

		} catch (Exception e)
		{
			JOptionPane.showMessageDialog(this, "导出出错！", "错误警告！", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		// 判断键值
		if (e.getKeyCode() == KeyEvent.VK_F1)
		{
			// 判断来源
			if (e.getSource() == textField)
			{
				QuotationsReader.autoDelete = !QuotationsReader.autoDelete;
				String message = "";
				// 自动删除标志
				if (QuotationsReader.autoDelete)
				{
					message = "自动删除已开启";
				} else
				{
					message = "自动删除已关闭";
				}

				JOptionPane.showMessageDialog(this, message);

			} else if (e.getSource() == textArea)
			{
				try
				{
					// 打开音频存放目录
					Desktop.getDesktop().open(new File(BaiDuAPI.dir));

				} catch (IOException e1)
				{
					e1.printStackTrace();
					return;
				}
			}
		}

	}

}
