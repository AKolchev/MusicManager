package utils;

import java.util.Comparator;
import models.MusicFileTagsModel;

/**
 * An object, which implements the Comparator interface. Used for music file
 * metadata sorting by visibility and Album artist Ensures that after a row
 * deletion from the table, the correct entries will be visualized and one and
 * the same order will be used for all entries - by Album artist, so the entries
 * won't change order when rows are deleted
 *
 * @author AKolchev, f55283
 */
public class SortTableRows implements Comparator<MusicFileTagsModel> {

    /**
     * Compares the Visibility and Album artist values of two music files
     * metadata entries
     *
     * @param t1 The first music file metadata set
     * @param t2 The second music file metadata set
     * @return The result of the comparison, expressed as an int value
     */
    @Override
    public int compare(MusicFileTagsModel t1, MusicFileTagsModel t2) {

        if (t1.getIsVisible() && !t2.getIsVisible()) {
            return -1;
        }
        if (!t1.getIsVisible() && t2.getIsVisible()) {
            return 1;
        }

        return t2.getAlbumArtist().compareTo(t1.getAlbumArtist());
    }
}
