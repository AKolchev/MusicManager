/*
 * Main frame of the UI
 * Wires together all views and serves as a coordinator/controller of the presentation logic
 */
package views;

import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 * @author AKolchev
 */
public class MainFrame extends JFrame {
    
    private TablePanel tablePanel;
    
    /**
     * MainFrame constructor
     * Initializes all necessary components
     */
    public MainFrame() {
        super("Music Manager");
        this.tablePanel = new TablePanel();
        
        setLookAndFeel();
    }
    
    /**
     * Sets Nimbus UI as application theme
     */
    private void setLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            //Nimbus has not been found. Default theme will be used.
        }
    }
}
