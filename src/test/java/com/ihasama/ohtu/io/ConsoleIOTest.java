/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ihasama.ohtu.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import junit.framework.TestCase;

public class ConsoleIOTest extends TestCase {
    
    private ByteArrayOutputStream out;
    private ByteArrayInputStream in;
    private ConsoleIO io;
    
    public ConsoleIOTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        out = new ByteArrayOutputStream();
        in = new ByteArrayInputStream("123".getBytes());
        System.setOut(new PrintStream(out));
        System.setIn(in);
        io = new ConsoleIO();
    }
    
    public void testPrintln() {
        io.println();
        assertTrue(out.toString().equals("\n"));
        io.println("test");
        assertTrue(out.toString().equals("\ntest\n"));
    }
    
    public void testReadInt() {
        assertEquals(123, io.readInt("prompt"));
        assertEquals("prompt ", out.toString());
    }
    
    public void testReadLine() {
        assertEquals("123", io.readLine("prompt"));
        assertEquals("prompt ", out.toString());
    }
    
}
