/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihasama.ohtu.data_access;

import com.ihasama.ohtu.domain.EntryType;
import com.ihasama.ohtu.domain.Reference;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author max
 */
public class MemoryReferenceDaoTest extends TestCase {
    
    ReferenceMemoryDao dao;
    Reference rf1;
    Reference rf2;
    
    public MemoryReferenceDaoTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        dao = new ReferenceMemoryDao();
        rf1 = new Reference(EntryType.ARTICLE, "0");
        rf2 = new Reference(EntryType.BOOK, "1");
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    
    public void testAdd() {
        dao.add(rf1);
        assertEquals(dao.getObjects().contains(rf1), true);
    }
    public void testSetRef() {
        List<Reference> uusi = new ArrayList<>();
        uusi.add(rf1);
        uusi.add(rf2);
        dao.setReferences(uusi);
        assertEquals(dao.getObjects(), uusi);
    }
    
    public void testGetObjects() {
        dao.add(rf1);
        assertEquals(dao.getObjects().get(0), rf1);
    }

    public void testRemove() {
        dao.add(rf1);
        dao.remove(rf1);
        assertEquals(0, dao.getObjects().size());
    }

    public void testRemoveAll() {
        dao.add(rf1);
        dao.add(rf2);
        dao.removeAll();
        assertEquals(0, dao.getObjects().size());
    }
    
        public void testGetWithFilter(){
        dao.add(rf1);
        dao.add(rf2);
        assertEquals(dao.getObjects("0").size(),1 );
    }
}
