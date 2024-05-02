import java.awt.Image;
import java.awt.Rectangle;

public abstract class SpaceObject {

    private int radius;
    private Pair pos;
    private Pair vel;
    private Pair accel;
    private Image texture;
    private Rectangle bounds;
    
    public int getRadius(){
        return radius;
    }

    public Pair getPos(){
        return pos;
    }

    public Pair getVel(){
        return vel;
    }

    public Pair getAccel(){
        return accel;
    }

    public void setPos(Pair a){
        pos = a;
        bounds = new Rectangle((int)a.x, (int)a.y, radius, radius);
    }

    public void setVel(Pair v){
        vel = v;
    }

    public void setAccel(Pair a){
        accel = a;
    }

    public Pair getCenter(){
        return new Pair(texture.getWidth(null)/2+pos.x,texture.getHeight(null)/2+pos.y);
    }

    public Rectangle getBounds(){
        
        return bounds;
    }

    


} 
 
