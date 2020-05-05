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
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import views.interfaces.MusicFilesTableListener;
import views.interfaces.ToolbarEventListener;
import views.utils.ImportSongsFileFilter;

/**
 * @author AKolchev, f55283
 */
public class MainFrame extends JFrame {

    private Toolbar toolbar;
    private JFileChooser fileChooser;
    private Controller controller;
    private TablePanel tablePanel;

    /**
     * MainFrame constructor Initializes all necessary components
     */
    public MainFrame() {
        super("Music Manager");
        setLookAndFeel();
        
        setLayout(new BorderLayout());

        setJMenuBar(createMenuBar());

        toolbar = new Toolbar();
        tablePanel = new TablePanel();
        fileChooser = new JFileChooser();
        controller = new Controller();

        tablePanel.setData(controller.getMusicFilesTags());
        tablePanel.setPersonTableListener(new MusicFilesTableListener() {
            public void rowDeleted(int row) {
                controller.removeMusicFile(row);
            }
        });

        fileChooser.setFileFilter(new ImportSongsFileFilter());
        toolbar.setToolbarListener(new ToolbarEventListener() {

            @Override
            public void addMusicFilesEvent() {

                //controller.addPerson(newDummyEvent);
            }

            @Override
            public void refreshMusicFilesEvent() {
                tablePanel.refresh();
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent arg0) {
                //close any files
                dispose();
                System.gc();
            }
        });

        add(tablePanel, BorderLayout.CENTER);
        add(toolbar, BorderLayout.PAGE_START);

        setMinimumSize(new Dimension(500, 400));
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setVisible(true);
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

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem importMenuItem = new JMenuItem("Import tracks...");
        JMenuItem exportMenuItem = new JMenuItem("Export data");
        JMenuItem exitMenuItem = new JMenuItem("Exit");

        fileMenu.add(importMenuItem);
        fileMenu.add(exportMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);

        JMenu viewMenu = new JMenu("View");
        JMenuItem fullScreenMenu = new JMenuItem("Full Screen");
        JCheckBoxMenuItem showToolbarMenuItem = new JCheckBoxMenuItem("Show Toolbar");
        showToolbarMenuItem.setSelected(true);

        viewMenu.add(fullScreenMenu);
        viewMenu.add(showToolbarMenuItem);
        menuBar.add(viewMenu);

        showToolbarMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem) e.getSource();

                toolbar.setVisible(menuItem.isSelected());
            }
        });

        importMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
                    //try {
                    controller.loadMusicFiles(fileChooser.getSelectedFiles());
                    tablePanel.refresh();
                    //} catch (IOException ex) {
                    //    JOptionPane.showMessageDialog(MainFrame.this, ex, "Exception while loading files", JOptionPane.ERROR_MESSAGE);
                    //}
                }
            }
        });

        exportMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
                    //try {
                    controller.saveToFile(fileChooser.getSelectedFile());
                    //} catch (IOException ex) {
                    //    JOptionPane.showMessageDialog(MainFrame.this, ex, "Exception while exporting file", JOptionPane.ERROR_MESSAGE);
                    //}
                }
            }
        });

        exitMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                int action = JOptionPane.showConfirmDialog(MainFrame.this, "Do you really want to exit the application?", "Exit program", JOptionPane.OK_CANCEL_OPTION);
                if (action == JOptionPane.OK_OPTION) {
                    WindowListener[] listeners = getWindowListeners();

                    for (WindowListener listener : listeners) {
                        listener.windowClosing(new WindowEvent(MainFrame.this, 0));
                    }
                }
            }
        });

        fileMenu.setMnemonic(KeyEvent.VK_F);
        importMenuItem.setMnemonic(KeyEvent.VK_I);
        exportMenuItem.setMnemonic(KeyEvent.VK_E);
        exitMenuItem.setMnemonic(KeyEvent.VK_X);
        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));

        return menuBar;
    }
}
