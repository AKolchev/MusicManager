/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;

/**
 *
 * @author mgkon
 */
public class MusicFileTag implements Serializable {

    private static int count = 0;
    private String name;
    private String artist;
    private String genre;
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

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getGenre() {
        return this.genre;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public String getFileLocation() {
        return fileLocation;
    }
}
