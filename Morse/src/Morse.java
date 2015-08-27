import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import khh.string.util.StringUtil;


public class Morse {


	
	
	HashMap<String, String> em = null;
	HashMap<String, String> me = null;
	
    String[] alpha = { 
    		"a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
            "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
            "u", "v", "w", "x", "y", "z", "1", "2", "3", "4",
            "5", "6", "7", "8", "9", "0", " " 
            };
    String[] morse = {
    		".-",		"-...",		"-.-.", 	"-..", 		".", 		"..-.",		"--.",		"....",		"..",		".---",
    		"-.-",		".-..",		"--", 		"-.", 		"---",		".--.",		"--.-",		".-.",		"...",		"-",
    		"..-", 		"...-", 	".--", 		"-..-",		"-.--",		"--..",		".----",	"..---",	"...--",	"....-",
    		".....",	"-....", 	"--...", 	"---..",	"----.",	"-----",	"|" };
//	
	public Morse() {
		em = new HashMap<String, String>();
		me = new HashMap<String, String>();
		for (int i = 0; i < alpha.length; i++) {
			em.put(alpha[i], morse[i]);
			me.put(morse[i], alpha[i]);
		}
	}
    
    
	public String toMorse(String engStr){
		String engStru = engStr.toLowerCase();
		StringBuffer b = new StringBuffer();
		for (int i = 0; i < engStru.length(); i++) {
			b.append(em.get(engStru.substring(i, i+1)));
			if(i+1 < engStru.length()){
				b.append("|");
			}
		}
		return b.toString();
	}

	public String toEnglish(String morseStr){
		StringBuffer b = new StringBuffer();
		String[] morseArr = morseStr.split("\\|");
		for (int i = 0; i < morseArr.length; i++) {
			b.append(me.get(morseArr[i]));
		}
		return b.toString();
	}
	
	
	public String toMorseBybyte(File file) throws IOException{
		InputStream input = new FileInputStream(file);
		byte[] buf = new byte[(int)file.length()];
		input.read(buf);
		input.close();
		return toMorseBybyte(buf);
	}
	public String toMorseBybyte(byte[] byteArr){
		StringBuffer b = new StringBuffer();
		for (int i = 0; i < byteArr.length; i++) {
			b.append(String.format("%X ", byteArr[i]));
		}
//		return b.toString();
		return toMorse(b.toString());
	}
	public byte[] morseToByteArray(String morseStr){
		int dcnt	= StringUtil.getMatchingCount("\\.", morseStr);
		int ecnt	= StringUtil.getMatchingCount("\\|", morseStr);
		int lcnt	= StringUtil.getMatchingCount("-", morseStr);
		int cnt 	= morseStr.length();
		int hs		= 1000; //10000 / 1 초
		int sc		= (dcnt*hs) + (ecnt*hs*3) + (cnt*hs) + (lcnt*(hs*2));
		
		byte[] pcm_data = new byte[sc];
		double L1 = 44100.0/240.0;
		double L2 = 44100.0/240.0;
		  
		  //System.out.println(sc+" "+" "+pcm_data.length+"  "+dcnt+" "+ecnt+" "+lcnt+" " );
		  int startIndex=0;
		  for (int i = 0; i < morseStr.length(); i++) {
			  String s = morseStr.substring(i, i+1);
			  int width = s.equals("-") ? hs*2 : hs;
			  //System.out.println(width);
			  
			  for(int j = startIndex; j < startIndex + width; j++) {
				  //System.out.println(j);
				  if(s.equals(".") || s.equals("-")){
					  pcm_data[j] = (byte)(1000 * Math.sin((j/L1) * Math.PI * 2));
					  //System.out.println((byte)pcm_data[j]);
					  //pcm_data[j]=(byte) 225;//(byte)0xff;
					  //pcm_data[j] += (byte)(100*Math.sin((j/L2)*Math.PI*2));
					  //pcm_data[j] = (byte) (1024 * Math.cos((double)Math.PI *L1)* Math.sin(Math.PI * sc)*100);
				  }else{
					  break;
				  }
			  }
			  
			  startIndex += width;
			  startIndex += s.equals("|") ? hs*3 : hs;
//			  startIndex +=hs*2;
		  }
		  
		  return pcm_data;
	}
	//신호간격은1타점,   문자간격은 3타점 , 단어간격은 7타점,  공백은 허용하지않아요
	public void writeWAV(String morseStr, File file){
		byte[] pcm_data = morseToByteArray(morseStr);
		AudioFormat frmt = getAudioFormat();
		AudioInputStream ais = new AudioInputStream(
		    new ByteArrayInputStream(pcm_data), frmt, pcm_data.length / frmt.getFrameSize()
		  );
		 
		  try {
		    AudioSystem.write(ais, AudioFileFormat.Type.WAVE, file);
		  } 
		  catch(Exception e) {
		    e.printStackTrace();
		  }
	}
	
	public AudioFormat getAudioFormat(){
		int sampleRate = 44100;
		int sampleSizeInBits = 8;
		int channeles = 1;
		boolean signed = true;
		boolean bigEndian = true;
		return new AudioFormat(sampleRate, sampleSizeInBits, channeles, signed, bigEndian);	
	}
	
	
	public void outSound(String morseStr) throws LineUnavailableException {
		byte[] pcm_data = morseToByteArray(morseStr);
		
		AudioFormat format = getAudioFormat();
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
		SourceDataLine line = null;
		line = (SourceDataLine) AudioSystem.getLine(info);
		line.open(format);
		line.start();
		line.write(pcm_data, 0, pcm_data.length);
		line.drain();
		line.close();
	}
	
	
	public static void main(String[] args) throws Exception {
		Morse m = new Morse();
		
		String msg = m.toMorse("goodjobman");
		System.out.println(msg);
		
		m.outSound(msg);
		
		msg = m.toEnglish("...-|..|...|..-|.-|.-..|-.-|....|....");
		System.out.println(msg);
		
		

//		m.writeWAV(a,new File("c:\\test.wav"));
	}

}
