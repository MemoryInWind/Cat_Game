import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * The tutorial screen. Displays a button that leads back to the main menu screen
 */
public class Tutorial extends JPanel {
    private JButton mainMenuButton;
    Image tutorialImg;
    ImageIcon mainMenuButtonImg;

    /**
     * Constructor for Tutorial class.
     */
    public Tutorial() {
        tutorialImg = new ImageIcon(getClass().getResource("/img/TutorialBackground.png")).getImage();
        mainMenuButtonImg = new ImageIcon(getClass().getResource("/img/BackButton.png"));
        //initialize mainMenuButton
        mainMenuButton = new JButton();
        mainMenuButton.setBounds(514, 490, 173, 60);
        mainMenuButton.setIcon(mainMenuButtonImg);
        mainMenuButton.setBorderPainted(false);
        mainMenuButton.setContentAreaFilled(false);
        mainMenuButton.setFocusPainted(false);

        setLayout(null);
    }

    /**
     * render background.
     * @param panel
     */
    public void render(Graphics g) {
        g.drawImage(tutorialImg, 0, 0, null);
    }
    
    /**
     * add the main menu button.
     * @param panel
     */
    public void addButtons(JPanel panel) {
        panel.setLayout(null);
        panel.add(mainMenuButton);
    }

    /**
     * set the visibility of button.
     */
    public void setVisible(boolean visible) {
        mainMenuButton.setVisible(visible);
    }

    /**
     * return to main menu screen after pressing the main menu button.
     */
    public void addActionListeners(CatGame game) {
        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.showMainMenu();
            }
        });
    }
}
