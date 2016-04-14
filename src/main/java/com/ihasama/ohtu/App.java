package com.ihasama.ohtu;

import com.ihasama.ohtu.data_access.ReferenceDao;
import com.ihasama.ohtu.domain.EntryType;
import com.ihasama.ohtu.domain.FieldType;
import com.ihasama.ohtu.domain.Reference;
import com.ihasama.ohtu.io.IO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class App {
    private IO io;
    private ReferenceDao dao;

    @Autowired
    public App(IO io, ReferenceDao dao) {
        this.io = io;
        this.dao = dao;
    }

    public static void main( String[] args ) {
        ApplicationContext ctx = new FileSystemXmlApplicationContext("src/main/resources/spring-context.xml");

        App application = ctx.getBean(App.class);
        application.run();
    }

    private void run() {
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
        List<Reference> references = dao.listAll();

        if (references.isEmpty()) {
<<<<<<< HEAD
            io.print("No references.");
            System.out.println();
=======
            io.println("No references.\n");
>>>>>>> efd0c90e78fcc0ead87c15deed2b6209cde5d4e5
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
<<<<<<< HEAD
                io.print("Illegal reference type.");
=======
                io.println("Illegal reference type.");
>>>>>>> efd0c90e78fcc0ead87c15deed2b6209cde5d4e5
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
<<<<<<< HEAD
                    io.print("Illegal field type.");
=======
                    io.println("Illegal field type.");
>>>>>>> efd0c90e78fcc0ead87c15deed2b6209cde5d4e5
                }
            }

            do {
                value = io.readLine("Value:");
            } while (value.isEmpty());

            ref.addField(fieldType, value);
<<<<<<< HEAD
            io.print("Field added.\n");
        }

        dao.add(ref);
        System.out.println();
        io.print("Reference added successfully.");
        System.out.println();
        System.out.println(ref);
        System.out.println();
=======
            io.println("Field added.\n");
        }

        dao.add(ref);
        io.println("\nReference added successfully.\n");
        io.println(ref + "\n");
>>>>>>> efd0c90e78fcc0ead87c15deed2b6209cde5d4e5
    }
}
