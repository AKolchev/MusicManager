/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author AKolchev, f55283
 */
public class ImportSongsFileFilter extends FileFilter {

    @Override
    public boolean accept(File file) {
    
        String fileName = file.getName();
        String fileExt = Helper.getFileExtension(fileName);
        
        return (file.isDirectory() || (fileExt != null && ( fileExt.toLowerCase().equals("mp3") || fileExt.toLowerCase().equals("flac")))) == true;
    }

    @Override
    public String getDescription() {
        return "Music files allowed (*.mp3, *.flac)";
    }
}
