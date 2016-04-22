
package com.ihasama.ohtu.ui;

import com.ihasama.ohtu.data_access.Dao;
import com.ihasama.ohtu.domain.Reference;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;

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
            JLabel label = new JLabel(ref.getId());
            add(label);
            JPanel panel = new JPanel();
            panel.add(new JButton(new EditReferenceAction("edit", dao, ref)));
            panel.add(new JButton(new DeleteReferenceAction("delete", dao, ref)));
            add(panel, "wrap");
        }
    }

    private class EditReferenceAction extends AbstractAction {
        
        private Reference ref;
        private Dao<Reference> dao;
        
        public EditReferenceAction(String text, Dao<Reference> dao, Reference ref) {
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

        private Reference ref;
        private Dao<Reference> dao;

        public DeleteReferenceAction(String text, Dao<Reference> dao, Reference ref) {
            super(text);
            this.dao = dao;
            this.ref = ref;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            dao.remove(ref);
            refresh();
        }
    }
    
}
