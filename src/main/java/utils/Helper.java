package utils;

import java.util.ArrayList;
import java.util.List;
import org.jaudiotagger.tag.reference.GenreTypes;

/**
 * An object containing several helper methods
 *
 * @author AKolchev, f55283
 */
public class Helper {

    /**
     * Gets the extension of a given file
     *
     * @param name The name of the file
     * @return
     */
    public static String getFileExtension(String name) {
        int pointIndex = name.lastIndexOf(".");

        if (pointIndex == -1) {
            return null;
        }

        if (pointIndex == name.length() - 1) {
            return null;
        }

        return name.substring(pointIndex + 1, name.length());
    }

    /**
     * Tries to parse a string value into an Integer. Returns null if the string
     * is not a numeric value
     *
     * @param inputString
     * @return
     */
    public static Integer tryParseInt(String inputString) {
        try {
            return Integer.parseInt(inputString);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    /**
     * Converts a given int array to list
     *
     * @param intArray the int array to be converted to a list
     * @return a list of ints
     */
    public static List<Integer> arrayToList(int[] intArray) {
        List<Integer> newArray = new ArrayList<>();
        for (int item : intArray) {
            newArray.add(item);
        }

        return newArray;
    }

    /**
     * Formats the track genre/s, in accordance to the ID3 standard. Supports
     * multiple genres per track
     *
     * @param genreValue The track genre to be normalized
     * @return a normalized genre value
     */
    public static String getNormalizedGenreValue(String genreValue) {
        StringBuilder genre = new StringBuilder();

        String[] genreValues = genreValue.split(",");
        for (String genreItem : genreValues) {
            genreItem = genreItem.trim();
            Integer genreId = Helper.tryParseInt(genreItem);
            String genreName;
            if (genreId != null) {
                genreName = GenreTypes.getInstanceOf().getValueForId(genreId);
            } else {
                genreItem = genreItem.toLowerCase().substring(0, 1).toUpperCase() + genreItem.substring(1);
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

        return genre.toString();
    }
}
