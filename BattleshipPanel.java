
/**
 * Write a description of class BattleshipPanel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.awt.*;
import javax.swing.*;
public class BattleshipPanel extends JPanel
{
    private GridPanel enemyGrid, playerGrid;
    private JPanel divider;
    private ControlPanel control;
    
    public BattleshipPanel()
    {
        control = new ControlPanel();
        playerGrid = new GridPanel(control, "human");
        divider = new JPanel();
        enemyGrid = new GridPanel(control, "computer");
        
        divider.setBackground(Color.cyan);
        divider.setPreferredSize(new Dimension(25, 500));
        
        control.setPlayer(playerGrid);
        control.setComputer(enemyGrid);
        
        playerGrid.setOpponent(enemyGrid);
        enemyGrid.setOpponent(playerGrid);
        
        add(control);
        add(playerGrid);
        add(divider);
        add(enemyGrid);
    }
}
