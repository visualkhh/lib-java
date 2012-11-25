package khh.file.pack;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import khh.conversion.util.ConversionUtil;
import khh.debug.LogK;
import khh.file.util.FileUtil;
import khh.string.util.StringUtil;

//쓰지말것 ㅠㅠ 짱꼬름 
public class Kip  implements Serializable{
    ArrayList<KipByte[]> kepCellCommonContainer = null;
    KipCellContainer kepCellContainer = null;
    ArrayList<File> packFilelist = null;
//    ArrayList<File> unpackFilelist = null;
    File packfile= null;
    File unpackfile=null;
    transient LogK logk = LogK.getInstance();
    
    
    public Kip() {
        kepCellCommonContainer = new ArrayList<KipByte[]>();
        kepCellContainer = new KipCellContainer();
        packFilelist = new ArrayList<File>();
    }
    
    public void pack(File packfile) throws IOException{
        this.packfile = packfile;
        packSetting(packfile);
        packStart();
    }
    private void packSetting(File file) throws IOException {
        clear();
        ArrayList<File> onlyfiles =new  ArrayList<File>();
        if(file.isFile()){
            packFilelist.add(file);
            onlyfiles.add(file);
        }else if(file.isDirectory()){
            packFilelist = FileUtil.getFileListFullDepth(file);
            onlyfiles = FileUtil.getFileListOnlyFileFullDepth(file);
        }
        for (int i = 0; i < onlyfiles.size(); i++) {
            kepCellContainer.appendData(FileUtil.readFileToByte(onlyfiles.get(i)));
        }
        
    }

    private void packStart() {
        getLogk().info("pack Target  packFILE     "+packfile.getAbsolutePath()+"      kepCellContainer size"+kepCellContainer.size());
        for (int i = 0; i < packFilelist.size(); i++) {
            getLogk().info("pack FileList "+i+"   "+packFilelist.get(i).getAbsolutePath()+"       size:"+packFilelist.get(i).length()+"      isfile:"+packFilelist.get(i).isFile());
        }
        //................
    }
    
    
   public void unpack(File unpakcfile) throws IOException{
       
       
       this.unpackfile = unpakcfile;
       getLogk().info("unpack UnTarget  unpackFILE     "+unpackfile.getAbsolutePath());

       unpackStart();  //unpackstart
       
       if(!unpackfile.exists()){ //없음
           unpakcfile.mkdirs();
       }

       
       for (int i = 0; i < packFilelist.size(); i++) {
           File packFile = packFilelist.get(i);
           String newfile = packFile.getAbsolutePath().replaceAll(StringUtil.regexMetaCharToEscapeChar(packfile.getAbsolutePath()), "");
           
           File file = new File(unpackfile,newfile);
//           unpackFilelist.add(file);
           if(packFile.isDirectory()){
               file.mkdir();
           }else if(packFile.isFile()){
               file.createNewFile();
               FileUtil.writeFile(file, kepCellContainer.getCutByte((int)packFile.length()));
           }
           getLogk().info("unpack FileList "+i+"   "+file.getAbsolutePath()+"     size:"+file.length()+"       isfile:"+file.isFile());
       }
       
       
       
       
       
//       for (int i = 0; i < unpackFilelist.size(); i++) {
//           File file = unpackFilelist.get(i);
//           getLogk().info("unpack FileList "+i+"   "+file.getAbsolutePath()+"     size:"+file.length()+"       isfile:"+file.isFile());
//           if(file.isDirectory()){
//             continue;  
//           }else if (file.isFile()){
//               
//           }
//       }
       
       
   }
    
   
    private void unpackStart() {
    }

    public void clear() {
        kepCellCommonContainer.clear();
        kepCellContainer.clear();
        packFilelist.clear();
    }
    
    private LogK getLogk(){
        if(logk==null){
            logk =  LogK.getInstance();
        }
        return logk;
    }
    
    @Override
    protected void finalize() throws Throwable {
        logk.close();
        super.finalize();
    }
}
