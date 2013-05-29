import javax.swing.*;
import javax.swing.event.*;
import java.io.*;

import javax.media.*;
import javax.media.format.*;
import javax.media.util.*;
import javax.media.control.*;
import javax.media.protocol.*;
import java.util.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;

import com.sun.image.codec.jpeg.*;

public class JPEGCapture {

 public static Player player = null;
 public CaptureDeviceInfo di = null;
 public MediaLocator ml = null;

 public Buffer buf = null;
 public Image img = null;
 public VideoFormat vf = null;
 public BufferToImage btoi = null;

 public JPEGCapture() {
  this.initDevice();
 }

 public void initDevice() {
  String str1 = "vfw:Logitech USB Video Camera:0";
  String str2 = "vfw:Microsoft WDM Image Capture (Win32):0";
  di = CaptureDeviceManager.getDevice(str2);
  ml = di.getLocator();
  try
  {
   player = Manager.createRealizedPlayer(ml);
      player.start();
  }
  catch(Exception e)
  {
   e.printStackTrace();
  }
 }

 public static void playerClose() {
  player.close();
  player.deallocate();
 }

 public void Capture(String absPath) {
  FrameGrabbingControl fgc = (FrameGrabbingControl) player
    .getControl("javax.media.control.FrameGrabbingControl");
  buf = fgc.grabFrame();

  // Convert it to an image
  btoi = new BufferToImage((VideoFormat) buf.getFormat());
  img = btoi.createImage(buf);

  // save image
  saveJPG(img, absPath);
 }

 public static void saveJPG(Image img, String s) {
  BufferedImage bi = new BufferedImage(img.getWidth(null), img
    .getHeight(null), BufferedImage.TYPE_INT_RGB);
  Graphics2D g2 = bi.createGraphics();
  g2.drawImage(img, null, null);

  FileOutputStream out = null;
  try {
   out = new FileOutputStream(s);
  } catch (java.io.FileNotFoundException io) {
   System.out.println("File Not Found");
  }

  JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
  JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bi);
  param.setQuality(0.5f, false);
  encoder.setJPEGEncodeParam(param);

  try {
   encoder.encode(bi);
   out.close();
  } catch (java.io.IOException io) {
   System.out.println("IOException");
  }
 }
 
 
 
 public static void main(String[] args) {
     JPEGCapture cap = new JPEGCapture();
     cap.Capture("C:\\test.jpg");
}
}