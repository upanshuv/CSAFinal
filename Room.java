
import java.util.ArrayList;

public class Room {
    private String filePath; // path to room image
    private String key; // key to identify the room
    private boolean currentlyActive = false; // is this room currently active
    private ArrayList<Door> doors; // doors in this room


    /**
     * Constructor for Room.
     * @param filePath the path to the room image
     * @param key the key to identify the room
     */
    public Room(String filePath, String key) {
        this.filePath = filePath;
        //TODO just make an imageIcon and return that 
        this.key = key;
        currentlyActive = false;
        doors = new ArrayList<Door>();
    }

    /** <enter>
     * Returns the path to the room image.
     * @return the path to the room image
     */
    public String getImagePath() {
        return filePath;
    }

    /**
     * Returns the key to identify the room.
     * @return the key to identify the room
     */
    public String getKey() {
        return key;
    }

    /** <enter>
     * Returns the doors in this room.
     * @return the doors in this room
     */
    public ArrayList<Door> getDoors() {
        return doors;
    }

    /** <enter>
     * @param key the key to identify the room
     */
    public void setKey(String key) {
        this.key = key;
    }

    /** <enter>
     * Adds a door list to the room.
     * @param doors the door list to add
     */
    public void addDoors(ArrayList<Door> doors){
        this.doors = doors;
    }

    /** <enter>
     * determines whether room is active (currently being displayed).
     * @param active determines whether room is active
     */
    public void setActive(boolean active) {
        currentlyActive = active;
    }

    /** <enter>
     * Returns whether the room is currently active.
     * @return true if the room is currently active, false otherwise
     */
    public boolean getActive() {
        return currentlyActive;
    }
}