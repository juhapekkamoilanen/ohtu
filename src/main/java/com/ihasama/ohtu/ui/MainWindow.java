
package com.ihasama.ohtu.ui;

import com.ihasama.ohtu.data_access.Dao;
import com.ihasama.ohtu.data_access.ReferenceMemoryDao;
import com.ihasama.ohtu.domain.Reference;
import com.ihasama.ohtu.util.FileUtils;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
<<<<<<< HEAD
import java.awt.event.ActionEvent;
import java.io.IOException;
=======
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
>>>>>>> dev
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
        
        try{
            addMenu();
        } catch (Exception e){
            //derp
        }
        addContents((JPanel) frame.getContentPane());
        
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }
    
    private void addMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");

        JMenuItem selectAll = new JMenuItem("Select all");
        selectAll.addActionListener(e -> {
            for (Component c : list.getComponents()) {
                ((ReferenceListItem) c).getCheckBox().setSelected(true);
            }
        });

        JMenuItem deselectAll = new JMenuItem("Deselect all");
        deselectAll.addActionListener(e -> {
            for (Component c : list.getComponents()) {
                ((ReferenceListItem) c).getCheckBox().setSelected(false);
            }
        });

        JMenuItem saveSelected = new JMenuItem("Save selected..");
        saveSelected.addActionListener(e -> {
            Dao<Reference> selected = new ReferenceMemoryDao();

            for (Component c : list.getComponents()) {
                ReferenceListItem item = (ReferenceListItem) c;

                if (item.getCheckBox().isSelected()) {
                    selected.add(item.getRef());
                }
            }

            try {
                FileUtils.saveDaoAs(this, selected);
            } catch (IOException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        JMenuItem deleteSelected = new JMenuItem("Delete selected");
        deleteSelected.addActionListener(e -> {
            List<Reference> selected = new ArrayList<>();
            Component[] components = list.getComponents();

            for (Component c : components) {
                ReferenceListItem item = (ReferenceListItem) c;

                if (item.getCheckBox().isSelected()) {
                    selected.add(item.getRef());
                }
            }

            selected.stream().forEach(ref->dao.remove(ref));
            list.setDao(dao);
            refresh();
        });

        JMenuItem loadFrom = new JMenuItem("Load from..");
        loadFrom.addActionListener(e -> {
            Dao<Reference> dao = FileUtils.loadDaoFrom(this);

            if (dao != null) {
                this.dao = dao;
                list.setDao(dao);
                refresh();
            }
        });

        JMenuItem saveAll = new JMenuItem("Save all..");
        saveAll.addActionListener(e -> {
            try {
                FileUtils.saveDaoAs(this, dao);
            } catch (IOException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        fileMenu.add(saveAll);
        fileMenu.add(saveSelected);
        fileMenu.add(loadFrom);
        editMenu.add(selectAll);
        editMenu.add(deselectAll);
        editMenu.addSeparator();
        editMenu.add(deleteSelected);
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        
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

                list.setFilter(searchField.getText().toLowerCase());
                refresh();
            }
        });

        JScrollPane scrollPane = new JScrollPane(list, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        container.add(addBtn, "grow, gap 10 10 10 10");
        container.add(new JSeparator(), "grow");
        container.add(searchPanel);
        container.add(scrollPane);
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
