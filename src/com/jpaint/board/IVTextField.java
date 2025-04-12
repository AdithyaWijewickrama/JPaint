package com.jpaint.board;

import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;

public class IVTextField {

    JTextField f;
    boolean focusedNow = false;
    boolean clearWhenFocused = false;

    public IVTextField(JTextField f) {
        this.f = f;
    }

    public void focusedNow() {
        focusedNow = true;
    }

    public void focuseLost() {
        focusedNow = false;
    }

    public void clearWhenFocused(boolean b) {

    }

    public boolean isFocusedNow() {
        return focusedNow;
    }

    public void setAsStandardNumberField() {
        f.setEditable(false);
        f.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char key = e.getKeyChar();
                if (f.getText().length() == 1) {
                    if (key == KeyEvent.VK_BACK_SPACE || key == KeyEvent.VK_DELETE) {
                        f.setText("0");
                    } else if (Character.isDigit(key) || key == '.') {
                        if (f.getText().equals("0")) {
                            f.setText(key + "");
                        } else {
                            f.setText(f.getText() + "" + key);
                        }
                    }
                    focuseLost();
                } else if (key == KeyEvent.VK_DELETE) {
                    f.setText("0");
                    focuseLost();
                } else if (key == KeyEvent.VK_BACK_SPACE) {
                    if (focusedNow) {
                        f.setText("0");
                        focuseLost();
                    } else {
                        f.setText(new StringBuffer(f.getText()).substring(0, f.getText().length() - 1));
                    }
                    e.consume();
                } else if (Character.isDigit(key) || key == '.') {
                    if (focusedNow) {
                        f.setText("" + key);
                        focuseLost();
                    } else {
                        f.setText(f.getText() + "" + key);
                    }
                }
            }
        });
        f.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                Font fn = f.getFont();
                f.setFont(new Font(fn.getName(), Font.BOLD, fn.getSize()));
                if (clearWhenFocused) {
                    focusedNow();
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                Font fn = f.getFont();
                f.setFont(new Font(fn.getName(), Font.PLAIN, fn.getSize()));
                if (clearWhenFocused) {
                    focuseLost();
                }
            }
        });
    }

    public JTextField getF() {
        return f;
    }

    public void setF(JTextField f) {
        this.f = f;
    }

    public boolean isClearWhenFocused() {
        return clearWhenFocused;
    }

    public void setClearWhenFocused(boolean clearWhenFocused) {
        this.clearWhenFocused = clearWhenFocused;
    }

}
