/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package events;

import java.util.EventObject;
import models.MusicGenre;

/**
 *
 * @author mgkon
 */
public class MusicFileEditEventData extends EventObject {

    public String name;
    public String artist;
    public MusicGenre genre;
    public String fileLocation;

    public MusicFileEditEventData(Object source) {
        super(source);
    }

    public MusicFileEditEventData(Object source, String name, String artist, MusicGenre genre, String fileLocation) {
        super(source);
        setName(name);
        setArtist(artist);
        setGenre(genre);
        setFileLocation(fileLocation);
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

    public MusicGenre getGenre() {
        return genre;
    }

    public void setGenre(MusicGenre genre) {
        this.genre = genre;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public String getFileLocation() {
        return fileLocation;
    }
}
