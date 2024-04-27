import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.util.Random;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.RenderingHints;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.GradientPaint;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;


public class Background {
    
    private int WIDTH;
    private int HEIGHT;

    public Background(int worldWidth, int worldHeight){
        this.HEIGHT = worldHeight;
        this.WIDTH = worldWidth;
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


}
