package khh.excel;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import jxl.Image;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExcelWriterParser extends ExcelParser  {
    private OutputStream outputstream;
    private WritableWorkbook writableworkbook;
    
    public ExcelWriterParser() {
    }
    public ExcelWriterParser(File file) throws BiffException, IOException{
        setFile(file);
    }
    
    public ExcelWriterParser(String filepath) throws BiffException, IOException{
        setFile(new File(filepath));
    }
    
    public ExcelWriterParser (OutputStream outputstream) throws BiffException, IOException{
        setOutputStrem(outputstream);
    }
    public ExcelWriterParser (WritableWorkbook writableworkbook){
        setWritableWorkbook(writableworkbook);
    }
 
    
    public void setOutputStrem(OutputStream inputstream) throws BiffException, IOException{
        this.outputstream = inputstream;
        makeWorkBook(inputstream);
    }
    public void setWritableWorkbook(WritableWorkbook writableworkbook){
        this.writableworkbook = writableworkbook;
    }
    
    public void finalize(){
        if(outputstream!=null){
            try
            {
                outputstream.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        
        
        if( writableworkbook !=null){
            try {
                writableworkbook.close();
            } catch (WriteException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        try {
            super.finalize();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        
        
    }
    
    
    
    public void makeWorkBook(File file) throws BiffException, IOException{
         writableworkbook = Workbook.createWorkbook(file);
    }
    private void makeWorkBook(OutputStream outputstream) throws BiffException, IOException{
        writableworkbook = Workbook.createWorkbook(outputstream);
    }
    
    public Sheet getSheet(int sheetindex){
        Sheet sheet  = writableworkbook.getSheet(sheetindex);
        return sheet;
    }
    public WritableSheet getWritableSheet(int sheetindex){
        WritableSheet sheet  = writableworkbook.getSheet(sheetindex);
        return sheet;
    }
    
    
    
    public String[] getSheetNames(){
        return writableworkbook.getSheetNames();
    }
    public int getSheetCount(){
        return writableworkbook.getNumberOfSheets();
    }
    
  
    
    public void createSheet(int sheetindex,String sheetname){
        writableworkbook.createSheet(sheetname,sheetindex);
    }
    public void addCell(int sheetindex, Label label) throws RowsExceededException, WriteException{
        getWritableSheet(sheetindex).addCell( label);
    }
    
    public void addImage(int sheetindex,double colindex, double rowindex, double width_col, double height_row,String onlyPNGfile){
        this.addImage(sheetindex,colindex, rowindex, width_col, height_row, new File(onlyPNGfile));
    }
    public void addImage(int sheetindex,double colindex, double rowindex, double width_col, double height_row,File onlyPNGfile){
        WritableImage image = new WritableImage(colindex, rowindex, width_col, height_row,onlyPNGfile);
        addImage(sheetindex,image);
    }
    public void addImage(int sheetindex,double colindex, double rowindex, double width_col, double height_row,byte[] imgdata){
        WritableImage image = new WritableImage(colindex, rowindex, width_col, height_row,imgdata);
        addImage(sheetindex,image);
    }
    public void addImage(int sheetindex,WritableImage image){
        getWritableSheet(sheetindex).addImage(image);             
    }
    public void addImage(int sheetindex,double colindex, double rowindex, double width_col, double height_row,Image img){
        addImage(sheetindex, img.getColumn(), img.getRow(), img.getWidth(), img.getHeight(), img.getImageData());
    }
    
    
    public void mergeCells(int sheetindex,int start_col,int start_row, int end_col, int end_row) throws RowsExceededException, WriteException{
        getWritableSheet(sheetindex).mergeCells(start_col,start_row, end_col ,end_row); 
    }
    
    public void mergeCellsRelativity (int sheetindex,int start_col,int start_row, int rend_col, int rend_row) throws RowsExceededException, WriteException{
        getWritableSheet(sheetindex).mergeCells(start_col,start_row, start_col+rend_col ,start_row+rend_row); 
    }
    
    
    public void setColumnSize(int sheetindex,int columnindex,int size){
        getWritableSheet(sheetindex).setColumnView(columnindex, (int) ((int)size*1.02424));
//        getColumnView(sheetindex, columnindex).setSize(size);
    }
    public void setRowSize(int sheetindex,int rowindex,int size) throws RowsExceededException{
        getWritableSheet(sheetindex).setRowView(rowindex, size*20);
//        getRowView(sheetindex, rowindex).setSize(size);
    }
    
    public void write() throws IOException{
        writableworkbook.write();
    }
    public void close()  {
        try {
            if(writableworkbook!=null)
            writableworkbook.close();
        } catch (WriteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
