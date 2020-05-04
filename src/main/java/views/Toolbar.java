/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import views.interfaces.ToolbarEventListener;

/**
 *
 * @author mgkon
 */
public class Toolbar extends JToolBar implements ActionListener {

    private JButton refreshMusicFileTagsBtn;
    private JTextField searchFiled;
    
    private ToolbarEventListener eventListener;

    public Toolbar() {
        refreshMusicFileTagsBtn = new JButton();
        refreshMusicFileTagsBtn.setIcon(createIcon("images/refresh-icon.png"));
        refreshMusicFileTagsBtn.setToolTipText("Refresh music file tags");
        refreshMusicFileTagsBtn.addActionListener(this);
        
        add(refreshMusicFileTagsBtn);
        
        addSeparator();
        
        searchFiled = new JTextField();
        searchFiled.setText("Search");
        searchFiled.setToolTipText("Search");
        add(searchFiled);   
        
        /*If it implements JPanel
        setLayout(new FlowLayout(FlowLayout.RIGHT));
        add(addTracksBtn);
        add(clearTrackListBtn);

        Border innerBorder = BorderFactory.createTitledBorder("Toolbar");
        Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
         */
    }

    private ImageIcon createIcon(String fileName) {
        //URL url = getClass().getResource(path);
        URL url = getClass().getClassLoader().getResource(fileName);
        if (url == null) {
            System.err.println("Unable to load image: " + url);
        }

        ImageIcon icon = new ImageIcon(url, "Icon Image");

        return icon;
    }

    public void setToolbarListener(ToolbarEventListener listener) {
        this.eventListener = listener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        eventListener.refreshMusicFilesEvent();
    }
}
