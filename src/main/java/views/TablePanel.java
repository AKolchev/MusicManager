/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import events.MusicFileEditEventData;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import models.MusicFileTag;
import views.interfaces.TableRowDeletedListener;
import views.interfaces.TableRowEditedListener;
import views.tableModels.MusicFileTagsTableModel;
import views.utils.GenreEditor;
import views.utils.GenreRenderer;

/**
 *
 * @author AKolchev, f55283
 */
public class TablePanel extends JTable{

    private JTable table;
    private MusicFileTagsTableModel tableModel;
    private JPopupMenu popup;
    private TableRowDeletedListener tableRowDeletedListener;
    private TableRowEditedListener tableRowEditedListener;
    
    public TablePanel() {
        this.tableModel = new MusicFileTagsTableModel();
        this.table = new JTable(tableModel);
        this.popup = new JPopupMenu();
        
        table.setRowHeight(20);
        
        table.setDefaultRenderer(GenreRenderer.class, new GenreRenderer());
        table.setDefaultEditor(GenreEditor.class, new GenreEditor());
        
        JMenuItem removeItem = new JMenuItem("Delete row");
        popup.add(removeItem);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                int row = table.rowAtPoint(e.getPoint());

                //table.getSelectionModel().setSelectionInterval(row, row);

                if (e.getButton() == MouseEvent.BUTTON3) {
                    popup.show(table, e.getX(), e.getY());
                }
            }
        });

        removeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                
                if(tableRowDeletedListener != null){
                    tableRowDeletedListener.rowDeleted(row);
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
    
    public void setData(LinkedList<MusicFileTag> fo) {
        tableModel.setData(fo);
    }
    
    public void setTableRowDeletedListener(TableRowDeletedListener listener){
        this.tableRowDeletedListener = listener;
    }

    void setTableRowEditedListener(TableRowEditedListener listener) {
        this.tableRowEditedListener = listener;
        
        tableModel.setTableRowEditedListener(new TableRowEditedListener() {
            @Override
            public void tableRowEdited(MusicFileEditEventData event) {
                tableRowEditedListener.tableRowEdited(event);
            }
        });}
}