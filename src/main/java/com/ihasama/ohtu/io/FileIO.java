/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ihasama.ohtu.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileIO implements IO {
    
    private final PrintWriter writer;
    private final Scanner scanner;
    
    public FileIO(File file) throws FileNotFoundException {
        writer = new PrintWriter(file);
        scanner = new Scanner(file);
    }

    @Override
    public void println() {
        writer.println();
    }

    @Override
    public void println(Object obj) {
        writer.println(obj);
    }

    @Override
    public int readInt(String prompt) {
        return scanner.nextInt();
    }

    @Override
    public String readLine(String prompt) {
        return scanner.nextLine();
    }

}