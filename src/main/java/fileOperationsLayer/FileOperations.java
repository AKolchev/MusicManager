/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import java.util.logging.LogManager;
import models.FlacCommentKeysEnum;
import models.MusicFileTags;
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
import org.jaudiotagger.tag.reference.GenreTypes;
import org.jaudiotagger.tag.vorbiscomment.VorbisCommentTag;
import utils.Helper;
import utils.SortTableRows;

/**
 *
 * @author mgkon
 */
public class FileOperations {

    private List<MusicFileTags> musicFilesTags;

    public FileOperations() {
        this.musicFilesTags = new LinkedList<>();
    }

    public void addMusicFiles(MusicFileTags musicFileTags) {

        musicFilesTags.add(musicFileTags);
    }

    /**
     *
     * @return
     */
    public List<MusicFileTags> getMusicFilesTags() {

        return Collections.unmodifiableList(musicFilesTags);
    }

    public void saveProjectToFile(File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        MusicFileTags[] fileTags = musicFilesTags.toArray(new MusicFileTags[musicFilesTags.size()]);

        oos.writeObject(fileTags);

        oos.close();
    }

    public void loadProjectFromFile(File file) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(file);
        var ois = new ObjectInputStream(fis);

        MusicFileTags[] fileTags = (MusicFileTags[]) ois.readObject();
        musicFilesTags.clear();
        musicFilesTags.addAll(Arrays.asList(fileTags));
        ois.close();
    }

    public void filterMusicFiles(String filterKeyWord) {
        String filter = filterKeyWord.toLowerCase();

        if ("".equals(filter)) {
            musicFilesTags.forEach(x -> x.setIsVisible(true));
        } else {
            musicFilesTags.forEach(x -> x.setIsVisible(false));
            musicFilesTags.stream().filter(x -> x.getTitle().toLowerCase().contains(filter)
                    || x.getAlbumArtist().toLowerCase().contains(filter)
                    || x.getGenre().toLowerCase().contains(filter)
            ).forEach(x -> x.setIsVisible(true));
        }
        Collections.sort(musicFilesTags, new SortTableRows());
    }

    public void reloadMusicFiles() throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
        ArrayList<File> musicFiles = new ArrayList<>();

        musicFilesTags.forEach((musicFile) -> {
            musicFiles.add(new File(musicFile.getFileLocation()));
        });

        musicFilesTags.clear();
        File[] files = new File[musicFiles.size()];
        musicFiles.toArray(files);

        loadMusicFiles(files);
    }

    public void loadMusicFiles(File[] files) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
        LogManager.getLogManager().reset();
        AudioFile audioFile;
        MusicFileTags fileTags;

        for (File file : files) {
            audioFile = AudioFileIO.read(file);

            Tag fileTag = audioFile.getTag();
            if (fileTag != null) {
                fileTags = new MusicFileTags();

                fileTags.setFileName(file.getName());
                fileTags.setAlbumArtist(fileTag.getValue(FieldKey.ARTIST, 0));
                String genreValue = fileTag.getValue(FieldKey.GENRE, 0).replace(")", "").replace("(", "").trim();

                if (genreValue != null && !"".equals(genreValue.trim())) {
                    StringBuilder genre = new StringBuilder();

                    String[] genreValues = genreValue.split(",");
                    for (String genreItem : genreValues) {
                        genreItem = genreItem.trim();
                        Integer genreId = Helper.tryParseInt(genreItem);
                        String genreName;
                        if (genreId != null) {
                            genreName = GenreTypes.getInstanceOf().getValueForId(genreId);
                        } else {
                            genreName = genreItem;
                            genreId = GenreTypes.getInstanceOf().getIdForValue(genreItem);
                            if (genreId == null) {
                                genreName = "Other";
                            }
                        }
                        if (genre.length() > 0) {
                            genre.append(", ");
                        }
                        genre.append(genreName);
                    }
                    fileTags.setGenre(genre.toString());
                }

                fileTags.setFileLocation(file.getCanonicalPath());
                fileTags.setIsVisible(true);
                musicFilesTags.add(fileTags);
            }
        }
        Collections.sort(this.musicFilesTags, new SortTableRows());
    }

    public void removeMusicFiles(int[] rows) {
        int rowCount = 0;
        List<Integer> rowsToBeDeleted = Helper.arrayToList(rows);
        Iterator<MusicFileTags> collection = musicFilesTags.iterator();
        while (collection.hasNext()) {
            collection.next();
            if (rowsToBeDeleted.contains(rowCount)) {
                collection.remove();
            }

            rowCount++;
        }
    }

    public void saveMusicFiles() throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException, CannotWriteException {
        //FileTypeEnum fileType;
        //MusicFileTypeFactory fileTypeFactory;

        for (MusicFileTags fileTags : musicFilesTags) {
            String fileExtension = Helper.getFileExtension(fileTags.getFileLocation());
            //fileType = fileExtension.equalsIgnoreCase("mp3") ? FileTypeEnum.FLAC : FileTypeEnum.MP3;
            //fileType = FileTypeEnum.valueOf(fileExtension.toUpperCase());

            //fileTypeFactory = new MusicFileTypeFactory(fileType);
            //MusicFile musicFile = fileTypeFactory.getMusicFileType();
            //musicFile.editMusicFile(fileTags);
            if ("mp3".equalsIgnoreCase(fileExtension)) {
                editMp3File(fileTags);
            } else if ("flac".equalsIgnoreCase(fileExtension)) {
                editFlacFile(fileTags);
            }
        }
    }

    public void editMp3File(MusicFileTags fileTags) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException, CannotWriteException {

        String fileLocation = fileTags.getFileLocation();
        File file = new File(fileLocation);
        MP3File mp3File = (MP3File) AudioFileIO.read(file);
        Tag tag = mp3File.getTagAndConvertOrCreateAndSetDefault();
        if (fileTags.getArtist() != null) {
            tag.setField(FieldKey.ARTIST, fileTags.getArtist());
        }
        mp3File.commit();
    }

    public void editFlacFile(MusicFileTags fileTags) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException, CannotWriteException {
        AudioFile audioFile = AudioFileIO.read(new File(fileTags.getFileLocation()));
        FlacTag tag = (FlacTag) audioFile.getTag();
        VorbisCommentTag vorbisTag = tag.getVorbisCommentTag();

        if (fileTags.getAlbumArtist() != null) {
            vorbisTag.setField(FlacCommentKeysEnum.ALBUMARTIST.toString(), fileTags.getAlbumArtist());
        }
        if (fileTags.getGenre() != null) {
            vorbisTag.setField(FlacCommentKeysEnum.GENRE.toString(), fileTags.getGenre());
        }

        audioFile.commit();
    }
}
