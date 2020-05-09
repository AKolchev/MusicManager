/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.tableModels;

import events.MusicFileEditEventData;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import models.MusicFileTag;
import org.jaudiotagger.tag.reference.GenreTypes;
import views.interfaces.TableRowEditedListener;
import views.utils.Helper;

/**
 *
 * @author mgkon
 */
public class MusicFileTagsTableModel extends AbstractTableModel {
    
    private TableRowEditedListener tableRowEditedListener;
    
    private List<MusicFileTag> fo;
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
            //eventData.fileTagData = new MusicFileTag();
            eventData.fileTagData.setFileLocation(fileTags.getFileLocation());
            switch (col) {
                case 2:
                    fileTags.setArtist((String) value);
                    eventData.fileTagData.setArtist((String) value);
                    break;
                case 3:
                    
                    String genreValue = (String) value;
                    
                    StringBuilder genre = new StringBuilder();
                    
                    String[] genreValues = genreValue.split(",");
                    for (String genreItem : genreValues) {
                        genreItem = genreItem.trim();
                        Integer genreId = Helper.tryParseInt(genreItem);
                        String genreName;
                        if (genreId != null) {
                            genreName = GenreTypes.getInstanceOf().getValueForId(genreId);
                        } else {
                            genreName = genreItem;
                            genreId = GenreTypes.getInstanceOf().getIdForValue(genreItem);
                            if (genreId == null && !"".equals(genreName)) {
                                genreName = "Other";
                            }
                        }
                        if (genre.length() > 0) {
                            genre.append(", ");
                        }
                        genre.append(genreName);
                    }
                    fileTags.setGenre(genre.toString());
                    eventData.fileTagData.setGenre(genre.toString());
                    
                    break;
                default:
                    break;
            }
            tableRowEditedListener.tableRowEdited(eventData);
        }
    }
    
    public void setData(List<MusicFileTag> data) {
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
                return String.class;
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
