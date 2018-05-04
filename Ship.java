
/**
 * Write a description of class Ship here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.awt.*;
import javax.swing.*;
public class Ship
{
    private int x, y;
    private ShipType shipType;
    private int direction;
    private int length;
    
    public Ship(int x, int y, int direction, ShipType shipType)
    {
        this.x = x;
        this.y = y;
        this.shipType = shipType;
        this.direction = direction;
        
        switch(shipType)
        {
           case PATROL:
                length = 2;
                break;
            case SUB:
                length = 3;
                break;
            case DESTROYER:
                length = 3;
                break;
            case BATTLESHIP:
                length = 4;
                break;
            case CARRIER:
                length = 5;
                break;
        }
    }
    
    public void draw(Graphics g)
    {
        switch(shipType)
        {
            case PATROL:
                if(direction == 1)
                {
                    int[] xPoints = {x*50+12,x*50+75,x*50+95,
                                    x*50+75,x*50+12};
                    int[] yPoints = {y*50+4,y*50+4,y*50+25,
                                    y*50+46,y*50+46};
                    
                    g.setColor(Color.gray);
                    g.fillOval(x*50+2,y*50+4,20,42);
                    g.fillPolygon(xPoints, yPoints, 5);
                    
                    g.fill3DRect(x*50+25,y*50+15,40,20,true);
                }
                else if(direction == 2)
                {
                    int[] xPoints = {x*50+4,x*50+4,x*50+25,
                                    x*50+46,x*50+46};
                    int[] yPoints = {y*50+12,y*50+75,y*50+95,
                                    y*50+75,y*50+12};
                    
                    g.setColor(Color.gray);
                    g.fillOval(x*50+4,y*50+2,42,20);
                    g.fillPolygon(xPoints, yPoints, 5);
                    
                    g.fill3DRect(x*50+15,y*50+25,20,40,true);
                }
                break;
            case SUB:
                if(direction == 1)
                {
                    g.setColor(Color.gray);
                    g.fillOval(x*50+5,y*50+5,140,40);
                    g.setColor(Color.lightGray);
                    g.fillOval(x*50+25,y*50+15,60,20);
                }
                else if(direction == 2)
                {
                    g.setColor(Color.gray);
                    g.fillOval(x*50+5,y*50+5,40,140);
                    g.setColor(Color.lightGray);
                    g.fillOval(x*50+15,y*50+25,20,60);
                }
                break;
            case DESTROYER:
                if(direction == 1)
                {
                    g.setColor(Color.gray);
                    g.fillOval(x*50+5, y*50+3,44,44);
                    g.fillRect(x*50+24,y*50+3,60,44);
                    g.fillOval(x*50+40,y*50+3,100,44);
                    g.fill3DRect(x*50+20,y*50+10,35,30,true);
                    g.fill3DRect(x*50+75,y*50+15,40,20,true);
                    
                    g.setColor(Color.lightGray);
                    g.fillOval(x*50+23,y*50+15,20,20);
                    
                    g.setColor(Color.black);
                    g.drawLine(x*50+35,y*50+22,x*50+22,y*50+17);
                    g.drawLine(x*50+33,y*50+28,x*50+20,y*50+23);
                    
                    g.drawLine(x*50+95,y*50+23,x*50+130,y*50+26);
                    g.drawLine(x*50+95,y*50+27,x*50+130,y*50+30);
                }
                else if(direction == 2)
                {
                    g.setColor(Color.gray);
                    g.fillOval(x*50+3, y*50+5,44,44);
                    g.fillRect(x*50+3,y*50+24,44,60);
                    g.fillOval(x*50+3,y*50+40,44,100);
                    g.fill3DRect(x*50+10,y*50+20,30,35,true);
                    g.fill3DRect(x*50+15,y*50+75,20,40,true);
                    
                    g.setColor(Color.lightGray);
                    g.fillOval(x*50+15,y*50+23,20,20);
                    
                    g.setColor(Color.black);
                    g.drawLine(x*50+22,y*50+35,x*50+17,y*50+22);
                    g.drawLine(x*50+28,y*50+33,x*50+23,y*50+20);
                    
                    g.drawLine(x*50+23,y*50+95,x*50+26,y*50+130);
                    g.drawLine(x*50+27,y*50+95,x*50+30,y*50+130);
                }
                break;
            case BATTLESHIP:
                if(direction == 1)
                {
                    g.setColor(Color.gray);
                    g.fillOval(x*50+5, y*50+3,44,44);
                    g.fillRect(x*50+24,y*50+3,110,44);
                    g.fillOval(x*50+90,y*50+3,100,44);
                    g.fill3DRect(x*50+90,y*50+20,80,10,true);
                    g.fill3DRect(x*50+30,y*50+10,60,30,true);
                    
                    g.setColor(Color.lightGray);
                    g.drawLine(x*50+35,y*50+25,x*50+75,y*50+25);
                    g.drawLine(x*50+50,y*50+15,x*50+50,y*50+35);
                }
                else if(direction == 2)
                {
                    g.setColor(Color.gray);
                    g.fillOval(x*50+3,y*50+5,44,44);
                    g.fillRect(x*50+3,y*50+24,44,110);
                    g.fillOval(x*50+3,y*50+90,44,100);
                    g.fill3DRect(x*50+20,y*50+90,10,80,true);
                    g.fill3DRect(x*50+10,y*50+30,30,60,true);
                    
                    g.setColor(Color.lightGray);
                    g.drawLine(x*50+25,y*50+35,x*50+25,y*50+75);
                    g.drawLine(x*50+15,y*50+50,x*50+35,y*50+50);
                }
                break;
            case CARRIER:
                if(direction == 1)
                {
                    int[] xPoints = {x*50+140,x*50+240,
                                    x*50+240,x*50+155};
                    int[] yPoints = {y*50+5,y*50+25,
                                    y*50+41,y*50+45};
                    
                    g.setColor(Color.gray);
                    g.fillRect(x*50+5,y*50+5,150,40);
                    g.fillPolygon(xPoints,yPoints,4);
                    g.fill3DRect(x*50+32,y*50+15,55,20,true);
                    
                    g.setColor(Color.black);
                    g.drawLine(x*50+130,y*50+25,x*50+230,y*50+25);
                    g.drawLine(x*50+130,y*50+38,x*50+230,y*50+38);
                }
                else if(direction == 2)
                {
                    int[] xPoints = {x*50+5,x*50+25,
                                    x*50+41,x*50+45};
                    int[] yPoints = {y*50+140,y*50+240,
                                    y*50+240,y*50+155};
                    
                    g.setColor(Color.gray);
                    g.fillRect(x*50+5,y*50+5,40,150);
                    g.fillPolygon(xPoints,yPoints,4);
                    g.fill3DRect(x*50+15,y*50+32,20,55,true);
                    
                    g.setColor(Color.black);
                    g.drawLine(x*50+25,y*50+130,x*50+25,y*50+230);
                    g.drawLine(x*50+38,y*50+130,x*50+38,y*50+230);
                }
                break;
        }
    }
    
    public int getLength()
    {
        return length;
    }
    
    public ShipType getShipType()
    {
        return shipType;
    }
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }
    
    public int getDirection()
    {
        return direction;
    }
}
