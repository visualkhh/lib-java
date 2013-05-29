package khh.encryption.sha1;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA1 {
	
	public static byte[] encode(String txtByte) throws NoSuchAlgorithmException{
		return encode(txtByte.getBytes());
	}
	public static byte[] encode(byte[] txtByte) throws NoSuchAlgorithmException{
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		md.update(txtByte);
		byte[] digest = md.digest();
		return digest;
	}
}
