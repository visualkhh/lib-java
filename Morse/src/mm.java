import javax.sound.sampled.*;
import java.io.*;
 
void setup() {}
void draw() {}
void mousePressed() {
  byte[] pcm_data = new byte[44100*2];
  double L1 = 44100.0/240.0;
  double L2 = 44100.0/245.0;
  for (int i=0; i<pcm_data.length; i++) {
    pcm_data[i] = (byte)(55*Math.sin((i/L1)*Math.PI*2));
    pcm_data[i] += (byte)(55*Math.sin((i/L2)*Math.PI*2));
  }
 
  AudioFormat frmt = new AudioFormat(44100, 8, 1, true, true);
  AudioInputStream ais = new AudioInputStream(
    new ByteArrayInputStream(pcm_data), frmt, 
    pcm_data.length / frmt.getFrameSize()
  );
 
  try {
    AudioSystem.write(ais, AudioFileFormat.Type.WAVE, new
      File(sketchPath + "/test.wav")
    );
  } 
  catch(Exception e) {
    e.printStackTrace();
  }
}