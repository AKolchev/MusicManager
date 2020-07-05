package utils;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * An object extending FIleFilter, used as a file extension filter in the
 * MusicManagerProject (mmproj) file chooser window
 *
 * @author AKolchev, f55283
 */
public class ProjectFileFilter extends FileFilter {

    /**
     * Filters files by supported extensions (mmproj)
     *
     * @param file The Files to be filtered
     * @return Boolean value, indicating if a given file is of type .mmproj or not
     */
    @Override
    public boolean accept(File file) {

        String fileName = file.getName();
        String fileExt = Helper.getFileExtension(fileName);

        return (file.isDirectory() || (fileExt != null && fileExt.toLowerCase().equals("mmproj"))) == true;
    }

    /**
     * Sets a description in the file chooser window
     *
     * @return The description message
     */
    @Override
    public String getDescription() {
        return "Music manager project files (*.mmproj)";
    }
}
