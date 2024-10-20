import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.w3c.dom.events.MouseEvent;

public class Menu extends JPanel{
    private JButton startButton;
    private JButton tutorialButton;
    Image startImg;
    ImageIcon tutorialButtonImg;
    ImageIcon startButtonImg;

    public Menu(){
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

    
    
    public void render(Graphics g) {
        g.drawImage(startImg, 0, 0, null);
        System.out.println("Rendering Menu...");

    }
    public void addButtons (JPanel panel) {
        panel.setLayout(null);
        panel.add(startButton);
        panel.add(tutorialButton);
    }

    public void setVisible(boolean Visible) {
        startButton.setVisible(Visible);
        tutorialButton.setVisible(Visible);
    }

    public void addActionListeners(CatGame game) {
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Start button clicked");
                game.startGame();
            }
        });

        tutorialButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Tutorial button clicked");
                game.showTutorial();
            }
        });
    }


}