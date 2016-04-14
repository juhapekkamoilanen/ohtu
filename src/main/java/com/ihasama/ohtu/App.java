package com.ihasama.ohtu;

import com.ihasama.ohtu.domain.EntryType;
import com.ihasama.ohtu.domain.FieldType;
import com.ihasama.ohtu.domain.Reference;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class App {
    public static void main( String[] args ) {
        ApplicationContext ctx = new FileSystemXmlApplicationContext("src/main/resources/spring-context.xml");

        App application = ctx.getBean(App.class);
        application.run();
    }

    private void run() {
        List<Reference> references = new ArrayList();
        Scanner sc = new Scanner(System.in);
        
        while (true) {
            EntryType entryType;
            String id;
                    
            while (true) {
                try {
                    System.out.print("Reference type: ");
                    entryType = EntryType.valueOf(sc.nextLine().toUpperCase());
                    break;
                } catch (IllegalArgumentException ex) {
                    System.out.println("Illegal reference type.");
                }
            }
            
            System.out.print("Reference id: ");
            do {
                id = sc.nextLine();
            } while (id.isEmpty());
            
            Reference ref = new Reference(entryType, id);
            
            FieldType fieldType;
            String value;
            
            askfields: // label loop to jump out of it
            while (true) {
                
                while (true) {
                    try {
                        System.out.print("Field type (empty to save): ");
                        
                        String str = sc.nextLine();
                        if (str.isEmpty())
                            break askfields; // jump out of outer loop
                        
                        fieldType = FieldType.valueOf(str.toUpperCase());
                        break;
                    } catch (IllegalArgumentException ex) {
                        System.out.println("Illegal field type.");
                    }
                }
                
                System.out.print("Value: ");
                do {
                    value = sc.nextLine();
                } while (value.isEmpty());
                
                ref.addField(fieldType, value);
                System.out.println("Field added.\n");
            }
            
            references.add(ref);
            System.out.println("Reference added successfully.");
            System.out.println("Add another (y/n)?");
            String answer = sc.nextLine();
            
            if (answer.equals("n"))
                break;
        }
        
        for (Reference ref : references) {
            System.out.println(ref);
            System.out.println();
        }
    }
}
