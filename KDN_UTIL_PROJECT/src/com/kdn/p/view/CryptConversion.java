package com.kdn.p.view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTextField;

import khh.encryption.CryptUtil;
import khh.file.util.FileUtil;
import khh.gui.component.frame.FrameFrame;

public class CryptConversion extends FrameFrame{
	public static enum VIEWID{
		INPUTTEXT("INPUTTEXT"), RESULTTEXT("RESULTTEXT"), ENCRYPTBTN("ENCRYPTBTN"), DECRYPTBTN(
				"DECRYPTBTN"), ;
		String id;

		VIEWID(String id){
			this.id = id;
		}

		public String getValue(){
			return this.id;
		}
	}

	public CryptConversion(String title){
		super(title);
		super.flow();
	}

	JPanel toolbar;
	JTextField input;
	JTextField path;
	Button enButton;
	Button deButton;

	@Override
	public void onViewSetting(){
		setBounds(10, 10, 400, 400);
		setVisible(true);

		toolbar = new JPanel();
		toolbar.setBackground(new Color(80, 80, 80));
		input = new JTextField(10);
		input.setName(VIEWID.INPUTTEXT.getValue());
		enButton = new Button("ENCODING");
		enButton.setName(VIEWID.ENCRYPTBTN.getValue());
		deButton = new Button("DECODING");
		deButton.setName(VIEWID.DECRYPTBTN.getValue());

		toolbar.setLayout(new FlowLayout(FlowLayout.CENTER));

		toolbar.add(input);
		toolbar.add(enButton);
		toolbar.add(deButton);
		add(toolbar, BorderLayout.NORTH);

		// textarea = new TextArea();
		// textarea.setName(VIEWID.INFO.getValue());
		// add(textarea,BorderLayout.CENTER);

		path = new JTextField(60);
		path.setName(VIEWID.RESULTTEXT.getValue());
		add(path, BorderLayout.SOUTH);

		this.pack();
	}

	@Override
	public void onDataSetting(){
		// TODO Auto-generated method stub

	}

	@Override
	public void onAddListener(){
		Button jb = (Button)getComponent(VIEWID.ENCRYPTBTN.getValue());
		jb.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				onAction(1);
			}
		});

		Button db = (Button)getComponent(VIEWID.DECRYPTBTN.getValue());
		db.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				onAction(2);
			}
		});

	}

	@Override
	public void onAction(int gb, Object o){
		JTextField result =  (JTextField)getComponent(VIEWID.RESULTTEXT.getValue());
		JTextField jb = (JTextField)getComponent(VIEWID.INPUTTEXT.getValue());
		if(gb == 1){
			result.setText(CryptUtil.encrypt(jb.getText()));
		}else if(gb == 2){
			result.setText(CryptUtil.decrypt(jb.getText()));
		}
	}

}
