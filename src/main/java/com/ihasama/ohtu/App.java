package com.ihasama.ohtu;

import com.ihasama.ohtu.data_access.Dao;
import com.ihasama.ohtu.domain.EntryType;
import com.ihasama.ohtu.domain.FieldType;
import com.ihasama.ohtu.domain.Reference;
import com.ihasama.ohtu.io.IO;
import com.ihasama.ohtu.ui.MainWindow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class App {

    private IO io;
    private Dao<Reference> dao;

    @Autowired
    public App(IO io, Dao<Reference> dao) {
        this.io = io;
        this.dao = dao;
    }

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("com/ihasama/ohtu/spring-context.xml");

        App application = ctx.getBean(App.class);
        application.runGUI();

        /*
         File f = new File("test.bib");
         System.out.println(f.getAbsolutePath());
         try {
         ReferenceFileDao d = new ReferenceFileDao(f);
         d.getObjects();
         } catch (InvalidFileException | IOException ex) {
         Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
         }*/
    }

    public void runGUI() {
        new MainWindow("BibTeX gen", dao).show();
    }

    public void runConsole() {
        while (true) {
            io.print("[1] List references [2] Add new reference [3] Quit [4] Remove reference [5] Filter references");
            int command = Integer.parseInt(io.readLine());

            switch (command) {
                case 1:
                    handleList();
                    break;
                case 2:
                    handleAdd();
                    break;
                case 3:
                    return;
                case 4:
                    handleRemove();
                    break;
                case 5:
                    handleFilter();
                    break;
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

        entryType = readEntryType();

        id = readUserInput("Reference id: ");

        Reference ref = new Reference(entryType, id);

        ref = askFields(ref);
        
        dao.add(ref);
        io.println("\nReference added successfully.\n");
        io.println(ref + "\n");
    }
    
    private Reference askFields(Reference ref) {
        FieldType fieldType;
        String value;
        
        while (true) {

            while (true) {
                try {
                    io.print("Field type (empty to save): ");
                    String str = io.readLine();
                    if (str.isEmpty()) {
                        return ref;
                    }
                    fieldType = FieldType.valueOf(str.toUpperCase());
                    break;
                } catch (IllegalArgumentException ex) {

                    io.println("Illegal field type.");
                }
            }

            value = readUserInput("Value: ");
            ref.addField(fieldType, value);
            io.println("Field added.\n");
        }
    } 

    public void handleRemove() {
        String id;

        io.print("Give reference id to remove: ");

        id = readUserInput("");

        if (dao.getObjects(id).isEmpty()) {
            io.println("Reference of that id doesn't exist");
        } else {
            for (Reference ref : dao.getObjects(id)) {
                if (ref.getId().equals(id)) {
                    dao.remove(ref);
                }
            }
        }
    }

    public void handleFilter() {
        String keyword;

        io.print("Give keyword for filtering: ");

        keyword = readUserInput("");

        if (dao.getObjects(keyword).isEmpty()) {
            io.println("References containing keyword do not exist\n");
        } else {
            for (Reference ref : dao.getObjects(keyword)) {
                io.println(ref + "\n");
            }
        }
    }
    
    private String readUserInput(String message) {
        String input;
        
        do {
            showMessage(message);
            input = io.readLine();
        } while (input.isEmpty());
        
        return input;
    }
    
    private void showMessage(String message) {
        if (!message.equals("")) {
            io.print(message);
        }
    }
    
    private EntryType readEntryType() {
        EntryType entryType;
        while (true) {
            try {
                io.print("Reference type: ");
                entryType = EntryType.valueOf(readUserInput("").toUpperCase());
                break;
            } catch (IllegalArgumentException ex) {

                io.println("Illegal reference type.");
            }
        }
        return entryType;
    }
}
