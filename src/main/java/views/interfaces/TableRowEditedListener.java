/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.interfaces;

import events.MusicFileEditEventData;

/**
 *
 * @author mgkon
 */
public interface TableRowEditedListener {
    public void tableRowEdited(MusicFileEditEventData event);
}
