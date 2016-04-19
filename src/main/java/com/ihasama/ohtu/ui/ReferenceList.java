
package com.ihasama.ohtu.ui;

import com.ihasama.ohtu.data_access.Dao;
import com.ihasama.ohtu.domain.Reference;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

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
            add(new JButton(new EditReferenceAction("edit", ref)), "wrap");
        }
    }
    
    private class EditReferenceAction extends AbstractAction {
        
        private Reference ref;
        
        public EditReferenceAction(String text, Reference ref) {
            super(text);
            this.ref = ref;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            Reference newRef = new ReferenceDialog("Edit Reference", ref).showDialog();
            ref.setType(newRef.getType());
            ref.setId(newRef.getId());
            ref.setFields(newRef.getFields());
            refresh();
        }
        
    }
    
}
