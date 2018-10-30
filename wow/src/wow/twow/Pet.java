package wow.twow;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;

import wow.twow.support.WOW;

public class Pet extends JFrame implements ActionListener, WindowListener {

	public static JLabel wowJLabel = new JLabel();
	public static JLabel wowJLabel_state = new JLabel("停止");
	public static JLabel wowJLabel_help = new JLabel("end");

	public Pet() {
		// JIntellitype.getInstance().registerHotKey(2000,
		// JIntellitype.MOD_ALT,(int) 'E');
		// JIntellitype.getInstance().registerHotKey(3000,
		// JIntellitype.MOD_ALT,(int) 'Q');

		JIntellitype.getInstance().registerHotKey(1000, "END");
		JIntellitype.getInstance().registerHotKey(2000, JIntellitype.MOD_ALT,
				(int) 'W');

		wowJLabel_state.setBounds(80, 80, 100, 25);
		wowJLabel_help.setBounds(10, 145, 180, 25);

		this.setSize(200, 200);
		wowJLabel.add(wowJLabel_state);
		wowJLabel.add(wowJLabel_help);

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
		Pet a = new Pet();
		a.show();
		HotkeyListener HL = new HotkeyListener() {
			int stat = 0;
			PetThread petThread = new PetThread();

			@SuppressWarnings( { "static-access", "deprecation" })
			public void onHotKey(int arg0) {
				WOWMisc wowMisc = new WOWMisc();
				try {
					if (arg0 == 1000) {// +
						if (stat == 0) {
							stat = 1;
							wowJLabel_state.setText("开始");
							wowMisc.play(WOW.url_start, 5000, false);
							petThread.start();
						} else if (stat == 1) {
							stat = 0;
							wowJLabel_state.setText("停止");
							wowMisc.play(WOW.url_error, 5000, false);
							try {
								petThread.stop();
							} catch (Exception e) {
							}
						}

					} else if (arg0 == 2000) {
						wowMisc.play(WOW.url_start, 5000, false);
						petThread = new PetThread();

						Point mousePoint = MouseInfo.getPointerInfo()
								.getLocation();
						petThread.x1 = mousePoint.x;
						petThread.y1 = mousePoint.y;
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
