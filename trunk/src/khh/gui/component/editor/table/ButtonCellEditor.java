package khh.gui.component.editor.table;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.EventObject;

import javax.swing.JButton;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

public class ButtonCellEditor extends JButton implements TableCellEditor
{
	  public ButtonCellEditor(){
	  }// 생성자
	  public ButtonCellEditor(String name){
		  super(name);
	  }// 생성자
  public Component getTableCellEditorComponent(JTable table,Object value,
                                    boolean isSelected,int row,int col)
  {
	  
	  ActionListener [] listeners =  getActionListeners();
	  for (int i = 0; i < listeners.length; i++)
	{
		removeActionListener(listeners[i]);
	}
	  
	  
    if(value instanceof ActionListener){
    	this.addActionListener((ActionListener) value);
    }
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
  public Object getCellEditorValue(){ return this.getActionListeners(); }
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
