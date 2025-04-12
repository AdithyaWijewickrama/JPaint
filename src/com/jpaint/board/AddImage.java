package com.jpaint.board;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.TrayIcon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class AddImage extends javax.swing.JPanel {

    Container parent;
    BufferedImage originalImage;
    Image img;
    public static File lastPath = null;
    public final int PANELWIDTH;
    public final int PANELHEIGHT;
    public static int hieghtCount = 5;
    public Color borderColor;
    public String fileName;
    public boolean asRatio = true;
    public Integer DEFH;
    public Integer DEFW;

    public AddImage(Container parent, BufferedImage img) {
        DEFH = img.getHeight();
        DEFW = img.getWidth();
        borderColor = img.createGraphics().getColor();
        PANELWIDTH = parent.getWidth() - 10;
        PANELHEIGHT = (int) (new Double(img.getHeight(null)) / img.getWidth(null) * PANELWIDTH);
        System.out.printf("Image:\n\tX: %d\n\tImage Height: %d\n\tImage Width: %d", hieghtCount, img.getHeight(null), img.getWidth(null));
        originalImage = img;
        this.img = img;
        setBackground(Color.red);
        this.parent = parent;
        setBounds(5, hieghtCount, PANELWIDTH, PANELHEIGHT);
        setPreferredSize(new Dimension(PANELWIDTH, PANELHEIGHT));
        setMaximumSize(new Dimension(PANELWIDTH, PANELHEIGHT));
        setMinimumSize(new Dimension(PANELWIDTH, PANELHEIGHT));
        initComponents();
        jDialog1.setIconImage(ImageVeiwer.FRAMEICON.getImage());
        hieghtCount += PANELHEIGHT + 5;
        new IVTextField(sh).setAsStandardNumberField();
        new IVTextField(sw).setAsStandardNumberField();
        sw.setText(img.getWidth() + "");
        sh.setText(img.getHeight() + "");
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage((IVUI.getHorzFitImage(new ImageIcon(img), PANELWIDTH)).getImage(), 0, 0, this);
    }

    public int getImgWidth() {
        return Integer.parseInt(sw.getText());
    }

    public int getImgHeight() {
        return Integer.parseInt(sh.getText());
    }

    public BufferedImage getOriginalImage() {
        return originalImage;
    }

    public Image getPrefferedImage() {
        return IVCommons.getImage(new ImageIcon(originalImage), getImgWidth(), getImgHeight()).getImage();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jDialog2 = new javax.swing.JDialog();
        jLabel12 = new javax.swing.JLabel();
        sh = new javax.swing.JTextField();
        sw = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jToggleButton1 = new javax.swing.JToggleButton();
        jButton6 = new javax.swing.JButton();

        jDialog1.setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);

        jTextField1.setEditable(false);
        jTextField1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jTextField1PropertyChange(evt);
            }
        });

        jButton1.setText("Select");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Save");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDialog1Layout.createSequentialGroup()
                        .addComponent(jTextField1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addGap(9, 9, 9))
                    .addGroup(jDialog1Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 215, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        jMenuItem1.setText("Save");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        jMenuItem2.setText("Remove");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem2);

        jMenuItem3.setText("Resize");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem3);

        jDialog2.setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);

        jLabel12.setText("Height");

        sh.setText("0");
        sh.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                shKeyReleased(evt);
            }
        });

        sw.setText("0");
        sw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                swKeyReleased(evt);
            }
        });

        jLabel13.setText("Width");

        jButton4.setText("Cancel");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("OK");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jToggleButton1.setText("Lock aspect ratio");

        jButton6.setText("Reset to defaults");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jDialog2Layout = new javax.swing.GroupLayout(jDialog2.getContentPane());
        jDialog2.getContentPane().setLayout(jDialog2Layout);
        jDialog2Layout.setHorizontalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sw, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
                    .addComponent(sh, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
                    .addGroup(jDialog2Layout.createSequentialGroup()
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 240, Short.MAX_VALUE)
                        .addComponent(jButton5))
                    .addGroup(jDialog2Layout.createSequentialGroup()
                        .addGroup(jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel12))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jDialog2Layout.createSequentialGroup()
                        .addComponent(jToggleButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jDialog2Layout.setVerticalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addGap(2, 2, 2)
                .addComponent(sw, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addGap(2, 2, 2)
                .addComponent(sh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jToggleButton1)
                    .addComponent(jButton6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addGroup(jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton5))
                .addGap(5, 5, 5))
        );

        setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 371, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 359, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JFileChooser jfc = IVUI.chooseImage();
        if (lastPath != null) {
            jfc.setCurrentDirectory(lastPath);
        }
        switch (jfc.showSaveDialog(null)) {
            case JFileChooser.APPROVE_OPTION:
                jTextField1.setText(jfc.getSelectedFile().getAbsolutePath());
                jButton3.setEnabled(true);
                fileName = jfc.getName(jfc.getSelectedFile());
                lastPath = jfc.getCurrentDirectory();
                break;
            case JFileChooser.CANCEL_OPTION:
            case JFileChooser.ERROR_OPTION:
                jButton3.setEnabled(false);
                break;
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String s = jTextField1.getText();
        String ext = new StringBuilder(s).substring(s.indexOf('.') + 1);
        int op = 0;
        if (new File(s).exists()) {
            op = JOptionPane.showConfirmDialog(null, "This file already exists\nDo you want to replace it?", "File exists", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
            if (op == JOptionPane.NO_OPTION) {
                String n = new StringBuffer(s).substring(0, s.indexOf('.'));
                int i = 1;
                while (new File(n + "" + i + "." + ext).exists()) {
                    i++;
                }
                s = n + "" + i + "." + ext;
            }
        }
        if (op != JOptionPane.CANCEL_OPTION) {
            try {
                ImageIO.write(ImageVeiwer.getBufferedImage(getPrefferedImage()), ext, new File(s));
                TrayMessage msg = new TrayMessage("Image Saved", s, ImageVeiwer.IMAGEPATH, TrayIcon.MessageType.INFO);
                msg.display();
                jDialog1.setVisible(false);
            } catch (IOException | AWTException ex) {
                Logger.getLogger(AddImage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        IVUI.genarateCenter(jDialog1, 355, 120);
        jDialog1.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        parent.remove(this);
        hieghtCount -= PANELHEIGHT + 5;
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        if (evt.isPopupTrigger()) {
            jPopupMenu1.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_formMouseReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        jDialog1.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTextField1PropertyChange

    }//GEN-LAST:event_jTextField1PropertyChange

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

    }//GEN-LAST:event_jButton5ActionPerformed
    public boolean isAspectRatioValid() {
        return jToggleButton1.isSelected();
    }
    private void swKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_swKeyReleased
        if (isAspectRatioValid()) {
            double val = DEFH.doubleValue() / DEFW * (Integer.parseInt(sw.getText()));
            try {
                sh.setText(new BigDecimal(val - val % 1).toPlainString());
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_swKeyReleased

    private void shKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_shKeyReleased
        if (isAspectRatioValid()) {
            double val = DEFW.doubleValue() / DEFH * (Integer.parseInt(sh.getText()));
            try {
                sw.setText(new BigDecimal(val - val % 1).toPlainString());
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_shKeyReleased

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        IVUI.genarateCenter(jDialog2, 300, 250);
        jDialog2.setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        sw.setText(new BigDecimal(DEFW - DEFW % 1).toPlainString());
        sh.setText(new BigDecimal(DEFH - DEFH % 1).toPlainString());
    }//GEN-LAST:event_jButton6ActionPerformed

//<editor-fold defaultstate="collapsed" desc="Main method for testing">
    /*public static void main(String[] args) {
    JFrame frame = new JFrame();
    try {
    UIManager.setLookAndFeel(new FlatLightLaf());
    } catch (UnsupportedLookAndFeelException ex) {
    Logger.getLogger(AddImage.class.getName()).log(Level.SEVERE, null, ex);
    }
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setBounds(0, 0, 500, 500);
    AddImage hf = new AddImage(frame, ImageVeiwer.getBufferedImage(IVCommons.getImage("C:\\Users\\Dell\\Documents\\NetBeansProjects\\ImageVeiwer\\src\\Images\\Frame image-2.png", 578, 695).getImage()));
    frame.add(hf);
    hf.addNotify();
    hf.setBounds(10, 10, 1050, 590);
    hf.setVisible(true);
    frame.setVisible(true);
    frame.validate();
    System.out.println((BufferedImage)hf.getPrefferedImage());
    }*/
//</editor-fold>
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton jButton1;
    public javax.swing.JButton jButton2;
    public javax.swing.JButton jButton3;
    public javax.swing.JButton jButton4;
    public javax.swing.JButton jButton5;
    public javax.swing.JButton jButton6;
    public javax.swing.JDialog jDialog1;
    public javax.swing.JDialog jDialog2;
    public javax.swing.JLabel jLabel12;
    public javax.swing.JLabel jLabel13;
    public javax.swing.JMenuItem jMenuItem1;
    public javax.swing.JMenuItem jMenuItem2;
    public javax.swing.JMenuItem jMenuItem3;
    public javax.swing.JPopupMenu jPopupMenu1;
    public javax.swing.JTextField jTextField1;
    public javax.swing.JToggleButton jToggleButton1;
    public javax.swing.JTextField sh;
    public javax.swing.JTextField sw;
    // End of variables declaration//GEN-END:variables
}
