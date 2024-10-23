import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * The main menu at the start of the game. Displays button for game start and tutorial.    
 */
public class MainMenu extends JPanel{
    private JButton startButton;
    private JButton tutorialButton;
    Image startImg;
    ImageIcon tutorialButtonImg;
    ImageIcon startButtonImg;

    /**
     * Constructor for MainMenu Class.
     */
    public MainMenu() {
        startImg = new ImageIcon(getClass().getResource("/img/StartInterface_Background.png")).getImage();
        startButtonImg = new ImageIcon(getClass().getResource("/img/StartButton.png"));
        tutorialButtonImg = new ImageIcon(getClass().getResource("/img/TutorialButton.png"));

        //initialize start button
        startButton = new JButton();
        startButton.setBounds(515, 400, 170, 60);
        startButton.setIcon(startButtonImg);
        startButton.setBorderPainted(false);
        startButton.setContentAreaFilled(false);
        startButton.setFocusPainted(false);

        //initialize tutorial button
        tutorialButton = new JButton();
        tutorialButton.setBounds(482, 490, 235, 60);
        tutorialButton.setIcon(tutorialButtonImg);
        tutorialButton.setBorderPainted(false);
        tutorialButton.setContentAreaFilled(false);
        tutorialButton.setFocusPainted(false);

        setLayout(null);

    }

    /**
     * render background.
     * @param panel
     */
    public void render(Graphics g) {
        g.drawImage(startImg, 0, 0, null);


    }

    /**
     * add the start and tutorial buttons.
     * @param panel
     */
    public void addButtons(JPanel panel) {
        panel.setLayout(null);
        panel.add(startButton);
        panel.add(tutorialButton);
    }

    /**
     * set the visibility of buttons.
     */
    public void setVisible(boolean visible) {
        startButton.setVisible(visible);
        tutorialButton.setVisible(visible);
    }

    public void addActionListeners(CatGame game) {
        //start the game after pressing the start game button
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.startGame();
            }
        });

        //show the tutorial screen after pressing the tutorial button
        tutorialButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.showTutorial();
            }
        });
    }
}