import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;
import javax.swing.JPanel;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

class Villager extends JPanel {

    public Pair position;
    public Pair velocity;
    public Image villager;

    public Villager(Pair position){

        Random rand = new Random();

        this.position = position;
        this.velocity = new Pair(20, 0);
        villager = new ImageIcon("assets\\StarterPlanet\\villager.png").getImage();
        villager = villager.getScaledInstance(70, 70, Image.SCALE_DEFAULT);
        
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
       
        
    }

    public void draw(Graphics g){
        g.drawImage(villager, (int)position.x, -200, null);
    }

    
}

