import java.awt.Rectangle;

public class EnemyTrigger {

    private Rectangle bounds;
    private int x;
    private int y;
    private boolean isMercied;

    public EnemyTrigger(int x, int y) {
        isMercied = false;
        this.x = x;
        this.y = y;
        this.bounds = new Rectangle(x, y, 50, 50); 
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public boolean isMercied() {
        return isMercied;
    }

    public void setMercied(boolean mercied) {
        isMercied = mercied;
    }

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
