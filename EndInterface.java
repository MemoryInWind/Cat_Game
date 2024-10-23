import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * The end screen after game end. Displays the final score and two buttons retry and main menu. 
 */
public class EndInterface extends JPanel {
    private JButton retryButton;
    private JButton mainMenuButton;
    Image endImg;
    ImageIcon retryButtonImg;
    ImageIcon mainMenuButtonImg;
    Image endScoreFrameImg;
    int finalScore;

    /**
     * The constructor for EndInterface class.
     */
    public EndInterface() {
        endImg = new ImageIcon(getClass().getResource("/img/EndInterface_Background.png")).getImage();
        retryButtonImg = new ImageIcon(getClass().getResource("/img/RetryButton.png"));
        mainMenuButtonImg = new ImageIcon(getClass().getResource("/img/MainMenuButton.png"));
        endScoreFrameImg = new ImageIcon(getClass().getResource("/img/ScoreFrame-60.png")).getImage();
        //initialize retryButton
        retryButton = new JButton();
        retryButton.setBounds(516, 400, 169, 60);
        retryButton.setIcon(retryButtonImg);
        retryButton.setBorderPainted(false);
        retryButton.setContentAreaFilled(false);
        retryButton.setFocusPainted(false);
        //initialize mainMenuButton
        mainMenuButton = new JButton();
        mainMenuButton.setBounds(456, 490, 289, 60);
        mainMenuButton.setIcon(mainMenuButtonImg);
        mainMenuButton.setBorderPainted(false);
        mainMenuButton.setContentAreaFilled(false);
        mainMenuButton.setFocusPainted(false);

        setLayout(null);
    }

    /**
     * pass the score from CatGame to EndInterface.
     * @param score the final score in the game
     */
    public void setFinalScore(int score) {
        this.finalScore = score;
    }

    /**
     * render background and score.
     * @param panel
     */
    public void render(Graphics g) {
        g.drawImage(endImg, 0, 0, null);
        g.drawImage(endScoreFrameImg, 417, 290, null);
        //draw score
        g.setFont(new Font("Arial", Font.BOLD, 46));
        g.setColor(Color.BLACK);
        g.drawString("" + finalScore, 590, 337);
    }

    /**
     * add the retry and main menu buttons.
     * @param panel
     */
    public void addButtons(JPanel panel) {
        panel.setLayout(null);
        panel.add(retryButton);
        panel.add(mainMenuButton);
    }

    /**
     * set the visibility of buttons.
     */
    public void setVisible(boolean visible) {
        retryButton.setVisible(visible);
        mainMenuButton.setVisible(visible);
    }

    /**
     * behavior of the buttons.
     * @param game the instance of the Catgame
     */
    public void addActionListeners(CatGame game) {
        retryButton.addActionListener(new ActionListener() {
            @Override
            //start a new instance of the game after pressing retry button
            public void actionPerformed(ActionEvent e) {
                game.retryGame();
            }
        });

        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            //show main menu screen after pressing the main menu button
            public void actionPerformed(ActionEvent e) {
                game.showMainMenu();
            }
        });
    }
}
