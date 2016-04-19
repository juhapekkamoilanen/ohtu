
package com.ihasama.ohtu.ui;

import com.ihasama.ohtu.data_access.Dao;
import com.ihasama.ohtu.domain.EntryType;
import com.ihasama.ohtu.domain.FieldType;
import com.ihasama.ohtu.domain.Reference;
import com.ihasama.ohtu.util.Pair;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReferenceDialog extends JDialog {
    
    private JComboBox refTypeCombo;
    private JTextField refIdField;
    private List<Pair<JComboBox, JTextField>> fields;
    private JPanel fieldPanel;
    private Dao<Reference> dao;

    private Reference oldRef;
    
    public ReferenceDialog(String title, Dao<Reference> dao) {
        this.setTitle(title);
        this.dao = dao;
        this.oldRef = null;
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.setLayout(new MigLayout("wrap 1", "[grow]"));
        
        refTypeCombo = new JComboBox(TypeItem.fromObjects(EntryType.values()));
        refIdField = new JTextField();
        
        fields = new ArrayList<>();
        fieldPanel = new JPanel(new MigLayout("insets 10, wrap 1", "[grow, fill]"));
    }
    
    public ReferenceDialog(String title, Dao<Reference> dao, Reference ref) {
        this(title, dao);
        init(ref);
    }
    
    private void init(Reference ref) {
        refTypeCombo.setSelectedItem(new TypeItem(ref.getType()));
        refIdField.setText(ref.getId());
        oldRef = ref;
        
        for (Map.Entry<FieldType, String> e : ref.getFields().entrySet()) {
            addEmptyField();
            Pair<JComboBox, JTextField> p = fields.get(fields.size() - 1);
            p.first.setSelectedItem(new TypeItem(p.first));
            p.second.setText(e.getValue());
        }
    }
    
    public void showDialog() {
        addContents();
        pack();
        setLocationByPlatform(true);
        setVisible(true);
    }
    
    private void addContents() {
        JPanel refDataPanel = new JPanel(new MigLayout("insets 10, wrap 2", "[]8[grow, fill]"));
        refDataPanel.add(new JLabel("Type"));
        refDataPanel.add(refTypeCombo, "grow");
        refDataPanel.add(new JLabel("ID"));
        refDataPanel.add(refIdField, "grow");
        
        JPanel buttonPanel = new JPanel(new MigLayout("insets 10"));        
        buttonPanel.add(new JButton(new AddFieldAction("Add field")));
        buttonPanel.add(new JButton(new SaveAction("Save")));
        
        add(refDataPanel, "grow");
        add(new JSeparator(), "grow");
        add(fieldPanel, "grow");
        add(new JSeparator(), "grow");
        add(buttonPanel, "grow");
        
        if (fields.isEmpty())
            addEmptyField();
    }
    
    private void addEmptyField() {
        JPanel panel = new JPanel(new MigLayout("insets 0, wrap 2", "[]8[grow, fill]"));
        JComboBox combo = new JComboBox(TypeItem.fromObjects(FieldType.values()));
        JTextField field = new JTextField();
        
        panel.add(combo);
        panel.add(field, "grow, wmin 100px");
        fieldPanel.add(panel, "grow");
        fields.add(new Pair(combo, field));
        this.revalidate();
        this.pack();
    }
    
    private class AddFieldAction extends AbstractAction {
        
        public AddFieldAction(String text) {
            super(text);
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            ReferenceDialog.this.addEmptyField();
        }
        
    }
    
    private class SaveAction extends AbstractAction {
        
        public SaveAction(String text) {
            super(text);
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            Reference ref = this.generateReference();

            if (oldRef != null) {
                oldRef.setType(ref.getType());
                oldRef.setId(ref.getId());
                oldRef.setFields(ref.getFields());
            } else {
                dao.add(ref);
            }

            ReferenceDialog.this.dispose();
        }

        private Reference generateReference() {
            TypeItem ti = (TypeItem) refTypeCombo.getSelectedItem();
            Reference ref = new Reference(
                    (EntryType) ti.getItem(),
                    refIdField.getText()
            );

            for (Pair<JComboBox, JTextField> pair : fields) {
                ti = (TypeItem) pair.first.getSelectedItem();
                ref.addField(
                        (FieldType) ti.item,
                        pair.second.getText()
                );
            }

            return ref;
        }
    }
    
    private static class TypeItem {
        
        public static TypeItem[] fromObjects(Object[] objects) {
            List<TypeItem> list = new ArrayList<>();
            for (Object o : objects)
                list.add(new TypeItem(o));
            
            return list.toArray(new TypeItem[] {});
        }
        
        private final Object item;
        
        public TypeItem(Object item) {
            this.item = item;
        }
        
        public Object getItem() {
            return item;
        }
        
        @Override
        public String toString() {
            return item.toString().toLowerCase();
        }
        
    }
    
}
