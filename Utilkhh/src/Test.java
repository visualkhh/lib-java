import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.mozilla.javascript.edu.emory.mathcs.backport.java.util.Arrays;


public class Test {

	
	public void s(StringBuffer a){
		a.append("vvvvvvv");
	}

	public void s(String a){
		a+="v";
	}
	public static void main(String[] args) throws Exception {
		ArrayList<String> a = new ArrayList<>();
		a.add("a");
		a.add("b");
		a.add("c");
		a.add("d");
		a.add("e");
		a.add("f");
		a.add("g");
		for (int i = 0; i < a.size(); i++) {
			String b = a.get(i);
			if(b.equals("g")){
				a.remove(i);
				i--;
			}
			System.out.println(i+" "+a.get(i));
		}
		
	}

       
}
