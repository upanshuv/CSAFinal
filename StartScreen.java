import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StartScreen extends JPanel {
    private Image backgroundImage;

    public StartScreen(ActionListener startListener) {
        this.setBounds(0, 0, 500, 500); 
        this.setLayout(null);

        // Load background image
        backgroundImage = new ImageIcon("./images/background/cowboyEllis.jpeg").getImage();

        JButton startButton = new JButton("Start Game");
        startButton.setBounds(200, 250, 100, 40);
        startButton.addActionListener(startListener);
        this.add(startButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw background image, scaled to panel size
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}