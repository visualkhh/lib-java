package khh.excel;

import java.io.File;
import java.io.IOException;

import jxl.Cell;
import jxl.CellView;
import jxl.Image;
import jxl.Range;
import jxl.Sheet;
import jxl.read.biff.BiffException;
import jxl.write.WritableSheet;

abstract class ExcelParser {
    private String filePath;
    private File   file;
    public ExcelParser(){
    }
    public ExcelParser(String path) throws BiffException, IOException{
        setFilePath(path);
    }
    public ExcelParser(File file) throws BiffException, IOException{
        setFile(file);
    }
    
    public void setFilePath(String filepath) throws BiffException, IOException{
        setFile(new File(filepath));
    }
    public void setFile(File file) throws BiffException, IOException{
        this.filePath = file.getAbsolutePath();
        this.file = file;
        makeWorkBook(file);
    }
    
   public abstract void makeWorkBook(File file) throws BiffException, IOException;
   public abstract Sheet getSheet(int sheetindex);
   public abstract String[] getSheetNames();
   public abstract int getSheetCount();
   public abstract void close();
    
    
    
    
    public String getContents(int sheetindex, int colindex, int rowindex){
        Sheet sheet  = getSheet(sheetindex);
        Cell a1 = sheet.getCell(colindex,rowindex);
        return a1.getContents();
    }
    public byte[] getImageData(int sheetindex,int imgindex){
        Sheet sheet  = getSheet(sheetindex);
        Image img = sheet.getDrawing(imgindex);
        byte[] a = img.getImageData();
        return a;
    }
    public Image getImages(int sheetindex,int imgindex){
        Sheet sheet  = getSheet(sheetindex);
        Image img = sheet.getDrawing(imgindex);
        return img;
    }
    public Range[] getMergedCells(int sheetindex){
        Sheet sheet  = getSheet(sheetindex);
        Range[] range = sheet.getMergedCells();
        return range;
    }
    public int getRowCount(int sheetindex){
        Sheet sheet  = getSheet(sheetindex);
        return sheet.getRows(); 
    }
    public int getColumnCount(int sheetindex){
        Sheet sheet  = getSheet(sheetindex);
        WritableSheet a =null;
        return sheet.getColumns(); 
    }
    public int getImageCount(int sheetindex){
        Sheet sheet  = getSheet(sheetindex);
        return sheet.getNumberOfImages(); 
    }
    
    public CellView getColumnView(int sheetindex,int columnindex){
        return getSheet(sheetindex).getColumnView(columnindex);
    }
    public CellView getRowView(int sheetindex,int rowindex){
        return getSheet(sheetindex).getRowView(rowindex);
    }
    public double getColumnWidth(int sheetindex,int columnindex){
        return getColumnView(sheetindex,columnindex).getSize();
    }
    public double getRowWidth(int sheetindex,int rowindex){
        return getRowView(sheetindex,rowindex).getSize();
    }
    
    public Cell[]  getRow(int sheetindex, int rowindex){
        Cell[] c = getSheet(sheetindex).getRow(rowindex);
        return c;
    }
    public Cell[]  getColumn(int sheetindex, int colindex){
        Cell[] c = getSheet(sheetindex).getColumn(colindex);
        return c;
    }
}
