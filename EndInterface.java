import java.awt.*;
import javax.swing.*;

public class EndInterface extends JPanel{
    
    public void render(Graphics g) {
        Image endImg = new ImageIcon(getClass().getResource("/img/EndInterface_Background.png")).getImage();
        g.drawImage(endImg, 0, 0, null);
    }
}