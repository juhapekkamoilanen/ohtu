/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihasama.ohtu.data_access;

import com.ihasama.ohtu.domain.Reference;

public class MemoryReferenceDao extends MemoryDao<Reference> {
    
    public Reference findById(String id)
    {
        for (Reference ref : this.objects)
        {
            if (ref.getId().equals(id))
            {
                return ref;
            }
        }

        return null;
    }
    
}
