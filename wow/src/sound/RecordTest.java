package sound;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Vector;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;

import sun.audio.AudioPlayer;

/**
 * @see java录音程序
 * 
 * 
 */
public class RecordTest extends JPanel implements ActionListener {
	final int bufSize = 16384;
	Capture capture = new Capture();
	static AudioInputStream audioInputStream;
	JButton captB;
	String fileName = "untitled";
	String errStr;
	double duration, seconds;
	File file;
	Vector lines = new Vector();

	public RecordTest() {
		setLayout(new BorderLayout());
		EmptyBorder eb = new EmptyBorder(5, 5, 5, 5);
		SoftBevelBorder sbb = new SoftBevelBorder(SoftBevelBorder.LOWERED);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		JPanel p1 = new JPanel();
		p1.setBorder(sbb);
		p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBorder(new EmptyBorder(10, 0, 5, 0));
		captB = addButton("Record", buttonsPanel, true);
		p1.add(buttonsPanel);
		add(p1);
	}

	public void open() {
	}

	public void close() {
		if (capture.thread != null)
			captB.doClick(0);
	}

	private JButton addButton(String name, JPanel p, boolean state) {
		JButton b = new JButton(name);
		b.addActionListener(this);
		b.setEnabled(state);
		p.add(b);
		return b;
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj.equals(captB)) {
			if (captB.getText().startsWith("Record")) {
				file = null;
				capture.start();
				fileName = "untitled";
				captB.setText("Stop");
			} else {
				lines.removeAllElements();
				capture.stop();
				captB.setText("Record");

			}
		}
	}

	public void saveToFile(AudioFileFormat.Type fileType) {
		if (audioInputStream == null) {
			reportStatus("No loaded audio to save");
			return;
		}
		// reset to the beginnning of the captured data

		// File file = new File(fileName = name);
		try {
			// if (AudioSystem.write(audioInputStream, fileType, file) == -1) {
			// throw new IOException("Problems writing to file");
			// }
			AudioPlayer.player.start(audioInputStream);
			Thread.currentThread().sleep(12000);
			audioInputStream.close();
		} catch (Exception ex) {
			reportStatus(ex.toString());
		}
	}

	private void reportStatus(String msg) {
		if ((errStr = msg) != null) {
			System.out.println(errStr);
		}
	}

	class Capture implements Runnable {
		TargetDataLine line;
		Thread thread;

		public void start() {
			errStr = null;
			thread = new Thread(this);
			thread.setName("Capture");
			thread.start();
		}

		public void stop() {
			thread = null;
		}

		private void shutDown(String message) {
			if ((errStr = message) != null && thread != null) {
				thread = null;
			}
		}

		public void run() {
			duration = 0;
			audioInputStream = null;
			AudioFormat.Encoding encoding = AudioFormat.Encoding.PCM_SIGNED;
			float rate = 8000f;
			int sampleSize = 8;
			boolean bigEndian = true;
			int channels = 2;
			AudioFormat format = new AudioFormat(encoding, rate, sampleSize,
					channels, (sampleSize / 8) * channels, rate, bigEndian);
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
			if (!AudioSystem.isLineSupported(info)) {
				shutDown("Line matching " + info + " not supported.");
				return;
			}
			System.out.println(info);
			try {
				line = (TargetDataLine) AudioSystem.getLine(info);
				line.open(format, line.getBufferSize());
			} catch (Exception ex) {
				shutDown(ex.toString());
				return;
			}
			// play back the captured audio data
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ByteArrayOutputStream out1 = new ByteArrayOutputStream();
			int frameSizeInBytes = format.getFrameSize();
			int bufferLengthInFrames = line.getBufferSize() / 8;
			int bufferLengthInBytes = bufferLengthInFrames * frameSizeInBytes;
			byte[] data = new byte[bufferLengthInBytes];
			int numBytesRead;
			line.start();
			while (thread != null) {
				Date date = new Date();
				while (true) {
					if ((numBytesRead = line.read(data, 0, bufferLengthInBytes)) == -1) {
						break;
					}
					out.write(data, 0, numBytesRead);
					out1.write(data, 0, numBytesRead);
					if (new Date().getTime() - date.getTime() > 100) {
						break;
					}
				}
				byte b[] = out1.toByteArray();
				for (int i = 0; i < b.length; i++) {
					if (Math.abs(b[i]) >= 1) {
						System.out.println(b[i]);
					}

				}
				out1 = new ByteArrayOutputStream();
			}
			// we reached the end of the stream. stop and close the line.
			line.stop();
			line.close();
			line = null;
			// stop and close the output stream
			try {
				out.flush();
				out.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			// load bytes into the audio input stream for playback
			byte audioBytes[] = out.toByteArray();
			ByteArrayInputStream bais = new ByteArrayInputStream(audioBytes);
			audioInputStream = new AudioInputStream(bais, format,
					audioBytes.length / frameSizeInBytes);
			long milliseconds = (long) ((audioInputStream.getFrameLength() * 1000) / format
					.getFrameRate());
			duration = milliseconds / 1000.0;
			saveToFile(AudioFileFormat.Type.WAVE);
		}
	}

	public static void main(String[] args) {
		RecordTest test = new RecordTest();
		test.open();
		JFrame f = new JFrame("Capture");
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		f.getContentPane().add("Center", test);
		f.pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int w = 500;
		int h = 340;
		f.setLocation(screenSize.width / 2 - w / 2, screenSize.height / 2 - h
				/ 2);
		f.setSize(w, h);
		f.show();
	}
}
