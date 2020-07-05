package views;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import models.MusicFileTagsModel;
import eventListeners.TableRowDeletedListener;
import views.tableModels.MusicFileTagsTableModel;

/**
 * A partial view of the MainFrame. Contains a table listing the music files
 * metadata.
 *
 * @author AKolchev, f55283
 */
public class TablePanelPartialView extends JTable {

    private final JTable table;
    private final MusicFileTagsTableModel tableModel;
    private final JPopupMenu popup;
    private final JMenuItem removeItem;
    private TableRowDeletedListener tableRowDeletedListener;

    /**
     * The class constructor initializes the properties of the table panel.
     */
    public TablePanelPartialView() {
        this.tableModel = new MusicFileTagsTableModel();
        this.table = new JTable(tableModel);
        this.popup = new JPopupMenu();
        this.removeItem = new JMenuItem("Delete row");

        popup.add(removeItem);
        table.setRowHeight(20);
        table.getColumnModel().getColumn(5).setPreferredWidth(50);
        addEventListeners();
        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    /**
     * Sets event listeners, for the delete row functionality
     */
    private void addEventListeners() {

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                if (e.getButton() == MouseEvent.BUTTON3) {
                    popup.show(table, e.getX(), e.getY());
                }
            }
        });

        removeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] rows = table.getSelectedRows();

                if (tableRowDeletedListener != null) {
                    tableRowDeletedListener.rowDeleted(rows);
                    tableModel.fireTableDataChanged();
                }
            }
        });
    }

    /**
     * Signals the table model to refresh the table data.
     */
    void refresh() {
        tableModel.fireTableDataChanged();
    }

    /**
     * Sets data in the table model
     *
     * @param musicFileTags The music files metadata sets to be set in the table
     */
    public void setData(List<MusicFileTagsModel> musicFileTags) {
        tableModel.setData(musicFileTags);
    }

    /**
     * Sets a listener for deleted table rows
     *
     * @param listener An instance of TableRowDeletedListener, acting as a
     * listener for table row deletions inside the TablePanelPartialView
     */
    public void setTableRowDeletedListener(TableRowDeletedListener listener) {
        this.tableRowDeletedListener = listener;
    }
}
