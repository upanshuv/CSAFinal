import javax.swing.*;

public class Animation {
    private static JFrame frame;
    private static GamePanel gamePanel;

    public static void main(String[] args) {
        frame = new JFrame("Undercoooked"); 
        frame.setBounds(50, 50, 500, 500);
        frame.setResizable(false);

        gamePanel = new GamePanel();

        StartScreen startScreen = new StartScreen(e -> {
            frame.add(gamePanel);
            frame.revalidate();
            frame.repaint();
            gamePanel.requestFocusInWindow();
        });

        frame.add(startScreen);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Call this from GamePanel when game ends
    public static void showEndScreen(boolean win) {
        SwingUtilities.invokeLater(() -> {
            frame.getContentPane().removeAll();
            frame.add(new EndScreen(win));
            frame.revalidate();
            frame.repaint();
        });
    }
}