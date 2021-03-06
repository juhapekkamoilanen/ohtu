package com.ihasama.ohtu.data_access;

import java.util.ArrayList;
import java.util.List;

public class MemoryDao<T> implements Dao<T>
{
    protected List<T> objects;

    public MemoryDao()
    {
        objects = new ArrayList<>();
    }        

    @Override
    public List<T> getObjects() {
        return new ArrayList<>(objects);
    }

    @Override
    public List<T> getObjects(String filter) {
        return new ArrayList<>(objects);
    }

    @Override
    public void add(T obj)
    {
        objects.add(obj);
    }

    @Override
    public void removeAll() { objects.clear(); }

    @Override
    public void remove(T obj) { objects.remove(obj); }

    public void setReferences(List<T> objects) {
        this.objects = objects;
    }
}
