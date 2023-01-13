package Interfaces.textui;

import java.util.Scanner;

import Logic.control.Game;
import Logic.enumerates.Placeable;

/**
 * Class to execute the Text interface
 */
public class TextInterface {

    /**
     * Attribute game
     */
    private static Game game;

    /**
     * Attribute scanner
     */
    private static Scanner scanner = new Scanner(System.in);

    /**
     * To select debug or normal mode
     */
    private static String mode;

    /**
     * Main method
     *
     * @param args string
     */
    public static void main(String[] args) {
        String play = null;
        initializeGame();
        do {
            showBoard(mode);
            play = collectPlay();
            play(play);
        } while (!finishedGame());
        showBoard(mode);
        if(game.checkExplotedBomb()){
            System.out.println("Bomb explodes and you die");
        } else if(game.checkFinishedGame()){
            System.out.println("you win");
        }
    }


    /**
     * Method to initialize a game
     */
    public static void initializeGame() {
        int dimension = 0;
        float mines = 0.0f;
        String difficulty = null;
        //selection of mode
        do {
            System.out.print("Debug mode or Normal mode: ");
            mode = scanner.next();
            if (!checkModeSelection(mode)) {
                System.out.println("difficulty selected wrong, try again");
            }
        } while (!checkModeSelection(mode));
        //selection of difficulty
        do {
            System.out.print("Specify difficulty level between this ones => Easy / Medium / Hard / Custom : ");
            difficulty = scanner.next();
            if (difficulty.equalsIgnoreCase("Easy")) {
                dimension = 10;
                mines = 0.2f;
            } else if (difficulty.equalsIgnoreCase("Medium")) {
                dimension = 18;
                mines = 0.2f;
            } else if (difficulty.equalsIgnoreCase("Hard")) {
                dimension = 24;
                mines = 0.3f;
            } else if (difficulty.equalsIgnoreCase("custom")){
                System.out.println("specify the dimension of the board and the % of mines");
                System.out.print("dimension of board (Ej: 20): ");
                dimension = scanner.nextInt();
                System.out.print("% of mines (Ej: 0,4): ");
                mines = scanner.nextFloat();
            } else {
                System.out.println("difficulty selected wrong, try again");
            }
        } while (!checkDifficultySelection(difficulty));
        //inicialice of game
        game = new Game(dimension, mines);
        showBoard(mode);
        firstPlay(collectPlay());
        game.lookNearMines();
        game.getBoard().expandPlay();
    }

    /**
     * Private method to check if the mode is right selected
     *
     * @param mode selected
     * @return true if it's right false if not
     */
    private static boolean checkModeSelection(String mode) {
        return mode.equalsIgnoreCase("debug") || mode.equalsIgnoreCase("normal");
    }

    /**
     * Private method to check if the difficulty is right selected
     *
     * @param difficulty selected
     * @return true if it's right false if not
     */
    private static boolean checkDifficultySelection(String difficulty) {
        return difficulty.equalsIgnoreCase("easy") || difficulty.equalsIgnoreCase("medium") ||
                difficulty.equalsIgnoreCase("hard") || difficulty.equalsIgnoreCase("custom");
    }

    /**
     * Play save as Play-row-colum example play-0-2
     *
     * @return String
     */

    public static String collectPlay() {
        String play = null;
        int row, colum;
        boolean check = true;
        String columS = null, rowS = null;
        do {
            System.out.print("Make a (write Flag to set one of it or play): ");
            play = scanner.next();
            check = validatePlay(play.toLowerCase());
        } while (!check);
        check = true;
        do {
            System.out.print("Row to play in: ");
            row = scanner.nextInt();
            if (row <= 9) {
                rowS = "0" + row;
            } else {
                rowS = String.valueOf(row);
            }
            System.out.print("Colum to play in: ");
            colum = scanner.nextInt();
            check = !validateCoords(row, colum) ? false : check;
            if (colum <= 9) {
                columS = "0" + colum;
            } else {
                columS = String.valueOf(colum);
            }
        } while (!check);
        return play + "-" + rowS + "-" + columS;
    }

    /**
     * Method to get concrete information of a play
     * part = 0  => type of play
     * part = 1  => row
     * part = 2  => colum
     *
     * @param play to decrypt
     * @param part of the play to decrypt
     */
    private static String decryptPlay(String play, int part) {
        StringBuilder type = new StringBuilder(), row = new StringBuilder(), colum = new StringBuilder();
        char[] Arrayplay = play.toCharArray();
        String result = null;
        int dashes = 0;
        for (int i = 0; i < Arrayplay.length; i++) {
            if (Arrayplay[i] == '-') {
                dashes++;
            } else if (dashes == part && part == 0) {
                type.append(Arrayplay[i]);
                result = type.toString();
            } else if (dashes == part && part == 1) {
                row.append(Arrayplay[i]);
                result = row.toString();
            } else if (dashes == part && part == 2) {
                colum.append(Arrayplay[i]);
                result = colum.toString();
            }
        }
        return result;
    }

    /**
     * Method to make the first play of the game
     *
     * @param play
     */
    public static void firstPlay(String play) {
        int row = Integer.parseInt(decryptPlay(play, 1));
        int colum = Integer.parseInt(decryptPlay(play, 2));
        game.getBoard().getACell(row, colum).setPlaceable(Placeable.Played);
        game.generateMines();
    }

    /**
     * Method to make a play in game
     */
    public static void play(String play) {
        String type = decryptPlay(play, 0);
        int row = Integer.parseInt(decryptPlay(play, 1));
        int colum = Integer.parseInt(decryptPlay(play, 2));
        game.makePlay(row, colum, type.toString());
        game.getBoard().expandPlay();
    }

    public static boolean validatePlay(String play) {
        if (play.contains("play") || play.contains("flag")) {
            return true;
        } else {
            showPlayFormatError(play);
            return false;
        }
    }

    public static boolean validateCoords(int row, int colum) {
        if (!(row >= 0 && row <= game.getBoard().getNum_Rows())) {
            showCoordsError("row", row);
            return false;
        } else if (!(colum >= 0 && colum <= game.getBoard().getNum_Colums())) {
            showCoordsError("colum", colum);
            return false;
        }
        return true;
    }

    /**
     * Print the error format in the introduced play
     */
    public static void showPlayFormatError(String play) {
        System.out.println("The inserted play should be play or flag");
        StringBuilder error = new StringBuilder();
        char[] arrayPlay = play.toCharArray();
        boolean check = false;
        for (int i = 0; i < play.length(); i++) {
            char character = arrayPlay[i];
            if (character != '-' && !check) {
                error.append(arrayPlay[i]);
            } else if (character == '-') {
                check = true;
            }
        }
        System.out.println("introduced play: " + error);
    }

    private static void showCoordsError(String direction, int coord) {
        System.out.println("The introduced " + direction + ": " + coord + " is not in the board");
    }

    private static boolean finishedGame() {
        return game.checkFinishedGame() || game.checkExplotedBomb();
    }

    /**
     * Method to print in screen the actual board
     *
     * @param mode of the game
     */
    public static void showBoard(String mode) {
        if (mode.equalsIgnoreCase("debug")) {
            System.out.print(game.getBoard().toTextDebug());
        } else if (mode.equalsIgnoreCase("normal")) {
            System.out.print(game.getBoard().toText());
        }
    }
}
