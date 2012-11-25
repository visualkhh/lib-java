package propertiTest;

import com.kdn.util.property.PropertiesUtil;
import com.kdn.util.property.PropertyUtil;



class ttt{
	
	public ttt()
	{
		this(10);
	}
	
	public ttt(int i){
		
	}
	
	
	public void print(){
//		InputStream is = getClass().getResourceAsStream("db.properties");
//		Properties dbProps = new Properties();
//		try {
//			dbProps.load(is);
//			String logFile = dbProps.getProperty("logfile", "");
//			String maxconn = dbProps.getProperty("oracle.maxconn", "");
//			String user = dbProps.getProperty("oracle.user", "");
//			String password = dbProps.getProperty("oracle.password", "");
//			String password2 = dbProps.getProperty("oracle.password2", "빵꾸");
//			System.out.println(logFile);
//			System.out.println(maxconn);
//			System.out.println(user);
//			System.out.println(password);
//			System.out.println(password2);
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
		try
		{
			PropertiesUtil pu = new PropertiesUtil("db.properties");
			System.out.println(pu.getProperty("logfile"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		System.out.println(PropertyUtil.getEndorsedDir());
	}
}

public class propertiT
{
	public static void main(String[] args)
	{
	new ttt().print();
	}
}
