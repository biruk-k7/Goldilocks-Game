import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public abstract class SpaceObject {

    public int radius;
    public  Pair pos;
    public Pair vel;
    public Pair accel;
    Rectangle bounds;
    Image texture;
    
    public int getRadius(){
        return radius;
    }

    public Pair getPos(){
        return pos;
    }

    public double getX(){
        return pos.x;
    }

    public double getY(){
       
        return pos.y;
    }

    public Pair getVel(){
        return vel;
    }

    public Pair getAccel(){
        return accel;
    }

    public void setPos(Pair a){
        pos = a;
        bounds.setLocation((int)a.x,(int)a.y);
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

    public Image getImage(){
        return texture;
    }

    public abstract void draw(Graphics g);
} 
 
