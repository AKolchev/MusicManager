/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author AKolchev, f55283 TODO - make singleton
 */
public class Helper {

    public static String getFileExtension(String name) {
        int pointIndex = name.lastIndexOf(".");

        if (pointIndex == -1) {
            return null;
        }

        if (pointIndex == name.length() - 1) {
            return null;
        }

        return name.substring(pointIndex + 1, name.length());
    }

    public static Integer tryParseInt(String inputString) {
        try {
            return Integer.parseInt(inputString);
        } catch (NumberFormatException ex) {
            return null;
        }
    }
    
    public static List<Integer> arrayToList(int[] intArray){
        List<Integer> newArray = new ArrayList<>();
        for(int item:intArray){
            newArray.add(item);
        }
        
        return newArray;
    }
}
