package models;

import java.io.Serializable;

/**
 * A model class, containing the music files metadata properties (tags and
 * comments)
 *
 * @author AKolchev, f55283
 */
public class MusicFileTagsModel implements Serializable {

    private static int count = 0;
    private String album;
    private String albumArtist;
    private String artist;
    private String title;
    private int year;
    private String genre;
    private boolean modified;
    private String fileName;
    private String fileLocation;
    private boolean isVisible;

    /**
     *
     * @return the name of the track album
     */
    public String getAlbum() {
        return album;
    }

    /**
     * Sets the track album
     *
     * @param album The name of the track album
     */
    public void setAlbum(String album) {
        this.album = album;
    }

    /**
     *
     * @return the name of the track artist
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Sets the name of the track artist
     *
     * @param artist the name of the track artist
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

    /**
     * Keeps count of the entries
     */
    public MusicFileTagsModel() {
        MusicFileTagsModel.count++;
    }

    /**
     *
     * @return The title of the track
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the track
     *
     * @param title The track title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return The name of the album artist
     */
    public String getAlbumArtist() {
        return albumArtist;
    }

    /**
     * Sets the name of the album artist
     *
     * @param albumArtist
     */
    public void setAlbumArtist(String albumArtist) {
        this.albumArtist = albumArtist;
    }

    /**
     *
     * @return The year of the track
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets the year of the track
     *
     * @param year The year of the track
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Sets the genre of the track
     *
     * @param genre the track genre
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     *
     * @return the track genre
     */
    public String getGenre() {
        return this.genre;
    }

    /**
     * Sets whether the music file metadata has been modified and not saved yet
     *
     * @param modified Boolean variable indicating if the music file data has
     * been modified
     */
    public void setModified(boolean modified) {
        this.modified = modified;
    }

    /**
     *
     * @return Whether the music file metadata has been modified and not saved
     * yet
     */
    public boolean getModified() {
        return modified;
    }

    /**
     * Sets the name of the file
     *
     * @param fileName The name of the file
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     *
     * @return The name of the file
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets the location of the file on the file system
     *
     * @param fileLocation The location of the file
     */
    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    /**
     * Gets the location of the file
     *
     * @return The file location on the file system
     */
    public String getFileLocation() {
        return fileLocation;
    }

    /**
     * Sets whether the music file metadata visibility
     *
     * @param isVisible Indicates whether the music file metadata must be
     * visible
     */
    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    /**
     *
     * @return If the music file metadata is visible
     */
    public boolean getIsVisible() {
        return isVisible;
    }
}
