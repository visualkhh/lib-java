package com.kdn.p.view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.Utilities;

import jxl.Cell;
import jxl.Image;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import khh.debug.util.DebugUtil;
import khh.excel.ExcelReaderParser;
import khh.excel.ExcelWriterParser;
import khh.file.util.FileUtil;
import khh.gui.component.frame.FrameFrame;
import khh.std.Standard;
import khh.string.util.StringUtil;

public class ExcelParser extends FrameFrame
{
    public static enum VIEWID{
        NAME_A("name_a"),
        NAME_B("name_b"),
        BTN("obtn"),
        CSVBTN("obtn2"),
        INFO("info"),
        INFO2("info2"),
        PATH("path");
        String id;
        VIEWID(String id){
            this.id=id;
        }
        public String getValue(){
            return this.id;
        }
    }

    public ExcelParser(String title)
    {
        super(title);
        super.flow();
    }
    JPanel toolbar;
//    JTextField textfield_a  ;
//    JTextField textfield_b  ;
  TextArea textarea;
  TextArea textarea2;
    JTextField path;
    Button csvButton;
    Button cutButton;
    @Override
    public void onViewSetting()
    {
        setBounds(10,10,400,400);
        setVisible(true);
        
        
         toolbar = new JPanel();
         toolbar.setBackground(new Color(80, 80, 80));
//         textfield_a = new JTextField(10);
//         textfield_b = new JTextField(10);
         cutButton = new Button("Replace Start");
         cutButton.setName(VIEWID.BTN.getValue());
         csvButton = new Button("CSV Start");
         csvButton.setName(VIEWID.CSVBTN.getValue());
         
         toolbar.setLayout(new FlowLayout(FlowLayout.CENTER));
         
//         toolbar.add(textfield_a);
//         toolbar.add(textfield_b);
         toolbar.add(cutButton);
         toolbar.add(csvButton);
        add(toolbar,BorderLayout.NORTH);
        
        ///////////////
      textarea = new TextArea();
       textarea.setName(VIEWID.INFO.getValue());
       
       textarea2 = new TextArea();
       textarea2.setName(VIEWID.INFO.getValue());
       
       JPanel   toolbar2 = new JPanel();
       toolbar2.setLayout(new FlowLayout(FlowLayout.CENTER));
       
       toolbar2.add(textarea);
       toolbar2.add(textarea2);
       
      add(toolbar2,BorderLayout.CENTER);
        //////////
        
        path = new JTextField(20);
         path.setName(VIEWID.PATH.getValue());
        add(path,BorderLayout.SOUTH);
        
        
        
        this.pack();
    }

    @Override
    public void onDataSetting()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void onAddListener()
    {
        Button jb = (Button) getComponent(VIEWID.BTN.getValue());
        jb.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                onAction(1);
                
            }
        });
         jb = (Button) getComponent(VIEWID.CSVBTN.getValue());
        jb.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                onAction(2);
                
            }
        });

    }

    @Override
    public void onAction(int gb, Object o)
    {
        
        if(gb==1){
            onCut();
        }else if(gb==2){
            onCSV();
        }
      
        
    }

    
    
    
    
    private void onCSV() {
  File[] file =null;
        
        try {
            file = FileUtil.getFileListToFile(path.getText());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        for (int fileindex = 0; fileindex < file.length; fileindex++) {
                File fileat = file[fileindex];
                if(fileat.isDirectory())
                    continue;
                
                String foldername="info.txt";
                String filePAth = path.getText()+"\\"+fileat.getName();
                String outcsv = path.getText()+"\\"+foldername;
                try
                {
                    ExcelReaderParser reader = new ExcelReaderParser(filePAth);
//                    ArrayList<String>  startrowindex  = getCSV(reader);
                    ArrayList<String>  startrowindex  = getCSVHWP(reader);
                    
                    for (int i = 0; i < startrowindex.size(); i++) {
                        textarea2.append(startrowindex.get(i)+"\r\n");
                        FileUtil.writeFile(outcsv, startrowindex.get(i)+"\r\n",true);
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
        }
        
    }

    private void onCut() {
  File[] file =null;
        
        try {
            file = FileUtil.getFileListToFile(path.getText());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        for (int fileindex = 0; fileindex < file.length; fileindex++) {
                File fileat = file[fileindex];
                if(fileat.isDirectory())
                    continue;
                
                String filePAth = path.getText()+"\\"+fileat.getName();
                String foldername = fileat.getName().split("\\.")[0];
                String outfolder = path.getText()+"\\"+foldername;
                DebugUtil.trace("open : %s \nsavefolder : %s ",filePAth,outfolder);
                try
                {
                    ExcelReaderParser reader = new ExcelReaderParser(filePAth);
                    new File(outfolder).mkdirs();
                    ExcelWriterParser writer = null;
                    
                    ArrayList<Standard<String, Integer>>  startrowindex  = getStartRowindex(reader);
                    
                    textarea.append("startrowindex   " + startrowindex.size()+"\n");
                    int sheetindex = 0 ;
                    int endcnt=16;
                    int imgindex=0;
                    for (int i = 0; i < startrowindex.size(); i++) {
                        String title=foldername+"\t"+startrowindex.get(i).getKey()+"\t"+startrowindex.get(i).getValue();
                        textarea.append(title);
                        writer =  new ExcelWriterParser(outfolder+"\\"+startrowindex.get(i).getKey()+".xls");
                        writer.createSheet(0, "sheet01");
                        int rowindex = startrowindex.get(i).getValue();
                        int addindex=0;
        //              System.out.println("img cnt "+reader.getImageCount(sheetindex));
                        try{
                            for (int startindex = rowindex; startindex < rowindex+endcnt; startindex++) {
                                Cell[] cells = reader.getRow(sheetindex, startindex);
                                writeExcel(writer, addindex, cells);
                                addindex++;
                            }
                            Image[] img = {reader.getImages(sheetindex, imgindex++),reader.getImages(sheetindex, imgindex++)};
                            writeImage(writer,img);
                            setStyle(writer);
                            writer.write();
                            writer.close();
                            addindex=0;
                            textarea.append("\t 성공\n");
                        }catch (Exception e) {
                            textarea.append("\t 실패\n");
                        }finally{
                        }
                    }
                    reader.close();
                    System.gc();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
        }
        
    }

    private void writeImage(ExcelWriterParser writer, Image[] img) {
        int sheetindex=0;
        writer.addImage(sheetindex, 1.0, 15.0, 3.0, 1.0, img[0].getImageData());
        writer.addImage(sheetindex, 4.0, 15.0, 2.0, 1.0, img[1].getImageData());
    }

    private void setStyle(ExcelWriterParser writer) throws RowsExceededException, WriteException {
        int sheetindex=0;
        //공통점의조서
        writer.mergeCellsRelativity(sheetindex, 1, 0, 4, 0);
        
        //검사점번호
        writer.mergeCellsRelativity(sheetindex, 2, 1, 1, 0);
        
        //소재지
        writer.mergeCellsRelativity(sheetindex, 2, 2, 3, 0);
        
        //계획기간
        writer.mergeCellsRelativity(sheetindex, 2, 3, 1, 0);
        
        //관측일자
        writer.mergeCellsRelativity(sheetindex, 2, 4, 1, 0);
        
        //성과
        writer.mergeCellsRelativity(sheetindex, 1, 5, 0, 2);
        
        //GRS80
        writer.mergeCellsRelativity(sheetindex, 2, 5, 1, 0);
        
        //BESSEL
        writer.mergeCellsRelativity(sheetindex, 4, 5, 1, 0);
        
        //중부원점
        writer.mergeCellsRelativity(sheetindex, 2, 8, 3, 0);
        
        //경로 
        writer.mergeCellsRelativity(sheetindex, 1, 9, 0, 3);
        //경로 내용
        writer.mergeCellsRelativity(sheetindex, 2, 9, 3, 3);
        
        //변화대상
        writer.mergeCellsRelativity(sheetindex, 1, 13, 2, 0);
        //수치지형도
        writer.mergeCellsRelativity(sheetindex, 4, 13, 1, 0);
        //근경
        writer.mergeCellsRelativity(sheetindex, 1, 14, 2, 0);
        //약도
        writer.mergeCellsRelativity(sheetindex, 4, 14, 1, 0);
        //근경내용
        writer.mergeCellsRelativity(sheetindex, 1, 15, 2, 0);
        //약도내용
        writer.mergeCellsRelativity(sheetindex, 4, 15, 1, 0);
        
        writer.setColumnSize(sheetindex, 0, 5);
        writer.setColumnSize(sheetindex, 1, 13);
        writer.setColumnSize(sheetindex, 2, 13);
        writer.setColumnSize(sheetindex, 3, 13);
        writer.setColumnSize(sheetindex, 4, 13);
        writer.setColumnSize(sheetindex, 5, 25);
        
    //    
        for (int i = 0; i < writer.getRowCount(sheetindex); i++) {
            writer.setRowSize(sheetindex, i, 30);
        }
        writer.setRowSize(sheetindex, 15, 200);
        }

    private String writeExcel(ExcelWriterParser writer, int rowindex, Cell[] cells) throws RowsExceededException, WriteException {
        String filename=null;
//        System.out.print(rowindex);
        for (int colindex = 0; colindex < cells.length; colindex++) {
            Cell cell = cells[colindex];
            try{
                String content = cell.getContents();
//                System.out.print(content);
                Label label=null;
                if(cell.getCellFormat()!=null){
                  WritableCellFormat titleFormat= new WritableCellFormat(cell.getCellFormat()); 
                titleFormat.setBorder(Border.ALL, BorderLineStyle.THIN);  // 보더와 보더라인스타일 설정
                  label = new Label(colindex, rowindex, cell.getContents(),titleFormat);
                  
                  
                }else{
                    label = new Label(colindex, rowindex, cell.getContents());
                }
                writer.addCell( 0, label);
              }catch (Exception e) {
              //label = new Label(colindex, rowindex, v);
              }
            
            
        }
        int  sheetindex=0;
//        System.out.println();

        return filename;
            
    }





        private ArrayList<Standard<String,Integer>>  getStartRowindex(ExcelReaderParser reader) {
            
            ArrayList<Standard<String,Integer>> index = new ArrayList<Standard<String,Integer>>();
            int sheetcnt = reader.getSheetCount();
            for (int sheetindex = 0; sheetindex < sheetcnt; sheetindex++) {
                int rowcnt = reader.getRowCount(sheetindex);
                
                Standard<String, Integer> st = new Standard<String, Integer>();
                for (int rowindex = 0; rowindex < rowcnt; rowindex++) {
                    Cell[] cells = reader.getRow(sheetindex, rowindex);
                    for (int colindex = 0; colindex < cells.length; colindex++) {
                        Cell cell = cells[colindex]; 
                        String contents =cell.getContents();
                        if(contents==null)
                            continue;
                        
//                        if(StringUtil.deleteAllSpace(v).equals("공통점의조서")){
                        if(StringUtil.deleteAllSpace(contents).equals("검사점의조서") || StringUtil.deleteAllSpace(contents).equals("공통점의조서")){
                            st.setValue(rowindex);
                        }
                        if(StringUtil.deleteAllSpace(contents).equals("검사점번호")){
                            st.setKey(cells[colindex+1].getContents());
                        }

                        System.out.print("..");
                    }
                    
                    if(st.getKey()!=null && st.getValue()!=null){
                        index.add(st);
                        st = new Standard<String, Integer>();
                    }
                }
            }
            return index;
        }
    
    
    
        
        
        
        
        
        
        
        
        
        
     private ArrayList<String>  getCSV(ExcelReaderParser reader) {
            ArrayList<String> index = new ArrayList<String>();
            int sheetcnt = reader.getSheetCount();
            for (int sheetindex = 0; sheetindex < sheetcnt; sheetindex++) {
                int rowcnt = reader.getRowCount(sheetindex);
                
                String gumsanum           =null; //검사점번호
                String doynum               =null; //도업번호
                String soje                    =null; //소재지
                String point                  =null; //좌표원점
                String mymd                 =null; //관측일자
                String x                            =null; //X(North)
                String y                         =null; //Y(East)
                String songga                   =null; //성과
                String mpeple                   =null; //관측자
                String root                        =null; //경로
                String filename                 =null; //파일이름
                
             
                    
                    
                
                
                String result="";
                for (int rowindex = 0; rowindex < rowcnt; rowindex++) {
                    Cell[] cells = reader.getRow(sheetindex, rowindex);
                    for (int colindex = 0; colindex < cells.length; colindex++) {
                        Cell cell = cells[colindex]; 
                        String contents =cell.getContents();
                        if(contents==null)
                            continue;
                        
                        if(StringUtil.deleteAllSpace(contents).equals("검사점번호")){
                            gumsanum = cells[colindex+1].getContents();
                        }
                        if(StringUtil.deleteAllSpace(contents).equals("도엽번호")){
                            doynum = cells[colindex+1].getContents();
                        }
                        if(StringUtil.deleteAllSpace(contents).equals("소재지")){
                            soje = cells[colindex+1].getContents();
                            soje = StringUtil.replaceUL(soje, ",", ".");
                        }
                        if(StringUtil.deleteAllSpace(contents).equals("좌표원점")){
                            point = cells[colindex+1].getContents();
                            point = StringUtil.replaceUL(point, ",", ".");
                        }
                        if(StringUtil.deleteAllSpace(contents).equals("관측일자")){
                            mymd = cells[colindex+1].getContents();
                            mymd = StringUtil.replaceUL(mymd, ",", ".");
                        }
//                        if(StringUtil.deleteAllSpace(contents).equals("관측일자")){
//                            result+=(cells[colindex+1].getContents()+",");
//                        }
                        if(StringUtil.deleteAllSpace(contents).equals("GRS80") ){
                            Cell[] cells2 = reader.getRow(sheetindex, rowindex+1);
                            x = cells2[colindex+1].getContents();
                        }
                        if(StringUtil.deleteAllSpace(contents).equals("GRS80") ){
                            Cell[] cells2 = reader.getRow(sheetindex, rowindex+2);
                            y = cells2[colindex+1].getContents();
                        }
                        
//                        if(StringUtil.deleteAllSpace(contents).equals("성과")){
//                            songga = cells[colindex+1].getContents();
//                        }
                        if(StringUtil.deleteAllSpace(contents).equals("GRS80")){
                            songga = cells[colindex].getContents();
                        }
                        if(StringUtil.deleteAllSpace(contents).equals("관측자")){
                            mpeple = cells[colindex+1].getContents();
                        }
//                        if(StringUtil.deleteAllSpace(contents).equals("관측자")){
//                            result+=(cells[colindex+1].getContents()+",,");
//                        }
                        
                        if(StringUtil.deleteAllSpace(contents).equals("경로")){
                            root = cells[colindex+1].getContents();
                            root = StringUtil.replaceUL(root, ",", ".");
                        }
                        if(StringUtil.deleteAllSpace(contents).equals("검사점번호")){
                            filename = cells[colindex+1].getContents()+".xls";
                        }
                        System.out.print(".");
                    }
                    
                    
                    
                    if(
                    gumsanum !=null && doynum    !=null && soje      !=null && point     !=null &&
                    mymd      !=null && x         !=null && y         !=null && songga    !=null &&
                    mpeple    !=null && root      !=null && filename !=null 
                    ){
                        result = gumsanum+","+doynum+",,"+soje+","+point+",한국전력공사,"+mymd+","+mymd+","+x+","+y+",0,"+songga+",4,"+mpeple+","+mpeple+",,"+root+",,,,,,,,,"+filename+",,,,";
                        index.add(result);
                        gumsanum           =null; //검사점번호               
                        doynum               =null; //도업번호              
                        soje                    =null; //소재지            
                        point                  =null; //좌표원점            
                        mymd                 =null; //관측일자              
                        x                            =null; //X(North)  
                        y                         =null; //Y(East)      
                        songga                   =null; //성과            
                        mpeple                   =null; //관측자           
                        root                        =null; //경로         
                        filename                 =null; //파일이름          
                                                                        
                    }
                }
            }
            return index;
        }
    
     private ArrayList<String>  getCSVHWP(ExcelReaderParser reader) {
         ArrayList<String> index = new ArrayList<String>();
         int sheetcnt = reader.getSheetCount();
         for (int sheetindex = 0; sheetindex < sheetcnt; sheetindex++) {
             int rowcnt = reader.getRowCount(sheetindex);
             
             String gumsanum           =null; //검사점번호
             String doynum               =null; //도업번호
             String soje                    =null; //소재지
             String point                  =null; //좌표원점
             String mymd                 =null; //관측일자
             String x                            =null; //X(North)
             String y                         =null; //Y(East)
             String songga                   =null; //성과
             String mpeple                   =null; //관측자
             String root                        =null; //경로
             String filename                 =null; //파일이름
             
          
                 
                 
             
             
             String result="";
             for (int rowindex = 0; rowindex < rowcnt; rowindex++) {
                 Cell[] cells = reader.getRow(sheetindex, rowindex);
                 for (int colindex = 0; colindex < cells.length; colindex++) {
                     Cell cell = cells[colindex]; 
                     String contents =cell.getContents();
                     if(contents==null)
                         continue;
                     
                     System.out.println(StringUtil.deleteAllSpace(contents));
                     
                     if(StringUtil.deleteAllSpace(contents).equals("번호")){
                         gumsanum = cells[colindex+1].getContents();
                     }
                     if(StringUtil.deleteAllSpace(contents).equals("도엽번호")){
                         doynum = cells[colindex+1].getContents();
                     }
                     if(StringUtil.deleteAllSpace(contents).equals("소재지")){
                         soje = cells[colindex+1].getContents();
                         soje = StringUtil.replaceUL(soje, ",", ".");
                     }
                     if(StringUtil.deleteAllSpace(contents).equals("좌표원점")){
                         point = cells[colindex+1].getContents();
                         point = StringUtil.replaceUL(point, ",", ".");
                     }
                     if(StringUtil.deleteAllSpace(contents).equals("관측일자")){
                         mymd = cells[colindex+1].getContents();
                         mymd = StringUtil.replaceUL(mymd, ",", ".");
                     }
//                     if(StringUtil.deleteAllSpace(contents).equals("관측일자")){
//                         result+=(cells[colindex+1].getContents()+",");
//                     }
                     if(StringUtil.deleteAllSpace(contents).equals("GRS80") ){
                         Cell[] cells2 = reader.getRow(sheetindex, rowindex+1);
                         x = cells2[colindex+1].getContents();
                     }
                     if(StringUtil.deleteAllSpace(contents).equals("GRS80") ){
                         Cell[] cells2 = reader.getRow(sheetindex, rowindex+2);
                         y = cells2[colindex+1].getContents();
                     }
                     
//                     if(StringUtil.deleteAllSpace(contents).equals("성과")){
//                         songga = cells[colindex+1].getContents();
//                     }
                     if(StringUtil.deleteAllSpace(contents).equals("GRS80")){
                         songga = cells[colindex].getContents();
                     }
                     if(StringUtil.deleteAllSpace(contents).equals("관측자")){
                         mpeple = cells[colindex+1].getContents();
                     }
//                     if(StringUtil.deleteAllSpace(contents).equals("관측자")){
//                         result+=(cells[colindex+1].getContents()+",,");
//                     }
                     
                     if(StringUtil.deleteAllSpace(contents).equals("경로")){
                         Cell[] cells2 = reader.getRow(sheetindex, rowindex+1);
                         root = cells2[colindex+1].getContents();
                         root = StringUtil.replaceUL(root, ",", ".");
                     }
                     if(StringUtil.deleteAllSpace(contents).equals("번호")){
                         filename = cells[colindex+1].getContents()+".hwp";
                     }
                     System.out.print(".");
                 }
                 
                 
                 
                 if(
                 gumsanum !=null && doynum    !=null && soje      !=null && point     !=null &&
                 mymd      !=null && x         !=null && y         !=null && songga    !=null &&
                 mpeple    !=null && root      !=null && filename !=null 
                 ){
                     result = gumsanum+","+doynum+",,"+soje+","+point+",한국전력공사,"+mymd+","+mymd+","+x+","+y+",0,"+songga+",4,"+mpeple+","+mpeple+",,"+root+",,,,,,,,,"+filename+",,,,";
                     index.add(result);
                     gumsanum           =null; //검사점번호               
                     doynum               =null; //도업번호              
                     soje                    =null; //소재지            
                     point                  =null; //좌표원점            
                     mymd                 =null; //관측일자              
                     x                            =null; //X(North)  
                     y                         =null; //Y(East)      
                     songga                   =null; //성과            
                     mpeple                   =null; //관측자           
                     root                        =null; //경로         
                     filename                 =null; //파일이름          
                                                                     
                 }
             }
         }
         return index;
     }
    
}
