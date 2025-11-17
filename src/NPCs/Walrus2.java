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
import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Random;

public class Walrus2 extends NPC {

    private enum State {
        WANDERING,
        CHARGING,
        CHARGING_ATTACK
    }

    // Health
    private int health = 100;

    // Movement Behavior
    private float wanderSpeed = 1.0f;
    private float chaseSpeed = 1.5f;
    private float chargeDx = 0.0f;
    private float chargeDy = 0.0f;
    private float detectionRange = 200.0f;
    private int wanderDirection = 1; // 0=up, 1=right, 2=down, 3=left
    private long lastDirectionChange = 0; // Timestamp for changing wander direction
    private long chargeAttackStartTime;
    private Random random = new Random();

    // Charge Behavior
    private State currentState = State.WANDERING; // Start in current wandering state
    private long chargeStartTime;
    private Point targetPosition; // Record player position to charge toward
    private float chargeAttackSpeed = 5.0f;
    private float chargeStopDistance = 5.0f;

    // Hit Indicator
    private boolean isHit = false;
    private long hitStartTime;
    private final int HIT_DURATION = 500;

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
        this.isHit = true;
        this.hitStartTime = System.currentTimeMillis();
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

        if (currentState == State.CHARGING) {
            System.out.println("Walrus entered CHARGING state");
        }

        // Get player and enemy center then calculate distance between them for attack
        float playerCenterX = player.getX() + 1.0f;
        float playerCenterY = player.getY() + 1.0f;
        float walrusCenterX = this.x + 16.5f;
        float walrusCenterY = this.y + 10.5f;
        float distanceToPlayer = (float) Math.hypot(playerCenterX - walrusCenterX, playerCenterY - walrusCenterY);

        long currentTime = System.currentTimeMillis();
        // Change to Charge state when the player is detected
        switch (currentState) {
            case WANDERING:

                if (distanceToPlayer < detectionRange) {
                    currentState = State.CHARGING;
                    chargeStartTime = currentTime;
                    targetPosition = new Point(playerCenterX, playerCenterY); // Record player's position
                } else {
                    // Wait a few seconds after charge to return back to random Wandering state
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
                // Stay in place for 1 second to indicate charge will happen
                if (currentTime - chargeStartTime >= 1000) {
                    currentState = State.CHARGING_ATTACK;
                }

                break;

            case CHARGING_ATTACK:
                // Record the start time of charge attack for reset mechanic
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
                // Charge toward the locked location (player's last known position)
                dx = chargeDx;
                dy = chargeDy;
                isMoving = true;

                float currentTargetDx = targetPosition.x - walrusCenterX;
                float currentTargetDy = targetPosition.y - walrusCenterY;
                float distanceToTarget = (float) Math.hypot(currentTargetDx, currentTargetDy);

                // Reset if player is no longer detected or after 1 second of charge without
                // detection
                if (currentTime - chargeAttackStartTime >= 1000 || distanceToTarget <= 10.0f) {
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
            //takeDamage(1);
            PlayLevelScreen.player.takeDamage(10);
        }

        if (player.isInvincible) {
            if (currentTime - player.invStartTime >= player.invDuration) {
                player.isInvincible = false;
            }
        }

        // Flip depending on direction the enemy is facing the player
        if (dx < 0) {
            this.currentAnimationName = "CHARGE_LEFT";
        } else if (dx > 0) {
            this.currentAnimationName = "CHARGE_RIGHT";
        }

        if (isHit && System.currentTimeMillis() - hitStartTime > HIT_DURATION) {
            isHit = false;
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
                                .withBounds(7, 13, 30, 40)
                                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                                .build()
                });
                put("STAND_RIGHT", new Frame[] {
                        new FrameBuilder(spriteSheet.getSprite(0, 0))
                                .withScale(2)
                                .withBounds(7, 13, 30, 40)
                                .build()
                });

                put("WALK_LEFT", new Frame[] {
                        new FrameBuilder(spriteSheet.getSprite(0, 1), 15)
                                .withScale(2)
                                .withBounds(7, 15, 30, 40)
                                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                                .build(),
                        new FrameBuilder(spriteSheet.getSprite(0, 0), 15)
                                .withScale(2)
                                .withBounds(7, 15, 30, 40)
                                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                                .build()
                });

                put("WALK_RIGHT", new Frame[] {
                        new FrameBuilder(spriteSheet.getSprite(0, 1), 15)
                                .withScale(2)
                                .withBounds(7, 15, 30, 40)
                                .build(),
                        new FrameBuilder(spriteSheet.getSprite(0, 0), 15)
                                .withScale(2)
                                .withBounds(7, 15, 30, 40)
                                .build()
                });

                put("CHARGE_LEFT", new Frame[] {
                        new FrameBuilder(spriteSheet.getSprite(1, 2), 40)
                                .withScale(2)
                                .withBounds(7, 15, 30, 40)
                                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                                .build(),
                        new FrameBuilder(spriteSheet.getSprite(1, 1), 140)
                                .withScale(2)
                                .withBounds(7, 15, 30, 40)
                                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                                .build(),
                });

                put("CHARGE_RIGHT", new Frame[] {
                        new FrameBuilder(spriteSheet.getSprite(1, 2), 40)
                                .withScale(2)
                                .withBounds(7, 15, 30, 40)
                                .build(),
                        new FrameBuilder(spriteSheet.getSprite(1, 1), 140)
                                .withScale(2)
                                .withBounds(7, 15, 30, 40)
                                .build()
                });
            }
        };
    }

    // Hit Indicator: Blinking Effect and Time Duration
    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        if (isHit) {
            long currentTime = System.currentTimeMillis();

            if (currentTime % 200 < 100) {
                super.draw(graphicsHandler);
            }
        } else {
            super.draw(graphicsHandler);
        }
    }
}
