import java.awt.*;
import javax.swing.*;
public class App {
    public static void main(String[] args) throws Exception {
        int frameWidth = 750;
        int frameHeight = 250;

        JFrame frame = new JFrame("Cat Game");
        frame.setVisible(true);
        frame.setSize(frameWidth, frameHeight);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}