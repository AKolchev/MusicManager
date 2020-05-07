/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.utils;

import java.util.List;
import models.MusicGenre;
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

    public static void getMusicGenres(List<MusicGenre> musicGenres) {
        MusicGenre musicGenre;

        for (int i = 0; i <= 191; i++) {
            musicGenre = new MusicGenre(i, GenreTypes.getInstanceOf().getValueForId(i));
            musicGenres.add(musicGenre);
        }
    }

    public static Integer tryParseInt(String inputString) {
        try {
            return Integer.parseInt(inputString);
        } catch (NumberFormatException ex) {
            return null;
        }
    }
}
