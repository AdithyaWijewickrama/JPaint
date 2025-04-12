package com.jpaint.board;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class IVCommons {

    public static Date date = Calendar.getInstance().getTime();
    static String defaultImage = "src\\Images\\WhiteBackground.png";
    public static final String APPLICATIONNAME = "JPaint";

    public static void CopytoClipboard(String text) {
        java.awt.datatransfer.Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection sel = new StringSelection(text);
        clip.setContents(sel, sel);
    }

    public static int showConfMsg(String e) {
        return JOptionPane.showConfirmDialog(null, e);
    }

    public static void showMsg(Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }

    public static void showMsg(String e) {
        JOptionPane.showMessageDialog(null, e);
    }

    public static void setToConsume(JTextField txt, char c) {
        txt.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char key = e.getKeyChar();
                if (key == c) {
                    e.consume();
                }
            }
        });
    }

    public static void setToOnlyDigits(JTextField txt) {
        txt.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char key = e.getKeyChar();
                if (key != KeyEvent.VK_BACK_SPACE && key != KeyEvent.VK_DELETE && !Character.isDigit(key)) {
                    e.consume();
                }
            }
        });
    }

    public static void setToOnlyDigitsWITHb(JTextField txt, boolean b) {
        txt.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (b) {
                    e.consume();
                }
            }
        });
    }

    public static void setToOnlyDigitsORb(JTextField txt, boolean b) {
        txt.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char key = e.getKeyChar();
                if (key != KeyEvent.VK_BACK_SPACE || key != KeyEvent.VK_DELETE || !Character.isDigit(key) || b) {
                    e.consume();
                }
            }
        });
    }

    public static void setPlaceholder(JTextField txt, String placeholder, Color textColor) {
        Color c = Color.lightGray;
        txt.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (txt.getText().equals(placeholder) && c.equals(txt.getForeground())) {
                    txt.setForeground(textColor);
                    txt.setText(Character.toString(e.getKeyChar()));
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (txt.getText().equals("") && textColor.equals(txt.getForeground())) {
                    txt.setForeground(c);
                    txt.setText(placeholder);
                    txt.setCaretPosition(0);
                }
            }
        });
        txt.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (txt.getText().equals(placeholder) && c.equals(txt.getForeground())) {
                    txt.setCaretPosition(0);
                }
            }
        });
    }

    public static void CopyPasteFile(String CurrentPath, String NewPath) {
        try {
            FileInputStream in = new FileInputStream(CurrentPath);
            FileOutputStream out = new FileOutputStream(NewPath);
            BufferedOutputStream bout;
            try (BufferedInputStream bin = new BufferedInputStream(in)) {
                bout = new BufferedOutputStream(out);
                int bi = 0;
                while (bi != -1) {
                    bi = bin.read();
                    bout.write(bi);
                }
            }
            bout.close();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public static Date currentDate() {
        return date;
    }

    public static void openDocument(String Path_Name) throws IOException {
        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + Path_Name);

    }

    public static ImageIcon getImage(String ImagePath, int Width, int Height) {
        ImageIcon Imge;
        if (ImagePath != null) {
            Imge = new ImageIcon(ImagePath);
        } else {
            Imge = new ImageIcon(defaultImage);
        }
        if (Width == 0) {
            Width = Imge.getIconWidth();
        }
        if (Height == 0) {
            Height = Imge.getIconHeight();
        }
        java.awt.Image img = Imge.getImage();
        java.awt.Image newimg = img.getScaledInstance(Width, Height, 25);
        ImageIcon i = new ImageIcon(newimg);
        return i;
    }

    public static ImageIcon getImage(ImageIcon Image, int Width, int Height) {
        if (Width == 0) {
            Width = Image.getIconWidth();
        }
        if (Height == 0) {
            Height = Image.getIconHeight();
        }
        java.awt.Image img = Image.getImage();
        java.awt.Image newimg = img.getScaledInstance(Width, Height, 25);
        ImageIcon i = new ImageIcon(newimg);
        return i;
    }

}
