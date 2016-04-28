
package com.ihasama.ohtu.data_access;

import com.ihasama.ohtu.domain.Reference;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReferenceMemoryDao extends MemoryDao<Reference> {

    public List<Reference> getObjects(String filter) {
        if (filter == null || filter.isEmpty()) {
            return objects;
        }

        return objects.stream().filter(ref->ref.getId().toLowerCase().contains(filter) ||
                ref.getFields().values().stream().anyMatch(v->v.toLowerCase().contains(filter)))
                .collect(Collectors.toList());
    }
}
