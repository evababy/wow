import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MainFrame extends JFrame implements ActionListener,
		WindowListener, Runnable {

	private static final long serialVersionUID = 1L;

	private JMenuBar mBar = new JMenuBar();

	private JMenu M_file = new JMenu("File");

	private JMenu M_capture = new JMenu("Capture");

	private JMenu M_tool = new JMenu("Tool");

	private JMenu M_help = new JMenu("Help");

	private JMenuItem F_open = new JMenuItem("Open File");

	private JMenuItem F_save = new JMenuItem("Save File");

	private JMenuItem F_exit = new JMenuItem("Exit");

	private JMenuItem C_show = new JMenuItem("Network Card information");

	private JMenuItem C_Configure = new JMenuItem("Configure");

	private JMenuItem C_start = new JMenuItem("Start");

	private JMenuItem C_stop = new JMenuItem("Stop");

	private JMenuItem T_wireshake = new JMenuItem("Wireshake");

	private JMenuItem T_factory = new JMenuItem("Check network card factory");

	private JMenuItem H_api = new JMenuItem("API for JPCAP");

	private JMenuItem H_author = new JMenuItem("About Author");

	public JPanel mainPanel1;

	public JScrollPane mainPanel2;

	public Container con;

	public JTextArea area = new JTextArea();

	public CatchPacket cPacket;

	Thread thread = new Thread(cPacket);

	public Configure configure;

	public static boolean isWorking = false;



	// 布局页面

	public void init() {

		// file菜单

		M_file.add(F_open);

		M_file.add(F_save);

		M_file.add(F_exit);

		// capture菜单

		M_capture.add(C_show);

		M_capture.add(C_Configure);

		M_capture.add(C_start);

		M_capture.add(C_stop);

		// tool菜单

		M_tool.add(T_wireshake);

		M_tool.add(T_factory);

		// help菜单

		M_help.add(H_api);

		M_help.add(H_author);

		// 组装菜单

		mBar.add(M_file);

		mBar.add(M_capture);

		mBar.add(M_tool);

		mBar.add(M_help);

		setJMenuBar(mBar);

		setLayout(null);

		mainPanel1 = welPanel();

		mainPanel2 = workPanel();

		mainPanel1.setBounds(0, 0, 995, 445);

		mainPanel2.setBounds(0, 0, 995, 445);

		add(mainPanel1);

		add(mainPanel2);

		mainPanel1.setVisible(!isWorking);

		mainPanel2.setVisible(isWorking);

		setTitle("PacketCatcher-WWC(World-Wide-Catch)");

		setSize(1000, 500);

		setResizable(false);

		setVisible(true);

		F_open.addActionListener(this);

		F_save.addActionListener(this);

		F_exit.addActionListener(this);

		C_show.addActionListener(this);

		C_Configure.addActionListener(this);

		C_start.addActionListener(this);

		C_stop.addActionListener(this);

		T_wireshake.addActionListener(this);

		T_factory.addActionListener(this);

		H_api.addActionListener(this);

		H_author.addActionListener(this);

		this.addWindowListener(this);

	}

//	// 设置托盘
//
//	public void setTray() {
//
//		sTray = SystemTray.getSystemTray();
//
//		ImageIcon icon = new ImageIcon(".\\.\\.\\images\\tray.jpg");
//
//		PopupMenu menu = new PopupMenu();
//
//		MenuItem show = new MenuItem("显示窗体");
//
//		MenuItem exit = new MenuItem("退出窗体");
//
//		tIcon = new TrayIcon(icon.getImage(), "World-Wide-Catch", menu);
//
//		tIcon.addMouseListener(new MouseAdapter() {
//
//			public void mouseClicked(MouseEvent e) {
//
//				if (e.getClickCount() == 2) {// 鼠标双击
//
//					sTray.remove(tIcon);
//
//					setVisible(true);
//
//					// 设置窗口全屏
//
//					// setExtendedState(JFrame.MAXIMIZED_BOTH);
//
//				}
//
//			}
//
//		});

//	}

	// 初始化界面

	public MainFrame() {

		// 初始化一个CacthPacket对象，以后都是在操作此对象

		cPacket = new CatchPacket();

		con = this.getContentPane();

		init();

//		setTray();

	}

	// 添加welcome界面

	public JPanel welPanel() {

		JPanel jPanel = new JPanel();

		JLabel label = new JLabel();

		ImageIcon icon = new ImageIcon(".\\.\\.\\images\\final.jpg");

		label.setIcon(icon);

		jPanel.add(label);

		return jPanel;

	}

	// 运行时界面

	public JScrollPane workPanel() {

		area.setBackground(Color.yellow);

		area.setText(CatchPacket.sb.toString());

		area.setEditable(false);

		JScrollPane sPane = new JScrollPane(area);

		sPane.setSize(800, 350);

		sPane.setVisible(isWorking);

		return sPane;

	}

	// 菜单栏listener

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == F_open) {

			if (isWorking == false) {

				isWorking = true;

			}

			JFileChooser chooser = new JFileChooser();

			int returnType = chooser.showOpenDialog(null);

			if (returnType == JFileChooser.APPROVE_OPTION) {

				File file = chooser.getSelectedFile();

				String fileName = file.getAbsolutePath();

				CatchPacket.sb.delete(0, CatchPacket.sb.length());

				cPacket.openFile(fileName);

			}

		} else if (e.getSource() == F_save) {

			JFileChooser chooser = new JFileChooser();

			int returnType = chooser.showSaveDialog(null);

			if (returnType == JFileChooser.APPROVE_OPTION) {

				File file = chooser.getSelectedFile();

				String fileName = file.getAbsolutePath();

				cPacket.saveFile(fileName);

			}

		} else if (e.getSource() == F_exit) {

			int show = JOptionPane.showConfirmDialog(null, "是否关闭?", "确认关闭系统",

			JOptionPane.YES_NO_OPTION);

			if (show == JOptionPane.YES_OPTION) {

				System.exit(0);

			} else {

				this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

			}

		} else if (e.getSource() == C_show) {

			isWorking = true;

			cPacket.devices = cPacket.getDevices();

			CatchPacket.sb.delete(0, CatchPacket.sb.length());

			cPacket.desNetworkInterface();

			area.setText(CatchPacket.sb.toString());

		} else if (e.getSource() == C_Configure) {

			configure = new Configure();

			configure.init(cPacket);

		} else if (e.getSource() == C_start) {

			if (configure == null) {

				JOptionPane.showMessageDialog(null, "Configure first!",
						"INFORMATION", JOptionPane.INFORMATION_MESSAGE);

			} else {

				isWorking = true;

				cPacket.getCap(CatchPacket.chooseDevice,
						CatchPacket.chooseMode, "");

				thread = new Thread(cPacket);

				CatchPacket.sb.delete(0, CatchPacket.sb.length());

				thread.start();

			}

		}

		else if (e.getSource() == C_stop) {

			if (thread.isAlive()) {

				thread.stop();

			}

		}

		else if (e.getSource() == T_wireshake) {

			String cmd = "rundll32 url.dll,FileProtocolHandler C:\\Program Files\\Wireshark\\wireshark.exe";

			try {

				Runtime.getRuntime().exec(cmd);

			} catch (IOException e1) {

				e1.printStackTrace();

			}

		} else if (e.getSource() == T_factory) {

			String cmd = "rundll32 url.dll,FileProtocolHandler http://standards.ieee.org/regauth/oui/index.shtml";

			try {

				Runtime.getRuntime().exec(cmd);

			} catch (IOException e1) {

				e1.printStackTrace();

			}

		} else if (e.getSource() == H_api) {

			String cmd = "rundll32 url.dll,FileProtocolHandler E:\\doc\\javadoc\\index.html";

			try {

				Runtime.getRuntime().exec(cmd);

			} catch (IOException e1) {

				e1.printStackTrace();

			}

		} else if (e.getSource() == H_author) {

			// 查看空间

			String cmd = "rundll32 url.dll,FileProtocolHandler http://user.qzone.qq.com/627443660?ADUIN=627443660&ADSESSION=1277106345&ADTAG=CLIENT.QQ.2881_MyTip.0&ptlang=2052";

			try {

				Runtime.getRuntime().exec(cmd);

			} catch (IOException e1) {

				e1.printStackTrace();

			}

		}

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

		int show = JOptionPane.showConfirmDialog(null, "是否关闭?", "确认关闭系统",

		JOptionPane.YES_NO_OPTION);

		if (show == JOptionPane.YES_OPTION) {

			System.exit(0);

		} else {

			this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		}

	}

//	// 最小化
//
//	public void windowIconified(WindowEvent arg0) {
//
//		if (getState() == Frame.ICONIFIED) {
//
//			try {
//
//				sTray = SystemTray.getSystemTray();
//
//				sTray.add(tIcon);
//
//			} catch (AWTException e1) {
//
//				e1.printStackTrace();
//
//			}
//
//			setVisible(false);
//
//		}
//
//	}

	public static void main(String[] args) {

//		try {
//
//			UIManager.setLookAndFeel(new SubstanceLookAndFeel());
//
//		} catch (Exception e) {
//
//		}

		MainFrame mFrame = new MainFrame();

		Thread mThread = new Thread(mFrame);

		mThread.setPriority(Thread.MAX_PRIORITY);

		mThread.start();

	}

	public void run() {

		while (true) {

			try {

				Thread.sleep(800);

				this.mainPanel1.setVisible(!isWorking);

				this.mainPanel2.setVisible(isWorking);

				area.setText(CatchPacket.sb.toString());

				con.validate();

				con.repaint();

			} catch (InterruptedException e) {

				e.printStackTrace();

			}

		}

	}

	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}
