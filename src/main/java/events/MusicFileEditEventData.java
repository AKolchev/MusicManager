/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package events;

import java.util.EventObject;
import models.MusicFileTag;

/**
 *
 * @author mgkon
 */
public class MusicFileEditEventData extends EventObject {

    public MusicFileTag fileTagData;

    public MusicFileEditEventData(Object source) {
        super(source);
    }

    public MusicFileEditEventData(Object source, MusicFileTag fileTagData) {
        super(source);
        this.fileTagData = fileTagData;
    }
}
