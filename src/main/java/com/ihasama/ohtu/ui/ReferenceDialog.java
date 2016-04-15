
package com.ihasama.ohtu.ui;

import com.ihasama.ohtu.domain.EntryType;
import com.ihasama.ohtu.domain.FieldType;
import com.ihasama.ohtu.domain.Reference;
import com.ihasama.ohtu.util.Pair;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;

public class ReferenceDialog extends JDialog {
    
    private JComboBox entryCombo;
    private List<Pair<JComboBox, String>> fields;
    
    public ReferenceDialog() {
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.setLayout(new MigLayout("wrap 1", "[grow]"));
        entryCombo = new JComboBox(EntryType.values());
        fields = new ArrayList<>();
    }
    
    public Reference showDialog() {
        addContents();
        pack();
        setLocationByPlatform(true);
        setVisible(true);
        
        //Reference ref = new Reference(EntryType.ARTICLE, "art1");
        return null;
    }
    
    private void addContents() {
        add(entryCombo);
        addEmptyField();
    }
    
    private void addEmptyField() {
        JPanel panel = new JPanel(new MigLayout("insets 0, wrap 2", "[]8[grow, fill]"));
        JComboBox combo = new JComboBox(FieldType.values());
        JTextField field = new JTextField();
        
        panel.add(combo);
        panel.add(field, "grow, wmin 100px");
        add(panel, "grow");
        fields.add(new Pair(combo, field));
    }
    
}
