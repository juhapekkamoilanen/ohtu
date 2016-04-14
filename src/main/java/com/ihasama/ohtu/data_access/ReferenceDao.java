
package com.ihasama.ohtu.data_access;

import com.ihasama.ohtu.domain.Reference;

import java.util.List;

public interface ReferenceDao
{
    List<Reference> listAll();
    Reference findById(String id);
    void add(Reference reference);
}
