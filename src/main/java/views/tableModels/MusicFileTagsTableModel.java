/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.tableModels;

import events.MusicFileEditEventData;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import models.MusicFileTag;
import models.MusicGenre;
import org.jaudiotagger.tag.reference.GenreTypes;
import views.interfaces.TableRowEditedListener;

/**
 *
 * @author mgkon
 */
public class MusicFileTagsTableModel extends AbstractTableModel {

    private TableRowEditedListener tableRowEditedListener;

    private LinkedList<MusicFileTag> fo;
    private String[] colNames = {"FileLocation", "Name", "Artist", "Genre"};

    public MusicFileTagsTableModel() {

    }

    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return col > 1;
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        if (fo != null) {
            MusicFileTag fileTags = fo.get(row);
            MusicFileEditEventData eventData = new MusicFileEditEventData(this);
            eventData.setFileLocation(fileTags.getFileLocation());
            switch (col) {
                case 2:
                    fileTags.setArtist((String) value);
                    eventData.setArtist((String) value);
                    break;
                case 3:
                    Integer genreId = GenreTypes.getInstanceOf().getIdForName((String)value);
                    MusicGenre musicGenre = new MusicGenre(genreId, GenreTypes.getInstanceOf().getValueForId(genreId));
                    List<MusicGenre> genres = new ArrayList<MusicGenre>();
                    genres.add(musicGenre);
                    
                    fileTags.setGenre(genres);
                    eventData.setGenre(musicGenre);
                    break;
                default:
                    break;
            }
            tableRowEditedListener.tableRowEdited(eventData);
        }
    }

    public void setData(LinkedList<MusicFileTag> data) {
        this.fo = data;
    }

    @Override
    public int getRowCount() {
        return fo.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Class<?> getColumnClass(int col) {
        switch (col) {
            case 0:
                return String.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return MusicGenre.class;
            default:
                return null;
        }
    }

    @Override
    public Object getValueAt(int row, int column) {

        MusicFileTag fileTags = fo.get(row);

        switch (column) {
            case 0:
                return fileTags.getFileLocation();
            case 1:
                return fileTags.getName();
            case 2:
                return fileTags.getArtist();
            case 3:
                return fileTags.getGenre();
        }

        return null;
    }

    public void setTableRowEditedListener(TableRowEditedListener listener) {
        this.tableRowEditedListener = listener;
    }
}
