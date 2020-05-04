/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import fileAccessLayer.FileOperations;
import java.io.File;
import java.util.List;
import models.MusicFileTags;

/**
 *
 * @author AKolchev, f55283
 */
public class Controller {
    FileOperations fo = new FileOperations(); 
    
    public List<MusicFileTags> getMusicFilesTags(){
       return fo.getMusicFilesTags();
    }
    public static void loadMusicFiles(File[] selectedFiles) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void saveToFile(File selectedFile) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void removeMusicFile(int row) {
        //db.removePerson(row);
    }
}
