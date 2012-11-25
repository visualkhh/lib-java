import java.io.OutputStream;
import java.net.Socket;

import com.kdt.util.math.Calculator;

public class communicationTest
{
	public static void main(String[] args) throws Exception
	{
		String ServerAddr = "211.210.38.106";
		int ServerPort = 5000;
		Socket soc =new Socket(ServerAddr, ServerPort);
		OutputStream os=soc.getOutputStream();
		
		
		
		while(true){
			try{
			System.out.println("isClosed:["+soc.isClosed()+"]    isConnected:["+soc.isConnected()+"]   isInputShutdown:["+soc.isInputShutdown()+"]    isOutputShutdown:["+soc.isOutputShutdown()+"]");
		os.write("aaaaaa".getBytes());
			}catch (Exception e) {
				e.printStackTrace();
			}
		Thread.sleep(2000);
		
		}
		
//		String ServerAddr = "211.210.38.106";
//		String ServerAddr = "192.168.1.254";
//		int ServerPort = 5000;
//		Cons c = new Cons(ServerAddr, ServerPort);
//		int i = 0;
//		while (true)
//		{
//			try
//			{
//				System.out.println(c.isConnected());
//				i++;
//				c.sendMessage("abcdefg" + i);
//			}
//			catch (Exception e)
//			{
//				e.printStackTrace();
//			}
//			Thread.sleep(2000);
//		}
		
		
		
		
//		BcheckerCommunication com= null;
//		
//		try{
//			Utilities.trace("Start"+ServerAddr);
//			com  = new BcheckerCommunication(ServerAddr, ServerPort);
//			com.setReadBlock_TimeOut(3000);
//			Utilities.trace("END");
//		
//		}catch(Exception e){
//			Utilities.trace("Connection Exception   MSG  =>  %s",e.getMessage());
//		}
//		
//		
//		
//			while(true){
//				try{
//					com.setFunctionCode("R001");
//					com.execute();
//				}catch(Exception e){
////					try{
////					Utilities.trace("isConnected (%s)   Write Exception   MSG  =>  %s",com.isConnected(),e.getMessage());
////					}catch(Exception ee){
////						ee.printStackTrace();
////					}
//					e.printStackTrace();
//				}
//				Thread.sleep(2000);
//				}
////		
//		Adapter_Std<String, String>	taskContainer	= new Adapter_Std<String, String>();
//		
//		taskContainer.add("1","1");
//		System.out.println(taskContainer.size());
//		taskContainer.add("2","2");
//		System.out.println(taskContainer.size());
//		taskContainer.set("3","3");
//		System.out.println(taskContainer.size());
////		taskContainer.remove_byIndex(1);
////		taskContainer.remove_byKey("1");
//		System.out.println(taskContainer.size());
//		
//		
//		
//		Standard<String, String> ba = new Standard<String, String>("1", "1");
//		Standard<String, String> aa = ba;
//		if(aa==(ba)){
//			System.out.println("1t");
//		}
////		
//		if(new Date(888).equals(new Date(888))){
//			System.out.println("2t");
//		}
////		
////		if(new Standard<String, String>("1", "1").equals(new Standard<String, String>("1", "1"))){
////			System.out.println("3t");
////		}
////		
//		
//		if(new String("a").equals(new String("a"))){
//			System.out.println("4t");
//		}
		
	
//		
	}
		
}
