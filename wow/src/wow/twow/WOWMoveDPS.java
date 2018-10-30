package wow.twow;

import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;

import wow.twow.support.WOW;

public class WOWMoveDPS extends JFrame implements ActionListener,
		WindowListener {

	public static JLabel wowJLabel = new JLabel();
	public static JLabel wowJLabel_state = new JLabel("停止");
	public static JLabel wowJLabel_help = new JLabel("end（7）    home（FQ）");
	
	public static ButtonGroup ButtonGroup = new ButtonGroup();
	public static JRadioButton JRadioButton1 = new JRadioButton("十字军");
	public static JRadioButton JRadioButton2 = new JRadioButton("锤子");

	public WOWMoveDPS() {
		// JIntellitype.getInstance().registerHotKey(2000,
		// JIntellitype.MOD_ALT,(int) 'E');
		// JIntellitype.getInstance().registerHotKey(3000,
		// JIntellitype.MOD_ALT,(int) 'Q');

		JIntellitype.getInstance().registerHotKey(1000, "END");
		JIntellitype.getInstance().registerHotKey(2000, "HOME");


		wowJLabel_state.setBounds(80, 80, 100, 25);
		wowJLabel_help.setBounds(10, 145, 180, 25);

		ButtonGroup.add(JRadioButton1);
		ButtonGroup.add(JRadioButton2);
		JRadioButton1.setBounds(10,20,80,25);
		JRadioButton2.setBounds(90,20,80,25);
		JRadioButton1.setSelected(true);
		this.setSize(200, 200);
		wowJLabel.add(wowJLabel_state);
		wowJLabel.add(wowJLabel_help);
		wowJLabel.add(JRadioButton1);
		wowJLabel.add(JRadioButton2);
		
		this.add(wowJLabel);
		this.addWindowListener(this);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @comments :
	 * @param args
	 * @author : chenyanji
	 * @date : Mar 9, 2011 4:05:45 PM
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WOWMoveDPS a = new WOWMoveDPS();
		a.show();
		HotkeyListener HL = new HotkeyListener() {
			int stat = 0;
			Date date = new Date();
			WOWMoveDPSThread wowMoveDPSThread = new WOWMoveDPSThread();
			WOWStaticDPSThread wowStaticDPSThread = new WOWStaticDPSThread();

			@SuppressWarnings( { "static-access", "deprecation" })
			public void onHotKey(int arg0) {
				WOWMisc wowMisc = new WOWMisc();
				try {
					Robot robot = new Robot();
					if (arg0 == 1000) {// +
						if (stat == 0 || stat == 2) {
							stat = 1;
							wowJLabel_state.setText("《 7 》");
							wowMisc.play(WOW.url_start, 5000, false);
							try {
								wowMoveDPSThread.stop();
							} catch (Exception e) {
							}
							wowStaticDPSThread = new WOWStaticDPSThread();
							wowStaticDPSThread.start();
						} else if (stat == 1) {
							stat = 0;
							wowJLabel_state.setText("停止");
							wowMisc.play(WOW.url_error, 5000, false);
							try {
								wowStaticDPSThread.stop();
							} catch (Exception e) {
							}
						}

					} else if (arg0 == 2000) {// -
						if (stat == 0 || stat == 1) {
							stat = 2;
							wowJLabel_state.setText("《 FQ 》");
							wowMisc.play(WOW.url_start, 5000, false);
							try {
								wowStaticDPSThread.stop();
							} catch (Exception e) {
							}
							wowMoveDPSThread = new WOWMoveDPSThread();
							wowMoveDPSThread.start();
						} else if (stat == 2) {
							stat = 0;
							wowJLabel_state.setText("停止");
							wowMisc.play(WOW.url_error, 5000, false);
							try {
								wowMoveDPSThread.stop();
							} catch (Exception e) {
							}
						}
					} else {
						wowMisc.play(WOW.url_error, 5000, false);
					}
				} catch (Exception e) {

				}
			}
		};
		JIntellitype.getInstance().addHotKeyListener(HL);
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

}
