/*
 * Main frame of the UI
 * Wires together all views and serves as a coordinator/controller of the presentation logic
 */
package views;

import controllers.Controller;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import eventListeners.TableFilteredListener;
import eventListeners.TableRowDeletedListener;
import utils.ImportSongsFileFilter;
import eventListeners.ToolbarButtonsEventListener;
import utils.ProjectFileFilter;

/**
 * @author AKolchev, f55283
 */
public class MainFrame extends JFrame {

    private Toolbar toolbar;
    private JFileChooser fileChooser;
    private Controller controller;
    private TablePanel tablePanel;
    private JMenuBar menuBar;

    private JMenu fileMenu;
    private JMenuItem importMusicFilesMenuItem;
    private JMenuItem saveMusicFilesMenuItem;
    private JMenuItem exportProjectMenuItem;
    private JMenuItem importProjectMenuItem;
    private JMenuItem exitMenuItem;
    private JMenu viewMenu;
    private JCheckBoxMenuItem fullScreenMenu;
    private JCheckBoxMenuItem showToolbarMenuItem;

    /**
     * MainFrame constructor Initializes all necessary components
     */
    public MainFrame() {
        super("Music Manager");
        setLookAndFeel();
        initializeFormComponents();
        setFormLayout();
        setJMenuBar(createMenuBar());
        setFrameEvents();

        tablePanel.setData(controller.getMusicFilesTags());
        setVisible(true);
    }

    private void setFormLayout() {
        setLayout(new BorderLayout());
        add(tablePanel, BorderLayout.CENTER);
        add(toolbar, BorderLayout.PAGE_START);
        setMinimumSize(new Dimension(500, 400));
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    private void initializeFormComponents() {
        toolbar = new Toolbar();
        tablePanel = new TablePanel();
        fileChooser = new JFileChooser();
        controller = new Controller();
    }

    private void setFrameEvents() {

        tablePanel.setTableRowDeletedListener(new TableRowDeletedListener() {
            @Override
            public void rowDeleted(int[] rows) {
                controller.removeMusicFiles(rows);
            }
        });

        toolbar.setTableFilterListener(new TableFilteredListener() {
            @Override
            public void tableFiltered(String filter) {
                controller.filterMusicFilesTable(filter);
                tablePanel.refresh();
            }
        });

        toolbar.setToolbarButtonsListener(new ToolbarButtonsEventListener() {

            @Override
            public void reloadMusicFilesEvent() {
                reloadMusicFiles();
            }

            @Override
            public void saveMusicFilesEvent() {
                controller.saveMusicFiles();
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent arg0) {
                //close any files
                dispose();
                System.gc();
            }
        });
    }

    private void reloadMusicFiles() {
        int action = JOptionPane.showConfirmDialog(MainFrame.this, "Any unsaved changes will be lost!", "Reload music files", JOptionPane.OK_CANCEL_OPTION);
        if (action == JOptionPane.OK_OPTION) {
            controller.reloadMusicFiles();
            tablePanel.refresh();
        }
    }

    /**
     * Sets Nimbus UI as application theme
     */
    private void setLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            //Nimbus has not been found. Default theme will be used.
        }
    }

    private JMenuBar createMenuBar() {

        JMenuBar menuBar = constructMenuItems();
        addMenuEventListeners();
        setMenuShortKeys();

        return menuBar;
    }

    private JMenuBar constructMenuItems() {

        menuBar = new JMenuBar();

        fileMenu = new JMenu("File");
        importMusicFilesMenuItem = new JMenuItem("Import music files...");
        saveMusicFilesMenuItem = new JMenuItem("Save");
        exportProjectMenuItem = new JMenuItem("Export project..");
        importProjectMenuItem = new JMenuItem("Import project..");
        exitMenuItem = new JMenuItem("Exit");

        fileMenu.add(importMusicFilesMenuItem);
        fileMenu.add(saveMusicFilesMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(importProjectMenuItem);
        fileMenu.add(exportProjectMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);

        viewMenu = new JMenu("View");
        fullScreenMenu = new JCheckBoxMenuItem("Full Screen");
        fullScreenMenu.setSelected(false);

        showToolbarMenuItem = new JCheckBoxMenuItem("Show Toolbar");
        showToolbarMenuItem.setSelected(true);
        viewMenu.add(fullScreenMenu);
        viewMenu.add(showToolbarMenuItem);
        menuBar.add(viewMenu);

        return menuBar;
    }

    private void addMenuEventListeners() {

        showToolbarMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setToolbarVisibility(e);
            }
        });

        fullScreenMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setScreenMode(e);
            }
        });

        importMusicFilesMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importMusicFiles();
            }
        });

        saveMusicFilesMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.saveMusicFiles();
            }
        });

        exportProjectMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportProject();
            }
        });

        importProjectMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importProject();
            }
        });

        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applicationExit();
            }
        });
    }

    private void setToolbarVisibility(ActionEvent e) {
        JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem) e.getSource();
        toolbar.setVisible(menuItem.isSelected());

    }

    private void setScreenMode(ActionEvent e) {
        JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem) e.getSource();
        if (menuItem.isSelected()) {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        } else {
            setSize(600, 500);
        }
    }

    private void importMusicFiles() {
        fileChooser.setFileFilter(new ImportSongsFileFilter());
        fileChooser.setMultiSelectionEnabled(true);
        if (fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
            fileChooser.setSelectedFile(new File(""));
            controller.loadMusicFiles(fileChooser.getSelectedFiles());
            tablePanel.refresh();
        }
    }

    private void exportProject() {
        fileChooser.setFileFilter(new ProjectFileFilter());
        fileChooser.setSelectedFile(new File("My music manager project.mmproj"));
        fileChooser.setMultiSelectionEnabled(false);
        if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
            controller.saveProjectToFile(fileChooser.getSelectedFile());
        }
    }

    private void importProject() {
        fileChooser.setFileFilter(new ProjectFileFilter());
        fileChooser.setMultiSelectionEnabled(false);
        if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
            controller.loadProjectFromFile(fileChooser.getSelectedFile());
            tablePanel.refresh();
        }
    }

    private void applicationExit() {
        String popUpMessage = "Do you really want to exit the application?";
        String popUpTitle = "Exit program";
        int action = JOptionPane.showConfirmDialog(MainFrame.this, popUpMessage, popUpTitle, JOptionPane.OK_CANCEL_OPTION);

        if (action == JOptionPane.OK_OPTION) {
            WindowListener[] listeners = getWindowListeners();

            for (WindowListener listener : listeners) {
                listener.windowClosing(new WindowEvent(MainFrame.this, 0));
            }
        }
    }

    private void setMenuShortKeys() {
        fileMenu.setMnemonic(KeyEvent.VK_F);
        importMusicFilesMenuItem.setMnemonic(KeyEvent.VK_I);
        saveMusicFilesMenuItem.setMnemonic(KeyEvent.VK_S);
        exportProjectMenuItem.setMnemonic(KeyEvent.VK_E);
        importProjectMenuItem.setMnemonic(KeyEvent.VK_P);
        exitMenuItem.setMnemonic(KeyEvent.VK_X);
        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
    }
}
