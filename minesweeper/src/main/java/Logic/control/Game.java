package Logic.control;

import Logic.enumerates.Placeable;
import Logic.modelGame.*;

import java.util.Objects;

/**
 * A class rule and coordinate all the other objects in a game
 *
 * @author David Peñasco
 */
public class Game {

    /**
     * Attribute that save a board object
     */
    private Board board;

    /**
     * Attribute with the dimension of the board
     */
    private int dimension;

    /**
     * Attribute with the percentage of mines in board
     */
    private float mines;

    /**
     * Construct
     *
     * @param dimension      of the board
     * @param percentOfMines in the board
     */
    public Game(int dimension, float percentOfMines) {
        board = new Board(dimension, percentOfMines);
        this.dimension = dimension;
        this.mines = percentOfMines;
    }

    /**
     * Method to make a play in the game
     *
     * @param row   of the play
     * @param colum of the play
     * @param play  type (bomb or flag)
     */
    public void makePlay(int row, int colum, String play) {
        if (board.getACell(row, colum).getPlaceable() == Placeable.Blank &&
                play.equalsIgnoreCase(Placeable.Played.getName())) {
            board.getACell(row, colum).setPlaceable(Placeable.Played);
        } else if (board.getACell(row, colum).getPlaceable() == Placeable.Blank &&
                play.equalsIgnoreCase(Placeable.Flag.getName())) {
            board.setFlag(row, colum);
        }
    }

    /**
     * Method to verify if a play is legal
     *
     * @param row   of the play
     * @param colum of the play
     * @return true if it is legal or false if it is ilegal
     */
    public boolean validatePlay(int row, int colum, String play) {
        if (board.getACell(row, colum).getPlaceable() == Placeable.Played ||
                board.getACell(row, colum).getPlaceable() == Placeable.Mine ||
                play.equalsIgnoreCase("flag")) {
            if (play.equalsIgnoreCase("flag")) {
                return true;
            } else if (play.equalsIgnoreCase("play")) {
                return false;
            }
        }
        return true;
    }

    /**
     * method that return the board
     *
     * @return this.board
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * Method to generate the mines in the board
     */
    public void generateMines() {
        board.generateMines(dimension, mines);
    }

    /**
     * Method that actualice the near mines attribute of all cells
     */
    public void lookNearMines() {
        for (int i = 0; i < getBoard().getNum_Rows(); i++) {
            for (int j = 0; j < board.getNum_Colums(); j++) {
                int mines = 0;
                int si = -1;
                for (int k = -1; k < 2; k++) {
                    for (int l = -1; l < 2; l++) {
                        if (getBoard().cellInBoard(i + k, j + l) &&
                                getBoard().getACell(i + k, j + l).getBomb() == Placeable.Mine) {
                            mines++;
                        }
                    }
                }
                getBoard().getACell(i, j).setNearMines(mines);
            }
        }
    }

    /**
     * Method to check if the game has finish
     * @return false if the game has not finished or true in otherwise
     */
    public boolean checkFinishedGame() {
        for (int i = 0; i < board.getNum_Rows(); i++) {
            for (int j = 0; j < board.getNum_Colums(); j++) {
                if (board.getACell(i, j).getNearMines() == 0 && board.getACell(i, j).getPlaceable() != Placeable.Played) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Mehtod to check if th player explode a bomb
     * @return true if it has exploded or false if not
     */
    public boolean checkExplotedBomb(){
        for(int i=0;i< getBoard().getNum_Rows();i++){
            for(int j=0; j< getBoard().getNum_Colums();j++){
                if(board.getACell(i,j).getBomb() == Placeable.Mine &&
                        board.getACell(i,j).getPlaceable() == Placeable.Played){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return dimension == game.dimension && Float.compare(game.mines, mines) == 0 && Objects.equals(board, game.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, dimension, mines);
    }

    @Override
    public String toString() {
        return "Game{" +
                "board=" + board +
                ", dimension=" + dimension +
                ", mines=" + mines +
                '}';
    }
}