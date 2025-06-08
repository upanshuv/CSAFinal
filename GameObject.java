import javax.swing.JLabel;

public abstract class GameObject extends JLabel {

    public GameObject(int x, int y) {
        this.setLocation(x, y);
    }

    /**
     * 
     * determine if this object has collided with other
     * 
     * @param other GameObject with which we are detecting collision
     * @return true if collision is detected between this and other
     */
    public boolean hasCollidedWith(GameObject other) {

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
    
    public abstract void update();
}

