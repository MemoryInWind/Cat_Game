import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class CatGame extends JPanel {
    int panelWidth = 750;
    int panelHeight = 250;
    Image catImg;

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

        catImg = new ImageIcon(getClass().getResource("/img/dino.png")).getImage();

        cat = new Item(catX, catY, catWidth, catHeight, catImg);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        //cat
        g.drawImage(cat.img, cat.x, cat.y, cat.width, cat.height, null);
    }
 
}
