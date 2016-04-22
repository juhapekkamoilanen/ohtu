
package com.ihasama.ohtu.io;

import java.io.IOException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Primary
@Component
public class ConsoleIO implements IO
{
    private Scanner scanner = new Scanner(System.in);
    
    @Override
    public void println() {
        System.out.println();
    }
    
    @Override
    public void println(Object obj) {
        System.out.println(obj);
    }

    @Override
    public int readInt(String prompt)
    {
        System.out.print(prompt+" ");
        return Integer.parseInt(scanner.nextLine());
    }

    @Override
    public String readLine(String prompt)
    {
        System.out.print(prompt+" ");
        return scanner.nextLine();
    }

    @Override
    public void close() throws IOException {

        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }
    
}
