package khh.cloude.image;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.stream.ImageInputStream;

import khh.conversion.util.ConversionUtil;
import khh.file.util.FileUtil;
import khh.image.ImageUtil;
import khh.util.ByteUtil;

public class CloudImage {

    private byte[] stximage = new byte[]{0x19, (byte) 0x86,0x04,0x25};
    private byte[] etximage  = new byte[]{0x25, 0x04,(byte) 0x86,0x19};
    private byte[] data =null;
    
    
    public byte[] getStximage() {
        return stximage;
    }


    public byte[] getEtximage() {
        return etximage;
    }






    
    
    
    public byte[] getData() {
        return data;
    }


    public void setData(byte[] data) {
        this.data = data;
    }


    public ArrayList<Color> getColorData(){
        //stx ,length,data,etx
        byte[] stx            = getStximage();
        byte[] buff = getData();
        byte[] etx            = getEtximage();
        if(buff==null&&stx==null&&etx==null){
            return null;
        }
        
        ArrayList<Color> returnval = ImageUtil.toColorList(buff);
        byte[] length_fulldata       = ConversionUtil.toByteArray((returnval.size()+1)*4);      //onlydata length = +1    color = 4byte 
        byte[] length_onlydata       = ConversionUtil.toByteArray(buff.length);
        
        
        returnval.add(0,ImageUtil.toColor(stx));                          //stx
        returnval.add(1,ImageUtil.toColor(length_fulldata));            //length_fulldata
        returnval.add(2,ImageUtil.toColor(length_onlydata));           //length_onlydata
        returnval.add(ImageUtil.toColor(etx));                             //etx
        
        return returnval;
    }
    public void writeImagePNG(File file) throws IOException{
        ArrayList<Color> colorlist = getColorData();
        double size_root =  Math.sqrt(colorlist.size());
        int size= (int) Math.ceil(size_root);
       // System.out.println("colorsize : "+colorlist.size()+"     size_root : "+size_root+"    size : "+size);
        BufferedImage bufferedImage  =  ImageUtil.drawBufferedImage(size, size,  BufferedImage.TYPE_INT_ARGB,colorlist);
        FileUtil.writeFile(file, bufferedImage, "png");
    }
    
    public byte[] readImagePNG(File file) throws IOException{
        return readImagePNG(ImageUtil.getImage(file));
    }
    public byte[] readImagePNG(InputStream inputstream ) throws IOException{
        return readImagePNG(ImageUtil.getImage(inputstream));
    }
    public byte[] readImagePNG(ImageInputStream imageinputstream) throws IOException{
        return readImagePNG(ImageUtil.getImage(imageinputstream));
    }
    public byte[] readImagePNG(URL url) throws IOException{
        return readImagePNG(ImageUtil.getImage(url));
    }
    public byte[] readImagePNG(BufferedImage bufferedimage) throws IOException{
       // System.out.println("hasAlpha "+ ImageUtil.hasAlpha(ri));
        //ArrayList<Color> data = ImageUtil.toColorList(ri);
        byte[] data = ImageUtil.toByteArray(bufferedimage);
     /*  
        for (int i = 0; i < data.length; i++) {
            if(i!=0&&i%4==0)
              System.out.println();
            System.out.format("%02X  ",data[i]);
        }
       */ 
        
        
     
        
        byte[] stximage = getStximage();
        byte[] etximage  = getEtximage();
        
        byte[] stxdata = new byte[stximage.length];
        byte[] etxdata = new byte[etximage.length];
        byte[] length = new byte[4];
        
        boolean stxsw=false;
        boolean etxsw=false;
        byte[] full_data=null;
        byte[] only_data=null;
        try{
            for (int i = 0; i < data.length; i++) {
                 System.arraycopy(data, i, stxdata,0, stxdata.length);
                 stxsw = ByteUtil.equals(stximage, stxdata);
                 int subindex=i+stxdata.length;
                 if(stxsw){
                     System.arraycopy(data, subindex, length,0, length.length);
                     int length_fulldata = ConversionUtil.toInt(length);
                   //  System.out.format("%02X    %d      subIndex[%d]",data[subindex],length_fulldata,subindex);
                     
                     
                     subindex+=i+length.length;
                     int etx_index= subindex+ length_fulldata;
        //            System.out.println("subindex : "+subindex+"     etx_index : "+etx_index);
                     System.arraycopy(data, etx_index, etxdata,0, etxdata.length);
                     
//                     for (int j = 0; j < etxdata.length; j++) {
//                        System.out.format("%02X ",etxdata[j]);
//                    }
                     
                     etxsw = ByteUtil.equals(etximage, etxdata);
                     if(etxsw){
                         full_data = new byte[etx_index-subindex];
                         System.arraycopy(data, subindex, full_data,0, full_data.length);     //fulldata
                         break;
                     }else{
                         continue;
                     }
                 }
                 
                 
            }
            
            /////only data  search
            System.arraycopy(full_data, 0, length,0, length.length);
            int length_onlydata = ConversionUtil.toInt(length); 
            only_data = new byte[length_onlydata];
            System.arraycopy(full_data, length.length, only_data,0, only_data.length);
            
            
            
        }catch (Exception e) {
            return null;
        }
        
        return only_data;
        
    }
    
    
}
