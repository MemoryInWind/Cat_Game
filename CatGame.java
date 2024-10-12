import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class CatGame extends JPanel implements ActionListener, KeyListener{
    int panelWidth = 750;
    int panelHeight = 250;
    Image catImg;

    Timer timer;
    int jumpVelocity;
    int gravity = 1;
    boolean jumping = false;

    class Item {
        int x;
        int y;
        int width;
        int height;
        Image img;

        Item(int x, int y, int width, int height, Image img) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.img = img;

        }
    }

    //cat
    int catWidth = 88;
    int catHeight = 94;
    int catX = 50;
    int catY = panelHeight - catHeight;
    Item cat;

    public CatGame(){
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        setBackground(Color.lightGray);
        setFocusable(true);
        addKeyListener(this);

        //render inmage

        catImg = new ImageIcon(getClass().getResource("/img/dino-run.gif")).getImage();

        cat = new Item(catX, catY, catWidth, catHeight, catImg);

        //game loop
        timer = new Timer(1000/60, this);
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        //cat
        g.drawImage(cat.img, cat.x, cat.y, cat.width, cat.height, null);
    }

    public void move(){
        //cat jump
        if(jumping){
            cat.y += jumpVelocity;
            jumpVelocity += gravity;
            if(cat.y > catY){
                cat.y = catY;
                jumping = false;
            }

        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && !jumping) {
            jumping = true; 
            jumpVelocity = -17;
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
 
}
