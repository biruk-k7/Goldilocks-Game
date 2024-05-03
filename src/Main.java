import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.*;
import java.lang.Math.*;
import java.net.Socket;
import java.awt.Dimension;
import java.awt.Toolkit;



public class Main extends JPanel implements KeyListener, MouseListener{

    //store the state of the game 
    public static final int START_SCREEN = 0;
    public static final int GAME_RUNNING = 1;
    public static final int GAME_OVER = 2;
    public static boolean isIntroScreen = false;

    //main storage variables for the size of the world
    public static final int GAME_WIDTH = 10000;
    public static final int GAME_HEIGHT = 10000;
    //store the size of the system screen
    public static Dimension screenDimension; 
    //store the size of the game SCREEN
    public static int WIDTH;
    public static int HEIGHT;
    public static final int FPS = 60;
    //field to represent the game
    Game steveGame;

    public Main(){
        //get dimensions for the screen
        screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        //initialize the width and height of the game screen on system
        WIDTH = (int)screenDimension.getWidth();
        HEIGHT = (int)screenDimension.getHeight();
        System.out.println(WIDTH);
        System.out.println(HEIGHT);
        //constructor for game
        steveGame = new Game();
        //add listeners 
        addKeyListener(this);
        addMouseListener(this);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        //start thread
        Thread mainThread = new Thread(new Runner());
        mainThread.start();
    }

    public static void main(String[] args){

        //basic game setup
        JFrame frame = new JFrame("Space Adventure World");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Main mainGame = new Main();
        frame.setContentPane(mainGame);
        frame.setBackground(Color.BLACK);
        frame.pack();
        frame.setVisible(true);

        //https://stackoverflow.com/questions/10468149/jframe-on-close-operation 
        //clear planet files on close

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e){
                for (int i =1; i<=Planet.getNumPlanets(); i++){
                    File file = new File("./assets/planets/planet"+i+".png");
                    file.delete();
                }
            }
        });
    }

    class Runner implements Runnable{
        //the thread class
        public void run() {
            while(true){
                
                if(isIntroScreen){
                    steveGame.updateIntroScreen(1.0 / (double) FPS);
                }else{
                    steveGame.updateGame(1.0 / (double)FPS);
                }

                

                repaint();
                try{
                    Thread.sleep(1000/FPS);
                } catch(InterruptedException e){}
            } 
        }
    }

    //method to handle key presses
    public void keyPressed(KeyEvent e) {
        char c = e.getKeyChar();

        //handle player and camera movemnet with key inputs
        if(steveGame.steve.getFuel() > 1){
            switch (c) {
                case 'w':
                    steveGame.steve.move(0);
                    steveGame.cam.move(0);
                    if(steveGame.steve.getIsSpaceShip()) steveGame.rotate=0;
                    break;
                case 'a':
                    steveGame.steve.move(1);
                    steveGame.cam.move(1);
                    if(steveGame.steve.getIsSpaceShip()) steveGame.rotate=-90;
                    break;
                case 's':
                    steveGame.steve.move(2);
                    steveGame.cam.move(2);
                    if(steveGame.steve.getIsSpaceShip()) steveGame.rotate=180;
                    break;
                case 'd':
                    steveGame.steve.move(3);
                    steveGame.cam.move(3);
                    if(steveGame.steve.getIsSpaceShip()) steveGame.rotate=90;
            }
        }
        repaint();
    }

    //method to handle what happens when a key is released
    public void keyReleased(KeyEvent e) {
        char c=e.getKeyChar();
        //handle player movemnet with key inputs
        switch (c) {
            //moving camera and player
            case 'w':
                steveGame.steve.stopMove(0);
                steveGame.cam.stopMove(0);
                break;
            case 'a':
                steveGame.steve.stopMove(1);
                steveGame.cam.stopMove(1);
                break;
            case 's':
                steveGame.steve.stopMove(2);
                steveGame.cam.stopMove(2);
                break;
            case 'd':
                steveGame.steve.stopMove(3);
                steveGame.cam.stopMove(3);
                break;
            case ' ':
               if(!steveGame.steve.isOnPlanet(steveGame)){
                steveGame.steve.shoot(steveGame.rotate);
               }
                break;
            case 'f':
              steveGame.steve.refuel();

        }
    }
    
    //method to handle key types
    public void keyTyped(KeyEvent e) {
    	char c = e.getKeyChar();
    }
    
    
    public void addNotify() {
        super.addNotify();
        requestFocus();
    }

    public void mousePressed(MouseEvent e){

    }

    public void mouseReleased(MouseEvent e){

    }
    
    public void mouseEntered(MouseEvent e){

    }

    public void mouseExited(MouseEvent e){

    }

    public void mouseClicked(MouseEvent e){

    }   

    public void paintComponent(Graphics g) {
        //draw the screen and the player
        super.paintComponent(g);    
        
        //this simulates the camera movement
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(-steveGame.cam.position.x, -steveGame.cam.position.y);
    
        //"infinite" world
        g2d.setColor(Color.BLACK);
        g2d.fillRect(-50000, -50000, 100000, 100000);
        if(isIntroScreen){steveGame.introScreen.draw(g);}
        else{steveGame.background.drawBackground(g);
        steveGame.drawPlayers(g);}
        
    }

   
    
}

class Game{
    //class to represent the RUNNING_GAME

    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;
    public int spawnRadius = 600;
    public static int gameX, gameY;
    public Player steve;
    public Planet testPlanet;
    public Villager villager;
    public int rotate=0;


    public static final int renderDistance=2000;

    public Camera cam;
    public IntroScreen introScreen;
    public WorldNoise worldNoise;
    public Background background;
    public StarterPlanet starterPlanet;
    public ArrayList<Planet> planets;
    //public ArrayList<Planet> asteroids;
    //Asteroid asteroid;
    

    public Game(){
      

        steve = new Player(0, 0);
        villager = new Villager(new Pair(0,0));
        cam = new Camera(new Pair(-Main.WIDTH/2,-Main.HEIGHT/2));
        planets = new ArrayList<Planet>();
        introScreen = new IntroScreen(WIDTH, HEIGHT);

        //testing planet
        testPlanet = new Planet(17);

        starterPlanet = new StarterPlanet();

        //asteroid = new Asteroid();
        //asteroid.setPos(new Pair(-400, -400));

        testPlanet.setPos(new Pair(-300,-300));
    
       
        this.worldNoise = new WorldNoise(new Pair(10000,10000), new Pair(0,0));
        worldNoise.generateNoise();

        background = new Background(10000, 10000);

        for(int i = 0; i < worldNoise.noise.length; i++){
            for(int j = 0; j < worldNoise.noise.length; j++){

                //check if a planet should spawn
                if(worldNoise.checkForPlanet(i, j)){
                    //get rand numbers to set position
                    Random rand = new Random();
                    int randomNumber = rand.nextInt(2);
                    int multiplier = (randomNumber == 0) ? -1 : 1;
                    Random rand2 = new Random();
                    int randomNumber2 = rand2.nextInt(2);
                    int multiplier2 = (randomNumber2 == 0) ? -1 : 1;
                    //create new planet with rand radius
                    Planet toAdd = new Planet(10+rand.nextInt(15));
                    toAdd.setPos(new Pair(multiplier*i*50, multiplier2*j*50));
                    //if planwt doesnt overlap, set position offset for image
                    while (checkTotalOverlap(toAdd)){
                        toAdd.setPos(new Pair(toAdd.getX()+toAdd.getImage().getHeight(null), toAdd.getY()+toAdd.getImage().getHeight(null)));
                    
                    }

                    planets.add(toAdd);
                }
               
            }
           
        }
      
    }

    public boolean checkTotalOverlap(Planet a){
        for (Planet p:planets){
            if (p.checkPlanetOverlap(a)){
                return true; 
            }
        }
        return false;
    }


    public void initializeAsteroids(){

        //asteroids.add(new Asteroid());
    }

    public void updateGame(double time){
        villager.updateAI(this, time);
        for(Bullet bullet:steve.bullets){
            bullet.update(this, time);
          }
        steve.update(this, time);
        cam.update(this, time);

        if(steve.getFuel() <= 1){
            cam.velocity.x = 0;
            cam.velocity.y = 0;
            steve.vel.x = 0;
            steve.vel.y = 0;
        }
        
    }

    public void drawPlayers(Graphics g){
      
        drawPlanets(g);
        starterPlanet.drawStarterPlanet(g);
        steve.drawFuelCapacity(g);
        villager.draw(g);
        //drawAsteroids(g);

        for(Bullet bullet: steve.bullets){

           bullet.draw(g);
    
        }
        Graphics2D g2d= (Graphics2D)g;
        
        if(!steve.isOnPlanet(this)){
             g2d.rotate(Math.toRadians(rotate),steve.getX(),steve.getY());
        }
       
        steve.draw(g2d);
    }

    public void drawPlanets(Graphics g){

        for(Planet s:planets){
            s.draw(g);
        }

    }


    public void drawAsteroids(Graphics g){

        //asteroid.draw(g);
    }

 

    public void updateIntroScreen(double time){

    }

   
}