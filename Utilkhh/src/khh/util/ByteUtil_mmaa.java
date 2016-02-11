
package khh.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * SocketAgent.java
 * @author		: �����
 * @version 	: 1.0
 * @since		: JDK1.4.2
 * ��  ��  �� 	: 2004. 01. 11 By Lee WonYoung
 * ����  �̷� 	: 2009. 09. 23
 * ��      Ÿ 	: 
 */
public class ByteUtil_mmaa
{

    private ByteUtil_mmaa()
    {
    }

    public static final byte[] short2byte(short s)
    {
        byte dest[] = new byte[2];
        dest[1] = (byte)(s & 0xff);
        dest[0] = (byte)(s >>> 8 & 0xff);
        return dest;
    }

    public static final byte[] int2byte(int i)
    {
        byte dest[] = new byte[4];
        dest[3] = (byte)(i & 0xff);
        dest[2] = (byte)(i >>> 8 & 0xff);
        dest[1] = (byte)(i >>> 16 & 0xff);
        dest[0] = (byte)(i >>> 24 & 0xff);
        return dest;
    }

    public static final byte[] long2byte(long l)
    {
        byte dest[] = new byte[8];
        dest[7] = (byte)(int)(l & 255L);
        dest[6] = (byte)(int)(l >>> 8 & 255L);
        dest[5] = (byte)(int)(l >>> 16 & 255L);
        dest[4] = (byte)(int)(l >>> 24 & 255L);
        dest[3] = (byte)(int)(l >>> 32 & 255L);
        dest[2] = (byte)(int)(l >>> 40 & 255L);
        dest[1] = (byte)(int)(l >>> 48 & 255L);
        dest[0] = (byte)(int)(l >>> 56 & 255L);
        return dest;
    }

    public static final byte[] float2byte(float f)
    {
        byte dest[] = new byte[4];
        return setfloat(dest,0,f);
    }
    public static final byte[] double2byte(double d)
    {
        byte dest[] = new byte[8];
        return setdouble(dest,0,d);
    }

    public static final byte getbyte(byte src[], int offset)
    {
        return src[offset];
    }

    public static final byte[] getbytes(byte src[], int offset, int length)
    {
        byte dest[] = new byte[length];
        System.arraycopy(src, offset, dest, 0, length);
        return dest;
    }

    public static final short getshort(byte src[], int offset)
    {
        return (short)((src[offset] & 0xff) << 8 | src[offset + 1] & 0xff);
    }

    public static final int getint(byte src[], int offset)
    {
        return 
            (src[offset] & 0xff) << 24 | 
            (src[offset + 1] & 0xff) << 16 | 
            (src[offset + 2] & 0xff) << 8 | 
            src[offset + 3] & 0xff;
    }

    public static final long getlong(byte src[], int offset)
    {
        return 
            (long)getint(src, offset) << 32 | 
            (long)getint(src, offset + 4) & 0xffffffffL;
    }

    public static final float getfloat(byte src[], int offset)
    {
        return Float.intBitsToFloat(getint(src, offset));
    }

    public static final double getdouble(byte src[], int offset)
    {
        return Double.longBitsToDouble(getlong(src, offset));
    }

    public static final byte[] setbyte(byte dest[], int offset, byte b)
    {
        dest[offset] = b;
        return dest;
    }

    public static final byte[] setbytes(byte dest[], int offset, byte src[])
    {
        System.arraycopy(src, 0, dest, offset, src.length);
        return dest;
    }

    public static final byte[] setbytes(byte dest[], int offset, byte src[], int len)
    {
        System.arraycopy(src, 0, dest, offset, len);
        return dest;
    }

    public static final byte[] setshort(byte dest[], int offset, short s)
    {
        dest[offset] = (byte)(s >>> 8 & 0xff);
        dest[offset + 1] = (byte)(s & 0xff);
        return dest;
    }

    public static final byte[] setint(byte dest[], int offset, int i)
    {
        dest[offset] = (byte)(i >>> 24 & 0xff);
        dest[offset + 1] = (byte)(i >>> 16 & 0xff);
        dest[offset + 2] = (byte)(i >>> 8 & 0xff);
        dest[offset + 3] = (byte)(i & 0xff);
        return dest;
    }

    public static final byte[] setlong(byte dest[], int offset, long l)
    {
        setint(dest, offset, (int)(l >>> 32));
        setint(dest, offset + 4, (int)(l & 0xffffffffL));
        return dest;
    }

    public static final byte[] setfloat(byte dest[], int offset, float f)
    {
        return setint(dest, offset, Float.floatToIntBits(f));
    }

    public static final byte[] setdouble(byte dest[], int offset, double d)
    {
        return setlong(dest, offset, Double.doubleToLongBits(d));
    }

    public static final boolean isEquals(byte b[], String s)
    {
        if(b == null || s == null)
            return false;
        int slen = s.length();
        if(b.length != slen)
            return false;
        for(int i = slen; i-- > 0;)
            if(b[i] != s.charAt(i))
                return false;

        return true;
    }

    public static final boolean isEquals(byte a[], byte b[])
    {
        if(a == null || b == null)
            return false;
        if(a.length != b.length)
            return false;
        for(int i = a.length; i-- > 0;)
            if(a[i] != b[i])
                return false;

        return true;
    }
    
    public static final byte[] getbytes(String src)
    {
    	byte dest[] = null;
        if(src == null) src = "";
        dest = src.getBytes();
        return dest;
    }
   
    /**
     * �ڹٿ� C���� ��ſ� �̿��Ѵ�
     * 	
     * @param 
     * @return byte[]
     * @throws
     */
	public static byte[] int2Byte( int i ) {
		byte[] dest = new byte[4];
		dest[0] = (byte)(i & 0xff);
		dest[1] = (byte)((i>>8) & 0xff);
		dest[2] = (byte)((i>>16) & 0xff);
		dest[3] = (byte)((i>>24) & 0xff);
		return dest;
	}

	/**
	 * �ڹٿ� C���� ��ſ� �̿��Ѵ�
	 * 	
	 * @param 
	 * @return int
	 * @throws
	 */
	public static int getInt( byte[] src, int offset ) {
		return ((src[offset+3]&0xff) << 24) | 
				((src[offset+2]&0xff) << 16) |
				 ((src[offset+1]&0xff) << 8) | 
					(src[offset]&0xff) ;
	}   

	/**
	 * �ڹٿ� C���� ��ſ� �̿��Ѵ�
	 * 	
	 * @param 
	 * @return byte[]
	 * @throws
	 */
	public static byte[] parseStringBytes(String data, int len) {
		byte bData[] = new byte[len];
		byte realData[] = data.getBytes();

		int dataLength = data.length();
		for(int j=0; j < dataLength; j++) {
			bData[j] = realData[j];
		}

		int offset = len - dataLength;

		if(offset > 0){
			for (int i=0; i < offset; i++) {
				bData[i+dataLength] = 0x00;
			}
		}
		return bData;
	}

    /**
     * 
     * 	
     * @param singFlag : + ǥ������, false�̸� +ǥ�� �ȵ�, true�̸� +ǥ�õ�
     * @param type : 1�̸� ������, 0�̸� ������
     * @param fillType : 0�̸� �� ��� ' 'ä��, 1�̸� �հ���� 0���� ä��, 2�̸� �տ� ' ' ä��
     * @return byte[]
     * @throws
     */
    public static final byte[] setbytes(byte dest[], int offset, byte src[], int len, boolean singFlag, int type, int fillType)
    {
    	if(type == 1) 
    	{
    		src = makeHostNumeric(src, len, false, 0);
    	}
    	
    	src = fill(0, src, len, fillType);
    	
        System.arraycopy(src, 0, dest, offset, len);
        return dest;
    }
    
    public static byte[] makeHostNumeric( byte input[], int totalSize, boolean signFlag, int vSize)
    {
        for(int i = 0; i < input.length; i++)
            if(input[i] == 32)
                input[i] = 48;

        String st = new String(input);
        StringBuffer strbuf = new StringBuffer(st);
        int total = st.length();
        int psign = st.indexOf('+');
        int msign = st.indexOf('-');
        int dot = st.indexOf('.');
        if(dot >= 0)
        {
            int dotSize = st.length() - dot - 1;
            if(vSize > 0)
            {
                if(vSize >= dotSize)
                {
                    strbuf.deleteCharAt(dot);
                    for(int i = 0; i < vSize - dotSize; i++)
                        strbuf.append('0');

                } else
                {
                    strbuf.delete(dot + vSize + 1, total);
                    strbuf.deleteCharAt(dot);
                }
            } else
            {
                strbuf.delete(dot, total);
            }
        } else
        {
            for(int i = 0; i < vSize; i++)
                strbuf.append('0');

        }
        if(signFlag)
        {
            if(msign >= 0)
            {
                strbuf.setCharAt(msign, '0');
                strbuf.insert(0, '-');
            } else
            if(psign >= 0)
            {
                strbuf.setCharAt(psign, '0');
                strbuf.insert(0, '+');
            } else
            {
                strbuf.insert(0, '+');
            }
        } else
        if(psign < 0 && msign < 0 && strbuf.length() < totalSize)
            strbuf.insert(0, '0');
        int start = 1;
        int j = strbuf.length();
        if(j < 1)
            start = 0;
        for(int i = 0; i < totalSize - j; i++)
            strbuf.insert(start, '0');

        if(!signFlag && strbuf.charAt(0) == '+')
            strbuf.setCharAt(0, '0');
        if(totalSize < j)
        {
            int diff = j - totalSize;
            if(signFlag)
                strbuf.delete(1, diff + 1);
            else
                strbuf.delete(0, diff);
        }
        return strbuf.toString().getBytes();
    }

    
    public static byte[] fill(byte input[], int size, int type)
    {
        return fill(0, input, size, type);
    }

    public static byte[] fill(int targetCode, byte input[], int size, int type)
    {
        int c = -1;
        if(type == 1)
        {
            if(targetCode == 1)
                c = 240;
            else
                c = 48;
        } else{
        	if(type == 6 || type == 5)
                c = 0;
            else{
            	if(targetCode == 1)
                    c = 64;
                else
                    c = 32;
            }            
        }
        
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try
        {
            int len;
            if(input == null)
                len = 0;
            else
                len = input.length;
            if(type == 1 || type == 6 || type == 5)
            {
                for(; len < size; len++)
                    bout.write(c);
                if(input != null)
                    bout.write(input);
            }else if(type == 2){
            	for(; len < size; len++)
                    bout.write(c);
            	if(input != null)
                    bout.write(input);                
            }else
            {
                if(input != null)
                    bout.write(input);
                for(; len < size; len++)
                    bout.write(c);
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        byte b[] = bout.toByteArray();
        return b;
    }
    
}
