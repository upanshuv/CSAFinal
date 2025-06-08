import java.awt.Rectangle;

public class EnemyTrigger {

    private Rectangle bounds; // The bounds of the trigger
    private int x; // The x-coordinate of the trigger
    private int y; // The y-coordinate of the trigger
    private boolean isMercied; // Indicates if the enemy has been mercied

    public EnemyTrigger(int x, int y) {
        isMercied = false;
        this.x = x;
        this.y = y;
        this.bounds = new Rectangle(x, y, 50, 50); 
    }

    /** <enter>
     * Returns the bounds of the trigger.
     * @return the bounds of the trigger as a Rectangle object
     */
    public Rectangle getBounds() {
        return bounds;
    }

    /** <enter>
     * @return whether the enemy has been mercied
     */
    public boolean isMercied() {
        return isMercied;
    }

    /** <enter>
     * Sets the mercied status of the enemy.
     * @param mercied true if the enemy has been mercied, false otherwise
     */
    public void setMercied(boolean mercied) {
        isMercied = mercied;
    }

    /** <enter>
     * Checks if this trigger has collided with another game object.
     * @param other the other game object to check for collision
     * @return true if this trigger has collided with the other game object, false otherwise
     */
    public boolean hasCollided(GameObject other) {
        try {
            if (this.getBounds().intersects(other.getBounds())) {
                return true;
            } else {
                return false;
            }
        } catch (NullPointerException e) {
            return false;
        }
    }
}
