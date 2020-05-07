/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author mgkon
 */
public class MusicFileTag implements Serializable {

    private static int count = 0;
    private String name;
    private String artist;
    private List<MusicGenre> genres;
    private String fileLocation;

    public MusicFileTag() {
        MusicFileTag.count++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setGenre(List<MusicGenre> genres) {
        this.genres = genres;
    }

    public String getGenre() {
        String genresCollection = null;
        if (genres != null) {
            StringBuilder genresBuilder = new StringBuilder();
            for (MusicGenre genreItem : genres) {
                if (genresBuilder.length() <= 0) {
                    genresBuilder.append(genreItem.getGenreName());
                } else {
                    genresBuilder.append(", ");
                    genresBuilder.append(genreItem.getGenreName());
                }
            }
            genresCollection = genresBuilder.toString();
        }
        return genresCollection;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }
}
