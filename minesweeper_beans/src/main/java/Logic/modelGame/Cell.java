package Logic.modelGame;

import Logic.enumerates.Placeable;

import java.util.Objects;

/**
 * Class to storage and edit a cell's info
 */
public class Cell {

    /**
     * Attribute with the row of the cell
     */
    private int row;

    /**
     * Attribute with the colum of the cell
     */
    private int colum;

    /**
     * Attribute to storage the type of cell
     */
    private Placeable placeable;

    /**
     * Attribute to storage the bomb
     */
    private Placeable bomb;

    /**
     * Attribute with the number of near mines
     */
    private int nearMines;

    /**
     * Construct
     * @param row of the cell
     * @param colum of the cell
     */
    public Cell(int row, int colum) {
        this.row = row;
        this.colum = colum;
    }

    /**
     * Method to return placeable of the cell
     * @return placeable
     */
    public Placeable getPlaceable() {
        return placeable;
    }

    /**
     * Method to set a placeable in the cell according to the type argument
     * @param type of the placeable
     */
    public void setPlaceable(Placeable type) {
        if (type.equals(Placeable.Flag)) {
            placeable = Placeable.Flag;
        } else if(type.equals(Placeable.Blank)){
            placeable = Placeable.Blank;
        } else if(type.equals(Placeable.Played)){
            placeable = Placeable.Played;
        }
        if (type.equals(Placeable.Mine)) {
            bomb = Placeable.Mine;
        }
    }

    /**
     * method to set the number of near mines
     * @param x mines near
     */
    public void setNearMines(int x){
        this.nearMines=x;
    }

    /**
     * Method that return the number of near mines
     * @return nearMines
     */
    public int getNearMines(){
        return nearMines;
    }

    /**
     * Method that return if there is a bomb or not
     * @return bomb
     */
    public Placeable getBomb() {
        return bomb;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "row=" + row + ", colum=" + colum +
                " bomb=" + bomb +
                ", nearMines=" + nearMines +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return row == cell.row && colum == cell.colum && nearMines == 
                cell.nearMines && placeable == cell.placeable && bomb == cell.bomb;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, colum, placeable, 
                bomb, nearMines);
    }
}
