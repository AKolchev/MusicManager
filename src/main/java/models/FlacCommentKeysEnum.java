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
public enum FlacCommentKeysEnum {
    ALBUM("ALBUM"),
    ARTIST("ARTIST"),
    ALBUMARTIST("ALBUMARTIST"),
    TITLE("TITLE"),
    YEAR("YEAR"),
    COMMENT("COMMENT"),
    GENRE("GENRE");
    
    private final String tagKey;
    private FlacCommentKeysEnum(String tagName){
       this.tagKey = tagName; 
    }
    
    @Override
    public String toString(){
        return this.tagKey;
    }
}
