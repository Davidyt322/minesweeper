package Interfaces.GUI;

import org.netbeans.lib.awtextra.AbsoluteLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameFrame extends javax.swing.JFrame{

    BoardFrame boardPanel;

    TextBoardPanel boardTextPanel;

    public GameFrame(){
        initialiceComponentes();
    }

    private void initialiceComponentes(){
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        //panel with the main board
        JPanel buttons = new JPanel();

        //set the board
        boardPanel = new BoardFrame(main.getboardDimension());
        boardPanel.setVisible(true);

        //set a text board panel
        boardTextPanel = new TextBoardPanel(main.getboardDimension());
        boardTextPanel.setVisible(false);

        //create the button for main board
        JButton gameButton = new JButton("Board");
        gameButton.setBackground(Color.LIGHT_GRAY);
        gameButton.setForeground(Color.white);
        gameButton.setSize(10,30);

        //a listener for the button
        gameButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1){
                    boardPanel.setVisible(true);
                    boardTextPanel.setVisible(false);
                    boardPanel.repaint();
                    boardPanel.revalidate();
                    pack();
                }
            }
        });

        //create the button for the text board
        JButton textButton = new JButton("Text Board");
        textButton.setBackground(Color.LIGHT_GRAY);
        textButton.setForeground(Color.white);
        textButton.setSize(10,30);

        //a listener for the button
        textButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1){
                    boardPanel.setVisible(false);
                    boardTextPanel.setVisible(true);
                    boardTextPanel.repaint();
                    boardTextPanel.revalidate();
                    pack();
                }
            }
        });

        //we add the buttons to the buttons panel
        buttons.add(gameButton , BorderLayout.EAST);
        buttons.add(textButton, BorderLayout.AFTER_LAST_LINE);
        buttons.setBackground(Color.LIGHT_GRAY);
        buttons.setSize(1000,20);
        //we add the both panels in the center Default textboard visible: false and main board visible : true
        add(boardTextPanel, BorderLayout.AFTER_LAST_LINE);
        add(boardPanel);

        //we add the panel with the buttons
        add(buttons, BorderLayout.NORTH);

        //we pack all and set in the center
        pack();
        setLocationRelativeTo(null);
    }
}
