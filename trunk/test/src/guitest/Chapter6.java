package guitest;
import java.awt.Container;
import java.awt.Event;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;




public class Chapter6 extends JFrame{

	Container contentpane;
	JLabel jl; ImageIcon image;
	JPanel jp;
	Chapter6(){
	
		setTitle("버블 게임");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentpane = getContentPane();
		contentpane.setLayout(null);
		
		
		
		contentpane.addMouseListener(new bubble(){
		
		
		});
		image = new ImageIcon("a.png");
		jl = new JLabel(image);
			   add(jl,5);
		setSize(400, 300);
		setVisible(true);
	}

	class bubble extends Thread implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			int x= e.getX();
			int y = e.getY();
			
			jl.setLocation(x, y);
			jl.setSize(0, 0);
		    contentpane.add(jl);
			
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
		@Override
		public void mousePressed(MouseEvent e) {}
		@Override
		public void mouseReleased(MouseEvent e) {}		
	}
	public static void main(String[] args) {
		new Chapter6();

	}

}
