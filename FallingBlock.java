import java.awt.Rectangle;

public class FallingBlock {
    
    private int x; // X position of the block
    private int y; // Y position of the block
    private int width; // Width of the block
    private int height; // Height of the block
    private int speed; // Speed at which the block falls
    private Rectangle bounds; // Rectangle for collision detection
    private boolean active; // Whether the block is currently active (falling)

    /**
     * Constructor for the FallingBlock class.
     * @param x the initial x position of the block
     * @param y the initial y position of the block
     * @param width the width of the block
     * @param height the height of the block
     * @param speed the speed at which the block falls
     */

    public FallingBlock(int x, int y, int width, int height, int speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        bounds = new Rectangle(x, y, width, height);
        active = false; 
    }

    /** <enter>
     * sets the y position of the block.
     * @param y the new y position of the block
     */
    public void setY(int y) {
        this.y = y;
    }

    /** <enter>
     * sets the block as active or inactive
     * @param active whether the block is active (falling)
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /** <enter>
     * @return  whether or not the block is active (falling)
     */
    public boolean getActive() {
        return active;
    }

    /** <enter>
     * update the position of the block based on its speed.
     */
    public void update() {
        y += speed; // Move the block down by its speed
        bounds = new Rectangle(x, y, width, height); // Update bounds
    }

    /** <enter>
     * @return the bounds of the block for collision detection.
     */
    public Rectangle getBounds() {
        return bounds;
    }
}
