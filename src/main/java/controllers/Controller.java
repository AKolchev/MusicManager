/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import fileAccessLayer.FileOperations;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.MusicFileTags;

/**
 *
 * @author AKolchev, f55283
 */
public class Controller {
    FileOperations fo = new FileOperations(); 
    
    public LinkedHashMap<String, MusicFileTags> getMusicFilesTags(){
       return fo.getMusicFilesTags();
    }
    public void loadMusicFiles(File[] selectedFiles) {
        try {
            fo.loadMusicFiles(selectedFiles);
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveToFile(File selectedFile) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void removeMusicFile(int row) {
        //db.removePerson(row);
    }
}
