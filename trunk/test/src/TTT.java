import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import khh.file.util.FileUtil;
import khh.url.util.URLUtil;

public class TTT {
	public static void main(String[] args) throws MalformedURLException, IOException {
		//for (int i = 3725; i <99999 ; i++) {
			//FileUtil.writeFile(new File("G:\\viusalhhk\\list.txt"),"------",true);
			//System.out.println(i);
		//}
		String toDay ="20121220";
			String startDate = "20121218";
			String  endDate   = "20121219";
		if((Integer.parseInt(startDate) <= Integer.parseInt(toDay)) && (Integer.parseInt(toDay) <= Integer.parseInt(endDate))){
			System.out.println("탔어");
		}
	}
}
