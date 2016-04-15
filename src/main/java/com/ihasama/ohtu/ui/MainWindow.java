
package com.ihasama.ohtu.ui;

import com.ihasama.ohtu.data_access.Dao;
import com.ihasama.ohtu.domain.Reference;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import net.miginfocom.swing.MigLayout;

public class MainWindow implements Runnable, GUI {

    private JFrame frame;
    private final String title;
    private Dao<Reference> dao;
    
    public MainWindow(String title, Dao<Reference> dao) {
        this.title = title;
        this.dao = dao;
    }
    
    @Override
    public void run() {
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        addContents((JPanel) frame.getContentPane());
        
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }
    
    private void addContents(JPanel container) {
        container.setLayout(new MigLayout("", "[grow]"));
        JButton addBtn = new JButton(new AddReferenceAction("New Reference", dao));
        //ReferenceList list = new ReferenceList(dao);
        container.add(addBtn, "grow");
    }

    @Override
    public JFrame getFrame() {
        return this.frame;
    }

    @Override
    public void show() {
        SwingUtilities.invokeLater(this);
    }

    @Override
    public void hide() {
        this.frame.setVisible(false);
    }
    
    private class AddReferenceAction extends AbstractAction {

        Dao<Reference> dao;
        
        public AddReferenceAction(String text, Dao<Reference> dao) {
            super(text);
            this.dao = dao;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            Reference ref = new ReferenceDialog().showDialog();
        }
        
    }
    
}
