/*
 * Application entry point
 */
package main;

import javax.swing.SwingUtilities;
import views.MainFrame;

/**
 * @author AKolchev
 */
public class App {

    /**
     * @param args initial input arguments
     */
    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame();
            }
        });
    }
}