import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;
import javax.swing.JPanel;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Villager extends JPanel {

    public Pair position;
    public Pair velocity;
    public Image villager;
    public boolean prompt = false;
    public Font font;
    public BufferedImage img;

    public Villager(Pair position) throws IOException{

        Random rand = new Random();

        this.position = position;
        position.y = -160;
        this.velocity = new Pair(20, 0);
        File file = new File("./assets/StarterPlanet/villager.png");
        villager = ImageIO.read(file);
        villager = villager.getScaledInstance(70, 70, Image.SCALE_DEFAULT);

        String fontFile = "GameFont.ttf";
        
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new java.io.File(fontFile)).deriveFont((float) 12);

        } catch (Exception e) {
            font = new Font("Arial", Font.PLAIN, 25); 
        }
        
    }

    public void talkTo(){

    }

    public void flipImage(){
        BufferedImage bufferedImage = new BufferedImage(villager.getWidth(null), villager.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(villager, 0, 0, null);
        g2d.dispose();
    
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-bufferedImage.getWidth(null), 0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        villager = op.filter(bufferedImage, null);
    }

    public void updateAI(Game g, double time){
        if(velocity.x > 0){
            if(position.x < 100){
            position = position.add(velocity.times(time));
            }else{
                velocity.x = -velocity.x;
                flipImage();
            }
        }else if(velocity.x < 0){
            if(position.x > -200){
                position = position.add(velocity.times(time));
            }else{
                velocity.x = -velocity.x;
                flipImage();
            }
        }

        double posPlayerX = g.steve.getX();
        double posPlayerY = g.steve.getY();
        double distanceToPlayer = Math.sqrt(Math.pow(posPlayerX - position.x, 2) + Math.pow(posPlayerY - position.y, 2));
        // System.out.println(distanceToPlayer);
        // System.out.println(posPlayerX + " " + position.x);
        // System.out.println(posPlayerY + " " + position.y);

        if(distanceToPlayer < 80){
            // System.out.println("bruh");
            // System.out.println(distanceToPlayer);
            prompt = true;
        }else{
            prompt = false;
        }
       
        
    }

    public void draw(Graphics g){
        g.drawImage(villager, (int)position.x - 40, (int)position.y - 40, null);
        g.setFont(font);

        if(prompt){
            g.setColor(Color.black);
            g.fillRoundRect((int) position.x + 20, (int) position.y - 10, 80, 50, 10, 10);
            g.setColor(Color.white);
            g.fillRoundRect((int) position.x + 23, (int) position.y - 8, 75, 45, 10, 10);
            g.setColor(Color.black);
            g.drawString("Hello", (int) position.x + 27, (int) position.y + 10);
            g.drawString("Young One",(int) position.x + 27, (int) position.y + 30);
           
        }


    }

    
}

