import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.Dimension;

public class Player extends Character implements Movable{

    private Pair position = new Pair();
    private Pair velocity = new Pair();
    private Image image;
    private int fuelCapacity;
    private int spaceshipLevel;

    public Player(double initX, double initY){

        super();
        position.x = initX;
        position.y = initY;
        velocity.x = 0;
        velocity.y = 0;
        image = new ImageIcon("spaceship.png").getImage();
        image = image.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        this.fuelCapacity = 10;
        this.spaceshipLevel = 1; 
    }

    public int getFuel(){
        return fuelCapacity;
    }

    public void updateFuel(boolean isOnPlanet, int fuelDrain){
        this.fuelCapacity -= fuelDrain;
    }

    public int getSpaceshipLevel(){
        return spaceshipLevel;
    }

    public void upgradeSpaceship(){
        spaceshipLevel++;
        this.fuelCapacity += 5;
    }

    public double getX(){
        return position.x;
    }

    public double getY(){
        return position.y;
    }
    
    public void draw(Graphics g){
        //method to draw the player

        
        g.setColor(Color.GREEN);
        
    
        g.drawImage(image, (int)position.x, (int)position.y, null);
        g.drawRect((int)position.x-125, (int)position.y-125, 300, 300);
        g.drawRect((int)position.x-125, (int)position.y-125, 300, 300);

    }

    public void drawFuelCapacity(Graphics g){
        g.setColor(Color.RED);
        g.fillRoundRect((int)position.x - 800, (int)position.y- 500, fuelCapacity*10, 31, 15, 15);
        g.setColor(Color.GREEN);
        g.fillRoundRect((int)position.x - 800, (int)position.y- 500, fuelCapacity*10, 31, 15, 15);
        g.setColor(Color.white);
        g.drawString("Fuel", (int)position.x - 790, (int)position.y- 450);
        
    }

    public void move(int dir){
        switch (dir){
            case 0:
                System.out.println("steve's moving");
                velocity.y=-100;
                break;
            case 1:
                velocity.x=-100;
                break;
            case 2:
                velocity.y=100;
                break;
            case 3:
                velocity.x=100;
                
        }
    }

        public void stopMove(int dir){
        switch (dir){
            case 0:
                System.out.println("steve's moving");
                velocity.y=0;
                break;
            case 1:
                velocity.x=0;
                break;
            case 2:
                velocity.y=0;
                break;
            case 3:
                velocity.x=0;    
        }
    }

    public void update(Game g, double time){
        
        position = position.add(velocity.times(time));
        System.out.println(position.x + " " + position.y);
    }


    // public void checkBoundary(Game g){
    //     //method to check if the player is going to exit the map

    //     //if goes out of x boundary
    //     switch (position.x){
    //         case g.getWidth():
                
    //             break;
    //         case 0:

                
    //     }

    //     //if gores out of y boundary 
    //     switch (position.y){
    //         case g.getHeight():

    //             break;
    //         case 0:
                

    //     }
    // }
   

   

}