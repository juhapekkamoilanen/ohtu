/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihasama.ohtu.io;

import junit.framework.TestCase;
import org.junit.Test;

/**
 *
 * @author Joonatan
 */
public class TestIO extends TestCase {

    public StubIO io;

    public TestIO(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        io = new StubIO();
        
    }

    //lisätään yksi line
    @Test
    public void testPrint() {
        io.println("Test");
        assertEquals(io.getPrints().size(),1);
    }
}
