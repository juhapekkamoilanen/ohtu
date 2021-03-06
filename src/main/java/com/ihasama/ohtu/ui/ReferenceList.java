
package com.ihasama.ohtu.ui;

import com.ihasama.ohtu.data_access.Dao;
import com.ihasama.ohtu.domain.Reference;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class ReferenceList extends JPanel {

    private Dao<Reference> dao;
    private String filter;

    public ReferenceList(Dao<Reference> dao) {
        super(new MigLayout("insets 0", "[grow]"));
        this.dao = dao;
        this.filter = null;
        addContents();
    }
    
    public void refresh() {
        removeAll();
        addContents();
        revalidate();
        repaint();
    }

    private void addContents() {
        for (Reference ref : dao.getObjects(filter)) {
            ReferenceListItem item = new ReferenceListItem(ref);

            JButton editBtn = new JButton("Edit");
            editBtn.addActionListener(e -> {
                new ReferenceDialog("Edit Reference", dao, item).showDialog();
                refresh();
            });
            item.add(editBtn);

            JButton deleteBtn = new JButton("Delete");
            deleteBtn.addActionListener(e -> {
                dao.remove(ref);
                refresh();
            });
            item.add(deleteBtn);

            add(item, "wrap");
        }
    }

    public void setDao(Dao<Reference> dao) {
        this.dao = dao;
        refresh();
    }

    public void setFilter(String filter) {
        this.filter = filter;
        refresh();
    }
}
