package com.ihasama.ohtu.domain;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import junit.framework.TestCase;

/**
 *
 * @author max
 */
public class ReferenceTest extends TestCase {
    
    public Reference ref;
    
    public ReferenceTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        ref = new Reference(EntryType.ARTICLE, "0");
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGetId() {
        assertEquals(ref.getId(), "0");
    }
    public void testGetType() {
        assertEquals(EntryType.ARTICLE, ref.getType());
    }
    public void testAddFieldTest() {
        ref.addField(FieldType.AUTHOR, "jerrykotton");
        assertEquals(ref.getFields().containsKey(FieldType.AUTHOR), true);
    }
    public void testAddExistingField() {
        ref.addField(FieldType.BOOKTITLE, "Siisti");
        ref.addField(FieldType.BOOKTITLE, "eisiisti");
        assertEquals(ref.getFields().containsValue("eisiisti"), false);
    }
    public void testToString() {
        ref.addField(FieldType.AUTHOR, "PASI SAASTA");
        assertEquals("@article{0,\n" +
"author = {PASI SAASTA},\n" +
"}", ref.toString());
    }
}
