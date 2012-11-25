package khh.gui.component.table;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class TableModelCustom  extends AbstractTableModel
{
	public static String[] header=new String[]{};
	public static boolean[] isCellEditable=new boolean[]{};
	Vector data = new Vector();
	 
	 
    public TableModelCustom() {
    }

    public TableModelCustom(String[] header, Object[][] data) {
        this.header = header;
        setData(data, true);
    }
	 
	 
	 public void setHeader(String[] header){
		 this.header=header;
	 }
	 public void setData(Object[][] data,boolean wantAppend){
		 if(wantAppend)
			 this.data = new Vector();
		 
        for (int i = 0; i < data.length; i++) {
            addRow(data[i]);
        }
	 }
	 
	 
	  public void addRow(Object[] row){
		    // 테이블에 한줄을 추가한다.
		    // 테이블 로우는 {업체코드,업체명,거래량,상한가,하한가,현재가} 순으로 구성된다.
		    // 배열 row의 length가 모자르거나 하면 row를 추가하지 않고, 그냥 리턴한다.

		    // STEP 1. 입력된 데이타가 VALID한지 체크한다.
		    if(row.length < 0 || row.length > header.length) return;

		    // STEP 2. 데이타 객체에 로우를 추가한다.
		    data.add(row);
		  }// addRow
	  public String getColumnName(int col){ return header[col];}
	  
	  
	 ///////////overriding
	public int getColumnCount()
	{
		
		return header.length;
	}

	public int getRowCount()
	{
		return data.size();
	}

	public Object getValueAt(int row, int col)
	{
	    // 테이블의 특정 로우와 컬럼에서의 값을 리턴한다.
		try{
	    Object[] rowData = (Object[])data.get(row);
	    return rowData[col];
		}catch (Exception e) {
			return null;
		}
	}
	
	public boolean isCellEditable(int row,int col){ return true;}
	

}
