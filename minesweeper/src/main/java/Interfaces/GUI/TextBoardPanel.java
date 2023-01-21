package Interfaces.GUI;

import Logic.enumerates.Placeable;
import Logic.modelGame.Cell;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class TextBoardPanel extends JPanel {

    public TextBoardPanel(int dimension) {
        dimension++;
        setBoardText(dimension);
        setSize(1000,1000);
        setVisible(true);
    }

    public void setBoardText(int dimension) {
        JPanel boardPanel = new JPanel(new GridLayout(dimension, dimension));
        boardPanel.add(new JLabel(" "));
        for (int i = 0; i < dimension - 1; i++) {
            JLabel index = new JLabel(String.valueOf(i));
            boardPanel.add(index);
        }
        for (int i = 0; i < dimension - 1; i++) {
            JLabel verticalIndex = new JLabel(String.valueOf(i));
            boardPanel.add(verticalIndex);
            for (int j = 0; j < dimension - 1; j++) {
                JLabel square = new JLabel(getStatusCell(i,j).getName());
                boardPanel.add(square);
            }
        }
        add(boardPanel);
    }

    private void actualiceBoard(){

    }

    private Placeable getStatusCell(int row, int colum){
        return main.getACell(row,colum).getPlaceable();
    }

    @Override
    public void setVisible(boolean aFlag){
        super.setVisible(aFlag);
    }
}
