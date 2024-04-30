import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.util.Random;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.RenderingHints;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.GradientPaint;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import java.awt.Image;
import javax.swing.ImageIcon;


public class Background {
    
    private int WIDTH;
    private int HEIGHT;
    private Image star1;
    private Image star2;
    private Image star3;
    private Image star4;
    private Image star5;

    private double x = Math.random();

    public Background(int worldWidth, int worldHeight){
        this.HEIGHT = worldHeight;
        this.WIDTH = worldWidth;

        star1 = new ImageIcon("assets\\stars\\star1.png").getImage();
        star1 = star1.getScaledInstance(30, 30, Image.SCALE_DEFAULT);

        star2 = new ImageIcon("assets\\stars\\star2.png").getImage();
        star2 = star2.getScaledInstance(30, 30, Image.SCALE_DEFAULT);

        star3 = new ImageIcon("assets\\stars\\star3.png").getImage();
        star3 = star3.getScaledInstance(30, 30, Image.SCALE_DEFAULT);

        star4 = new ImageIcon("assets\\stars\\star4.png").getImage();
        star4 = star4.getScaledInstance(30, 30, Image.SCALE_DEFAULT);

        star5 = new ImageIcon("assets\\stars\\star5.png").getImage();
        star5 = star5.getScaledInstance(30, 30, Image.SCALE_DEFAULT);
    }

    public void drawBackground(Graphics g0ri){

        Graphics2D g = (Graphics2D) g0ri;
        
        
        Point2D center = new Point2D.Float(0, 0);
        float radius = WIDTH/2;
        float[] dist = {0.0f, 0.2f, 1.0f};
        Color[] colors = {new Color(24, 5, 39), new Color(17, 3, 31), Color.black};
        RadialGradientPaint p = new RadialGradientPaint(center, radius, dist, colors, CycleMethod.REPEAT);

        g.setPaint(p);

        g.fillRect(-WIDTH/2, -HEIGHT/2, WIDTH, HEIGHT);
        
    }

    public void drawStars(Graphics g, double posx, double posy){

         g.drawImage(star2, 50, 50, null);
         
    
        if(x < 0.2){
            g.drawImage(star1, (int)posx, (int)posy, null);
        }
        if(x > 0.2 && x < 0.4){
            g.drawImage(star2, (int)posx, (int)posy, null);
        }
        if(x > 0.4 && x < 0.6){
            g.drawImage(star3, (int)posx, (int)posy, null);
        }
        if(x > 0.6 && x < 0.8){
            g.drawImage(star4, (int)posx, (int)posy, null);
        }
        if(x > 0.8){
            g.drawImage(star5, (int)posx, (int)posy, null);
        }
    }


}
