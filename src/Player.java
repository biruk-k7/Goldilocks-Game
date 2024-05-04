import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Iterator;


public class Player extends Character implements Movable{

    private Pair position ;
    private Pair velocity ;
    private Image spaceship;
    private Image astronaut;
    private double fuelCapacity;
    public double fuelCollected;
    private double currentFuel;
    private int spaceshipLevel;
    private Rectangle bounds;
    public ArrayList<Bullet> bullets = new ArrayList<Bullet>();

    private boolean isSpaceship = false;
 
    public Player(double initX, double initY){

        super();
       position = new Pair(initX, initY);
        velocity = new Pair(0, 0);
        spaceship = new ImageIcon("./assets/player/spaceship.png").getImage();
        spaceship = spaceship.getScaledInstance(60, 60, Image.SCALE_DEFAULT);
        astronaut = new ImageIcon("./assets/player/astronaut.gif").getImage();
        astronaut=astronaut.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        this.fuelCapacity = 10;
        this.currentFuel = 10;
        this.spaceshipLevel = 1; 
        bounds = new Rectangle((int)position.x, (int)position.y, 50, 50);


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

    public void  refuel(){
        if(fuelCollected>=2){

        if(currentFuel+2>=fuelCapacity){
            fuelCollected -= fuelCapacity-currentFuel;
            currentFuel=fuelCapacity;
            
        }
        else
        currentFuel+=2;
        fuelCollected-=2;
    }
 }

    public void updateFuel(boolean isOnPlanet, double fuelDrain){
        if(!isOnPlanet){
        this.currentFuel -= fuelDrain;
        }
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
            //g.drawRect((int)position.x-25, (int)position.y-25, spaceship.getWidth(null)-15, spaceship.getHeight(null)-15);
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
        bounds = new Rectangle((int)position.x-20, (int)position.y-20, spaceship.getWidth(null)-20, spaceship.getHeight(null)-20);
        
        if(Math.pow(velocity.x, 2) + Math.pow(velocity.y,2) > 0){
            updateFuel(isOnPlanet(g), 0.01);;
        }

    }

    public boolean isOnPlanet(Game g){
        // for loop to check all planets
        // our own position
        // planet position + radius calculation
        
        for (Planet s:g.planets){
            if (this.bounds.intersects(s.getBounds())){
            
                return true;
            }
            
        }
      
        return false;
    }

   

public void shoot(int d){
    bullets.add(new Bullet(position.x, position.y, d));
}
  
   

}

class Bullet{
    Pair position;
    Pair velocity;
    int radius;
    public boolean used;
    private Rectangle bounds;
    final int direction;
    
    public Bullet(double x, double y, int d){
        position =  new Pair(x-5, y-5);
        velocity= new Pair(200,200);
        radius=5;
        bounds=new Rectangle((int)position.x, (int)position.y,2*radius,2*radius);
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

       Iterator<Asteroid> asteroidIterator = g.asteroids.iterator();
while (asteroidIterator.hasNext()) {
    Asteroid a = asteroidIterator.next();
    double dist = Math.sqrt(Math.pow((a.pos.x + a.radius) - (position.x + radius / 2), 2) +
            Math.pow((a.pos.y + a.radius) - (position.y + radius / 2), 2));
    if (dist < 20&&!used) {
        asteroidIterator.remove(); 
        this.used =true;
        g.steve.fuelCollected  += 2;
    }
}

          
     }

     
       
       

    public Rectangle getBounds(){
        return bounds;
    }
    public void draw(Graphics g){
       
        
        if(!used){
            g.setColor(Color.WHITE);
            g.fillOval((int)position.x, (int)position.y, 10,10);
        }
    
            

      

    }

    

}