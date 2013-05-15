package khh.util;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 이 클래스는 Byte 관련 함수를 제공합니다. 
 *  
*/
public class ByteUtil {
	public static Byte DEFAULT_BYTE = new Byte((byte) 0);
	
	/**
	 * <p>문자열을 바이트로 변환한다.</p>
	 * 
	 * <pre>
	 * ByteUtils.toByte("1", *)    = 0x01
	 * ByteUtils.toByte("-1", *)   = 0xff
	 * ByteUtils.toByte("a", 0x00) = 0x00
	 * </pre>
	 * 
	 * @param value 10진수 문자열 값
	 * @param defaultValue
	 * @return
	 */
	public static byte toByte(String value, byte defaultValue) {
		try {
			return Byte.parseByte(value);	
		} catch(Exception e) {
			return defaultValue;
		}
	}

	/**
	 * <p>문자열을 바이트로 변환한다.</p>
	 * 
	 * <pre>
	 * ByteUtils.toByteObject("1", *)    = 0x01
	 * ByteUtils.toByteObject("-1", *)   = 0xff
	 * ByteUtils.toByteObject("a", 0x00) = 0x00
	 * </pre>
	 * 
	 * @param value 10진수 문자열 값
	 * @param defaultValue
	 * @return
	 */
	public static Byte toByteObject(String value, Byte defaultValue) {
		try {
			return new Byte(value);
		} catch (Exception e) {
			return defaultValue;
		}
	}

    
    /**
     * <p>singed byte를 unsinged byte로 변환한다.</p>
     * <p>Java에는 unsinged 타입이 없기때문에, int로 반환한다.(b & 0xff)</p>
     * 
     * @param b singed byte
     * @return unsinged byte 
     */
	public static int unsignedByte(byte b) {
		return  b & 0xFF;
	}

	/**
	 * <p>입력한 바이트 배열(4바이트)을 int 형으로 변환한다.</p>
	 * 
	 * @param src
	 * @param srcPos
	 * @return
	 */
	public static int toInt(byte[] src, int srcPos) {
		int dword = 0;
		for (int i = 0; i < 4; i++) {
			dword = (dword << 8) + (src[i + srcPos] & 0xFF);
		}
		return dword;
	}
	
	/**
	 * <p>입력한 바이트 배열(4바이트)을 int 형으로 변환한다.</p>
	 * 
	 * @param src
	 * @return
	 */
	public static int toInt(byte[] src) {
		return toInt(src, 0);
	}
	
	/**
	 * <p>입력한 바이트 배열(8바이트)을 long 형으로 변환한다.</p>
	 * 
	 * @param src
	 * @param srcPos
	 * @return
	 */
	public static long toLong(byte[] src, int srcPos) {
		long qword = 0;
		for (int i = 0; i < 8; i++) {
			qword = (qword << 8) + (src[i + srcPos] & 0xFF);
		}
		return qword;
	}
	
	/**
	 * <p>입력한 바이트 배열(8바이트)을 long 형으로 변환한다.</p>
	 * 
	 * @param src
	 * @return
	 */
	public static long toLong(byte[] src) {
		return toLong(src, 0);
	}

	/**
	 * <p>int 형의 값을 바이트 배열(4바이트)로 변환한다.</p>
	 * 
	 * @param value
	 * @param dest
	 * @param destPos
	 */
	public static void toBytes(int value, byte[] dest, int destPos) {
		for (int i = 0; i < 4; i++) {
			dest[i + destPos] = (byte)(value >> ((7 - i) * 8));
		}
	}
	
	/**
	 * <p>int 형의 값을 바이트 배열(4바이트)로 변환한다.</p>
	 * 
	 * @param value
	 * @return
	 */
	public static byte[] toBytes(int value) {
		byte[] dest = new byte[4];
		toBytes(value, dest, 0);
		return dest;
	}
	
	 public static final byte[] toBytes(short s)
	    {
	        byte dest[] = new byte[2];
	        dest[1] = (byte)(s & 0xff);
	        dest[0] = (byte)(s >>> 8 & 0xff);
	        return dest;
	    }
	
	/**
	 * <p>long 형의 값을 바이트 배열(8바이트)로 변환한다.</p>
	 * 
	 * @param value
	 * @param dest
	 * @param destPos
	 */
	public static void toBytes(long value, byte[] dest, int destPos) {
		for (int i = 0; i < 8; i++) {
			dest[i + destPos] = (byte)(value >> ((7 - i) * 8));
		}
	}
	
	/**
	 * <p>long 형의 값을 바이트 배열(8바이트)로 변환한다.</p>
	 * 
	 * @param value
	 * @return
	 */
	public static byte[] toBytes(long value) {
		byte[] dest = new byte[8];
		toBytes(value, dest, 0);
		return dest;
	}
	
	/**
	 * <p>8, 10, 16진수 문자열을 바이트 배열로 변환한다.</p>
	 * <p>8, 10진수인 경우는 문자열의 3자리가, 16진수인 경우는 2자리가, 하나의 byte로 바뀐다.</p>
	 * 
	 * <pre>
	 * ByteUtils.toBytes(null)     = null
	 * ByteUtils.toBytes("0E1F4E", 16) = [0x0e, 0xf4, 0x4e]
	 * ByteUtils.toBytes("48414e", 16) = [0x48, 0x41, 0x4e]
	 * </pre>
	 * 
	 * @param digits 문자열
	 * @param radix 진수(8, 10, 16만 가능)
	 * @return
	 * @throws NumberFormatException
	 */
	public static byte[] toBytes(String digits, int radix) throws IllegalArgumentException, NumberFormatException {
		if (digits == null) {
			return null;
		}
		if (radix != 32 && radix != 16 && radix != 10 && radix != 8) {			//radix != 32 추가
			throw new IllegalArgumentException("For input radix: \"" + radix + "\"");
		}
		int divLen = (radix == 32) ? 2 : 3;
    	int length = digits.length();
    	if (length % divLen == 1) {
    		throw new IllegalArgumentException("For input string: \"" + digits + "\"");
    	}
    	length = length / divLen;
    	byte[] bytes = new byte[length];
    	for (int i = 0; i < length; i++) {
    		int index = i * divLen;
    		bytes[i] = (byte)(Short.parseShort(digits.substring(index, index+divLen), radix));
    	}
    	return bytes;
	}
	
	/**
	 * <p>16진수 문자열을 바이트 배열로 변환한다.</p>
	 * <p>문자열의 2자리가 하나의 byte로 바뀐다.</p>
	 * 
	 * <pre>
	 * ByteUtils.toBytesFromHexString(null)     = null
	 * ByteUtils.toBytesFromHexString("0E1F4E") = [0x0e, 0xf4, 0x4e]
	 * ByteUtils.toBytesFromHexString("48414e") = [0x48, 0x41, 0x4e]
	 * </pre>
	 * 
	 * @param digits 16진수 문자열
	 * @return
	 * @throws NumberFormatException
	 * @see HexUtils.toBytes(String)
	 */
	public static byte[] toBytesFromHexString(String digits) throws IllegalArgumentException, NumberFormatException {
		if (digits == null) {
			return null;
		}
    	int length = digits.length();
    	if (length % 2 == 1) {
    		throw new IllegalArgumentException("For input string: \"" + digits + "\"");
    	}
    	length = length / 2;
    	byte[] bytes = new byte[length];
    	for (int i = 0; i < length; i++) {
    		int index = i * 2;
    		bytes[i] = (byte)(Short.parseShort(digits.substring(index, index+2), 16));
    	}
    	return bytes;
	}
	
	/**
	 * <p>unsigned byte(바이트)를 16진수 문자열로 바꾼다.</p>
	 * 
	 * ByteUtils.toHexString((byte)1)   = "01"
	 * ByteUtils.toHexString((byte)255) = "ff"
	 * 
	 * @param b unsigned byte
	 * @return
	 * @see HexUtils.toString(byte)
	 */
	public static String toHexString(byte b) {
		StringBuffer result = new StringBuffer(3);
		result.append(Integer.toString((b & 0xF0) >> 4, 16));
		result.append(Integer.toString(b & 0x0F, 16));
		return result.toString();
	}
	
	/**
	 * <p>unsigned byte(바이트) 배열을 16진수 문자열로 바꾼다.</p>
	 * 
	 * <pre>
	 * ByteUtils.toHexString(null)                   = null
	 * ByteUtils.toHexString([(byte)1, (byte)255])   = "01ff"
	 * </pre>
	 * 
	 * @param bytes unsigned byte's array
	 * @return
	 * @see HexUtils.toString(byte[])
	 */
	public static String toHexString(byte[] bytes) {
		if (bytes == null) {
			return null;
		}
		
		StringBuffer result = new StringBuffer();
		for (byte b : bytes) {
			result.append(Integer.toString((b & 0xF0) >> 4, 16));
			result.append(Integer.toString(b & 0x0F, 16));
		}
		return result.toString();
	}
	
	/**
	 * <p>unsigned byte(바이트) 배열을 16진수 문자열로 바꾼다.</p>
	 * 
	 * <pre>
	 * ByteUtils.toHexString(null, *, *)                   = null
	 * ByteUtils.toHexString([(byte)1, (byte)255], 0, 2)   = "01ff"
	 * ByteUtils.toHexString([(byte)1, (byte)255], 0, 1)   = "01"
	 * ByteUtils.toHexString([(byte)1, (byte)255], 1, 2)   = "ff"
	 * </pre>
	 * 
	 * @param bytes unsigned byte's array
	 * @return
	 * @see HexUtils.toString(byte[])
	 */
	public static String toHexString(byte[] bytes, int offset, int length) {
		if (bytes == null) {
			return null;
		}
		
		StringBuffer result = new StringBuffer();
		for (int i = offset; i < offset + length; i++) {
			result.append(Integer.toString((bytes[i] & 0xF0) >> 4, 16));
			result.append(Integer.toString(bytes[i] & 0x0F, 16));
		}
		return result.toString();
	}
    
	/**
	 * <p>두 배열의 값이 동일한지 비교한다.</p>
	 * 
	 * <pre>
	 * ArrayUtils.equals(null, null)                        = true
	 * ArrayUtils.equals(["one", "two"], ["one", "two"])    = true
	 * ArrayUtils.equals(["one", "two"], ["three", "four"]) = false
	 * </pre>
	 * 
	 * @param array1
	 * @param array2
	 * @return 동일하면 <code>true</code>, 아니면 <code>false</code>
	 */
	public static boolean equals(byte[] array1, byte[] array2) {
		if (array1 == array2) {
			return true;
		}
		
		if (array1 == null || array2 == null) {
			return false;
		}
		
		if (array1.length != array2.length) {
			return false;
		}
		
		for (int i = 0; i < array1.length; i++) {
			if (array1[i] != array2[i]) {
				return false;
			}
		}
		
		return true;
	}	
    public static Key generateKey(String algorithm) throws NoSuchAlgorithmException {  
	 KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);  
	 SecretKey key = keyGenerator.generateKey();  
	 return key;  
	}  


	/**  
	* <P>주어진 데이터로, 해당 알고리즘에 사용할 비밀키(SecretKey)를 생성한다.</P>  
	*   
	* @param algorithm DES/DESede/TripleDES/AES  
	* @param keyData  
	* @return  
	*/ 
	public static Key generateKey(String algorithm, byte[] keyData) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {  
	 String upper = algorithm.toUpperCase();  
	 if ("DES".equals(upper)) {  
	     KeySpec keySpec = new DESKeySpec(keyData);  
	     SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(algorithm);  
	     SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);  
	     return secretKey;  
	 } else if ("DESede".equals(upper) || "TripleDES".equals(upper)) {  
	     KeySpec keySpec = new DESedeKeySpec(keyData);  
	     SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(algorithm);  
	     SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);  
	     return secretKey;  
	 } else {  
	     SecretKeySpec keySpec = new SecretKeySpec(keyData, algorithm);  
	     return keySpec;  
	 }  
	} 
	
	//secret/ensecret 수정
	public static byte[] secret(String str) throws Exception {  
	     byte[] passwordBytes = str.getBytes();  
	     /*키값은 16진수로 지정 해야 한다*/
	     Key key = generateKey("AES", toBytes("68af404b513073584c4b6f22b6c63e6b", 32));
	     String transformation = "AES/CBC/PKCS5Padding";
	     Cipher cipher = Cipher.getInstance(transformation);
	     cipher.init(Cipher.ENCRYPT_MODE, key);
	     byte[] plain = str.getBytes();
	     byte[] encrypt = cipher.doFinal(plain);
	     return encrypt;
	     
//	     byte[] passwordBytes = str.getBytes();  
//	     /*키값은 16진수로 지정 해야 한다*/
//	     Key key = generateKey("DES", toBytes("aa16e6765656eff1", 16));  
//	     String transformation = "DES/ECB/PKCS5Padding";  
//	     Cipher cipher = Cipher.getInstance(transformation);  
//	     cipher.init(Cipher.ENCRYPT_MODE, key);  
//	     byte[] plain = str.getBytes();  
//	     byte[] encrypt = cipher.doFinal(plain);
//	     return encrypt;
	     
	 }  
	public static String ensecret(byte[] str) throws Exception {  
         String return_str = "";   
         /*키값은 16진수로 지정 해야 한다*/
	     Key key = generateKey("AES", toBytes("68af404b513073584c4b6f22b6c63e6b", 32));  
   
//		 String transformation = "DES/ECB/PKCS5Padding";
		 String transformation = "AES/CBC/PKCS5Padding";
	     Cipher cipher = Cipher.getInstance(transformation);  
	     cipher.init(Cipher.DECRYPT_MODE, key);  
	     byte[] decrypt = cipher.doFinal(str);
	     
	     for (int i = 0; i < str.length-1 ; i++) {  
	    	 return_str +=(char)decrypt[i];   
         }  
	     return return_str;
	 } 
	
	
	
	public static ByteBuffer toByteBuffer(int i){
		ByteBuffer buf = ByteBuffer.allocate(Integer.SIZE);
		buf.putInt(i);
		buf.clear();
		return buf;
	}
	public static ByteBuffer toByteBuffer(double i){
		ByteBuffer buf = ByteBuffer.allocate(Double.SIZE);
		buf.putDouble(i);
		buf.clear();
		return buf;
	}
	public static ByteBuffer toByteBuffer(float i){
		ByteBuffer buf = ByteBuffer.allocate(Float.SIZE);
		buf.putFloat(i);
		buf.clear();
		return buf;
	}
	public static ByteBuffer toByteBuffer(long i){
		ByteBuffer buf = ByteBuffer.allocate(Long.SIZE);
		buf.putLong(i);
		buf.clear();
		return buf;
	}
	
	
	public static ByteBuffer toByteBuffer(byte[] bytearray){
		return  ByteBuffer.wrap(bytearray);
	}
	public static ByteBuffer toByteBuffer(String str){
		return  ByteBuffer.wrap(str.getBytes());
	}
	public static byte[] toByteArray(ByteBuffer buffer){
		   int limit 	= buffer.limit();
		   int position = buffer.position();
		   byte[] b = new byte[limit-position];
		   buffer.get(b);
		   buffer.limit(limit);
		   buffer.position(position);
		   return b;
		   //return buffer.array();
		   
	   }
	   public static ByteBuffer copyByteBuffer( ByteBuffer orig ){
	       int pos = orig.position();
	       try
	       {
	           ByteBuffer toReturn;
	           // try to maintain implementation to keep performance
	           if( orig.isDirect() )
	               toReturn = ByteBuffer.allocateDirect(orig.remaining());
	           else
	               toReturn = ByteBuffer.allocate(orig.remaining());

	           toReturn.put(orig);
	           toReturn.order(orig.order());

	           return (ByteBuffer) toReturn.position(0);
	       }
	       finally
	       {
	           orig.position(pos);
	       }
	   }
	   
	   
			
}