import java.awt.Graphics;
import java.awt.Graphics2D;

public abstract class Character{

    private boolean alive;
    protected Pair pos;
    protected Pair vel;
    protected Pair accel;

    public Character(){
        alive = true;
        pos = new Pair();
        vel = new Pair();
        accel = new Pair();
    }

    public abstract void draw(Graphics2D g);
}


