/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fileAccessLayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import models.MusicFileTags;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author mgkon
 */
public class FileOperations {

    private LinkedHashMap<String, MusicFileTags> musicFilesTags;

    public FileOperations() {
        this.musicFilesTags = new LinkedHashMap<>();
    }

    public void addMusicFiles(MusicFileTags musicFileTags, String fileName) {

        musicFilesTags.put(fileName, musicFileTags);
    }

    /**
     *
     * @return
     */
    public LinkedHashMap<String, MusicFileTags> getMusicFilesTags() {
        return musicFilesTags;//Makes the collection unmodifiable, because otherwise the private collection is exposed by ref.
    }

    public void saveToFile(File file) throws IOException {

    }

    public void loadMusicFiles(File[] files) throws IOException {
        
        ContentHandler handler = new DefaultHandler();
        Metadata metadata = new Metadata();
        Parser parser = new Mp3Parser();
        ParseContext parseCtx = new ParseContext();
        InputStream input;

        for (File file : files) {
            try {
                input = new FileInputStream(file);
                parser.parse(input, handler, metadata, parseCtx);
                input.close();
                // metadata.set("xmpDM:artist", "Joe Satriani, Steve Vai");
                // System.out.println("Title: " + metadata.get("title"));
                // System.out.println("Artists: " + metadata.get("xmpDM:artist"));
                String[] metadataNames = metadata.names();

                for (String name : metadataNames) {
                    System.out.println(name + ": " + metadata.get(name));
                }
                MusicFileTags fileTags = new MusicFileTags();
                fileTags.setName(file.getName());
                fileTags.setArtist(metadata.get("xmpDM:artist"));
                fileTags.setGenre(metadata.get("xmpDM:genre"));

                musicFilesTags.put(file.getCanonicalPath(), fileTags);
            } catch (IOException | SAXException | TikaException e) {
            }
        }
    }

    public void removeMusicFile(int row) {
        musicFilesTags.remove(row);
    }
}
