import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import jpcap.NetworkInterface;

public class Configure extends JFrame implements ActionListener {

	private JLabel choose_Device = new JLabel("Choose a Network Interface:");

	private JLabel choose_Mode = new JLabel("Choose Work Mode:");

	private JLabel set_Filter = new JLabel("Set Filter:");

	private JComboBox device_Box;

	private JComboBox mode_Box;

	private JTextField filter_Field = new JTextField("Input here");

	private JButton go = new JButton("Go");

	public static NetworkInterface[] devices;

	public String[] device_Names;

	public String[] mode_Names = new String[] { "混杂模式", "优化模式" };

	public CatchPacket catchPacket;

	public void init(CatchPacket cPacket) {

		this.catchPacket = cPacket;

		devices = cPacket.getDevices();

		device_Names = new String[devices.length];

		for (int i = 0; i < devices.length; i++) {

			device_Names[i] = devices[i].description + i;

		}

		device_Box = new JComboBox(device_Names);

		device_Box.setSelectedIndex(device_Names.length - 1);

		mode_Box = new JComboBox(mode_Names);

		mode_Box.setSelectedIndex(0);

		// 布局

		setLayout(null);

		choose_Device.setBounds(50, 20, 250, 20);

		add(choose_Device);

		device_Box.setBounds(300, 20, 250, 20);

		add(device_Box);

		choose_Mode.setBounds(50, 50, 250, 20);

		add(choose_Mode);

		mode_Box.setBounds(300, 50, 250, 20);

		add(mode_Box);

		set_Filter.setBounds(50, 80, 250, 20);

		add(set_Filter);

		filter_Field.setBounds(300, 80, 250, 20);

		add(filter_Field);

		go.setBounds(500, 110, 50, 20);

		add(go);

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setTitle("Configure");

		setSize(600, 180);

		setResizable(false);

		setVisible(true);

		// device_Box.addItemListener(this);

		// mode_Box.addItemListener(this);

		// filter_Field.addActionListener(this);

		go.addActionListener(this);

	}

	public void actionPerformed(ActionEvent e) {

		String device_Item = (String) device_Box.getSelectedItem();

		String mode_Item = (String) mode_Box.getSelectedItem();

		Boolean is_Mix = true;

		for (int i = 0; i < devices.length; i++) {

			if (device_Item.endsWith(i + "")) {

				catchPacket.chooseDevice = devices[i];

				break;

			}

		}

		if (mode_Item.equals("混杂模式")) {

			is_Mix = true;

		} else

			is_Mix = false;

		catchPacket.chooseMode = is_Mix;

		catchPacket.inputFilter = filter_Field.getText();

		this.setVisible(false);

		/*
		 * if(e.getSource()==device_Box){
		 * 
		 * device_Box.getSelectedIndex();
		 * 
		 * 
		 *  }
		 * 
		 * if(e.getSource()==filter_Field){
		 * 
		 * String msg = filter_Field.getText();
		 * 
		 * msg = msg.trim();
		 * 
		 * if(msg!=null || !msg.equals("")||!msg.equals("Input here")){
		 * 
		 * catchPacket.inputFilter = msg;
		 * 
		 * sets[2] = true; //表示过滤器已设
		 * 
		 * this.setVisible(false);
		 *  }
		 *  }
		 */

	}

	public static void main(String[] args) {

		// new Configure();

	}

}
