package com.ihasama.ohtu;

import com.ihasama.ohtu.domain.EntryType;
import com.ihasama.ohtu.domain.FieldType;
import com.ihasama.ohtu.domain.Reference;
import com.ihasama.ohtu.io.IO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class App {
    private IO io;

    @Autowired
    public App(IO io) {
        this.io = io;
    }

    public static void main( String[] args ) {
        ApplicationContext ctx = new FileSystemXmlApplicationContext("src/main/resources/spring-context.xml");

        App application = ctx.getBean(App.class);
        application.run();
    }

    private void run() {
        List<Reference> references = new ArrayList();

        while (true) {

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
            
            references.add(ref);
            System.out.println("Reference added successfully.");
            String answer = io.readLine("Add another (y/n)?");
            
            if (answer.equals("n"))
                break;
        }
        
        for (Reference ref : references) {
            System.out.println(ref);
            System.out.println();
        }
    }
}
