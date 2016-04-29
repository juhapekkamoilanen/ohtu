package com.ihasama.ohtu.ui;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.HashSet;
import java.util.Set;

public class TagList extends JPanel {
    Set<String> tags;

    public TagList() {
        super(new MigLayout("insets 0", "[grow, fill]"));
        this.tags = new HashSet<>();
        addContents();
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        if (tags != null) {
            this.tags = tags;
            refresh();
        }
    }

    public void refresh() {
        removeAll();
        addContents();
        revalidate();
        repaint();
    }

    private void addContents() {
        for (String tag : tags) {
            JPanel panel = new JPanel(new MigLayout("wrap 2", "[grow]"));
            JLabel label = new JLabel(tag);

            JButton deleteBtn = new JButton("Delete");
            deleteBtn.addActionListener(e -> {
                tags.remove(tag);
                refresh();
            });
            panel.add(label);
            panel.add(deleteBtn);

            add(panel, "wrap");
        }
    }
}
