/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Interfaces.GUI;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

import Logic.enumerates.Placeable;
import Logic.modelGame.Cell;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author David Peñasco
 */
public class BoardPanel extends javax.swing.JPanel {

    /**
     * Creates new form Board
     */
    static List<List<CellButton>> board = new LinkedList<>();

    public BoardPanel(int dimension) {
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(dimension, dimension));
        createBoard(boardPanel, dimension);
        add(boardPanel);
        setBounds(0, 0, 1000, 1000);
        setVisible(true);
        setPreferredSize(new Dimension(1000, 1000));
        boardPanel.setPreferredSize(new Dimension(1000, 1000));
    }

    public void createBoard(JPanel boardPanel, int dimension) {
        for (int i = 0; i < dimension; i++) {
            board.add(new ArrayList<>());
            for (int j = 0; j < dimension; j++) {
                CellButton button = new CellButton(i, j);
                board.get(i).add(button);
                if (dimension <= 20) {
                    button.setPreferredSize(new Dimension(50, 50));
                } else {
                    button.setPreferredSize(new Dimension(35, 35));
                }
                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            main.makeAPlay(button.getRow(), button.getColum());
                        } else if (e.getButton() == MouseEvent.BUTTON3) {
                            main.makeAFlag(button.getRow(), button.getColum());
                        }
                        actualiceButtons();
                    }
                });
                boardPanel.add(button);
            }
        }
    }

    private void actualiceButtons() {
        for (int i = 0; i < main.getboardDimension(); i++) {
            for (int j = 0; j < main.getboardDimension(); j++) {
                JButton button = board.get(i).get(j);
                Cell cell = main.getACell(i, j);
                Placeable status = main.getGame().getBoard().getACell(i, j).getPlaceable();
                switch (status) {
                    case Blank -> {
                        boolean done = false;
                        button.setBackground(Color.GRAY);
                        if(cell.getBomb() != null){
                            button.setText(" ");
                            done = true;
                        }
                        for (int k = -1; k < 2; k++) {
                            for (int l = -1; l < 2; l++) {
                                if (cell.getNearMines() > 0 && cellInBoard(i + k, j + l) &&
                                        main.getACell(i + k, j + l).getPlaceable() == Placeable.Played
                                        && !done) {
                                    button.setText(String.valueOf(cell.getNearMines()));
                                    button.setBackground(Color.white);
                                    done = true;
                                }
                            }
                        }
                        if (!done) {
                            button.setText(" ");
                        }
                    }
                    case Played -> button.setBackground(Color.white);
                    case Flag -> {
                        button.setBackground(Color.GRAY);
                        button.setText(Placeable.Flag.getIcon());
                    }
                }
            }
        }
    }

    @Deprecated
    private Cell getACell(int row, int colum) {
        return main.getACell(row, colum);
    }

    private boolean cellInBoard(int row, int colum) {
        return main.getGame().getBoard().cellInBoard(row, colum);
    }
}
