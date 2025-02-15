package views;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import eventListeners.TableFilteredListener;
import eventListeners.ToolbarButtonsEventListener;

/**
 * Toolbar partial view of the MainFrame. Contains two buttons, for saving and
 * reloading music files and a search field, for filtering table contents
 *
 * @author AKolchev, f55283
 */
public class ToolbarPartialView extends JToolBar implements ActionListener {

    private JButton reloadMusicFileTagsBtn;
    private JButton saveMusicTagsBtn;
    private JButton importMusicTagsBtn;
    private JTextField searchField;
    private JLabel iconLabel;
    private ToolbarButtonsEventListener buttonsEventListener;
    private TableFilteredListener tableFilteredListener;
    private JLabel searchLabel;

    /**
     * The class constructor is used for the toolbar initialization.
     */
    public ToolbarPartialView() {
        addImportButton();
        addSaveButton();
        addReloadButton();
        add(Box.createHorizontalGlue());
        addSearchField();
        addEventListeners();
    }

    /**
     * Creates the Reload button.
     */
    private void addReloadButton() {

        reloadMusicFileTagsBtn = new JButton();
        reloadMusicFileTagsBtn.setLayout(new FlowLayout());
        reloadMusicFileTagsBtn.setIcon(createIcon("images/refresh-icon.png"));
        reloadMusicFileTagsBtn.setToolTipText("Refresh music files");
        reloadMusicFileTagsBtn.addActionListener(this);
        add(reloadMusicFileTagsBtn);
    }

    /**
     * Creates the Save button.
     */
    private void addSaveButton() {
        saveMusicTagsBtn = new JButton();
        saveMusicTagsBtn.setLayout(new FlowLayout());
        saveMusicTagsBtn.setIcon(createIcon("images/save-icon.png"));
        saveMusicTagsBtn.setToolTipText("Save music tags");
        saveMusicTagsBtn.addActionListener(this);
        add(saveMusicTagsBtn);
    }

    /**
     * Creates the Import button.
     */
    private void addImportButton() {
        importMusicTagsBtn = new JButton();
        importMusicTagsBtn.setLayout(new FlowLayout());
        importMusicTagsBtn.setIcon(createIcon("images/upload-icon.png"));
        importMusicTagsBtn.setToolTipText("Import music files");
        importMusicTagsBtn.addActionListener(this);
        add(importMusicTagsBtn);
    }
    
    /**
     * Creates the Search text areа.
     */
    private void addSearchField() {
        searchField = new JTextField(25);
        searchField.setLayout(new BorderLayout());
        iconLabel = new JLabel(createIcon("images/refresh-icon.png"));
        iconLabel.setCursor(Cursor.getDefaultCursor());
        searchField.add(iconLabel, BorderLayout.LINE_END);
        searchLabel = new JLabel("Search by name, artist, genre..");
        searchLabel.setCursor(Cursor.getDefaultCursor());
        searchField.add(searchLabel, BorderLayout.LINE_START);
        add(searchField);
    }

    /**
     * Gets an ImageIcon by a given file path.
     *
     * @param fileName The icon file path
     * @return the ImageIcon to be loaded
     */
    private ImageIcon createIcon(String fileName) {
        URL url = getClass().getClassLoader().getResource(fileName);
        if (url == null) {
            System.err.println("Unable to load image: " + url);
        }
        ImageIcon icon = new ImageIcon(url, "Icon Image");
        return icon;
    }

    /**
     * Sets events for the Search text area, which control the text label of the
     * search field.
     */
    private void addEventListeners() {
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
    }

    /**
     * Sets an event listener for button clicks.
     *
     * @param listener An instance of ToolbarButtnosEventListener, acting as
     * a listener for toolbar button clicks inside the ToolbarPartialView
     */
    public void setToolbarButtonsListener(ToolbarButtonsEventListener listener) {
        this.buttonsEventListener = listener;
    }

    /**
     * Sets an event listener for entries in the Search text area (the filter).
     *
     * @param listener An instance of TableFilteredListener, acting as a 
     * listener for table rows filtering
     */
    public void setTableFilterListener(TableFilteredListener listener) {
        this.tableFilteredListener = listener;
    }

    /**
     * An event fired upon a button click.
     *
     * @param e The ActionEvent information, stored for the event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btnSource = (JButton) e.getSource();
        if (btnSource == reloadMusicFileTagsBtn) {
            buttonsEventListener.reloadMusicFilesEvent();
        } else if (btnSource == saveMusicTagsBtn) {
            buttonsEventListener.saveMusicFilesEvent();
        } else if (btnSource == importMusicTagsBtn) {
            buttonsEventListener.importMusicFilesEvent();
        }
    }
}
