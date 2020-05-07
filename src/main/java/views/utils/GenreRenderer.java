/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.utils;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import models.MusicGenre;
import org.jaudiotagger.tag.reference.GenreTypes;
import views.customComponents.SearchableComboBox;

/**
 *
 * @author mgkon
 */
public class GenreRenderer implements TableCellRenderer {

    private SearchableComboBox searchableCombo;
    private List<MusicGenre> musicGenres;

    public GenreRenderer() {
        musicGenres = new ArrayList<MusicGenre>();
        Helper.getMusicGenres(musicGenres);
        searchableCombo = new SearchableComboBox(musicGenres);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        searchableCombo.setSelectedItem(value);
        return searchableCombo;
    }
}
