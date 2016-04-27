
package com.ihasama.ohtu.data_access;

import java.util.List;

public interface Dao <T>
{
    List<T> getObjects();
    List<T> getObjects(String filter);
    void add(T t);
    void removeAll();
    void remove(T t);
}
