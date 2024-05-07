import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;

public class IntroAnimation extends JPanel {

    private int width;
    private int height;
    private Image background;
    private Image textbox;
    private Image wizardude;
    private Font gameFont;
    public int continueCounter = 0;

    public IntroAnimation(int WIDTH, int HEIGHT){
        this.width = WIDTH;
        this.height = HEIGHT;

        background = new ImageIcon("assets\\IntroAnimation\\background.png").getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);

        textbox = new ImageIcon("assets\\IntroAnimation\\textbox.png").getImage();
        textbox = textbox.getScaledInstance(width/2+100, height/2, Image.SCALE_DEFAULT);

        wizardude = new ImageIcon("assets\\IntroAnimation\\wizarddude.png").getImage().getScaledInstance(width, height,Image.SCALE_DEFAULT);
        
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
        g.fillRect(-width/2, -height/2, width, height);

        g.drawImage(background, 0, 0, null);
        g.drawImage(wizardude, - 200, 0, null);
        g.drawImage(textbox, width/2-100, height/2-450, null);
        
        g.setFont(gameFont);
        g.setColor(Color.white);
        g.drawString("Press K to Continue", 0, 0);
        g.drawString("Press K to Continue", 1150, 700);
        g.setColor(Color.black);

        if(continueCounter == 0){
            g.drawString("Hello, Young One", width/2 + 200, height/2 - 200);
        }

        if(continueCounter == 1){
            g.drawString("Glad to see you woke up", width/2 + 160, height/2-200);
        }

        if(continueCounter == 2){
            g.drawString("I know you're lost", width/2 + 200, height/2 - 200);
        }

        if(continueCounter == 3){
            g.drawString("...that's a you problem", width/2 + 170, height/2 - 200);
        }

        if(continueCounter == 4){
            g.drawString("So you're going to", width/2 + 200, height/2 - 210);
            g.drawString("explore the galaxy", width/2 + 200, height/2 - 180);
        }
        
        if(continueCounter == 5){
            g.drawString("aim with mouse left click ", width/2 + 140, height/2 - 210);
            g.drawString("to shoot to break asteroids", width/2 + 140, height/2 - 180);
            g.drawString("to get fuel", width/2 + 140, height/2 - 150);
        }

        if(continueCounter == 6){
            g.drawString("and explore planets", width/2 + 170, height/2 - 210);
            g.drawString("for upgrades", width/2 + 170, height/2 - 180);
        }

        if(continueCounter == 7){
            g.drawString("find the Goldilocks planet", width/2 + 160, height/2 - 210);
            g.drawString("to save yourself!", width/2 + 170, height/2 - 180);
        }

        if(continueCounter == 8){
            g.drawString("okay bye", width/2 + 170, height/2 - 210);
            g.drawString("i wish you no luck", width/2 + 170, height/2 - 180);
        }

        //System.out.println(continueCounter);

        //debugging tools
        // g.setColor(Color.PINK);
        // g.drawLine(width/2, 0, width/2, height);
        // g.drawLine(-width/2, 0, width/2, 0);
        //end debugging
    }
}
