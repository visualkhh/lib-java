package khh.encryption;

import xcipher.XCipher;

public class CryptUtil{
	
	/* 암호화
	 * @param  암호화될 문자열
	 * @return 암호화된 문자열 
	 */
	public static String encrypt(String encStr) {
		String rtn = "";
		if("".equals((encStr))) {
			return rtn;
		}
        
        rtn = XCipher.i_Encrypt(encStr);
        return rtn;
	}

	/* 복호화
	 * @param  복호화될 문자열
	 * @return 복호화된 문자열 
	 */
	public static String decrypt(String decStr) {
		String rtn = "";
		if("".equals((decStr))) {
			return rtn;
		}
        
        rtn = XCipher.i_Decrypt(decStr);
        return rtn;
	}
}