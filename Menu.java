import java.awt.*;
import javax.swing.*;

public class Menu extends JPanel{
    
    public void render(Graphics g) {
        Image startImg = new ImageIcon(getClass().getResource("/img/StartInterface_Background.png")).getImage();
        g.drawImage(startImg, 0, 0, null);
    }
}