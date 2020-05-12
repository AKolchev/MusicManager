/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import models.MusicFileTags;
import eventListeners.TableRowDeletedListener;
import views.tableModels.MusicFileTagsTableModel;

/**
 *
 * @author AKolchev, f55283
 */
public class TablePanel extends JTable {

    private JTable table;
    private MusicFileTagsTableModel tableModel;
    private JPopupMenu popup;
    private TableRowDeletedListener tableRowDeletedListener;

    public TablePanel() {
        this.tableModel = new MusicFileTagsTableModel();
        this.table = new JTable(tableModel);
        this.popup = new JPopupMenu();

        table.setRowHeight(20);

        JMenuItem removeItem = new JMenuItem("Delete row");
        popup.add(removeItem);

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

        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    void refresh() {
        tableModel.fireTableDataChanged();
    }

    public void setData(List<MusicFileTags> fo) {
        tableModel.setData(fo);
    }

    public void setTableRowDeletedListener(TableRowDeletedListener listener) {
        this.tableRowDeletedListener = listener;
    }
}
