package sound;

import javax.sound.sampled.TargetDataLine;

/**
 * @Comments ：
 * @Author ：陈彦吉
 * @Group : A组
 * @Worker: 1416
 * @Date ：2012-10-24 下午04:05:33
 * @Project ：wow
 * @Company ：Vstsoft
 */
public class Test {

	static TargetDataLine line;

	static int size = 0;

//	public static void listenerMisc() {
//		AudioFormat.Encoding encoding = AudioFormat.Encoding.PCM_SIGNED;
//		float rate = 96000;
//		int sampleSize = 8;
//		boolean bigEndian = true;
//		int channels = 2;
//		AudioFormat format = new AudioFormat(encoding, rate, sampleSize,
//				channels, (sampleSize / sampleSize) * channels, rate, bigEndian);
//		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
//		if (!AudioSystem.isLineSupported(info)) {
//			return;
//		}
//		try {
//			line = (TargetDataLine) AudioSystem.getLine(info);
//			line.open(format, line.getBufferSize());
//			System.out.println(line.getBufferSize());
//		} catch (Exception ex) {
//			line.close();
//			return;
//		}
//		ByteArrayOutputStream out = new ByteArrayOutputStream();
//		int frameSizeInBytes = format.getFrameSize();
//		int bufferLengthInFrames = line.getBufferSize() / sampleSize;
//		int bufferLengthInBytes = bufferLengthInFrames * frameSizeInBytes;
//		
//		byte[] data = new byte[bufferLengthInBytes];
//		int numBytesRead;
//		line.start();
//		while (true) {
//			Date date = new Date();
//// if (date.getTime() - datekey1 >= 19000) {
//// int temp1 = new Date().getSeconds();
//// wowAWTFishing.println("超时："
//// + (temp1 < 10 ? "0" + temp1 : temp1));
//// wowAWTFishing.wowJTextField_no.setText(Integer
//// .parseInt(wowAWTFishing.wowJTextField_no.getText())
//// + 1 + "");
//// try {
//// line.stop();
//// line.close();
//// line = null;
//// out.close();
//// } catch (IOException ex) {
//// }
//// return;
//// }
//			while (true) {
//				if ((numBytesRead = line.read(data, 0, bufferLengthInBytes)) == -1) {
//					break;
//				}
//				out.write(data, 0, numBytesRead);
//				if (new Date().getTime() - date.getTime() > 100) {
//					break;
//				}
//			}
//			byte b[] = out.toByteArray();
//			for (int i = 0; i < b.length; i++) {
//				if (Math.abs(b[i]) > size ){
//					size=Math.abs(b[i]);
//				}else{
//					System.out.println(size+":"+Math.abs(b[i]));
//				}
////				if (Math.abs(b[i]*10) >= wowAWTFishing.miscSize) {
////					shiqu();
////					try {
////						line.stop();
////						line.close();
////						line = null;
////						out.close();
////					} catch (IOException ex) {
////					}
////					return;
////				}
//
//			}
//			out = new ByteArrayOutputStream();
//		}
//	}

	/**
	 * @Comments ：
	 * @param args
	 * @Author ：陈彦吉
	 * @Group : A组
	 * @Worker: 1416
	 * @Date ：2012-10-24 下午04:05:33
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		listenerMisc();
	}
	


}
