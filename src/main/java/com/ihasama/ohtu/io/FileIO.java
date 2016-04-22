/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ihasama.ohtu.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileIO implements IO {
    
    private final PrintWriter writer;
    private final BufferedReader reader;
    
    public FileIO(File file) throws FileNotFoundException, IOException {
        writer = new PrintWriter(file);
        reader = new BufferedReader(new FileReader(file));
        reader.mark(0);
    }

    @Override
    public void print(Object obj) {
        writer.print(obj);
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
    public int readInt() {
        try {
            return Integer.parseInt(reader.readLine());
        } catch (IOException ex) {
            return Integer.MIN_VALUE;
        }
    }

    @Override
    public String readLine() {
        try {
            return reader.readLine();
        } catch (IOException ex) {
            return "";
        }
    }

    @Override
    public void flushInput() {
        writer.flush();
    }

    @Override
    public void resetOutput() throws IOException {
        reader.reset();
    }

}
