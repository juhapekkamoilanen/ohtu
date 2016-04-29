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
        super(new MigLayout("wrap 1", "[grow]"));
        this.ref = ref;
        this.checkBox = new JCheckBox();
        addContents();
    }

    public JCheckBox getCheckBox() {
        return checkBox;
    }

    private void addContents() {
        JPanel firstPanel = new JPanel();
        firstPanel.add(new JLabel(ref.getId()));
        firstPanel.add(checkBox, "span");
        add(firstPanel);

        for (Map.Entry<FieldType, String> e : ref.getFields().entrySet()) {
            JPanel panel = new JPanel();
            JLabel label = new JLabel(e.getKey().toString().toLowerCase() + ":");
            JLabel value = new JLabel(e.getValue());
            label.setFont(fieldFont);
            value.setFont(fieldFont);
            panel.add(label);
            panel.add(value);
            add(panel);
        }

        JPanel tagPanel = new JPanel();

        JLabel label = new JLabel("tags: ");
        label.setFont(fieldFont);
        tagPanel.add(label);

        for (String tag : ref.getTags()) {
            JLabel tagLabel = new JLabel("#" + tag + " ");
            tagLabel.setFont(fieldFont);
            tagPanel.add(tagLabel);
        }

        add(tagPanel);

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
