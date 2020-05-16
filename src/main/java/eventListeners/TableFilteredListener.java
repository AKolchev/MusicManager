package eventListeners;

/**
 * An interface, representing the event of applying a filter to the table
 * contents
 *
 * @author AKolchev, f55283
 */
public interface TableFilteredListener {

    /**
     * Method fired when a filtering string has been set for the table
     *
     * @param filter The string which has been set as a table content filter
     */
    public void tableFiltered(String filter);
}
