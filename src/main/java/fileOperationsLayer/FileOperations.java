package fileOperationsLayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import models.FlacCommentKeysEnum;
import models.MusicFileTagsModel;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.flac.FlacTag;
import org.jaudiotagger.tag.id3.ID3v24Tag;
import org.jaudiotagger.tag.vorbiscomment.VorbisCommentTag;
import utils.Helper;
import utils.SortTableRows;

/**
 * Class containing all the file IO operations
 *
 * @author AKolchev, f55283
 */
public class FileOperations {

    private final List<MusicFileTagsModel> musicFilesTags;

    /**
     * Class constructor. Initializes the musicFilesTags List collection
     */
    public FileOperations() {
        this.musicFilesTags = new LinkedList<>();
    }

    /**
     *
     * @return a collection of MusicFileTagsModel, containing the music file
     * metadata entries
     */
    public List<MusicFileTagsModel> getMusicFilesTags() {
        return Collections.unmodifiableList(musicFilesTags);
    }

    /**
     * Saves the current state of the music files metadata entries into a
     * MusicManager project file
     *
     * @param file The file into which the metadata entries state to be stored
     * @throws IOException if there is an error while writing to the file
     */
    public void saveProjectToFile(File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            MusicFileTagsModel[] fileTags = musicFilesTags.toArray(new MusicFileTagsModel[musicFilesTags.size()]);
            oos.writeObject(fileTags);
        }
    }

    /**
     * Loads a MusicManager project file, containing a stored state of music
     * files metadata entries
     *
     * @param file The MusicManager file to be loaded
     * @throws IOException if there is an error while reading the file
     * @throws ClassNotFoundException if the metadata structure in the file is
     * not compatible to the one used in the current version of the MusicManager
     * application
     */
    public void loadProjectFromFile(File file) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(file);
        try (java.io.ObjectInputStream ois = new ObjectInputStream(fis)) {
            MusicFileTagsModel[] fileTags = (MusicFileTagsModel[]) ois.readObject();
            musicFilesTags.clear();
            musicFilesTags.addAll(Arrays.asList(fileTags));
        }
    }

    /**
     * Filters the contents of the metadata collection, by applying a given key
     * word
     *
     * @param filterKeyWord the key word/s, by which the contents of the
     * metadata collection to be filtered
     */
    public void filterMusicFiles(String filterKeyWord) {
        String filter = filterKeyWord.toLowerCase();

        if ("".equals(filter)) {
            musicFilesTags.forEach(x -> x.setIsVisible(true));
        } else {
            musicFilesTags.forEach(x -> x.setIsVisible(false));
            musicFilesTags.stream().filter(x -> x.getTitle().toLowerCase().contains(filter)
                    || x.getAlbum().toLowerCase().contains(filter)
                    || x.getAlbumArtist().toLowerCase().contains(filter)
                    || x.getArtist().toLowerCase().contains(filter)
                    || x.getFileName().toLowerCase().contains(filter)
                    || x.getGenre().toLowerCase().contains(filter)
                    || String.valueOf(x.getYear()).toLowerCase().contains(filter)
            ).forEach(x -> x.setIsVisible(true));
        }
        Collections.sort(musicFilesTags, new SortTableRows());
    }

    /**
     * Reloads the contents of the music files metadata entries collection from
     * the file system
     *
     * @throws CannotReadException
     * @throws IOException
     * @throws TagException
     * @throws ReadOnlyFileException
     * @throws InvalidAudioFrameException
     */
    public void reloadMusicFiles() throws CannotReadException, IOException,
            TagException, ReadOnlyFileException, InvalidAudioFrameException {
        ArrayList<File> musicFiles = new ArrayList<>();

        musicFilesTags.forEach((musicFile) -> {
            musicFiles.add(new File(musicFile.getFileLocation()));
        });

        musicFilesTags.clear();
        File[] files = new File[musicFiles.size()];
        musicFiles.toArray(files);

        loadMusicFiles(files);
    }

    /**
     * Loads the metadata tags of given files into the file metadata collection
     *
     * @param files An array of files, from which the metadata tags to be
     * extracted
     *
     * @throws CannotReadException If a file cannot be read
     * @throws IOException If there is an error while reading a file
     * @throws TagException If there is an error with one of the tags
     * @throws ReadOnlyFileException In case of write operation over a read only
     * file
     * @throws InvalidAudioFrameException If a file contains an invalid frame
     */
    public void loadMusicFiles(File[] files) throws CannotReadException, IOException,
            TagException, ReadOnlyFileException, InvalidAudioFrameException {

        for (File file : files) {
            AudioFile audioFile = AudioFileIO.read(file);
            Tag fileTag = audioFile.getTag();
            if (fileTag != null) {
                MusicFileTagsModel fileTags = new MusicFileTagsModel();

                fileTags.setFileName(file.getName());
                fileTags.setTitle(fileTag.getValue(FieldKey.TITLE, 0));
                fileTags.setArtist(fileTag.getValue(FieldKey.ARTIST, 0));
                fileTags.setAlbum(fileTag.getValue(FieldKey.ALBUM, 0));
                fileTags.setAlbumArtist(fileTag.getValue(FieldKey.ALBUM_ARTIST, 0));

                String year = fileTag.getValue(FieldKey.YEAR, 0);
                if (!"".equalsIgnoreCase(year)) {
                    fileTags.setYear(Integer.parseInt(year));
                }
                String genreValue = fileTag.getValue(FieldKey.GENRE, 0).replace(")", "").replace("(", "").trim();
                if (genreValue != null && !"".equals(genreValue.trim())) {
                    String genre = Helper.getNormalizedGenreValue(genreValue);
                    fileTags.setGenre(genre);
                }
                
                fileTags.setFileLocation(file.getCanonicalPath());
                fileTags.setIsVisible(true);
                musicFilesTags.add(fileTags);
            }
        }
        Collections.sort(this.musicFilesTags, new SortTableRows());
    }

    /**
     * Removes given file/s from the metadata collection
     *
     * @param rows An int array, containing the positions of the rows to be
     * removed from the metadata collection
     */
    public void removeMusicFiles(int[] rows) {
        int rowCount = 0;
        List<Integer> rowsToBeDeleted = Helper.arrayToList(rows);
        Iterator<MusicFileTagsModel> collection = musicFilesTags.iterator();
        while (collection.hasNext()) {
            collection.next();
            if (rowsToBeDeleted.contains(rowCount)) {
                collection.remove();
            }
            rowCount++;
        }
    }

    /**
     * Saves the current state of the music file tags into the music files
     *
     * @throws CannotReadException If a file cannot be read
     * @throws IOException If there is an error while reading a file
     * @throws TagException If there is an error with one of the tags
     * @throws ReadOnlyFileException In case of write operation over a read only
     * file
     * @throws InvalidAudioFrameException If a file contains an invalid frame
     * @throws CannotWriteException In case of an error while writing into a
     * file
     */
    public void saveMusicFiles() throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException, CannotWriteException {
        for (MusicFileTagsModel fileTags : musicFilesTags) {
            String fileExtension = Helper.getFileExtension(fileTags.getFileLocation());
            if (fileTags.getModified()) {
                if ("mp3".equalsIgnoreCase(fileExtension)) {
                    editMp3File(fileTags);
                } else if ("flac".equalsIgnoreCase(fileExtension)) {
                    editFlacFile(fileTags);
                }
            }
        }
    }

    /**
     * Edits mp3 files
     *
     * @param fileTags
     * @throws CannotReadException If a file cannot be read
     * @throws IOException If there is an error while reading a file
     * @throws TagException If there is an error with one of the tags
     * @throws ReadOnlyFileException In case of write operation over a read only
     * file
     * @throws InvalidAudioFrameException If a file contains an invalid frame
     * @throws CannotWriteException In case of an error while writing into a
     * file
     */
    private void editMp3File(MusicFileTagsModel fileTags) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException, CannotWriteException {

        String fileLocation = fileTags.getFileLocation();
        File file = new File(fileLocation);
        MP3File mp3File = new MP3File(file.getAbsolutePath());
        ID3v24Tag tag = new ID3v24Tag();
        mp3File.setID3v2Tag(tag);

        if (fileTags.getTitle() != null) {
            tag.setField(FieldKey.TITLE, fileTags.getTitle());
        }
        if (fileTags.getArtist() != null) {
            tag.setField(FieldKey.ARTIST, fileTags.getArtist());
        }
        if (fileTags.getAlbum() != null) {
            tag.setField(FieldKey.ALBUM, fileTags.getAlbum());
        }
        if (fileTags.getAlbumArtist() != null) {
            tag.setField(FieldKey.ALBUM_ARTIST, fileTags.getAlbumArtist());
        }
        tag.setField(FieldKey.YEAR, String.valueOf(fileTags.getYear()));
        if (fileTags.getGenre() != null) {
            tag.setField(FieldKey.GENRE, fileTags.getGenre());
        }
      
        mp3File.commit();
        mp3File.save();
        fileTags.setModified(false); //The file has already been saved
    }

    /**
     * Edits flac files
     *
     * @param fileTags
     * @throws CannotReadException If a file cannot be read
     * @throws IOException If there is an error while reading a file
     * @throws TagException If there is an error with one of the tags
     * @throws ReadOnlyFileException In case of write operation over a read only
     * file
     * @throws InvalidAudioFrameException If a file contains an invalid frame
     * @throws CannotWriteException In case of an error while writing into a
     * file
     */
    private void editFlacFile(MusicFileTagsModel fileTags) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException, CannotWriteException {
        AudioFile audioFile = AudioFileIO.read(new File(fileTags.getFileLocation()));
        FlacTag tag = (FlacTag) audioFile.getTag();
        VorbisCommentTag vorbisTag = tag.getVorbisCommentTag();

        if (fileTags.getTitle() != null) {
            vorbisTag.setField(FlacCommentKeysEnum.TITLE.toString(), fileTags.getTitle());
        }
        if (fileTags.getArtist() != null) {
            vorbisTag.setField(FlacCommentKeysEnum.ARTIST.toString(), fileTags.getArtist());
        }
        if (fileTags.getAlbum() != null) {
            vorbisTag.setField(FlacCommentKeysEnum.ALBUM.toString(), fileTags.getAlbum());
        }

        if (fileTags.getAlbumArtist() != null) {
            vorbisTag.setField(FlacCommentKeysEnum.ALBUMARTIST.toString(), fileTags.getAlbumArtist());
        }

        vorbisTag.setField(FlacCommentKeysEnum.YEAR.toString(), String.valueOf(fileTags.getYear()));

        if (fileTags.getGenre() != null) {
            vorbisTag.setField(FlacCommentKeysEnum.GENRE.toString(), fileTags.getGenre());
        }

        audioFile.commit();
        fileTags.setModified(false); //The file has already been saved
    }
}
