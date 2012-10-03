package khh.gui.component.editor.table;

import java.awt.Component;
import java.util.EventObject;

import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

public class ScrollBarCellEditor extends JScrollBar implements TableCellEditor
{
  public ScrollBarCellEditor(){
    super(JScrollBar.HORIZONTAL);
  }// 생성자

  public Component getTableCellEditorComponent(JTable table,Object value,
                                    boolean isSelected,int row,int col)
  {
    if(value instanceof Integer){
      Integer intValue = (Integer)value;
      this.setValue(intValue.intValue());
    }// if
    return this;
  }// getTableCellEditorComponent

  /**
   * TableCellEditor interface Implement
   */
  public void removeCellEditorListener(CellEditorListener cel){
  }
  public void addCellEditorListener(CellEditorListener cel){
  }
  public void cancelCellEditing(){ fireEditingCanceled();}
  public Object getCellEditorValue(){ return new Integer(getValue()); }
  public boolean isCellEditable(EventObject eo) { return true;}
  public boolean shouldSelectCell(EventObject eo){ return true;}
  public boolean stopCellEditing(){
    fireEditingStopped();
    return true;
  }
  protected void fireEditingCanceled(){
  }
  protected void fireEditingStopped(){
  }
}// class ScrollBarCellEditor
