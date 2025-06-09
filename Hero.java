import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Rectangle;

public class Hero extends GameObject {

    private ImageIcon[][] icons; // 2D array to hold icons for each direction and phase
    private int phase; // animation phase
    private int direction; //direction of the hero (0: down, 1: left, 2: right, 3: up)
    private int phaseCounter; //animation phase counter
    private int dx; // horizontal velocity
    private int dy; // vertical velocity
    private int panelHeight = 0; // height of the panel to restrict movement
    private int panelWidth = 0; // width of the panel to restrict movement
    private int speed = 0; // speed of the hero
    private boolean soulMode; // boolean to check if the hero is in soul mode
    private Image soulImage; // image for the soul
    private Rectangle soulHitbox; // hitbox for the soul, used to check collisions in soul mode

    public Hero(int x, int y) {
        super(x, y);
        this.setSize(32, 36);
        direction = Direction.DOWN;
        phase = 0;
        phaseCounter = 0;
        dx = 0;
        dy = 0;
        soulMode = false;

        icons = new ImageIcon[4][4];

        soulHitbox = new Rectangle(x, y, 8, 8); // Initialize soul hitbox with the same size as the hero

        for (int i = 0; i < icons.length; i++) {
            for (int j = 0; j < icons[i].length; j++) {
                icons[i][j] = new ImageIcon("./images/character/sprite_" + i + "_" + j + ".png");
            }
        }

        this.setIcon(icons[direction][phase]);

    }

    /** <enter>
     * Set the soul mode for the hero
     * 
     * @param soulMode true if in soul mode, false otherwise
     */
    public void setSoulMode(boolean soulMode) { //used to keep track of soulMode within class for bounds
        this.soulMode = soulMode;
    }

    /**<enter>
     * Get the hitbox for the soul, used to check collisions in soul mode
     * 
     * @return Rectangle representing the hitbox of the soul
     */
    public Rectangle getSoulHitbox() {
        soulHitbox.setLocation(this.getX(), this.getY());
        return soulHitbox;
    }

    /**<center>
     * Set the position of the hero
     * 
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public void setPosition(int x, int y) {
        this.setLocation(x, y);
    }

    /**<center>
     * Get the image of the soul
     * 
     * @return Image representing the soul
     */
    public Image getSoul(){
        if (soulImage == null) {
            soulImage = new ImageIcon("./images/character//soul/soul.png").getImage();
        }
        return soulImage;
    }

    /**<center>
     * Get the ImageIcon for the hero, depending on the current direction and phase
     * If in soul mode, returns the soul image
     * 
     * @return ImageIcon representing the hero or soul
     */
    public ImageIcon getImageIcon() {
        if (soulMode) {
            return new ImageIcon(getSoul());
        } else {
            return icons[direction][phase];
        }
    }

    



    /**
     * Change direction of the character
     * 
     * @param direction
     */
    public void setDirection(int direction) {
        if (direction < 0 || direction > 3) {
            throw new IllegalArgumentException("int: " + direction + " is invalid.  Must be between 0 and 3 inclusive");
        }
        this.direction = direction;
    }

    /**
     * update the icon to animate the character
     */
    private void updateIcon() {
        if (soulMode) {
            return; 
        }
        if (phaseCounter % 6 == 0) {
            phase = (phase + 1) % 4;
            this.setIcon(icons[direction][phase]);
        }

        phaseCounter++;
    }

    /**
     * reset character in idle position
     */
    public void setIdle() {
        this.setIcon(icons[direction][0]);
        phaseCounter = 0;
    }

    /**
     * set dx for the character to make him move horizontally
     * 
     * @param dx horizontal velocity
     */
    public void setDx(int dx) {
        dx += speed;
        this.dx = dx; //set to zero if larger than bounds 
    }

    /**
     * set dy for the character to make him move vertically
     * 
     * @param dy vertical velocity
     */
    public void setDy(int dy) {
        dy += speed;
        this.dy = dy;
    }

    /**
     * update the character's location and image based on the dx and dy
     */
    public void update() {

        int updatedX = this.getX() + dx;
        int updatedY = this.getY() + dy;
       
        if (!soulMode){ //normal boundaries
            if (updatedX < 0 || updatedY < 0 || updatedX > panelWidth - this.getWidth()|| updatedY > panelHeight - this.getHeight()){ //the correct boundary code, replace ints with panel bounds with get method
                dx = 0;
                dy = 0;
                this.setIdle();
            }
        }
        if (soulMode) { //soul mode boundaries
            if (updatedX < 155 || updatedY < 260 || updatedX > 305 || updatedY > 390) {
                dx = 0;
                dy = 0;
                this.setIdle();
            }
        }
        
        this.setLocation(this.getX() + dx, this.getY() + dy);
        if (dx != 0 || dy != 0) {
            this.updateIcon();
        } else {
            this.setIdle();
        }
        
    }

    /**<center>
     * Get the current direction of the hero
     * 
     * @return int representing the direction (0: down, 1: left, 2: right, 3: up)
     */

    public int getDirection(){
        return direction;  
    }

    /** <enter>
     * get the height of the panel to restrict movement
     */
    public void getPanelHeight(int panelHeight){ 
        this.panelHeight = panelHeight;
    }

    /** <enter>
     * set the speed of the hero
     * 
     * @param speed speed of the hero
     */
    public void setSpeed(int speed){
        this.speed = 20;
    }

    /** <enter>
     * get the width of the panel to restrict movement
     */
    public void getPanelWidth(int panelWidth){ 
        this.panelWidth = panelWidth;
    }
}