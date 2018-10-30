package sound;

import java.awt.Component;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.media.CannotRealizeException;
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.EndOfMediaEvent;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoDataSourceException;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.media.RealizeCompleteEvent;
import javax.media.Time;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class SimpPlayer extends JFrame implements ControllerListener {
	private static final long serialVersionUID = 1L;
	private static JPanel jContentPane = null;
	private JPanel jPanel = null;

	private JMenuBar jmenuBar = null;
	private JMenu jMenu = null;
	private JMenuItem jMenuItem = null;
	private JMenuItem jMenuItem1 = null;
	private JMenu jMenu1 = null;
	private static Component jPanel2 = null;
	private static Component jPanel3 = null;
	private JFileChooser jFileChooser = new JFileChooser("e://");

	// 存放文件的地址
	static List<String> list = new ArrayList(); // @jve:decl-index=0:
	// list的索引
	static int i;
	private static File file = null;
	// 定义数据源对象
	private javax.media.protocol.DataSource ds; // @jve:decl-index=0:
	// 定义Player
	private static Player audioPlayer = null;

	public SimpPlayer() {
		super("s");
		initialize();
	}
	public static void main(String []aa){
		SimpPlayer sp=new SimpPlayer();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(600, 400);
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
		this.setJMenuBar(getJmenuBar());

	}

	private JMenuBar getJmenuBar() {
		if (jmenuBar == null) {
			jmenuBar = new JMenuBar();
			jmenuBar.add(getJmenu());
		}
		return jmenuBar;
	}

	private JMenu getJmenu() {
		// TODO Auto-generated method stub
		jMenu = new JMenu();
		jMenu.setText("文件");
		jMenu.add(getmenuItem());
		jMenu.add(getmenuItem1());

		return jMenu;
	}

	private JMenuItem getmenuItem1() {
		if (jMenuItem1 == null) {
			jMenuItem1 = new JMenuItem();
			jMenuItem1.setText("文件夹");
		}
		return jMenuItem1;
	}

	private JMenuItem getmenuItem() {
		if (jMenuItem == null) {
			jMenuItem = new JMenuItem();
			jMenuItem.setText("文件");
			jMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					int result = jFileChooser.showOpenDialog(jPanel);
					if (result == JFileChooser.APPROVE_OPTION)
						;
					{
						System.out.println("================");

						file = jFileChooser.getSelectedFile();
						System.out.println("================");
						// 因为只允许同时出现一个player，所以播放前必须判断player是否初始化。
						if (audioPlayer != null) {
							if (jPanel2 != null) {
								// 销毁视频面板
								jContentPane.remove(jPanel2);
							}
							if (jPanel3 != null) {
								// 销毁控制面板
								jContentPane.remove(jPanel3);
							}
							// 销毁PLAYER
							audioPlayer.close();
							list.clear();
						}

						try {
							try {
								// 调用player
								open();
							} catch (NoDataSourceException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						} catch (NoPlayerException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (CannotRealizeException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

				}
			});
		}
		return jMenuItem;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
		}
		return jContentPane;
	}

	public void open() throws NoPlayerException, CannotRealizeException, IOException, NoDataSourceException {
		list.add(file.getAbsolutePath());
		System.out.println(file.getAbsolutePath());
		// MediaLocator加载多媒体文件
		MediaLocator loc = new MediaLocator("file:" + list.get(i));
		// 创建多媒体文件
		ds = Manager.createDataSource(loc);
		// 初始化player；
		audioPlayer = Manager.createPlayer(ds);
		// 为player添加监听器。。参数为ControllerListener对象，此处用this，所以本类实现了ControllerListener接口
		// 此接口只有一个方法controllerUpdate（），实现了此接口就必须实现controllerUpdate（）
		// 监听器作用：判断player是否有视频面板，控制面板，及播放状态。
		audioPlayer.addControllerListener(this);
		// 设置player在realie状态
		audioPlayer.realize();

		audioPlayer.start();

	}

	public synchronized void controllerUpdate(ControllerEvent event) {
		// 播放状态，判断文件是否比方完毕
		if (event instanceof EndOfMediaEvent) {
			// 设置player播放位置，
			audioPlayer.setMediaTime(new Time(0));
			audioPlayer.start();
		}

		// 判断文件是否处于realize状态，此状态为player检查文件是什么类型的文件。媒体的需求以及媒体播放类型的资料。
		if (event instanceof RealizeCompleteEvent) {
			// System.out.println(event.getClass());

			// 创建视频面板
			jPanel2 = audioPlayer.getVisualComponent();

			// System.out.println(jPanel2);
			if (jPanel2 != null) {
				System.out.println("**********************");
				jPanel2.setBounds(new Rectangle(10, 0, 400, 290));
				jContentPane.add(jPanel2);
				jContentPane.repaint();
			}
			// 创建控制面板
			jPanel3 = audioPlayer.getControlPanelComponent();

			// System.out.println(jPanel3);
			if (jPanel3 != null) {
				System.out.println("asasasa");

				jPanel3.setBounds(new Rectangle(10, 300, 400, 26));

				jContentPane.add(jPanel3);

				jContentPane.repaint();
			}
		}

	}
}