package com.ihasama.ohtu;

import com.ihasama.ohtu.data_access.Dao;
import com.ihasama.ohtu.data_access.ReferenceFileDao;
import com.ihasama.ohtu.domain.EntryType;
import com.ihasama.ohtu.domain.FieldType;
import com.ihasama.ohtu.domain.Reference;
import com.ihasama.ohtu.exception.InvalidFileException;
import com.ihasama.ohtu.io.IO;
import com.ihasama.ohtu.ui.MainWindow;
import java.io.File;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Component
public class App {
    private IO io;
    private Dao<Reference> dao;

    @Autowired
    public App(IO io, Dao<Reference> dao) {
        this.io = io;
        this.dao = dao;
    }

    public static void main( String[] args ) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("com/ihasama/ohtu/spring-context.xml");

        App application = ctx.getBean(App.class);
        application.runGUI();
        
        File f = new File("test.bib");
        System.out.println(f.getAbsolutePath());
        try {
            ReferenceFileDao d = new ReferenceFileDao(f);
            d.getObjects();
        } catch (InvalidFileException | IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void runGUI() {
        new MainWindow("BibTeX gen", dao).show();
    }

    public void runConsole() {        
        while (true) {
            int command = io.readInt("[1] List references [2] Add new reference [3] Quit");

            switch (command) {
                case 1:
                    handleList();
                    break;
                case 2:
                    handleAdd();
                    break;
                case 3:
                    return;
                default:
            }
        }
    }

    public void handleList() {
        io.println();
        List<Reference> references = dao.getObjects();

        if (references.isEmpty()) {

            io.println("No references.\n");
            return;
        }

        for (Reference reference : references) {
            io.println(reference + "\n");
        }
    }

    public void handleAdd() {
        io.println();
        EntryType entryType;
        String id;

        while (true) {
            try {
                entryType = EntryType.valueOf(io.readLine("Reference type:").toUpperCase());
                break;
            } catch (IllegalArgumentException ex) {

                io.println("Illegal reference type.");
            }
        }

        do {
            id = io.readLine("Reference id:");
        } while (id.isEmpty());

        Reference ref = new Reference(entryType, id);

        FieldType fieldType;
        String value;

        askfields: // label loop to jump out of it
        while (true) {

            while (true) {
                try {
                    String str = io.readLine("Field type (empty to save):");
                    if (str.isEmpty())
                        break askfields; // jump out of outer loop

                    fieldType = FieldType.valueOf(str.toUpperCase());
                    break;
                } catch (IllegalArgumentException ex) {

                    io.println("Illegal field type.");
                }
            }

            do {
                value = io.readLine("Value:");
            } while (value.isEmpty());

            ref.addField(fieldType, value);

            io.println("Field added.\n");
        }

        dao.add(ref);
        io.println("\nReference added successfully.\n");
        io.println(ref + "\n");
    }
}
