package renametest;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.Box.Filler;

import com.kdn.util.ValidationUtil;
import com.kdt.util.Utilities;
import com.khh.util.file.FileUtil;

public class rename
{
	public static void main(String[] args)
	{
		if(args.length>0)
		System.out.println(args[0]);
		else 
			System.exit(0);
		
		
		File file = new File(args[0]);
		
		if(FileUtil.getFileExistence(file) && file.isDirectory()){
			
			File[] filslist = file.listFiles();
			for (int i = 0; i < filslist.length; i++)
			{
				File atFile = filslist[i];
				
				boolean isrename=false;
				if(!atFile.isDirectory()){
					HashMap map  = new HashMap();
					map.put("업무처리 내역","");
					map.put("4.","");
					map.put("3.","");
					map.put(" ","");
					map.put("_back","");
					
					String newpath = atFile.getParent()+"\\"+ValidationUtil.replaceAll(atFile.getName(), map);
					try
					{
						FileUtil.FileRename(atFile, new File(newpath));
						isrename=true;
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
				
				System.out.println(atFile.getParent()+"    "+atFile.getName()+"      "+atFile.isDirectory()+"       isrename: "+isrename);
				
				
			}
		}
		
		
		
	}
}
