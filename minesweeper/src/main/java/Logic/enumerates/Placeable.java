package Logic.enumerates;

/**
 * Enumerate cell that contain all forms of a cell
 */
public enum Placeable {

    /**
     * A flag
     */
    Flag("Flag", "\uD83D\uDEA9"),

    /**
     * A mine
     */
    Mine("Mine", "💣"),

    /**
     * A blank or unplayed cell
     */
    Blank("Blank", "\u2B1B"),

    /**
     * A played cell
     */
    Played("Play", "\u2B1C"),

    /**
     * A explode bomb
     */
    Explode("Explode","💥");

    /**
     * Attribute name of the Placeable
     */
    private String name;

    /**
     * Attribute icon of the Placeable
     */
    private String icon;

    /**
     * Constructor
     *
     * @param name of the Placeable
     * @param icon of the Placeable
     */
    private Placeable(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }

    /**
     * Method that returns the name of the Placeable
     *
     * @return name of Placeable
     */
    public String getName() {
        return this.name;
    }

    /**
     * Method that returns the icon of the Placeable
     *
     * @return icon of Placeable
     */
    public String getIcon() {
        return this.icon;
    }

    @Override
    public String toString() {
        return "Placeables{" +
                "name='" + name + '\'' +
                '}';
    }
}
