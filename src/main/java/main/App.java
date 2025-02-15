/*
 * Application entry point
 */
package main;

import javax.swing.SwingUtilities;
import views.MainFrameView;

/**
 * @author AKolchev
 */
public class App {

    /**
     * Application entry point
     * 
     * @param args initial input arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrameView();
            }
        });
    }
}