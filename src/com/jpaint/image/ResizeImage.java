package com.jpaint.image;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import javax.swing.ImageIcon;
import com.jpaint.sizes.Size2D;

/**
 *
 * @author AW Developer
 */
public  class ResizeImage extends ImageIcon implements Size2D{

    private int width;
    private int height;
    
    public ResizeImage(Image img){
        super(img);
        setSize(getIconWidth(), getIconHeight());
    }

    public ResizeImage(String filename) {
        super(filename);
        setSize(getIconWidth(), getIconHeight());
    }
    
    public ImageIcon getScaledImage(){
        return new ImageIcon(getImage().getScaledInstance(this.width, this.height, Image.SCALE_SMOOTH));
    }
    public ImageIcon getHorizontalyScaledImage(int width){
        return new ImageIcon(getImage().getScaledInstance(width, this.height/this.width*width, Image.SCALE_SMOOTH));
    }
    public ImageIcon getVerticalyScaledImage(int height){
        return new ImageIcon(getImage().getScaledInstance(this.width/this.height*height, height, Image.SCALE_SMOOTH));
    }
    
    @Override
    public final void setSize(int width, int height) {
        this.width=width;
        this.height=height;
    }

    @Override
    public Rectangle2D getSize() {
        return new Rectangle(width, height);
    }
}
