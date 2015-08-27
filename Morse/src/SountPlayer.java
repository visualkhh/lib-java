import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import khh.debug.LogK;


public class SountPlayer {

	LogK log = LogK.getInstance();
	public SountPlayer() {
		// TODO Auto-generated constructor stub
	}
	
	public AudioFormat getAudioFormat(){
		int sampleRate = 44100;
		int sampleSizeInBits = 8;
		int channeles = 1;
		boolean signed = true;
		boolean bigEndian = true;
		return new AudioFormat(sampleRate, sampleSizeInBits, channeles, signed, bigEndian);	
	}
	public void start(File file) throws Exception, IOException, UnsupportedAudioFileException{
		
		AudioInputStream ais = AudioSystem.getAudioInputStream(file);
		AudioFormat format = ais.getFormat();
		SourceDataLine line = null;
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
		
		line = (SourceDataLine) AudioSystem.getLine(info);
		line.open(format);
		line.start();
		
		
		int nRead=0;
		byte[] abData = new byte[128000+75960];
//		byte[] abData = new byte[80000];
		
		while(nRead!=-1){
			nRead = ais.read(abData,0,abData.length);
			if(nRead>=0){
				log.debug("msg",abData);
				int nWrite = line.write(abData, 0, nRead);
				
			}
			System.out.println(nRead);
		}

		line.drain();
		line.close();
		
		
		
	}
	public void finish(){
	}
	public static void main(String[] args) throws Exception {
		
		final SountPlayer s = new SountPlayer();
		s.start(new File("C:/r2.wav"));
	}
}
