import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import jpcap.JpcapCaptor;
import jpcap.JpcapWriter;
import jpcap.NetworkInterface;
import jpcap.PacketReceiver;
import jpcap.packet.ARPPacket;
import jpcap.packet.DatalinkPacket;
import jpcap.packet.EthernetPacket;
import jpcap.packet.ICMPPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;

public class CatchPacket implements PacketReceiver, Runnable {

	public NetworkInterface[] devices;

	public NetworkInterface device;

	public JpcapCaptor jCaptor;

	public JpcapWriter writer;

	public LinkedList<Packet> packets = new LinkedList();

	public static NetworkInterface chooseDevice;

	public static boolean chooseMode;

	public static String inputFilter;

	public static StringBuffer sb = new StringBuffer();

	// 获得网络接口list

	public NetworkInterface[] getDevices() {

		devices = JpcapCaptor.getDeviceList();

		return devices;

	}

	// 获得网卡接口list描述

	public void desNetworkInterface() {

		sb
				.append("************************网卡信息****************************************\n");

		sb.append("总共有 " + devices.length + " 个网络设备接口\n");

		for (int i = 0; i < devices.length; i++) {

			sb.append("\n设备接口" + (i + 1) + ":\n");

			sb.append("网络接口名称:" + devices[i].name + "\n");

			// 选中一个网卡接口进行监听

			if (!(devices[i].name.contains("GenericDialupAdapter"))) {

				device = devices[i];

			}

			sb.append("网络接口描述:" + devices[i].description + "\n");

			sb.append("数据链路层名称:" + devices[i].datalink_name + "\n");

			sb.append("数据链路层描述:" + devices[i].datalink_description + "\n");

			sb.append("是否是LOOPBACK设备:" + devices[i].loopback + "\n");

			sb.append("MAC地址:");

			int flag = 0;

			for (byte b : devices[i].mac_address) {

				flag++;

				if (flag < devices[i].mac_address.length) {

					sb.append(Integer.toHexString(b & 0xff) + ":");

				} else

					sb.append(Integer.toHexString(b & 0xff) + "\n");

			}

		}

		sb
				.append("**********************************************************************\n");

		// System.out.println(sb);

	}

	// 获得某个网卡接口的连接

	public void getCap(NetworkInterface nInterface, boolean mixMode,
			String filter) {

		try {

			jCaptor = JpcapCaptor.openDevice(nInterface, 2048, mixMode, 5000);

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	// 设置过滤器

	public void setFilter(String filter) {

		try {

			jCaptor.setFilter(filter, true);

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	// 设置抓包模式开始抓包

	public void beginCatch() {

		jCaptor.processPacket(1, this);

	}

	// 结束抓包

	public void endCatch() {

		if (jCaptor != null) {

			jCaptor.breakLoop();

		}

	}

	// 保存捕获包于文件中

	public void saveFile(String fileName) {

		if (jCaptor == null) {

			JOptionPane.showMessageDialog(null, "No Packet yet!", "NO-PACKETS",
					JOptionPane.INFORMATION_MESSAGE);

		} else {

			try {

				writer = JpcapWriter.openDumpFile(jCaptor, fileName);

				while (packets.size() != 0) {

					writer.writePacket(packets.removeFirst());

				}

				writer.close();

			} catch (IOException e) {

				// TODO Auto-generated catch block

				e.printStackTrace();

			}

		}

	}

	// 打开存于文件中的包

	public void openFile(String fileName) {

		try {

			jCaptor = JpcapCaptor.openFile(fileName);

			while (true) {

				Packet packet = jCaptor.getPacket();

				if (packet == null) {

					break;

				}

				analysis(packet);

			}

		} catch (IOException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		} finally {

			if (jCaptor != null) {

				jCaptor.close();

			}

		}

	}

	public void analysis(Packet packet) {

		sb.append("------包分析-------\n");

		sb.append("Captured Length:" + packet.caplen + " byte\n");

		sb.append("Length of this Packet:" + packet.len + " byte\n");

		sb.append("Header:" + packet.header + "\n");

		sb.append("Length of Header:" + packet.header.length + " byte\n");

		sb.append("Data:" + packet.data + "\n");

		sb.append("Length of Data:" + packet.data.length + " byte\n");

		sb.append("---Ethernet头部信息---\n");

		DatalinkPacket dPacket = packet.datalink;

		if (dPacket instanceof EthernetPacket) { // 分析以太网帧

			EthernetPacket ePacket = (EthernetPacket) dPacket;

			sb.append("src_mac:");

			int flag1 = 0;

			for (byte b : ePacket.src_mac) {

				flag1++;

				if (flag1 < ePacket.src_mac.length) {

					sb.append(Integer.toHexString(b & 0xff) + ":");

				} else

					sb.append(Integer.toHexString(b & 0xff) + "\n");

			}

			sb.append("dst_mac:");

			int flag2 = 0;

			for (byte b : ePacket.dst_mac) {

				flag2++;

				if (flag2 < ePacket.dst_mac.length) {

					sb.append(Integer.toHexString(b & 0xff) + ":");

				} else

					sb.append(Integer.toHexString(b & 0xff) + "\n");

			}

			sb.append("frametype:"
					+ Integer.toHexString(ePacket.frametype & 0xffff) + "\n");

			sb.append("------------------\n");

		} else {

			sb.append(dPacket + "\n");

			sb.append("------------------\n");

		}

		if (packet instanceof ARPPacket) { // 分析ARP协议

			sb.append("---ARP---\n");

			ARPPacket aPacket = (ARPPacket) packet;

			sb.append("硬件类型：" + aPacket.hardtype + "\n");

			sb.append("协议类型：" + aPacket.prototype + "\n");

			sb.append("硬件地址长度：" + aPacket.hlen + "\n");

			sb.append("协议地址长度：" + aPacket.plen + "\n");

			sb.append("Operation：" + aPacket.operation + "\n");

			sb.append("发送者硬件地址：" + new String(aPacket.sender_hardaddr )+ "\n");

			sb.append("发送者协议地址：" + new String(aPacket.sender_protoaddr )+ "\n");

			sb.append("目标硬件地址：" + new String(aPacket.target_hardaddr) + "\n");

			sb.append("目标协议地址：" + new String(aPacket.target_protoaddr) + "\n");

			sb.append("------------------\n");

		}

		if (packet instanceof ICMPPacket) { // 分析ICMP协议

			sb.append("---ICMP---\n");

			ICMPPacket iPacket = (ICMPPacket) packet;

			sb.append("ICMP_TYPE:" + iPacket.type + "\n");

			sb.append("由于ICMP格式种类繁多，故省去不分析\n");

			sb.append("------------------\n");

		}

//		if (packet instanceof IPPacket) { // 分析IP

			IPPacket iPacket = (IPPacket) packet;

			sb.append("---IP版本: " + iPacket.version + " ---\n");

			if (iPacket.version == 4) { // 分析IPv4协议

				sb.append("Type of service:" + iPacket.rsv_tos + "\n");

				sb.append("Priprity:" + iPacket.priority + "\n");

				sb.append("Packet Length:" + iPacket.length + "\n");

				sb.append("Identification:" + iPacket.ident + "\n");

				sb.append("Don't Frag? " + iPacket.dont_frag + "\n");

				sb.append("More Frag? " + iPacket.more_frag + "\n");

				sb.append("Frag Offset:" + iPacket.offset + "\n");

				sb.append("Time to Live:" + iPacket.hop_limit + "\n");

				sb.append("Protocol:" + iPacket.protocol
						+ "        (TCP = 6; UDP = 17)\n");

				sb.append("Source address:" + iPacket.src_ip.toString() + "\n");

				sb.append("Destination address:" + iPacket.dst_ip.toString()
						+ "\n");

				sb.append("Options:" + iPacket.option + "\n");

				sb.append("------------------\n");

			}

			if (iPacket instanceof UDPPacket) { // 分析UDP协议

				sb.append("---UDP---\n");

				UDPPacket uPacket = (UDPPacket) iPacket;

				sb.append("Source Port:" + uPacket.src_port + "\n");

				sb.append("Destination Port:" + uPacket.dst_port + "\n");

				sb.append("Length:" + uPacket.length + "\n");

				sb.append("------------------\n");

//				if (uPacket.src_port == 53 || uPacket.dst_port == 53) { // 分析DNS协议

//					sb.append("---DNS---\n");

					sb.append("数据："+new String(uPacket.data)+"\n");

//					sb.append("------------------\n");
//
//				}

			}

			if (iPacket instanceof TCPPacket) { // 分析TCP协议

				sb.append("---TCP---\n");

				TCPPacket tPacket = (TCPPacket) iPacket;

				sb.append("Source Port:" + tPacket.src_port + "\n");

				sb.append("Destination Port:" + tPacket.dst_port + "\n");

				sb.append("Sequence Number:" + tPacket.sequence + "\n");

				sb.append("Acknowledge Number:" + tPacket.ack_num + "\n");

				sb.append("URG:" + tPacket.urg + "\n");

				sb.append("ACK:" + tPacket.ack + "\n");

				sb.append("PSH:" + tPacket.psh + "\n");

				sb.append("RST:" + tPacket.rst + "\n");

				sb.append("SYN:" + tPacket.syn + "\n");

				sb.append("FIN:" + tPacket.fin + "\n");

				sb.append("Window Size:" + tPacket.window + "\n");

				sb.append("Urgent Pointer:" + tPacket.urgent_pointer + "\n");

				sb.append("Option:" + tPacket.option + "\n");

				sb.append("------------------\n");

//				if (tPacket.src_port == 80 || tPacket.dst_port == 80) { // 分析HTTP协议

//					sb.append("---HTTP---\n");

					byte[] data = tPacket.data;

					if (data.length == 0) {

						sb.append("此为不带数据的应答报文！\n");

					} else {
						System.out.println("打印："+data);
//						if (tPacket.src_port == 80) { // 接受HTTP回应

							String str = null;

							try {

								String str1 = new String(data, "UTF-8");

								if (str1.contains("HTTP/1.1")) {

									str = str1;

								} else {

									String str2 = new String(data, "GB2312");
//
////									if (str2.contains("HTTP/1.1")) {
////
////										str = str2;
////
////									} else {

										String str3 = new String(data, "GBK");

										if (str3.contains("HTTP/1.1")) {

											str = str3;

										} else {

											str = new String(data, "Unicode");

										}

									}
//
//								}

								sb.append(str + "\n");

							} catch (UnsupportedEncodingException e) {

								// TODO Auto-generated catch block

								e.printStackTrace();

							}

//						}

//						if (tPacket.dst_port == 80) {

							try {

								String str5 = new String(data, "ASCII");

								sb.append("data:"+str5);

							} catch (Exception e) {

								// TODO: handle exception

							}

//						}

					}

//				}

//			}

			sb.append("\n");

			System.out.println(sb);

		}

	}

	public void receivePacket(Packet packet) {

		packets.add(packet);

		analysis(packet);

	}

	public static void main(String[] args) {

		CatchPacket cPacket = new CatchPacket();

		cPacket.devices = cPacket.getDevices();

		cPacket.desNetworkInterface();

		cPacket.getCap(cPacket.device, true, "");

		Thread thread = new Thread(cPacket);

		sb.delete(0, sb.length());

		thread.start();

	}

	public void run() {

		while (true) {

			try {

				Thread.sleep(5000);

				beginCatch();

			} catch (InterruptedException e) {

				e.printStackTrace();

			}

		}

	}

}
