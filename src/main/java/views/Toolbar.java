/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import views.interfaces.TableFilteredListener;
import views.interfaces.ToolbarButtonsEventListener;

/**
 *
 * @author mgkon
 */
public class Toolbar extends JToolBar implements ActionListener {

    //private JButton refreshMusicFileTagsBtn;
    private JTextField searchField;

    private ToolbarButtonsEventListener buttonsEventListener;
    private TableFilteredListener tableFilteredListener;

    public Toolbar() {

        //refreshMusicFileTagsBtn = new JButton();
        //refreshMusicFileTagsBtn.setIcon(createIcon("images/refresh-icon.png"));
        //refreshMusicFileTagsBtn.setToolTipText("Refresh music file tags");
        //refreshMusicFileTagsBtn.addActionListener(this);
        //add(refreshMusicFileTagsBtn);
        //addSeparator();
        searchField = new JTextField(25);
        searchField.setLayout(new BorderLayout());
        JLabel iconLabel = new JLabel(createIcon("images/refresh-icon.png"));
        iconLabel.setCursor(Cursor.getDefaultCursor());
        searchField.add(iconLabel, BorderLayout.LINE_END);

        JLabel searchLabel = new JLabel("Search by name, artist, genre..");
        searchLabel.setCursor(Cursor.getDefaultCursor());
        searchField.add(searchLabel, BorderLayout.LINE_START);

        iconLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                searchField.setText("");
                searchLabel.setText("Search by name, artist, genre..");
            }
        });

        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent arg0) {
                searchField.setText("");
                searchLabel.setText("Search by name, artist, genre..");
            }
        });

        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (searchField.hasFocus() && searchField.getText().length() >= 0) {
                    searchLabel.setText("");
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                tableFilteredListener.tableFiltered(searchField.getText());
                if (searchField.hasFocus() && searchField.getText().length() == 0) {
                    searchLabel.setText("Search by name, artist, genre..");
                }
            }
        });

        add(searchField);
    }

    private ImageIcon createIcon(String fileName) {
        URL url = getClass().getClassLoader().getResource(fileName);
        if (url == null) {
            System.err.println("Unable to load image: " + url);
        }
        ImageIcon icon = new ImageIcon(url, "Icon Image");
        return icon;
    }

    public void setToolbarButtonsListener(ToolbarButtonsEventListener listener) {
        this.buttonsEventListener = listener;
    }

    public void setTableFilterListener(TableFilteredListener listener) {
        this.tableFilteredListener = listener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //    eventListener.refreshMusicFilesEvent();
    }
}
