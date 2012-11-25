/* ---------------------------------------------------------------------
 * @(#)SimpleEncrypt.java  
 * @Creator    Dustin Pak
 * @version    1.0
 * @date       2005-07-20
 * ---------------------------------------------------------------------
 */
package khh.encryption;

/** 
 * <p>단순 암호/복호화 기능을 제공.</p>
 * byte[] bytes = SimpleEncrypt.encrypt(String str);	
 * String str = SimpleEncrypt.decrypt(bytes);
 * @version  1.0
 */
public class SimpleEncrypt {
	
	
	// 대문자, 소문자, 숫자
	private static final byte arrayBytes[] = {
		65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 100,
		75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 
		85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 
		101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 
		111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 
		121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 72,
		56, 57, 43, 47, 61};
	private static final int SIZE = 1024;
	private static final byte ASCII_TEN = 10;
	private static final byte[] KEY = 
		{(byte)'r',(byte)'i',(byte)'c',(byte)'e',(byte)'m',(byte)'a',(byte)'n'};
	
	
	/**
	 * <p>encrypt() 에서 암호화한 byte[] 배열을 인자로 받아 다시 String 형으로 복원.</p>
	 *
	 * @param    암호화된 바이트 배열. 
	 * @return   복원된 문자열.
	 */
	public static String decrypt(byte[] src) {

		String result = null;
		try {
			int i = src.length;
			byte[] pool = arrayChar2Byte(src);
			byte[] aResult0 = dec1(pool,i);
			byte[] aResult1 = dec2(aResult0);
			byte[] aResult2 = dec3(aResult1);
			byte[] aResult3 = dec4(aResult2);

			result =new String(aResult3, "KSC5601");
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}


	/**
	 * <p>인자로 받은 문자열을 암호화 시킴.</p>
	 *
	 * @param    대상 문자열. 
	 * @return   암호화된 바이트 배열.
	 */
	public static byte[] encrypt(String src) {

		byte[] aResult0=null;
		try {
			byte[] pool = src.getBytes("KSC5601");
			byte[] aResult4 = dec4(pool);
			byte[] aResult3 = dec3(aResult4);
			byte[] aResult2 = dec5(aResult3);
			byte[] aResult1 = enc1(aResult2);
			aResult0 = enc2(aResult1);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return aResult0;
	}


	private static final byte[] dec1(byte[] pool, int validLength) {

		int iCharLength = validLength*3/4;
		boolean[] bResult = new boolean[validLength*6];
		byte b;
		for(int l=0 ; l < validLength ; l++) {
			b = pool[l];
			for(int k = 0; k<6;k++) {
				bResult[l*6+k]= (b&0x20) !=0;
				b = (byte)(b << 1);
			}
		}
		byte[] aResult = new byte[iCharLength];
		for(int x=0 ; x < iCharLength ; x++) {
			aResult[x]=0;
			for(int y=0;y<8;y++) {
				if(bResult[8*x+y])
					aResult[x] += (byte)1<<(7-y);
			}
		}
		return aResult;
	}
	
	
	private static final byte[] enc1(byte[] source) {

		byte abyte0[] = new byte[(source.length*4+2)/3];
		int i = 0;
		int j;
		for(j=0; j + 3 <= source.length; j += 3) {
			abyte0[i]=(byte)get1To6(source, j); i++;
			abyte0[i]=(byte)get7To12(source, j); i++;
			abyte0[i]=(byte)get13To18(source, j);	i++;
			abyte0[i]=(byte)get19To24(source, j);	i++;

		}

		switch(source.length-j) {
		case 1: 
			abyte0[i]=(byte)get1To6(source, j); i++;
			abyte0[i]=(byte)get7To12(source, j);
			break;

		case 2: 
			abyte0[i]=(byte)get1To6(source, j); i++;
			abyte0[i]=(byte)get7To12(source, j); i++;
			abyte0[i]=(byte)get13To18(source, j);
			break;
		}
		return abyte0;
	}


	private static final byte[] arrayChar2Byte(byte[] b) {
	 
		byte[] rtn = new byte[b.length];
		for(int l=0;l<rtn.length;l++) {
			rtn[l]=arrayBytes[l%arrayBytes.length];
			for(int i=0;i<arrayBytes.length;i++)
				if(arrayBytes[i]==b[l])rtn[l]=(byte)i;
		}
		return rtn;
	}
	
	
	private static final byte[] enc2(byte[] b) {
	
		byte[] rtn = new byte[b.length];
		for(int l=0;l<rtn.length;l++) {
			rtn[l]=arrayBytes[b[l]];
		}
		return rtn;
		
	}


	private static final int get19To24(byte abyte0[], int i) {

		int rtn =abyte0[i + 2] & 0x3f;
		return rtn;
	}


	private static final int get13To18(byte abyte0[], int i) {

		int rtn =(abyte0[i + 1] & 0xf) << 2 | (abyte0.length>i+2 ? (abyte0[i + 2]& 0xc0):0) >>> 6;
		return rtn;
	}


	private static final int get7To12(byte abyte0[], int i) {

		int rtn = (abyte0[i] & 0x3) << 4 | (abyte0.length>i+1 ? (abyte0[i + 1] & 0xf0):0) >>> 4;
		return rtn;
	}


	private static final int get1To6(byte abyte0[], int i) {

		int rtn = (abyte0[i] & 0xfc) >> 2;
		return rtn;
	}


	private static final byte[] dec2(byte[] src) {

		if(src.length%2 !=0) {
			return null;
		}
		byte[] aByte = new byte[src.length/2];
		String temp;
		for(int i=0;i<src.length/2;i++) {
			temp = new String(src,i*2,2);
			aByte[i] = (byte)Integer.parseInt(temp,16);
		}
		return aByte;
	}
	
	
	private static final byte[] dec5(byte[] src) {

		StringBuffer sb = new StringBuffer(src.length*2);
		for(int i=0;i<src.length;i++)
			sb.append(asc(src[i]));
		return sb.toString().getBytes();
	}
	
	
	private static final String asc(byte src) {

		char[] rtn = new char[2];
		int i = (src<0)?(src+0x100):src;
		int[] tmp = new int[2];
		tmp[0]= i/0x10; tmp[1]=i%0x10;
		return Integer.toString(tmp[0],16)+Integer.toString(tmp[1],16);
	}
	
	
	private static final byte[] dec4(byte[] src) {

		byte[] rtn = new byte[src.length];
		byte tmp = 0;
		for(int i=0;i<src.length;i++) {
			rtn[i] = (byte)((src[i] & 0xf0)/16 + (src[i] &0x0f)*16);
		}
		return rtn;
	}
	
	
	private static final byte[] dec3(byte[] src) {

		return dec3(src,KEY);
	}
	
	
	private static final byte[] dec3(byte[] src, byte[] key) {

		byte[] rtn = new byte[src.length];
		for(int i=0;i<rtn.length;i++)
			rtn[i]=(byte)(src[i]^key[i % key.length]);
		return rtn;
	}
	
}