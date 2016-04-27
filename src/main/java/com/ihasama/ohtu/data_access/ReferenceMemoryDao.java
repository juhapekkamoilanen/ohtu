
package com.ihasama.ohtu.data_access;

import com.ihasama.ohtu.domain.Reference;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReferenceMemoryDao extends MemoryDao<Reference> {

    public List<Reference> getObjects(String filter) {
        if (filter == null || filter.isEmpty()) {
            return objects;
        }

        List<Reference> filteredObjects = new ArrayList<>();

        for (Reference ref : objects) {
            if (ref.getId().contains(filter)) {
                filteredObjects.add(ref);
                break;
            }

            for (String value : ref.getFields().values()) {
                if (value.contains(filter)) {
                    filteredObjects.add(ref);
                    break;
                }
            }
        }

        return filteredObjects;
    }
}
