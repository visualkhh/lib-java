package encryption;

import com.kdt.util.encryption.Encrypt;

public class encryptiontest
{
	public static void main(String[] args)
	{
		int nKey1=434;
		int nKey2=542;
		Encrypt e = new Encrypt(654564,6346);
		
		
		
		String r = e.Encode("김현하",588,52);
		System.out.println(r);
		System.out.println(e.Decode(r,588, 52));
	}
}
