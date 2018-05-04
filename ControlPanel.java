
/**
 * Write a description of class ControlPanel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.text.NumberFormat;
public class ControlPanel extends JPanel
{
    private JButton newGame, patrolBoat, destroyer, sub, battleShip, carrier, horizontal, vertical, start;
    private JLabel shipSelect, directionSelect, placeShip, statusMessage;
    private JLabel totalHit, totalFired, accuracy;
    private JPanel containerPanel;
    private ShipType selectedShip;
    private int direction, length, shipsDeployed, shotsHit, shotsFired;
    private String shotPercent;
    private GridPanel player, computer;
    
    static final int NEWGAME = 0;
    static final int INBATTLE = 1;
    
    public ControlPanel()
    {
        setPreferredSize(new Dimension(160,500));
        setBackground(Color.cyan);
        
        containerPanel = new JPanel();
        containerPanel.setBackground(Color.cyan);
        
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
        
        statusMessage = new JLabel(" ");
        
        newGame = new JButton("New Game ");
        patrolBoat = new JButton("Patrol Boat");
        destroyer = new JButton(" Destroyer ");
        sub = new JButton("Submarine");
        battleShip = new JButton(" Battle Ship");
        carrier = new JButton("    Carrier    ");
        horizontal = new JButton(" Horizontal ");
        vertical = new JButton("   Vertical   ");
        start = new JButton("Start Battle");
        
        ButtonListener listener = new ButtonListener();
        newGame.addActionListener(listener);
        patrolBoat.addActionListener(listener);
        destroyer.addActionListener(listener);
        sub.addActionListener(listener);
        battleShip.addActionListener(listener);
        carrier.addActionListener(listener);
        horizontal.addActionListener(listener);
        vertical.addActionListener(listener);
        start.addActionListener(listener);
        
        start.setEnabled(false);
        patrolBoat.setEnabled(false);
        destroyer.setEnabled(false);
        sub.setEnabled(false);
        battleShip.setEnabled(false);
        carrier.setEnabled(false);
        horizontal.setEnabled(false);
        vertical.setEnabled(false);
        
        shipSelect = new JLabel("1. Select a ship");
        directionSelect = new JLabel("2. Select a ship direction");
        placeShip = new JLabel("3. Click ship position");
        
        shotsHit = 0;
        shotsFired = 0;
        shotPercent = new String("");
        totalHit = new JLabel("Total shots hit: " +shotsHit);
        totalFired = new JLabel("Total shots fired: " +shotsFired);
        accuracy = new JLabel("Accuracy: " +shotPercent);
        
        containerPanel.add(Box.createRigidArea(new Dimension(0,4)));
        containerPanel.add(newGame);
        containerPanel.add(Box.createRigidArea(new Dimension(0,4)));
        containerPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        containerPanel.add(Box.createRigidArea(new Dimension(0,4)));
        containerPanel.add(shipSelect);
        containerPanel.add(Box.createRigidArea(new Dimension(0,4)));
        containerPanel.add(patrolBoat);
        containerPanel.add(destroyer);
        containerPanel.add(sub);
        containerPanel.add(battleShip);
        containerPanel.add(carrier);
        containerPanel.add(Box.createRigidArea(new Dimension(0,4)));
        containerPanel.add(directionSelect);
        containerPanel.add(Box.createRigidArea(new Dimension(0,4)));
        containerPanel.add(horizontal);
        containerPanel.add(vertical);
        containerPanel.add(Box.createRigidArea(new Dimension(0,4)));
        containerPanel.add(placeShip);
        containerPanel.add(Box.createRigidArea(new Dimension(0,4)));
        containerPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        containerPanel.add(Box.createRigidArea(new Dimension(0,4)));
        containerPanel.add(start);
        containerPanel.add(statusMessage);
        containerPanel.add(Box.createRigidArea(new Dimension(0,4)));
        containerPanel.add(totalFired);
        containerPanel.add(totalHit);
        containerPanel.add(accuracy);
        
        add(containerPanel);
        
        selectedShip = ShipType.PATROL;
        direction = 1;
        length = 2;
        shipsDeployed = 0;
    }
    
    private class ButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            if(event.getSource() == patrolBoat)
            {
                selectedShip = ShipType.PATROL;
                length = 2;
            }
            else if(event.getSource() == sub)
            {
                selectedShip = ShipType.SUB;
                length = 3;
            }
            else if(event.getSource() == carrier)
            {
                selectedShip = ShipType.CARRIER;
                length = 5;
            }
            else if(event.getSource() == destroyer)
            {
                selectedShip = ShipType.DESTROYER;
                length = 3;
            }
            else if(event.getSource() == battleShip)
            {
                selectedShip = ShipType.BATTLESHIP;
                length = 4;
            }
            else if(event.getSource() == horizontal)
                direction = 1;
            else if(event.getSource() == vertical)
                direction = 2;
            else if(event.getSource() == start)
            {
                computer.autoShipDeploy();
                start.setEnabled(false);
                patrolBoat.setEnabled(false);
                destroyer.setEnabled(false);
                sub.setEnabled(false);
                battleShip.setEnabled(false);
                carrier.setEnabled(false);
                horizontal.setEnabled(false);
                vertical.setEnabled(false);
                
                player.setStateOfGame(INBATTLE);
                computer.setStateOfGame(INBATTLE);
            }
            else if(event.getSource() == newGame)
            {
                start.setEnabled(false);
                patrolBoat.setEnabled(true);
                destroyer.setEnabled(true);
                sub.setEnabled(true);
                battleShip.setEnabled(true);
                carrier.setEnabled(true);
                horizontal.setEnabled(true);
                vertical.setEnabled(true);
                
                shotsHit = 0;
                shotsFired = 0;
                shotPercent = "";
                totalHit.setText("Total shots hit: " +shotsHit);
                totalFired.setText("Total shots fired: " +shotsFired);
                accuracy.setText("Accuracy: " +shotPercent);
                statusMessage.setText("New Game");
                statusMessage.setForeground(Color.black);
                
                player.setStateOfGame(NEWGAME);
                computer.setStateOfGame(NEWGAME);
                
                player.startNewGame();
                computer.startNewGame();
            }
        }
    }
    
    public ShipType getSelectedShip()
    {
        return selectedShip;
    }
    
    public int getDirection()
    {
        return direction;
    }
    
    public int getLength()
    {
        return length;
    }
    
    public void setOutOfRange()
    {
        statusMessage.setText("Ship is out of bounds.");
        statusMessage.setForeground(Color.red);
    }
    
    public void setOverlap()
    {
        statusMessage.setText("Ships are overlapping.");
        statusMessage.setForeground(Color.red);
    }
    
    public void shipDeployed(int numberShips)
    {
        if(numberShips == 5)
            start.setEnabled(true);
        
        statusMessage.setText("Number of ships deployed: " + numberShips);
        statusMessage.setForeground(Color.black);
    }
    
    public void updateShots(int shotsHit, int shotsFired)
    {
        this.shotsHit = shotsHit;
        this.shotsFired = shotsFired;
        NumberFormat percentFormat = NumberFormat.getPercentInstance();
        percentFormat.setMaximumFractionDigits(1);
        shotPercent = percentFormat.format((double)shotsHit / (double)shotsFired);
        
        totalHit.setText("Total shots hit: " +shotsHit);
        totalFired.setText("Total shots fired: " +shotsFired);
        accuracy.setText("Accuracy: " +shotPercent);
    }
    
    public void setPlayer(GridPanel player)
    {
        this.player = player;
    }
    
    public void setComputer(GridPanel computer)
    {
        this.computer = computer;
    }
}
