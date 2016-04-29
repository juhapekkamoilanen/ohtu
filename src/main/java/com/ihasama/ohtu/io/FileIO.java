/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ihasama.ohtu.io;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileIO implements IO {
    
    private final File file;
    private BufferedWriter writer;
    private BufferedReader reader;
    private boolean hasErrored;
    private boolean isClosed;
    
    public FileIO(File file) throws FileNotFoundException, IOException {
        this.file = file;
        
        // FileWriter(file, true): append = true
        writer = new BufferedWriter(new FileWriter(file, true));
        reader = new BufferedReader(new FileReader(file));
    }

    @Override
    public void print(Object obj) {
        try {
            writer.append(obj.toString());
        } catch (IOException ex) {
            Logger.getAnonymousLogger().log(Level.SEVERE, null, ex);
            hasErrored = true;
        }
    }

    @Override
    public void println() {
        try {
            writer.newLine();
        } catch (IOException ex) {
            Logger.getAnonymousLogger().log(Level.SEVERE, null, ex);
            hasErrored = true;
        }
    }

    @Override
    public void println(Object obj) {
        print(obj.toString());
        println();
    }

    @Override
    public String readLine() {
        try {
            return reader.readLine();
        } catch (IOException ex) {
            Logger.getAnonymousLogger().log(Level.SEVERE, null, ex);
            hasErrored = true;
        }
        
        return null;
    }

    @Override
    public void close() {
        isClosed = true;
        
        try {
            writer.close();
            reader.close();
        } catch (IOException ex) {
            Logger.getAnonymousLogger().log(Level.SEVERE, null, ex);
            hasErrored = true;
        }
    }

    @Override
    public void truncate() {
        try {
            writer = new BufferedWriter(new FileWriter(file));
            reader = new BufferedReader(new FileReader(file));
        } catch (IOException ex) {
            Logger.getAnonymousLogger().log(Level.SEVERE, null, ex);
            hasErrored = true;
        }
    }

    @Override
    public void flush() {
        try {
            writer.flush();
        } catch (IOException ex) {
            Logger.getAnonymousLogger().log(Level.SEVERE, null, ex);
            hasErrored = true;
        }
    }
    
    /**
     * Flushes the stream if it's not closed and checks its error state.
     * 
     * @return true if the print stream has encountered an error.
     */
    public boolean checkError() {
        if (!isClosed)
            flush();
        return hasErrored;
    }

    @Override
    public void close() throws IOException {

        writer.close();

    }

}
