import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class TenzinEnemy {

    private ArrayList<ImageIcon> idleImages; // List to hold images
    private Rectangle bounds; // Rectangle for enemy rendering
    private int x; // x-coordinate of the enemy
    private int y; // y-coordinate of the enemy
    private int timerCount; // Timer to control image updates
    private int i; // Index for the current image


    /** <enter> * TenzinEnemy constructor initializes the enemy with default values.
     * It sets the initial position, initializes the image list, and sets the bounds.
     */    
    public TenzinEnemy() {
        x = 100;
        y = 100;
        i = 0;
        timerCount = 0;
        bounds = new Rectangle(x, y, 50, 50); // default size of enemy
        idleImages = new ArrayList<>();
        idleImages.add(new ImageIcon("./images/Enemies/TenzinEnemy/Idle0.png"));
    }

    /** <enter> * 
     * Returns the current bounds of the enemy.
     * @return Rectangle representing the bounds of the enemy.
     */
    public Rectangle getBounds() {
        return bounds;
    }

    /** <enter> * 
     * updates the enemy's state, including the image based on a timer.
     * This method is called every frame to update the enemy's appearance.
     * this method was not implemented
     */
    public void update(){
        timerCount++;
        if (timerCount % 10 == 0) {
            // Update the image every 10 frames
            // For now, just keep it as the first image
            if (i < idleImages.size() - 1) {
                i++;
            } else {
                i = 0; // Loop back to the first image
            }
        }
    }

    /** <enter> * 
     * Returns the current image of the enemy.
     * @return ImageIcon representing the current image of the enemy.
     */
    public ImageIcon getImage() {
        return idleImages.get(0); // Return the first image for now
    }


    
}
