package com.kdn.p.view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Calendar;

import javax.swing.JPanel;
import javax.swing.JTextField;

import khh.date.util.DateUtil;
import khh.gui.component.frame.FrameFrame;

public class DateJump extends FrameFrame
{
	public static enum VIEWID{
		NAME_A("name_a"),
		NAME_B("name_b"),
		BTN("obtn"),
		INFO("info"),
		PATH("path");
		String id;
		VIEWID(String id){
			this.id=id;
		}
		public String getValue(){
			return this.id;
		}
	}

	public DateJump(String title)
	{
		super(title);
		super.flow();
	}
	JPanel toolbar;
	JTextField textfield_a  ;
	JTextField textfield_b  ;
//	TextArea textarea;
	JTextField path;
	Button cutButton;
	@Override
	public void onViewSetting()
	{
		setBounds(10,10,400,400);
		setVisible(true);
		
		
		 toolbar = new JPanel();
		 toolbar.setBackground(new Color(80, 80, 80));
		 textfield_a = new JTextField(10);
		 textfield_b = new JTextField(10);
		 cutButton = new Button("Replace Start");
		 cutButton.setName(VIEWID.BTN.getValue());
		 
		 toolbar.setLayout(new FlowLayout(FlowLayout.CENTER));
		 
		 toolbar.add(textfield_a);
		 toolbar.add(textfield_b);
		 toolbar.add(cutButton);
		add(toolbar,BorderLayout.NORTH);
		
		
//		textarea = new TextArea();
//		 textarea.setName(VIEWID.INFO.getValue());
//		add(textarea,BorderLayout.CENTER);
		
		
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

	}

	@Override
	public void onAction(int gb, Object o)
	{
		
		String startdate =  textfield_a.getText();
		String jumpsize = textfield_b.getText();
//		System.out.println(startdate);
//		System.out.println(jumpsize);
		int jumpdate = Integer.parseInt( jumpsize );
		
		
		try {
            Calendar startC = DateUtil.getCalender("yyyyMMdd", startdate);
            Calendar endC = DateUtil.modifyDate(startC, Calendar.DATE, jumpdate);
            
            
            path.setText(DateUtil.dateFormat(endC, "yyyy-MM-dd"));
            
            
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//		
	}

}
