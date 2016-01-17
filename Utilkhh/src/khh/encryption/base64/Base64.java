// Copyright 2003-2010 Christian d'Heureuse, Inventec Informatik AG, Zurich, Switzerland
// www.source-code.biz, www.inventec.ch/chdh
//
// This module is multi-licensed and may be used under the terms
// of any of the following licenses:
//
//  EPL, Eclipse Public License, V1.0 or later, http://www.eclipse.org/legal
//  LGPL, GNU Lesser General Public License, V2.1 or later, http://www.gnu.org/licenses/lgpl.html
//  GPL, GNU General Public License, V2 or later, http://www.gnu.org/licenses/gpl.html
//  AL, Apache License, V2.0 or later, http://www.apache.org/licenses
//  BSD, BSD License, http://www.opensource.org/licenses/bsd-license.php
//
// Please contact the author if you need another license.
// This module is provided "as is", without warranties of any kind.

package khh.encryption.base64;

import java.io.File;
import java.io.IOException;

import khh.file.util.FileUtil;

/**
* A Base64 encoder/decoder.
*
* <p>
* This class is used to encode and decode data in Base64 format as described in RFC 1521.
*
* <p>
* Project home page: <a href="http://www.source-code.biz/base64coder/java/">www.source-code.biz/base64coder/java</a><br>
* Author: Christian d'Heureuse, Inventec Informatik AG, Zurich, Switzerland<br>
* Multi-licensed: EPL / LGPL / GPL / AL / BSD.
*/
public class Base64 {

// The line separator string of the operating system.
private static final String systemLineSeparator = System.getProperty("line.separator");

// Mapping table from 6-bit nibbles to Base64 characters.
private static char[]    map1 = new char[64];
   static {
      int i=0;
      for (char c='A'; c<='Z'; c++) map1[i++] = c;
      for (char c='a'; c<='z'; c++) map1[i++] = c;
      for (char c='0'; c<='9'; c++) map1[i++] = c;
      map1[i++] = '+'; map1[i++] = '/'; }

// Mapping table from Base64 characters to 6-bit nibbles.
private static byte[]    map2 = new byte[128];
   static {
      for (int i=0; i<map2.length; i++) map2[i] = -1;
      for (int i=0; i<64; i++) map2[map1[i]] = (byte)i; }

/**
* Encodes a string into Base64 format.
* No blanks or line breaks are inserted.
* @param s  A String to be encoded.
* @return   A String containing the Base64 encoded data.
*/
public static String encodeString (String s) {
   return new String(encode(s.getBytes())); }

/**
* Encodes a byte array into Base 64 format and breaks the output into lines of 76 characters.
* This method is compatible with <code>sun.misc.BASE64Encoder.encodeBuffer(byte[])</code>.
* @param in  An array containing the data bytes to be encoded.
* @return    A String containing the Base64 encoded data, broken into lines.
*/
public static String encodeLines (byte[] in) {
   return encodeLines(in, 0, in.length, 76, systemLineSeparator); }

/**
* Encodes a byte array into Base 64 format and breaks the output into lines.
* @param in            An array containing the data bytes to be encoded.
* @param iOff          Offset of the first byte in <code>in</code> to be processed.
* @param iLen          Number of bytes to be processed in <code>in</code>, starting at <code>iOff</code>.
* @param lineLen       Line length for the output data. Should be a multiple of 4.
* @param lineSeparator The line separator to be used to separate the output lines.
* @return              A String containing the Base64 encoded data, broken into lines.
*/
public static String encodeLines (byte[] in, int iOff, int iLen, int lineLen, String lineSeparator) {
   int blockLen = (lineLen*3) / 4;
   if (blockLen <= 0) throw new IllegalArgumentException();
   int lines = (iLen+blockLen-1) / blockLen;
   int bufLen = ((iLen+2)/3)*4 + lines*lineSeparator.length();
   StringBuilder buf = new StringBuilder(bufLen);
   int ip = 0;
   while (ip < iLen) {
      int l = Math.min(iLen-ip, blockLen);
      buf.append (encode(in, iOff+ip, l));
      buf.append (lineSeparator);
      ip += l; }
   return buf.toString(); }

/**
* Encodes a byte array into Base64 format.
* No blanks or line breaks are inserted in the output.
* @param in  An array containing the data bytes to be encoded.
* @return    A character array containing the Base64 encoded data.
*/
public static char[] encode (byte[] in) {
   return encode(in, 0, in.length); }

/**
* Encodes a byte array into Base64 format.
* No blanks or line breaks are inserted in the output.
* @param in    An array containing the data bytes to be encoded.
* @param iLen  Number of bytes to process in <code>in</code>.
* @return      A character array containing the Base64 encoded data.
*/
public static char[] encode (byte[] in, int iLen) {
   return encode(in, 0, iLen); }

/**
* Encodes a byte array into Base64 format.
* No blanks or line breaks are inserted in the output.
* @param in    An array containing the data bytes to be encoded.
* @param iOff  Offset of the first byte in <code>in</code> to be processed.
* @param iLen  Number of bytes to process in <code>in</code>, starting at <code>iOff</code>.
* @return      A character array containing the Base64 encoded data.
*/
public static char[] encode (byte[] in, int iOff, int iLen) {
   int oDataLen = (iLen*4+2)/3;       // output length without padding
   int oLen = ((iLen+2)/3)*4;         // output length including padding
   char[] out = new char[oLen];
   int ip = iOff;
   int iEnd = iOff + iLen;
   int op = 0;
   while (ip < iEnd) {
      int i0 = in[ip++] & 0xff;
      int i1 = ip < iEnd ? in[ip++] & 0xff : 0;
      int i2 = ip < iEnd ? in[ip++] & 0xff : 0;
      int o0 = i0 >>> 2;
      int o1 = ((i0 &   3) << 4) | (i1 >>> 4);
      int o2 = ((i1 & 0xf) << 2) | (i2 >>> 6);
      int o3 = i2 & 0x3F;
      out[op++] = map1[o0];
      out[op++] = map1[o1];
      out[op] = op < oDataLen ? map1[o2] : '='; op++;
      out[op] = op < oDataLen ? map1[o3] : '='; op++; }
   return out; }

/**
* Decodes a string from Base64 format.
* No blanks or line breaks are allowed within the Base64 encoded input data.
* @param s  A Base64 String to be decoded.
* @return   A String containing the decoded data.
* @throws   IllegalArgumentException If the input is not valid Base64 encoded data.
*/
public static String decodeString (String s) {
   return new String(decode(s)); }

/**
* Decodes a byte array from Base64 format and ignores line separators, tabs and blanks.
* CR, LF, Tab and Space characters are ignored in the input data.
* This method is compatible with <code>sun.misc.BASE64Decoder.decodeBuffer(String)</code>.
* @param s  A Base64 String to be decoded.
* @return   An array containing the decoded data bytes.
* @throws   IllegalArgumentException If the input is not valid Base64 encoded data.
*/
public static byte[] decodeLines (String s) {
   char[] buf = new char[s.length()];
   int p = 0;
   for (int ip = 0; ip < s.length(); ip++) {
      char c = s.charAt(ip);
      if (c != ' ' && c != '\r' && c != '\n' && c != '\t')
         buf[p++] = c; }
   return decode(buf, 0, p); }

/**
* Decodes a byte array from Base64 format.
* No blanks or line breaks are allowed within the Base64 encoded input data.
* @param s  A Base64 String to be decoded.
* @return   An array containing the decoded data bytes.
* @throws   IllegalArgumentException If the input is not valid Base64 encoded data.
*/
public static byte[] decode (String s) {
   return decode(s.toCharArray()); }

/**
* Decodes a byte array from Base64 format.
* No blanks or line breaks are allowed within the Base64 encoded input data.
* @param in  A character array containing the Base64 encoded data.
* @return    An array containing the decoded data bytes.
* @throws    IllegalArgumentException If the input is not valid Base64 encoded data.
*/
public static byte[] decode (char[] in) {
   return decode(in, 0, in.length); }

/**
* Decodes a byte array from Base64 format.
* No blanks or line breaks are allowed within the Base64 encoded input data.
* @param in    A character array containing the Base64 encoded data.
* @param iOff  Offset of the first character in <code>in</code> to be processed.
* @param iLen  Number of characters to process in <code>in</code>, starting at <code>iOff</code>.
* @return      An array containing the decoded data bytes.
* @throws      IllegalArgumentException If the input is not valid Base64 encoded data.
*/
public static byte[] decode (char[] in, int iOff, int iLen) {
   if (iLen%4 != 0) throw new IllegalArgumentException ("Length of Base64 encoded input string is not a multiple of 4.");
   while (iLen > 0 && in[iOff+iLen-1] == '=') iLen--;
   int oLen = (iLen*3) / 4;
   byte[] out = new byte[oLen];
   int ip = iOff;
   int iEnd = iOff + iLen;
   int op = 0;
   while (ip < iEnd) {
      int i0 = in[ip++];
      int i1 = in[ip++];
      int i2 = ip < iEnd ? in[ip++] : 'A';
      int i3 = ip < iEnd ? in[ip++] : 'A';
      if (i0 > 127 || i1 > 127 || i2 > 127 || i3 > 127)
         throw new IllegalArgumentException ("Illegal character in Base64 encoded data.");
      int b0 = map2[i0];
      int b1 = map2[i1];
      int b2 = map2[i2];
      int b3 = map2[i3];
      if (b0 < 0 || b1 < 0 || b2 < 0 || b3 < 0)
         throw new IllegalArgumentException ("Illegal character in Base64 encoded data.");
      int o0 = ( b0       <<2) | (b1>>>4);
      int o1 = ((b1 & 0xf)<<4) | (b2>>>2);
      int o2 = ((b2 &   3)<<6) |  b3;
      out[op++] = (byte)o0;
      if (op<oLen) out[op++] = (byte)o1;
      if (op<oLen) out[op++] = (byte)o2; }
   return out; }

// Dummy constructor.
private Base64() {}

public static void main(String[] args) throws IOException {
//	byte[] data = Base64.decode("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");
//	FileUtil.writeFile(new File("c:\\bbb.png"), data);
	byte[] data = FileUtil.readFileToByte(new File("c:\\vv.png"));
	char[] s = Base64.encode(data);
	System.out.println(s);
	
}
} // end class Base64Coder


