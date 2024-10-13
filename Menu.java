import java.awt.*;
import javax.swing.*;

public class Menu extends JPanel{
    
    public void render(Graphics g) {
        Image startAndEndImg = new ImageIcon(getClass().getResource("/img/StartInterface_Background.png")).getImage();
        g.drawImage(startAndEndImg, 0, 0, null);
    }
}
