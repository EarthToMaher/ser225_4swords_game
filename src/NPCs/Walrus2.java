package NPCs;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.NPC;
import Level.Player;
import Utils.Point;

import java.util.HashMap;
import java.util.Random; // For random wandering 

// This class is for the walrus NPC
public class Walrus2 extends NPC {

    private int health = 100; // int for initial health value

    // Movement fields
    private float wanderSpeed = 1.0f; // Slow speed for wandering
    private float chaseSpeed = 1.5f; // Faster speed for chasing
    private float detectionRange = 150.0f; // Pixels; start chasing if player within this
    private int wanderDirection = 1; // 0=up, 1=right, 2=down, 3=left
    private long lastDirectionChange = 0; // Timestamp for changing wander direction
    private Random random = new Random();

    public Walrus2(int id, Point location) {
        super(id, location.x, location.y, new SpriteSheet(ImageLoader.load("Walrus.png"), 24, 24), "STAND_RIGHT");
    }

    // Getter for health value
    public int getHealth() {
        return health;
    }

    // Setter for health value
    public void setHealth(int health) {
        this.health = Math.max(0, health);
    }

    // Method for decreasing health (taking damage)
    public void takeDamage(int damage) {
        System.out.println("I am being touched!");
        this.health -= damage;
        this.health = Math.max(0, this.health);
        System.out.println("Entity health: " + this.health);
        if (this.health == 0) {
            System.out.println("The enemy is dead");
            super.setIsHidden(true);
        }
    }

    @Override
    public void update(Player player) {
        // Movement AI (horizontal only)
        float dx = 0.0f; // Horizontal movement
        float dy = 0.0f; // Vertical movement
        boolean isMoving = false;

        float playerCenterX = player.getX() + 18.0f;
        float playerCenterY = player.getY() + 10.5f;
        float walrusCenterX = this.x + 16.5f;
        float walrusCenterY = this.y + 10.5f;
        float distanceToPlayer = (float) Math.hypot(playerCenterX - walrusCenterX, playerCenterY - walrusCenterY);

        long currentTime = System.currentTimeMillis(); // For timing wander changes

        if (distanceToPlayer < detectionRange && distanceToPlayer > 20.0f) {
            // Move toward player
            dx = Math.signum(playerCenterX - walrusCenterX) * chaseSpeed;
            dy = Math.signum(playerCenterY - walrusCenterY) * chaseSpeed;
            isMoving = true;
        } else if (distanceToPlayer <= 20.0f) {
            // Stop following player
            dx = 0.0f;
            dy = 0.0f;
            isMoving = false;
        } else {
            // Wander: Move in current direction, change randomly every 1-3 seconds
            if (currentTime - lastDirectionChange > (1000 + random.nextInt(2000))) { // 1-3 sec
                wanderDirection = random.nextInt(4);
                lastDirectionChange = currentTime;
            }
            switch (wanderDirection) {
                // Move up
                case 0:
                    dy = -wanderSpeed;
                    break;
                // Move right
                case 1:
                    dx = wanderSpeed;
                    break;
                // Move down
                case 2:
                    dy = wanderSpeed;
                    break;
                // Move left
                case 3:
                    dx = -wanderSpeed;
                    break;
            }

            isMoving = true;
        }

        // this.setX(this.getX() + dx);
        // this.setY(this.getY() + dy);
        this.moveXHandleCollision(dx);
        this.moveYHandleCollision(dy);

        /*
         * if (isMoving) {
         * System.out.println("Walrus moving: dx=" + dx + ", distance to player=" +
         * distanceToPlayer);
         * }
         */

        // Damage on touch player
        if (touching(player)) {
            //takeDamage(1);
            player.takeDamage(1);
        }

        super.update(player);
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {
            {
                put("STAND_LEFT", new Frame[] {
                        new FrameBuilder(spriteSheet.getSprite(0, 0))
                                .withScale(3)
                                .withBounds(7, 13, 11, 7)
                                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                                .build()
                });
                put("STAND_RIGHT", new Frame[] {
                        new FrameBuilder(spriteSheet.getSprite(0, 0))
                                .withScale(3)
                                .withBounds(7, 13, 11, 7)
                                .build()
                });
            }
        };
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }
}
