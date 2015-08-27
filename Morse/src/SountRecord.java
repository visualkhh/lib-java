import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import khh.debug.LogK;


public class SountRecord {

	ArrayList<MorseRecordDTO> list = new ArrayList<MorseRecordDTO>();
	TargetDataLine line = null;
	AudioInputStream ais = null;
	int nRead = 0;
	public SountRecord() {
		// TODO Auto-generated constructor stub
	}
	LogK log = LogK.getInstance();
	public AudioFormat getAudioFormat(){
		int sampleRate = 44100;
		int sampleSizeInBits = 8;
		int channeles = 1;
		boolean signed = true;
		boolean bigEndian = true;
		return new AudioFormat(sampleRate, sampleSizeInBits, channeles, signed, bigEndian);	
	}
	public void start() throws LineUnavailableException, IOException{
		AudioFormat format = getAudioFormat();
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
		line = (TargetDataLine)AudioSystem.getLine(info);
		line.open(format);
		System.out.println("-st--");
		line.start();
		
		System.out.println("---0");
		ais = new AudioInputStream(line);

		
		
		nRead=0;
		byte[] abData	= new byte[100];
		int blockCnt	= 0;
		
		while(nRead!=-1){
			nRead = ais.read(abData, 0, abData.length);
			if(nRead >=0 ){
				log.debug("msg", abData);
				//logic
				int scopeCnt=0;
				for (int j=0; j<nRead; j++){
					if( (abData[j]&0xFF) >(5) && (abData[j]&0xFF)<250 ){
						scopeCnt++;
					}
				}
				log.debug("blockCnt:"+blockCnt+"  scopeCnt:"+scopeCnt);
				
				//처음
				boolean isDot = scopeCnt > abData.length/2;
				if(isDot && list.size()==0){
					log.debug("start");
					list.add(new MorseRecordDTO(".",1));
				}
				//닷나올때
				else if(isDot && list.size()>0){
					MorseRecordDTO mr = list.get(list.size()-1);
					if(mr.getMsg().equals(" ")){
						list.add(new MorseRecordDTO(".", 1));
					}else{
						mr.setLength(mr.getLength()+1);
					}
				}
				//아닐때
				else if(!isDot && list.size()>0){
					MorseRecordDTO mr = list.get(list.size()-1);
					if(mr.getMsg().equals(".")){
						list.add(new MorseRecordDTO(" ", 1));
					}else{
						mr.setLength(mr.getLength()+1);
					}
				}
				
			}
			//break;
		}
		
		
//		System.out.println("---1");
//		AudioSystem.write(ais, AudioFileFormat.Type.WAVE, new File("c:/rr.wav"));
//		System.out.println("---0");
		
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
	}
	public void finish(){
		nRead=-1;
		try {
			Thread.sleep(5000);
			ais.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		line.stop();
		line.close();
		
		
		int dMin	= Integer.MAX_VALUE;
		int dMax	= Integer.MIN_VALUE;
		int sMin	= Integer.MAX_VALUE;
		int sMax	= Integer.MIN_VALUE;
		for (int i = 0; i < list.size()-1; i++) {
			MorseRecordDTO mr = list.get(i);
			if(mr.getMsg().equals(".")){
				dMin = Math.min(dMin, mr.getLength());
				dMax = Math.max(dMax, mr.getLength());
			}else if(mr.getMsg().equals(" ")){
				sMin = Math.min(sMin, mr.getLength());
				sMax = Math.max(sMax, mr.getLength());
			}
		}
		int dscop	= dMin+   ((dMax-dMin)/2);	
		int sscop	= sMin+   ((sMax-sMin)/2);	
		
		
		log.debug("dMin("+dMin+") dMax("+dMax+") sMin("+sMin+")  sMax("+sMax+")     // dscop("+dscop+")  sscop("+sscop+")");
		
		String msg = "";
		for (int i = 0; i < list.size()-1; i++){
			MorseRecordDTO mr = list.get(i);
			log.debug("result("+i+")"+mr.getMsg()+"  "+mr.getLength()+"   ->"+dscop+", "+sscop);
			if(mr.getMsg().equals(".")){
				if(mr.getLength()>dscop){
					msg+="-";
				}else{
					msg+=".";
				}
			}else if(mr.getMsg().equals(" ")){
				if(mr.getLength()>sscop){
					msg+=" ";
				}else{
					msg+="";
				}
			}
		}
		log.debug("==end==");
		log.debug(msg);
		log.debug(new Morse().toEnglish(msg.replace(" ", "|")));
	}
	public static void main(String[] args) throws Exception {
		
		final SountRecord s = new SountRecord();
		
		Thread stopper = new Thread(new Runnable() {
			public void run() {
				Scanner input = new Scanner(System.in);
				String ans = input.nextLine();
				s.finish();
				System.exit(0);
			}
		});
//
		stopper.start();
		s.start();
	}
}
