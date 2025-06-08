import javax.swing.*;
import java.awt.*;

public class EndScreen extends JPanel {
    private Image endImage;

    public EndScreen(boolean win) {
        // Use different images for win/lose
        if (win) {
            endImage = new ImageIcon("./images/background/winendscreen.png").getImage();
        } else {
            endImage = new ImageIcon("./images/background/deadendscreen.png").getImage();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (endImage != null) {
            g.drawImage(endImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
