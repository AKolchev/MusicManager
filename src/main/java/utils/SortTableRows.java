/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Comparator;
import models.MusicFileTags;

/**
 *
 * @author mgkon
 */
public class SortTableRows implements Comparator<MusicFileTags> {

    @Override
    public int compare(MusicFileTags t1, MusicFileTags t2) {
        
        if (t1.getIsVisible() && !t2.getIsVisible()) {
            return -1;
        }
        if (!t1.getIsVisible() && t2.getIsVisible()) {
            return 1;
        }

        return t2.getAlbumArtist().compareTo(t1.getAlbumArtist());
    }

}
