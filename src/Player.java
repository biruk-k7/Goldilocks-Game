import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

public class Player extends Character implements Movable{

    private Pair position = new Pair();
    private Pair velocity = new Pair();
    private Image spaceship;
    private Image astronaut;
    private double fuelCapacity;
    private double currentFuel;
    private int spaceshipLevel;
    private Rectangle bounds;
    public ArrayList<Bullet> bullets = new ArrayList<Bullet>();

    private boolean isSpaceship = false;
 
    public Player(double initX, double initY){

        super();
        position.x = initX;
        position.y = initY;
        velocity.x = 0;
        velocity.y = 0;
        spaceship = new ImageIcon("./assets/player/spaceship.png").getImage();
        spaceship = spaceship.getScaledInstance(60, 60, Image.SCALE_DEFAULT);
        astronaut = new ImageIcon("./assets/player/astronaut.gif").getImage();
        astronaut=astronaut.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        this.fuelCapacity = 10;
        this.currentFuel = 10;
        this.spaceshipLevel = 1; 
        bounds = new Rectangle((int)position.x, (int)position.y, spaceship.getWidth(null), spaceship.getHeight(null));


    }

    public boolean getIsSpaceShip(){
        return isSpaceship;
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public double getFuel(){
        return currentFuel;
    }

    public void updateFuel(boolean isOnPlanet, int fuelDrain){
        this.currentFuel -= fuelDrain;
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
    
    public void draw(Graphics2D g){
        //method to draw the player

        g.setColor(Color.GREEN);

        if(isSpaceship){
            g.drawImage(spaceship, (int)position.x - 25, (int)position.y - 25, null);
            //g.drawRect((int)position.x-150, (int)position.y-150, 300, 300);
            //g.drawRect((int)position.x-150, (int)position.y-150, 300, 300);
        } 
        else {
            g.drawImage(astronaut, (int)position.x - 25, (int)position.y - 25, null);
            //g.drawRect((int)position.x-10, (int)position.y-10, 20, 20);
        }


       

    }


    public void drawFuelCapacity(Graphics g){
        g.setColor(Color.RED);
        g.fillRoundRect((int)position.x - Main.WIDTH/2+20, (int)position.y- Main.HEIGHT/2+20, (int)fuelCapacity*10, 31, 15, 15);
        g.setColor(Color.GREEN);
        g.fillRoundRect((int)position.x - Main.WIDTH/2+20, (int)position.y- Main.HEIGHT/2+20, (int)currentFuel*10, 31, 15, 15);
        g.setColor(Color.white);

        String fontFile = "GameFont.ttf";
        Font customFont = null;
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new java.io.File(fontFile)).deriveFont((float) 20);

        } catch (Exception e) {
            customFont = new Font("Arial", Font.PLAIN, 50); 
        }
        g.setFont(customFont);
        g.drawString("FUEL", (int)position.x - Main.WIDTH/2+20, (int)position.y-Main.HEIGHT/2+70);

        if(currentFuel <= 1){
            g.setColor(Color.RED);
            g.drawString("OUT OF FUEL", (int)position.x - 65, (int)position.y - 30);
        }
        
    }

    public void move(int dir){

            switch (dir){
                case 0:
                    //System.out.println("steve's moving");
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
                //System.out.println("steve's moving");
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
         
        if(isOnPlanet(g)){
            isSpaceship = false;
        }else{
            isSpaceship = true;
        }
        position = position.add(velocity.times(time));
        bounds = new Rectangle((int)position.x, (int)position.y, spaceship.getWidth(null), spaceship.getHeight(null));
        
        if(Math.pow(velocity.x, 2) + Math.pow(velocity.y,2) > 0){
            currentFuel -= 0.01;
        }

    }

    public boolean isOnPlanet(Game g){
        // for loop to check all planets
        // our own position
        // planet position + radius calculation
        double distance = 100000.0;
        Planet p = null;

        for (Planet s:g.planets){
            double testDist = distanceToPlanet(s.getCenter().x, s.getCenter().y);

            if (testDist < distance) {
                distance = testDist;
                p = s;
            }
        }
        System.out.println(distance);
        System.out.println(p.getBounds().getWidth());

        if (this.bounds.intersects(p.getBounds())){
            System.out.println("true");
            return true;
        }
        
        return false;
    }

    private double distanceToPlanet(double x2, double y2){

        return Math.sqrt(Math.pow(this.position.x - x2, 2) + Math.pow(this.position.y - y2, 2));

    }

public void shoot(int d){
    bullets.add(new Bullet(position.x, position.y, d));
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

class Bullet{
    Pair position;
    Pair velocity;
    final int direction;
    
    public Bullet(double x, double y, int d){
        position =  new Pair(x-5, y-5);
        velocity= new Pair(200,200);
        direction=d;
    
        
    }

    public void update(Game g, double time){
        
       switch (direction) {
        case 0:
            position.y -= velocity.y*time;
            break;
        case 90:
             position.x+= velocity.x*time;
            break;
        case 180:
            position.y += velocity.y*time;
            break;
        case -90:
             position.x -= velocity.x*time;
             break;
       }
      
        

    }

    public void draw(Graphics g){
       
        

            g.setColor(Color.WHITE);
            g.fillOval((int)position.x, (int)position.y, 10,10);
    
            

      

    }

    

}