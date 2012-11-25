package com.kdn.p.view;

import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.kdn.gui.frame.FrameFrame;
import com.kdt.util.Utilities;

public class PasswordConversion extends FrameFrame
{
	public PasswordConversion(String title)
	{
		super(title);
		super.flow();
	}

	public static enum VIEWID{
		BTN("obtn"),
		CH("ch"),
		INPUT("input"),
		OUTPUT("output");
		String id;
		VIEWID(String id){
			this.id=id;
		}
		public String getValue(){
			return this.id;
		}
	}
	
	
	
	
	@Override
	public void onViewSetting()
	{
		setBounds(10,10,400,400);
		setVisible(true);
		
		GridLayout layout=new GridLayout(3,4);
		setLayout(layout);
		
		Panel topPanel = new Panel();
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		Button b1=new Button("GoChange");
		b1.setName(VIEWID.BTN.getValue());
		
		TextField ch= new TextField("",20);
		ch.setName(VIEWID.CH.getValue());
		topPanel.add(b1);
		topPanel.add(ch);
		
		
		
		TextField tif= new TextField("",20);
		tif.setName(VIEWID.INPUT.getValue());
		
		TextField tof= new TextField("",20);
		tof.setName(VIEWID.OUTPUT.getValue());
		
		
		
		topPanel.setBackground(new Color(40,40,40));
		
		
		this.add(topPanel);
		this.add(tif);
		this.add(tof);
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
		Button b = (Button) getComponent(VIEWID.BTN.getValue());

		
		
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				TextField tif = (TextField) getComponent("input");
				String in  = tif.getText();
				String out="";
				TextField tf = (TextField) getComponent(VIEWID.CH.getValue());
				String chnum = tf.getText();
				chnum = chnum.equals("")?"0":chnum;
				int ch = Integer.parseInt(chnum);
				
				
				
				for (int i = 0; i < in.length(); i++)
				{
					char c = in.charAt(i);
					char g = (char) ((char)c+ch);
					out+=g;
				}
				
				TextField tof = (TextField) getComponent("output");
				tof.setText(out);
			}
		});
	}

	@Override
	public void onAction(int gb, Object o)
	{
		// TODO Auto-generated method stub
		
	}




}
