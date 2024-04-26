public class Pair{
    /*
    a class to represent pairs of values such as:
        - coordinates
        - vectors
    */
    public double x;
    public double y;
     
    public Pair(double initX, double initY){
    	x = initX;
    	y = initY;
    }

    public Pair(){
        x = 0;
        y = 0;
    }
 
    public Pair add(Pair toAdd){
    	return new Pair(x + toAdd.x, y + toAdd.y);
    }
 
    public Pair divide(double denom){
    	return new Pair(x / denom, y / denom);
    }
 
    public Pair times(double val){
    	return new Pair(x * val, y * val);
    }
 
    public void flipX(){
    	x = -x;
    }
     
    public void flipY(){
    	y = -y;
    }
}