package eventListeners;

/**
 *
 * @author AKolchev, f55283 Interface representing the event of one or more rows
 * being deleted from the table
 */
public interface TableRowDeletedListener {

    /**
     *
     * @param rows The row positions of the records, which have been deleted
     */
    public void rowDeleted(int[] rows);
}
