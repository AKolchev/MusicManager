package models;

/**
 * Enumeration containing the flac comment names
 *
 * @author AKolchev, f55283
 */
public enum FlacCommentKeysEnum {

    /**
     * Album comment name
     */
    ALBUM("ALBUM"),
    /**
     * Track artist comment name
     */
    ARTIST("ARTIST"),
    /**
     * Album artist comment name
     */
    ALBUMARTIST("ALBUMARTIST"),
    /**
     * Track title comment name
     */
    TITLE("TITLE"),
    /**
     * Track date comment name
     */
    YEAR("DATE"),
    /**
     * Track genre
     */
    GENRE("GENRE");

    private final String tagKey;

    /**
     * Sets the comment key as a value to the enumeration
     *
     * @param tagName The name of the key for the comment
     */
    private FlacCommentKeysEnum(String tagName) {
        this.tagKey = tagName;
    }

    /**
     * Override of toString method.
     * 
     * @return The comment key
     */
    @Override
    public String toString() {
        return this.tagKey;
    }
}
