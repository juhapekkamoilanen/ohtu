package com.ihasama.ohtu.data_access;

import com.ihasama.ohtu.domain.Reference;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MemoryReferenceDao implements ReferenceDao
{

    private List<Reference> references;

    public MemoryReferenceDao()
    {
        references = new ArrayList<>();
    }        

    @Override
    public List<Reference> listAll() {
        return references;
    }

    @Override
    public Reference findById(String id)
    {
        for (Reference reference : references)
        {
            if (reference.getId().equals(id))
            {
                return reference;
            }
        }

        return null;
    }

    @Override
    public void add(Reference reference)
    {
        references.add(reference);
    }

    public void setReferences(List<Reference> reference) {
        this.references = references;
    }

    public List<Reference> getReferencess() {
        return new ArrayList<>(references);
    }
}
