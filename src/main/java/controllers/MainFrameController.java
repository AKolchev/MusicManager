package controllers;

import fileOperationsLayer.FileOperations;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import models.MusicFileTagsModel;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

/**
 * The controller class of the MainFrame view Controls the events and operations
 * happening in the MainFrame Separates the view from the FileOperations layer
 *
 * @author AKolchev, f55283
 */
public class MainFrameController {

    FileOperations fileOperations = new FileOperations();

    /**
     * Invokes the FileOperationsLayer and returns a collection containing music
     * files metadata
     *
     * @return a collection of music files metadata
     */
    public List<MusicFileTagsModel> getMusicFilesTags() {
        return fileOperations.getMusicFilesTags();
    }

    /**
     * Loads music files metadata into a collection, from preselected music
     * files
     *
     * @param selectedFiles File array, containing selected music files
     */
    public void loadMusicFiles(File[] selectedFiles) {
        try {
            fileOperations.loadMusicFiles(selectedFiles);
        } catch (CannotReadException | IOException | TagException | ReadOnlyFileException | InvalidAudioFrameException ex) {
            JOptionPane.showMessageDialog(null, "Error while loading files!", "Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(MainFrameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Unloads file metadata from the table
     *
     * @param rows the row positions of the music files in the table
     */
    public void removeMusicFiles(int[] rows) {
        fileOperations.removeMusicFiles(rows);
    }

    /**
     * Filters the loaded music files The filter is applied to all visualized
     * metadata tags
     *
     * @param filter the string applied as a filter to the records
     */
    public void filterMusicFiles(String filter) {
        fileOperations.filterMusicFiles(filter);
    }

    /**
     * Saves the current state of the table entries into a MusicManager project
     * file
     *
     * @param selectedFile The file into which the table entries state to be
     * saved
     */
    public void saveProjectToFile(File selectedFile) {
        try {
            fileOperations.saveProjectToFile(selectedFile);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error while saving project file!", "Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(MainFrameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Loads a MusicManager project file
     *
     * @param selectedFile the selected MusicManager file to be loaded
     */
    public void loadProjectFromFile(File selectedFile) {
        try {
            fileOperations.loadProjectFromFile(selectedFile);
        } catch (IOException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error while loading project file!", "Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(MainFrameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Reloads the music files in the table
     */
    public void reloadMusicFiles() {
        try {
            fileOperations.reloadMusicFiles();
        } catch (CannotReadException | IOException | TagException | ReadOnlyFileException | InvalidAudioFrameException ex) {
            JOptionPane.showMessageDialog(null, "Error while reloading music files!", "Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(MainFrameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Saves the file tags from the table into the respective music files
     */
    public void saveMusicFiles() {
        try {
            fileOperations.saveMusicFiles();
        } catch (CannotReadException | IOException | TagException | ReadOnlyFileException | InvalidAudioFrameException | CannotWriteException ex) {
            JOptionPane.showMessageDialog(null, "Error while saving music files!", "Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(MainFrameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
