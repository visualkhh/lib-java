import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;

public class PhotoGPS {
	public static void main(String[] args) {
		
		try {
            ImageInputStream inStream = ImageIO.createImageInputStream(new File("c:\\IMG_5003.JPG"));
            Iterator<ImageReader> imgItr = ImageIO.getImageReaders(inStream);

            while (imgItr.hasNext()) {
                ImageReader reader = imgItr.next();
                reader.setInput(inStream, true);
                IIOMetadata  metadata = reader.getImageMetadata(0);

                String[] names = metadata.getMetadataFormatNames();
                int length = names.length;
                //metadata.getMetadataFormat(names[ 0 ]);
                for (int i = 0; i < length; i++) {
                    System.out.println( "Format name: " + names[ i ]+ "  " + metadata.getMetadataFormat(names[ i ]) );
                    System.out.println(metadata.getAsTree(names[ i ]));;
                }  
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
