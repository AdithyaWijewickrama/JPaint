package com.jpaint.board;

import javax.swing.*;
import java.awt.event.*;

public class ToggleGroup extends JToggleButton {

    JToggleButton[] togles;
    JToggleButton selected;

    public ToggleGroup(JToggleButton selected, JToggleButton... togles) {
        this(togles, selected);
    }

    public ToggleGroup(JToggleButton[] togles, JToggleButton selected) {
        this.togles = togles;
        this.selected = selected;
        init();
    }

    public void init() {
        for (JToggleButton togle : togles) {
            togle.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (!selected.equals(togle)) {
                        if (selected.isSelected()) {
                            selected.doClick();
                        }
                        selected = togle;
                    }
                }
            });
        }
    }

    public JToggleButton[] getTogles() {
        return togles;
    }

    public void setTogles(JToggleButton[] togles) {
        this.togles = togles;
    }

    public JToggleButton getSelected() {
        return selected;
    }

    public void setSelected(JToggleButton selected) {
        this.selected = selected;
    }
}
