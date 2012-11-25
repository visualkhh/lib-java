package khh.gui.component.randerer.table;

import java.awt.Component;

import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ScrollBarCellRenderer extends JScrollBar implements TableCellRenderer
{
  /**
   * 생성자
   */
  public ScrollBarCellRenderer(){
    super(JScrollBar.HORIZONTAL); // 스크롤바의 방향을 횡방향으로 설정
    this.setMaximum(10000);
  }// 생성자
  public Component getTableCellRendererComponent(JTable table,
                                Object value,boolean isSelected,
                                boolean hasFocus,int row,int col)
  {
    if(value instanceof Integer){
      Integer intValue = (Integer)value;
      this.setValue(intValue.intValue());
    }else{
      this.setValue(0);
    }
    return this;
  }// getTableCellRendererComponent
}// ScrollBarCellRenderer

