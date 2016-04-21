

package com.ihasama.ohtu.util;

import com.ihasama.ohtu.data_access.Dao;
import com.ihasama.ohtu.domain.EntryType;
import com.ihasama.ohtu.domain.FieldType;
import com.ihasama.ohtu.domain.Reference;
import com.ihasama.ohtu.io.FileIO;
import com.ihasama.ohtu.io.IO;
import com.ihasama.ohtu.ui.GUI;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public final class FileUtils {
    
    public static void saveDaoAs(GUI parent, Dao<Reference> dao) throws IOException {
        JFileChooser fc = getBibtexFileChooser();
        int ret = fc.showSaveDialog(parent.getFrame());
        if (ret == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fc.getSelectedFile();
                writeDaoToFile(new FileIO(file), dao);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void writeDaoToFile(IO io, Dao dao) throws FileNotFoundException, IOException {
        for (Object obj : dao.getObjects()) {
            io.println(obj);
            io.println();
        }
        io.close();
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

            if (reference == null) { return; }

            dao.add(reference);
        }
    }

    private static Reference getReference(Scanner scanner) {
        Reference reference = new Reference();
        String line;

        while (scanner.hasNextLine()) {
            line = scanner.nextLine().trim();

            if (Pattern.matches("^@+.*\\{.*,$", line)) {
                getTypeAndId(line, reference);
            } else if (Pattern.matches("^.*\\s*=\\s*(\\{|\").*(\\}|\"),?$", line)) {
                getField(line, reference);
            } else if (Pattern.matches("^\\}$", line)) {
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

        while (line.charAt(i) != '{' && line.charAt(i) != '\"') {
            i++;
        }

        for (i += 1; !Pattern.matches("^(\\}|\"),?$", line.substring(i)); i++) {
            value.append(line.charAt(i));
        }

        reference.addField(FieldType.valueOf(key.toString().trim().toUpperCase()),
                                StringUtils.toNormalFormat(value.toString().trim()));
    }
}
