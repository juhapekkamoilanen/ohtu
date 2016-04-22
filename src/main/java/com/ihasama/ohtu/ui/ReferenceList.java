
package com.ihasama.ohtu.ui;

import com.ihasama.ohtu.data_access.Dao;
import com.ihasama.ohtu.domain.Reference;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;

public class ReferenceList extends JPanel {
    
    private Dao<Reference> dao;
    
    public ReferenceList(Dao<Reference> dao) {
        super(new MigLayout("insets 0", "[grow]"));
        this.dao = dao;
        addContents();
    }
    
    public void refresh() {
        removeAll();
        addContents();
        revalidate();
    }

    private void addContents() {
        for (Reference ref : dao.getObjects()) {
            ReferenceListItem item = new ReferenceListItem(ref);
            item.add(new JLabel(item.toString()), "wrap");
            item.add(new JButton(new EditReferenceAction("edit", dao, item)));
            item.add(new JButton(new DeleteReferenceAction("delete", dao, item)));
            add(item, "wrap");
        }
    }

    private class EditReferenceAction extends AbstractAction {
        
        private ReferenceListItem ref;
        private Dao<Reference> dao;
        
        public EditReferenceAction(String text, Dao<Reference> dao, ReferenceListItem ref) {
            super(text);
            this.dao = dao;
            this.ref = ref;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            new ReferenceDialog("Edit Reference", dao, ref).showDialog();
            refresh();
        }
    }

    private class DeleteReferenceAction extends AbstractAction {

        private ReferenceListItem ref;
        private Dao<Reference> dao;

        public DeleteReferenceAction(String text, Dao<Reference> dao, ReferenceListItem ref) {
            super(text);
            this.dao = dao;
            this.ref = ref;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            dao.remove(ref.getRef());
            refresh();
        }
    }

    public void filter(String filter) {
        List<Component> components = Arrays.asList(this.getComponents());

        for (Component c : components) {
            ReferenceListItem ref = (ReferenceListItem) c;

            for (String value : ref.getRef().getFields().values()) {
                if (!value.contains(filter)) {
                    if (ref.getParent() != null) {
                        this.remove(ref);
                        revalidate();
                        break;
                    }
                } else if (value.contains(filter)) {
                    if (ref.getParent() == null) {
                        this.add(ref);
                        revalidate();
                        break;
                    }
                }
            }
        }
    }
}
