/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.utils;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import models.MusicGenre;
import views.customComponents.SearchableComboBox;

/**
 *
 * @author mgkon
 */
public class GenreEditor extends AbstractCellEditor implements TableCellEditor {

    private SearchableComboBox searchableCombo;
    private List<MusicGenre> musicGenres;

    public GenreEditor() {
        musicGenres = new ArrayList<MusicGenre>();
        Helper.getMusicGenres(musicGenres);
        searchableCombo = new SearchableComboBox(musicGenres);
    }

    @Override
    public Object getCellEditorValue() {
        return searchableCombo.getSelectedItem();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        searchableCombo.setSelectedItem(value);

        searchableCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
        return searchableCombo;
    }

    @Override
    public boolean isCellEditable(EventObject e) {
        return true;
    }
}
