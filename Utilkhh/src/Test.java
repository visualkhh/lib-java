import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

import khh.string.util.StringUtil;


public class Test {

	
	public void s(StringBuffer a){
		a.append("vvvvvvv");
	}

	public void s(String a){
		a+="v";
	}
	public static void main(String[] args) throws Exception {
//		String requestURI="/ajax";
//		String atUrl="/ajax.*IN=info.*";
////		String atUrl=null;
//		boolean s = StringUtil.isMatches(requestURI, atUrl);
//		System.out.println(s);
		
		LinkedHashMap<String, String> c = new LinkedHashMap<>();
		LinkedHashMap<String, String> m = new LinkedHashMap<>();
		m.put("a_m","aa");
		m.put("b_m","ba");
		m.put("c_m","ca");
		LinkedHashMap<String, String> mm = new LinkedHashMap<>();
		mm.put("a_mm","aa");
		mm.put("b_mm","ba");
		mm.put("c_mm","ca");
		mm.put("b_m","ba");
		mm.put(null,null);
		
		
		c.putAll(m);;
		c.putAll(mm);;

		
		LinkedHashMap<String, LinkedHashMap<String,String>> aaa = new LinkedHashMap<>();
		aaa.put("zz", c);
		System.out.println(aaa);
//		c.entrySet().stream().forEach(v->{
//			System.out.println(v);
//		});
//		
//		
//		System.out.println(mm.size());
//		System.out.println("-------------");
//		ArrayList list=	mm.entrySet().stream().map(at->at.getValue()).collect(Collectors.toCollection(ArrayList::new));
//		for (int i = 0; i < list.size(); i++) {
//			System.out.println(list.get(i));
//		}
	
	}

       
}
