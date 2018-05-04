
/**
 * Write a description of class PlayerGridPanel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
public class GridPanel extends JPanel
{
    private ArrayList<Ship> ships;
    private ControlPanel controlPanel;
    private int[][] shipMap;
    private int[][] hitMap;
    private int[][] opponentMap;
    private String name;
    private final int OCCUPIED = 1;
    private final int EMPTY = 0;
    private final int HIT = 2;
    private final int MISS = 3;
    private final int MAXHITS = 17;
    private final int NONE = -1;
    private int stateOfGame;
    private int totalHits, totalShots;
    private GridPanel opponent;
    private boolean isLastHit;
    private int lastHitLocation;
    private boolean isShipSunk;
    
    public GridPanel(ControlPanel control, String name)
    {
        setPreferredSize(new Dimension(501,501));
        setBackground(Color.cyan);
        controlPanel = control;
        this.name = name;
        
        totalHits = 0;
        totalShots = 0;
        
        isLastHit = false;
        lastHitLocation = NONE;
        
        isShipSunk = false;
        
        hitMap = new int[10][10];
        shipMap = new int[10][10];
        opponentMap = new int[10][10];
        for(int x = 0; x < 10; x++)
        {
            for(int y = 0; y < 10; y++)
            {
                shipMap[x][y] = EMPTY;
                hitMap[x][y] = EMPTY;
                opponentMap[x][y] = EMPTY;
            }
        }
        
        ships = new ArrayList<Ship>();
        
        addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                processMouseClick(e);
                repaint();
            }
        });
    }
    
    public void paintComponent(Graphics page)
    {
        drawGridCells(page);
        drawGridLines(page);
       
        for(Ship aShip : ships)
        {
            aShip.draw(page);
        }
        
        for(int x = 0; x < 10; x++)
        {
            for(int y = 0; y < 10; y++)
            {
                if(hitMap[x][y] == MISS)
                {
                    page.setColor(Color.blue);
                    page.fillRect(x*50,y*50,50,50);
                }
                else if(hitMap[x][y] == HIT)
                {
                    page.setColor(Color.red);
                    page.fillRect(x*50,y*50,50,50);
                }
            }
        }
    }
    
    private void drawGridCells(Graphics g)
    {
        Color color = Color.cyan;
        g.setColor(color);
        
        for(int x = 0; x < 10; x++)
        {
            for(int y = 0; y < 10; y++)
            {
                g.fillRect(x*50,y*50,50,50);
            }
        }
    }
    
    private void drawGridLines(Graphics g)
    {
        Color color = Color.black;
        g.setColor(color);
        
        for(int x = 0; x < 11; x++)
        {
            g.drawLine(x*50,0,x*50,500);
        }
        
        for(int y = 0; y < 11; y++)
        {
            g.drawLine(0,y*50,500,y*50);
        }
    }
    
    private void processMouseClick(MouseEvent e)
    {
        int x = e.getX();
        int y = e.getY();
        x = x/50;
        y = y/50;
        if(stateOfGame == ControlPanel.NEWGAME)
        {
            if(name.equals("computer"))
                return;
            else
            {
                int direction = controlPanel.getDirection();
                int length = controlPanel.getLength();
                ShipType selectedShip = controlPanel.getSelectedShip();
                
                //check for out of bounds
                if(outOfRange(x,y,direction,controlPanel.getLength()))
                {
                    controlPanel.setOutOfRange();
                    return;
                }
                
                //check for overlap
                if(overlap(x,y,length,direction))
                {
                    controlPanel.setOverlap();
                    return;
                }
            
                
                //check if ship has been placed already
                for(int i = 0; i < ships.size(); i++)
                {
                    int dir = ships.get(i).getDirection();
                    int len = ships.get(i).getDirection();
                    int xCoord = ships.get(i).getX();
                    int yCoord = ships.get(i).getY();
                    
                    if(ships.get(i).getShipType() == selectedShip)
                    {
                        if(direction == 1)
                        {
                            for(int index = 0; index < length; index++)
                            {
                                shipMap[xCoord+index][yCoord] = EMPTY;
                            }
                        }
                        else
                        {
                            for(int index = 0; index < length; index++)
                            {
                                shipMap[xCoord][yCoord+index] = EMPTY;
                            }
                        }
                        
                        ships.remove(i);
                    }
                }
                
                
                //if all conditions are satisfied, then ship can be drawn and recorded
                Ship ship = new Ship(x,y,direction,controlPanel.getSelectedShip());
                ships.add(ship);
                controlPanel.shipDeployed(ships.size());
                
                for(int i = 0; i < controlPanel.getLength(); i++)
                {
                    if(direction == 1)
                        shipMap[x+i][y] = OCCUPIED;
                    else if(direction == 2)
                        shipMap[x][y+i] = OCCUPIED;
                }
            }
        }
        else if(stateOfGame == ControlPanel.INBATTLE)
        {
            if(name.equals("human"))
                return;
            
            if(totalHits == MAXHITS)
                return;
            
            if(shipMap[x][y] == EMPTY)
            {
                if(hitMap[x][y] == MISS)
                        return;
                
                hitMap[x][y] = MISS;
            }
            else if(shipMap[x][y] == OCCUPIED)
            {
                if(hitMap[x][y] == HIT)
                    return;
                    
                hitMap[x][y] = HIT;
                totalHits++;
            }
            
            totalShots++;
            controlPanel.updateShots(totalHits, totalShots);
            
            autoAttack();
        }
    }
    
    public void setControlPanel(ControlPanel control)
    {
        controlPanel = control;
    }
    
    public void autoShipDeploy()
    {
        for(ShipType type : ShipType.values())
        {
            Random generator = new Random();
            boolean cont = true;
            
            while(cont)
            {
                int x = generator.nextInt(10);
                int y = generator.nextInt(10);
                int direction = generator.nextInt(2)+1;
                int length = type.getLength();
                
                if(outOfRange(x,y,direction,controlPanel.getLength()) == false
                    && overlap(x,y,length,direction) == false)
                {
                    Ship ship = new Ship(x,y,direction,type);
                    ships.add(ship);
                    
                    for(int i = 0; i < length; i++)
                    {
                        if(direction == 1)
                            shipMap[x+i][y] = OCCUPIED;
                        else if(direction == 2)
                            shipMap[x][y+i] = OCCUPIED;
                    }
                    
                    cont = false;
                }
            }
        }
        
        repaint();
    }
    
    public void autoAttack()
    {
        int coordinate = AIChooseTarget();
        int x = coordinate % 10;
        int y = coordinate / 10;
        
        isLastHit = opponent.locationAttacked(x,y);
        if(isLastHit)
        {
            if(opponent.isShipSunk(x,y))
                lastHitLocation = NONE;
            else
                lastHitLocation = coordinate;
            
            opponentMap[x][y] = HIT;
        }
        else
            opponentMap[x][y] = MISS;
    }
    
    public int AIChooseTarget()
    {
        Random generator = new Random();
        int x,y;
        int target = NONE;
        
        if(lastHitLocation != NONE)
        {
            x = lastHitLocation % 10;
            y = lastHitLocation / 10;
            int offset = 1;
            
            System.out.println("lastHitLocation=" + lastHitLocation + " x=" +x +" y=" +y);
            
            //Check if there is already a hit adjacent to lastHitLocaton
            if(opponentMap[x][y-1] == HIT && opponentMap[x][y+1] == EMPTY)
                target = lastHitLocation + 10;
            else if(opponentMap[x+1][y] == HIT && opponentMap[x-1][y] == EMPTY)
                target = lastHitLocation -1;
            else if(opponentMap[x][y+1] == HIT && opponentMap[x][y-1] == EMPTY)
                target = lastHitLocation -10;
            else if(opponentMap[x-1][y] == HIT && opponentMap[x+1][y] == EMPTY)
                target = lastHitLocation +1;
            else
            {
                //checks adjacent squares for a miss: to go opposite direction
                if(opponentMap[x][y-1] == MISS && opponentMap[x][y+1] == HIT)
                {
                    while(opponentMap[x][y+offset] == HIT)
                    {
                        offset++;
                    }
                    
                    if(opponentMap[x][y+offset] == EMPTY)
                        target = lastHitLocation + 10*offset;
                }
                
                else if(opponentMap[x+1][y] == MISS && opponentMap[x-1][y] == HIT)
                {
                    while(opponentMap[x-offset][y] == HIT)
                    {
                        offset++;
                    }
                    
                    if(opponentMap[x-offset][y] == EMPTY)
                        target = lastHitLocation - offset;
                }
                
                else if(opponentMap[x][y+1] == MISS && opponentMap[x][y-1] == HIT)
                {
                    while(opponentMap[x][y - offset] == HIT)
                    {
                        offset++;
                    }
                    
                    if(opponentMap[x][y - offset] == EMPTY)
                        target = lastHitLocation - 10*offset;
                }
                
                else if(opponentMap[x-1][y] == MISS && opponentMap[x+1][y+1] == HIT)
                {
                    while(opponentMap[x+offset][y] == HIT)
                    {
                        offset++;
                    }
                    
                    if(opponentMap[x+offset][y] == EMPTY)
                        target = lastHitLocation + offset;
                }
                
                else
                {
                    //Looks at squares adjacent to one previously hit
                    if(opponentMap[x][y-1] == EMPTY)
                        target = lastHitLocation - 10;
                    else if(opponentMap[x+1][y] == EMPTY)
                        target = lastHitLocation +1;
                    else if(opponentMap[x][y+1] == EMPTY)
                        target = lastHitLocation +10;
                    else if(opponentMap[x-1][y] == EMPTY)
                        target = lastHitLocation -1;
                }
            }
                
            x = target % 10;
            y = target / 10;
        }
        else
        {
            target = generator.nextInt(100);
            x = target % 10;
            y = target / 10;
            
            while(opponentMap[x][y] != EMPTY)
            {
                target = generator.nextInt(100);
                x = target % 10;
                y = target / 10;
            }
        }
        
        //opponentMap[x][y] = OCCUPIED;
        return target;
    }
    
    public boolean locationAttacked(int x, int y)
    {
        boolean isHit = false;
        
        if(shipMap[x][y] == EMPTY)
        {
            if(hitMap[x][y] == MISS)
                return isHit;
            
            hitMap[x][y] = MISS;
        }
        else if(shipMap[x][y] == OCCUPIED)
        {
            if(hitMap[x][y] == HIT)
                return isHit;
                
            hitMap[x][y] = HIT;
            isHit = true;
            //totalHits++;
        }
            
        repaint();
        
        return isHit;
    }
    
    public boolean outOfRange(int coordX, int coordY, int direction, int length)
    {
        boolean outOfRange = false;
        
        if(direction == 1)
        {
            if(coordX + length <= 10)
                outOfRange = false;
            else
                outOfRange = true;
        }
        else if(direction == 2)
        {
            if(coordY + length <= 10)
                outOfRange = false;
            else
                outOfRange = true;
        }
        
        return outOfRange;
    }
    
    public boolean overlap(int coordX, int coordY, int length, int direction)
    {
        boolean isOverlap = false;
        int count = 0;
        int[] spacesOccupied = new int[length];
        
        if(direction == 1)
        {
            for(int i = 0; i < length; i++)
            {
                spacesOccupied[i] = coordX+i;
            }
            
            for(int i = 0; i < length; i++)
            {
                if(shipMap[spacesOccupied[i]][coordY] == OCCUPIED)
                    count++;
            }
        }
        else
        {
            for(int i = 0; i < length; i++)
            {
                spacesOccupied[i] = coordY+i;
            }
            
            for(int i = 0; i < length; i++)
            {
                if(shipMap[coordX][spacesOccupied[i]] == OCCUPIED)
                    count++;
            }
        }
        
        if(count > 0)
            isOverlap = true;
        
        return isOverlap;
    }
    
    public void setStateOfGame(int state)
    {
        stateOfGame = state;
    }
    
    public void setOpponent(GridPanel opponent)
    {
        this.opponent = opponent;
    }
    
    public void startNewGame()
    {
        totalHits = 0;
        totalShots = 0;
        
        for(int x = 0; x < 10; x++)
        {
            for(int y = 0; y < 10; y++)
            {
                shipMap[x][y] = EMPTY;
                hitMap[x][y] = EMPTY;
                opponentMap[x][y] = EMPTY;
            }
        }
        
        ships.clear();
        
        repaint();
    }
    
    public boolean isShipSunk(int x, int y)
    {
        for(int i = 0; i < ships.size(); i++)
        {
            int xCoord = ships.get(i).getX();
            int yCoord = ships.get(i).getY();
            int direction = ships.get(i).getDirection();
            int length = ships.get(i).getLength();
            
            if(direction == 1 && yCoord == y)
            {
                if(x >= xCoord && x <= xCoord+length - 1)
                {
                    for(int j = 0; j<length; j++)
                    {
                        if(hitMap[xCoord+j][yCoord] != HIT)
                            return false;
                    }
                }
            }
            else if(direction == 2 && xCoord == x)
            {
                if(y >= yCoord && y <= yCoord+length - 1)
                {
                    for(int j = 0; j<length; j++)
                    {
                        if(hitMap[xCoord][yCoord+j] != HIT)
                            return false;
                    }
                }
            }
        }
        
        return true;
    }
}
