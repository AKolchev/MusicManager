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
import org.jaudiotagger.tag.id3.AbstractID3v2Frame;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.AbstractTagFrame;
import org.jaudiotagger.tag.id3.ID3v22Tag;
import org.jaudiotagger.tag.id3.ID3v24FieldKey;
import org.jaudiotagger.tag.id3.ID3v24Frames;
import org.jaudiotagger.tag.id3.ID3v24Tag;
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
        //MP3File audioFile;
        MusicFileTags fileTags;

        for (File file : files) {
            audioFile = AudioFileIO.read(file);
            //audioFile = (MP3File) AudioFileIO.read(file);

            Tag fileTag = audioFile.getTag();
            if (fileTag != null) {
                fileTags = new MusicFileTags();

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
                fileTags.setComment(fileTag.getValue(FieldKey.COMMENT, 0));
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
        for (MusicFileTags fileTags : musicFilesTags) {
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

    public void editMp3File(MusicFileTags fileTags) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException, CannotWriteException {

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
        if (fileTags.getComment() != null) {
            tag.setField(FieldKey.COMMENT, fileTags.getComment());
        }

        mp3File.commit();
        mp3File.save();
    }

    public void editFlacFile(MusicFileTags fileTags) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException, CannotWriteException {
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

        if (fileTags.getComment() != null) {
            vorbisTag.setField(FlacCommentKeysEnum.COMMENT.toString(), fileTags.getComment());
        }

        audioFile.commit();
    }
}
