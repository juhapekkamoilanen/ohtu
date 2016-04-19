

package com.ihasama.ohtu.util;

import com.ihasama.ohtu.data_access.Dao;
import com.ihasama.ohtu.domain.EntryType;
import com.ihasama.ohtu.domain.FieldType;
import com.ihasama.ohtu.domain.Reference;
import com.ihasama.ohtu.ui.GUI;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public static void loadDaoFrom(GUI parent, Dao<Reference> dao) {
        JFileChooser fc = getBibtexFileChooser();
        int ret = fc.showOpenDialog(parent.getFrame());
        if (ret == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fc.getSelectedFile();
                readDaoFromFile(file, dao);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void readDaoFromFile(File file, Dao<Reference> dao) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        dao.removeAll();

        while (true) {
            Reference reference = getReference(scanner);

            if (reference == null) {
                return;
            }

            dao.add(reference);
        }
    }

    private static Reference getReference(Scanner scanner) {
        Reference reference = new Reference();
        String line;

        while (scanner.hasNextLine()) {
            line = scanner.nextLine().trim();

            if (line.length() > 0 && line.charAt(0) == '@') {
                getTypeAndId(line, reference);
            } else if (line.contains("=") && line.contains("},")) {
                getField(line, reference);
            } else if (line.length() > 0 && line.charAt(0) == '}') {
                return reference;
            }
        }

        return null;
    }

    private static void getTypeAndId(String line, Reference reference) {
        StringBuilder entryType = new StringBuilder();
        int i = 1;

        for (; line.charAt(i) != '{'; i++) {
            entryType.append(line.charAt(i));
        }

        reference.setType(EntryType.valueOf(entryType.toString().trim().toUpperCase()));

        i++;

        StringBuilder id = new StringBuilder();

        for (; line.charAt(i) != ','; i++) {
            id.append(line.charAt(i));
        }

        reference.setId(id.toString().trim());
    }

    private static void getField(String line, Reference reference) {
        StringBuilder key = new StringBuilder();
        StringBuilder value = new StringBuilder();
        int i = 0;

        for (; line.charAt(i) != ' '; i++) {
            key.append(line.charAt(i));
        }

        while (line.charAt(i) != '{') {
            i++;
        }

        i++;

        for (; line.substring(i+1).contains("},"); i++) {
            value.append(line.charAt(i));
        }

        reference.addField(FieldType.valueOf(key.toString().trim().toUpperCase()),
                                StringUtils.toNormalFormat(value.toString().trim()));
    }
}
