package utils;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * An object extending FIleFilter, used as a file extension filter in the during
 * the music files chooser window
 *
 * @author AKolchev, f55283
 */
public class ImportSongsFileFilter extends FileFilter {

    /**
     * Filters files by supported extensions (mp3 and flac)
     *
     * @param file The Files to be filtered
     * @return
     */
    @Override
    public boolean accept(File file) {

        String fileName = file.getName();
        String fileType = Helper.getFileExtension(fileName);

        return (file.isDirectory() || (("mp3").equalsIgnoreCase(fileType)
                || ("flac").equalsIgnoreCase(fileType))) == true;
    }

    /**
     * Sets a description in the file chooser window
     *
     * @return The description for file extensions allowed
     */
    @Override
    public String getDescription() {
        return "Music files allowed (*.mp3, *.flac)";
    }
}
