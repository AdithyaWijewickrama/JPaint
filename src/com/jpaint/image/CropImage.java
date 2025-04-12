package com.jpaint.image;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class CropImage {

    Image src;
    Rectangle rect;

    public CropImage(Image img, Rectangle rect) {
        this.src = img;
        this.rect = rect;
    }

    public BufferedImage cropRectangle() {
        BufferedImage dest = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) dest.getGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawImage(src, 0, 0, rect.width, rect.height, rect.x, rect.y, (rect.x + rect.width), (rect.y + rect.height), null);
        g2d.dispose();
        return dest;
    }

    public BufferedImage cropEllips() {
        BufferedImage dest = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) dest.createGraphics();
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_RESOLUTION_VARIANT, RenderingHints.VALUE_RESOLUTION_VARIANT_SIZE_FIT);
        
        g2d.setClip(new Ellipse2D.Double(0, 0, rect.width, rect.height));
        
        g2d.drawImage(src, 0, 0, rect.width, rect.height, rect.x, rect.y, (rect.x + rect.width), (rect.y + rect.height), null);
        g2d.dispose();
                
        return dest;
    }

    public static void main(String[] args) {
        String s = "C:/Users/AW Developer/Pictures/Screenshots/Screenshot 2024-06-22 041940.png";
        BufferedImage im;
        try {
            im = new CropImage(new ImageIcon(s).getImage(), new Rectangle(10, 0, 400, 400)).cropEllips();
            JOptionPane.showMessageDialog(null, "msg", "t", JOptionPane.OK_OPTION, new ImageIcon(im));
        } catch (Exception ex) {
            Logger.getLogger(CropImage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
