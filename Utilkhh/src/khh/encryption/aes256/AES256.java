package khh.encryption.aes256;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import khh.encryption.base64.Base64;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
public class AES256 {
	 private static volatile AES256 INSTANCE;
	 //http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html 
	 //local_policy.jar, US_export_policy.jar download version 별로 받아서 사용해라 
	 //http://stackoverflow.com/questions/6481627/java-security-illegal-key-size-or-default-parameters
	 //$JAVA_HOME/jre/lib/security 덮어쒸어라.
	 
	 
//	 final static String secretKey   = "12345678901234567890123456789012"; //32bit
//	 static String IV                = ""; //16bit
	 
//	 public static AES256 getInstance(){
//	     if(INSTANCE==null){
//	         synchronized(AES256.class){ 
//	             if(INSTANCE==null)
//	                 INSTANCE=new AES256();
//	         }
//	     }
//	     return INSTANCE;
//	 }
	 
//	 private AES256(){
//	     IV = secretKey.substring(0,16);
//	    }
	 
	 //암호화
	 public static String encode(String secretKey,String str) throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
		 
	     byte[] keyData = secretKey.getBytes();
	     String IV = secretKey.substring(0,16);
	 
	 SecretKey secureKey = new SecretKeySpec(keyData, "AES");
	 
	 Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
	 c.init(Cipher.ENCRYPT_MODE, secureKey, new IvParameterSpec(IV.getBytes()));
	 
	 byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
	 String enStr = new String(Base64.encode(encrypted));
	 
	 return enStr;
	 }
	 
	 //복호화
	 public static String decode(String secretKey,String str) throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
	  byte[] keyData = secretKey.getBytes();
	  String IV = secretKey.substring(0,16);
	  SecretKey secureKey = new SecretKeySpec(keyData, "AES");
	  Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
	  c.init(Cipher.DECRYPT_MODE, secureKey, new IvParameterSpec(IV.getBytes("UTF-8")));
	 
	  byte[] byteStr = Base64.decode(str);
	 
	  return new String(c.doFinal(byteStr),"UTF-8");
	 }
	 public static void main(String[] args) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		 String key = "12345678901234567890123456789012";
		 String a = AES256.encode(key,"rtyrt");
//		 String a ="ff";
		 System.out.println(a);
		 
		 a = AES256.decode(key, a);
		 System.out.println(a);
	}
}
