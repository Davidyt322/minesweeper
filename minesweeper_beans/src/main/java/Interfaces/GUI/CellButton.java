/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces.GUI;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;

/**
 *
 * @author David Peñasco
 */
public class CellButton extends javax.swing.JButton{
    
    final private Color bakcground = Color.GRAY;
            
    final private Color foreground = Color.black;
    
    private int row;
    
    private int colum;
    
    public CellButton(int row, int colum){
        super();
        this.row = row;
        this.colum = colum;
        setBackground(bakcground);
        setForeground(foreground);
        setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    }
    
    public int getRow(){
        return row;
    }
    
    public int getColum(){
        return colum;
    }
    
    
}
