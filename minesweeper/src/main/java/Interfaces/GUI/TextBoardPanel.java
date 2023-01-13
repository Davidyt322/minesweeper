package Interfaces.GUI;

import Logic.modelGame.Cell;

import javax.swing.*;
import java.awt.*;

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
                JLabel square = new JLabel(" si " + i);
                boardPanel.add(square);
            }
        }
        add(boardPanel);
    }

    public static void main(String[] args) {
        new TextBoardPanel(10);
    }
}
