package khh.debug.util;

import java.nio.ByteBuffer;

import khh.date.util.DateUtil;

public class DebugUtil
{

	//////////////////Debug
	public static void trace(String format, Object... args)
	{
		System.out.format(DateUtil.getDate("yyyy-MM-dd HH:mm:ss,SSS") + " : " + format + "\n", args);
	}

	
	
	public static void trace(ByteBuffer tby, String title)
	{
		System.out.println(" | "+title+"  "+" ("+tby.toString()+") ]");
//		System.out.println("/-["+getDateFull_now()+"]─────────────────────────────length("+tby.limit()+")──┐");
		System.out.println("/-["+DateUtil.getDate("yyyy/MM/dd HH:mm:ss/SSS")+"]-----------------------------length("+(tby.limit()-tby.position())+")--\\");
		String charbuffer = "";
		for (int i = tby.position(); i < tby.limit(); i++)
		{
			if ( i % 16 == 0 && i !=0 )
				System.out.println("\t| "+(charbuffer));
			if ( i % 8 == 0 && i !=0 ){
				if(i % 16 != 0)
				System.out.print("|");
			}
			
			if ( i % 16 == 0 && i !=0 )
				charbuffer="";
			
			charbuffer+=(char) tby.get(i);
			System.out.format(" %02X ", tby.get(i));
			
			
			//16개 꽉안찼을때..채운다.
			if(i+1 < tby.limit() ==false){
				int z =16-(i % 16);
				String fill_space="";
				for(int a = 0 ; a <z;a++)
					fill_space+="    ";
					
				System.out.println(fill_space+"\t| "+new String(charbuffer));
			}
		}
//		System.out.print("\n├───────────────────────────────────────────────────────────────────┤");
//		for (int i = tby.position(); i < tby.limit(); i++)
//		{
//			if ( i % 32 == 0 && i !=0 )
//				System.out.println("");
//			System.out.format(" %c ", tby.get(i));
//		}
//		System.out.println("\n└───────────────────────────────────────────────────────────────────┘");
		System.out.println("\n\\------------------------------------------------------------------/");
	}
	
	
	
	public static void trace(final byte[] tby, String title)
	{
		System.out.println("┏▶ "+title+"  "+" ("+tby.toString()+")");
		System.out.println("|-["+DateUtil.getDate("yyyy/MM/dd HH:mm:ss/SSS")+"]-----------------------------length("+tby.length+")--|");
//		System.out.println("┌─["+getDateFull_now()+"]─────────────────────────────length("+tby.length+")──┐");
		String charbuffer ="";// new byte[16];
		for (int i = 0; i <tby.length; i++)
		{
			if ( i % 16 == 0 && i !=0 )
				System.out.println("\t| "+(charbuffer));
			if ( i % 8 == 0 && i !=0 ){
				if(i % 16 != 0)
				System.out.print("| ");
			}
			charbuffer+= (char)tby[i];
			System.out.format(" %02X ", tby[i]);
			
			
			//16개 꽉안찼을때..채운다.
			if(i+1 < tby.length ==false){
				int z =16-(i % 16);
				String fill_space="";
				for(int a = 0 ; a <z;a++)
					fill_space+="    ";
					
				System.out.println(fill_space+"\t| "+(charbuffer));
			}
			
		}
		
			System.out.println("\n|------------------------------------------------------------------|");
		
	}
	
	
}
