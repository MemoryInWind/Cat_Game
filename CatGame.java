import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class CatGame extends JPanel implements ActionListener, KeyListener{
    int panelWidth = 750;
    int panelHeight = 250;
    Image catImg;
    Image catDeadImg;
    Image barrierImg1;
    Image barrierImg2;
    Image barrierImg3;
    Image bulletImg;
    Image rewardImg;

    Timer timer;
    Timer barrierTimer;
    Timer rewardTimer;
    int jumpVelocity;
    int maxJumpPosition;
    int gravity = 1;
    int barrierVelocity = -12;
    int bulletVelocity = 10;
    boolean jumping = false;

    // State
    public static enum State {
        MENU,
        GAME,
        END
    };

    public static State state = State.MENU;

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

    //bullet
    Item bullet;
    int bulletWidth = 20;
    int bulletHeight = 20;
    int bulletX = catX + catWidth;

    //cool
    long lastBulletTime = 0;
    int coolDown = 500;

    //reward
    Item reward;
    int rewardWidth = 30;
    int rewardHeight = 30;
    int rewardX = 700;
    int rewardY;

    ArrayList<Item> barrierArray;
    ArrayList<Item> bulletArray;
    ArrayList<Item> rewardArray;

    boolean gameOver = false;
    int score = 0;

    //button
    JButton startButton = new JButton();
    JButton tutorialButton = new JButton();

    public CatGame(){
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        setBackground(Color.lightGray);
        setFocusable(true);
        addKeyListener(this);

        //render image

        catImg = new ImageIcon(getClass().getResource("/img/dino-run.gif")).getImage();
        catDeadImg = new ImageIcon(getClass().getResource("/img/dino-dead.png")).getImage();
        barrierImg1 = new ImageIcon(getClass().getResource("/img/cactus1.png")).getImage();
        barrierImg2 = new ImageIcon(getClass().getResource("/img/cactus2.png")).getImage();
        barrierImg3 = new ImageIcon(getClass().getResource("/img/cactus3.png")).getImage();
        bulletImg = new ImageIcon(getClass().getResource("/img/dino.png")).getImage();
        rewardImg = new ImageIcon(getClass().getResource("/img/dino.png")).getImage();

        barrierArray = new ArrayList<Item>();
        bulletArray = new ArrayList<Item>();
        rewardArray = new ArrayList<Item>();

        ImageIcon startButtonImg = new ImageIcon(getClass().getResource("/img/StartButton.png"));
        ImageIcon tutorialButtonImg = new ImageIcon(getClass().getResource("/img/TutorialButton.png"));

        //start button for the menu
        startButton.setBounds(320, 150, 100, 40);
        startButton.setIcon(startButtonImg);
        //remove border, background and outline of the button
        startButton.setBorderPainted(false);
        startButton.setContentAreaFilled(false);
        startButton.setFocusPainted(false);
        startButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                System.out.println("Start button clicked");
                state = State.GAME;
                startButton.setVisible(false);
                tutorialButton.setVisible(false);
                //start the game
                timer.start();
                barrierTimer.start();
                rewardTimer.start();
            }
        });

        //tutorial button for the menu
        tutorialButton.setBounds(295, 200, 150, 40);
        tutorialButton.setIcon(tutorialButtonImg);
        //remove border, background and outline of the button
        tutorialButton.setBorderPainted(false);
        tutorialButton.setContentAreaFilled(false);
        tutorialButton.setFocusPainted(false);
        tutorialButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                System.out.println("Tutorial");
                startButton.setVisible(false);
                tutorialButton.setVisible(false);

            }
        });

        setLayout(null);
        add(startButton);
        add(tutorialButton);

        cat = new Item(catX, catY, catWidth, catHeight, catImg);

        //determine max position
        maxJumpPosition= catY-(jumpVelocity * jumpVelocity) /(2*gravity);

        //game loop
        timer = new Timer(1000/60, this);
        

        barrierTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                generateBarrier();
            }
        });


        rewardTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                generateReward();
            }
        });

    }

    void generateBarrier() {
        if (gameOver) {
            return;
        }
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

    void generateReward() {
        if (gameOver) {
            return;
        }
        double rewardChance = Math.random();
        
        if (rewardChance > 0.20) {
            rewardY = maxJumpPosition + (int) (Math.random() * (panelHeight - rewardHeight -maxJumpPosition));
            Item reward = new Item(rewardX, rewardY, rewardWidth, rewardHeight, rewardImg);
            rewardArray.add(reward);
        }

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Menu menu = new Menu();
        
        if (state == State.GAME) {
            draw(g);
        } else if (state == State.MENU) {
            menu.render(g);
        }
    }

    public void draw(Graphics g){
        //draw cat
        g.drawImage(cat.img, cat.x, cat.y, cat.width, cat.height, null);
        //draw barrier
        for (int i = 0; i < barrierArray.size(); i++){
            Item barrier = barrierArray.get(i);
            g.drawImage(barrier.img, barrier.x, barrier.y, barrier.width, barrier.height, null);
        }
        //draw bullet
        for (int i = 0; i < bulletArray.size(); i++){
            Item bullet = bulletArray.get(i);
            g.drawImage(bullet.img, bullet.x, bullet.y, bullet.width, bullet.height, null);
        }
        //draw reward
        for (int i = 0; i < rewardArray.size(); i++){
            Item reward = rewardArray.get(i);
            g.drawImage(reward.img, reward.x, reward.y, reward.width, reward.height, null);
        }
    }

    public void move(){
        //cat jump
        if (jumping) {
            cat.y += jumpVelocity;
            jumpVelocity += gravity;
            if (cat.y > catY) {
                cat.y = catY;
                jumping = false;
            }

        }
        //barrier move
        for (int i = 0; i < barrierArray.size(); i++){
            Item barrier = barrierArray.get(i);
            barrier.x += barrierVelocity;

            if (collision(cat, barrier)) {
                gameOver = true;
                cat.img = catDeadImg;
            }

            if (barrier.x + barrier.width < 0) { //if move outside screen
                barrierArray.remove(i);
                i--; //adjust index
            }
        }

        //bullet move
        for (int i = 0; i < bulletArray.size(); i++) {
            Item bullet = bulletArray.get(i);
            bullet.x += bulletVelocity;

            if (bullet.x > panelWidth / 3) {
                bulletArray.remove(i);
                i--;
            }
            for (int j = 0; j < barrierArray.size(); j++) {
                Item barrier = barrierArray.get(j);
                if (collision(bullet, barrier)){
                    barrierArray.remove(j);
                    bulletArray.remove(i);
                    i--;
                }
            }
        }
        //reward
        for (int i = 0; i < rewardArray.size(); i++) {
            Item reward = rewardArray.get(i);
            reward.x += barrierVelocity;
            for (int j = 0; j < barrierArray.size(); j++) {
                Item barrier = barrierArray.get(j);
                if (collision(reward, barrier)){
                    rewardArray.remove(i);
                    i--;
                    break;
                }
            }
            if (reward.x + reward.width < 0) {
                rewardArray.remove(i);
                i--; //adjust index
            }
            if (collision(reward, cat)) {
                score += 100;
                rewardArray.remove(i);
                i--;
            }
        }
        score++;
    }

    boolean collision(Item itemA,Item itemB){
        return itemA.x < itemB.x + itemB.width &&
           itemA.x + itemA.width > itemB.x &&
           itemA.y < itemB.y + itemB.height &&
           itemA.y + itemA.height > itemB.y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver) {
            barrierTimer.stop();
            timer.stop();
            rewardTimer.stop();
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && !jumping) {
            jumping = true; 
            jumpVelocity = -17;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastBulletTime >= coolDown){
                Item bullet = new Item(bulletX, cat.y+(cat.height/2), bulletWidth, bulletHeight, bulletImg);
                bulletArray.add(bullet);
                lastBulletTime = currentTime;

            }
        }
        }
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
 
}
