package eventListeners;

/**
 *
 * @author AKolchev, f55283 Interface representing the events fired, upon a
 * toolbar button click
 */
public interface ToolbarButtonsEventListener {

    /**
     * An event fired upon Save button click
     * Music files metadata to be saved into the respective files
     */
    public void saveMusicFilesEvent();

    /**
     * An event fired upon Reload button click
     * Music files metadata to be reloaded from the file system
     */
    public void reloadMusicFilesEvent();
    
    /**
     * An event fired upon Import button click
     * Music files metadata to be loaded from the file system
     */
    public void importMusicFilesEvent();
}
