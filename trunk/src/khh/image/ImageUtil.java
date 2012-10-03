package khh.image;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;

import jxl.format.RGB;

import khh.conversion.util.ConversionUtil;
import khh.file.util.FileUtil;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageUtil {
    static public final String SET_PNG_NAME= "png"; 
    
    
    public static BufferedImage resize (BufferedImage buff,  int width, int height ){
        return resize(buff, 0, 0, width, height);
    }
    public static BufferedImage resize (BufferedImage buff, int x, int y, int width, int height ){
        return resize(buff,x,y,width,height,BufferedImage.TYPE_INT_RGB);
    }
    public static BufferedImage resize(BufferedImage buff, int x, int y, int width, int height, int imgtype ){
//        BufferedImage thum = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); 
        BufferedImage thum = new BufferedImage(width, height, imgtype); 
      //가상으로 이미지 만듬 
        Graphics2D g = thum.createGraphics(); // 가상이미지에 씀 
        g.setComposite(AlphaComposite.Src);
        g.drawImage(buff, x,y, width, height, null); // 이미지 만듬 
        g.dispose();
        return thum;
    }
    public static  BufferedImage crop(BufferedImage buff,int x, int y, int width, int height) {
        BufferedImage dest = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
        Graphics2D g = dest.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.drawImage(buff, 0, 0, width, height, x, y, x + width, y + height,null);
        g.dispose();
        return dest;
    }
    
    
    /* 아래2개 썸네일 쓰지말것*/
    /*public static BufferedImage thumbnail(File originalfile,int width, int height,String extension) throws IOException{
        return thumbnail(originalfile, width, height, extension, BufferedImage.TYPE_INT_RGB    );
    }
    //extension= jpg , BufferedImage.TYPE_INT_RGB    
    public static BufferedImage thumbnail(File originalfile,int width, int height,String extension,int imgtype) throws IOException{
        String imagePath=originalfile.getAbsolutePath();
        ParameterBlock pb = new ParameterBlock (); 
        pb.add(imagePath);
        RenderedOp rOp =  JAI.create("fileload",pb);
        BufferedImage bi = rOp.getAsBufferedImage(); //가상
        BufferedImage thum =ImageUtil.thumbnail(bi, 0,0, width, height,imgtype);
        return thum;
//        ImageIO.write(thum, "jpg", thumbnailfile);
        //ImageIO.write(thum, extension, thumbnailfile);
    }*/
    

    public static void encodingJPG(OutputStream outputstream, BufferedImage buffereadImage, int quality) throws ImageFormatException, IOException{
        //FileOutputStream output = new FileOutputStream("c:\\bbb.jpg");
        float quality_f = ((float)quality)/100f;
        //System.out.println("quality_f : "+quality_f);
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder ( outputstream );
        JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam ( buffereadImage );
        param.setQuality ( quality_f, false );   // 100% high quality setting, no compression
        encoder.setJPEGEncodeParam ( param );
        encoder.encode ( buffereadImage );
    }
    
    
    
        public static BufferedImage toBufferedImage(Image image) {
           if (image instanceof BufferedImage) {
//               System.out.println("image instanceof BufferedImage");
               return (BufferedImage)image;
               }
       
           // This code ensures that all the pixels in the image are loaded
           boolean hasAlpha  = false;
           image = new ImageIcon(image).getImage();
           hasAlpha = hasAlpha(image);
           // Determine if the image has transparent pixels
           // Create a buffered image with a format that's compatible with the screen
           BufferedImage bimage = null;
           GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
           try {
               // Determine the type of transparency of the new buffered image
               int transparency = Transparency.OPAQUE;
               if (hasAlpha == true) {transparency = Transparency.BITMASK;}
       
               // Create the buffered image
               GraphicsDevice gs = ge.getDefaultScreenDevice();
               GraphicsConfiguration gc = gs.getDefaultConfiguration();
               bimage = gc.createCompatibleImage(image.getWidth(null), image.getHeight(null), transparency);
           } 
           catch (HeadlessException e) {} //No screen
       
           if (bimage == null) {
               // Create a buffered image using the default color model
               int type = BufferedImage.TYPE_INT_RGB;
               if (hasAlpha == true) {type = BufferedImage.TYPE_INT_ARGB;}
               bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
           }
       
           // Copy image to buffered image
           Graphics g = bimage.createGraphics();
       
           // Paint the image onto the buffered image
           g.drawImage(image, 0, 0, null);
           g.dispose();
       
           return bimage;
       }

        public static boolean hasAlpha(BufferedImage image) {
           return  ((BufferedImage)image).getColorModel().hasAlpha();
        }
         public static boolean hasAlpha(Image image) {
                // If buffered image, the color model is readily available
                if (image instanceof BufferedImage) {return ((BufferedImage)image).getColorModel().hasAlpha();}
            
                // Use a pixel grabber to retrieve the image's color model;
                // grabbing a single pixel is usually sufficient
                PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
                try {pg.grabPixels();} catch (InterruptedException e) {}
            
                // Get the image's color model
                return pg.getColorModel().hasAlpha();
            }

    
    
    
    public static String getFormatName(File file) throws IOException {
        String formatName = null;
        ImageInputStream iis = ImageIO.createImageInputStream(file);
        Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
        if (iter.hasNext()) {
         ImageReader reader = (ImageReader)iter.next();
         iis.close();
         formatName = reader.getFormatName();
         if (formatName != null) {
          formatName = formatName.toLowerCase();
         }
        }
        return formatName;
       }
    
    
    public static ImageInputStream getImageInputStream(File file) throws IOException{
        ImageInputStream iis = ImageIO.createImageInputStream(file);
        return iis;
    }
    
    
    
    public static Image getImage(String filepath) throws IOException{
        return Toolkit.getDefaultToolkit().getImage(filepath);
    }
    
    public static BufferedImage getImage(File file) throws IOException{
        return ImageIO.read(file);
    }
    public static BufferedImage getImage(URL url) throws IOException{
        return ImageIO.read(url);
    }
    public static BufferedImage getImage(ImageInputStream imageinputstream ) throws IOException{
        return ImageIO.read(imageinputstream);
    }
    public static BufferedImage getImage(InputStream inputstream ) throws IOException{
        return ImageIO.read(inputstream);
    }
    
    
    
    public static  ArrayList<Color> toColorList(Image image){
        return toColorList(ImageUtil.toBufferedImage(image));
    }
    public static  ArrayList<Color> toColorList(BufferedImage bufferedImage){
      //  System.out.println(bufferedImage_r.getType()+"    "+BufferedImage.TYPE_4BYTE_ABGR);
        //System.out.println(bufferedImage_r.getWidth() +"    "+bufferedImage_r.getHeight());
        //System.out.println();
        ArrayList<Color> colorlist = new ArrayList<Color>();
        int input_index=0;
        for (int i = 0; i < bufferedImage.getWidth(); i++) {
            for (int j = 0; j < bufferedImage.getHeight(); j++) {
              //  int rgb =  bufferedImage_r.get
                ColorModel w = bufferedImage.getColorModel();
               //System.out.println(w.hasAlpha());
                Color color = new Color(bufferedImage.getRGB(i, j),true);
                colorlist.add(color);
               int rgb =  color.getRGB();
            /*   byte[] by = Converting.toByteArray(rgb);
               for (int z = 0; z < by.length; z++) {
                   System.out.format("%02X  ",by[z]);
               }
               System.out.format("    A: %02X R: %02X G: %02X B: %02X   RGBA : %d  ",color.getAlpha(),(byte)color.getRed(),(byte)color.getGreen(),(byte)color.getBlue(),color.getRGB());
               System.out.println();
               */
           }
       }
        return colorlist;
    }
    
    
    public static Color toColor(final byte[] buff){
        byte[] input_data =new byte[]{(byte) 0xff,(byte) 0xff,(byte) 0xff,(byte) 0xff};
        for (int i = 0; i < buff.length; i++) {
            input_data[i%4] = buff[i];
            if( ( i!=0 && i%4==0 ) || i+1>=buff.length){
         /*      
                int input_data0 = Integer.parseInt(String.format("%02X",input_data[0]), 16);
                int input_data1 = Integer.parseInt(String.format("%02X",input_data[1]), 16);
                int input_data2 = Integer.parseInt(String.format("%02X",input_data[2]), 16);
                int input_data3 = Integer.parseInt(String.format("%02X",input_data[3]), 16);
        */
                int input_data0 = ConversionUtil.toInt(new byte[]{ input_data[0]});
                int input_data1 = ConversionUtil.toInt(new byte[]{ input_data[1]});
                int input_data2 =ConversionUtil.toInt(new byte[]{ input_data[2]});
                int input_data3 = ConversionUtil.toInt(new byte[]{ input_data[3]});
                
                Color color = new Color(input_data1,input_data2,input_data3,input_data0);
                return color;
             //   input_data =new byte[]{(byte) 0xff,(byte) 0xff,(byte) 0xff,(byte) 0xff};
            }
        }
        return null;
    }
    
    
    public static ArrayList<Color> toColorList(final byte[] buff){
        ArrayList<Color> returnval = new ArrayList<Color>();
        
        byte[][] grouparray =ConversionUtil.toByteGroupArray(buff, 4, (byte)0xff, ConversionUtil.ALIGN_LEFT);
        for (int i = 0; i < grouparray.length; i++) {
            returnval.add(toColor(new byte[]{grouparray[i][0],grouparray[i][1],grouparray[i][2],grouparray[i][3]}));
        }
        
        
        
//        byte[] input_data =new byte[]{(byte) 0xff,(byte) 0xff,(byte) 0xff,(byte) 0xff};
//        for (int i = 0; i < buff.length; i++) {
//            input_data[i%4] = buff[i];
//            if( ( i!=0 && i%4==0 ) || i+1>=buff.length){
//                System.out.println("inside");
//                int input_data0 = Integer.parseInt(String.format("%02X",input_data[0]), 16);
//                int input_data1 = Integer.parseInt(String.format("%02X",input_data[1]), 16);
//                int input_data2 = Integer.parseInt(String.format("%02X",input_data[2]), 16);
//                int input_data3 = Integer.parseInt(String.format("%02X",input_data[3]), 16);
//                Color color = new Color(input_data1,input_data2,input_data3,input_data0);
//                returnval.add(color);
//                input_data =new byte[]{(byte) 0xff,(byte) 0xff,(byte) 0xff,(byte) 0xff};
//            }
//        }
        return returnval;
    }
    
    
    public static byte[] toByteArray(Image image){
        return toByteArray(toBufferedImage(image));
    }
    public static byte[] toByteArray(BufferedImage bufferedImage_r){
      //  System.out.println(bufferedImage_r.getType()+"    "+BufferedImage.TYPE_4BYTE_ABGR);
        //System.out.println(bufferedImage_r.getWidth() +"    "+bufferedImage_r.getHeight());
        //System.out.println();
        byte[] return_data = new byte[ bufferedImage_r.getWidth()*bufferedImage_r.getHeight()*4];
        int input_index=0;
        for (int i = 0; i < bufferedImage_r.getWidth(); i++) {
            for (int j = 0; j < bufferedImage_r.getHeight(); j++) {
              //  int rgb =  bufferedImage_r.get
                ColorModel w = bufferedImage_r.getColorModel();
               //System.out.println(w.hasAlpha());
                Color color = new Color(bufferedImage_r.getRGB(i, j),true);
               int rgb =  color.getRGB();
               byte[] by = ConversionUtil.toByteArray(rgb);
               for (int z = 0; z < by.length; z++) {
             //      System.out.format("%02X  ",by[z]);
                   return_data[input_index++] = by[z];
               }
             //  System.out.format("    A: %02X R: %02X G: %02X B: %02X   RGBA : %d  ",color.getAlpha(),(byte)color.getRed(),(byte)color.getGreen(),(byte)color.getBlue(),color.getRGB());
              // System.out.println();
           }
       }
        return return_data;
    }
    
    
    
    
    
    
    
    //toBufferedImage
    /*
    public static BufferedImage getBuffreadImage(Image image){
        BufferedImage bi = new BufferedImage ( image.getWidth(null),
                image.getHeight(null),
                BufferedImage.TYPE_INT_RGB );
        Graphics2D g2 = bi.createGraphics();
        g2.drawImage ( image, null, null );
        g2.dispose();
        return bi;
    }
    */
    
    
    public static BufferedImage getBuffreadImage(File file) throws IOException{
        return toBufferedImage(ImageIO.read(file));
    }
    public static BufferedImage getBuffreadImage(URL url) throws IOException{
        return toBufferedImage(ImageIO.read(url));
    }
    public static BufferedImage getBuffreadImage(ImageInputStream imageinputstream ) throws IOException{
        return toBufferedImage(ImageIO.read(imageinputstream));
    }
    public static BufferedImage getBuffreadImage(InputStream inputstream ) throws IOException{
        return toBufferedImage(ImageIO.read(inputstream));
    }
    
    
    
    
    public static BufferedImage drawBufferedImage(int width, int height, int  bufferedimageType,ArrayList<Color> colorlist){
        //BufferedImage.TYPE_INT_ARGB
        BufferedImage bufferedImage = new BufferedImage(width,height,bufferedimageType);
        
        /*
        BufferedImage i = new BufferedImage(100,100,BufferedImage.TYPE_INT_BGR);
        i.getGraphics().setColor(new Color(255, 10, 20));
        i.getGraphics().fillRect(0, 0, 50, 50);
        FileUtil.writeFile("c:\\goodjob.jpg", i, "jpg");
        */
        
       // Graphics2D g = bufferedImage.createGraphics();
        int index=0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if(index>=colorlist.size())
                    break;
                Color color = colorlist.get(index++);
                bufferedImage.setRGB(i, j, color.getRGB());
                /*
                g.setColor(new Color(color.getRGB(),true));
                g.drawLine(i, j, i,j);
                byte[] by = Converting.toByteArray(color.getRGB());
              for (int z = 0; z < by.length;z++) {
                  System.out.format("%02X  ",by[z]);
              }
              System.out.format("    A: %02X R: %02X G: %02X B: %02X     RGBA : %d ",color.getAlpha(),(byte)color.getRed(),(byte)color.getGreen(),(byte)color.getBlue(),color.getRGB());
              System.out.println();
              */
            }
        }
        return bufferedImage;
    }
    
    
    
    
    
    
    
    public static Color getAvgColorIgnore(BufferedImage bi,int x, int y, int width,int height,Color startColor_i, Color endColor_i){
        Color startColor = new Color(Math.min(startColor_i.getRed(), endColor_i.getRed()),Math.min(startColor_i.getGreen(), endColor_i.getGreen()),Math.min(startColor_i.getBlue(), endColor_i.getBlue()));
        Color endColor = new Color(Math.max(startColor_i.getRed(), endColor_i.getRed()),Math.max(startColor_i.getGreen(), endColor_i.getGreen()),Math.max(startColor_i.getBlue(), endColor_i.getBlue()));
       
        
        int e_x = x+width;
        int e_y = y+height;
        //avg color
        double r_avg=0;
        double g_avg=0;
        double b_avg=0;
        int ignore_cnt=0;
        
        for (int sub_x = x; sub_x < e_x; sub_x++) {
            for (int sub_y = y; sub_y < e_y; sub_y++) {
                Color color = new Color(bi.getRGB(sub_x, sub_y));
                
                //해당되는건 제외해라.
                if(    startColor.getRed()<=color.getRed() && startColor.getGreen()<=color.getGreen() && startColor.getBlue()<= color.getBlue()  &&  
                        endColor.getRed()>=color.getRed() && endColor.getGreen()>=color.getGreen() && endColor.getBlue()>= color.getBlue()  ){
                    ignore_cnt++;
                    continue;
                }
                
                
                r_avg += color.getRed();
                g_avg += color.getGreen();
                b_avg += color.getBlue();
            }
            
        }
        r_avg = r_avg/(width*height-ignore_cnt);
        g_avg = g_avg/(width*height-ignore_cnt);
        b_avg = b_avg/(width*height-ignore_cnt);
        
        return new Color((int)r_avg,(int)g_avg,(int)b_avg);
    }
    public static Color getAvgColor(BufferedImage bi,int x, int y, int width,int height){
        int e_x = x+width;
        int e_y = y+height;
        //avg color
        double r_avg=0;
        double g_avg=0;
        double b_avg=0;
        
        for (int sub_x = x; sub_x < e_x; sub_x++) {
            for (int sub_y = y; sub_y < e_y; sub_y++) {
                Color color = new Color(bi.getRGB(sub_x, sub_y));
                r_avg += color.getRed();
                g_avg += color.getGreen();
                b_avg += color.getBlue();
            }
            
        }
        
        r_avg = r_avg/(width*height);
        g_avg = g_avg/(width*height);
        b_avg = b_avg/(width*height);
        
        return new Color((int)r_avg,(int)g_avg,(int)b_avg);
    }
    
    
    //농도**Density
    public static Color upDensity(Color baseColor,Color upColor){
       RGB rgb =  upDensity( new RGB(baseColor.getRed(),baseColor.getGreen(),baseColor.getBlue()),
        new RGB(upColor.getRed(),upColor.getGreen(),upColor.getBlue()));
        return new Color(rgb.getRed(),rgb.getGreen(),rgb.getBlue());
    }
    public static RGB upDensity(RGB baseColor,RGB upColor){
        int r = baseColor.getRed()  +   upColor.getRed();     
        int g = baseColor.getGreen()+   upColor.getGreen();   
        int b = baseColor.getBlue() +   upColor.getBlue();    
        
        r = (r>255?255:r);
        g = (g>255?255:g);
        b = (b>255?255:b);
        return new RGB(r,g,b);
    }
    
    public static Color downDensity(Color baseColor,Color downColor){
        RGB rgb =  upDensity( new RGB(baseColor.getRed(),baseColor.getGreen(),baseColor.getBlue()),
         new RGB(downColor.getRed(),downColor.getGreen(),downColor.getBlue()));
         return new Color(rgb.getRed(),rgb.getGreen(),rgb.getBlue());
     }
    public static RGB downDensity(RGB baseColor,RGB downColor){
        int r = baseColor.getRed()  -   downColor.getRed();     
        int g = baseColor.getGreen()-   downColor.getGreen();   
        int b = baseColor.getBlue() -   downColor.getBlue();    
        
        r = (r<0?0:r);
        g = (g<0?0:g);
        b = (b<0?0:b);
        return new RGB(r,g,b);
    }
    
    
    
    public static ArrayList<Rectangle> getScop(BufferedImage bi,int gappx,int x, int y, int width,int height,final Color startColor,Color endColor){
     return getScop(bi,gappx,x,y,width,height,startColor,endColor,Integer.MAX_VALUE);   
    }
    public static ArrayList<Rectangle> getScop(BufferedImage bi,int gappx,int x, int y, int width,int height,final Color startColor_i,Color endColor_i,int size){
        
       // System.out.println("x:"+x+ "    y:"+y+"    width:"+width+"    height:"+height+ "     size:"+size+"    gappx:"+gappx+"    ");
        
        Color startColor = new Color(Math.min(startColor_i.getRed(), endColor_i.getRed()),Math.min(startColor_i.getGreen(), endColor_i.getGreen()),Math.min(startColor_i.getBlue(), endColor_i.getBlue()));
        Color endColor = new Color(Math.max(startColor_i.getRed(), endColor_i.getRed()),Math.max(startColor_i.getGreen(), endColor_i.getGreen()),Math.max(startColor_i.getBlue(), endColor_i.getBlue()));
        
       // Color startColor = startColor_i;
       // Color endColor = endColor_i;
        
        
        
        int s_w  =  x;
        int s_h  =  y;
        int e_w = x+width;
        int e_h = y+height;
       // System.out.println("s_w"+s_w+" s_h"+s_h+" e_w"+e_w+" e_h"+e_h+" size"+size);
        /*
                            왼쪽에서 오른쪽으로 긁는다.
                            위에서 아래로 긁는다.
                              
        */
        //ractangle
        ArrayList<Rectangle> rectanglelist=new ArrayList<Rectangle>();
        int gap_y = gappx;
        int gap_x = gappx;
        //serarch Color  Draw Color
      for (int s_h_v =  s_h  ; s_h_v <e_h; s_h_v++) {
           h: for (int s_w_v  = s_w; s_w_v < e_w; s_w_v++) {
              // System.out.println("s_h_v:"+s_h_v+"  s_w_v:"+s_w_v);
               if(rectanglelist.size()>=size){  //원하는개수체크
                   return rectanglelist;
               }
               
                 Color color = new Color(bi.getRGB(s_w_v, s_h_v)); 
                 //System.out.println("Color : R:"+color.getRed()+"    G:"+color.getGreen()+"     B:"+color.getBlue());
                 //찾으면
                 if(    startColor.getRed()<=color.getRed() && startColor.getGreen()<=color.getGreen() && startColor.getBlue()<= color.getBlue()  &&  
                        endColor.getRed()>=color.getRed() && endColor.getGreen()>=color.getGreen() && endColor.getBlue()>= color.getBlue()  ){
                     //--------기존거에있냐?
                     for (int rcnt = 0; rcnt < rectanglelist.size(); rcnt++) {
                         Rectangle r = rectanglelist.get(rcnt);
                        if(s_w_v >=  r.getX() && s_w_v <= r.getX()+r.getWidth() &&
                            s_h_v >=  r.getY() && s_h_v <= r.getY()+r.getHeight()      
                        ){
                            s_w_v = (int) (r.getX()+r.getWidth())+1;
                            break h;
                        }
                    }
                     
                     //------------네모!만드는거.
                     //초기화
                     int min_y              =   s_h_v;
                     int y_position         =   s_h_v;
                      
                     //search max_y
                     int max_y=Integer.MIN_VALUE;
                     int min_x=Integer.MAX_VALUE;
                     int max_x=Integer.MIN_VALUE;
                     //하나의 꼭지를 잡았으니깐  이제 다시한번 gap 만큼 처리한다.  위에서 아래를 하면서 
                     //아래로내려가며    지금좌표에서  우측으로 gap만큼 좌측으로 gap만큼  
                     for (int y_cnt = 0; y_cnt < gap_y && y_position<=e_h; y_cnt++) {
                         boolean y_sw = false;
                         int x_positive    =   s_w_v;
                         int x_nagative    =   s_w_v;
                      
                         //search max_x 우측에..좌표
                         for (int x_cnt = 0;  x_cnt <= gap_x  &&  x_positive<e_w ; x_cnt++) {
                             Color color_x =  new Color(bi.getRGB(x_positive ,y_position)); 
                             if( startColor.getRed()<=color_x.getRed() && startColor.getGreen()<=color_x.getGreen() && startColor.getBlue()<= color_x.getBlue() && 
                                 endColor.getRed()>=color_x.getRed() && endColor.getGreen()>=color_x.getGreen() && endColor.getBlue()>= color_x.getBlue()  ){
                                 max_x = Math.max(max_x, x_positive);
                                 x_cnt=0;
                                 y_sw=true;
                             }
                            x_positive++;
                        }
                         //search min_x  좌측에..좌표
                         for (int x_cnt = 0;  x_cnt <= gap_x  &&  x_nagative>s_w; x_cnt++) {
                             Color color_x =  new Color(bi.getRGB(x_nagative ,y_position)); 
                             if( startColor.getRed()<=color_x.getRed() && startColor.getGreen()<=color_x.getGreen() && startColor.getBlue()<= color_x.getBlue() && 
                                 endColor.getRed()>=color_x.getRed() && endColor.getGreen()>=color_x.getGreen() && endColor.getBlue()>= color_x.getBlue()  ){
                                 min_x = Math.min(min_x,x_nagative);
                                 x_cnt=0;
                                 y_sw=true;
                             }
                            x_nagative--;
                        }
                     
                         
                         y_position++;
                         if(y_sw){
                             max_y=y_position;
                             y_cnt=0;
                         }
                         
                     }
                     
                     //restart
                     //....
                     Rectangle rectangle = new Rectangle();
                     rectangle.setBounds(min_x ,min_y,max_x-min_x , max_y-min_y);
                     rectanglelist.add(rectangle);
                     
                     //넘김
                     s_w_v=max_x+1;
                 }
             }
         }
     return rectanglelist;   
    }
    
    
    
    
    
    public static byte[] bufferedImageToByteArray(BufferedImage b,String set_name_filename) throws IOException{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(b, set_name_filename, baos);
        baos.flush();
        return baos.toByteArray();
    }
    
    
    
    
}
