import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import com.ibm.icu.text.NumberFormat;

import khh.file.util.FileUtil;
import khh.url.util.URLUtil;

public class testmain {
    public testmain() {
        // TODO Auto-generated constructor stub
    }

    public void s() throws InterruptedException, IOException {
    }

    public static void main(String[] args) throws MalformedURLException, IOException{
    	
    	NumberFormat numberformat = NumberFormat.getInstance();
    	numberformat.setMinimumIntegerDigits(2);
    	
    	
    	for (int i = 1; i <= 30; i++) {
    	//System.out.println(numberformat.format(1));
    		String url="http://contents.e-campus.co.kr/vmc/94062/94062_"+numberformat.format(i);	
    		//01/01/swf/0101.swf
    		for (int j = 0; j <= 30; j++) {
    			String long_url = url+"/"+numberformat.format(j)+"/swf/";
    			
    			for (int z = 100; z < 120; z++) {
    				String file_name = "0"+z+".swf";
    				String full_url = long_url+file_name;
    				String save_name = numberformat.format(i)+"_"+numberformat.format(j)+"_"+file_name;
    				System.out.println(full_url+"     "+save_name);
    				
    				
    				try{
    		    	InputStream is = URLUtil.getInputStream(full_url);
    		    	String filepath ="Z:\\download\\"+save_name;
    		    	FileUtil.writeFile(filepath, is);
    				}catch (Exception e) {
    					e.printStackTrace();
					}
    		    	
    		    	
				}
			}
			
		}

    }
}
