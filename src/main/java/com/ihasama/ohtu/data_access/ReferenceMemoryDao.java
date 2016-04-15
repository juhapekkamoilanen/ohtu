
package com.ihasama.ohtu.data_access;

import com.ihasama.ohtu.domain.Reference;
import org.springframework.stereotype.Component;

@Component
public class ReferenceMemoryDao extends MemoryDao<Reference> {
    
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
