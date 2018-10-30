package wow.twow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class WOWAWTDeleteFile extends JFrame implements ActionListener,
		WindowListener {

	public static JLabel wowJLabel = new JLabel();

	public static JTextField JTextField1 = new JTextField("PitBull4");
	public static JTextField JTextField2 = new JTextField(
			"PitBull4,PitBull4_HealthBar,PitBull4_SoulShards,PitBull4_VisualHeal");
	public static JButton JButton = new JButton("清理无用插件");
	public static JLabel JLabel = new JLabel("0");

	public WOWAWTDeleteFile() {

		JTextField1.setBounds(10, 5, 400, 25);
		JTextField2.setBounds(10, 40, 400, 25);
		JButton.setBounds(260, 80, 150, 25);
		JLabel.setBounds(240, 80, 30, 25);

		JButton.addActionListener(this);
		this.setSize(430, 150);
		wowJLabel.add(JTextField1);
		wowJLabel.add(JTextField2);
		wowJLabel.add(JButton);
		wowJLabel.add(JLabel);
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
		WOWAWTDeleteFile a = new WOWAWTDeleteFile();
		a.show();
	}

	public void del(String filepath) {
		File f = new File(filepath);
		if (f.exists() && f.isDirectory()) {
			if (f.listFiles().length == 0) {
				f.delete();
			} else {
				File delFile[] = f.listFiles();
				int i = f.listFiles().length;
				for (int j = 0; j < i; j++) {
					if (delFile[j].isDirectory()) {
						del(delFile[j].getAbsolutePath());
					}
					delFile[j].delete();
				}
			}
		} else {
			f.delete();
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == JButton) {
			String dir = JTextField1.getText();
			String dirstr = JTextField2.getText();
			File file = new File("E:/TWOW/Interface/AddOns");
			int i = 0;
			for (String temp : dir.split(",")) {
				for (String str : file.list()) {
					if (str.toLowerCase().indexOf(temp.toLowerCase()) >= 0) {
						int j = 0;
						for (String te : dirstr.split(",")) {
							if (te.toLowerCase().equals(str.toLowerCase())) {
								j++;
							}
						}
						if (j == 0) {
							del("E:/TWOW/Interface/AddOns" + "/" + str);

							i++;
						}
					}
				}
			}

			JLabel.setText(i + "");
		}

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
