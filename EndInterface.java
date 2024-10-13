import java.awt.*;
import javax.swing.*;

public class EndInterface extends JPanel{
    
    public void render(Graphics g) {
        Image endtImg = new ImageIcon(getClass().getResource("/img/EndInterface_Background.png")).getImage();
        g.drawImage(endtImg, 0, 0, null);
    }
}