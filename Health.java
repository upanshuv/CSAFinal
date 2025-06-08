import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Health {
    private Rectangle bounds; // Rectangle to represent the health bar's bounds
    private int x; // X coordinate of the health bar
    private int y; // Y coordinate of the health bar
    private int health; // Current health value
    private ImageIcon image; // ImageIcon to hold the health bar image

    /** <enter>
     * Constructor to initialize the health bar at specified coordinates.
     * 
     * @param x X coordinate of the health bar.
     * @param y Y coordinate of the health bar.
     */

    public Health(int x, int y) {
        this.x = x;
        this.y = y;
        this.bounds = new Rectangle(x, y, 180, 45); // Adjust width and height as needed
        this.health = 10; // Default health value

        try {
            image = new ImageIcon("images/healthbar/healthbar-start.png"); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** <enter>
     * Returns the current health bar image.
     * 
     * @return Image of the health bar.
     */
    public Image getImage(){
        return image.getImage(); // Return the current health bar image
    }

    /** <enter>
     * Returns the bounds of the health bar.
     * 
     * @return Rectangle representing the bounds of the health bar.
     */
    public Rectangle getBounds() {
        bounds.setBounds(x,y,200,800);
        return bounds;
    }

    /** <enter>
     * Sets the health value and updates the health bar image accordingly.
     * 
     * @param health New health value.
     */
    public void setHealth(int health) {
        this.health = health;
        updateIcon(); // Update the image whenever health changes
    }

    /** <enter>
     * Returns the current health value.
     * 
     * @return Current health value.
     */
    public int getHealth() {
        return health;
    }

    /** <enter>
     * Updates the health bar image based on the current health value.
     * The image file is determined by the health value (e.g., healthbar-10.png for full health).
     */
    public void updateIcon() {
        // Determine the image file based on health
        String imagePath = "images/healthbar/healthbar-" + health + ".png";

        try {
            image = new ImageIcon(imagePath); // Load the new image
            System.out.println("Health bar updated to: " + imagePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** <enter>
     * Returns the image of the health bar.
     * 
     * @return Image of the health bar.
     */
    public Image getBar() {
        return image.getImage();
    }

    /** <enter>
     * Returns the X and Y coordinates of the health bar repsectively.
     * 
     * @return X and Y coordinates as an integer
     */
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}