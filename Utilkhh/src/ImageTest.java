
import java.io.File;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.swing.text.html.HTML.Tag;

public class ImageTest {
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Usage: Test <image-file>");
			System.exit(0);
		}

		String filename = args[0];
		System.out.println("Filename: " + filename);

		try {
			File jpgFile = new File(filename);
			Metadata metadata = ImageMetadataReader.readMetadata(jpgFile);

			// Read Exif Data
			Directory directory = metadata.getDirectory(ExifDirectory.class);
			if (directory != null) {
				// Read the date
				Date date = directory.getDate(ExifDirectory.TAG_DATETIME);
				DateFormat df = DateFormat.getDateInstance();
				df.format(date);
				int year = df.getCalendar().get(Calendar.YEAR);
				int month = df.getCalendar().get(Calendar.MONTH) + 1;

				System.out.println("Year: " + year + ", Month: " + month);

				System.out.println("Date: " + date);

				System.out.println("Tags");
				for (Iterator i = directory.getTagIterator(); i.hasNext();) {
					Tag tag = (Tag) i.next();
					System.out.println("\t" + tag.getTagName() + " = " + tag.getDescription());

				}
			} else {
				System.out.println("EXIF is null");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
