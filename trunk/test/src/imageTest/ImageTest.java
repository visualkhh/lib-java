package imageTest;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import khh.conversion.util.ConversionUtil;
import khh.file.util.FileUtil;
import khh.image.ImageUtil;

public class ImageTest {
	public static void main(String[] args) throws IOException {
		// BufferedImage i = new
		// BufferedImage(100,100,BufferedImage.TYPE_INT_BGR);
		// i.getGraphics().setColor(new Color(255, 10, 20));
		// i.getGraphics().fillRect(0, 0, 50, 50);
		// FileUtil.writeFile("c:\\goodjob.jpg", i, "jpg");
		// / ImageUtil.drawBufferedImage(width, height, bufferedimageType,
		// colorlist)
		BufferedImage buf = ImageUtil.getBuffreadImage(new File("c:\\23950902.jpg"));
		System.out.println( ImageUtil.toByteArray(buf).length );
		
		System.out.println( ImageUtil.toByteArray(buf, "jpg").length );
		FileUtil.writeFile(new File("c:\\23950902hh.jpg"), ImageUtil.toByteArray(buf, "jpg"));
		FileUtil.writeFile(new File("c:\\23950902h33.jpg"), ImageUtil.toByteArray(buf));
	}
}
