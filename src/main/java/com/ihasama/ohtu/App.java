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
        System.out.println();
        List<Reference> references = dao.listAll();

        if (references.isEmpty()) {
            System.out.println("No references.");
            System.out.println();
            return;
        }

        for (Reference reference : references) {
            System.out.println(reference);
            System.out.println();
        }
    }

    public void handleAdd() {
        System.out.println();
        EntryType entryType;
        String id;

        while (true) {
            try {
                entryType = EntryType.valueOf(io.readLine("Reference type: ").toUpperCase());
                break;
            } catch (IllegalArgumentException ex) {
                System.out.println("Illegal reference type.");
            }
        }

        do {
            id = io.readLine("Reference id: ");
        } while (id.isEmpty());

        Reference ref = new Reference(entryType, id);

        FieldType fieldType;
        String value;

        askfields: // label loop to jump out of it
        while (true) {

            while (true) {
                try {
                    String str = io.readLine("Field type (empty to save): ");
                    if (str.isEmpty())
                        break askfields; // jump out of outer loop

                    fieldType = FieldType.valueOf(str.toUpperCase());
                    break;
                } catch (IllegalArgumentException ex) {
                    System.out.println("Illegal field type.");
                }
            }

            do {
                value = io.readLine("Value: ");
            } while (value.isEmpty());

            ref.addField(fieldType, value);
            System.out.println("Field added.\n");
        }

        dao.add(ref);
        System.out.println();
        System.out.println("Reference added successfully.");
        System.out.println();
        System.out.println(ref);
        System.out.println();
    }
}
