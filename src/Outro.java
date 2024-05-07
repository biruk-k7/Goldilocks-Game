import javax.swing.JPanel;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Font;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Outro{
    private int width;
    private int height;
    private Image background;
    private Font gameFont;
    public int continueCounter;
    

    public Outro(int WIDTH, int HEIGHT){

        this.width = WIDTH;
        this.height = HEIGHT;
        this.continueCounter = 0;
       

        background = new ImageIcon("assets\\outro\\outro.png").getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);

        String fontFile = "GameFont.ttf";
        gameFont = null;
        try {
            gameFont = Font.createFont(Font.TRUETYPE_FONT, new java.io.File(fontFile)).deriveFont((float) 30);

        } catch (Exception e) {
            gameFont = new Font("Arial", Font.PLAIN, 50); 
        }
    }
   
   

    public void draw(Graphics g){
        g.setColor(Color.black);
        g.drawRect(0, 0, width, height);
        g.drawImage(background, 0, 0, null);
        g.setFont(gameFont);
        g.setColor(Color.white);
        g.drawString("Press K to Continue", 0, 0);
        g.drawString("Press K to Continue", 1150, 700);
        g.setColor(Color.black);

        if(continueCounter == 0){
            g.drawString("Seems like you found Goldilocks", width/2 + 160, height/2 - 210);
        }

        if(continueCounter == 1){
            g.drawString("Good job, Young One", width/2 + 160, height/2-210);
        }

        if(continueCounter == 2){
            g.drawString("First lost, then Found", width/2 + 160, height/2 - 210);
        }

        if(continueCounter == 3){
            g.drawString("The universe awaits you", width/2 + 160, height/2 - 210);
        }

        if(continueCounter == 4){
            g.drawString("Wake up", width/2 + 160, height/2 - 210);
        }

        if(continueCounter == 5){
            System.exit(1);
        }

    }
}