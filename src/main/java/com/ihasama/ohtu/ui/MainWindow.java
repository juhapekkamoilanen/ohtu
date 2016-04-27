
package com.ihasama.ohtu.ui;

import com.ihasama.ohtu.data_access.Dao;
import com.ihasama.ohtu.domain.Reference;
import com.ihasama.ohtu.util.FileUtils;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainWindow implements Runnable, GUI {

    private JFrame frame;
    private final String title;
    private Dao<Reference> dao;
    private ReferenceList list;
    
    public MainWindow(String title, Dao<Reference> dao) {
        this.title = title;
        this.dao = dao;
        this.list = new ReferenceList(dao);
    }
    
    @Override
    public void run() {
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        addMenu();
        addContents((JPanel) frame.getContentPane());
        
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }
    
    private void addMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("file");

        JMenuItem loadFrom = new JMenuItem("load from...");
        loadFrom.addActionListener(e -> {
            Dao<Reference> dao = FileUtils.loadDaoFrom(this);

            if (dao != null) {
                this.dao = dao;
                list.setDao(dao);
                list.refresh();
            }

            refresh();
        });

        JMenuItem saveAs = new JMenuItem("save as...");
        saveAs.addActionListener(e -> {
            try {
                FileUtils.saveDaoAs(this, dao);
            } catch (IOException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        fileMenu.add(saveAs);
        fileMenu.add(loadFrom);
        menuBar.add(fileMenu);
        
        frame.setJMenuBar(menuBar);
    }
    
    private void addContents(JPanel container) {
        container.setLayout(new MigLayout("wrap 1", "[grow]"));
        JButton addBtn = new JButton("Add new reference");

        addBtn.addActionListener(e -> {
            new ReferenceDialog("New Reference", dao).showDialog();
            list.refresh();
            refresh();
        });

        list = new ReferenceList(dao);
        JPanel searchPanel = new JPanel();
        JLabel searchLabel = new JLabel("Filter: ");
        JTextField searchField = new JTextField(20);

        JButton filterResetBtn = new JButton("Reset");
        filterResetBtn.addActionListener(e -> {
            searchField.setText("");
            list.setFilter("");
            refresh();
        });

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(filterResetBtn);

        searchField.addKeyListener(new KeyAdapter() {

            public void keyReleased(KeyEvent e) {
                list.setFilter(searchField.getText());
            }
        });

        container.add(addBtn, "grow, gap 10 10 10 10");
        container.add(new JSeparator(), "grow");
        container.add(searchPanel);
        container.add(list, "grow, gap 10 10 10 10");
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
    
    @Override
    public void refresh() {
        this.frame.pack();
    }
}
