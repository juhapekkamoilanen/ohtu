
package com.ihasama.ohtu.ui;

import com.ihasama.ohtu.data_access.Dao;
import com.ihasama.ohtu.domain.Reference;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

public class ReferenceList extends JPanel {
    
    private List<Reference> objects;
    
    public ReferenceList(Dao<Reference> dao) {
        super(new MigLayout("insets 0", "[grow]"));
        this.objects = dao.getObjects();
        addContents();
    }
    
    private void addContents() {
        for (Reference ref : objects) {
            JLabel label = new JLabel(ref.getId());
            add(label, "wrap");
        }
    }
    
}
