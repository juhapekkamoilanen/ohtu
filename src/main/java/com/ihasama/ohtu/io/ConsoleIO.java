
package com.ihasama.ohtu.io;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Scanner;

@Primary
@Component
public class ConsoleIO implements IO
{
    private Scanner scanner = new Scanner(System.in);

    @Override
    public void print(Object obj) {
        System.out.print(obj);
    }
    
    @Override
    public void println() {
        System.out.println();
    }
    
    @Override
    public void println(Object obj) {
        System.out.println(obj);
    }

    @Override
    public String readLine()
    {
        return scanner.nextLine();
    }

    @Override
    public void close() {
    }

    @Override
    public void truncate() {
    }

    @Override
    public void flush() throws IOException {
    }
}
