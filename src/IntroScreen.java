import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Font;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;


public class IntroScreen extends JPanel implements MouseListener {

    private int width;
    private int height;
    private Image bacImage;
    private Image star2;

    public Color menuButton1 = Color.white;
    public Color menuButton2 = Color.white;

    int buttonx1;
    int buttony1;
    int buttonx2;
    int buttony2;
    double widthBox;
    int heightBox;

    public RoundRectangle2D playButton;
    public RoundRectangle2D quitButton;

    public IntroScreen(int WIDTH, int HEIGHT){
        width = WIDTH;
        height = HEIGHT;

        buttonx1 =  width/2 - 100;
        buttony1 =  height/2 + 100;
        buttonx2 =  width/2 - 100;
        buttony2 =  height/2 + 200;
        widthBox = 2*100;
        heightBox = 50;
        playButton = new RoundRectangle2D.Double(buttonx1, buttony1, widthBox, heightBox, 20, 50);
        quitButton = new RoundRectangle2D.Double(buttonx2, buttony2,  widthBox, heightBox, 20, 50);
        bacImage = new ImageIcon("assets\\IntroScreen\\GOLDILOCKS.png").getImage();
        star2 = new ImageIcon("assets\\stars\\star2.png").getImage();
        star2 = star2.getScaledInstance(30, 30, Image.SCALE_DEFAULT);
    }

    public void updateIntroScreen(){
        playButton = new RoundRectangle2D.Double(buttonx1, buttony1, widthBox, heightBox, 20, 50);
    }

    public void draw(Graphics g){
        g.setColor(Color.black);

        g.drawImage(bacImage, 0, 0, null);

        String fontFile = "GameFont.ttf";
        Font customFont = null;
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new java.io.File(fontFile)).deriveFont((float) 30);

        } catch (Exception e) {
            customFont = new Font("Arial", Font.PLAIN, 50); 
        }

        g.setFont(customFont);

        Graphics2D g2d = (Graphics2D) g;
    
        g.setFont(customFont);
        g.setColor(Color.white);
        

        g.setFont(customFont);
        g2d.setColor(menuButton1);
        g2d.draw(playButton);
        g2d.setColor(Color.white);
        g.drawString("Play", width/2 - 137,  height/2 + 133);
        g2d.setColor(menuButton2);
        g2d.draw(quitButton);
        g.setColor(Color.white);
        g.drawString("Quit", width/2 - 137, height/2 + 233);

        //debugging tools
        // g.setColor(Color.PINK);
        // g.drawLine(width/2 - 100, 0, width/2 - 100, height);
        // g.drawLine(0, height/2, width, height/2);
        //end debugging


    }

    













    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseClicked'");
    }
    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mousePressed'");
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseReleased'");
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseEntered'");
    }
    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseExited'");
    }


    
}
