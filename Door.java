import java.awt.Rectangle;

public class Door{

    private Rectangle bounds; // The bounds of the door
    private String key; // the room the door leads to
    private String sideOfRoom; //the side of the room the door is on

    public Door(String key, String sideOfRoom) {
        this.key = key;
        this.sideOfRoom = sideOfRoom;
        if (sideOfRoom.equals("right") || sideOfRoom.equals("left")) {
            this.bounds = new Rectangle(0, 0, 5, 400); // default size of door
        } 
        else if (sideOfRoom.equals("up") || sideOfRoom.equals("down")) {
            this.bounds = new Rectangle(0, 0, 400, 5); // default size of door
        }
    }

    /** <enter> * Returns the bounds of the door.
     * @return Rectangle representing the bounds of the door.
     */
    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * <enter> Returns the side of the room where the door is located.
     * @return String representing the side of the room.
     */
    public String getSideOfRoom() {
        return sideOfRoom;
    }
    
    /** <enter> * Returns the key associated with the door.
     * @return String representing the key of the door.
     */
    public String getKey() {
        return key;
    }

    /** <enter> * Sets the position of the door in the game world.
     * @param x The x-coordinate of the door's position.
     * @param y The y-coordinate of the door's position.
     */
    public void setPosition(int x, int y) {
        bounds.setLocation(x, y); 
    }

    /** <enter> * Checks if the door has collided with another game object.
     * @param other The other game object to check for collision.
     * @return boolean indicating whether a collision has occurred.
     */
    public boolean hasCollided(GameObject other){
        try {
            if (this.getBounds().intersects(other.getBounds())){
                return true;
            }
            else{
                return false;
            }
                
        } catch (NullPointerException e) {
            return false;
        }
    }
    
    
}

