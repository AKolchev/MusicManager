/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.tableModels;

import java.util.List;
import java.util.stream.Collectors;
import javax.swing.table.AbstractTableModel;
import models.MusicFileTags;
import org.jaudiotagger.tag.reference.GenreTypes;
import utils.Helper;

/**
 *
 * @author mgkon
 */
public class MusicFileTagsTableModel extends AbstractTableModel {

    private List<MusicFileTags> fo;
    private String[] colNames = {"File name", "Title", "Artist", "Album", "Album Artist", "Year", "Genre", "Comment"};

    public MusicFileTagsTableModel() {

    }

    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return col > 0;
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        if (fo != null) {
            MusicFileTags fileTags = fo.get(row);
            switch (col) {
                case 2:
                    fileTags.setArtist((String)value);
                    break;
                case 6:

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

                    break;
                default:
                    break;
            }
        }
    }

    public void setData(List<MusicFileTags> data) {
        this.fo = data;
    }

    @Override
    public int getRowCount() {
        return fo.stream().filter(x -> x.getIsVisible() == true).collect(Collectors.toList()).size();
    }

    @Override
    public int getColumnCount() {
        return colNames.length;
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
            case 4:
                return String.class;
            case 5:
                return Integer.class;
            case 6:
                return String.class;
            case 7:
                return String.class;
            default:
                return null;
        }
    }

    @Override
    public Object getValueAt(int row, int column) {

        MusicFileTags fileTags = fo.get(row);
        if (fileTags.getIsVisible() == true) {
            switch (column) {
                case 0:
                    return fileTags.getFileName();
                case 1:
                    return fileTags.getTitle();
                case 2:
                    return fileTags.getArtist();
                case 3:
                    return fileTags.getAlbum();
                case 4:
                    return fileTags.getAlbumArtist();
                case 5:
                    return fileTags.getYear();
                case 6:
                    return fileTags.getGenre();
                case 7:
                    return fileTags.getComment();
            }
        }

        return null;
    }
}
