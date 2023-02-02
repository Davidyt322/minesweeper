package Interfaces.GUI;

import Logic.enumerates.Placeable;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;


public class TextBoardPanel extends JPanel {

    List<List<JLabel>> board = new LinkedList<>();

    public TextBoardPanel(int dimension) {
        dimension++;
        setBoardText(dimension);
        setBounds(0,0,1000,1000);
        setSize(1000,1000);
        setVisible(true);
    }

    public void setBoardText(int dimension) {
        JPanel boardPanel = new JPanel(new GridLayout(dimension, dimension));
        boardPanel.add(new JLabel(" "));
        for (int i = 0; i < dimension - 1; i++) {
            JLabel index = new JLabel(String.valueOf(i));
            boardPanel.add(index);
            board.add(i, new LinkedList<>());
        }
        for (int i = 0; i < dimension - 1; i++) {
            JLabel verticalIndex = new JLabel(String.valueOf(i));
            boardPanel.add(verticalIndex);
            for (int j = 0; j < dimension - 1; j++) {
                JLabel square = new JLabel(getStatusCell(i,j).getName());
                board.get(i).add(j, square);
                boardPanel.add(square);
            }
        }
        add(boardPanel);
    }

    private Placeable getStatusCell(int row, int colum){
        return main.getACell(row,colum).getPlaceable();
    }

    @Override
    public void setVisible(boolean aFlag){
        super.setVisible(aFlag);
        actualiceBoard();
    }

    private void actualiceBoard(){
        for(int i =0; i<main.getboardDimension();i++){
            for(int j=0;j<main.getboardDimension();j++){
                board.get(i).get(j).setText(getStatusCell(i,j).getName());
            }
        }
    }
}
