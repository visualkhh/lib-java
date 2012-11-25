package guitest;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class drag extends Frame {
    public drag() {
        this.setLayout(new FlowLayout()); 
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
        Frame f = new drag(); 
        f.setTitle("MouseMotionAdapter"); 
        f.setSize(300,200); 
        f.setVisible(true); 
    }
}
