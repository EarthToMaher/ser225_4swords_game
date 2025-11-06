package NPCs;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.NPC;
import Level.Player;
import Screens.PlayLevelScreen;
import Utils.Point;

import java.util.HashMap;
import java.util.Random;

public class Dinosaur2 extends NPC {

    private int health = 65; // int for health
    

    // Movement fields
    private float wanderSpeed = .6f; // Slow speed for wandering
    private float chaseSpeed = 0f; // Faster speed for chasing (0 for stand still)
    private float detectionRange = 150.0f; // Start chasing if player within this range
    private int wanderDirection = 1; // 0=up, 1=right, 2=down, 3=left
    private long lastDirectionChange = 0; // Timestamp for changing wander direction
    private Random random = new Random();

    // Projectile firing fields
    private float firingRange = 350.0f;
    private long fireCooldown = 2000; 
    private long lastFiredTime = 0;

    public Dinosaur2(int id, Point location) {
        super(id, location.x, location.y, new SpriteSheet(ImageLoader.load("LilShooter.png"), 64, 64), "STAND_LEFT");
    }

    // Getter for health value
    public int getHealth() {
        return health;
    }

    // Setter for health value
    public void setHealth(int health) {
        this.health = Math.max(0, health);
    }

    // Method for taking damage
    public void takeDamage(int damage) {
        System.out.println("I am being touched!");
        this.health -= damage;
        this.health = Math.max(0, this.health);
        System.out.println("Entity health: " + this.health);
        if (this.health == 0) {
            System.out.println("The enemy is dead");
            super.setIsHidden(true);
            map.deleteNPC(this);
        }
    }

    @Override
    public void update(Player player) {
        float dx = 0.0f; // Horizontal movement
        float dy = 0.0f; // Vertical movement
        boolean isMoving = false;

        
        float playerCenterX = player.getX() + 18.0f;
        float playerCenterY = player.getY() + 10.5f;
        float dinosaurCenterX = this.x + 16.5f;
        float dinosaurCenterY = this.y + 10.5f;
        float distanceToPlayer = (float) Math.hypot(playerCenterX - dinosaurCenterX, playerCenterY - dinosaurCenterY);

        long currentTime = System.currentTimeMillis(); 

        if (distanceToPlayer < firingRange && distanceToPlayer > 20.0f) {
            // Stop and fire projectile at player
            dx = 0.0f;
            dy = 0.0f;
            isMoving = false;
            // Face the player
            if (playerCenterX < dinosaurCenterX) {
                this.setCurrentAnimationName("SHOOT_LEFT");
            } else {
                this.setCurrentAnimationName("SHOOT_RIGHT");
            }
            // Fire if cooldown passed
            if (currentTime - lastFiredTime > fireCooldown) {
                fireProjectile(player, playerCenterX, playerCenterY, dinosaurCenterX, dinosaurCenterY);
                lastFiredTime = currentTime;
            }
        } else if (distanceToPlayer <= 40.0f) {
            // Too close to player: stop and fire
            dx = 0.0f;
            dy = 0.0f;
            isMoving = false;
        } else {
            // Wander around: Move in current direction, change direction randomly every 1-3 seconds
            if (currentTime - lastDirectionChange > (1000 + random.nextInt(2000))) {
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
                    this.setCurrentAnimationName("WALK_RIGHT");
                    break;
                // Move down
                case 2:
                    dy = wanderSpeed;
                    break;
                // Move left
                case 3:
                    dx = -wanderSpeed;
                    this.setCurrentAnimationName("WALK_LEFT");
                    break;
            }

            isMoving = true;
        }

        // this.setX(this.getX() + dx);
        // this.setY(this.getY() + dy);
        this.moveXHandleCollision(dx);
        this.moveYHandleCollision(dy);

        /* testing code for enemy movement
         * if (isMoving) {
         * System.out.println("Walrus moving: dx=" + dx + ", distance to player=" +
         * distanceToPlayer);
         * }
         */

        // Damage on touch player
        if (touching(player)) {
            takeDamage(1);
            PlayLevelScreen.player.takeDamage(1);
        }

        super.update(player);
    }

   // Method to fire a projectile toward the player
private void fireProjectile(Player player, float playerCenterX, float playerCenterY, float dinosaurCenterX, float dinosaurCenterY) {
    float dx = playerCenterX - dinosaurCenterX;
    float dy = playerCenterY - dinosaurCenterY;
    float distance = (float) Math.hypot(dx, dy);
    System.out.println("I will fire something!");
    
    // Set speed
    float speed = 4.0f;
    float velX = (dx / distance) * speed;
    float velY = (dy / distance) * speed;
    
    // Create projectile
    EnemyProjectile projectile = new EnemyProjectile(dinosaurCenterX, dinosaurCenterY, velX, velY, 300);
    if(projectile.touching(this)) {
        projectile.setIsUncollidable(true);
    } else {
        projectile.setIsUncollidable(false);
    }
    System.out.println("Projectile created at (" + dinosaurCenterX + ", " + dinosaurCenterY + ") with velX=" + velX + ", velY=" + velY);
    
    
    if (this.map != null) {
        this.map.addNPC(projectile);
        System.out.println("Projectile added to map.");
    } else {
        System.out.println("ERROR: Map is null, cannot add projectile!");
    }
}



    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {
            {
                put("STAND_LEFT", new Frame[] {
                        new FrameBuilder(spriteSheet.getSprite(0, 0))
                                .withScale(2)
                                .withBounds(7, 15, 40, 40)
                                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                                .build()
                });
                put("STAND_RIGHT", new Frame[] {
                        new FrameBuilder(spriteSheet.getSprite(0, 0))
                                .withScale(2)
                                .withBounds(7, 15, 40, 40)
                                .build()
                });

                put("WALK_LEFT", new Frame[] {
                        new FrameBuilder(spriteSheet.getSprite(0, 1),15)
                                .withScale(2)
                .withBounds(7, 15, 40, 40)
                                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                                .build(),
                        new FrameBuilder(spriteSheet.getSprite(0, 0),15)
                                .withScale(2)
                .withBounds(7, 15, 40, 40)
                                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                                .build()
                });

                put("WALK_RIGHT", new Frame[] {
                        new FrameBuilder(spriteSheet.getSprite(0, 1),15)
                                .withScale(2)
                .withBounds(7, 15, 40, 40)
                                .build(),
                        new FrameBuilder(spriteSheet.getSprite(0, 0),15)
                                .withScale(2)
                .withBounds(7, 15, 40, 40)
                                .build()
                });

                put("SHOOT_LEFT", new Frame[] {
                        new FrameBuilder(spriteSheet.getSprite(1, 2),40)
                                .withScale(2)
                .withBounds(7, 15, 40, 40)
                                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                                .build(),
                        new FrameBuilder(spriteSheet.getSprite(1, 1),80)
                                .withScale(2)
                .withBounds(7, 15, 40, 40)
                                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                                .build(),
                });

                put("SHOOT_RIGHT", new Frame[] {
                        new FrameBuilder(spriteSheet.getSprite(1, 2),40)
                                .withScale(2)
                .withBounds(7, 15, 40, 40)
                                .build(),
                        new FrameBuilder(spriteSheet.getSprite(1, 1),80)
                                .withScale(2)
                .withBounds(7, 15, 40, 40)
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
