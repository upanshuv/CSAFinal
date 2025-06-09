
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Mercy {
    private Rectangle bounds; // Rectangle to define the bounds of the Mercy health bar
    private int x; // x-coordinate of the Mercy health bar
    private int y;// y-coordinate of the Mercy health bar
    private int mercy; // Current health value of the Mercy health bar
    private ImageIcon image; // ImageIcon to hold the current health bar image

    /** <enter>
     * Constructor for Mercy class.
     * Initializes the position, bounds, and default health value.
     * Loads the initial image for the health bar.
     *
     * @param x The x-coordinate of the Mercy health bar.
     * @param y The y-coordinate of the Mercy health bar.
     */

    public Mercy(int x, int y) {
        this.x = x;
        this.y = y;
        this.bounds = new Rectangle(x, y, 180, 45); // Adjust width and height as needed
        this.mercy = 5; // Default health value

        try {
            image = new ImageIcon("images/mercybar/mercybar-start.png"); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** <enter>
     * Returns the current image of the Mercy health bar.
     *
     * @return The current health bar image.
     */
    public Image getImage(){
        return image.getImage(); // Return the current health bar image
    }

    /** <enter>
     * Returns the bounds of the Mercy health bar.
     *
     * @return The rectangle defining the bounds of the health bar.
     */
    public Rectangle getBounds() {
        bounds.setBounds(x,y,200,800);
        return bounds;
    }

    /** <enter>
     * Sets the current health value of the Mercy health bar.
     * Updates the image based on the new health value.
     *
     * @param mercy The new health value to set.
     */
    public void setMercy(int mercy) {
        this.mercy = mercy;
        updateIcon(); // Update the image whenever health changes
    }

    /** <enter>
     * Returns the current health value of the Mercy health bar.
     *
     * @return The current health value.
     */
    public int getMercy() {
        return mercy;
    }

    /** <enter>
     * Updates the image of the Mercy health bar based on the current health value.
     * The image file is determined by the health value.
     */
    public void updateIcon() {
        // Determine the image file based on health
        
        String imagePath = "images/mercybar/mercybar-" + mercy + ".png";

        try {
            image = new ImageIcon(imagePath); // Load the new image
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** <enter>
     * Returns the current image of the Mercy health bar.
     *
     * @return The current health bar image.
     */
    public Image getBar() {
        return image.getImage();
    }

    /** <enter>
     * Returns the x-coordinate of the Mercy health bar.
     *
     * @return The x-coordinate.
     */
    public int getX() {
        return x;
    }

    /** <enter>
     * Returns the y-coordinate of the Mercy health bar.
     *
     * @return The y-coordinate.
     */
    public int getY() {
        return y;
    }

    /** <enter>
     * Resets the Mercy health bar to its initial state.
     * Sets the health value to 5 and updates the image to the start image.
     */
    public void setStartImage(){
        try {
            image = new ImageIcon("images/mercybar/mercybar-start.png"); // Reset to start image
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

