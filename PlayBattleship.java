
/**
 * Write a description of class BattleShipGame here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import javax.swing.*;

public class PlayBattleship
{
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Battleship");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new BattleshipPanel());
        frame.pack();
        frame.setVisible(true);
    }
}
