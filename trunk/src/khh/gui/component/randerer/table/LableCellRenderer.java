package khh.gui.component.randerer.table;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

public class LableCellRenderer extends JLabel implements TableCellRenderer
{
  protected static Border noFocusBorder;

  /**
   * 생성자
   */
  public LableCellRenderer(){
    noFocusBorder = new EmptyBorder(1,2,1,2);
  }// 생성자
  public LableCellRenderer(String name){
	  super(name);
  }

  public Component getTableCellRendererComponent(JTable table,
    Object value,boolean isSelected,boolean hasFocus,
    int row,int col)
  {
    if(value instanceof String){
    	this.setText((String)value);
    }// if

    // 셀이 선택되었을 경우등에 출력을 바탕색등의 출력을 좀더 이쁘게 하기 위해서
    // 추가. 없어도 프로그램 수행에는 지장이 없음
//    setBackground(isSelected && !hasFocus ?
//                   table.getSelectionBackground() : table.getBackground() );
//    setForeground(isSelected && !hasFocus ?
//                    table.getSelectionForeground() : table.getForeground() );
//    setBorder(hasFocus ? UIManager.getBorder("Table.focusCellHighlightBorder")
//                : noFocusBorder );
    return this;
  }// getTableCellRendererComponent
}// class CheckCellRenderer
