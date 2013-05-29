package sha1;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import khh.debug.LogK;
import khh.encryption.base64.Base64;
import khh.encryption.sha1.SHA1;

import sun.misc.BASE64Encoder;

public class SHA1_Test {
	public static void main(String[] args) throws NoSuchAlgorithmException {
		LogK log = LogK.getInstance();
		String key="40Ugaf7isvw4N3MgNzqH2A==";
		String append="258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
		byte[] b = SHA1.encode((key+append).getBytes());
		log.debug("aaa",b);
		
		
		System.out.println( new String(Base64.encode(b) ));
		
		
		
		
		
		
		
//		byte[] txtByte = "테스트테스트".getBytes();
//
//		MessageDigest md = MessageDigest.getInstance("SHA-1");
//		md.update(txtByte);
//		byte[] digest = md.digest();
//
//		BASE64Encoder encoder = new BASE64Encoder();
//
//		String base64 = encoder.encode(digest);
//
//		// should be 20 bytes, 160 bits long
//		System.out.println(digest.length);
//
//		// dump out the hash
//		for (byte b : digest) {
//			System.out.print(Integer.toHexString(b & 0xff));
//		}
//
//		// String result = new String(digest.toCha);
//
//		//System.out.println("\r\n" + toString(digest, 0, digest.length));
//
//		// System.out.println("hexaString : " +
//		// HexString.bufferToHex(md.digest()));

	}

	private static final char[] HEX_DIGITS = "0123456789abcdef".toCharArray();

	public static final String toString(byte[] ba, int offset, int length) {
		char[] buf = new char[length * 2];
		for (int i = 0, j = 0, k; i < length;) {
			k = ba[offset + i++];
			buf[j++] = HEX_DIGITS[(k >>> 4) & 0x0F];
			buf[j++] = HEX_DIGITS[k & 0x0F];
		}
		return new String(buf);
	}
	
	
	
	
	
	
	public static String Password(String data) {
        StringBuffer sb = new StringBuffer();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(data.getBytes("UTF-8"));
            byte[] digestBytes = messageDigest.digest();
 
            String hex = null;
            for (int i = 0; i < digestBytes.length; i++) {
                hex = Integer.toHexString(0xFF & digestBytes[i]);
                if (hex.length() < 2)
                    sb.append("0");
                sb.append(hex);
                }
            String psw = sb.toString();
            }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            }
       return new String(sb);
    }
	
	
	
	
	
	
}
