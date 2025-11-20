/*NOTe TO PROFeSSOR:
Due to my e key being broken after finishing i threw this entire file into GPT to capitalize them properly where they should be
It also decided to make some other changes to this for formatting (such as adding extra comment lines with -'s where I have regions), but as far as I can tell there were no actual code changes
*/
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
import Level.*;

public class Boss extends NPC {

    public static boolean bossSpawned = false;

// Order for the first rotation:
private bossState[] initialOrder = {
    bossState.CHARGER,
    bossState.SHOOTER,
    bossState.SPREAD,
    bossState.ORBITAL,
    bossState.OVERHEAT
};

 public Boss(int id, Point location) {
        super(id, location.x, location.y, new Frame(ImageLoader.load("MrToon.png")));
        System.out.println("Boss made");
    }

private int initialIndex = 0;
private boolean firstCycleDone = false;

// For random mode rotation afterward
private bossState[] randomPool = bossState.values();
private bossState lastState;


    //#region === BOSS MODES ENUM ===
    public enum bossState {
        CHARGER,
        SHOOTER,
        SPREAD,
        ORBITAL,
        OVERHEAT
    }
    //#endregion

    //#region === SHARED VARIABLES ===
    private int health = 1000000000;

    private Random rand = new Random();

    private float phaseChangeLower = 5000f;
    private float phaseChangeUpper = 10000f;
    private float timeToNextChange;
    private float dt = 13.33333f;

    private BossMode currentMode;

    private float wanderSpeed = 1.4f;
    private float chargeAttackSpeed = 6.0f;
    private float detectionRange = 400f;
    private bossState currState;

    //#endregion


    // ---------------------------------------------------------
    //  BOSS MODE SYSTEM
    // ---------------------------------------------------------

    //#region === MODE INTERFACE ===
    private interface BossMode {
        void enter();
        void update(float dt, Player player);
        void exit();
    }
    //#endregion

    // ---------------------------------------------------------
    //  MODE IMPLEMENTATIONS
    // ---------------------------------------------------------

    //#region === CHARGER MODE ===
    private class ChargerMode implements BossMode {

        private enum InternalState { WANDER, CHARGE, ATTACK }
        private InternalState internal = InternalState.WANDER;

        private float wanderTimer=10f;
        private Point wanderTarget;
        private float chargeTime = 10f;

        @Override
        public void enter() {
            System.out.println("Entered CHARGER mode!");
            pickNewWanderTarget();
        }

        @Override
        public void update(float dt, Player player) {

            switch (internal) {

                case WANDER:
                    wanderTimer -= dt;
                    moveToward(wanderTarget, wanderSpeed);

                    if (wanderTimer <= 0)
                        pickNewWanderTarget();

                    if (distanceToPlayer(player) < detectionRange)
                        internal = InternalState.CHARGE;
                    break;


                case CHARGE:
                    moveToward(player.getLocation(), chargeAttackSpeed);
                    chargeTime-=dt;
                    if(chargeTime<=0){
                        chargeTime=10;
                        internal=InternalState.WANDER;
                    }

                    if (touching(player))
                        internal = InternalState.ATTACK;
                    break;


                case ATTACK:
                    // deal damage or animation
                    internal = InternalState.WANDER;
                    pickNewWanderTarget();
                    break;
            }
        }

        @Override
        public void exit() {
            System.out.println("Exiting CHARGER mode.");
        }

        private void pickNewWanderTarget() {
            wanderTimer = 2 + rand.nextFloat() * 2;
            wanderTarget = getRandomPointNearCenter();
        }
    }
    //#endregion


    //#region === SHOOTER MODE ===
    private class ShooterMode implements BossMode {

        private float shootTimer = 1000f;

        @Override
        public void enter() {
            System.out.println("Entered SHOOTER mode!");
            shootTimer = 1000f;
        }

        @Override
        public void update(float dt, Player player) {
            shootTimer -= dt;

            if (shootTimer <= 0) {
                fireProjectile(player);
                shootTimer = 1000f;
            }
        }

        @Override
        public void exit() {
            System.out.println("Exiting SHOOTER mode.");
        }
    }
    //#endregion


    //#region === SPREAD MODE ===
    private class SpreadMode implements BossMode {

        private float spreadTimer;

        @Override
        public void enter() {
            System.out.println("Entered SPREAD mode!");
            spreadTimer = 2000f;
        }

        @Override
        public void update(float dt, Player player) {
            spreadTimer -= dt;

            if (spreadTimer <= 0) {
                fireCircularSpread(12, 4.0f);
                spreadTimer = 2000f;
            }
        }

        @Override
        public void exit() {
            System.out.println("Exiting SPREAD mode.");
        }
    }
    //#endregion


    //#region === OVERHEAT MODE ===
    private class OverheatMode implements BossMode {

        private float overheatTimer;

        @Override
        public void enter() {
            System.out.println("Entered OVERHEAT mode! Boss vulnerable.");
            overheatTimer = 3.0f;
        }

        @Override
        public void update(float dt, Player player) {
            overheatTimer -= dt;

            // boss does nothing (stunned)

            if (overheatTimer <= 0) {
                updateBossState(); // auto-exit
            }
        }

        @Override
        public void exit() {
            System.out.println("Exiting OVERHEAT mode.");
        }
    }
    //#endregion



    // ---------------------------------------------------------
    //  MODE MANAGEMENT
    // ---------------------------------------------------------

    //#region === STATE TRANSITION ===

    private BossMode chargerMode = new ChargerMode();
    private BossMode shooterMode = new ShooterMode();
    private BossMode spreadMode  = new SpreadMode();
    private BossMode overheatMode = new OverheatMode();
    // Add orbital mode here later if needed.

    private bossState[] modeOrder = {
            bossState.CHARGER,
            bossState.SHOOTER,
            bossState.SPREAD,
            bossState.OVERHEAT,
    };
    private int modeIndex = 0;

    private void applyState(bossState newState) {
        if (currentMode != null)
            currentMode.exit();

        currState = newState;

        switch (newState) {
            case CHARGER:   currentMode = chargerMode; break;
            case SHOOTER:   currentMode = shooterMode; break;
            case SPREAD:    currentMode = spreadMode; break;
            case OVERHEAT:  currentMode = overheatMode; break;
        }

        currentMode.enter();
    }

public void updateBossState() {

    // FIRST ROTATION — fixed order
    if (!firstCycleDone) {
        bossState next = initialOrder[initialIndex];
        initialIndex++;

        // Finished the entire fixed order → switch to random system
        if (initialIndex >= initialOrder.length) {
            firstCycleDone = true;
        }

        lastState = next;
        applyState(next);
        return;
    }


    // RANDOM ROTATION (never picks same mode twice)
    bossState nextRandom;

    do {
        nextRandom = randomPool[rand.nextInt(randomPool.length)];
    } while (nextRandom == lastState); // avoid repeat

    lastState = nextRandom;
    applyState(nextRandom);
}

    //#endregion

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


    // ---------------------------------------------------------
    //  UPDATE FUNCTION — CALLED FROM GAME LOOP
    // ---------------------------------------------------------

        @Override
    public void update(Player player) {
        super.update(player);
        //System.out.println("Im the boss");
        timeToNextChange -= dt;
        if (timeToNextChange <= 0) {
            resetPhaseChangeTimer();
            updateBossState();
        }

        if (currentMode != null)
            currentMode.update(dt, player);
    }



    // ---------------------------------------------------------
    //  UTILITY FUNCTIONS
    // ---------------------------------------------------------

    private void resetPhaseChangeTimer() {
        timeToNextChange = phaseChangeLower + rand.nextFloat() * (phaseChangeUpper - phaseChangeLower);
    }

    private float distanceToPlayer(Player player) {
        float dx = player.getX() - this.getX();
        float dy = player.getY() - this.getY();
        return (float) Math.sqrt(dx*dx + dy*dy);
    }

    private void moveToward(Point target, float speed) {
        float dx = target.x - getX();
        float dy = target.y - getY();
        float dist = (float)Math.sqrt(dx*dx + dy*dy);

        if (dist > 1) {
            float vx = (dx / dist) * speed;
            float vy = (dy / dist) * speed;
            this.moveX(vx);
            this.moveY(vy);
        }
    }

    private Point getRandomPointNearCenter() {
        return new Point(
                (int)(getX() + rand.nextInt(200) - 100),
                (int)(getY() + rand.nextInt(200) - 100)
        );
    }

    // Your existing projectile function
private void fireProjectile(Player player) {
    float playerCenterX = player.getX() + 18.0f;
    float playerCenterY = player.getY() + 10.5f;
    float dinosaurCenterX = this.x + 16.5f;
    float dinosaurCenterY = this.y + 10.5f;
    //float distanceToPlayer = (float) Math.hypot(playerCenterX - dinosaurCenterX, playerCenterY - dinosaurCenterY);
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

    private void fireCircularSpread(int count, float speed) {
        System.out.println("Firing spread!");
        double angleStep = (Math.PI * 2) / count;

        for (int i = 0; i < count; i++) {
            double angle = i * angleStep;
            float vx = (float)(Math.cos(angle) * speed);
            float vy = (float)(Math.sin(angle) * speed);

            EnemyProjectile p = new EnemyProjectile(getX(), getY(), vx, vy, 300);
            map.addNPC(p);
        }
    }

        @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }
}
