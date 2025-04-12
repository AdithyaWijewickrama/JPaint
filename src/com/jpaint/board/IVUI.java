package com.jpaint.board;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.StringTokenizer;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Dell
 */
public class IVUI {

    public static final int HORIZONTALFIT = 0;
    public static final int VERTICALFIT = -1;

    public static void setLimit(JSpinner c, int min, int max) {
        c.addChangeListener(new javax.swing.event.ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (((int) c.getValue()) < min) {
                    c.setValue(min);
                }
                if (((int) c.getValue()) > max) {
                    c.setValue(max);
                }
            }
        });
    }

    public static Image getImageForChars(char[] c,Color cl){
        BufferedImage i = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = i.createGraphics();
        g.setColor(cl);
        g.drawChars(c, 0, c.length, 0, 0);
        return i;
    }
    
    public static void clearAll(Container cont) throws Exception {
        for (Component c : cont.getComponents()) {
            if (c instanceof JTextComponent) {
                ((JTextComponent) c).setText("");
            } else if (c instanceof JComboBox) {
                ((JComboBox) c).setSelectedIndex(0);
            } else if (c instanceof JTable) {
                ((JTable) c).addRowSelectionInterval(-1, -1);
            } else if (c instanceof Container) {
                clearAll((Container) c);
            }
        }
    }

    public static ImageIcon getVertFitImage(ImageIcon img, int height) {
        int width = new Double((double) img.getIconWidth() / img.getIconHeight() * height).intValue();
        Image img2 = img.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img2);
    }

    public static ImageIcon getHorzFitImage(ImageIcon img, int width) {
        int height = new Double((double) img.getIconHeight() / img.getIconWidth() * width).intValue();
        Image img2 = img.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img2);
    }

    public static Color getToolBarColor() {
        return UIManager.getLookAndFeel().toString().contains("Dark") ? new Color(0, 16, 37) : new Color(255, 255, 255);
    }

    public static Color getTransparentColor() {
        return UIManager.getLookAndFeel().toString().contains("Dark") ? new Color(0, 0, 0, 100) : new Color(255, 255, 255, 100);
    }

    public static Color getNavColor() {
        return UIManager.getLookAndFeel().toString().contains("Dark") ? new Color(0, 0, 0) : new Color(151, 151, 151);
    }

    public static void genarateCenter(JDialog dialog, int width, int height) {
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int y = (size.height - height) / 2;
        int x = (size.width - width) / 2;
        dialog.setSize(width, height);
        dialog.setPreferredSize(new Dimension(width, height));
        dialog.setBounds(x, y, width, height);
        dialog.setLocation(x, y);
        HashMap m = new HashMap();

        dialog.validate();
        dialog.repaint();
    }

    public static int getXOnParent(JComponent c) {
        int x = c.getX();
        if (c.getParent() != null) {
            c = (JComponent) c.getParent();
            x += c.getX();
        }
        return x;
    }

    public static int getYOnParent(JComponent c) {
        int y = c.getY();
        if (c.getParent() != null) {
            c = (JComponent) c.getParent();
            y += c.getY();
        }
        return y;
    }

    public static String verticalText(String s) {
        String r = "<html>";
        for (char c : s.toCharArray()) {
            r = r.concat(c + "<br>");
        }
        return r.concat("</html>");
    }

    public static void setOkButton(JButton b) {
        b.setForeground(Color.WHITE);
        b.setBackground(new Color(0, 100, 205));
        b.repaint();
    }

    public static void checkBoxSet(JToggleButton... boxes) {
        for (JToggleButton box : boxes) {
            box.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    box.setSelected(true);
                    for (JToggleButton box1 : boxes) {
                        if (box1.isSelected()) {
                            box.doClick(0);
                        }
                    }
                }
            });
        }
    }

    public static void checkBoxSet(JRadioButton... boxes) {
        for (JRadioButton box : boxes) {
            box.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    box.setSelected(true);
                    for (JRadioButton box1 : boxes) {
                        if (box1.equals(box)) {
                            continue;
                        }
                        if (box1.isSelected()) {
                            box.doClick();
                        }
                    }
                }
            });
        }
    }

    /**
     * This method divides the button text into lines by applying html tags.
     * Only way to get multiple lines on a JButton.
     *
     * @param string
     * @param align
     * @param btn
     * @return
     */
    public static String wrapText(String string, String align, JButton btn) {
        String returnString = "<html> <p align=\"" + align + "\">";
        int maxLineWidth = new ImageIcon("Images/buttonBackground.png").getIconWidth() - 10;

        FontMetrics metrics = btn.getFontMetrics(new Font("Dialog", Font.PLAIN, 15));

        int lineWidth = 0;

        StringTokenizer tokenizer = new StringTokenizer(string, " ");
        while (tokenizer.hasMoreElements()) {
            String word = (String) tokenizer.nextElement();
            int stringWidth = metrics.stringWidth(word);

            if (stringWidth + lineWidth >= maxLineWidth) {

                returnString = (returnString + "<br>" + word);

                lineWidth = 0;
            } else {

                returnString = (returnString + " " + word + "");
            }
            lineWidth += stringWidth;
        }

        returnString = (returnString + "</p></html>");

        return returnString;
    }

    public static String wrapText(String string, int maxLineWidth, String align, JLabel label) {
        String returnString = "<html> <p align=\"" + align + "\">";
        FontMetrics metrics = label.getFontMetrics(new Font("Dialog", Font.PLAIN, 15));

        int lineWidth = 0;

        StringTokenizer tokenizer = new StringTokenizer(string, " ");
        while (tokenizer.hasMoreElements()) {
            String word = (String) tokenizer.nextElement();
            int stringWidth = metrics.stringWidth(word);

            if (stringWidth + lineWidth >= maxLineWidth) {

                returnString = (returnString + "<br>" + word);

                lineWidth = 0;
            } else {

                returnString = (returnString + " " + word + "");
            }
            lineWidth += stringWidth;
        }

        returnString = (returnString + "</p></html>");

        return returnString;
    }

    public static String[] getArray(JTextArea a) throws BadLocationException {
        if (a.getLineCount() == 0) {
            return new String[]{};
        }
        String[] text = new String[a.getLineCount()];
        text[0] = a.getText(0, a.getLineEndOffset(0));
        for (int i = 1; i < text.length; i++) {
            text[i] = a.getText(a.getLineEndOffset(i - 1), a.getLineEndOffset(i) - a.getLineEndOffset(i - 1) - (text.length - 1 == i ? 0 : 1));
        }
        return text;
    }

    public static String[] getArray(int column, JTable table) {
        String[] data = new String[table.getRowCount()];
        for (int i = 0; i < data.length; i++) {
            try {
                data[i] = table.getValueAt(i, column).toString();
            } catch (Exception e) {
                data[i] = "";
            }
        }
        return data;
    }

    public static String[][] getArray(JTable table) {
        String[][] data = new String[table.getRowCount()][table.getColumnCount()];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < table.getColumnCount(); j++) {
                try {
                    data[i][j] = table.getValueAt(i, j).toString();
                } catch (Exception e) {
                    data[i][j] = "";
                }
            }
        }
        return data;
    }

    public static String[] getSelectedArray(int column, JTable table) {
        String[] data = new String[table.getSelectedRowCount()];
        for (int i = 0; i < data.length; i++) {
            try {
                data[i] = table.getValueAt(table.getSelectedRows()[i], column).toString();
            } catch (Exception e) {
                data[i] = "";
            }
        }
        return data;
    }

    public static void genarateCenter(JInternalFrame frame, Container parent, int w, int h) {
        frame.setBounds((parent.getWidth() - w) / 2, (parent.getHeight() - h) / 2, w, h);
    }

    public static String gsi(JComboBox box) {
        return box.getSelectedItem().toString();
    }

    public static boolean isEqual(ImageIcon i1, ImageIcon i2) {
        BufferedImage bi = new BufferedImage(VERTICALFIT, VERTICALFIT, VERTICALFIT);
        int[] rgbArray = new int[100 * 100];
        bi.getRGB(0, 0, 100, 100, rgbArray, VERTICALFIT, VERTICALFIT);
        return false;
    }

    public static void setImageToLabel(ImageIcon img, JLabel label) {
        label.setIcon(IVCommons.getImage(img, label.getWidth(), label.getHeight()));
    }

    public static void setImageToLabel(String img, JLabel label) {
        label.setIcon(IVCommons.getImage(img, label.getWidth(), label.getHeight()));
    }

    public static void setText(JComboBox c, String t) {
        if (c.isEditable()) {
            ((JTextField) c.getEditor().getEditorComponent()).setText(t);
        }
    }

    public static String getText(JComboBox c) {
        return ((JTextField) c.getEditor().getEditorComponent()).getText();
    }

    public static JFileChooser chooseImage() {
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Choose image");
        jfc.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("All Image Types", "png", "jpg", "jpeg");
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jfc.setFileFilter(filter);
        jfc.setApproveButtonText("select");
        return jfc;
    }
    public static JFileChooser choosePath() {
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Choose path");
        jfc.setAcceptAllFileFilterUsed(false);
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jfc.setApproveButtonText("select");
        return jfc;
    }
}
