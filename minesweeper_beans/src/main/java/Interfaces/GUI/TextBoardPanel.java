package Interfaces.GUI;

import Logic.modelGame.Cell;

import javax.swing.*;
import java.awt.*;

public class TextBoardPanel extends JPanel {

    public TextBoardPanel() {
        JPanel board = new JPanel();
        board.setBackground(Color.lightGray);
        board.setBackground(Color.white);
        board.setLayout(new GridLayout(11, 11, 10, 10));
        setBoardText(board);
        board.setPreferredSize(new Dimension(1000,1000));
        setPreferredSize(new Dimension(1000,1000));
        add(board, BorderLayout.CENTER);
    }

    public void setBoardText(JPanel board) {
        for (int number = 0; number < main.getboardDimension(); number++) {
            board.add(new JLabel(String.valueOf(number)));
        }
        for (int i = 0; i < main.getboardDimension(); i++) {
            for (int j = 0; j < main.getboardDimension(); j++) {
                Cell cell = main.getACell(i, j);

            }
        }
    }
}
