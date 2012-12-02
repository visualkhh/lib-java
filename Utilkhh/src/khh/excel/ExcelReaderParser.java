package khh.excel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ExcelReaderParser extends ExcelParser {
    private InputStream inputStream;
    private Workbook workbook;
    
    public ExcelReaderParser() {
    }
    public ExcelReaderParser(File file) throws BiffException, IOException{
        setFile(file);
    }
    
    public ExcelReaderParser(String filepath) throws BiffException, IOException{
        setFile(new File(filepath));
    }
    
    public ExcelReaderParser (InputStream fileStream) throws BiffException, IOException{
        setInputStrem(fileStream);
    }
    public ExcelReaderParser (Workbook workbook){
        setWorkbook(workbook);
    }
    public void setInputStrem(InputStream inputstream) throws BiffException, IOException{
        this.inputStream = inputstream;
        makeWorkBook(inputstream);
    }
    public void setWorkbook(Workbook workbook){
        this.workbook = workbook;
    }
    
    public void finalize(){
        if(inputStream!=null){
            try
            {
                inputStream.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        try {
            super.finalize();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        
        if(workbook!=null)
            workbook.close();
    }
    
    
    
    public void makeWorkBook(File file) throws BiffException, IOException{
         workbook = Workbook.getWorkbook(file);
    }
    public void makeWorkBook(InputStream inputstream) throws BiffException, IOException{
        workbook = Workbook.getWorkbook(inputstream);
    }
    
    public Sheet getSheet(int sheetindex){
        Sheet sheet  = workbook.getSheet(sheetindex);
        return sheet;
    }
    
    
    
    
    
    public String[] getSheetNames(){
        return workbook.getSheetNames();
    }
    public int getSheetCount(){
        return workbook.getNumberOfSheets();
    }
    
    @Override
    public    void close() {
        if(workbook!=null)
        workbook.close();
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //Workbook workbook = Workbook.getWorkbook(new File(filePAth));
//  if( workbook != null)
//  {
//      Sheet sheet  = workbook.getSheet(0);
//      int rowCount = sheet.getRows();                                 //총 행의 수를 가져옴 
//      int colCount = sheet.getColumns();                              //총 열의 수를 가져옴 
//      
//      int imgcnt  =  sheet.getNumberOfImages();
//      DebugUtil.trace("row %d  colCount %s  imgcnt %d ",rowCount,colCount,imgcnt);
//      for (int i = 0; i < rowCount; i++)
//      {
//          for (int j = 0; j < colCount; j++)
//          {
//              Cell a1 = sheet.getCell( i, j);
////                CellFormat format = a1.getCellFormat();
////                Colour z = format.getBackgroundColour();
//
//              DebugUtil.trace("%s  ",a1.getContents());
//          }
//          
//          
//      }
//      
//      
//      
//      
//      
//      for (int i = 0; i < imgcnt; i++)
//      {
//          Image img = sheet.getDrawing(i);
//          byte[] a = img.getImageData();
//          DebugUtil.trace("size img %d  ",a.length);
//          FileUtilKDN.writeFile(new File("c:\\img"+i+".png"),a);
////            DebugUtil.trace(5, a, "a");
//      }
//      
//  }
    
    
    
    
    
    //이미지 저장
//  WritableImage image = new WritableImage(5.5, 1.2, 4.2, 3.6,new File("D:/WWW/BML_eTax/assets/images/info.png")); 
//  sh.addImage(image);             

    
//  WritableWorkbook workbook = null;
//  WritableSheet sheet = null;
//  File excelFile = new File( "c:\\test.xls");
//  Label label = null;
//  long start = 0;
//  long end = 0;
//  
//  
//  
//  workbook = Workbook.createWorkbook( excelFile);
//  
//  WritableCellFormat titleFormat= new WritableCellFormat(); 
//   titleFormat.setAlignment(Alignment.CENTRE);                     // 셀 가로정열(좌/우/가운데설정가능)
//   titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE); // 셀 세로정렬(상단/중단/하단설정가능)
//   titleFormat.setBorder(Border.ALL, BorderLineStyle.THIN);  // 보더와 보더라인스타일 설정
//   titleFormat.setBackground(Colour.ICE_BLUE);                    // 배경색 설정
//
//   
//  for(int i = 0 ; i < 5; i++)
//  {
//      workbook.createSheet("sheet"+i, i);
//      sheet = workbook.getSheet(i);
//      for( int j = 0; j < 10; j++){
//          label = new Label( 0,j, i+"test cell"+j,titleFormat);
//          sheet.addCell( label);
//          sheet.mergeCells(0, j, j, j ); 
//          
//      }
//  }
//  workbook.write();
//  workbook.close();
//
//
//
    
}
