public class Camera implements Movable{

    public Pair position, velocity;


    public Camera(Pair pos){

        position = pos;

        velocity = new Pair();


    }

    public void setPos(Pair a){
        position=a;
    }

   public void move(int dir){

    
        switch (dir){
            case 0:
                
                velocity.y=-100;
                break;
            case 1:
                velocity.x=-100;
                break;
            case 2:
                velocity.y=100;
                break;
            case 3:
                velocity.x=100;
                
        }
    
    }

        public void stopMove(int dir){
        switch (dir){
            case 0:             
                velocity.y=0;
                break;
            case 1:
                velocity.x=0;
                break;
            case 2:
                velocity.y=0;
                break;
            case 3:
                velocity.x=0;    
        }
    }

    public void update(Game g, double time){
        
        position = position.add(velocity.times(time));
    
    }


}