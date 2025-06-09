import java.awt.Rectangle;

import javax.swing.ImageIcon;


public class MainBattleButton {
    
    private String buttonID; //Determines button type / placement
    private ImageIcon nonSelectedIcon; // Image when button is not selected
    private ImageIcon selectedIcon; // Image when button is selected
    private boolean isSelected; // Indicates if the button is currently selected
    private int x; // x-coordinate of the button
    private int y; // y-coordinate of the button
    private Rectangle bounds; // Rectangle representing the button's bounds

    /** <enter>
     * Constructor for MainBattleButton.
     * @param nonSelectedImage Path to the image when the button is not selected.
     * @param selectedImage Path to the image when the button is selected.
     * @param buttonID Unique identifier for the button, used to determine its type and position.
     */
    public MainBattleButton(String nonSelectedImage, String selectedImage, String buttonID) {
        nonSelectedIcon = new ImageIcon(nonSelectedImage);
        selectedIcon = new ImageIcon(selectedImage);
        this.buttonID = buttonID;
        switch (buttonID) {
            case "fight":
                this.x = 20; 
                this.y = 420; 
                this.isSelected = true; // default button
                break;
            case "act":
                this.x = 135; 
                this.y = 420; 
                this.isSelected = false;
                break;
            case "item":
                this.x = 248; 
                this.y = 420; 
                this.isSelected = false;
                break;
            case "mercy":
                this.x = 361; 
                this.y = 420; 
                this.isSelected = false;
                break;
        }
        this.isSelected = false;
        this.bounds = new Rectangle(x, y, 112, 45);
    }

    /** <enter>
     * Sets the selected state of the button.
     * @param isSelected True if the button should be selected, false otherwise.
     */
    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    /** <enter>
     * Toggles the selected state of the button.
     */
    public boolean isSelected() {
        return isSelected;
    }

    /** <enter>
     * @return The non selected image of the button.
     */
    public ImageIcon getNonSelectedImage() {
        return nonSelectedIcon;
    }

    /** <enter>
     * @return the rectangle representing the button's bounds.
     */
    public Rectangle getBounds() {
        return bounds;
    }   
    
    /** <enter>
     * @return The selected image of the button.
     */
    public ImageIcon getSelectedImage() {
        return selectedIcon;
    }

    /** <enter>
     * @return The ID of the button.
     */
    public String getButtonID() {
        return buttonID;
    }
}
