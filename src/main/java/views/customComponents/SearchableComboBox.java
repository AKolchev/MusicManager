/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.customComponents;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import models.MusicGenre;

/**
 *
 * @author mgkon
 */
public class SearchableComboBox extends JComboBox {

    private List<MusicGenre> genres;

    public List<MusicGenre> getGenres() {
        return genres;
    }

    public SearchableComboBox(List<MusicGenre> genres) {
        super(genres.toArray());
        this.setEditable(true);

        this.genres = genres;

        final JTextField textField = (JTextField) this.getEditor().getEditorComponent();

        textField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        /**
                         * On key press filter the list. If there is "text"
                         * entered in text field of this combo use that "text"
                         * for filtering.
                         */
                        comboFilter(textField.getText());
                    }
                });
            }
        });
    }
    
    /**
     * Build a list of entries that match the given filter.
     */
    public void comboFilter(String enteredText)
    {
        List<MusicGenre> genresFiltered = new ArrayList<MusicGenre>();

        for (MusicGenre genreItem : getGenres())
        {
            if (genreItem.genreName.toLowerCase().contains(enteredText.toLowerCase()))
            {
                genresFiltered.add(genreItem);
            }
        }

        if (genresFiltered.size() > 0)
        {
            this.setModel(new DefaultComboBoxModel(genresFiltered.toArray()));
            this.setSelectedItem(enteredText);
            this.showPopup();
        }
        else
        {
            this.hidePopup();
        }
    }
}
