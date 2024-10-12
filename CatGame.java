import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class CatGame extends JPanel implements ActionListener, KeyListener{
    int panelWidth = 750;
    int panelHeight = 250;
    Image catImg;
    Image barrierImg1;
    Image barrierImg2;
    Image barrierImg3;

    Timer timer;
    Timer barrierTimer;
    int jumpVelocity;
    int gravity = 1;
    int barrierVelocity = -12;
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

    //barrier
    int barrier1Width = 34;
    int barrier2Width = 69;
    int barrier3Width = 102;
    int barrierHeight = 70;
    int barrierX = 700;
    int barrierY = panelHeight - barrierHeight;

    ArrayList<Item> barrierArray;

    public CatGame(){
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        setBackground(Color.lightGray);
        setFocusable(true);
        addKeyListener(this);

        //render inmage

        catImg = new ImageIcon(getClass().getResource("/img/dino-run.gif")).getImage();
        barrierImg1 = new ImageIcon(getClass().getResource("/img/cactus1.png")).getImage();
        barrierImg2 = new ImageIcon(getClass().getResource("/img/cactus2.png")).getImage();
        barrierImg3 = new ImageIcon(getClass().getResource("/img/cactus3.png")).getImage();
        barrierArray = new ArrayList<Item>();

        cat = new Item(catX, catY, catWidth, catHeight, catImg);

        //game loop
        timer = new Timer(1000/60, this);
        timer.start();

        barrierTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                generateBarrier();
            }
        });
        barrierTimer.start();
    }

    void generateBarrier() {
        double barrierChance = Math.random();
        if (barrierChance > 0.90) {
            Item barrier = new Item(barrierX, barrierY, barrier3Width, barrierHeight, barrierImg3);
            barrierArray.add(barrier);
        }
        else if (barrierChance > 0.70) {
            Item barrier = new Item(barrierX, barrierY, barrier2Width, barrierHeight, barrierImg2);
            barrierArray.add(barrier);
        }
        else if (barrierChance > 0.50) {
            Item barrier = new Item(barrierX, barrierY, barrier1Width, barrierHeight, barrierImg1);
            barrierArray.add(barrier);
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        //draw cat
        g.drawImage(cat.img, cat.x, cat.y, cat.width, cat.height, null);
        //draw barrier
        for (int i = 0; i < barrierArray.size(); i++){
            Item barrier = barrierArray.get(i);
            g.drawImage(barrier.img, barrier.x, barrier.y, barrier.width, barrier.height, null);
        }
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
        //barrier move
        for (int i = 0; i < barrierArray.size(); i++){
            Item barrier = barrierArray.get(i);
            barrier.x += barrierVelocity;

            if (barrier.x + barrier.width < 0) { //if move outside screen
                barrierArray.remove(i);
                i--; //adjust index
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
