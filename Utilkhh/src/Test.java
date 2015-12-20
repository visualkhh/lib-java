import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Test {
	public static byte[] bang() {
	    byte[] buf = new byte[8050];
	    Random r = new Random();
	    boolean silence = true;
	    for (int i = 0; i < 8000; i++) {
	        while (r.nextInt() % 10 != 0) {
	            buf[i] =
	                    silence ? 0
	                    : (byte) Math.abs(r.nextInt()
	                    % (int) (1. + 63. * (1. + Math.cos(((double) i)
	                    * Math.PI / 8000.))));
	            i++;
	        }
	        silence = !silence;
	    }
	    return buf;
	}


	private static void save(byte[] data, String filename) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
	    InputStream byteArray = new ByteArrayInputStream(data);
	    AudioInputStream ais = new AudioInputStream(byteArray, getAudioFormat(), (long) data.length);
	    AudioSystem.write(ais, AudioFileFormat.Type.WAVE, new File(filename));
	}

	private static AudioFormat getAudioFormat() {
	    return new AudioFormat(
	            8000f, // sampleRate
	            8, // sampleSizeInBits
	            1, // channels
	            true, // signed
	            false);      // bigEndian  
	}

	public static void main(String[] args) throws Exception {
	    byte[] data  = bang();
	    save(data, "test.wav");
	    System.out.println("--");
	}
       
}
