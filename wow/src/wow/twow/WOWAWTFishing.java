package wow.twow;

import java.awt.Color;
import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;

import wow.twow.support.WOW;

public class WOWAWTFishing extends JFrame implements ActionListener,
		WindowListener {
	/**
	 * 2111
	 * 
	 */
	static int miscSize = 25;
	private static final long serialVersionUID = 1L;
	public static JLabel wowJLabel = new JLabel();
	// 日志
	public static JTextArea wowJTextArea = new JTextArea();
	public static JScrollPane wowJScrollPane = new JScrollPane(wowJTextArea);
	// 状态
	public static JLabel wowJLabel_f_x = new JLabel("左上   X：");
	public static JLabel wowJLabel_f_y = new JLabel("Y：");
	public static JTextField wowJTextField_f_x = new JTextField("494");
	public static JTextField wowJTextField_f_y = new JTextField("64");
	public static JLabel wowJLabel_s_x = new JLabel("右下   X：");
	public static JLabel wowJLabel_s_y = new JLabel("Y：");
	public static JTextField wowJTextField_s_x = new JTextField("865");
	public static JTextField wowJTextField_s_y = new JTextField("252");
	public static JLabel wowJLabel_t_x = new JLabel("声控   ：");
	public static JTextField wowJTextField_misc = new JTextField(miscSize + "");
	public static JLabel wowJLabel_t_x_v = new JLabel("0");

	public static JButton wowJButton_save = new JButton("修改");
	public static JLabel wowJLabel_y_x = new JLabel("鱼漂   X：");
	public static JLabel wowJLabel_y_y = new JLabel("Y：");
	public static JLabel wowJTextField_y_x = new JLabel("");
	public static JLabel wowJTextField_y_y = new JLabel("");
	public static JLabel wowJTextField_y_r = new JLabel("");
	public static JLabel wowJTextField_y_g = new JLabel("");
	public static JLabel wowJTextField_y_b = new JLabel("");
	public static JLabel wowJLabel_ok = new JLabel("拾取：");
	public static JLabel wowJTextField_ok = new JLabel("0");
	public static JLabel wowJLabel_no = new JLabel("失败：");
	public static JLabel wowJTextField_no = new JLabel("0");

	// 设置
	public static ButtonGroup wowButtonGroup_0_255 = new ButtonGroup();
	public static JRadioButton wowJRadioButton_0 = new JRadioButton("黑色寻点");
	public static JRadioButton wowJRadioButton_255 = new JRadioButton("白色寻点");
	public static JCheckBox wowJCheckBox_10 = new JCheckBox("10分钟鱼饵");
	public static JCheckBox wowJCheckBox_mf = new JCheckBox("木筏");
	public static JCheckBox wowJCheckBox_start = new JCheckBox("启动时鱼饵");
	public static JCheckBox wowJCheckBox_misc = new JCheckBox("按键声音");
	public static JCheckBox wowJCheckBox_next = new JCheckBox("多次下杆");
	public static JCheckBox wowJCheckBox_line = new JCheckBox("掉线警报");
	public static JCheckBox wowJCheckBox_dead = new JCheckBox("死亡警报");
	public static JCheckBox wowJCheckBox_seep = new JCheckBox("随即延迟");
	public static JCheckBox wowJCheckBox_mouse = new JCheckBox("鼠标移动");
	public static JCheckBox wowJCheckBox_gm = new JCheckBox("GM警报");
	public static JCheckBox wowJCheckBox_gm_hf = new JCheckBox("GM警报回复");
	public static JCheckBox wowJCheckBox_gm_ls = new JCheckBox("炉石");
	public static JCheckBox wowJCheckBox_out_line = new JCheckBox("下線");
	public static JCheckBox wowJCheckBox_times = new JCheckBox("定时");
	public static JTextField wowJTextField_times = new JTextField("60");
	public static JCheckBox wowJCheckBox_closewindow = new JCheckBox("关机");
	public static JCheckBox wowJCheckBox_automisc = new JCheckBox("自动调整");

	// 低
	public static JLabel wowJLabel_help = new JLabel();
	public static JTextField wowJTextField_gm = new JTextField(
			"您好，找我有事？#難道有人舉報我？有沒有點天理啊！#各種採集外掛滿天飛，你們不管！我在這釣魚你們可真積極！#我回城行了吧！！！，懶得和你說");

	// 变量1
	static int stat = 0;
	static int start = 2;

	static int areaPosition[][] = new int[2][2];
	static WOWAWTFishingThread wowAWTFishingThread;
	static WOWAWTFishing wowAWTFishing = new WOWAWTFishing();
	static long dateh = new java.util.Date().getTime();
	static long dateh_bf = dateh;
	static long dates = new java.util.Date().getTime();
	static long dates_bf = dates;

	@SuppressWarnings("unchecked")
	public WOWAWTFishing() {
		// 开启快捷键监听
		JIntellitype.getInstance().registerHotKey(1000, JIntellitype.MOD_ALT,
				(int) 'W');
		JIntellitype.getInstance().registerHotKey(2000, JIntellitype.MOD_ALT,
				(int) 'E');
		JIntellitype.getInstance().registerHotKey(3000, JIntellitype.MOD_ALT,
				(int) 'Q');
		// 布局设置
		List list = new ArrayList();

		list = getlist(list);
		this.setTitle((String) list.get((int) (Math.random() * 19)));
		this.setSize(500, 600);
		// 设置事件
		this.addWindowListener(this);
		wowJButton_save.addActionListener(this);
		// 日志
		wowJTextArea.setEnabled(false);
		wowJTextArea.setFont(new Font("", Font.PLAIN, 15));
		wowJTextArea.setBackground(new Color(252, 249, 220));
		wowJScrollPane.setBounds(0, 0, 220, 545);

		// 状态
		wowJLabel_f_x.setBounds(220, 10, 60, 25);
		wowJTextField_f_x.setBounds(280, 10, 30, 25);
		wowJLabel_f_y.setBounds(320, 10, 25, 25);
		wowJTextField_f_y.setBounds(345, 10, 30, 25);

		wowJLabel_s_x.setBounds(220, 40, 60, 25);
		wowJTextField_s_x.setBounds(280, 40, 30, 25);
		wowJLabel_s_y.setBounds(320, 40, 25, 25);
		wowJTextField_s_y.setBounds(345, 40, 30, 25);

		wowJLabel_t_x.setBounds(220, 70, 60, 25);
		wowJTextField_misc.setBounds(280, 70, 30, 25);
		wowJLabel_t_x_v.setBounds(330, 70, 30, 25);
		wowJButton_save.setBounds(310, 100, 65, 25);
		setSZenabled(false);

		wowJLabel_y_x.setBounds(220, 130, 60, 25);
		wowJTextField_y_x.setBounds(280, 130, 30, 25);
		wowJLabel_y_y.setBounds(320, 130, 25, 25);
		wowJTextField_y_y.setBounds(345, 130, 30, 25);

		wowJTextField_y_r.setBounds(280, 160, 32, 25);
		wowJTextField_y_r.setForeground(new Color(255, 0, 0));
		wowJTextField_y_g.setBounds(312, 160, 32, 25);
		wowJTextField_y_g.setForeground(new Color(10, 215, 06));
		wowJTextField_y_b.setBounds(344, 160, 32, 25);
		wowJTextField_y_b.setForeground(new Color(0, 0, 255));

		wowJLabel_ok.setBounds(220, 190, 45, 25);
		wowJTextField_ok.setBounds(265, 190, 40, 25);
		wowJTextField_ok.setForeground(new Color(10, 215, 06));
		wowJLabel_no.setBounds(305, 190, 45, 25);
		wowJTextField_no.setBounds(350, 190, 40, 25);
		wowJTextField_no.setForeground(new Color(255, 0, 0));

		// 设置
		wowButtonGroup_0_255.add(wowJRadioButton_0);
		wowButtonGroup_0_255.add(wowJRadioButton_255);
		wowJRadioButton_255.setBounds(390, 10, 100, 25);
		wowJRadioButton_0.setBounds(390, 40, 100, 25);
		wowJRadioButton_0.setSelected(true);

		wowJCheckBox_10.setBounds(390, 100, 100, 25);
		wowJCheckBox_10.setSelected(true);
		wowJCheckBox_mf.setBounds(390, 130, 100, 25);
		wowJCheckBox_mf.setSelected(true);

		wowJCheckBox_start.setBounds(390, 70, 100, 25);
		wowJCheckBox_start.setSelected(true);

		wowJCheckBox_misc.setBounds(390, 160, 100, 25);
		wowJCheckBox_misc.setSelected(true);

		wowJCheckBox_next.setBounds(390, 190, 100, 25);
		wowJCheckBox_next.setSelected(true);

		wowJCheckBox_line.setBounds(390, 220, 100, 25);
		wowJCheckBox_line.setSelected(true);

		wowJCheckBox_dead.setBounds(390, 250, 100, 25);
		wowJCheckBox_dead.setSelected(true);

		wowJCheckBox_seep.setBounds(390, 280, 100, 25);
		wowJCheckBox_seep.setSelected(true);

		wowJCheckBox_mouse.setBounds(390, 310, 100, 25);
		wowJCheckBox_mouse.setSelected(true);

		wowJCheckBox_gm.setBounds(390, 340, 100, 25);
		wowJCheckBox_gm.setSelected(false);

		wowJCheckBox_gm_hf.setBounds(390, 370, 100, 25);
		wowJCheckBox_gm_hf.setSelected(false);

		wowJCheckBox_gm_ls.setBounds(390, 400, 100, 25);
		wowJCheckBox_gm_ls.setSelected(false);

		wowJCheckBox_out_line.setBounds(390, 430, 100, 25);
		wowJCheckBox_out_line.setSelected(false);

		wowJCheckBox_times.setBounds(220, 250, 60, 25);
		wowJCheckBox_times.setSelected(false);

		wowJTextField_times.setBounds(280, 250, 35, 25);

		wowJCheckBox_closewindow.setBounds(390, 460, 100, 25);
		wowJCheckBox_closewindow.setSelected(false);

		wowJCheckBox_automisc.setBounds(220, 100, 80, 25);
		// wowJCheckBox_automisc.setSelected(true);
		// 底边

		wowJTextField_gm.setBounds(220, 520, 270, 25);

		wowJLabel_help.setBounds(0, 545, 480, 25);
		wowJLabel_help
				.setText("【取点ALT+W   启动ALT+E   停止ALT+Q   钓鱼1   魚餌2   木筏3   炉石4】");
		wowJLabel_help.setForeground(new Color(255, 0, 0));

		// 添加组件
		wowJLabel.add(wowJRadioButton_0);
		wowJLabel.add(wowJRadioButton_255);
		wowJLabel.add(wowJCheckBox_start);
		wowJLabel.add(wowJCheckBox_10);
		wowJLabel.add(wowJCheckBox_mf);
		wowJLabel.add(wowJCheckBox_misc);
		wowJLabel.add(wowJCheckBox_next);
		wowJLabel.add(wowJCheckBox_line);
		wowJLabel.add(wowJCheckBox_dead);
		wowJLabel.add(wowJCheckBox_seep);
		wowJLabel.add(wowJCheckBox_mouse);
		wowJLabel.add(wowJCheckBox_gm);
		wowJLabel.add(wowJCheckBox_gm_hf);
		wowJLabel.add(wowJCheckBox_gm_ls);
		wowJLabel.add(wowJCheckBox_out_line);
		wowJLabel.add(wowJCheckBox_times);
		wowJLabel.add(wowJTextField_times);
		wowJLabel.add(wowJCheckBox_closewindow);

		// 状态
		wowJLabel.add(wowJLabel_f_x);
		wowJLabel.add(wowJTextField_f_x);
		wowJLabel.add(wowJLabel_f_y);
		wowJLabel.add(wowJTextField_f_y);

		wowJLabel.add(wowJLabel_s_x);
		wowJLabel.add(wowJTextField_s_x);
		wowJLabel.add(wowJLabel_s_y);
		wowJLabel.add(wowJTextField_s_y);

		wowJLabel.add(wowJLabel_t_x);
		wowJLabel.add(wowJTextField_misc);
		wowJLabel.add(wowJLabel_t_x_v);

		wowJLabel.add(wowJButton_save);

		wowJLabel.add(wowJLabel_y_x);
		wowJLabel.add(wowJTextField_y_x);
		wowJLabel.add(wowJLabel_y_y);
		wowJLabel.add(wowJTextField_y_y);

		wowJLabel.add(wowJTextField_y_r);
		wowJLabel.add(wowJTextField_y_g);
		wowJLabel.add(wowJTextField_y_b);

		wowJLabel.add(wowJLabel_ok);
		wowJLabel.add(wowJTextField_ok);
		wowJLabel.add(wowJLabel_no);
		wowJLabel.add(wowJTextField_no);

		wowJLabel.add(wowJCheckBox_automisc);

		// 日志
		wowJLabel.add(wowJScrollPane);

		wowJLabel.add(wowJTextField_gm);
		wowJLabel.add(wowJLabel_help);
		wowJLabel.setBackground(new Color(252, 217, 139));
		this.add(wowJLabel);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(WOW.ICON));
	}

	// 窗口listener

	public void windowActivated(WindowEvent arg0) {
	}

	public void windowClosed(WindowEvent arg0) {
	}

	public void windowDeactivated(WindowEvent arg0) {
	}

	public void windowDeiconified(WindowEvent arg0) {
	}

	public void windowOpened(WindowEvent arg0) {
	}

	public void windowClosing(WindowEvent e) {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		try {
			wowAWTFishingThread.stop();
			wowAWTFishingThread.line.stop();
			wowAWTFishingThread.line.close();
		} catch (Exception ex) {

		}
	}
	
	/**
	 * 
	 * @comment 刪除IBM STEREO MIX產生的臨時文件 
	 * @author chenyanji
	 * @date 2013-5-27 下午04:27:44
	 * @param path
	 */
	public static void delAllFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			temp = new File(path+"/"+ tempList[i]);
			if (temp.isFile()) {
				temp.delete();
			}
		}
	}

	public static void main(String[] args) {
		delAllFile("C:/Users/Administrator/Music/VirtualAudioStreaming");
		wowAWTFishing.show();
		HotkeyListener HL = new HotkeyListener() {

			// 取值次数
			int get_size = 0;

			@SuppressWarnings( { "static-access", "deprecation" })
			public void onHotKey(int arg0) {
				try {
					@SuppressWarnings("unused")
					Robot robot = new Robot();
					WOWMisc wowMisc = new WOWMisc();
					if (arg0 == 1000) {

						if (stat == 0) {
							if (get_size == 0) {
								Point mousePoint = MouseInfo.getPointerInfo()
										.getLocation();
								wowAWTFishing.areaPosition[0][0] = mousePoint.x;
								wowAWTFishing.areaPosition[0][1] = mousePoint.y;
								wowAWTFishing.wowJTextField_f_x
										.setText(wowAWTFishing.areaPosition[0][0]
												+ "");
								wowAWTFishing.wowJTextField_f_y
										.setText(wowAWTFishing.areaPosition[0][1]
												+ "");
								wowAWTFishing.println("左上坐标：【x:"
										+ wowAWTFishing.areaPosition[0][0]
										+ " y:"
										+ wowAWTFishing.areaPosition[0][1]
										+ "】");
								get_size++;
								if (wowAWTFishing.wowAWTFishing.wowJCheckBox_misc
										.isSelected()) {
									wowMisc.play(WOW.url_start, 2000, false);
								}
							} else if (get_size == 1) {
								Point mousePoint = MouseInfo.getPointerInfo()
										.getLocation();
								wowAWTFishing.areaPosition[1][0] = mousePoint.x;
								wowAWTFishing.areaPosition[1][1] = mousePoint.y;
								wowAWTFishing.wowJTextField_s_x
										.setText(wowAWTFishing.areaPosition[1][0]
												+ "");
								wowAWTFishing.wowJTextField_s_y
										.setText(wowAWTFishing.areaPosition[1][1]
												+ "");
								wowAWTFishing.println("右下坐标：【x:"
										+ wowAWTFishing.areaPosition[1][0]
										+ " y:"
										+ wowAWTFishing.areaPosition[1][1]
										+ "】");
								get_size++;
								if (wowAWTFishing.wowJCheckBox_misc
										.isSelected()) {
									wowMisc.play(WOW.url_start, 2000, false);
								}
								stat = 2;
								start = 0;
							} else {
								if (wowAWTFishing.wowJCheckBox_misc
										.isSelected()) {
									wowMisc.play(WOW.url_error, 2000, false);
								}
							}
							wowAWTFishing.setSZenabled(false);
						} else {
							if (wowAWTFishing.wowJCheckBox_misc.isSelected()) {
								wowMisc.play(WOW.url_error, 2000, false);
							}
						}
					} else if (arg0 == 2000) {

						if (stat == 0) {
							wowAWTFishing.areaPosition[0][0] = Integer
									.parseInt(wowAWTFishing.wowJTextField_f_x
											.getText());
							wowAWTFishing.areaPosition[0][1] = Integer
									.parseInt(wowAWTFishing.wowJTextField_f_y
											.getText());

							wowAWTFishing.areaPosition[1][0] = Integer
									.parseInt(wowAWTFishing.wowJTextField_s_x
											.getText());
							wowAWTFishing.areaPosition[1][1] = Integer
									.parseInt(wowAWTFishing.wowJTextField_s_y
											.getText());
							stat = 2;
						}

						if (stat == 2) {

							get_size = 0;
							wowAWTFishing.println("【启动】");
							try {
								wowAWTFishingThread = new WOWAWTFishingThread();
								wowAWTFishingThread.init(wowAWTFishing);
								wowAWTFishingThread.start();
							} catch (Exception gg) {
								wowAWTFishing.println("【启动失败】");
							}
							stat = 3;
							start = 1;
							if (wowAWTFishing.wowJCheckBox_misc.isSelected()) {
								wowMisc.play(WOW.url_start, 2000, false);
							}
							wowAWTFishing.setSZenabled(false);
						} else {
							if (wowAWTFishing.wowJCheckBox_misc.isSelected()) {
								wowMisc.play(WOW.url_error, 2000, false);
							}
						}
					} else if (arg0 == 3000) {
						if (stat == 3 && start == 1) {
							wowAWTFishing.println("【停止】");
							try {
								try {
									wowAWTFishingThread.line.stop();
									wowAWTFishingThread.line.close();
								} catch (Exception ex) {

								}
								try {
									wowAWTFishingThread.wowMisc.stop();
								} catch (Exception ess) {

								}
								wowAWTFishingThread.stop();
							} catch (Exception aa) {
								wowAWTFishing.println("【停止失败】");
							}

							stat = 0;
							start = 0;
							if (wowAWTFishing.wowJCheckBox_misc.isSelected()) {
								wowMisc.play(WOW.url_start, 2000, false);
							}
							wowAWTFishing.setSZenabled(true);
						} else {
							if (wowAWTFishing.wowJCheckBox_misc.isSelected()) {
								wowMisc.play(WOW.url_error, 2000, false);
							}
						}

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		JIntellitype.getInstance().addHotKeyListener(HL);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == wowJButton_save && wowJButton_save.isEnabled()) {
			wowAWTFishing.areaPosition[0][0] = Integer
					.parseInt(wowAWTFishing.wowJTextField_f_x.getText());
			wowAWTFishing.areaPosition[0][1] = Integer
					.parseInt(wowAWTFishing.wowJTextField_f_y.getText());

			wowAWTFishing.areaPosition[1][0] = Integer
					.parseInt(wowAWTFishing.wowJTextField_s_x.getText());
			wowAWTFishing.areaPosition[1][1] = Integer
					.parseInt(wowAWTFishing.wowJTextField_s_y.getText());
			wowAWTFishing.miscSize = Integer
					.parseInt(wowAWTFishing.wowJTextField_misc.getText());
			wowAWTFishing.println("左上坐标：【x:" + wowAWTFishing.areaPosition[0][0]
					+ " y:" + wowAWTFishing.areaPosition[0][1] + "】");
			wowAWTFishing.println("右下坐标：【x:" + wowAWTFishing.areaPosition[1][0]
					+ " y:" + wowAWTFishing.areaPosition[1][1] + "】");
			wowAWTFishing.println("声控值：【" + wowAWTFishing.miscSize + "】");
			setSZenabled(false);
			stat = 2;
			start = 0;
		}
	}

	public void setSZenabled(boolean boo) {
		wowJButton_save.setEnabled(boo);
		wowJTextField_f_x.setEnabled(boo);
		wowJTextField_f_y.setEnabled(boo);

		wowJTextField_s_x.setEnabled(boo);
		wowJTextField_s_y.setEnabled(boo);

		wowJTextField_misc.setEnabled(boo);

	}

	public void windowIconified(WindowEvent e) {

	}

	@SuppressWarnings("static-access")
	public long getDateh() {
		return this.dateh;
	}

	@SuppressWarnings("static-access")
	public void setDateh(long dateh) {
		this.dateh = dateh;
	}

	@SuppressWarnings("static-access")
	public long getDateh_bf() {
		return this.dateh_bf;
	}

	@SuppressWarnings("static-access")
	public void setDateh_bf(long dateh_bf) {
		this.dateh_bf = dateh_bf;
	}

	@SuppressWarnings("static-access")
	public long getDates() {
		return this.dates;
	}

	@SuppressWarnings("static-access")
	public void setDates(long dates) {
		this.dates = dates;
	}

	@SuppressWarnings("static-access")
	public long getDates_bf() {
		return this.dates_bf;
	}

	@SuppressWarnings("static-access")
	public void setDates_bf(long dates_bf) {
		this.dates_bf = dates_bf;
	}

	@SuppressWarnings("static-access")
	public void print(String str) {
		wowJTextArea.append(str);
		wowJTextArea.setSelectionStart(wowJTextArea.getText().length());
	}

	@SuppressWarnings("static-access")
	public void println(String str) {
		wowJTextArea.append(str + "\n");
		wowJTextArea.setSelectionStart(wowJTextArea.getText().length());
	}

	public List getlist(List list) {
		list.add("Maxthon");
		list.add("MSN Message");
		list.add("ACDSee");
		list.add("Mplayerc");
		list.add("Kingplayer");
		list.add("Adobe Reader");
		list.add("QQ");
		list.add("Lingoes");
		list.add("TTPlayer");
		list.add("taskmgr");
		list.add("TXPlatform");
		list.add("Thunder");
		list.add("AliIM");
		list.add("Ipmsg");
		list.add("iexplore");
		list.add("FireFox");
		list.add("FeedDemon");
		list.add("editplus");
		list.add("microsoft office");
		list.add("kmplayer");
		return list;
	}
}
