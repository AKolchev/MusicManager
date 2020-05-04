/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fileAccessLayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import models.MusicFileTags;

/**
 *
 * @author mgkon
 */
public class FileOperations {

    private List<MusicFileTags> musicFilesTags;

    public FileOperations() {
        this.musicFilesTags = new LinkedList<>();
    }

    public void addPerson(MusicFileTags musicFileTags) {
        musicFilesTags.add(musicFileTags);
    }

    /**
     *
     * @return
     */
    public List<MusicFileTags> getMusicFilesTags() {
        return Collections.unmodifiableList(musicFilesTags);//Makes the collection unmodifiable, because otherwise the private collection is exposed by ref.
    }

    public void saveToFile(File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        MusicFileTags[] persons = musicFilesTags.toArray(new MusicFileTags[musicFilesTags.size()]);

        oos.writeObject(persons);

        oos.close();
    }

    public void loadFromFile(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        var ois = new ObjectInputStream(fis);

        try {
            MusicFileTags[] persons = (MusicFileTags[]) ois.readObject();
            musicFilesTags.clear();
            musicFilesTags.addAll(Arrays.asList(persons));
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        ois.close();
    }

    public void removePerson(int row) {
        musicFilesTags.remove(row);
    }
}
