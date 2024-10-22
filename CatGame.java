import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class CatGame extends JPanel implements ActionListener, KeyListener{
    int panelWidth = 1200;
    int panelHeight = 600;
    Image catImg;
    Image catDeadImg;
    Image barrierImg1;
    Image barrierImg2;
    Image barrierImg3;
    Image bulletImg;
    Image rewardImg;
    Image scoreFrameImg;

    Menu menu;
    EndInterface endInterface;
    Tutorial tutorial;

    Timer timer;
    Timer barrierTimer;
    Timer rewardTimer;
    Timer endGameTimer;
    int jumpVelocity;
    int maxJumpPosition;
    int gravity = 1;
    int barrierVelocity = -18;
    int bulletVelocity = 15;
    boolean jumping = false;

    // State
    public static enum State {
        MENU,
        GAME,
        END,
        TUTORIAL
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
    int catWidth = 130;
    int catHeight = 100;
    int catX = 40;
    int catY = panelHeight - catHeight - 10;
    Item cat;

    //barrier
    int barrier1Width = 76;
    int barrier1Height = 100;
    int barrier2Width = 143;
    int barrier2Height = 100;
    int barrier3Width = 183;
    int barrier3Height = 116;
    int barrierX = 1200;
    int barrier1Y = panelHeight - barrier1Height - 10;
    int barrier2Y = panelHeight - barrier2Height - 10;
    int barrier3Y = panelHeight - barrier3Height - 10;

    //bullet
    Item bullet;
    int bulletWidth = 40;
    int bulletHeight = 40;
    int bulletX = catX + catWidth;

    //cool
    long lastBulletTime = 0;
    int coolDown = 500;

    //reward
    Item reward;
    int rewardWidth = 48;
    int rewardHeight = 47;
    int rewardX = 1200;
    int rewardY;

    //score frame
    Item scoreFrame;
    int frameWidth = 305;
    int frameHeight = 50;
    int frameX = 850;
    int frameY = 10;


    ArrayList<Item> barrierArray;
    ArrayList<Item> bulletArray;
    ArrayList<Item> rewardArray;

    boolean gameOver = false;
    int score = 0;



    public CatGame(){
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        setBackground(Color.lightGray);
        setFocusable(true);
        addKeyListener(this);

        //render image

        catImg = new ImageIcon(getClass().getResource("/img/CatRunning.gif")).getImage();
        catDeadImg = new ImageIcon(getClass().getResource("/img/CatDead.png")).getImage();
        barrierImg1 = new ImageIcon(getClass().getResource("/img/BarrierFireHydrant.png")).getImage();
        barrierImg2 = new ImageIcon(getClass().getResource("/img/BarrierBench.png")).getImage();
        barrierImg3 = new ImageIcon(getClass().getResource("/img/BarrierBike.png")).getImage();
        bulletImg = new ImageIcon(getClass().getResource("/img/Bullet.png")).getImage();
        rewardImg = new ImageIcon(getClass().getResource("/img/FishReward.png")).getImage();
        scoreFrameImg = new ImageIcon(getClass().getResource("/img/ScoreFrame-50.png")).getImage();

        barrierArray = new ArrayList<Item>();
        bulletArray = new ArrayList<Item>();
        rewardArray = new ArrayList<Item>();

        //initialize menu
        menu = new Menu();
        menu.addButtons(this); 
        menu.addActionListeners(this);
        //initialize end interface
        endInterface = new EndInterface();
        endInterface.addButtons(this);
        endInterface.addActionListeners(this);
        endInterface.setVisible(false);
        this.add(endInterface);
        //initialize tutorial
        tutorial = new Tutorial();
        tutorial.addButtons(this);
        tutorial.addActionListeners(this);
        tutorial.setVisible(false);
        this.add(tutorial);

        this.repaint();

        cat = new Item(catX, catY, catWidth, catHeight, catImg);

        scoreFrame = new Item(frameX, frameY, frameWidth, frameHeight, scoreFrameImg);

        //determine max position
        maxJumpPosition= catY-(jumpVelocity * jumpVelocity) /(2 * gravity) - catHeight;

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

        endGameTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                state= State.END;
                endInterface.setVisible(true);
                repaint();
                endGameTimer.stop();
            }
        });
        endGameTimer.setRepeats(false);
    }

    //start the game after clicking play
    void startGame() {
        state = State.GAME;
        menu.setVisible(false); 
        timer.start();
        barrierTimer.start();
        rewardTimer.start();
    }

    //show the tutorial
    void showTutorial() {
        menu.setVisible(false); 
        tutorial.setVisible(true);
        state = State.TUTORIAL;
        repaint();
    }

    void resetGame(){
        gameOver = false;
        cat.img = catImg;
        score = 0;
        barrierArray.clear();
        rewardArray.clear();
        bulletArray.clear();
    }

    void retryGame() {
        resetGame();
        state = State.GAME;
        //hide endInterface 
        endInterface.setVisible(false);
        //start new game loop
        timer.start();
        barrierTimer.start();
        rewardTimer.start();
        this.repaint();
    }

    void showMainMenu(){
        resetGame();
        endInterface.setVisible(false);
        tutorial.setVisible(false);
        state = State.MENU;
        menu.setVisible(true);
        repaint();
    }

    void generateBarrier() {
        if (gameOver) {
            return;
        }
        double barrierChance = Math.random();
        if (barrierChance > 0.90) {
            Item barrier = new Item(barrierX, barrier3Y, barrier3Width, barrier3Height, barrierImg3);
            barrierArray.add(barrier);
        }
        else if (barrierChance > 0.70) {
            Item barrier = new Item(barrierX, barrier2Y, barrier2Width, barrier2Height, barrierImg2);
            barrierArray.add(barrier);
        }
        else if (barrierChance > 0.50) {
            Item barrier = new Item(barrierX, barrier1Y, barrier1Width, barrier2Height, barrierImg1);
            barrierArray.add(barrier);
        }
    }

    void generateReward() {
        if (gameOver) {
            return;
        }
        double rewardChance = Math.random();
        
        if (rewardChance > 0.1) {
            rewardY = maxJumpPosition + (int) (Math.random() * (catY - rewardHeight -maxJumpPosition));
            Item reward = new Item(rewardX, rewardY, rewardWidth, rewardHeight, rewardImg);
            rewardArray.add(reward);
        }

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        //according to current state 
        if (state == State.GAME || endGameTimer.isRunning()) {
            draw(g);
        } else if (state == State.MENU) {
            menu.render(g);
        } else if (state == State.END) {
            endInterface.render(g);
        } else if (state == State.TUTORIAL) {
            tutorial.render(g);
        }
    }

    public void draw(Graphics g){
        //draw background
        Image startImg = new ImageIcon(getClass().getResource("/img/1200-600.png")).getImage();
        g.drawImage(startImg, 0, 0, null);
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
        //draw cat
        g.drawImage(cat.img, cat.x, cat.y, cat.width, cat.height, null);
        //draw score frame
        g.drawImage(scoreFrame.img, scoreFrame.x, scoreFrame.y, scoreFrame.width, scoreFrame.height, null);

        //draw score
        g.setFont(new Font("Arial",Font.BOLD,36));
        g.setColor(Color.BLACK);
        String scoreText = "" + score;
        g.drawString(scoreText, panelWidth - 200,48);
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
            endInterface.setFinalScore(score);
            endGameTimer.start();
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && !jumping) {
            jumping = true; 
            jumpVelocity = -18;
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
