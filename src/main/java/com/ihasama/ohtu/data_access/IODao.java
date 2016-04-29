/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ihasama.ohtu.data_access;

import com.ihasama.ohtu.exception.InvalidFileException;
import com.ihasama.ohtu.io.IO;
import java.util.List;

public abstract class IODao<T> implements Dao<T> {
    
    protected final IO io;
    
    public IODao(IO io) throws InvalidFileException {
        this.io = io;
    }

    @Override
    public void add(T t) {
        io.println(t);
        io.println();
    }

    @Override
    public void removeAll() {
        io.truncate();
    }

    @Override
    public void remove(T t) {
        List<T> objects = getObjects();
        objects.remove(t);
        io.truncate();
        for (T obj : objects)
            add(obj);
    }

}
