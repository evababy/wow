import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class GuiCamera {
	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

	public void snapShot() {
		try {
			BufferedImage screenshot = (new Robot())
					.createScreenCapture(new Rectangle(0, 0,
							(int) d.getWidth(), (int) d.getHeight()));
			Point point = findFirstImg();
			System.out.println(point.x + "," + point.y);
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	public static void main(String[] args) {
		GuiCamera cam = new GuiCamera();// 
		cam.snapShot();
	}

	public Point findFirstImg() throws Exception {
		int size = 10;
		try {
			BufferedImage formImg = ImageIO.read(new File("d:/Hello2.png"));// 目标图片
			BufferedImage ctrlImg = ImageIO.read(new File("d:/Hello1.png"));// 基准图片
			int formWidth = formImg.getWidth();
			int formHeight = formImg.getHeight();
			int ctrlWidth = ctrlImg.getWidth();
			int ctrlHeight = ctrlImg.getHeight();
			int start = ctrlImg.getRGB(ctrlImg.getMinX(), ctrlImg.getMinY());
			int two = ctrlImg.getRGB(ctrlWidth - 1, ctrlImg.getMinTileY());
			int three = ctrlImg.getRGB(ctrlImg.getMinX(), ctrlHeight - 1);
			int four = ctrlImg.getRGB(ctrlWidth - 1, ctrlHeight - 1);
			int five = ctrlImg.getRGB(ctrlWidth / 2, ctrlHeight / 2);
			int six = ctrlImg.getRGB(ctrlWidth / 4, ctrlHeight / 4);
			int seven = ctrlImg.getRGB(ctrlWidth / 2, (ctrlHeight / 4) * 3);
			int eight = ctrlImg.getRGB((ctrlWidth / 4) * 3, ctrlHeight / 2);
			int nine = ctrlImg.getRGB(ctrlWidth / 4, ctrlHeight / 2);
			for (int i = 0; i < formWidth; i++) {
				for (int j = 0; j < formHeight; j++) {
					if (Math.abs((formImg.getRGB(i, j) - start)) < size
							&& Math.abs((formImg.getRGB(i + ctrlWidth / 2, j
									+ ctrlHeight / 2) - five)) < size
							&& Math.abs(formImg.getRGB(i + ctrlWidth - 1, j)
									- two) < size
							&& Math.abs(formImg.getRGB(i, j + ctrlHeight - 1)
									- three) < size
							&& Math.abs(formImg.getRGB(i + ctrlWidth - 1, j
									+ ctrlHeight - 1)
									- four) < size
							&& Math.abs(formImg.getRGB(i + ctrlWidth / 4, j
									+ ctrlHeight / 4)
									- six) < size
							&& Math.abs(formImg.getRGB(i + ctrlWidth / 2, j
									+ (ctrlHeight / 4) * 3)
									- seven) < size
							&& Math.abs(formImg.getRGB(i + (ctrlWidth / 4) * 3,
									j + ctrlHeight / 2)
									- eight) < size
							&& Math.abs(formImg.getRGB(i + ctrlWidth / 4, j
									+ ctrlHeight / 2)
									- nine) < size) {
						return new Point(i, j);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("!!!!!" + e);
		}
		return null;
	}

}
