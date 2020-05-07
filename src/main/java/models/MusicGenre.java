/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author mgkon
 */
public class MusicGenre {

    
    @Override
    public String toString(){
        return genreName;
    }
    
    public MusicGenre(){}
    public MusicGenre(int genreId, String genreName){
        this.genreId = genreId;
        this.genreName = genreName;
    }
    
    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int GenreId) {
        this.genreId = GenreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String Genre) {
        this.genreName = Genre;
    }
    public int genreId;
    public String genreName;    
}
