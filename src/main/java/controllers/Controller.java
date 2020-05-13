/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import fileOperationsLayer.FileOperations;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import models.MusicFileTags;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import views.MainFrame;

/**
 *
 * @author AKolchev, f55283
 */
public class Controller {

    FileOperations fo = new FileOperations();

    public List<MusicFileTags> getMusicFilesTags() {
        return fo.getMusicFilesTags();
    }

    public void loadMusicFiles(File[] selectedFiles) {
        try {
            fo.loadMusicFiles(selectedFiles);
        } catch (CannotReadException | IOException | TagException | ReadOnlyFileException | InvalidAudioFrameException ex) {
            JOptionPane.showMessageDialog(null, "Error while loading files!", "Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void removeMusicFiles(int[] rows) {
        fo.removeMusicFiles(rows);
    }

    public void filterMusicFilesTable(String filter) {
        fo.filterMusicFiles(filter);
    }

    public void saveProjectToFile(File selectedFile) {
        try {
            fo.saveProjectToFile(selectedFile);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error while saving project file!", "Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadProjectFromFile(File selectedFile) {
        try {
            fo.loadProjectFromFile(selectedFile);
        } catch (IOException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error while loading project file!", "Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void reloadMusicFiles() {
        try {
            fo.reloadMusicFiles();
        } catch (CannotReadException | IOException | TagException | ReadOnlyFileException | InvalidAudioFrameException ex) {
            JOptionPane.showMessageDialog(null, "Error while reloading music files!", "Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveMusicFiles() {
        try {
            fo.saveMusicFiles();
        } catch (CannotReadException | IOException | TagException | ReadOnlyFileException | InvalidAudioFrameException | CannotWriteException ex) {
            JOptionPane.showMessageDialog(null, "Error while saving music files!", "Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
