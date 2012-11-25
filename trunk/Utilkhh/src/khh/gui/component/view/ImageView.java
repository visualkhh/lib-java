package khh.gui.component.view;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

public class ImageView  extends Canvas {
    protected Image image = null;
    // Image img= Toolkit.getDefaultToolkit().getImage("dukci.gif");
    public ImageView(Image image) {
        super ( );
        this.image=image;
        //setSize ( imageSize.width, imageSize.height );
    }
    public ImageView() {
        // TODO Auto-generated constructor stub
    }
    public void paint ( Graphics g )
    {
        super.paint ( g );
        if(image!=null)
        g.drawImage ( image, 0, 0, getWidth(), getHeight(), Color.black, this );
    }
    public Image getImage() {
        return image;
    }
    public void setImage(Image image) {
        this.image = image;
    }
    
    
}
