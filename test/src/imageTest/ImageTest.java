package imageTest;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import khh.file.util.FileUtil;
import khh.image.ImageUtil;

public class ImageTest {
public static void main(String[] args) throws IOException {
    BufferedImage i = new BufferedImage(100,100,BufferedImage.TYPE_INT_BGR);
    i.getGraphics().setColor(new Color(255, 10, 20));
    i.getGraphics().fillRect(0, 0, 50, 50);
    FileUtil.writeFile("c:\\goodjob.jpg", i, "jpg");
//   / ImageUtil.drawBufferedImage(width, height, bufferedimageType, colorlist)
}
}
