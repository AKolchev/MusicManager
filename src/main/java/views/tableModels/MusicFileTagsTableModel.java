package views.tableModels;

import java.util.List;
import java.util.stream.Collectors;
import javax.swing.table.AbstractTableModel;
import models.MusicFileTagsModel;
import utils.Helper;

/**
 * An object, used by the table displaying music files metadata. Extends the
 * AbstractTableModel class
 *
 * @author AKolchev, f55283
 */
public class MusicFileTagsTableModel extends AbstractTableModel {

    private List<MusicFileTagsModel> musicTags;
    private String[] colNames = {"File name", "Title", "Artist", "Album", "Album Artist", "Year", "Genre"};

    /**
     * Gets the title of the column
     *
     * @param column The column position the name of to be returned
     * @return the title of the column
     */
    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }

    /**
     * Indicates which cells are editable. All cells are editable, except those
     * in column File name
     *
     * @param row The cell row
     * @param col The cell columns
     * @return Whether the cell is editable
     */
    @Override
    public boolean isCellEditable(int row, int col) {
        return col > 0;
    }

    /**
     * Sets a value in a given cell
     *
     * @param value The value to be set
     * @param row the cell row
     * @param col the cell column
     */
    @Override
    public void setValueAt(Object value, int row, int col) {
        if (musicTags != null) {
            MusicFileTagsModel fileTags = musicTags.get(row);
            fileTags.setModified(true);
            switch (col) {
                case 1:
                    fileTags.setTitle((String) value);
                    break;
                case 2:
                    fileTags.setArtist((String) value);
                    break;
                case 3:
                    fileTags.setAlbum((String) value);
                    break;
                case 4:
                    fileTags.setAlbumArtist((String) value);
                    break;
                case 5:
                    fileTags.setYear((Integer) value);
                    break;
                case 6:
                    String genreValue = (String) value;
                    String genre = Helper.getNormalizedGenreValue(genreValue);
                    fileTags.setGenre(genre);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Gets the count of table rows
     * 
     * @return the number of table rows
     */
    @Override
    public int getRowCount() {
        return musicTags.stream().filter(x -> x.getIsVisible() == true)
                .collect(Collectors.toList()).size();
    }

    /**
     * Gets the count of table columns
     * @return The number of table columns
     */
    @Override
    public int getColumnCount() {
        return colNames.length;
    }

    /**
     * Gets the type of a given column
     *
     * @param col The column position
     * @return The type of the value stored in that column
     */
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
            default:
                return null;
        }
    }

    /**
     * Gets the value of a given cell
     *
     * @param row The row of the cell
     * @param column The column of the cell
     * @return The value of the given cell
     */
    @Override
    public Object getValueAt(int row, int column) {

        MusicFileTagsModel fileTags = musicTags.get(row);
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
            }
        }

        return null;
    }

    /**
     * Loads the table model with data
     *
     * @param data The music files metadata entries to be loaded into the table
     */
    public void setData(List<MusicFileTagsModel> data) {
        this.musicTags = data;
    }
}
