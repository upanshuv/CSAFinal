import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class GamePanel extends JPanel implements ActionListener {

    // --- UI & Rendering ---
    private BufferedImage background; //background image
    private int panelHeight; //height of gamepanel
    private int panelWidth; //width of gamepanel
    private int frameCount; // total number of rendered frames (used for animations)
    private int score; // used to display user score (from enemies defeated or mercied)

    // --- Player & Hero ---
    private Hero hero; // the player character
    private int heroXBeforeBattle; // hero's x position before battle (used to replace hero after battles)
    private int heroYBeforeBattle; // hero's y position before battle

    // --- Rooms & Doors ---
    private ArrayList<Room> rooms; // list of rooms in the game
    private Room activeRoom; //room currently being rendered
    private String[][] map; // used for visualization of rooms and doors

    // --- Enemy & Battle System ---
    private ArrayList<EnemyTrigger> enemyTriggers;  // list of enemy triggers in the game
    private ArrayList<String> genericEnemies; // list of generic enemy names in the game
    private int numActiveEnemies; // number of active enemies in the game
    private int randomEnemyInt; // index for random enemy selection
    private String enemyClassName; // name of the active enemy 
    private boolean triggersPlaced; // boolean tracking whether a room has had its enemy triggers rendered yet
    private boolean battleActive; // a boolean tracking whether a battle is currently active
    private boolean fightAnimationActive; // a boolean determining whether the fight animation is currently active
    private boolean displayDamageAnimation; // a boolean determining when to display enemy damage animaiton
    private boolean gameOver; // a boolean tracking whether the game is over
    private int damageAnimationCounter; // a counter for the damage animation frames
    private int fightAnimationIterator; //iterates through the fight animation frames
    private ArrayList<ImageIcon> fightAnimation; //arraylist of fight animation frames
    private int activeEnemyHealth; // health of the active enemy (used for battle logic)
    private int bulletBoardX; // x position of the bullet board (used for rendering)
    private int bulletBoardWidth; // width of bullet board (used for rendering)
    private TenzinEnemy tenzinEnemy; //enemy object for the Tenzin enemy

    // --- Battle Buttons ---
    private MainBattleButton fightButton; // the fight button
    private MainBattleButton itemButton; // the item button
    private MainBattleButton actButton; // the act button
    private MainBattleButton mercyButton; // the mercy button

    // --- Turn & Attack Logic ---
    private Boolean turn; // true for player, false for enemy
    private ArrayList<String> attacks; // list of attacks available to the enemy
    private String activeAttack; // the currently active attack (used for rendering and logic)
    private boolean centerSoul; // a boolean determining whether the soul should be centered in the middle of the screen

    // --- Attack Objects ---
    private ArrayList<FallingBlock> fallingBlocks; // for "fallingBlocks" attack
    private ArrayList<FallingBlock> fallingBlocksAtDifferentSpeeds; // for "fallingBlocksAtDifferentSpeeds" attack
    private ArrayList<FallingBlock> fallingBlocksUp; // for "falingBlocksUp" attack

    // --- Health & Mercy Bars ---
    private Health playerHealth; // player health bar
    private Health enemyHealth; // enemy health bar
    private Mercy mercyBar; // enemy mercy bar (battle ends when full)
    private int mercyLevel; // tracks mercy level of enemy

    // --- Invincibility & Damage ---
    private int invincibilityFrame; // frame when the player becomes invincible after taking damage
    private boolean tookDamageThisFrame; // boolean tracking whether the player took damage this frame

    // --- Sound ---
    private String taleSound; // base game music
    private String runeSound; // battle music
    private static Clip currentClip; // current sound clip being played


    public GamePanel() {

        //INIT FIELDS

        invincibilityFrame = 0; 
        tookDamageThisFrame = false;
        turn = true;

        heroXBeforeBattle = 0;
        heroYBeforeBattle = 0;

        centerSoul = false;

        mercyLevel = 0;
        activeEnemyHealth = 0;
        score = 0;
        numActiveEnemies = 10;

        frameCount = 0;
        fightAnimationIterator = 0;

        activeRoom = null;

        displayDamageAnimation = false; 
        damageAnimationCounter = 0; 
        fightAnimationActive = false; 
        
        triggersPlaced = false;

        battleActive = false;
        fightAnimationActive = false;
        gameOver = false;

        bulletBoardX = 155; 
        bulletBoardWidth = 150;

        //INIT ATTACKS //

        attacks = new ArrayList<String>();
        attacks.add("fallingBlocks");
        attacks.add("fallingBlocksAtDifferentSpeeds");
        attacks.add("fallingBlocksUp");
        activeAttack = "";

        fallingBlocks = new ArrayList<FallingBlock>(); 
        fallingBlocksAtDifferentSpeeds = new ArrayList<FallingBlock>();
        fallingBlocksUp = new ArrayList<FallingBlock>();
        

        //INIT ANIMATIONS //

        fightAnimation = new ArrayList<ImageIcon>();
        for (int i = 0; i < 5; i++) {
            String imagePath = "./images/animations/fightAnimations/fightAnimation" + i + ".png";
            URL imageURL = getClass().getResource(imagePath);
            if (imageURL != null) {
                ImageIcon icon = new ImageIcon(imageURL);
                fightAnimation.add(icon);
            } else {
                System.err.println("Image not found: " + imagePath);
            }
        }

        //INIT ANIMATIONS END //

        // INIT DOORS AND ROOMS //

        map = new String[][]{ // only used for visualization
            {"Mathroom"  ,  "TopRoom"},
            {"LeftRoom","TileRoom", "RightRoom"}, 
                        {"BottomRoom",}
        };

        rooms = new ArrayList<Room>();
        rooms.add(new Room("./images/background/gray_tile_background.png", "TileRoom"));
        rooms.add(new Room("./images/background/RightRoom.jpg", "RightRoom"));
        rooms.add(new Room("./images/background/BottomRoom.jpg", "BottomRoom"));
        rooms.add(new Room("./images/background/TopRoom.jpg", "TopRoom"));
        rooms.add(new Room("./images/background/LeftRoom.png", "LeftRoom"));

        for (Room room : rooms) {
            ArrayList<Door> roomDoors = new ArrayList<Door>();
            if (room.getKey().equals("TileRoom")) {
                roomDoors.add(new Door("RightRoom", "right"));
                roomDoors.add(new Door("BottomRoom", "down"));
                roomDoors.add(new Door("TopRoom", "up"));
                roomDoors.add(new Door("LeftRoom", "left"));
                room.setActive(true);
            } 
            else if (room.getKey().equals("RightRoom")) {
                roomDoors.add(new Door("TileRoom", "left"));
            } 
            else if (room.getKey().equals("BottomRoom")) {
                roomDoors.add(new Door("TileRoom", "up"));
            } 
            else if (room.getKey().equals("TopRoom")) {
                roomDoors.add(new Door("TileRoom", "down"));
            } 
            else if (room.getKey().equals("LeftRoom")) {
                roomDoors.add(new Door("TileRoom", "right"));
            }
            room.addDoors(roomDoors);
        }


        // INIT DOORS AND ROOMS END //

        //INIT BUTTONS//

        fightButton = new MainBattleButton("./images/buttons/mainBattleButtons/fightUnselected.png", "./images/buttons/mainBattleButtons/fightSelected.png", "fight");
        fightButton.setSelected(true);
        itemButton = new MainBattleButton("./images/buttons/mainBattleButtons/itemUnselected.png", "./images/buttons/mainBattleButtons/itemSelected.png", "item");
        actButton = new MainBattleButton("./images/buttons/mainBattleButtons/actUnselected.png", "./images/buttons/mainBattleButtons/actSelected.png", "act");
        mercyButton = new MainBattleButton("./images/buttons/mainBattleButtons/mercyUnselected.png", "./images/buttons/mainBattleButtons/mercySelected.png", "mercy");
        //INIT BUTTONS END//

        // INIT ENEMY TRIGGERS //

        enemyTriggers = new ArrayList<EnemyTrigger>();
        for (int i = 0; i < 10; i++) {
            enemyTriggers.add(new EnemyTrigger(9999, 9999));
        }

        this.setLayout(null);
        hero = new Hero(100, 100);

        URL imageURL = getClass().getResource("./images/background/gray_tile_background.png");

        //INIT ENEMY TRIGGERS END //

        //INIT BACKGROUND//

        try {
            background = ImageIO.read(imageURL);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //INIT HERO AND GAMELOOP//

        hero.setVisible(true);

        Timer gameLoop = new Timer(17, this);
        gameLoop.start();

        this.setFocusable(true);
        this.requestFocusInWindow();

        // INIT GENERIC ENEMIES // 

        tenzinEnemy = new TenzinEnemy();

        genericEnemies = new ArrayList<String>();
        genericEnemies.add("TenzinEnemy");

        // INIT BARS //
        playerHealth = new Health(0, 0);
        playerHealth.setHealth(10); 
        enemyHealth = new Health(320, 0);
        mercyBar = new Mercy(0, 45);

        // INIT SOUNDS //
        taleSound = "./sounds/Undertale-Ruins.wav";
        runeSound = "./sounds/Deltarune-Battle.wav";
        playSound(taleSound);
        // END INIT SOUNDS //

        // INIT KEYLISTENER //
        this.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                if (!battleActive || (battleActive && !turn)) {
                    switch (code) {
                        case KeyEvent.VK_UP:
    
                            hero.setDy(-5);
                            hero.setDx(0);
                            hero.setDirection(Direction.UP);
                            
                            
                            break;
                        case KeyEvent.VK_LEFT:
                        
                                hero.setDy(0);
                                hero.setDx(-5);
                                hero.setDirection(Direction.LEFT);
                            
                            break;
                        case KeyEvent.VK_DOWN:
                            
                                hero.setDy(5);
                                hero.setDx(0);
                                hero.setDirection(Direction.DOWN);
                            
                            break;
                        case KeyEvent.VK_RIGHT:
                            
                                hero.setDy(0);
                                hero.setDx(5);
                                hero.setDirection(Direction.RIGHT);
                            
                            break;
                    }
                }

                else if (battleActive && turn) {
                    switch (code) {
                        case KeyEvent.VK_Z:
                            if (fightButton.isSelected() && !fightAnimationActive && turn) {
                                doFIGHT();
                            } else if (actButton.isSelected()) {
                                doACT();
                                System.out.println("Act");
                            } else if (itemButton.isSelected()) {
                                System.out.println("Item");
                                doItem();
                            } else if (mercyButton.isSelected()) {
                                doMercy();
                                System.out.println("Mercy");
                            }
                            break;
                        case KeyEvent.VK_UP:
                            hero.setDy(-5);
                            hero.setDx(0);
                            break;
                        case KeyEvent.VK_LEFT:
                            hero.setDx(-5);
                            hero.setDy(0);

                            if (battleActive && turn){
                                if (fightButton.isSelected()) {
                                    fightButton.setSelected(false);
                                    mercyButton.setSelected(true);
                                } else if (actButton.isSelected()) {
                                    actButton.setSelected(false);
                                    fightButton.setSelected(true);
                                } else if (itemButton.isSelected()) {
                                    itemButton.setSelected(false);
                                    actButton.setSelected(true);
                                } else if (mercyButton.isSelected()) {
                                    mercyButton.setSelected(false);
                                    itemButton.setSelected(true);
                                }
                                repaint();
                            }
                            break;

                        case KeyEvent.VK_DOWN:
                            hero.setDy(5);
                            hero.setDx(0);
                            break;
                        case KeyEvent.VK_RIGHT:

                            hero.setDx(5);
                            hero.setDy(0);
                            if (battleActive && turn){
                                if (fightButton.isSelected()) {
                                    fightButton.setSelected(false);
                                    actButton.setSelected(true);
                                } else if (actButton.isSelected()) {
                                    actButton.setSelected(false);
                                    itemButton.setSelected(true);
                                } else if (itemButton.isSelected()) {
                                    itemButton.setSelected(false);
                                    mercyButton.setSelected(true);
                                } else if (mercyButton.isSelected()) {
                                    mercyButton.setSelected(false);
                                    fightButton.setSelected(true);
                                }
                                break;
                            }
                        }
                       
                }
            }

            private void doACT() {
                mercyLevel++;
                actButton.setSelected(false);

                mercyBar.setMercy(mercyLevel);
                setEnemyTurn();
            }

            private void doMercy() {
                if (mercyLevel >= 5) {
                    // make enemytrigger blue 
                    for (EnemyTrigger enemy : enemyTriggers) {
                        if (!enemy.isMercied()) {
                            enemy.setMercied(true);
                            break;
                        }
                    }
                    endBattle();
                } else {
                    mercyButton.setSelected(false);
                    setEnemyTurn();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int code = e.getKeyCode();

                switch (code) {
                    case KeyEvent.VK_Z:
                        if (battleActive && turn){
                            if (fightButton.isSelected() && !fightAnimationActive && turn) {
                                doFIGHT();

                            } else if (actButton.isSelected()) {
                                System.out.println("Act");
                            } else if (itemButton.isSelected()) {
                                System.out.println("Item");
                            } else if (mercyButton.isSelected()) {
                                System.out.println("Mercy");
                            }
                        }
                        break;
                    case KeyEvent.VK_UP:
                        hero.setDy(0);
                        hero.setIdle();
                        break;
                    case KeyEvent.VK_LEFT:
                        if (!battleActive || !turn || (battleActive && true)) {
                            hero.setDx(0);
                            hero.setIdle();
                        }
                        
                        hero.setDx(0);
                        hero.setIdle();
                        break;
                    case KeyEvent.VK_DOWN:
                        hero.setDy(0);
                        hero.setIdle();
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (!battleActive || !turn) {
                            hero.setDx(0);
                            hero.setIdle();
                        }
                        break;
                }
            }
        });

    }

    /** <enter> * Method to handle damage to the enemy.
     * This method decreases the active enemy's health by 1 and updates the enemy health bar.
     */
    public void doDamage(){        
        activeEnemyHealth --; 
        enemyHealth.setHealth(activeEnemyHealth);
    }


    /**
     * Method to set the background image of the game panel.
     * @param imagePath The path to the image file.
     */
    public void setBackground(String imagePath) {
        URL imageURL = getClass().getResource(imagePath);

        try {
            background = ImageIO.read(imageURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to start the fight animation 
     * This method sets the fightAnimationActive boolean to true, indicating that the fight animation is active.
     * This change will result in the enemies turn being set after the attack is rendered
     * in paintComponent.
     */
    public void doFIGHT() {
        fightAnimationActive = true;
    }
    
    /** <enter>
     * Method to handle item usage in the game.
     * This method increases the player's health by 1 and sets the enemy's turn.
     * It is called when the player uses an item during battle.
     */
    public void doItem(){
        itemButton.setSelected(false);
        playerHealth.setHealth(playerHealth.getHealth() + 1); // heal player by 1
        setEnemyTurn(); 
    }

    /** <enter>
     * Method to set the room for the game panel.
     * This method sets the background image of the game panel to the image path of the given room,
     * resets the enemy triggers' positions, and ensures that they do not overlap with each other or the hero.
     * @param room The room to set for the game panel.
     */
    public void setRoom(Room room) {
        this.setBackground(room.getImagePath());
        //reset triggers
        for (EnemyTrigger enemy : enemyTriggers) {
            int x = (int) (Math.random() * (getWidth() - enemy.getBounds().getWidth()));
            int y = (int) (Math.random() * (getHeight() - enemy.getBounds().getHeight()));
            enemy.getBounds().setLocation(x, y);
            for (EnemyTrigger compare : enemyTriggers) { //prevent overlap
                if (enemy != compare && enemy.getBounds().intersects(compare.getBounds())
                        || enemy.getBounds().intersects(hero.getBounds())) {
                    x = (int) (Math.random() * (getWidth() - enemy.getBounds().getWidth()));
                    y = (int) (Math.random() * (getHeight() - enemy.getBounds().getHeight()));
                    enemy.getBounds().setLocation(x, y);
                }
            }
        }
        
        triggersPlaced = true;
        repaint();
    }

    /** <enter>
     * Method to activate battle mode.
     * This method resets the active enemy health, mercy level, and sets the background to the battle background.
     * It also resets the positions of the battle buttons and sets the fight button as selected.
     * Finally, it sets the battleActive boolean to true, selects a random enemy, and plays the battle sound.
     */
    public void activateBattleMode() {

        activeEnemyHealth = 10; // reset active enemy health
        enemyHealth.setHealth(activeEnemyHealth);
        mercyLevel = 0; // reset mercy level
        mercyBar.setMercy(0); 
        mercyBar.setStartImage();
        this.setBackground("./images/background/GenericBattle.png");

        fightButton.getBounds().setLocation((int)fightButton.getBounds().getX(), (int)fightButton.getBounds().getY());
        actButton.getBounds().setLocation((int)actButton.getBounds().getX(), (int)actButton.getBounds().getY());
        itemButton.getBounds().setLocation((int)itemButton.getBounds().getX(), (int)itemButton.getBounds().getY());
        mercyButton.getBounds().setLocation((int)mercyButton.getBounds().getX(), (int)mercyButton.getBounds().getY());

        fightButton.setSelected(true);
        actButton.setSelected(false);
        itemButton.setSelected(false);
        mercyButton.setSelected(false);
        

        battleActive = true;

    
        randomEnemyInt = (int) (Math.random() * genericEnemies.size());
        enemyClassName = genericEnemies.get(randomEnemyInt);

        playSound(runeSound);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);

        // door rendering 
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getActive() && !battleActive) {
                ArrayList<Door> roomDoors = rooms.get(i).getDoors();
                for (Door door : roomDoors) {
                    int doorWidth = door.getBounds().width;
                    int doorHeight = door.getBounds().height;
                    g.setColor(java.awt.Color.BLUE);

                    if (door.getSideOfRoom().equals("right")) {
                        door.setPosition(this.getWidth() - doorWidth, this.getHeight() / 2 - doorHeight / 2);
                    } else if (door.getSideOfRoom().equals("left")) {
                        door.setPosition(0, this.getHeight() / 2 - doorHeight / 2);
                    } else if (door.getSideOfRoom().equals("up")) {
                        door.setPosition(this.getWidth() / 2 - doorWidth / 2, 0);
                    } else if (door.getSideOfRoom().equals("down")) {
                        door.setPosition(this.getWidth() / 2 - doorWidth / 2, this.getHeight() - doorHeight);
                    }
                    Rectangle bounds = door.getBounds();
                    g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);

                    if (door.hasCollided(hero)) {
                        rooms.get(i).setActive(false);
                        for (Room room : rooms) {
                            if (room.getKey().equals(door.getKey())) {
                                System.out.println(door.getKey());
                                room.setActive(true);
                                String side = door.getSideOfRoom();
                                int width = (int)door.getBounds().getWidth();
                                int height = (int)door.getBounds().getHeight();
                                if (side.equals("right")) { //TODO verify collision logic
                                    hero.setLocation(0 + width, this.getHeight() / 2 - hero.getHeight() / 2);
                                } else if (side.equals("left")) {
                                    hero.setLocation(this.getWidth() - hero.getWidth() - width, this.getHeight() / 2 - hero.getHeight() / 2);
                                } else if (side.equals("up")) {
                                    hero.setLocation(this.getWidth() / 2 - hero.getWidth() / 2, this.getHeight() - hero.getHeight() - height);
                                } else if (side.equals("down")) {
                                    hero.setLocation(this.getWidth() / 2 - hero.getWidth() / 2, 0 + height);
                                }
                                setRoom(room);
                                activeRoom = room;
                                System.out.println("New Room: " + room.getKey());
                            }
                        }
                    }
                }
            }
        }

        if (!battleActive){
            g.drawImage(hero.getImageIcon().getImage(), hero.getX(), hero.getY(), this);
        }

        
        // enemy trigger rendering
        if (!battleActive && triggersPlaced){
            for (int i = 0; i < enemyTriggers.size(); i++) {
                EnemyTrigger enemy = enemyTriggers.get(i);
                if (enemy.isMercied()) {
                    g.setColor(java.awt.Color.BLUE);
                } else {
                    g.setColor(java.awt.Color.RED);
                }
                Rectangle bounds = enemy.getBounds();
                g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height); // This line of code provided by ChatGPT
                if (enemy.hasCollided(hero) && !battleActive && !enemy.isMercied()) {
                    System.out.println("Enemy Triggered");
                    activateBattleMode();
                    repaint();
                    break;
                }
            }
        }
        // button rendering
        if (battleActive) {

            // FIGHT ANIMATION RENDERING//
            
            if (fightButton.isSelected()) {
                g.drawImage(fightButton.getSelectedImage().getImage(), (int)fightButton.getBounds().getX(), (int)fightButton.getBounds().getY(), this);
            } else {
                g.drawImage(fightButton.getNonSelectedImage().getImage(), (int)fightButton.getBounds().getX(), (int)fightButton.getBounds().getY(), this);
            }

            if (actButton.isSelected()) {
                g.drawImage(actButton.getSelectedImage().getImage(), (int)actButton.getBounds().getX(), (int)actButton.getBounds().getY(), this);
            } else {
                g.drawImage(actButton.getNonSelectedImage().getImage(), (int)actButton.getBounds().getX(), (int)actButton.getBounds().getY(), this);
            }

            if (itemButton.isSelected()) {
                g.drawImage(itemButton.getSelectedImage().getImage(), (int)itemButton.getBounds().getX(), (int)itemButton.getBounds().getY(), this);
            } else {
                g.drawImage(itemButton.getNonSelectedImage().getImage(), (int)itemButton.getBounds().getX(), (int)itemButton.getBounds().getY(), this);
            }

            if (mercyButton.isSelected()) {
                g.drawImage(mercyButton.getSelectedImage().getImage(), (int)mercyButton.getBounds().getX(), (int)mercyButton.getBounds().getY(), this);
            } else {
                g.drawImage(mercyButton.getNonSelectedImage().getImage(), (int)mercyButton.getBounds().getX(), (int)mercyButton.getBounds().getY(), this);
            }

            // soul rendering and attack rendering

            if (battleActive && !turn){
                g.drawImage(hero.getSoul(), hero.getX(), hero.getY(), this);
                switch (activeAttack) {
                    case "fallingBlocks":
                        //render falling blocks
                        
                        g.setColor(Color.BLUE);
                        for (FallingBlock block : fallingBlocks) {
                            if (!tookDamageThisFrame && frameCount > invincibilityFrame && block.getBounds().intersects(hero.getSoulHitbox())) {
                                // if the block intersects with the soul, deal damage to the hero
                                System.out.println("damage");
                                playerHealth.setHealth(playerHealth.getHealth() - 1); // deal damage to the player
                                invincibilityFrame = frameCount + 60; // player will be invincible for 60 frames
                                tookDamageThisFrame = true; // set tookDamageThisFrame to true to prevent multiple damage in one frame
                            }
                            g.fillRect((int)block.getBounds().getX(), (int)block.getBounds().getY(), (int)block.getBounds().getWidth(), (int)block.getBounds().getHeight());
                        }
                        tookDamageThisFrame = false; // reset tookDamageThisFrame for the next frame
                        
                        break;
                    case "fallingBlocksAtDifferentSpeeds":
                        
                        g.setColor(Color.RED);
                        for (FallingBlock block : fallingBlocksAtDifferentSpeeds) {
                            if (!tookDamageThisFrame && frameCount > invincibilityFrame && block.getBounds().intersects(hero.getSoulHitbox())) {
                                // if the block intersects with the soul, deal damage to the hero
                                System.out.println("damage");
                                doDamageToPlayer();
                            } 
                            g.fillRect((int)block.getBounds().getX(), (int)block.getBounds().getY(), (int)block.getBounds().getWidth(), (int)block.getBounds().getHeight());
                        }
                        tookDamageThisFrame = false; // reset tookDamageThisFrame for the next frame
                        break;
                    case "fallingBlocksUp":
                        g.setColor(Color.GREEN);
                        for (FallingBlock block : fallingBlocksUp) {
                            if (!tookDamageThisFrame && frameCount > invincibilityFrame && block.getBounds().intersects(hero.getSoulHitbox())) {
                                doDamageToPlayer();
                            }
                            System.out.println("falling blocks y" + block.getBounds().getY());
                            g.fillRect((int)block.getBounds().getX(), (int)block.getBounds().getY(), (int)block.getBounds().getWidth(), (int)block.getBounds().getHeight());
                        }
                        tookDamageThisFrame = false;
                        break;
                }
            }

            

        }

        // enemy rendering

        if (battleActive){
            switch (enemyClassName){
                case "TenzinEnemy":
                    g.drawImage(tenzinEnemy.getImage().getImage(), 200, 60, this);
                    break;

                // no other enemies implemented
            }
        }

        //fight animation rendering 

        if(fightAnimationActive) {
            if (fightAnimationIterator < fightAnimation.size()) {
                g.drawImage(fightAnimation.get(fightAnimationIterator).getImage(), 200, 120, this);
                if (frameCount % 5 == 0){
                    fightAnimationIterator++;
                }
            } 
            else {
                doDamage();
                setEnemyTurn();
            }
        }

        //bars
        if (battleActive && (playerHealth != null && enemyHealth != null && mercyBar != null)) {
            g.drawImage(playerHealth.getBar(), playerHealth.getX(), playerHealth.getY(), this);
            g.drawImage(enemyHealth.getBar(), enemyHealth.getX(), enemyHealth.getY(), this);
            g.drawImage(mercyBar.getBar(), mercyBar.getX(), mercyBar.getY(), this);

        }

        // texts
        if (!battleActive){
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 24));
            g.drawString("Score: " + score, 10, 30); // display score
        }
        if (battleActive)
        {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 14));
            g.drawString("Your Health", 180, 20);
            Graphics g2 = g;
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 14));
            g2.drawString("Mercy Level", 120, 65);
            Graphics g3 = g;
            g3.setColor(Color.WHITE);
            g3.setFont(new Font("Arial", Font.BOLD, 14));
            g3.drawString("Enemy Health", 220, 40);
        }

    }

    /** <enter>
     * Method to end the battle.
     * This method decreases the number of active enemies in the room, resets the battleActive boolean,
     * resets the hero's position, and resets the room.
     * It also plays the tale sound and increases the score by 1.
     */
    public void endBattle() {
        numActiveEnemies--; // decrease number of active enemies in the room
        if (numActiveEnemies <= 0) {
            System.out.println("All enemies defeated");
        }
        System.out.println("Battle ended");
        battleActive = false;
        turn = true; // reset turn to player
        hero.setSoulMode(false); // reset soul mode
        hero.setLocation(heroXBeforeBattle, heroYBeforeBattle); // reset hero position
        setRoom(activeRoom); // reset room
        activeAttack = ""; // reset active attack
        score += 1;
        playSound(taleSound);
    }

    /** <enter>
     * Method to deal damage to the player.
     * This method decreases the player's health by 1, sets the invincibility frame,
     * and sets tookDamageThisFrame to true to prevent multiple hits in one frame.
     */
    public void doDamageToPlayer() {
        playerHealth.setHealth(playerHealth.getHealth() - 1); // deal damage to the player
        System.out.println("Player took damage");
        invincibilityFrame = frameCount + 60; // player will be invincible for 60 frames
        tookDamageThisFrame = true; // set tookDamageThisFrame to true to prevent multiple damage in one frame
    }

    /** <enter>
     * Method to set the enemy's turn.
     * This method sets the active enemy health, resets the fight animation, and prepares the attack to be rendered.
     * It also sets the turn to false, indicating that it is now the enemy's turn.
     */
    public void setEnemyTurn(){
        
        System.out.println("Active Enemy Health: " + activeEnemyHealth);
        fightAnimationActive = false;
        fightAnimationIterator = 0;
        damageAnimationCounter = frameCount + 60; // Set the damage animation to display for 3 seconds
        displayDamageAnimation = true; // Show damage animation
        turn = false; // enemy turn
        activeAttack = attacks.get((int)(Math.random() * attacks.size()));
        //prepare the attack to be rendered 

        switch (activeAttack){
            case "fallingBlocks":
                fallingBlocks.clear(); // clear previous blocks
                for (int i = 0; i < 15; i++) {
                    int width = 30, height = 30, speed = 5;
                    int x = bulletBoardX + (int)(Math.random() * (bulletBoardWidth - width));
                    int y = 0; // start at the top
                    fallingBlocks.add(new FallingBlock(x, y, width, height, speed));                         
                }
                fallingBlocksUp.add(new FallingBlock(bulletBoardX + bulletBoardWidth, 0, 30, 30, 7));
                break;

            case "fallingBlocksAtDifferentSpeeds":
                fallingBlocksAtDifferentSpeeds.clear(); // clear previous blocks
                for (int i = 0; i < 8; i++) {
                    int width = 30; 
                    int height = 30;
                    int x = bulletBoardX + (int)(Math.random() * (bulletBoardWidth - width));
                    int y = 0;
                    int speed = (int) (Math.random() * 3 + 3); 
                    fallingBlocksAtDifferentSpeeds.add(new FallingBlock(x, y, width, height, speed));
                }
                fallingBlocksAtDifferentSpeeds.add(new FallingBlock(bulletBoardX + bulletBoardWidth, 0, 30, 30, 7));
                break;
            case "fallingBlocksUp":
                fallingBlocksUp.clear();
                for (int i = 0; i < 8; i++){
                    int width = 30;
                    int height = 30;
                    int x = bulletBoardX + (int)(Math.random() * (bulletBoardWidth - width));
                    int y = this.getHeight() + 200;   
                    int speed = (int) (Math.random() * 4 + 3); 
                    fallingBlocksUp.add(new FallingBlock(x, y, width, height, speed));
                }
                fallingBlocksUp.add(new FallingBlock(bulletBoardX + bulletBoardWidth, this.getHeight() - 30, 30, 30, 4));
        }
        fightButton.setSelected(false);
        if (battleActive){
            hero.setSoulMode(true);
            centerSoul = true; // center the soul in the middle of the screen
        }
    }

    /** <enter>
     * Method to play a sound from a given location.
     * This method stops the current clip if it is running, closes it, and then plays the new sound from the specified file location.
     * @param location The file path of the sound to be played.
     */
    public static void playSound(String location) {
        try {

            if (currentClip != null && currentClip.isRunning()) {
                currentClip.stop();
                currentClip.close();
            }
    
            File soundPath = new File(location);
            if (soundPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundPath);
                currentClip = AudioSystem.getClip();
                currentClip.open(audioInput);
                currentClip.start();
            } else {
                System.out.println("Cant find music file");
            }
        } catch (Exception e) {
            System.err.println("Error playing sound: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (!battleActive) {
            hero.update();
        }
        if (battleActive) {
            
            if (!turn){
                if (centerSoul) {
                    heroXBeforeBattle = hero.getX();
                    heroYBeforeBattle = hero.getY();
                    hero.setPosition(250, 300);
                    System.out.println("centered");
                    centerSoul = false; // only center once
                }
                hero.update();
            }
            else if (turn && battleActive){
                hero.setDx(0);
                hero.setDy(0);
            }

            //battle ending/enemy defeated logic 
            if (activeEnemyHealth <= 0) {
                //battle ending logic
                endBattle();
            }

            //attack logic here
            if (activeAttack.equals("fallingBlocks")) {
                for (int i = 0; i < fallingBlocks.size(); i++) {
                    FallingBlock block = fallingBlocks.get(i);
                    block.update();
                    if (block.getBounds().getY() > this.getHeight()) {
                        fallingBlocks.remove(i);
                        i--; // adjust index after removal
                    }
                    if (fallingBlocks.size() <= 0) {
                        turn = true; // player turn after all blocks are rendered
                        fightButton.setSelected(true);
                        activeAttack = ""; // reset active attack
                    }
                }
            }
            else if (activeAttack.equals("fallingBlocksAtDifferentSpeeds")) {
                for (int i = 0; i < fallingBlocksAtDifferentSpeeds.size(); i++) {
                    FallingBlock block = fallingBlocksAtDifferentSpeeds.get(i);
                    block.update();
                    if (block.getBounds().getY() > this.getHeight()) {
                        fallingBlocksAtDifferentSpeeds.remove(i);
                        i--; // adjust index after removal
                    }
                    if (fallingBlocksAtDifferentSpeeds.size() <= 0) {
                        turn = true; // player turn after all blocks are rendered
                        fightButton.setSelected(true);
                        activeAttack = ""; // reset active attack
                    }
                }
            }
            else if (activeAttack.equals("fallingBlocksUp")) {
                for (int i = 0; i < fallingBlocksUp.size(); i++) {
                    FallingBlock block = fallingBlocksUp.get(i);
                    block.updateUp(); 
                    if (block.getBounds().getY() + block.getBounds().getHeight() < 0) {
                        fallingBlocksUp.remove(i);
                        i--;
                    }
                    if (fallingBlocksUp.size() <= 0) {
                        turn = true;
                        fightButton.setSelected(true);
                        activeAttack = "";
                    }
                }
            }
        }
        frameCount++;
        repaint();

        hero.getPanelHeight(this.getHeight());
        hero.getPanelWidth(this.getWidth());

        if (playerHealth.getHealth() <= 0) {
            Animation.showEndScreen(false); // dead screen
        }
        if (score >= 10) {
            Animation.showEndScreen(true); // win screen
        }
    }
}