package com.kdn.p;

import java.awt.Button;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.kdn.gui.frame.FrameFrame;
import com.kdn.p.view.DateJump;
import com.kdn.p.view.ExcelParser;
import com.kdn.p.view.FileRenameReplaceName;
import com.kdn.p.view.FolderOpen;
import com.kdn.p.view.ISL_GENPROC1A;
import com.kdn.p.view.PasswordConversion;
import com.kdn.p.view.TechExport;
import com.kdn.p.view.PasswordConversion.VIEWID;

public class index extends FrameFrame {

    public static enum VIEWID {
        FILERENAME("FILERENAME"), PASSWORDCONVERSION("PASSWORDCONVERSION"), EXCELSPLIT("EXCELSPLIT"), FOLDEROPEN("FOLDEROPEN"), TECHEXPORT("TECHEXPORT"),ISL_GENPROC1A("ISL_GENPROC1A"),DATEJUMP("DATEJUMP");
        String id;

        VIEWID(String id) {
            this.id = id;
        }

        public String getValue() {
            return this.id;
        }
    }

    public index(String title) {
        super(title);
        flow();
    }

    @Override
    public void onViewSetting() {
        setBounds(10, 10, 400, 400);
        setVisible(true);
        GridLayout layout = new GridLayout(2, 4);
        setLayout(layout);

        Button b1 = new Button(VIEWID.FILERENAME.getValue());
        b1.setName(VIEWID.FILERENAME.getValue());
        Button b2 = new Button(VIEWID.PASSWORDCONVERSION.getValue());
        b2.setName(VIEWID.PASSWORDCONVERSION.getValue());
        Button b3 = new Button(VIEWID.FOLDEROPEN.getValue());
        b3.setName(VIEWID.FOLDEROPEN.getValue());
        Button b4 = new Button(VIEWID.EXCELSPLIT.getValue());
        b4.setName(VIEWID.EXCELSPLIT.getValue());
        Button b5 = new Button(VIEWID.TECHEXPORT.getValue());
        b5.setName(VIEWID.TECHEXPORT.getValue());
        Button b6 = new Button(VIEWID.ISL_GENPROC1A.getValue());
        b6.setName(VIEWID.ISL_GENPROC1A.getValue());
        Button b7 = new Button(VIEWID.DATEJUMP.getValue());
        b7.setName(VIEWID.DATEJUMP.getValue());

        add(b1);
        add(b2);
        add(b3);
        add(b4);
        add(b5);
        add(b6);
        add(b7);
    }

    @Override
    public void onDataSetting() {
    }

    @Override
    public void onAddListener() {
        Button b1 = (Button) getComponent(VIEWID.FILERENAME.getValue());
        Button b2 = (Button) getComponent(VIEWID.PASSWORDCONVERSION.getValue());
        Button b3 = (Button) getComponent(VIEWID.FOLDEROPEN.getValue());
        Button b4 = (Button) getComponent(VIEWID.EXCELSPLIT.getValue());
        Button b5 = (Button) getComponent(VIEWID.TECHEXPORT.getValue());
        Button b6 = (Button) getComponent(VIEWID.ISL_GENPROC1A.getValue());
        Button b7 = (Button) getComponent(VIEWID.DATEJUMP.getValue());

        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                new FileRenameReplaceName("파일이름바꾸기");
            }
        });

        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                new PasswordConversion("비밀번호 컨버팅");

            }
        });

        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                new FolderOpen("즐겨찾기폴더");

            }
        });
        b4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                new ExcelParser("엑셀파싱");

            }
        });
        b5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                new TechExport("신기술추출");
                
            }
        });
        b6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                new ISL_GENPROC1A("도서전력 GENPROC1A 프로시져 일일실행");
                
            }
        });
        b7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                new DateJump("데이트 점프");
                
            }
        });
    }

    @Override
    public void onAction(int gb, Object o) {
    }

    public static void main(String[] args) {
        new index("환영합니다.");
    }
}
