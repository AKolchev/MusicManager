/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fileAccessLayer;

import events.MusicFileEditEventData;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.LogManager;
import models.MusicFileTag;
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
import views.utils.Helper;

/**
 *
 * @author mgkon
 */
public class FileOperations {

    private List<MusicFileTag> musicFilesTags;

    public FileOperations() {
        this.musicFilesTags = new LinkedList<>();
    }

    public void addMusicFiles(MusicFileTag musicFileTags) {

        musicFilesTags.add(musicFileTags);
    }

    /**
     *
     * @return
     */
    public List<MusicFileTag> getMusicFilesTags() {
        return Collections.unmodifiableList(musicFilesTags);
    }

    public void saveToFile(File file) throws IOException {

    }

    public void loadMusicFiles(File[] files) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
        LogManager.getLogManager().reset();
        AudioFile audioFile;
        MusicFileTag fileTags;

        for (File file : files) {
            audioFile = AudioFileIO.read(file);

            Tag fileTag = audioFile.getTag();
            if (fileTag != null) {
                fileTags = new MusicFileTag();

                fileTags.setName(file.getName());
                fileTags.setArtist(fileTag.getValue(FieldKey.ARTIST, 0));
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

                musicFilesTags.add(fileTags);
            }
        }
    }

    public void removeMusicFiles(int[] rows) {
        int rowCount = 0;
        List rowsToBeDeleted = Helper.arrayToList(rows);
        Iterator<MusicFileTag> collection = musicFilesTags.iterator();
        while (collection.hasNext()) {
            if (rowsToBeDeleted.contains(rowCount)) {
                collection.remove();
            }
            rowCount++;
        }
    }

    public void saveMusicTags(MusicFileEditEventData eventData) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException, CannotWriteException {
        String fileLocation = eventData.fileTagData.getFileLocation();

        LogManager.getLogManager().reset();

        MP3File audioFile = (MP3File) AudioFileIO.read(new File(fileLocation));//For MP3
        //AudioFile audioFile = AudioFileIO.read(new File(fileLocation));//For Flac

        Tag fileTag = audioFile.getTag();
        if (fileTag != null) {
            MusicFileTag fileTags = new MusicFileTag();

            if (eventData.fileTagData.getArtist() != null) {
                editMp3ArtistTags(audioFile, eventData.fileTagData.getArtist());
            }
            if (eventData.fileTagData.getGenre() != null) {
                editMp3GenreTags(audioFile, eventData.fileTagData.getGenre());
            }

            audioFile.setTag(fileTag);
            audioFile.commit();
        }
    }

    public void editFlacTags(File file) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException, CannotWriteException {

        AudioFile audioFile = AudioFileIO.read(file);
        FlacTag tag = (FlacTag) audioFile.getTag();
        VorbisCommentTag vorbisTag = tag.getVorbisCommentTag();

        vorbisTag.setField("ALBUM ARTIST", "Alex");

        audioFile.commit();
    }

    public void editMp3GenreTags(AudioFile file, String Value) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException, CannotWriteException {

        Tag tag = file.getTag();
        tag.deleteField(FieldKey.GENRE);
        tag.addField(tag.createField(FieldKey.GENRE, Value));

        file.commit();
    }

    public void editMp3ArtistTags(AudioFile file, String Value) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException, CannotWriteException {

        Tag tag = file.getTag();
        tag.deleteField(FieldKey.ARTIST);
        tag.addField(tag.createField(FieldKey.ARTIST, Value));

        file.commit();
    }
}
