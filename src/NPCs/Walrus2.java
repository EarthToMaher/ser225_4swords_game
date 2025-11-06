package NPCs;

//Imports
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

// This class is for the walrus NPC
public class Walrus2 extends NPC {

    private enum State {
        WANDERING,
        CHARGING,
        CHARGING_ATTACK
    }

    // Health
    private int health = 100;

    // Movement Behavior
    private float wanderSpeed = 1.0f; // Slow speed for wandering
    private float chaseSpeed = 1.5f; // Faster speed for chasing
    private float chargeDx = 0.0f;
    private float chargeDy = 0.0f;
    private float detectionRange = 200.0f; // Start chasing if player within this
    private int wanderDirection = 1; // 0=up, 1=right, 2=down, 3=left
    private long lastDirectionChange = 0; // Timestamp for changing wander direction
    private long chargeAttackStartTime;
    private Random random = new Random();

    // Charge Behavior
    private State currentState = State.WANDERING; // Start in current wandering state
    private long chargeStartTime; // Begin charge attack
    private Point targetPosition; // Record player position to charge toward
    private float chargeAttackSpeed = 5.0f; // Charge attack speed
    private float chargeStopDistance = 5.0f; // Distance when charge attack stops

    public Walrus2(int id, Point location) {
        super(id, location.x, location.y, new SpriteSheet(ImageLoader.load("BigEnemyTest.png"), 64, 64), "STAND_RIGHT");
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
        float dx = 0.0f;
        float dy = 0.0f;
        boolean isMoving = false;

        // Get player and enemy center and calculate distance
        float playerCenterX = player.getX() + 10.0f;
        float playerCenterY = player.getY() + 10.5f;
        float walrusCenterX = this.x + 16.5f;
        float walrusCenterY = this.y + 10.5f;
        float distanceToPlayer = (float) Math.hypot(playerCenterX - walrusCenterX, playerCenterY - walrusCenterY);

        long currentTime = System.currentTimeMillis();

        switch (currentState) {
            case WANDERING:

                if (distanceToPlayer < detectionRange) {
                    currentState = State.CHARGING;
                    chargeStartTime = currentTime;
                    targetPosition = new Point(playerCenterX, playerCenterY); // Record player's position
                } else {

                    if (currentTime - lastDirectionChange > (1000 + random.nextInt(2000))) {
                        wanderDirection = random.nextInt(4);
                        lastDirectionChange = currentTime;
                    }
                    switch (wanderDirection) {
                        case 0:
                            dy = -wanderSpeed;
                            break; // Wander up
                        case 1:
                            dx = wanderSpeed;
                            this.setCurrentAnimationName("WALK_RIGHT");
                            break; // Wander right
                        case 2:
                            dy = wanderSpeed;
                            break; // Wander down
                        case 3:
                            dx = -wanderSpeed;
                            this.setCurrentAnimationName("WALK_LEFT");
                            break; // Wander left
                    }
                    isMoving = true;
                }
                break;

            case CHARGING:
                // Stay in place for 1 second
                if (currentTime - chargeStartTime >= 1000) {
                    currentState = State.CHARGING_ATTACK;
                }

                break;

            case CHARGING_ATTACK:
                // Record start time of charge attack
                if (chargeAttackStartTime == 0) {
                    chargeAttackStartTime = currentTime;
                }

                // Move in straight line toward recorded player position
                if (chargeDx == 0.0f && chargeDy == 0.0f) {
                    float targetDx = targetPosition.x - walrusCenterX;
                    float targetDy = targetPosition.y - walrusCenterY;
                    chargeDx = Math.signum(targetDx) * chargeAttackSpeed;
                    chargeDy = Math.signum(targetDy) * chargeAttackSpeed;
                }
                // Charge toward locked location
                dx = chargeDx;
                dy = chargeDy;
                isMoving = true;

                float currentTargetDx = targetPosition.x - walrusCenterX;
                float currentTargetDy = targetPosition.y - walrusCenterY;
                float distanceToTarget = (float) Math.hypot(currentTargetDx, currentTargetDy);

                if (currentTime - chargeAttackStartTime >= 2000 || distanceToTarget <= 10.0f) {
                    currentState = State.WANDERING;
                    chargeDx = 0.0f;
                    chargeDy = 0.0f;
                    chargeAttackStartTime = 0;
                }
                break;
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
            takeDamage(1);
            PlayLevelScreen.player.takeDamage(1);
        }

        // Flip depending on direction
        if (dx < 0) {
            this.currentAnimationName = "CHARGE_LEFT";
        } else if (dx > 0) {
            this.currentAnimationName = "CHARGE_RIGHT";
        }

        super.update(player);
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {
            {
                put("STAND_LEFT", new Frame[] {
                        new FrameBuilder(spriteSheet.getSprite(0, 0))
                                .withScale(2)
                                .withBounds(7, 13, 30, 20)
                                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                                .build()
                });
                put("STAND_RIGHT", new Frame[] {
                        new FrameBuilder(spriteSheet.getSprite(0, 0))
                                .withScale(2)
                                .withBounds(7, 13, 30, 20)
                                .build()
                });

                put("WALK_LEFT", new Frame[] {
                        new FrameBuilder(spriteSheet.getSprite(0, 1),15)
                                .withScale(2)
                                .withBounds(7, 15, 30, 20)
                                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                                .build(),
                        new FrameBuilder(spriteSheet.getSprite(0, 0),15)
                                .withScale(2)
                                .withBounds(7, 15, 30, 20)
                                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                                .build()
                });

                put("WALK_RIGHT", new Frame[] {
                        new FrameBuilder(spriteSheet.getSprite(0, 1),15)
                                .withScale(2)
                                .withBounds(7, 15, 30, 20)
                                .build(),
                        new FrameBuilder(spriteSheet.getSprite(0, 0),15)
                                .withScale(2)
                                .withBounds(7, 15, 30, 20)
                                .build()
                });

                put("CHARGE_LEFT", new Frame[] {
                        new FrameBuilder(spriteSheet.getSprite(1, 2),40)
                                .withScale(2)
                                .withBounds(7, 15, 30, 20)
                                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                                .build(),
                        new FrameBuilder(spriteSheet.getSprite(1, 1),140)
                                .withScale(2)
                                .withBounds(7, 15, 30, 20)
                                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                                .build(),
                });

                put("CHARGE_RIGHT", new Frame[] {
                        new FrameBuilder(spriteSheet.getSprite(1, 2),40)
                                .withScale(2)
                                .withBounds(7, 15, 30, 20)
                                .build(),
                        new FrameBuilder(spriteSheet.getSprite(1, 1),140)
                                .withScale(2)
                                .withBounds(7, 15, 30, 20)
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
