import java.awt.*;
import javax.swing.*;
public class App {
    public static void main(String[] args) throws Exception {
        int frameWidth = 1200;
        int frameHeight = 600;

        JFrame frame = new JFrame("Cat Game");
        frame.setVisible(true);
        frame.setSize(frameWidth, frameHeight);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        CatGame catGame = new CatGame();
        frame.add(catGame);
        frame.pack();
        catGame.requestFocus();
        frame.setVisible(true);
    }
}