/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces.GUI;

import Logic.control.Game;
import Logic.enumerates.Placeable;
import Logic.modelGame.*;

/**
 *
 * @author David Peñasco
 */
public class main {

    static Game game;

    static Object lock;
    
    static boolean generatedMines = false;

    public static void main(String[] args) throws InterruptedException {

        lock = new Object();

        mainMenu menu = new mainMenu();
        menu.setLocationRelativeTo(null);
        menu.setVisible(true);
        try {
            synchronized (lock) {
                lock.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        GameFrame gameGUI = new GameFrame();
        gameGUI.setVisible(true);

    }

    static void selectDificulty(int dimension, float mines) {
        game = new Game(dimension, mines);
    }

    static void mainLock() {
        lock.notify();
    }
    
    static void makeAPlay(int row, int colum){
        game.makePlay(row, colum, Placeable.Played.getName());
        generateMines();
        game.getBoard().expandPlay();
        boolean si = true;
    }
    
    static void makeAFlag(int row, int colum){
        if(game.getBoard().getACell(row, colum).getPlaceable() == Placeable.Flag){
            game.getBoard().getACell(row, colum).setPlaceable(Placeable.Blank);
        } else {
            game.makePlay(row, colum, Placeable.Flag.getName());
        }
    }
    
    static Game getGame(){
        return game;
    }
    
    static void generateMines(){
        if(generatedMines==false){
            game.generateMines();
            game.lookNearMines();
        }
        generatedMines = true;
    }
    
    static int nearMines(int row, int colum){
        return game.getBoard().getACell(row, colum).getNearMines();
    }
    
    static int getboardDimension(){
        return game.getBoard().getNum_Rows();
    }
    
    static Cell getACell(int row, int colum){
        return game.getBoard().getACell(row, colum);
    }
    
    static boolean cellInBoard(int row, int colum){
        return game.getBoard().cellInBoard(row, colum);
    }
}
