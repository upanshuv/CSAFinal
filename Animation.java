import javax.swing.JFrame;


public class Animation {

    private boolean startScreenDone = false; // used for satrting the actual game

    /**
     * main method which instantiates our JFrame and
     * our gamePanel.   
     */

    public static void main(String[] args) {
        
        JFrame frame = new JFrame("Undercoooked"); 
        frame.setBounds(50, 50, 500, 500);

        frame.setResizable(false);

        GamePanel gamePanel = new GamePanel();
        

        frame.add(gamePanel);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
