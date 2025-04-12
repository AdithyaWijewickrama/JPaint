package com.jpaint.board;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TrayMessage {

    static final String DEF = "";
    public String mainMsg;
    public Image Image;
    public String subMsg;
    public String toolTip;
    public MessageType MsgType;
    public String path = null;

    public TrayMessage(String mainMsg) {
        this(mainMsg, null, DEF, DEF);
    }

    public TrayMessage(String mainMsg, String Image) {
        this(mainMsg, Image, DEF, DEF);
    }

    public TrayMessage(String mainMsg, String Image, String subMsg) {
        this(mainMsg, Image, subMsg, DEF);
    }

    public TrayMessage(String mainMsg, String subMsg, MessageType type) {
        this(mainMsg, null, subMsg, null, type);
    }

    public TrayMessage(String mainMsg, String subMsg, String Image, MessageType type) {
        this(mainMsg, Image, subMsg, null, type);
    }

    public TrayMessage(String mainMsg, String Image, String subMsg, String toolTip) {
        this(mainMsg, Image, subMsg, toolTip, MessageType.NONE);
    }

    public TrayMessage(String mainMsg, String Image, String subMsg, String toolTip, MessageType messageType) {
        this.mainMsg = mainMsg;
        this.Image = Toolkit.getDefaultToolkit().createImage(Image);
        this.subMsg = subMsg;
        this.toolTip = toolTip;
        this.MsgType = messageType;
    }

    public MessageType getMsgType() {
        return MsgType;
    }

    public void setMsgType(MessageType MsgType) {
        this.MsgType = MsgType;
    }

    public String getMainMsg() {
        return mainMsg;
    }

    public void setMainMsg(String mainMsg) {
        this.mainMsg = mainMsg;
    }

    public Image getImage() {
        return Image;
    }

    public void setImage(Image Image) {
        this.Image = Image;
    }

    public String getSubMsg() {
        return subMsg;
    }

    public void setSubMsg(String subMsg) {
        this.subMsg = subMsg;
    }

    public String getToolTip() {
        return toolTip;
    }

    public void setToolTip(String toolTip) {
        this.toolTip = toolTip;
    }

    public void display() throws AWTException, MalformedURLException {
        SystemTray tray = SystemTray.getSystemTray();
        TrayIcon trayIcon = new TrayIcon(Image);
        PopupMenu pm = new PopupMenu();
        MenuItem m = new MenuItem("Open");
        m.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    IVCommons.openDocument(path);
                } catch (IOException ex) {
                    Logger.getLogger(TrayMessage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        pm.add(m);
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip(toolTip);
        tray.add(trayIcon);
        trayIcon.displayMessage(mainMsg, subMsg, MsgType);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
