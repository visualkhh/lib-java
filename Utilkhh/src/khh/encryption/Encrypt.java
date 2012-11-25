package khh.encryption;

public  class   Encrypt 
{
    public  int m_nC1Key    =   7410200;
    public  int m_nC2Key    =   1233700;

    public  Encrypt()   
    {
    }

    public  Encrypt(int nKey1, int nKey2)
    {
        m_nC1Key    =   nKey1;
        m_nC2Key    =   nKey2;
    }


    public  void    setKey(int nKey1,int nKey2)
    {
        m_nC1Key    =   nKey1;
        m_nC2Key    =   nKey2;
        
    }

    public  String  Encode(String szSrc,    int Key, int nLoop)
    {
        return DoEncrypt( Encode(szSrc.getBytes()), Key, nLoop);
        //return DoEncrypt( szSrc.getBytes(), Key, nLoop);
    }
    public  String  Decode(String szSrc,    int Key, int nLoop)
    {
        return new String(DoDecode( Decrypt(szSrc.getBytes(), Key, nLoop).getBytes() ));
        //return Decrypt(szSrc.getBytes(), Key, nLoop);
    }

    public  String  DoEncrypt(byte  pSrc[], int Key, int nLoop)
    {
        int nLen    =   pSrc.length;

        byte    pBuffDst[]  =   new byte[nLen];
        byte    pBuffSrc[]  =   new byte[nLen];
        
        for(int i=0; i<nLen; i++) pBuffSrc[i] = pSrc[i];

        for(int k=0; k<nLoop; k++) {
        
            DoEncrypt(pBuffDst, pBuffSrc, nLen, Key);

            if(k< (nLoop-1) )
                for(int i=0; i<nLen; i++) pBuffSrc[i] = pBuffDst[i];
        }
        
        return  ValueToHex(pBuffDst, nLen);
    }

    public  String  Decrypt(byte    pSrc[], int Key, int nLoop)
    {
        int nLen    =   pSrc.length / 2;

        byte    pBuffDst[]  =   new byte[nLen];
        byte    pBuffSrc[]  =   new byte[nLen];
        
        HexToValue(pBuffSrc, pSrc);

        for(int k=0; k<nLoop; k++) {

            DoDecrypt(pBuffDst, pBuffSrc, nLen, Key);

            if(k < (nLoop-1) )
                for(int i=0; i<nLen; i++) pBuffSrc[i] = pBuffDst[i];
        }

        String  sRet    =   new String(pBuffDst);
        return  sRet;
    }


    /*****************************************************************************/
    //
    //
    //
    /*****************************************************************************/

    public  byte[]  Encode(byte bSrc[])
    {
        int     nSrcLen =   bSrc.length;
        byte    bRet[]  =   new byte[nSrcLen];
        for (int i=0; i<nSrcLen; i+=2)
        {
            bRet[nSrcLen-i-1] = bSrc[i];
        }
        for (int i=1; i<nSrcLen; i+=2)
        {
            bRet[nSrcLen-i-1] = bSrc[i];
        }
        return bRet;
    }
    
    public  byte[]  DoDecode(byte bSrc[])
    {
        int     nSrcLen =   bSrc.length;
        byte    bRet[]  =   new byte[nSrcLen];
        for (int    i=1;i<nSrcLen;i+=2)
        {
            bRet[nSrcLen-i-1] = bSrc[i];
        }
        for (int    i=0;i<nSrcLen;i+=2)
        {
            bRet[nSrcLen-i-1] = bSrc[i];
        }
        return bRet;
    }

    public  void    DoEncrypt(byte pDst[], byte pSrc[], int nLen, int Key)
    {
        int nKey2   =   Key;
    
        for(int i   =   0   ;   i   <   nLen;   i++)
        {
            int nSrc = (pSrc[i] & 0xff);
            int nXor    =   nKey2   /   ((int)256);

            pDst[i] =   (byte)(nSrc^nXor);
            nKey2   =   ( ByteToInt(pDst[i]) + nKey2) * m_nC1Key    +   m_nC2Key;
        }
    }


    public  void    DoDecrypt(byte pDst[], byte pSrc[], int nLen, int   Key)
    {
        int nKey2   =   Key;
    
        for(int i   =   0   ;   i   <   nLen    ;   i++)
        {
            int nSrc = (int)(pSrc[i] & 0xff);
            int nXor    =   nKey2 / ((int)256);

            pDst[i] =   (byte)(nSrc^nXor);
            
            nKey2   =   ( nSrc + nKey2) * m_nC1Key  +   m_nC2Key;
        }
    }


    /*****************************************************************************/
    //
    //
    //
    /*****************************************************************************/
        
    public  byte    HexaByte(int    nVal)
    {
        byte    []  szHexaByte  =   {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        if  (nVal   >   15)
        {
            nVal    =   0;
        }
        return  szHexaByte[nVal];
    }
        
    public  String  ValueToHex(byte pSrc[], int nLen)
    {
        if  (pSrc   ==  null)
            return  null;
        
        byte    pBuf[]  =   new byte[nLen*2];
        
        for(int i=0;i<nLen;i++)
        {
            //int nval = pSrc[i] & 0xff;
            int nval = ByteToInt(pSrc[i]);
            
            pBuf[(i*2)+0]   =   HexaByte(nval/16);
            pBuf[(i*2)+1]   =   HexaByte(nval%16);
        }
        
        return  new String(pBuf);
    }
        
    public  void HexToValue(byte[] pRst, byte[] szSrc)
    {
        int nLen    =   szSrc.length / 2;
        char    szChar[]    =   new char[2];
        
        for(int i   =   0   ;   i   <   nLen;   i++)
        {
            szChar[0]   =   (char)szSrc[i*2];
            szChar[1]   =   (char)szSrc[i*2+1];
            byte    pValue  =   (byte)HexToDecimal(szChar);
            
            pRst[i] =   (byte)(pValue & 0xff);
        }
    }
        
    public  int HexToDecimal(char[] szSrc)
    {
        int nRet    =   0;
        int nLen    =   szSrc.length;
        for (int    i=0;i<nLen;i++)
        {
            byte    cChar   =   (byte)szSrc[i];
            nRet    =   nRet    *   16;
            nRet    +=  HexToDecimal(cChar);
        }
        return  nRet;
    }
        
    public  int HexToDecimal(byte   cChar)
    {
        if  (cChar  ==  'A' ||  cChar   ==  'a')
            return  10;
        if  (cChar  ==  'B' ||  cChar   ==  'b')
            return  11;
        if  (cChar  ==  'C' ||  cChar   ==  'c')
            return  12;
        if  (cChar  ==  'D' ||  cChar   ==  'd')
            return  13;
        if  (cChar  ==  'E' ||  cChar   ==  'e')
            return  14;
        if  (cChar  ==  'F' ||  cChar   ==  'f')
            return  15;
        return  (cChar-48);
    }
        
    public int ByteToInt(byte b) {
        int val = b;
        if(b < 0) {
            val += 256;
            //val = b& 0x80;
            //val += b & 0x7f;
        }
        return val;
    }
        
}
