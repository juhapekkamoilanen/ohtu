/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihasama.ohtu.util;

import com.ihasama.ohtu.data_access.Dao;
import com.ihasama.ohtu.data_access.ReferenceMemoryDao;
import com.ihasama.ohtu.domain.EntryType;
import com.ihasama.ohtu.domain.Reference;
import com.ihasama.ohtu.io.FileIO;
import com.ihasama.ohtu.io.IO;
import org.junit.*;

import java.io.File;
import java.io.IOException;

import static junit.framework.Assert.assertEquals;

/**
 *
 * @author max
 */
public class FileUtilsTest {
    
    Dao dao;
    IO io;
    File file;
    
    public FileUtilsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws IOException {
        file = new File("test.bib");
        dao = new ReferenceMemoryDao();
        io = new FileIO(file);
    }
    
    @After
    public void tearDown() {}

    @Test
    public void writeAndReadTest() throws IOException{
        dao.add(new Reference(EntryType.ARTICLE, "1"));
        FileUtils.writeDaoToFile(io, dao);
        dao = FileUtils.readDaoFromFile(file);
        assertEquals(dao.getObjects().size(), 1);
        file.delete();
    }
}
