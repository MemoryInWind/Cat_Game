import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Tutorial extends JPanel{
    private JButton mainMenuButton;
    Image tutorialImg;
    ImageIcon mainMenuButtonImg;
    
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


    public void render(Graphics g) {
        g.drawImage(tutorialImg, 0, 0, null);
    }

    public void addButtons (JPanel panel) {
        panel.setLayout(null);
        panel.add(mainMenuButton);
    }
    public void setVisible(boolean Visible) {
        mainMenuButton.setVisible(Visible);
    }
    public void addActionListeners(CatGame game) {

        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.showMainMenu();
            }
        });
    }


}
