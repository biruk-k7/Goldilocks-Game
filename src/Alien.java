//package Final;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class Alien extends Character {

        private boolean attacking;
        //make it move by itself by sort of AI
        public Alien(){
            super();
            attacking=false;
        }
        public void setPos(Pair a){
            pos=a;
        }
        public void setVel(Pair a){
            vel=a;
        }
        public void setAccel(Pair a){
            accel=a;
        }
        public void draw(Graphics2D g){
    
        }
        
}
