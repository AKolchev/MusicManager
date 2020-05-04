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
public class MusicFileTags implements Serializable {

    private int Id;
    private static int count = 0;
    private String name;
    private String artist;
    private String genre;

    public MusicFileTags(String name, String artist, String genre) {
        this.name = name;
        this.artist = artist;
        this.genre = genre;
        this.Id = MusicFileTags.count;

        MusicFileTags.count++;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
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
        return genre;
    }
}
