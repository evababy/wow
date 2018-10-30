import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;

public class ImageTest {
	public static int getBrighter(int red, int gre, int blu, int flag) {

		red += flag;
		gre += flag;
		blu += flag;

		if (red > 255)
			red = 255;
		if (gre > 255)
			gre = 255;
		if (blu > 255)
			blu = 255;

		if (red < 0)
			red = 0;
		if (gre < 0)
			gre = 0;
		if (blu < 0)
			blu = 0;

		return new Color(red, gre, blu).getRGB();
	}

	public static void main(String[] args) {

		BufferedImage Source_image = null;
		BufferedImage Water_image = null;
		BufferedImage New_image = null;
		try {
			FileInputStream in = new FileInputStream("d:/temp/aa.jpg");// ophama97.jpg要装入的图片
			JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(in);
			Source_image = decoder.decodeAsBufferedImage();// 取的bufferedimage
															// 对象

			// 水印文件
			File _filebiao = new File("d:/temp/bb.jpg");
			BufferedImage src_biao = ImageIO.read(_filebiao);
			int wideth_biao = src_biao.getWidth(null);
			int height_biao = src_biao.getHeight(null);

			int height = Source_image.getHeight();
			int width = Source_image.getWidth();
			// Source_image.getco
			New_image = new BufferedImage(wideth_biao, height_biao, 1);
			// New_image = Source_image;
			int color_now = 0;

			int[] intColor = new int[9];

			for (int i = 1; i < width; i++) {
				for (int j = 1; j < height; j++) {
					// int color_now = Source_image.getRGB(i,j); //从x,y读颜色

					// System.out.println(color_now);
					// 图片加亮
					// int newColor = getBrighter(red,gre,blu,100);
					// Source_image.setRGB(i,j,newColor);//向x,y写颜色color_now
					// System.out.println(color_now);

					// 上下翻转
					// New_image.setRGB(i,height -
					// j,color_now);//向x,y写颜色color_now

					// 左右镜像
					// New_image.setRGB(width -
					// i,j,color_now);//向x,y写颜色color_now

					// 反色

					// 水印
					// New_image.setRGB(i,j,color_now);//向x,y写颜色color_now

					// 旋转
					 //New_image.setRGB(i,j,color_now);
				}
			}

			// 旋转
			AffineTransform origXform = AffineTransform.getScaleInstance(1, 1);
//			origXform.rotate(Math.toRadians(-3), 128, 128);
			AffineTransformOp atfo = new AffineTransformOp(origXform, 1);
			atfo.filter(Source_image, New_image);

		} catch (Exception e) {
			e.printStackTrace();
		}

		boolean result = false;
		try {
			result = ImageIO.write(New_image, "JPEG", new File("d:/temp/cc.jpg"));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}

}
