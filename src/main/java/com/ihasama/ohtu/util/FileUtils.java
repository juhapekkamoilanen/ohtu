

package com.ihasama.ohtu.util;

import com.ihasama.ohtu.data_access.Dao;
import com.ihasama.ohtu.domain.Reference;
import com.ihasama.ohtu.ui.GUI;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public final class FileUtils {
    
    public static void saveDaoAs(GUI parent, Dao<Reference> dao) {
        JFileChooser fc = getBibtexFileChooser();
        int ret = fc.showSaveDialog(parent.getFrame());
        if (ret == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fc.getSelectedFile();
                writeDaoToFile(file, dao);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void writeDaoToFile(File file, Dao dao) throws FileNotFoundException {            
        PrintWriter writer = new PrintWriter(file);
        for (Object obj : dao.getObjects()) {
            writer.println(obj);
            writer.println();
        }
        writer.close();
    }
    
    private static JFileChooser getBibtexFileChooser() {
        JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter bibFilter = new FileNameExtensionFilter("BibTeX files (*.bib)", "bib");
        fc.addChoosableFileFilter(bibFilter);
        fc.setFileFilter(bibFilter);
        return fc;
    }
    
}
