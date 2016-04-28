package com.ihasama.ohtu.ui;

import com.ihasama.ohtu.domain.EntryType;
import com.ihasama.ohtu.domain.FieldType;
import com.ihasama.ohtu.domain.Reference;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ReferenceListItem extends JPanel {
    private static final Font idFont = new Font(Font.SANS_SERIF, Font.BOLD, 12);
    private static final Font fieldFont = new Font(Font.SANS_SERIF, Font.PLAIN, 10);
    private Reference ref;
    private JCheckBox checkBox;

    public ReferenceListItem(Reference ref) {
        super(new MigLayout("wrap 2", "[grow]"));
        this.ref = ref;
        this.checkBox = new JCheckBox();
        addContents();
    }

    public JCheckBox getCheckBox() {
        return checkBox;
    }

    private void addContents() {
        add(new JLabel(ref.getId()));
        add(checkBox, "span");

        for (Map.Entry<FieldType, String> e : ref.getFields().entrySet()) {
            JLabel label = new JLabel(e.getKey().toString().toLowerCase() + ":");
            JLabel value = new JLabel(e.getValue());
            label.setFont(fieldFont);
            value.setFont(fieldFont);
            add(label);
            add(value);
        }
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
