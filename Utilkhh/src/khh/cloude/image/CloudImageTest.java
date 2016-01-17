package khh.cloude.image;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.stream.ImageInputStream;

import khh.conversion.util.ConversionUtil;
import khh.file.util.FileUtil;
import khh.image.ImageUtil;
import khh.util.ByteUtil;

public class CloudImageTest {

	public static void main(String[] args) throws IOException {
		CloudImage img = new CloudImage();
		img.setData("caprisong@gmail.com".getBytes());
		img.writeImagePNG(new File("c:\\ggg.png"));
	}

}
