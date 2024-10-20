import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndInterface extends JPanel{
    private JButton retryButton;
    private JButton mainMenuButton;
    Image endImg;
    ImageIcon retryButtonImg;
    ImageIcon mainMenuButtonImg;
    
    public EndInterface() {
        endImg = new ImageIcon(getClass().getResource("/img/EndInterface_Background.png")).getImage();
        retryButtonImg = new ImageIcon(getClass().getResource("/img/RetryButton.png"));
        mainMenuButtonImg = new ImageIcon(getClass().getResource("/img/MainMenuButton.png"));
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

    public void render(Graphics g) {
        g.drawImage(endImg, 0, 0, null);
    }

    public void addButtons (JPanel panel) {
        panel.setLayout(null);
        panel.add(retryButton);
        panel.add(mainMenuButton);
    }
    public void setVisible(boolean Visible) {
        retryButton.setVisible(Visible);
        mainMenuButton.setVisible(Visible);
    }
    public void addActionListeners(CatGame game) {
        retryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("retry button clicked");
                game.retryGame();
            }
        });

        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Main Menu button clicked");
                game.showMainMenu();
            }
        });
    }


}
