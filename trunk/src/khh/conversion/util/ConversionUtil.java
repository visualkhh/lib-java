package khh.conversion.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import khh.std.adapter.Adapter_Std;

import org.bouncycastle.jce.provider.JDKDSASigner.stdDSA;

public class ConversionUtil {

    
    
    
    public static String nullToString(String input, String out){
        String  a = (String)nullToObject(input,out);
        return a;
    }
    public static Integer nullToInateger(Integer input, Integer out){
        Integer  a = (Integer)nullToObject(input,out);
        return a;
    }
    public static Float nullToFloat(Float input, Float out){
        Float  a = (Float)nullToObject(input,out);
        return a;
    }
    public static Double nullToDouble(Double input, Double out){
        Double  a = (Double)nullToObject(input,out);
        return a;
    }
    public static Boolean nullToBoolean(Boolean input, Boolean out){
        Boolean  a = (Boolean)nullToObject(input,out);
        return a;
    }
    
   public  static Object nullToObject(Object input, Object out){
        if(input ==null){
            return out;
        }
        return input;
    }
   
   public static ArrayList<String> toStringArrayList(String[] input){
       ArrayList<String> returnval =null;
       
           for(int i  = 0 ;  i  < input.length;i++){
               if(i==0)
               returnval = new ArrayList<String>();
               returnval.add(input[i]);
           }
       return returnval;
   }
   public static String[] toStringArray(ArrayList<String> input){
       String[] returnval =null;
           for(int i  = 0 ;  i  < input.size();i++){
               if(i==0)
               returnval = new String[input.size()];
               returnval[i]=input.get(i);
           }
       return returnval;
   }
   
   
   public static double[] toDoubleArray(ArrayList<Double> input){
       double[] returnval =null;
           for(int i  = 0 ;  i  < input.size();i++){
               if(i==0)
               returnval = new double[input.size()];
               returnval[i]=input.get(i);
           }
       return returnval;
   }
   
   /////Casting
   
   public static String stringCasting(String data){
      return data;
  }
   public static String stringCasting(int data){
      return Integer.toString(data);
  }

  
  
   public static  String stringCasting(double data){
      return Double.toString(data);
  }
   public static String stringCasting(float data){
      return Float.toString(data);
  }
  
  
   public static String stringCasting(boolean data){
      return Boolean.toString(data);
  }
   
   
   
   
   
   
   

// CONVERTING
   public static String toString(ByteBuffer bytebuff){
       byte[] bytearr = new byte[bytebuff.remaining()];
       bytebuff.get(bytearr);
       String s = new String(bytearr);
       return s;
   }
   
   public static ByteBuffer toByteBuffer(String str){
       ByteBuffer  buff = ByteBuffer.wrap(str.getBytes());
       buff.rewind();
       return buff;
   }
   public static byte[] toByteArray(final short shortvalue) {
       ByteBuffer buff = ByteBuffer.allocate(Short.SIZE / 8);
       buff.putShort(shortvalue);
       buff.order(ByteOrder.BIG_ENDIAN);
       return buff.array();
   }

   public static short toShort(byte[] bytes) {
       final int size = Short.SIZE / 8;
       ByteBuffer buff = ByteBuffer.allocate(size);
       final byte[] newBytes = new byte[size];
       for (int i = 0; i < size; i++) {
           if (i + bytes.length < size) {
               newBytes[i] = (byte) 0x00;
           } else {
               newBytes[i] = bytes[i + bytes.length - size];
           }
       }
       buff = ByteBuffer.wrap(newBytes);
       buff.order(ByteOrder.BIG_ENDIAN);
       return buff.getShort();
   }
   
   
   
   public static byte[] toByteArray(final int integer) {
       ByteBuffer buff = ByteBuffer.allocate(Integer.SIZE / 8);
       buff.putInt(integer);
       buff.order(ByteOrder.BIG_ENDIAN);
       return buff.array();
   }

   
   public static int toInt(byte[] bytes) {
       final int size = Integer.SIZE / 8;
       ByteBuffer buff = ByteBuffer.allocate(size);
       final byte[] newBytes = new byte[size];
       for (int i = 0; i < size; i++) {
           if (i + bytes.length < size) {
               newBytes[i] = (byte) 0x00;
           } else {
               newBytes[i] = bytes[i + bytes.length - size];
           }
       }
       buff = ByteBuffer.wrap(newBytes);
       buff.order(ByteOrder.BIG_ENDIAN);
       return buff.getInt();
   }
   
   public static int[] toIntArray(int data) {
       int [] return_val = new int[4];
       return_val[0]=(data >> 24) & 0xff;
       return_val[1]=(data >> 16) & 0xFF;
       return_val[2]=(data >> 8) & 0xFF;
       return_val[3]=(data >> 0) & 0xFF;
       return return_val;
   }
   
   
   public static byte[] toByteArray(float f) {
         ByteBuffer buff = ByteBuffer.allocate(Float.SIZE / 8);
           buff.putFloat(f);
           buff.order(ByteOrder.BIG_ENDIAN);
           return buff.array();
   }
   
   
   public static float toFloat(byte bytes[]) {
        final int size = Integer.SIZE / 8;
           ByteBuffer buff = ByteBuffer.allocate(size);
           final byte[] newBytes = new byte[size];
           for (int i = 0; i < size; i++) {
               if (i + bytes.length < size) {
                   newBytes[i] = (byte) 0x00;
               } else {
                   newBytes[i] = bytes[i + bytes.length - size];
               }
           }
           buff = ByteBuffer.wrap(newBytes);
           buff.order(ByteOrder.BIG_ENDIAN);
           return buff.getFloat();
       }


   
   ////////////////
   


   
   
   public static byte[][] toByteGroupArray(byte[] data,int groupsize,byte defaultdata,int align_type) {
       
       int rowsize = data.length/groupsize;
       int nam = data.length%groupsize;
       
      // System.out.println(rowsize+"     "+nam);
       
       if(nam>=1){
           rowsize+=1;
       }
          // System.out.println("-----"+data.length+"   nam: "+nam+"    "+groupsize+"       "+rowsize);
       byte[][] return_val = new byte[rowsize][];
       for (int i = 0; i < rowsize; i++) {
           byte[] input_data = new byte[groupsize];
           int row_startindex = groupsize*i;
          // row_startindex=row_startindex<=0?0:row_startindex-1;
           int row_endindex = (groupsize);
           
        
           
           
         //  System.out.println(input_data.length+"      "+row_startindex+"      "+row_endindex);
           try{
               input_data = sliceByteArray(data,row_startindex,row_endindex);
           }catch (IndexOutOfBoundsException e) {
               input_data = sliceByteArray(data,row_startindex,data.length-row_startindex);
           }
           
           
           return_val[i] =toByteArray(input_data,groupsize,defaultdata,align_type);
           
       }
       
      // System.out.println(rowsize+"    "+data.length+"      "+groupsize+"    "+nam );
       
       return return_val;
   }
   
   public static final int ALIGN_LEFT=1;
   public static final int ALIGN_RIGHT=2;
   public static byte[] sliceByteArray(byte[] data,int start_index,int length){
       byte[] return_datal=new byte[length];
       System.arraycopy(data, start_index, return_datal, 0,length );
       return return_datal;
   }
   
   public static byte[] toByteArray(byte[] data,int length,byte defaultdata, int align_type){
       byte[] retrun_data =new byte[length];
       for (int i = 0; i < retrun_data.length; i++) {
           retrun_data[i]=defaultdata;
       }
       if(align_type == ALIGN_LEFT){
           System.arraycopy(data, 0, retrun_data, 0, data.length);
       }else if(align_type == ALIGN_RIGHT){
           System.arraycopy(data, 0, retrun_data, retrun_data.length-data.length, data.length);
       }
       return retrun_data;
   }
   
   
// hex to byte[]
   public static byte[] toByteArray(String hex_str) {
       if (hex_str == null || hex_str.length() == 0) {
           return null;
       }
       hex_str = hex_str.replaceFirst("^0x", "");
    
       byte[] ba = new byte[hex_str.length() / 2];
       for (int i = 0; i < ba.length; i++) {
           ba[i] = (byte) Integer.parseInt(hex_str.substring(2 * i, 2 * i + 2), 16);
       }
       return ba;
   }
    
   
   // byte[] to hex
   public static String toHexString(byte[] ba) {
       if (ba == null || ba.length == 0) {
           return null;
       }
    
       StringBuffer sb = new StringBuffer(ba.length * 2);
       String hexNumber;
       for (int x = 0; x < ba.length; x++) {
           hexNumber = "0" + Integer.toHexString(0xff & ba[x]);
           sb.append(hexNumber.substring(hexNumber.length() - 2));
       }
       return sb.toString();
   } 
   
   public static int toInt(String data,int read_bit){
       return Integer.parseInt(data, 16);
   }
   
   
   
   public static Adapter_Std mapToAdapter(Map map) throws Exception{
//	   if(map==null){
//		   return null;
//	   }
	   Adapter_Std adapter = new Adapter_Std();
	   
	   Set keyset = map.keySet();
	   Iterator i = keyset.iterator();
	   while(i.hasNext()){
			Object key 		= i.next();
			Object value  	= map.get(key);
			adapter.add(key, value);
//			System.out.println("key:"+key+"    value:"+value);	
		}
	return adapter;
	   
   }
   
   
   
   
   
   
    
//    
//    static public String replaceUL(String inputStr,String matchingStr, String replaceStr){
//        return inputStr.replaceFirst("(?i)"+matchingStr, replaceStr);
//    }
//    
//    static public String replaceAll(String inputStr,String[] matchingStrArr, String replaceStr){
//        for(int i = 0 ; i < matchingStrArr.length;i++){
//            inputStr =  replaceAll(inputStr,matchingStrArr[i], replaceStr);
//        }
//        return inputStr;
//    }
//    
//    static public String replaceAll(String inputStr,ArrayList<String> matchingStrArr, String replaceStr){
//        for(int i = 0 ; i < matchingStrArr.size();i++){
//            inputStr =  replaceAll(inputStr,(String)matchingStrArr.get(i), replaceStr);
//        }
//        return inputStr;
//    }
//    
//    static public String replaceAll(String inputStr,HashMap<String,String> matchingMap){
//
//        Iterator iter = matchingMap.keySet().iterator();
//        
//        while(iter.hasNext()){
//            String key =(String) iter.next();
////          String key2 =h_rex(key);
////          System.out.println(key+"  "+key2);
////          System.out.println(inputStr);
//            inputStr =replaceAll(inputStr,key, (String) matchingMap.get(key));
//        }
//        
//        return inputStr;
//    }
//    
//    static public String replaceAll(String inputStr,String matchingStr,String replaceStr){
//        if(inputStr==null)
//            return null;
//        
//        String key  =regexMetaCharToEscapeChar(matchingStr);
//        if(matchingStr==null || replaceStr==null || key==null){
//            return inputStr;
//        }
//        
//        return inputStr.replaceAll(key,replaceStr);
//        
//    }
//    
//    
//    
//    public static String regexMetaCharToEscapeChar(String h){
//        String in = (String)h;
//        in = in.replaceAll("\\\\", "\\\\\\\\");
//        in = in.replaceAll("\\.", "\\\\\\.");
//        in = in.replaceAll("\\?", "\\\\\\?");
//        in = in.replaceAll("\\(", "\\\\\\(");
//        in = in.replaceAll("\\)", "\\\\\\)");
//        in = in.replaceAll("\\{", "\\\\\\{");
//        in = in.replaceAll("\\[", "\\\\\\[");
//        in = in.replaceAll("\\$", "\\\\\\$");
//        in = in.replaceAll("\\^", "\\\\\\^");
//        in = in.replaceAll("\\*", "\\\\\\*");
//        in = in.replaceAll("\\+", "\\\\\\+");
//        in = in.replaceAll("\\|", "\\\\\\|");
//        return in;
//    }
//    public static String tagMetaCharToEscapeChar(String h){
//        HashMap<String,String> map = new HashMap();
//        map.put("&", "&#38;");
//        h = replaceAll(h,map);
//        map = new HashMap();
//        map.put("<", "&lt;");
//        map.put(">", "&gt;");
//        map.put("(", "&#40;");
//        map.put(")", "&#41;");
//        map.put("#", "&#35;");
//        map.put("'", "&#39;");
//        map.put("\"", "&#quot;");
//        map.put(" ", "&nbsp;");
//        map.put("=", "&#61");
//         h = replaceAll(h,map);
//         return h;
//     }
    
    
    
    
    
    
    
    
    

    
    
    
}
