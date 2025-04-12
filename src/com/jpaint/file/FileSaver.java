package com.jpaint.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author AW Developer
 */
public class FileSaver {

    private FileOutputStream fo;

    public FileSaver(String filePath) {
        try {
            fo = new FileOutputStream(new File(filePath));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileSaver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
