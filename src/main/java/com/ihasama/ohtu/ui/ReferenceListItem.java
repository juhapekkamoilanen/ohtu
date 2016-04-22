package com.ihasama.ohtu.ui;

import com.ihasama.ohtu.domain.EntryType;
import com.ihasama.ohtu.domain.FieldType;
import com.ihasama.ohtu.domain.Reference;

import javax.swing.*;

public class ReferenceListItem extends JPanel {
    private Reference ref;

    public ReferenceListItem(Reference ref) {
        this.ref = ref;
    }

    public String toString() {
        EntryType entryType = ref.getType();
        StringBuilder sb = new StringBuilder();

        for (FieldType fieldType : entryType.getRequiredFieldTypes()) {
            sb.append(fieldType.name().toLowerCase()).append(": ")
                    .append(ref.getFields().get(fieldType)).append(System.lineSeparator());
        }

        return sb.toString();
    }

    public Reference getRef() {
        return ref;
    }
}
