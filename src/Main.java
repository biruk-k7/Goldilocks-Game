import javax.swing.JPanel;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.IOException;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.awt.event.*;
import java.lang.Math.*;
import java.net.Socket;
import java.awt.Dimension;
import java.awt.Toolkit;



public class Main extends JPanel implements KeyListener, MouseListener, MouseMotionListener{

    //store the state of the game 
    public static final int START_SCREEN = 0;
    public static final int GAME_RUNNING = 1;
    public static final int GAME_OVER = 2;
    public static boolean isIntroScreen = true;
    public static boolean isIntroAnimation = true;

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

    //variable to make the win music run only once
    public boolean isWinMusic;


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
        addMouseMotionListener(this);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        //start thread
        Thread mainThread = new Thread(new Runner());
        mainThread.start();
        steveGame.stopSound();
        try {
            steveGame.playIntro();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
        }
        isWinMusic=false;
       
    }

    
    
    public static void main(String[] args){

        //basic game setup
        JFrame frame = new JFrame("Goldilocks");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Main mainGame = new Main();
        frame.setContentPane(mainGame);
        frame.setBackground(Color.BLACK);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);

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

                if(isIntroScreen){steveGame.introScreen.updateIntroScreen();}
                
                if(!isIntroAnimation && !isIntroScreen){
                    steveGame.updateGame(1.0 / (double)FPS); 
                }
                //play the win music only at the instant when player wins
                if(steveGame.isWinner && !isWinMusic){
                    try {
                        steveGame.playGoldilocksSound();
                    } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
                    }
                    isWinMusic=true;
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
                case 'W':
                    steveGame.steve.move(0);
                    steveGame.cam.move(0);
                    if(steveGame.steve.getIsSpaceShip()) steveGame.rotate=0;
                    break;
                case 'a':
                case 'A':
                    steveGame.steve.move(1);
                    steveGame.cam.move(1);
                    if(steveGame.steve.getIsSpaceShip()) steveGame.rotate=-90;
                    break;
                case 's':
                case 'S':
                    steveGame.steve.move(2);
                    steveGame.cam.move(2);
                    if(steveGame.steve.getIsSpaceShip()) steveGame.rotate=180;
                    break;
                case 'd':
                case 'D':
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
            //moving camera and player, and account for upper case
            case 'w':
            case 'W':
                steveGame.steve.stopMove(0);
                steveGame.cam.stopMove(0);
                break;
            case 'a':
            case 'A':
                steveGame.steve.stopMove(1);
                steveGame.cam.stopMove(1);
                break;
            case 's':
            case 'S':
                steveGame.steve.stopMove(2);
                steveGame.cam.stopMove(2);
                break;
            case 'd':
            case 'D':
                steveGame.steve.stopMove(3);
                steveGame.cam.stopMove(3);
                break;
        }
    }
    
    //method to handle key types
    public void keyTyped(KeyEvent e) {
    	char c = e.getKeyChar();

        if(isIntroAnimation){
            if(c == 'k' || c=='K'){
                steveGame.introAnimation.continueCounter++;
            }

            if(steveGame.introAnimation.continueCounter == 9){
                isIntroAnimation = false;
                steveGame.stopSound();
                try {
                    steveGame.playGameSound();
                } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
                
                }
            }
        }

        
        if(steveGame.isWinner){
            if(c == 'k' || c == 'K'){
                steveGame.outro.continueCounter++;
            }
        }
            
        
    }
    
    public void addNotify() {
        super.addNotify();
        requestFocus();
    }

    public void mousePressed(MouseEvent e){
        int mouse_x = e.getX();
        int mouse_y = e.getY();
    
        //Coding for the Play Button & Quit Button
        if(isIntroScreen){
            if(mouse_x >= Main.WIDTH/2 - 100 && mouse_x <= Main.WIDTH/2 + 100){
                if(mouse_y >= Main.HEIGHT/2 + 50 && mouse_y <= Main.HEIGHT/2 + 150){
                    isIntroScreen = false;
                    isIntroAnimation = true;
                    steveGame.stopSound();
                    try {
                        steveGame.playAnimationSound();
                    } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
        
                    }
                    
                }else if(mouse_y >= Main.HEIGHT/2 + 150 && mouse_y <= Main.HEIGHT/2 + 250){
                    for (int i =1; i<=Planet.getNumPlanets(); i++){
                        File file = new File("./assets/planets/planet"+i+".png");
                        file.delete();
                    }
                    System.exit(1);
                }
            }

            
        } 
        else{
            if(!steveGame.steve.isOnPlanet(steveGame) && steveGame.steve.canShoot){
                steveGame.steve.shoot(mouse_x,mouse_y);
               }

        }
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
        
        if(isIntroScreen){steveGame.introScreen.draw(g);}
        else if(!isIntroScreen && isIntroAnimation){steveGame.introAnimation.draw(g);}
        else if(!isIntroAnimation && !isIntroScreen && !steveGame.isWinner){
            
            //draw the screen and the player
            super.paintComponent(g); 
            Graphics2D g2d = (Graphics2D) g;
            //this simulates the camera movement
            g2d.translate(-steveGame.cam.position.x, -steveGame.cam.position.y);
            //"infinite" world
            g2d.setColor(Color.BLACK);
            g2d.fillRect(-50000, -50000, 100000, 100000);
            steveGame.background.drawBackground(g);
            steveGame.drawPlayers(g);
        }
        else if (steveGame.isWinner){
            steveGame.outro.draw(g);
        }
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int mouse_x = e.getX();
        int mouse_y = e.getY();
        
        if(isIntroScreen){
            if(mouse_x >= Main.WIDTH/2 - 100 && mouse_x <= Main.WIDTH/2 + 100){
                if(mouse_y >= Main.HEIGHT/2 + 50 && mouse_y <= Main.HEIGHT/2 + 150){
                    steveGame.introScreen.menuButton1 = Color.yellow;
                    steveGame.introScreen.menuButton2 = Color.white;
                    steveGame.introScreen.widthBox = 2.5*100;
                    steveGame.introScreen.buttonx1 = Main.WIDTH/2 - 125;
                    
                }else if(mouse_y >= Main.HEIGHT/2 + 200 && mouse_y <= Main.HEIGHT/2 + 250){
                    steveGame.introScreen.menuButton1 = Color.white;
                    steveGame.introScreen.menuButton2 = Color.red;
                    steveGame.introScreen.widthBox = 2*100;
                    steveGame.introScreen.buttonx1 = Main.WIDTH/2 - 100;
                }
            }else{
                steveGame.introScreen.menuButton1 = Color.white;
                steveGame.introScreen.menuButton2 = Color.white;
                steveGame.introScreen.widthBox = 2*100;
                steveGame.introScreen.buttonx1 = Main.WIDTH/2 - 100;
                
            }
        }
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
    public IntroAnimation introAnimation;
    public WorldNoise worldNoise;
    public Background background;
    public ArrayList<Planet> planets;
    public ArrayList<Asteroid> asteroids;
    public Goldilocks goldilocks;
    public boolean isWinner;
    public Outro outro;

    //music files 
    File fileMenu;
    File fileAnimation;
    File fileGame;
    File fileGoldilocks;
    Clip clip;
    
    

    public Game(){

        steve = new Player(0, 0);
        villager = new Villager(new Pair(0,0));
        cam = new Camera(new Pair(-Main.WIDTH/2,-Main.HEIGHT/2));
        planets = new ArrayList<Planet>();
        introScreen = new IntroScreen(Main.WIDTH, Main.HEIGHT);
        introAnimation = new IntroAnimation(Main.WIDTH,Main.HEIGHT);
        //add start planet
        planets.add(new StarterPlanet());
        asteroids = initAsteroids(200);
        isWinner=false;
        outro=new Outro(Main.WIDTH, Main.HEIGHT);
        try {
            initMusic();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
        }
        


    
       
        this.worldNoise = new WorldNoise(new Pair(10000,10000), new Pair(0,0));
        worldNoise.generateNoise();

        background = new Background(10000, 10000);
        //add end planet
        goldilocks=new Goldilocks();
        planets.add(goldilocks);
        //generate planets
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
    public void initMusic()throws LineUnavailableException, IOException, UnsupportedAudioFileException{
        fileMenu= new File("./assets/Music/introScreen.wav");
        fileAnimation = new File("./assets/Music/introAnimation.wav");
        fileGame = new File("./assets/Music/track1.wav");
        fileGoldilocks = new File("./assets/Music/track2.wav");
        clip = AudioSystem.getClip();
    }

    public void stopSound(){
        clip.stop();
    }

    public void playGoldilocksSound()throws LineUnavailableException, IOException, UnsupportedAudioFileException{
        clip.stop();
        AudioInputStream soundWinner = AudioSystem.getAudioInputStream(fileGoldilocks);
        clip = AudioSystem.getClip();
        clip.open(soundWinner);
        clip.start();
    }
    public void playIntro() throws LineUnavailableException, IOException, UnsupportedAudioFileException{
        AudioInputStream soundMenu = AudioSystem.getAudioInputStream(fileMenu);
        clip = AudioSystem.getClip();
        clip.open(soundMenu);
        clip.start();

    }
    public void playAnimationSound()throws LineUnavailableException, IOException, UnsupportedAudioFileException{
        AudioInputStream soundAnimation = AudioSystem.getAudioInputStream(fileAnimation);
        clip = AudioSystem.getClip();
        clip.open(soundAnimation);
        clip.start();
    }
    public void playGameSound()throws LineUnavailableException, IOException, UnsupportedAudioFileException{
        AudioInputStream soundGame = AudioSystem.getAudioInputStream(fileGame);
        clip = AudioSystem.getClip();
        clip.open(soundGame);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public boolean checkTotalOverlap(Planet a){
        for (Planet p:planets){
            if (p.checkPlanetOverlap(a)){
                return true; 
            }
        }
        return false;
    }


    public ArrayList<Asteroid> initAsteroids(int n){
        ArrayList<Asteroid> a = new ArrayList<Asteroid>();
        for (int i=0; i<n; i++){
            a.add(new Asteroid((int)(30+Math.random()*25)));
            
        }
        return a;
    }

    public void updateGame(double time){

        if(goldilocks.getBounds().intersects(steve.getBounds())){
            isWinner=true;
        }

        villager.updateAI(this, time);
        
        for(Bullet bullet:steve.bullets){
            bullet.update(this, time);

        }
        steve.update(this, time);
        cam.update(this, time);
        for(Asteroid a: asteroids){
            a.update(this, time);
            //making a new asteroid when one is destroyed
            if (a.destroyed){
                a=new Asteroid((int)(30+Math.random()*25));
            }
        }

        if(steve.getFuel() <= 1){
            cam.velocity.x = 0;
            cam.velocity.y = 0;
            steve.vel.x = 0;
            steve.vel.y = 0;
            steve.canShoot=false;

            clip.stop();
        }
        
    } 

    public void drawPlayers(Graphics g){
        drawPlanets(g);
        steve.drawFuelCapacity(g);
        villager.draw(g);
        for (Asteroid a : asteroids){
            a.draw(g);
        }

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


}