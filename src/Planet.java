//a class to represent planets

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.util.Iterator;

import org.w3c.dom.css.Rect;

public class Planet extends SpaceObject{

    public static void main(String[] args){

        Planet testPlanet = new Planet(7);
        testPlanet.setPos(new Pair(500,500));

        double[][] noiseMap = new double[2*testPlanet.radius + 1][2*testPlanet.radius + 1];

        for(int i =0; i < noiseMap.length ; i++){
            for(int j=0; j< noiseMap.length; j++){
                noiseMap[i][j] = testPlanet.noise(i, j);
                
            }
            
        }

        for(int i = 0; i < noiseMap.length; i++){
            for(int j = 0; j < noiseMap.length; j++){
                testPlanet.circularNoise();
                System.out.print(String.format("%.1f", noiseMap[i][j]) + " ");
            }
            System.out.println();
        }


    }
    protected double[][] noiseMap;
    protected int planetType;
    private static int countPlanets = 0;
    private int resources;
    private Image resourceImage;
   

    public Planet(int initRadius){
        pos = new Pair();
        vel = new Pair();
        accel=new Pair();
        radius = initRadius;
        planetType=(int)(Math.random()*6);
        //if earth is the planet
        resources=0;
        resourceImage=null;
        //if lava is the planet
        if(planetType==1){
            resources = (int)(15+Math.random()*15);
            

        }

        noiseMap = new double[2*this.radius + 1][2*this.radius + 1];

        for(int i =0; i < noiseMap.length ; i++){
            for(int j=0; j< noiseMap.length; j++){
                noiseMap[i][j] = this.noise(i, j);
            }
            
        }

        this.circularNoise();
        countPlanets++;
        try {
            makePNGfile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getNumPlanets(){
        return countPlanets;
    }

    public double[][] getNoiseMap(){
        return noiseMap;
    }

    //https://stackoverflow.com/questions/335600/collision-detection-between-two-images-in-java helped resolve this 
    public boolean checkPlanetOverlap(Planet a){
        
        return this.bounds.intersects(a.getBounds()) || new Rectangle(-250,-250, 500,500).intersects(a.getBounds());
    }

    public double noise(int i, int j){

        SimplexNoise noise = new SimplexNoise();

        return noise.noise(i, j);
    }

    public boolean insideCircle(Pair center, Pair point){

        // This code has been inspired by https://www.redblobgames.com/grids/circle-drawing/

        double dx = center.x - point.x;
        double dy = center.y - point.y;

        double distance = Math.sqrt(dx*dx + dy*dy);
        return distance <= radius;
        
    }

    public void circularNoise(){

        Pair center = new Pair(noiseMap.length / 2, noiseMap.length /2);
        
        for(int y = 0; y < noiseMap.length; y++ ){
            for(int x = 0; x < noiseMap.length; x++){
                if(!insideCircle(center, new Pair(y, x))){
                    this.noiseMap[y][x] = 0;
                }
                if(insideCircle(center, new Pair(y,x)) && this.noiseMap[y][x]==0){
                    noiseMap[y][x]=Math.random()*.9+.001;
                }
            }
        }

    }



   

    //make the png textures for ech randomly generated planet and store them
    private void makePNGfile() throws IOException{
        BufferedImage img = new BufferedImage(noiseMap.length*8, noiseMap.length*8, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        for(int i=0; i<noiseMap.length;i++){
            for(int j =0; j<noiseMap.length;j++){
                if(noiseMap[i][j]!=0){


                    g2d.setColor(planetColorScheme(noiseMap[i][j]));
                    g2d.fillRect((int)pos.x+i*8, (int)pos.y+j*8, 8, 8);

                    
                }
            }
        }
        File file = new File("./assets/planets/planet" +countPlanets +".png");
        ImageIO.write(img, "png", file);
        texture = ImageIO.read(file);
        bounds = new Rectangle((int)pos.x, (int)pos.y, (int)texture.getWidth(null), (int)texture.getHeight(null));
    }
    @Override
    public void draw(Graphics g){

        
        g.drawImage(texture, (int)(pos.x), (int)(pos.y), null);
        // g.drawRect((int)pos.x, (int)pos.y, texture.getWidth(null), texture.getHeight(null));
        if(planetType==1 || planetType == 4){
            //g.fillOval(reso, planetType, countPlanets, radius);
        }
    }

    //determine colors for the planet based on its type
    public Color planetColorScheme(double alpha){
        //cyberpunk planet
        if(this.planetType==0){
            if(alpha<.4 &&alpha>0) return new Color( 	242, 56, 230);
            if(alpha>.4 && alpha<.8) return new Color( 	121, 18, 166);
            if(alpha<0 && alpha>-.4) return new Color( 	52, 28, 140);
            if(alpha<-.4) return new Color( 	24, 53, 140);
            if(alpha>.8) return new Color( 	102, 242, 242);
        }
        //lava planet
        if(planetType==1){
            if(alpha<.4 &&alpha>0) return new Color(242, 169, 80);
            if(alpha>.4 && alpha<.8) return new Color(242, 116, 5);
            if(alpha<0 && alpha>-.4) return new Color(242, 92, 5);
            if(alpha<-.4) return new Color(191, 38, 4);
            if(alpha>.8) return new Color(89, 16, 10);
        }
        //forest planet
        if(planetType==2){
            if(alpha<.4 &&alpha>0) return new Color(3,65,89);
            if(alpha>.4 && alpha<.8) return new Color(2,89,81);
            if(alpha<0 && alpha>-.4) return new Color(2,115,94);
            if(alpha<-.4) return new Color(3,140,62);
            if(alpha>.8) return new Color(12,242,93);
        }
        //ice planet
        if(planetType==3){
            if(alpha<.4 &&alpha>0) return new Color(89, 52, 59);
            if(alpha>.4 && alpha<.8) return new Color(215, 215, 217);
            if(alpha<0 && alpha>-.4) return new Color(159, 159, 166);
            if(alpha<-.4) return new Color(102, 106, 115);
            if(alpha>.8) return new Color(242, 242, 240);
        }
        //earth
        if(planetType==4){
            //green
            if(alpha<.3 &&alpha>0) return new Color(0,0,220);
            if(alpha>.3 && alpha<.5) return new Color(0,200,0);
            if(alpha>.5 && alpha<.8) return new Color(0,180,0);
            //white
            if(alpha>.8) return new Color(216,235,242);
            //blue
            if(alpha<-.3&&alpha>-.5) return new Color( 0, 0, 95);
            if(alpha<0 && alpha>-.3) return new Color(0,0,162);
            if(alpha<-.5&& alpha>-.8) return new Color( 0, 0, 128);
            if(alpha<-.8) return new Color(0, 0, 200);
        }
        //dune planet
        if(planetType==5){
            if(alpha<.4 &&alpha>0) return new Color(166, 155, 134);
            if(alpha>.4 && alpha<.8) return new Color(102, 96, 83);
            if(alpha<0 && alpha>-.4) return new Color( 	230, 215, 186);
            if(alpha<-.4) return new Color( 	242, 227, 196);
            if(alpha>.8) return new Color( 	204, 191, 165);
        }

            return null;   
    }
}

class Asteroid extends SpaceObject implements Movable{

    public boolean destroyed;
    //variables to store number of resources dropped after destroying the asteroid
    private Rectangle bounds;
    private Image texture;

    public Asteroid(int initRadius){
        pos = new Pair(-5000 + Math.random()*10000, -5000 + Math.random()*10000);
        int mult1 = (Math.random()>.5) ? 1: -1;
        int mult2 = (Math.random()>.5) ? 1: -1;
        vel = new Pair(mult1*(20 + Math.random()*100), mult2*(20+Math.random()*100));
        accel=new Pair();
        radius = initRadius;
        destroyed= false;

        bounds = new Rectangle((int)pos.x, (int)pos.y, radius, radius);
        texture = new ImageIcon("./assets/player/asteroid1.png").getImage();
        texture = texture.getScaledInstance(radius+5, radius, Image.SCALE_DEFAULT);
    }



    @Override
    public void draw(Graphics g){
      if(!destroyed){
        
        g.drawImage(texture,(int)pos.x, (int)pos.y, null);
                 
       }
    }

    @Override
    public void update(Game g, double time) {
        pos = pos.add(vel.times(time));
        //making the asteroids respawn if they are out of bounds 
        if(pos.x>5000 || pos.x<-5000 || pos.y>5000 || pos.y<-5000){
            destroyed=true;
        }
        
        //the following was helpful to avoid  concurrent modification exception. https://www.youtube.com/watch?v=83Omy_C8t24&t=340s
        Iterator<Bullet> bulletIterator = g.steve.bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet a = bulletIterator.next();
            double dist = Math.sqrt(Math.pow((a.position.x + a.radius) - (pos.x + radius / 2), 2) +
                    Math.pow((a.position.y + a.radius) - (pos.y + radius / 2), 2));
            if (dist < 20&&!destroyed) {
                destroyed = true;
                bulletIterator.remove();
                g.steve.fuelCollected  +=2;
                System.out.println("Collected: " +  g.steve.fuelCollected);
             

            }
        }

    
        
            
              
     }
    



    @Override
    public void move(int dir) {
      
    }

    @Override
    public void stopMove(int dir) {
        
    }

    public Rectangle getBounds(){
        return bounds;
    }

}

class Goldilocks extends Planet{
    private int gold;
    private int water;
    private int fossilfuels;

    public Goldilocks(int r){
        super(r);

    }

}

class StarterPlanet extends Planet {

    private Image starterPlanet;
    private Image cloud1;
    private Image cloud2;
    private Image castle;
    private  int cloudX1 = -350;
    private  int cloudX2 = -100;
    private int velocityX = 1;
    private int velocityX2 = -1;

    public StarterPlanet(){
        super(10);
        starterPlanet = new ImageIcon("assets\\StarterPlanet\\planet.png").getImage();
        starterPlanet = starterPlanet.getScaledInstance(500, 500, Image.SCALE_DEFAULT);
        
        cloud1 = new ImageIcon("assets\\StarterPlanet\\cloud1.png").getImage();
        cloud1 = cloud1.getScaledInstance(300, 200, Image.SCALE_DEFAULT);

        cloud2 = new ImageIcon("assets\\StarterPlanet\\cloud2.png").getImage();
        cloud2 = cloud2.getScaledInstance(600, 500, Image.SCALE_DEFAULT);

        castle = new ImageIcon("assets\\StarterPlanet\\castle.png").getImage();
        castle = castle.getScaledInstance(200, 200, Image.SCALE_DEFAULT);

    }

    public void drawStarterPlanet(Graphics g){
        g.drawImage(castle, -105, -400, null);
        
        g.drawImage(starterPlanet,-250 ,-250, null);
        animateCloud(g); 
    }

    public void animateCloud(Graphics g){

        // Moving cloud 1 to the right until it collides with a certain point

        if(velocityX > 0){
            if (cloudX1 < -100) {
                cloudX1 += velocityX;
                g.drawImage(cloud2, cloudX1, -150, null);
            } else {
                velocityX = -velocityX;
                g.drawImage(cloud2, cloudX1 - 1, -150, null);
            }
        }else if(velocityX < 0){
            if (cloudX1 > -500) {
                cloudX1 += velocityX;
                g.drawImage(cloud2, cloudX1, -150, null);
            } else {
                velocityX = -velocityX;
                g.drawImage(cloud2, cloudX1 + 1, -150, null);
            }
        }
        
    }

    public void animateCloud2(Graphics g){
        if(velocityX2 > 0){
            if (cloudX2 < -50) {
                cloudX2 += velocityX2;
                g.drawImage(cloud1, cloudX2, 0, null);
            } else {
                velocityX2 = -velocityX2;
            }
        }else if(velocityX2 < 0){
            if (cloudX2 > -400) {
                cloudX2+= velocityX2;
                g.drawImage(cloud1, cloudX2, 0, null);
            } else {
                velocityX2 = -velocityX2;
            }
        }
    }



    
    
}


