/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.ArrayList;
import java.util.List;
import org.jaudiotagger.tag.reference.GenreTypes;

/**
 *
 * @author AKolchev, f55283 TODO - make singleton
 */
public class Helper {

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

    public static Integer tryParseInt(String inputString) {
        try {
            return Integer.parseInt(inputString);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public static List<Integer> arrayToList(int[] intArray) {
        List<Integer> newArray = new ArrayList<>();
        for (int item : intArray) {
            newArray.add(item);
        }

        return newArray;
    }

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
