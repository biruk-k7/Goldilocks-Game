import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.Font; 
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

    //main storage variables
    public static final int GAME_WIDTH = 10000;
    public static final int GAME_HEIGHT = 10000;

    public static Dimension screenDimension; 

    public static int WIDTH;
    public static int HEIGHT;
    public static final int FPS = 60;



    Game steveGame;

    public Main(){

        screenDimension = Toolkit.getDefaultToolkit().getScreenSize();

        WIDTH = (int)screenDimension.getWidth();
        HEIGHT = (int)screenDimension.getHeight();
        System.out.println(WIDTH);
        System.out.println(HEIGHT);
        //constructor for main 
        steveGame = new Game();

        addKeyListener(this);
        addMouseListener(this);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        Thread mainThread = new Thread(new Runner());
        mainThread.start();
    }

    public static void main(String[] args){

        //basic game setup
        JFrame frame = new JFrame("Space Steve Adventure World");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Main mainGame = new Main();
        frame.setContentPane(mainGame);
        frame.setBackground(Color.BLACK);
        frame.pack();
        frame.setVisible(true);
    }

    class Runner implements Runnable{

        public void run() {
            while(true){
                
                steveGame.updateGame(1.0 / (double)FPS);

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
                    break;
                case 'a':
                    steveGame.steve.move(1);
                    steveGame.cam.move(1);
                    break;
                case 's':
                    steveGame.steve.move(2);
                    steveGame.cam.move(2);
                    break;
                case 'd':
                    steveGame.steve.move(3);
                    steveGame.cam.move(3);
            }
        }

        
        //adjust the camera based on the palayer's position &  width and the height
        

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
        }
    }
    
    //method to handle key types
    public void keyTyped(KeyEvent e) {
    	char c = e.getKeyChar();
    }
    
    //not quite sure what this does
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
        steveGame.background.drawBackground(g);
        steveGame.drawPlayers(g );
        
    }
}

class Game{
    //class to represent the RUNNING_GAME

    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;
    public static int gameX, gameY;
    public Player steve;
    public Planet testPlanet;


    public static final int renderDistance=2000;

    public Camera cam;
    public WorldNoise worldNoise;
    public Background background;
    public StarterPlanet starterPlanet;
    public Planet[][] planets = new Planet[100][100];
    

    public Game(){
        //set dimensions and add player
        //gameX = World.WIDTH/2;
        //gameY = World.HEIGHT/2;

        steve = new Player(0, 0);
        cam = new Camera(new Pair(-Main.WIDTH/2,-Main.HEIGHT/2));
       

        //testing planeto
        testPlanet = new Planet(17);

        starterPlanet = new StarterPlanet();

        testPlanet.setPos(new Pair(-300,-300));
    
        //draw planeto

        this.worldNoise = new WorldNoise(new Pair(10000,10000), new Pair(0,0));
        worldNoise.generateNoise();

        background = new Background(10000, 10000);

        for(int i = 0; i < worldNoise.noise.length; i++){
            for(int j = 0; j < worldNoise.noise.length; j++){
                if(worldNoise.checkForPlanet(i, j)){
                    //System.out.println(worldNoise.noise[i][j]);
                    Random rand = new Random();
                    int randomNumber = rand.nextInt(2);
                    int multiplier = (randomNumber == 0) ? -1 : 1;
                    Random rand2 = new Random();
                    int randomNumber2 = rand2.nextInt(2);
                    int multiplier2 = (randomNumber2 == 0) ? -1 : 1;
                    planets[i][j] = new Planet(10+rand.nextInt(15));
                    planets[i][j].setPos(new Pair(multiplier*i*50, multiplier2*j*50));
                }
            }
        }
        
    }

    public void updateGame(double time){
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
        //System.out.println(steve.getX() + " " + steve.getY());
        //draw the player
        //testPlanet.draw(g);
        drawPlanets(g);
        starterPlanet.drawStarterPlanet(g);
        steve.draw(g);
        steve.drawFuelCapacity(g);
    }

    public void drawPlanets(Graphics g){

        for(int i = 0; i < worldNoise.noise.length; i++){
            for(int j = 0; j < worldNoise.noise.length; j++){
                if(worldNoise.checkOverlap(i, j)){
                    planets[i][j].draw(g);
                }
                // else if(worldNoise.noise[i][j] < -0.95){
                //     background.drawStars(g, i*50, j*50);
                // }
            }
        }

    }

    public void getWidth(){
        //return width;
    }

    public void getHeight(){
        //return height;
    }

   
}