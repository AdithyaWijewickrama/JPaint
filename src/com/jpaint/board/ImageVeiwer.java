package com.jpaint.board;

import com.formdev.flatlaf.FlatDarkLaf;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.TrayIcon.MessageType;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImageVeiwer extends javax.swing.JFrame {

    public static final String IMAGEPATH = "src\\Images\\Imageeiwer ico.png";
    public static ImageIcon FRAMEICON;
    public static final String CREATEDIMAGE = "Image created by system";
    public static final String RECT = "rect";
    public static final String CIRCLE = "circle";

    private JLabel cropper;
    private Image img;
    private ImageIcon ico;
    private boolean labelMousePressed = false;
    private int dx;
    private int dy;
    private String currentImagePath;
    private int resizedImageWidth;
    private int resizedImageHeight;
    private Color borderColor = Color.MAGENTA;
    private JScrollBar horizontalScrollBar;
    private JScrollBar vertScrollBar;
    private JToggleButton colorSelection;

    private int imgWidth;
    private int imgHeight;
    public ArrayList<AddImage> cropedImages;
    public int maxCropes = -2;
    public int unitIncreament = 5;

    public ImageVeiwer(ImageIcon img, String filename) {
        if (new File(IMAGEPATH).exists()) {
            this.FRAMEICON = new ImageIcon(IMAGEPATH);
        }
        setMaxCropes(3);
        setIconImage(ImageVeiwer.FRAMEICON.getImage());
        cropedImages = new ArrayList<>();
        imgWidth = img.getIconWidth();
        imgHeight = img.getIconHeight();
        resizedImageHeight = imgHeight;
        resizedImageWidth = imgWidth;
        this.ico = img;
        this.img = img.getImage();
        initComponents();
        colorSelection = new ToggleGroup(
                jToggleButton1,
                jToggleButton2,
                jToggleButton3,
                jToggleButton4,
                jToggleButton5,
                jToggleButton6,
                jToggleButton7,
                jToggleButton8,
                jToggleButton9);
        setLabel(RECT);
        path.setText(filename != null ? filename : CREATEDIMAGE);
        wtxt.setText(img.getIconWidth() + "");
        htxt.setText(img.getIconHeight() + "");
        nm.setBackground(new Color(0, 0, 0, 160));
        nav.setBackground(new Color(0, 0, 0, 0));
        jPanel1.setBackground(new Color(0, 0, 0, 0));
        jp.add(nm);
        jp.add(cropper);
        nm.setVisible(false);
        new RepaintTimer(jp).run();
        new RepaintTimer(jScrollPane1).run();
        x.setMaximum(jp.getWidth());
        width.setMaximum(jp.getWidth());
        y.setMaximum(jp.getHeight());
        height.setMaximum(jp.getHeight());
        horizontalScrollBar = jScrollPane1.getHorizontalScrollBar();
        vertScrollBar = jScrollPane1.getVerticalScrollBar();
        vertScrollBar.setBlockIncrement(30);
        horizontalScrollBar.setBlockIncrement(30);
        cropedObjects.getVerticalScrollBar().setBlockIncrement(100);
        AdjustmentListener n = new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                nm.setBounds(horizontalScrollBar.getValue(), vertScrollBar.getValue(), 325, getVisRect().height);
            }
        };
        vertScrollBar.addAdjustmentListener(n);
        horizontalScrollBar.addAdjustmentListener(n);
        MouseListener lis = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                if (evt.getX() > getVisRect().x + getVisRect().width) {
                    horizontalScrollBar.setValue(horizontalScrollBar.getValue() + 1);
                }
                if (evt.getX() < getVisRect().x) {
                    horizontalScrollBar.setValue(horizontalScrollBar.getValue() - 1);
                }
                if (evt.getY() > getVisRect().y + getVisRect().height) {
                    vertScrollBar.setValue(vertScrollBar.getValue() + 1);
                }
                if (evt.getY() < getVisRect().y) {
                    vertScrollBar.setValue(vertScrollBar.getValue() - 1);
                }
            }
        };
        jButton1.addMouseListener(lis);
        new IVTextField(sx).setAsStandardNumberField();
        new IVTextField(sy).setAsStandardNumberField();
        new IVTextField(sw).setAsStandardNumberField();
        new IVTextField(sh).setAsStandardNumberField();
        horizontalScrollBar.setUnitIncrement(50);
        vertScrollBar.setUnitIncrement(50);
    }

    public int getMaxCropes() {
        return maxCropes;
    }

    public void setMaxCropes(int maxCropes) {
        this.maxCropes = maxCropes;
    }

    public void setLabel(String shape) {
        if (shape.equals(RECT)) {
            cropper = new JLabel();
            cropper.setBorder(new LineBorder(borderColor, 1));
        } else if (shape.equals(CIRCLE)) {
            jp.remove(cropper);
            cropper = new RoundLabel("", 0, 0, borderColor);
        }
        cropper.setBackground(new Color(0, 0, 0, 0));
        cropper.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                labelMousePressed = true;
                cropper.setCursor(new Cursor(Cursor.MOVE_CURSOR));
                dx = e.getX();
                dy = e.getY();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    jPopupMenu1.show(cropper, e.getX(), e.getY());
                }
                cropper.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                labelMousePressed = false;
            }
        });
        cropper.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent evt) {
                if (labelMousePressed) {
                    cropper.setBounds(cropper.getX() + evt.getX() - dx, cropper.getY() + evt.getY() - dy, cropper.getWidth(), cropper.getHeight());
                    x.setValue(cropper.getX() + evt.getX() - dx);
                    y.setValue(cropper.getY() + evt.getY() - dy);
                    width.setMaximum(jp.getWidth() - cropper.getX());
                    height.setMaximum(jp.getHeight() - cropper.getY());
                }
                if (cropper.getX() + cropper.getWidth() > getVisRect().x + getVisRect().width) {
                    horizontalScrollBar.setValue(horizontalScrollBar.getValue() + 1);
                } else if (cropper.getX() < getVisRect().x) {
                    horizontalScrollBar.setValue(cropper.getX());
                }
                if (cropper.getY() + cropper.getHeight() > getVisRect().y + getVisRect().height) {
                    vertScrollBar.setValue(vertScrollBar.getValue() + 1);
                } else if (evt.getY() < getVisRect().y) {
                    vertScrollBar.setValue(vertScrollBar.getValue() - 1);
                }
                x.setMaximum(jp.getWidth() - cropper.getWidth());
                y.setMaximum(jp.getHeight() - cropper.getHeight());
                width.setMaximum(jp.getWidth() - cropper.getX());
                height.setMaximum(jp.getWidth() - cropper.getY());
            }
        });
        cropper.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setSelection();
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                setSelection();
            }

        });
        if (cropper != null && jp.getComponents() != null) {
            if (Arrays.asList(jp.getComponents()).contains(cropper)) {
                jp.remove(cropper);
            }
        }
    }

    public Rectangle getVisRect() {
        if (horizontalScrollBar == null) {
            return new Rectangle();
        }
        return new Rectangle(horizontalScrollBar.getValue(), vertScrollBar.getValue(), jScrollPane1.getWidth(), jScrollPane1.getHeight());
    }

    public JFileChooser ChooseImage() {
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Choose an image");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("All Image Types", "png", "jpg", "jpeg");
        jfc.setFileFilter(filter);
        jfc.setApproveButtonText("Choose Image");
        if (currentImagePath != null) {
            jfc.setCurrentDirectory(new File(currentImagePath));
        }
        return jfc;
    }

    public static BufferedImage getBufferedImage(Image img) {
        BufferedImage buffer = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = (Graphics2D) buffer.getGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        return buffer;
    }

    public void addActionButtons(int notUsed, JButton... b) {
        addActionButtons(b);
    }

    public void addActionButtons(JButton[] b) {
        for (JButton b1 : b) {
            jToolBar1.add(b1);
        }
    }

    public TrayMessage showMessage(String msg, String subMsg, MessageType type) {
        return new TrayMessage(msg, subMsg, IMAGEPATH, type);
    }

    public int getWidthPart(int panelPart) {
        int w = (int) (((double) imgWidth / resizedImageWidth) * panelPart);
        return w;
    }

    public int getHeightPart(int panelPart) {
        int h = (int) (((double) imgHeight / resizedImageHeight) * panelPart);
        return h;
    }

    public void setSelectionVals() {
        cropper.setBounds(Integer.parseInt(sx.getText()), Integer.parseInt(sy.getText()), Integer.parseInt(sw.getText()), Integer.parseInt(sh.getText()));
    }

    public void setSelection() {
        if (cropper.getX() + cropper.getWidth() > getVisRect().x + getVisRect().width) {
            horizontalScrollBar.setValue(horizontalScrollBar.getValue() + unitIncreament);
        } else if (cropper.getX() < getVisRect().x) {
            horizontalScrollBar.setValue(horizontalScrollBar.getValue() - unitIncreament);
        }
        if (cropper.getY() + cropper.getHeight() > getVisRect().y + getVisRect().height) {
            vertScrollBar.setValue(vertScrollBar.getValue() + unitIncreament);
        } else if (cropper.getY() < getVisRect().y) {
            vertScrollBar.setValue(vertScrollBar.getValue() - unitIncreament);
        }
    }

    public void setSliders() {
        x.setMaximum(jp.getWidth() - cropper.getWidth());
        y.setMaximum(jp.getHeight() - cropper.getHeight());
        width.setMaximum(jp.getWidth() - cropper.getX());
        height.setMaximum(jp.getHeight() - cropper.getY());
    }

    public void setImage(String file) {
        this.ico = new ImageIcon(file);
        imgWidth = ico.getIconWidth();
        imgHeight = ico.getIconHeight();
        resizedImageHeight = imgHeight;
        resizedImageWidth = imgWidth;
        this.img = ico.getImage();
        path.setText(file);
        wtxt.setText(imgWidth + "");
        htxt.setText(imgHeight + "");
    }

    public JLabel getCropper() {
        return cropper;
    }

    public Image getImg() {
        return img;
    }

    public ImageIcon getIco() {
        return ico;
    }

    public boolean isLabelMousePressed() {
        return labelMousePressed;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public String getCurrentImagePath() {
        return currentImagePath;
    }

    public int getResizedImageWidth() {
        return resizedImageWidth;
    }

    public int getResizedImageHeight() {
        return resizedImageHeight;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public JScrollBar getHorizontalScrollBar() {
        return horizontalScrollBar;
    }

    public JScrollBar getVertScrollBar() {
        return vertScrollBar;
    }

    public JToggleButton getColorSelection() {
        return colorSelection;
    }

    public int getImgHeight() {
        return imgHeight;
    }

    public ArrayList<AddImage> getCropedImages() {
        return cropedImages;
    }

    public JScrollPane getCropedObjects() {
        return cropedObjects;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nm = new javax.swing.JScrollPane();
        nav = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        path = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        wtxt = new javax.swing.JTextField();
        htxt = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jToggleButton1 = new javax.swing.JToggleButton();
        jToggleButton2 = new javax.swing.JToggleButton();
        sx = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        sy = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        sh = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        sw = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jToggleButton9 = new javax.swing.JToggleButton();
        jToggleButton3 = new javax.swing.JToggleButton();
        jToggleButton4 = new javax.swing.JToggleButton();
        jToggleButton5 = new javax.swing.JToggleButton();
        jToggleButton6 = new javax.swing.JToggleButton();
        jToggleButton7 = new javax.swing.JToggleButton();
        jToggleButton8 = new javax.swing.JToggleButton();
        jLabel14 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jDialog1 = new javax.swing.JDialog();
        jLabel4 = new javax.swing.JLabel();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jButton1 = new javax.swing.JButton();
        x = new javax.swing.JSlider();
        y = new javax.swing.JSlider();
        width = new javax.swing.JSlider();
        height = new javax.swing.JSlider();
        jPanel4 = new JPanel(){
            public void paintComponent(Graphics g){
                g.setColor(getBackground());
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        jScrollPane1 = new JScrollPane(){
            public void paintComponent(Graphics g){
                if(jp.getSize().width<getWidth()||jp.getSize().height<getHeight()){
                    g.setColor(getBackground());
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        }
        ;
        jp = new JPanel(){
            public void paintComponent(Graphics g){
                g.setColor(jLabel10.getBackground());
                g.fillRect(0, 0, resizedImageWidth,resizedImageHeight);
                g.drawImage(img,0,0,resizedImageWidth,resizedImageHeight,getBackground(),null);
                setPreferredSize(new Dimension(resizedImageWidth,resizedImageHeight));
                setMaximumSize(new Dimension(resizedImageWidth,resizedImageHeight));
                setMinimumSize(new Dimension(resizedImageWidth,resizedImageHeight));
            }
        }
        ;
        jp.setPreferredSize(new Dimension(imgWidth,imgHeight));
        jp.setMaximumSize(new Dimension(imgWidth,imgHeight));
        jp.setMinimumSize(new Dimension(imgWidth,imgHeight));

        jPanel5 = new javax.swing.JPanel();
        cropedObjects = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jToolBar1 = new javax.swing.JToolBar();

        nm.setBackground(new Color(0,0,0,20));
        nm.setForeground(new Color(0,0,0,0));
        nm.setAutoscrolls(true);

        nav.setBackground(new Color(0,0,0,20));
        nav.setForeground(new Color(0,0,0,0));
        nav.setAutoscrolls(true);
        nav.setDoubleBuffered(false);
        nav.setMaximumSize(new java.awt.Dimension(327, 32767));
        nav.setMinimumSize(new java.awt.Dimension(327, 480));
        nav.setPreferredSize(new java.awt.Dimension(327, 480));
        nav.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                navFocusLost(evt);
            }
        });

        jButton2.setText("Open");
        jButton2.setMaximumSize(new java.awt.Dimension(2354, 32));
        jButton2.setMinimumSize(new java.awt.Dimension(150, 32));
        jButton2.setPreferredSize(new java.awt.Dimension(150, 32));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("  Options");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("  Description");

        jPanel1.setBackground(new Color(0,0,0,20));
        jPanel1.setForeground(new Color(0,0,0,0));
        jPanel1.setAutoscrolls(true);
        jPanel1.setDoubleBuffered(false);

        jLabel2.setText("Path");

        path.setEditable(false);

        jLabel6.setText("Width");

        wtxt.setEditable(false);

        htxt.setEditable(false);

        jLabel7.setText("Height");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(path)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(wtxt)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(htxt)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(path, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(wtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(htxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jToggleButton1.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jToggleButton1.setText("◯");
        jToggleButton1.setToolTipText("Crop circle");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        jToggleButton2.setFont(new java.awt.Font("Dialog", 0, 56)); // NOI18N
        jToggleButton2.setSelected(true);
        jToggleButton2.setText("□");
        jToggleButton2.setToolTipText("Crop rectangle");
        jToggleButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton2ActionPerformed(evt);
            }
        });

        sx.setText("0");
        sx.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                syKeyReleased(evt);
            }
        });

        jLabel9.setText("Selection");

        jLabel10.setText("X");

        sy.setText("0");
        sy.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                syKeyReleased(evt);
            }
        });

        jLabel11.setText("Y");

        sh.setText("0");
        sh.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                syKeyReleased(evt);
            }
        });

        jLabel12.setText("Height");

        sw.setText("0");
        sw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                syKeyReleased(evt);
            }
        });

        jLabel13.setText("Width");

        jToggleButton9.setBackground(new java.awt.Color(255, 255, 255));
        jToggleButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setColor(evt);
            }
        });

        jToggleButton3.setBackground(new java.awt.Color(255, 0, 0));
        jToggleButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setColor(evt);
            }
        });

        jToggleButton4.setBackground(new java.awt.Color(0, 51, 255));
        jToggleButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setColor(evt);
            }
        });

        jToggleButton5.setBackground(java.awt.Color.magenta);
        jToggleButton5.setSelected(true);
        jToggleButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setColor(evt);
            }
        });

        jToggleButton6.setBackground(new java.awt.Color(0, 204, 0));
        jToggleButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setColor(evt);
            }
        });

        jToggleButton7.setBackground(new java.awt.Color(0, 255, 204));
        jToggleButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setColor(evt);
            }
        });

        jToggleButton8.setBackground(new java.awt.Color(255, 255, 0));
        jToggleButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setColor(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jToggleButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToggleButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToggleButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToggleButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToggleButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToggleButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToggleButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToggleButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel14.setText("Selection color");

        jButton3.setText("Cropped objects");
        jButton3.setMaximumSize(new java.awt.Dimension(2354, 32));
        jButton3.setMinimumSize(new java.awt.Dimension(150, 32));
        jButton3.setPreferredSize(new java.awt.Dimension(150, 32));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout navLayout = new javax.swing.GroupLayout(nav);
        nav.setLayout(navLayout);
        navLayout.setHorizontalGroup(
            navLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, navLayout.createSequentialGroup()
                .addGroup(navLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(navLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(navLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(navLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel9)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(navLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(navLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(navLayout.createSequentialGroup()
                                        .addGroup(navLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel10)
                                            .addComponent(sx, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(navLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel11)
                                            .addComponent(sy, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(navLayout.createSequentialGroup()
                                        .addGroup(navLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel13)
                                            .addComponent(sw, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(navLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel12)
                                            .addComponent(sh, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, navLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(navLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(navLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(navLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jToggleButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jToggleButton2))
                    .addComponent(jLabel3))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        navLayout.setVerticalGroup(
            navLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(navLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(navLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jToggleButton1)
                    .addComponent(jToggleButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(navLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(navLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(2, 2, 2)
                        .addComponent(sx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(navLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(2, 2, 2)
                        .addComponent(sy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(navLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(navLayout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(2, 2, 2)
                        .addComponent(sw, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(navLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(2, 2, 2)
                        .addComponent(sh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        nm.setViewportView(nav);
        nav.getAccessibleContext().setAccessibleParent(jp);

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
            .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jDialog1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel4)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
            .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jDialog1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel4)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jMenuItem2.setText("Crop");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem2);

        jMenuItem1.setText("Cancel");
        jPopupMenu1.add(jMenuItem1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Century", 0, 36)); // NOI18N
        jButton1.setText("≡");
        jButton1.setBorder(null);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        x.setToolTipText("x");
        x.setValue(0);
        x.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                xStateChanged(evt);
            }
        });
        x.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                xMouseDragged(evt);
            }
        });

        y.setOrientation(javax.swing.JSlider.VERTICAL);
        y.setToolTipText("y");
        y.setValue(0);
        y.setInverted(true);
        y.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                yStateChanged(evt);
            }
        });
        y.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                yMouseDragged(evt);
            }
        });

        width.setToolTipText("Width");
        width.setValue(0);
        width.setValueIsAdjusting(true);
        width.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                widthStateChanged(evt);
            }
        });
        width.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                widthMouseDragged(evt);
            }
        });

        height.setMaximum(jp.getHeight());
        height.setOrientation(javax.swing.JSlider.VERTICAL);
        height.setToolTipText("Height");
        height.setValue(0);
        height.setInverted(true);
        height.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                heightStateChanged(evt);
            }
        });
        height.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                heightMouseDragged(evt);
            }
        });

        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.LINE_AXIS));

        jp.setPreferredSize(new java.awt.Dimension(884, 513));
        jp.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jpMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jpMouseMoved(evt);
            }
        });
        jp.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                jpMouseWheelMoved(evt);
            }
        });
        jp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jpMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jpMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jpMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout jpLayout = new javax.swing.GroupLayout(jp);
        jp.setLayout(jpLayout);
        jpLayout.setHorizontalGroup(
            jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 884, Short.MAX_VALUE)
        );
        jpLayout.setVerticalGroup(
            jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 560, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jp);

        jPanel4.add(jScrollPane1);

        cropedObjects.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        cropedObjects.setPreferredSize(new java.awt.Dimension(0, 0));

        jPanel3.setMaximumSize(new java.awt.Dimension(0, 0));
        jPanel3.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanel3.setPreferredSize(new java.awt.Dimension(0, 0));
        jPanel3.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                jPanel3ComponentAdded(evt);
            }
            public void componentRemoved(java.awt.event.ContainerEvent evt) {
                jPanel3ComponentRemoved(evt);
            }
        });
        cropedObjects.setViewportView(jPanel3);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jScrollPane2.setViewportView(jToolBar1);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(cropedObjects, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(7, 7, 7))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(cropedObjects, javax.swing.GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.add(jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addComponent(height, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(3, 3, 3))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(x, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(width, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(7, 7, 7))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(x, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(width, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(y, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(height, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        cropedObjects.setPreferredSize(new Dimension(cropedObjects.getPreferredSize().width, jScrollPane1.getHeight()));
        cropedObjects.setMaximumSize(new Dimension(cropedObjects.getPreferredSize().width, jScrollPane1.getHeight()));
    }//GEN-LAST:event_formComponentResized

    private void jpMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpMousePressed
        cropper.setBounds(evt.getX(), evt.getY(), 0, 0);
        nm.setVisible(false);
        jp.repaint();
        dx = evt.getX();
        dy = evt.getY();
    }//GEN-LAST:event_jpMousePressed

    private void jpMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpMouseReleased
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_jpMouseReleased

    private void jpMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpMouseMoved

    }//GEN-LAST:event_jpMouseMoved

    private void jpMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpMouseDragged
        jp.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        int x = Math.min(evt.getX(), dx);
        int y = Math.min(evt.getY(), dy);
        int h = Math.abs(evt.getY() - dy);
        int w = Math.abs(evt.getX() - dx);
        cropper.setBounds(x, y, w, h);
        this.x.setValue(x);
        this.y.setValue(y);
        this.width.setValue(w);
        this.height.setValue(h);
        setSliders();
        sx.setText(x + "");
        sy.setText(y + "");
        sw.setText(w + "");
        sh.setText(h + "");
    }//GEN-LAST:event_jpMouseDragged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (nm.isVisible()) {
            nm.setVisible(false);
        } else {
            nm.setBounds(horizontalScrollBar.getValue(), vertScrollBar.getValue(), 325, getVisRect().height);
            nm.setVisible(true);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void xMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_xMouseDragged
        cropper.setLocation(x.getValue(), cropper.getY());
        sx.setText(x.getValue() + "");
        setSliders();
        if (getVisRect().x > cropper.getX()) {
            horizontalScrollBar.setValue(cropper.getX());
        } else if (getVisRect().x + getVisRect().width < cropper.getX() + cropper.getWidth()) {
            horizontalScrollBar.setValue(cropper.getX() + cropper.getWidth() - getVisRect().width);
        }
    }//GEN-LAST:event_xMouseDragged

    private void widthMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_widthMouseDragged
        cropper.setSize(new Dimension(width.getValue(), cropper.getHeight()));
        sw.setText(width.getValue() + "");
        setSliders();
    }//GEN-LAST:event_widthMouseDragged

    private void heightMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_heightMouseDragged
        cropper.setSize(new Dimension(cropper.getWidth(), height.getValue()));
        sh.setText(height.getValue() + "");
        setSliders();
    }//GEN-LAST:event_heightMouseDragged

    private void yMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_yMouseDragged
        cropper.setLocation(cropper.getX(), y.getValue());
        sy.setText(y.getValue() + "");
        setSliders();
        if (getVisRect().y > cropper.getY()) {
            vertScrollBar.setValue(cropper.getY());
        } else if (getVisRect().y + getVisRect().height < cropper.getY() + cropper.getHeight()) {
            vertScrollBar.setValue(cropper.getY() + cropper.getHeight() - getVisRect().height);
        }
    }//GEN-LAST:event_yMouseDragged

    private void jpMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpMouseExited

    }//GEN-LAST:event_jpMouseExited

    private void navFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_navFocusLost

    }//GEN-LAST:event_navFocusLost

    private void jToggleButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton2ActionPerformed
        Rectangle r = cropper.getBounds();
        jp.remove(cropper);
        setLabel(RECT);
        jp.add(cropper);
        cropper.setBounds(r);
        jToggleButton1.setSelected(false);
    }//GEN-LAST:event_jToggleButton2ActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        Rectangle r = cropper.getBounds();
        jp.remove(cropper);
        setLabel(CIRCLE);
        jp.add(cropper);
        cropper.setBounds(r);
        jToggleButton2.setSelected(false);
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (cropedObjects.getWidth() < 14) {
            Dimension b = new Dimension(500, jScrollPane1.getHeight());
            cropedObjects.setPreferredSize(b);
            cropedObjects.setMaximumSize(b);
        } else {
            cropedObjects.setPreferredSize(new Dimension(13, jScrollPane1.getHeight()));
            cropedObjects.setMaximumSize(new Dimension(13, jScrollPane1.getHeight()));
        }
        this.dispatchEvent(new ComponentEvent(this, ComponentEvent.COMPONENT_FIRST));
        jPanel4.doLayout();
        cropedObjects.doLayout();
        jPanel3.doLayout();
        doLayout();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        JFileChooser jfc = ChooseImage();
        switch (jfc.showDialog(null, "Select image")) {
            case JFileChooser.APPROVE_OPTION:
                File f = jfc.getSelectedFile();
                String imgPath = f.getAbsolutePath();
                currentImagePath = imgPath;
                setImage(imgPath);
                break;
            case JFileChooser.CANCEL_OPTION:
            case JFileChooser.ERROR_OPTION:
                jfc.setVisible(false);
                break;
            default:
                break;
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void syKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_syKeyReleased
        setSelectionVals();
    }//GEN-LAST:event_syKeyReleased

    private void setColor(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setColor
        for (Component c : jPanel2.getComponents()) {
            JToggleButton t = (JToggleButton) c;
            if (t.isSelected()) {
                if (cropper instanceof RoundLabel) {
                    Rectangle r = cropper.getBounds();
                    borderColor = t.getBackground();
                    setLabel(CIRCLE);
                    jp.add(cropper);
                    cropper.setBounds(r);
                } else if (cropper.getBorder() instanceof LineBorder) {
                    cropper.setBorder(new LineBorder(t.getBackground()));
                }
            }
        }
    }//GEN-LAST:event_setColor

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        Rectangle rect = new Rectangle(getWidthPart(cropper.getX()), getHeightPart(cropper.getY()), getWidthPart(cropper.getWidth()), getHeightPart(cropper.getHeight()));
        BufferedImage cropedImage = null;
        CropImage crop = new CropImage(img, rect);
        try {
            if (cropper instanceof RoundLabel) {
                cropedImage = crop.cropCircle();
            } else if (cropper.getBorder() instanceof LineBorder) {
                cropedImage = crop.crop();
            }
            AddImage ai = new AddImage(jPanel3, cropedImage);
            if (maxCropes == -2 || cropedImages.size() < maxCropes) {
                cropedImages.add(ai);
                jPanel3.add(ai);
            } else {
                try {
                    showMessage("Crop length is out of bounds", "Maximum crop length is " + maxCropes + "", MessageType.ERROR).display();
                } catch (AWTException | MalformedURLException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jPanel3ComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_jPanel3ComponentAdded
        int h = 5;
        for (Component c : jPanel3.getComponents()) {
            h += c.getX() + c.getHeight();
        }
        jPanel3.setSize(jPanel3.getWidth(), h);
        Dimension b = new Dimension(jPanel3.getWidth(), h);
        jPanel3.setPreferredSize(b);
        jPanel3.setMaximumSize(b);
    }//GEN-LAST:event_jPanel3ComponentAdded

    private void jPanel3ComponentRemoved(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_jPanel3ComponentRemoved
        Component[] c = jPanel3.getComponents();
        if (c.length > 0) {
            c[0].setLocation(5, 5);
            for (int i = 1; i < c.length; i++) {
                Component c1 = c[i];
                c1.setLocation(5, c[i - 1].getX() + c[i - 1].getHeight() + 5);
            }
        }
    }//GEN-LAST:event_jPanel3ComponentRemoved

    private void xStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_xStateChanged
//        cropper.setLocation(x.getValue(), cropper.getY());
//        sx.setText(x.getValue() + "");
//        setSliders();
    }//GEN-LAST:event_xStateChanged

    private void yStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_yStateChanged
//        cropper.setLocation(cropper.getX(), y.getValue());
//        sy.setText(y.getValue() + "");
//        setSliders();
    }//GEN-LAST:event_yStateChanged

    private void heightStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_heightStateChanged
//        cropper.setSize(new Dimension(cropper.getWidth(), height.getValue()));
//        sh.setText(height.getValue() + "");
//        setSliders();
    }//GEN-LAST:event_heightStateChanged

    private void widthStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_widthStateChanged
//        cropper.setSize(new Dimension(width.getValue(), cropper.getHeight()));
//        sw.setText(width.getValue() + "");
//        setSliders();
    }//GEN-LAST:event_widthStateChanged

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        int conf = JOptionPane.showConfirmDialog(this, "Are you sure want to exit\nUnsaved things will be lost!", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (conf == 0) {
            this.dispose();
        }
    }//GEN-LAST:event_formWindowClosing

    private void jpMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_jpMouseWheelMoved
        int wheelRotation = evt.getWheelRotation();
        int inc = 50;
        switch (evt.getModifiersEx()) {
            case KeyEvent.CTRL_DOWN_MASK:
                if (wheelRotation > 0) {
                    resizedImageWidth -= inc;
                    resizedImageHeight -= (int) ((double) imgHeight / ((double) imgWidth / (double) inc));
                } else {
                    resizedImageWidth += inc;
                    resizedImageHeight += (int) ((double) imgHeight / ((double) imgWidth / (double) inc));
                }
                break;
            case KeyEvent.SHIFT_DOWN_MASK:
                if (wheelRotation > 0) {
                    horizontalScrollBar.setValue(horizontalScrollBar.getValue() + inc);
                } else {
                    horizontalScrollBar.setValue(horizontalScrollBar.getValue() - inc);
                }
                break;
            case 0:
                if (wheelRotation > 0) {
                    vertScrollBar.setValue(vertScrollBar.getValue() + inc);
                } else {
                    vertScrollBar.setValue(vertScrollBar.getValue() - inc);
                }
                break;
            default:
                break;
        }
    }//GEN-LAST:event_jpMouseWheelMoved

    class RepaintTimer extends Thread {

        final Container c;

        public RepaintTimer(Container c) {
            this.c = c;
        }

        @Override
        public void run() {
            createRepaintTimer(c);
        }

        private void createRepaintTimer(final Container frame) {
            final Timer timer = new Timer(100, null);
            timer.addActionListener(e -> {
                if (!frame.isVisible()) {
                    timer.stop();
                } else {
                    frame.validate();
                    frame.repaint();
                    frame.doLayout();
                }
            });
            timer.start();
        }
    }

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(ImageVeiwer.class.getName()).log(Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ImageIcon img = new ImageIcon("Images Of Students\\1.png");
                ImageVeiwer iv = new ImageVeiwer(img, new File("Images Of Students\\1.png").getAbsolutePath());
                iv.setVisible(true);
                iv.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        System.exit(0);
                    }
                });
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JScrollPane cropedObjects;
    public javax.swing.JSlider height;
    public javax.swing.JTextField htxt;
    public javax.swing.JButton jButton1;
    public javax.swing.JButton jButton2;
    public javax.swing.JButton jButton3;
    public javax.swing.JDialog jDialog1;
    public javax.swing.JLabel jLabel10;
    public javax.swing.JLabel jLabel11;
    public javax.swing.JLabel jLabel12;
    public javax.swing.JLabel jLabel13;
    public javax.swing.JLabel jLabel14;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel5;
    public javax.swing.JLabel jLabel6;
    public javax.swing.JLabel jLabel7;
    public javax.swing.JLabel jLabel9;
    public javax.swing.JMenuItem jMenuItem1;
    public javax.swing.JMenuItem jMenuItem2;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel2;
    public javax.swing.JPanel jPanel3;
    public javax.swing.JPanel jPanel4;
    public javax.swing.JPanel jPanel5;
    public javax.swing.JPopupMenu jPopupMenu1;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JToggleButton jToggleButton1;
    public javax.swing.JToggleButton jToggleButton2;
    public javax.swing.JToggleButton jToggleButton3;
    public javax.swing.JToggleButton jToggleButton4;
    public javax.swing.JToggleButton jToggleButton5;
    public javax.swing.JToggleButton jToggleButton6;
    public javax.swing.JToggleButton jToggleButton7;
    public javax.swing.JToggleButton jToggleButton8;
    public javax.swing.JToggleButton jToggleButton9;
    public javax.swing.JToolBar jToolBar1;
    public javax.swing.JPanel jp;
    public javax.swing.JPanel nav;
    public javax.swing.JScrollPane nm;
    public javax.swing.JTextField path;
    public javax.swing.JTextField sh;
    public javax.swing.JTextField sw;
    public javax.swing.JTextField sx;
    public javax.swing.JTextField sy;
    public javax.swing.JSlider width;
    public javax.swing.JTextField wtxt;
    public javax.swing.JSlider x;
    public javax.swing.JSlider y;
    // End of variables declaration//GEN-END:variables

}
