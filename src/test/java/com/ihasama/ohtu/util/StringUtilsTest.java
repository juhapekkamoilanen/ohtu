/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihasama.ohtu.util;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author max
 */
public class StringUtilsTest {
    
    StringUtils utils;
    Pair pair;
    public StringUtilsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testNormalFormat(){
        assertEquals("@article", StringUtils.toNormalFormat("@article{id,\n" +
"title = {A New Article},\n" +
"}"));
        assertEquals("@inproceedings", StringUtils.toNormalFormat("@inproceedings{VPL11,\n" +
"author = {Vihavainen, Arto and Paksula, Matti and Luukkainen, Matti},\n" +
"title = {Extreme Apprenticeship Method in Teaching Programming for Beginners.},\n" +
"year = {2011},\n" +
"booktitle = {SIGCSE '11: Proceedings of the 42nd SIGCSE technical symposium on Computer science education},\n" +
"}"));
    }
    
    @Test
    public void pairTest(){
        
        pair = new Pair(String.class, 1);
        assertEquals(pair.first, String.class);
        assertEquals(pair.second, 1);
    }
}
