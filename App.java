import javax.swing.*;

/**
 * Set up the game window and start the game.
 */
public class App {
    public static void main(String[] args) throws Exception {
        //set demensions of the window
        int frameWidth = 1200;
        int frameHeight = 600;

        JFrame frame = new JFrame("Cat Game"); //window title
        frame.setVisible(true);
        frame.setSize(frameWidth, frameHeight);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //create a new instance of the CatGame class
        CatGame catGame = new CatGame();
        frame.add(catGame);
        frame.pack();
        catGame.requestFocus();
        frame.setVisible(true);
    }
}