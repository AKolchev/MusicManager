/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import events.MusicFileEditEventData;
import fileAccessLayer.FileOperations;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.MusicFileTag;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

/**
 *
 * @author AKolchev, f55283
 */
public class Controller {

    FileOperations fo = new FileOperations();

    public List<MusicFileTag> getMusicFilesTags() {
        return fo.getMusicFilesTags();
    }

    public void loadMusicFiles(File[] selectedFiles) {

        try {
            fo.loadMusicFiles(selectedFiles);
        } catch (CannotReadException | IOException | TagException | ReadOnlyFileException | InvalidAudioFrameException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void removeMusicFiles(int[] rows) {
        fo.removeMusicFiles(rows);
    }

    public void saveEditedFileTags(MusicFileEditEventData eventData) {
        try {
            fo.saveMusicTags(eventData);
        } catch (CannotReadException | IOException | TagException | ReadOnlyFileException | InvalidAudioFrameException | CannotWriteException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void filterMusicFilesTable(String filter) {
        fo.filterMusicFiles(filter);
    }

    public void saveProjectToFile(File selectedFile) {
        try {
            fo.saveProjectToFile(selectedFile);
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadProjectFromFile(File selectedFile) {
        try {
            fo.loadProjectFromFile(selectedFile);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void reloadMusicFiles() {
        try {
            fo.reloadMusicFiles();
        } catch (CannotReadException | IOException | TagException | ReadOnlyFileException | InvalidAudioFrameException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveMusicFiles() {
        fo.saveMusicFiles();
    }

}
