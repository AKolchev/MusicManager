/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Comparator;
import models.MusicFileTag;

/**
 *
 * @author mgkon
 */
public class SortTableRows implements Comparator<MusicFileTag> {

    @Override
    public int compare(MusicFileTag t1, MusicFileTag t2) {
        
        if (t1.getIsVisible() && !t2.getIsVisible()) {
            return -1;
        }
        if (!t1.getIsVisible() && t2.getIsVisible()) {
            return 1;
        }

        return t2.getArtist().compareTo(t1.getArtist());
    }

}
