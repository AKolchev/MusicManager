/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.tableModels;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import models.MusicFileTags;

/**
 *
 * @author mgkon
 */
public class MusicFileTagsTableModel extends AbstractTableModel {
    private List<MusicFileTags> fo;
    private String[] colNames = {"Id", "Name", "Artist", "Genre"};
    
    public MusicFileTagsTableModel(){

    }
    
    @Override
    public String getColumnName(int column){
        return colNames[column];
    }
    
    @Override
    public boolean isCellEditable(int row, int col){
       return col>0;
    }
    
    @Override
    public void setValueAt(Object value, int row, int col){
        if(fo!=null){
            MusicFileTags fileTags = fo.get(row);
            switch(col){
                case 1:
                    fileTags.setName((String)value);
                    break;
                case 2:
                    fileTags.setArtist((String)value);
                    break;
                case 3:
                    try{
                        //person.setAgeCategory(AgeCategoryEnum.valueOf((String)value));
                        //person.setAgeCategory((AgeCategoryEnum)value);
                        fileTags.setGenre((String)value);
                    }
                    catch(Exception ex){
                        
                    }
                    break;
                default:
                    break;
            }
        }
    }
    
    public void setData(List<MusicFileTags> data){
        this.fo = data;
    }
    
    @Override
    public int getRowCount() {
        return fo.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Class<?> getColumnClass(int col){
        switch(col){
            case 0:
                return Integer.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return String.class;
            default:
                return null;
        }
    }
    
    @Override
    public Object getValueAt(int row, int column) {
        
        MusicFileTags musicFileTags = fo.get(row);
        
        switch(column){
            case 0:
                return musicFileTags.getId();
            case 1:
                return musicFileTags.getName();
            case 2:
                return musicFileTags.getArtist();
            case 3:
                return musicFileTags.getGenre();
        }
        
        return null;
    }
}
