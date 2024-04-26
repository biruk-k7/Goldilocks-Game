public interface Movable{
  
    public abstract void update(Game g, double time);
    public abstract void move(int dir);
    public abstract void stopMove(int dir);
}