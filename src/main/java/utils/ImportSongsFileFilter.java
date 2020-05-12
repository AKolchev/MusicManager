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
        String fileType = Helper.getFileExtension(fileName);
        
        return (file.isDirectory() || (("mp3").equalsIgnoreCase(fileType) || ("flac").equalsIgnoreCase(fileType))) == true;
    }

    @Override
    public String getDescription() {
        return "Music files allowed (*.mp3, *.flac)";
    }
}
