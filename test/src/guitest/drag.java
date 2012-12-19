package guitest;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;


public class drag extends JFrame {
    Frame context = this;
    Container contentpane =null;
    public drag() {
        contentpane = getContentPane();
        contentpane.setLayout(null);
        setLayout(null);
       final Label l = new Label();
        l.setBackground(new Color(55,55,55));
        l.setSize(10,10);
        context.add(l);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout()); 
        this.addMouseListener(new MouseListener() {
            public void mouseReleased(MouseEvent e) {
            }
            public void mousePressed(MouseEvent e) {
            }
            public void mouseExited(MouseEvent e) {
            }
            public void mouseEntered(MouseEvent e) {
            }
            public void mouseClicked(MouseEvent e) {
              
                l.setLocation(e.getX(),e.getY());
            }
        });
        
        
        this.addMouseMotionListener( 
                new MouseMotionAdapter(){ 
                //new MouseMotionListener(){ 
                    public void mouseDragged(MouseEvent e){ 
                        setTitle("MouseDragged:" + e.getX() + "," + e.getY()); 
                    } 
                    public void mouseMoved(MouseEvent e){ 
                        setTitle("MouseMoved:" + e.getX() + "," + e.getY()); 
                    } 
                } 
            ); 
    }
    public static void main(String[] args) {
        JFrame f = new drag(); 
        f.setTitle("MouseMotionAdapter"); 
        f.setSize(300,200); 
        f.setVisible(true); 
    }
}
