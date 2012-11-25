package com.kdn.p.view;

import java.awt.Component;
import java.awt.FileDialog;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.DefaultCellEditor;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import com.kdn.gui.component.editor.table.ButtonCellEditor;
import com.kdn.gui.component.randerer.table.ButtonCellRenderer;
import com.kdn.gui.component.randerer.table.LableCellRenderer;
import com.kdn.gui.component.table.TableModelCustom;
import com.kdn.gui.frame.FrameFrame;
import com.kdn.util.property.PropertyUtil;
import com.kdt.util.Utilities;
import com.kdt.util.xml.XMLparser;

public class FolderOpen extends FrameFrame
{

	public static enum VIEWID{
		MENUBAR("MENUBAR"),
		FILEMENU("FILEMENU"),
		FILEOPENMENUITEM("FILEOPENMENUITEM"),
		TABLE("TABLE"),
		;
		String id;
		VIEWID(String id){
			this.id=id;
		}
		public String getValue(){
			return this.id;
		}
	}
	
	public static enum ACTION{
		FILEOPEN(Utilities.getNextNumber())
		;
		int id;
		ACTION(int id){
			this.id=id;
		}
		public int getValue(){
			return this.id;
		}
	}
	
	
	FileDialog ofile=new FileDialog(this,"OpenFile",FileDialog.LOAD);
	
	
	private String[] header = {"경로","설명","열기"};// 컬럼명
	
	
	public FolderOpen(String title)
	{
		super(title);
		super.flow();
	}

	@Override
	public void onViewSetting()
	{
		
		try
		{// 매킨토시형 LOOK and Feel 적용
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		
		setBounds(10,10,400,400);
		setVisible(true);
		
		
        MenuBar  menuBar = new MenuBar(); //메뉴바생성
        menuBar.setName(VIEWID.MENUBAR.getValue());
        
        
        Menu fileMenu = new Menu(VIEWID.FILEMENU.getValue()); //메뉴생성
        fileMenu.setName(VIEWID.FILEMENU.getValue());
        
        MenuItem mi= new MenuItem(VIEWID.FILEOPENMENUITEM.getValue());
        mi.setName(VIEWID.FILEOPENMENUITEM.getValue());
        fileMenu.add(mi);
        
        
        menuBar.add(fileMenu);
        setMenuBar(menuBar);
        
        
        TableModelCustom tablemodel = new TableModelCustom();
        tablemodel.setHeader(header);
//        tablemodel.addRow(new String[]{"1","1"});
//        tablemodel.addRow(new String[]{"12","12"});
        
        JTable table = new JTable();
        table.setAutoCreateColumnsFromModel(false);
        table.setModel(tablemodel);
        table.setName(VIEWID.TABLE.getValue());
        
        
        for(int i=0;i<header.length;i++){
        	// STEP 1. Table Cell Renderer 생성 및 지정
        	TableCellRenderer renderer =null;
        	// STEP 2. Table Cell Editor 생성 및 지정
        	TableCellEditor editor = null;;
        	if(i==2){
        		renderer = new LableCellRenderer("여기를 클릭하면 열기를할수있음");
        		editor = new ButtonCellEditor("열기");
        	}else{
        		renderer = new DefaultTableCellRenderer();        	
        		editor = new DefaultCellEditor(new JTextField());
        	}

            // STEP 3. 컬럼을 생성하여 테이블에 삽입
            TableColumn column = new TableColumn(i,10,renderer,editor);
            table.addColumn(column);
          }
        
        
        
        
        
        
        
        
        JScrollPane scrollPane = new JScrollPane(table);
        
        add(scrollPane);

        
	}

	@Override
	public void onDataSetting()
	{
//		JTable j = (JTable) getComponent(VIEWID.TABLE.getValue());
//		System.out.println(j.getModel().toString());
	}

	@Override
	public void onAddListener()
	{
		MenuItem m = (MenuItem) getComponent(VIEWID.FILEOPENMENUITEM.getValue());
		m.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				ofile.setVisible(true);
				if(ofile.getDirectory()!=null && ofile.getFile()!=null){
					onAction(ACTION.FILEOPEN.getValue(), ofile);
				}
			}
		});
	}

	@Override
	public void onAction(int gb, Object o)
	{
		if(gb==ACTION.FILEOPEN.getValue()){
			onAction_FileOpen((FileDialog)o);
		}

	}

	private void onAction_FileOpen(FileDialog filedialog)
	{
		String filePAth = filedialog.getDirectory()+filedialog.getFile();
		String xmlpath="//PATHS/path";
		
		JTable  table = (JTable) getComponent(VIEWID.TABLE.getValue());
		TableModelCustom tablemodel = (TableModelCustom) table.getModel();
		tablemodel.setHeader(header);
		try
		{
			XMLparser parser = new XMLparser(filePAth);
			int size =  parser.getInt("count("+xmlpath+")");
			for (int i = 1; i <=size; i++)
			{
				String subxmlpath = xmlpath="//PATHS/path["+i+"]";
				final String url = parser.getString(subxmlpath+"/url");
				String command = parser.getString(subxmlpath+"/command");
				
				ActionListener actionListener= new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e)
					{
						System.out.println(e.getID());
						String osname = PropertyUtil.getOSName();
						if(osname.indexOf("Window")>=0){
							try
							{
								Utilities.executeSystem("explorer "+url);
							}
							catch (IOException e1)
							{
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							System.out.println(url);
							
						}
					}
				};
				Object[] data = new Object []{url,command,actionListener};
				tablemodel.addRow(data);
			}
			tablemodel.fireTableDataChanged();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
