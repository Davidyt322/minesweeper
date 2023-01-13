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
        JPanel buttons = new JPanel();
        boardPanel = new BoardFrame(main.getboardDimension());
        boardPanel.setVisible(true);
        boardTextPanel = new TextBoardPanel();
        boardTextPanel.setVisible(false);
        JButton gameButton = new JButton("Board");
        gameButton.setBackground(Color.LIGHT_GRAY);
        gameButton.setForeground(Color.white);
        gameButton.setSize(10,30);
        gameButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1){
                    boardPanel.setVisible(true);
                    boardTextPanel.setVisible(false);
                    repaint();
                    revalidate();
                    pack();
                }
            }
        });
        JButton textButton = new JButton("Text Board");
        textButton.setBackground(Color.LIGHT_GRAY);
        textButton.setForeground(Color.white);
        textButton.setSize(10,30);
        textButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1){
                    boardPanel.setVisible(false);
                    boardTextPanel.setVisible(true);
                    repaint();
                    revalidate();
                    pack();
                }
            }
        });
        buttons.add(gameButton , BorderLayout.EAST);
        buttons.add(textButton, BorderLayout.AFTER_LAST_LINE);
        buttons.setBackground(Color.LIGHT_GRAY);
        buttons.setSize(1000,20);
        boardTextPanel.setVisible(false);
        add(boardTextPanel, BorderLayout.CENTER);
        add(boardPanel, BorderLayout.CENTER);
        add(buttons, BorderLayout.NORTH);
        pack();
        setLocationRelativeTo(null);
    }
}
