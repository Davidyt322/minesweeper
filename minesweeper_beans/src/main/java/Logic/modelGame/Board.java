package Logic.modelGame;

import Logic.enumerates.Placeable;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

/**
 * A class that manage all the board
 *
 * @author David Peñasco
 */
public class Board {

    /**
     * Arraylist with all the cells
     */
    private Cell[][] cells;

    /**
     * Attribute with the number of rows
     */
    private int num_Rows;

    /**
     * Attribute with the number of colums
     */
    private int num_Colums;

    /**
     * Construct
     *
     * @param dimension of the board
     * @param mines     percent in board
     */
    public Board(int dimension, float mines) {
        this.num_Rows = dimension;
        this.num_Colums = dimension;
        cells = new Cell[num_Rows][num_Colums];
        for (int i = 0; i < num_Rows; i++) {
            for (int j = 0; j < num_Colums; j++) {
                cells[i][j] = new Cell(i, j);
                cells[i][j].setPlaceable(Placeable.Blank);
            }
        }
    }

    /**
     * Method to generate the mines in the board
     *
     * @param dimension of the board
     * @param mines     percent in the board
     */
    public void generateMines(int dimension, float mines) {
        int numberMines;
        Random random = new Random();
        numberMines = (int) (dimension * dimension * mines);
        do {
            for (int i = 0; i < num_Rows; i++) {
                for (int j = 0; j < num_Colums; j++) {
                    boolean check = true;
                    for (int k = -1; k <= 1; k++) {
                        for (int l = -1; l <= 1; l++) {
                            if (cellInBoard((i + k), (j + l))
                                    && getACell((i + k), (j + l)).getPlaceable() == Placeable.Played) {
                                check = false;
                            }
                        }
                    }
                    int numberR = random.nextInt(100);
                    if (numberR < 15 && numberMines > 0 && check) {
                        cells[i][j].setPlaceable(Placeable.Mine);
                        numberMines--;
                    }
                }
            }
        } while (numberMines > 0);
    }

    /**
     * Method to print the board
     *
     * @return board string to use in the interface
     */
    public String toText() {
        int jump = 0;
        StringBuilder board = new StringBuilder("\t");
        for (int number = 0; number < num_Colums; number++) {
            board.append(number).append("\t");
        }
        board.append("\n");
        for (int i = 0; i < num_Rows; i++) {
            board.append(i).append("\t");
            for (int j = 0; j < num_Colums; j++) {

                //without line breaks
                //flags

                if (this.cells[i][j].getPlaceable() == Placeable.Flag && jump != num_Colums - 1) {
                    board.append(Placeable.Flag.getIcon()).append("\t");
                    jump++;

                    //unplayed cells

                } else if (this.cells[i][j].getPlaceable() == Placeable.Blank && jump != num_Colums - 1) {
                    boolean writed = false;
                    if (this.cells[i][j].getNearMines() > 0) {
                        for (int k = 1; k > -2; k--) {
                            for (int l = 1; l > -2; l--) {
                                if (cellInBoard((i + k), (j + l)) && (k != 0 | l != 0) && !writed &&
                                        this.cells[(i + k)][(j + l)].getPlaceable() == Placeable.Played) {
                                    board.append(this.cells[i][j].getNearMines()).append("\t");
                                    writed = true;
                                }
                            }
                        }
                    }
                    if (!writed) {
                        board.append(Placeable.Blank.getIcon()).append("\t");
                    }
                    jump++;

                    //played cells

                } else if (this.cells[i][j].getPlaceable() == Placeable.Played && jump != num_Colums - 1) {
                    if (this.cells[i][j].getNearMines() != 0) {
                        board.append(this.cells[i][j].getNearMines()).append("\t");
                    } else if (this.cells[i][j].getBomb() == Placeable.Mine &&
                            this.cells[i][j].getPlaceable() == Placeable.Played) {
                        board.append(Placeable.Explode.getIcon()).append("\t");
                    } else {
                        board.append(Placeable.Played.getIcon()).append("\t");
                    }
                    jump++;

                    //with line breaks
                    //flags

                } else if (this.cells[i][j].getPlaceable() == Placeable.Flag && jump == num_Colums - 1) {
                    board.append("🚩\n");
                    jump = 0;

                    //unplayed cells

                } else if (this.cells[i][j].getPlaceable() == Placeable.Blank && jump == num_Colums - 1) {
                    int counter = 1;
                    if (this.cells[i][j].getNearMines() > 0) {
                        for (int k = 1; k > -2; k--) {
                            for (int l = 1; l > -2; l--) {
                                if (cellInBoard((i + k), (j + l)) && (k != 0 | l != 0) && counter == 1 &&
                                        this.cells[(i + k)][(j + l)].getPlaceable() == Placeable.Played) {
                                    board.append(this.cells[i][j].getNearMines()).append("\n");
                                    counter--;
                                }
                            }
                        }
                    }
                    if (counter == 1) {
                        board.append(Placeable.Blank.getIcon()).append("\n");
                    }
                    jump = 0;

                    //played cells

                } else if (this.cells[i][j].getPlaceable() == Placeable.Played && jump == num_Colums - 1) {
                    if (this.cells[i][j].getNearMines() != 0) {
                        board.append(this.cells[i][j].getNearMines()).append("\n");
                    } else if (this.cells[i][j].getBomb() == Placeable.Mine &&
                            this.cells[i][j].getPlaceable() == Placeable.Played) {
                        board.append(Placeable.Explode.getIcon()).append("\n");
                    } else {
                        board.append(Placeable.Played.getIcon()).append("\n");
                    }
                    jump = 0;
                }
            }
        }
        return board.toString();
    }

    /**
     * Method to print the board in debug mode
     *
     * @return board string in debuge mode
     */
    public String toTextDebug() {
        int jump = 0;
        StringBuilder board = new StringBuilder("\t");
        for (int number = 0; number < num_Colums; number++) {
            board.append(number).append("\t");
        }
        board.append("\n");
        for (int i = 0; i < num_Rows; i++) {
            board.append(i).append("\t");
            for (int j = 0; j < num_Colums; j++) {

                //Development tool = it print where is a bomb too
                //without line breaks
                //Mines

                if (this.cells[i][j].getBomb() == Placeable.Mine && jump != num_Colums - 1 &&
                        this.cells[i][j].getPlaceable() == Placeable.Blank) {
                    board.append(Placeable.Mine.getIcon()).append("\t");
                    jump++;

                    //Flags

                } else if (this.cells[i][j].getPlaceable() == Placeable.Flag && jump != num_Colums - 1) {
                    board.append("🚩\t");
                    jump++;

                    //Unplayed cells

                } else if (this.cells[i][j].getPlaceable() == Placeable.Blank && jump != num_Colums - 1) {
                    int counter = 1;
                    if (this.cells[i][j].getNearMines() > 0) {
                        for (int k = 1; k > -2; k--) {
                            for (int l = 1; l > -2; l--) {
                                if (cellInBoard((i + k), (j + l)) && (k != 0 | l != 0) && counter == 1 &&
                                        this.cells[(i + k)][(j + l)].getPlaceable() == Placeable.Played) {
                                    board.append(this.cells[i][j].getNearMines()).append("\t");
                                    counter--;
                                }
                            }
                        }
                    }
                    if (counter == 1) {
                        board.append(Placeable.Blank.getIcon()).append("\t");
                    }
                    jump++;

                    //PLayed cells

                } else if (this.cells[i][j].getPlaceable() == Placeable.Played && jump != num_Colums - 1) {
                    if (this.cells[i][j].getNearMines() != 0 && this.cells[i][j].getBomb() == null) {
                        board.append(this.cells[i][j].getNearMines()).append("\t");
                    } else if(this.cells[i][j].getBomb() == Placeable.Mine &&
                            this.cells[i][j].getPlaceable() == Placeable.Played){
                        board.append(Placeable.Explode.getIcon()).append("\t");
                    } else {
                        board.append(Placeable.Played.getIcon()).append("\t");
                    }
                    jump++;

                    //with line break
                    //Mines

                } else if (this.cells[i][j].getBomb() == Placeable.Mine && jump == num_Colums - 1 &&
                this.cells[i][j].getPlaceable() == Placeable.Blank) {
                    board.append(Placeable.Mine.getIcon()).append("\n");
                    jump = 0;

                    //Flags

                } else if (this.cells[i][j].getPlaceable() == Placeable.Flag && jump == num_Colums - 1) {
                    board.append("🚩\n");
                    jump = 0;

                    //Unplayed cells

                } else if (this.cells[i][j].getPlaceable() == Placeable.Blank && jump == num_Colums - 1) {
                    int counter = 1;
                    if (this.cells[i][j].getNearMines() > 0) {
                        for (int k = 1; k > -2; k--) {
                            for (int l = 1; l > -2; l--) {
                                if (cellInBoard((i + k), (j + l)) && (k != 0 | l != 0) && counter == 1 &&
                                        this.cells[(i + k)][(j + l)].getPlaceable() == Placeable.Played) {
                                    board.append(this.cells[i][j].getNearMines()).append("\n");
                                    counter--;
                                }
                            }
                        }
                    }
                    if (counter == 1) {
                        board.append(Placeable.Blank.getIcon()).append("\n");
                    }
                    jump = 0;

                    //Played cells

                } else if (this.cells[i][j].getPlaceable() == Placeable.Played && jump == num_Colums - 1) {
                    if (this.cells[i][j].getNearMines() != 0) {
                        board.append(this.cells[i][j].getNearMines()).append("\n");
                    } else if(this.cells[i][j].getBomb() == Placeable.Mine &&
                            this.cells[i][j].getPlaceable() == Placeable.Played){
                        board.append(Placeable.Explode.getIcon()).append("\t");
                    } else {
                        board.append(Placeable.Played.getIcon()).append("\n");
                    }
                    jump = 0;
                }
            }
        }
        return board.toString();
    }

    /**
     * Method that return the list of cells
     *
     * @return cell
     */
    public Cell[][] getCells() {
        return cells;
    }

    /**
     * Method that return a cell of the list
     *
     * @param row   of the cell
     * @param colum of the cell
     * @return cell
     */
    public Cell getACell(int row, int colum) {
        return cells[row][colum];
    }

    /**
     * Method that return the number of row in board
     *
     * @return rows in board
     */
    public int getNum_Rows() {
        return this.num_Rows;
    }

    /**
     * Method that return the number of colum in board
     *
     * @return colums in board
     */
    public int getNum_Colums() {
        return this.num_Colums;
    }

    /**
     * Method to set a flag in a cell
     *
     * @param row   of the cell
     * @param colum of the cell
     */
    public void setFlag(int row, int colum) {
        if (cells[row][colum].getPlaceable() == Placeable.Blank) {
            cells[row][colum].setPlaceable(Placeable.Flag);
        } else if (cells[row][colum].getPlaceable() == Placeable.Flag) {
            cells[row][colum].setPlaceable(Placeable.Blank);
        }
    }

    /**
     * Method to check if a cell is in board
     *
     * @param row   of the cell
     * @param colum of the cell
     * @return true if the cell is in board, false in otherwise
     */
    public boolean cellInBoard(int row, int colum) {
        return row < num_Rows && row >= 0 && colum < num_Colums && colum >= 0;
    }

    /**
     * Method to expand a play in the board
     */
    public void expandPlay() {
        int changes;
        do {
            changes = 0;
            for (int i = 0; i < getNum_Rows(); i++) {
                for (int j = 0; j < getNum_Colums(); j++) {
                    if (getACell(i, j).getPlaceable() == Placeable.Blank) {
                        for (int k = -1; k < 2; k++) {
                            try {
                                if (cellInBoard(i + k, j) &&
                                        getACell(k + i, j).getPlaceable() == Placeable.Played
                                        && getACell(i, j).getNearMines() == 0) {
                                    getACell(i, j).setPlaceable(Placeable.Played);
                                    changes++;
                                }
                            } catch (IndexOutOfBoundsException e) {
                                System.out.println(i + "-" + k + "---" + j + "---" + (k + i) + "-" + j);
                            }
                            for (int l = -1; l < 2; l++) {
                                try {
                                    if (cellInBoard(i, j + l) &&
                                            getACell(i, l + j).getPlaceable() == Placeable.Played
                                            && getACell(i, j).getNearMines() == 0) {
                                        getACell(i, j).setPlaceable(Placeable.Played);
                                        changes++;
                                    }
                                } catch (IndexOutOfBoundsException e) {
                                    System.out.println(i + "---" + j + "-" + l + "---" + i + "-" + (j + l));
                                }
                            }
                        }
                    }
                }
            }
        } while (changes != 0);
    }

    /**
     * To string for the array
     *
     * @return cells
     */
    private String cellsToString() {
        StringBuilder cell = new StringBuilder();
        for (int i = 0; i < num_Rows; i++) {
            for (int j = 0; j < num_Colums; j++) {
                if (cellInBoard(i, j)) {
                    cell.append(this.cells[i][j].toString());
                }
            }
        }
        return cell.toString();
    }

    @Override
    public String toString() {
        return "Board{" +
                "cells=" + cellsToString() +
                ", num_Rows=" + num_Rows +
                ", num_Colums=" + num_Colums +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return num_Rows == board.num_Rows && num_Colums == board.num_Colums && 
                Arrays.equals(cells, board.cells);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(num_Rows, num_Colums);
        result = 31 * result + Arrays.hashCode(cells);
        return result;
    }
}
