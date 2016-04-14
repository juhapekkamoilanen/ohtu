
package com.ihasama.ohtu.io;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Primary
@Component
public class ConsoleIO implements IO
{
    private Scanner scanner = new Scanner(System.in);
    
    public void print(String toPrint) {
        System.out.println(toPrint);
    }

    public int readInt(String prompt)
    {
        System.out.print(prompt+" ");
        return Integer.parseInt(scanner.nextLine());
    }

    public String readLine(String prompt)
    {
        System.out.print(prompt+" ");
        return scanner.nextLine();
    }
    
}
