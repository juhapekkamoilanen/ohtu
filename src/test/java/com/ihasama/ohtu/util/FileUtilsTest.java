/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihasama.ohtu.util;

import com.ihasama.ohtu.data_access.Dao;
import com.ihasama.ohtu.data_access.ReferenceMemoryDao;
import com.ihasama.ohtu.io.ConsoleIO;
import com.ihasama.ohtu.io.IO;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import static junit.framework.Assert.assertEquals;

/**
 *
 * @author max
 */
public class FileUtilsTest {
    
    Dao dao;
    IO io;
    File file;
    File file2;
    Scanner scanner;
    FileUtils f;
    
    public FileUtilsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws FileNotFoundException {
        file = new File("test1.bib");
        file2 = new File("test.bib");
        dao = new ReferenceMemoryDao();
        io = new ConsoleIO();
        scanner = new Scanner(file);
        f = new FileUtils();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void getRefTest() throws IOException{
        FileUtils.readDaoFromFile(file, dao);
        
        assertEquals(dao.getObjects().size(), 1);
    }
//    @Test
//    public void writeDaoTest() throws FileNotFoundException, IOException{
//        FileUtils.readDaoFromFile(file, dao);
//        FileUtils.writeDaoToFile(io, dao);
//        
//    }
}
