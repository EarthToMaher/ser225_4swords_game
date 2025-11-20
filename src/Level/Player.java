package Level;

import Engine.Key;
import Engine.KeyLocker;
import Engine.Keyboard;
import Game.ScreenCoordinator;
import GameObject.Frame;
import GameObject.GameObject;
import GameObject.Rectangle;
import GameObject.SpriteSheet;
import NPCs.ElectricBall;
import Players.Robot;
import Players.SecondRobot;
import Utils.Direction;
import NPCs.Walrus2;
import Items.Item;

public abstract class Player extends GameObject {
    // values that affect player movement
    // these should be set in a subclass
    protected float walkSpeed = 0;
    protected int interactionRange = 1;
    protected Direction currentWalkingXDirection;
    protected Direction currentWalkingYDirection;
    protected Direction lastWalkingXDirection;
    protected Direction lastWalkingYDirection;
    public Item currentItem;

    // values used to handle player movement
    protected float moveAmountX, moveAmountY;
    protected float lastAmountMovedX, lastAmountMovedY;

    // values used to keep track of player's current state
    protected PlayerState playerState;
    protected PlayerState previousPlayerState;
    protected Direction facingDirection;
    protected Direction lastMovementDirection;

    // define keys
    protected static KeyLocker keyLocker = new KeyLocker();
    protected Key MOVE_LEFT_KEY = Key.LEFT;
    protected Key MOVE_RIGHT_KEY = Key.RIGHT;
    protected Key MOVE_UP_KEY = Key.UP;
    protected Key MOVE_DOWN_KEY = Key.DOWN;
    protected Key INTERACT_KEY = Key.SPACE;
    protected Key PROJECTILE_KEY = Key.K;
    protected Key ATTACK_KEY = Key.E;
    protected Key C_KEY = Key.C;

    // --- MERGED KEYS ---
    protected Key BOOMERANG_KEY =  Key.Q;     // From Class 2
    protected Key DASH_KEY = Key.R;          // From Class 1

    protected int currencyAmount = 0;
    private boolean isThrowingBoomerang;
    private int timeBetweenReloads = 0;
    private boolean isFiring = false;
    private static int ammo = 5;
    private boolean hasHitThisAttack = false;
    protected boolean isLocked = false;
    protected boolean hasKey = false;

    // --- DASH FIELDS (From Class 1) ---
    protected boolean isDashing = false;
    private int timeAfterDash = 0;

    // --- HEALTH/DAMAGE FIELDS (From Class 2) ---
    private int health = 60; // From Class 2
    private boolean isInjured = false;
    public boolean isInvincible = false;
    public long invStartTime;
    public final long invDuration = 800;

    // Map transition fields
    private String pendingMapName = null;
    private Utils.Point pendingMapLocation = null;

    public Player(SpriteSheet spriteSheet, float x, float y, String startingAnimationName) {
        super(spriteSheet, x, y, startingAnimationName);
        facingDirection = Direction.RIGHT;
        playerState = PlayerState.STANDING;
        previousPlayerState = playerState;
        this.affectedByTriggers = true;
    }

    public void CollectKey(){hasKey=true;}

    public void gainCurrency(int amount) {currencyAmount+=amount;}

    public boolean spendCurrency(int amount){
        if (amount <= currencyAmount){
            currencyAmount -= amount;
            return true;
        }
        return false;
    }

    public int getCurrency(){
        return currencyAmount;
    }

    public boolean isInjured() {
        return isInjured;
    }

    // --- onDeath (From Class 1, includes lock()) ---
    protected void onDeath() {
        if (isInjured) return;
        isInjured = true;
        lock(); // From Class 1
        this.currentAnimationName = "INJURED";
        resetAnimationToFirstFrame();
    }

    public void setItem(Item item){currentItem = item;}

    // --- takeDamage (From Class 2, includes invincibility) ---
    public void takeDamage(int damageAmount) {
        if (!isInvincible) {
            this.health -= damageAmount;
            isInvincible = true;
            invStartTime = System.currentTimeMillis();
            System.out.println(this.health);
            if(isInjured) return;
            // System.out.println(this.health); // Removed duplicate print and double damage bug from Class 1
            if (this.health <= 0) {
                this.health = 0; //Ensure health wont be negative
                onDeath();
            }
        }
    }

    public int getHealth() {
        return health;
    }

    public void requestMapTransition(String mapName, Utils.Point location) {
        this.pendingMapName = mapName;
        this.pendingMapLocation = location;
        this.currentItem = null;
    }

    public boolean hasPendingMapRequest() {
        return pendingMapName != null;
    }

    public String consumePendingMapName() {
        String temp = pendingMapName;
        pendingMapName = null;
        return temp;
    }

    public Utils.Point consumePendingMapLocation() {
        Utils.Point temp = pendingMapLocation;
        pendingMapLocation = null;
        return temp;
    }

    public void update() {

        // --- MERGED LOGIC ---

        // Added: Logic to reset invincibility (from Class 2's fields)
        if (isInvincible) {
            if (System.currentTimeMillis() - invStartTime > invDuration) {
                isInvincible = false;
            }
        }

        if (currentItem != null) {
            currentItem.update(this);
        }

        // Ammo reload logic (present in both)
        timeBetweenReloads++;
        if(timeBetweenReloads >= 100 && ammo < 5) {
            ammo++;
            timeBetweenReloads = 0;
        } else if(timeBetweenReloads >= 100) {
            timeBetweenReloads = 0;
        }

        // Dash logic (from Class 1)
        if(isDashing) {
            timeAfterDash++;
            if(timeAfterDash >= 15) {
                isDashing = false;
                this.walkSpeed = 4F; // Resets to base walk speed (assuming 4F)
            }
        }

        if(!keyLocker.isKeyLocked(DASH_KEY) && Keyboard.isKeyDown(DASH_KEY)) {
            keyLocker.lockKey(DASH_KEY);
            if(ammo > 1 && !isDashing) {
                isDashing = true;
                timeAfterDash = 0;
                ammo -=2;
                this.walkSpeed = 9F; // Dash speed
            }
        }

        // --- END MERGED LOGIC ---


        if (!isLocked) {
            moveAmountX = 0;
            moveAmountY = 0;

            // if player is currently playing through level (has not won or lost)
            // update player's state and current actions, which includes things like determining how much it should move each frame and if its walking or jumping
            do {
                previousPlayerState = playerState;
                handlePlayerState();
            } while (previousPlayerState != playerState);

            // move player with respect to map collisions based on how much player needs to move this frame
            lastAmountMovedY = super.moveYHandleCollision(moveAmountY);
            lastAmountMovedX = super.moveXHandleCollision(moveAmountX);
        }

        handlePlayerAnimation();

        updateLockedKeys();

        // update player's animation
        super.update();

        //Prevent player from going out of bounds
        if(map != null){
            float minX = 0;
            float minY = 0;
            float maxX = map.getWidthPixels() - getBounds().getWidth();
            float maxY = map.getHeightPixels() - getBounds().getHeight();

            if (this.x < minX) this.x = minX;
            if (this.y < minY) this.y = minY;
            if (this.x > maxX) this.x = maxX;
            if (this.y > maxY) this.y = maxY;
        }
    }

    public boolean hasKey() { return hasKey; }
    public void setHasKey(boolean value) { hasKey = value; }

    // based on player's current state, call appropriate player state handling method
    protected void handlePlayerState() {
        switch (playerState) {
            case STANDING:
                playerStanding();
                break;
            case WALKING:
                playerWalking();
                break;
        }
    }


    // player STANDING state logic
    protected void playerStanding() {
        if (!keyLocker.isKeyLocked(INTERACT_KEY) && Keyboard.isKeyDown(INTERACT_KEY)) {
            keyLocker.lockKey(INTERACT_KEY);
            System.out.println("Space Test!");
            map.entityInteract(this);
        }

        // Uses BOOMERANG_KEY (set to Q)
        if (!keyLocker.isKeyLocked(BOOMERANG_KEY)&& Keyboard.isKeyDown(BOOMERANG_KEY))
        {
            keyLocker.lockKey(INTERACT_KEY);
            //System.out.println("m hitting enter");
            //isThrowingBoomerang = true;
            if (currentItem != null&&!currentItem.itemIsActive) currentItem.UseItem(this);
        }


        if (!keyLocker.isKeyLocked(ATTACK_KEY) && Keyboard.isKeyDown(ATTACK_KEY)) {
            keyLocker.lockKey(ATTACK_KEY);
            this.currentAnimationName = facingDirection == Direction.RIGHT ? "ATTACK_RIGHT" : "ATTACK_LEFT";

            if (ammo > 0 && map != null) {
                ElectricBall ball = new ElectricBall(12, getLocation());
                ball.setDirection(facingDirection);
                map.addNPC(ball);
                ammo--;
            }
        }



        // if a walk key is pressed, player enters WALKING state
        if (Keyboard.isKeyDown(MOVE_LEFT_KEY) || Keyboard.isKeyDown(MOVE_RIGHT_KEY) || Keyboard.isKeyDown(MOVE_UP_KEY) || Keyboard.isKeyDown(MOVE_DOWN_KEY)) {
            playerState = PlayerState.WALKING;
        }

        // If the c key is pressed, player enters SWITCHING state
        if(!keyLocker.isKeyLocked(C_KEY) && Keyboard.isKeyDown(C_KEY)) {
            keyLocker.lockKey(C_KEY);
            if(Robot.isActivePlayer) {
                Robot.isActivePlayer = false;
                SecondRobot.isActivePlayer = true;
                System.out.println(Robot.isActivePlayer);
                System.out.println(SecondRobot.isActivePlayer);
            } else if(SecondRobot.isActivePlayer) {
                Robot.isActivePlayer = true;
                SecondRobot.isActivePlayer = false;
                System.out.println(Robot.isActivePlayer);
                System.out.println(SecondRobot.isActivePlayer);
            }
        }


    }

    // player WALKING state logic
    protected void playerWalking() {
        if (!keyLocker.isKeyLocked(INTERACT_KEY) && Keyboard.isKeyDown(INTERACT_KEY)) {
            keyLocker.lockKey(INTERACT_KEY);
            map.entityInteract(this);
        }

        if (!keyLocker.isKeyLocked(ATTACK_KEY) && Keyboard.isKeyDown(ATTACK_KEY)) {
            keyLocker.lockKey(ATTACK_KEY);
            this.currentAnimationName = facingDirection == Direction.RIGHT ? "ATTACK_RIGHT" : "ATTACK_LEFT";

            if (ammo > 0 && map != null) {
                ElectricBall ball = new ElectricBall(12, getLocation());
                ball.setDirection(facingDirection);
                map.addNPC(ball);
                ammo--;

            }
        }

        // Uses BOOMERANG_KEY (set to Q)
        if (!keyLocker.isKeyLocked(BOOMERANG_KEY)&& Keyboard.isKeyDown(BOOMERANG_KEY))
        {
            keyLocker.lockKey(INTERACT_KEY);
            //isThrowingBoomerang = true;
            if (currentItem != null&&!currentItem.itemIsActive) currentItem.UseItem(this);
        }


        //If space key is pressed while walking, switching should also occur
        if(!keyLocker.isKeyLocked(C_KEY) && Keyboard.isKeyDown(C_KEY)) {
            keyLocker.lockKey(C_KEY);

            ScreenCoordinator sc = map.getScreenCoordinator();

            if(Robot.isActivePlayer) {
                Robot.isActivePlayer = false;
                SecondRobot.isActivePlayer = true;
                System.out.println(Robot.isActivePlayer);
                System.out.println(SecondRobot.isActivePlayer);
            } else {
                Robot.isActivePlayer = true;
                SecondRobot.isActivePlayer = false;
                System.out.println(Robot.isActivePlayer);
                System.out.println(SecondRobot.isActivePlayer);
            }
        }

        // if walk left key is pressed, move player to the left
        if (Keyboard.isKeyDown(MOVE_LEFT_KEY)) {
            moveAmountX -= walkSpeed;
            facingDirection = Direction.LEFT;
            currentWalkingXDirection = Direction.LEFT;
            lastWalkingXDirection = Direction.LEFT;
        }

        // if walk right key is pressed, move player to the right
        else if (Keyboard.isKeyDown(MOVE_RIGHT_KEY)) {
            moveAmountX += walkSpeed;
            facingDirection = Direction.RIGHT;
            currentWalkingXDirection = Direction.RIGHT;
            lastWalkingXDirection = Direction.RIGHT;
        }
        else {
            currentWalkingXDirection = Direction.NONE;
        }
        if (Keyboard.isKeyDown(MOVE_UP_KEY)) {
            moveAmountY -= walkSpeed;
            currentWalkingYDirection = Direction.UP;
            lastWalkingYDirection = Direction.UP;
        }
        else if (Keyboard.isKeyDown(MOVE_DOWN_KEY)) {
            moveAmountY += walkSpeed;
            currentWalkingYDirection = Direction.DOWN;
            lastWalkingYDirection = Direction.DOWN;
        }
        else {
            currentWalkingYDirection = Direction.NONE;
        }

        if ((currentWalkingXDirection == Direction.RIGHT || currentWalkingXDirection == Direction.LEFT) && currentWalkingYDirection == Direction.NONE) {
            lastWalkingYDirection = Direction.NONE;
        }

        if ((currentWalkingYDirection == Direction.UP || currentWalkingYDirection == Direction.DOWN) && currentWalkingXDirection == Direction.NONE) {
            lastWalkingXDirection = Direction.NONE;
        }

        if (Keyboard.isKeyUp(MOVE_LEFT_KEY) && Keyboard.isKeyUp(MOVE_RIGHT_KEY) && Keyboard.isKeyUp(MOVE_UP_KEY) && Keyboard.isKeyUp(MOVE_DOWN_KEY)) {
            playerState = PlayerState.STANDING;
        }
    }



    public boolean getIsThrowingBoomerang(){return isThrowingBoomerang;}

    // --- updateLockedKeys (From Class 1, includes DASH_KEY) ---
    protected void updateLockedKeys() {
        if (Keyboard.isKeyUp(INTERACT_KEY) && !isLocked) {
            keyLocker.unlockKey(INTERACT_KEY);
        }

        if (Keyboard.isKeyUp(DASH_KEY) && !isLocked) {
            keyLocker.unlockKey(DASH_KEY);
        }

        if (Keyboard.isKeyUp(BOOMERANG_KEY)&&!isLocked){
            keyLocker.unlockKey(BOOMERANG_KEY);
            isThrowingBoomerang = false;
        }

        if (Keyboard.isKeyUp(C_KEY) && !isLocked) {
            keyLocker.unlockKey(C_KEY);
        }
        if (Keyboard.isKeyUp(ATTACK_KEY)) {
            keyLocker.unlockKey(ATTACK_KEY);
        }
    }

    // anything extra the player should do based on interactions can be handled here
    protected void handlePlayerAnimation() {
        switch (playerState) {
            case STANDING:
                currentAnimationName = facingDirection == Direction.RIGHT ? "STAND_RIGHT" : "STAND_LEFT";
                break;
            case WALKING:
                currentAnimationName = facingDirection == Direction.RIGHT ? "WALK_RIGHT" : "WALK_LEFT";
                break;
        }
    }

    @Override
    public void onEndCollisionCheckX(boolean hasCollided, Direction direction, GameObject entityCollidedWith) { }

    @Override
    public void onEndCollisionCheckY(boolean hasCollided, Direction direction, GameObject entityCollidedWith) { }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    public Direction getFacingDirection() {
        return facingDirection;
    }

    public void setFacingDirection(Direction facingDirection) {
        this.facingDirection = facingDirection;
    }

    public boolean getIsFiring() {return isFiring;}

    public void setIsFiring(boolean firing) {this.isFiring = firing;}

    public int getAmmo() {return ammo;}

    public void setAmmo(int ammo) {this.ammo = ammo;}

    public Rectangle getInteractionRange() {
        return new Rectangle(
                getBounds().getX1() - interactionRange,
                getBounds().getY1() - interactionRange,
                getBounds().getWidth() + (interactionRange * 2),
                getBounds().getHeight() + (interactionRange * 2));
    }

    public Key getInteractKey() { return INTERACT_KEY; }
    public Key getProjectileKey() { return PROJECTILE_KEY; }
    public Direction getCurrentWalkingXDirection() { return currentWalkingXDirection; }
    public Direction getCurrentWalkingYDirection() { return currentWalkingYDirection; }
    public Direction getLastWalkingXDirection() { return lastWalkingXDirection; }
    public Direction getLastWalkingYDirection() { return lastWalkingYDirection; }


    public void lock() {
        isLocked = true;
        playerState = PlayerState.STANDING;
        this.currentAnimationName = facingDirection == Direction.RIGHT ? "STAND_RIGHT" : "STAND_LEFT";
    }

    public void unlock() {
        isLocked = false;
        playerState = PlayerState.STANDING;
        this.currentAnimationName = facingDirection == Direction.RIGHT ? "STAND_RIGHT" : "STAND_LEFT";
    }

    // used by other files or scripts to force player to stand
    public void stand(Direction direction) {
        playerState = PlayerState.STANDING;
        facingDirection = direction;
        if (direction == Direction.RIGHT) {
            this.currentAnimationName = "STAND_RIGHT";
        }
        else if (direction == Direction.LEFT) {
            this.currentAnimationName = "STAND_LEFT";
        }
    }

    // used by other files or scripts to force player to walk
    public void walk(Direction direction, float speed) {
        playerState = PlayerState.WALKING;
        facingDirection = direction;
        if (direction == Direction.RIGHT) {
            this.currentAnimationName = "WALK_RIGHT";
        }
        else if (direction == Direction.LEFT) {
            this.currentAnimationName = "WALK_LEFT";
        }
        if (direction == Direction.UP) {
            moveY(-speed);
        }
        else if (direction == Direction.DOWN) {
            moveY(speed);
        }
        else if (direction == Direction.LEFT) {
            moveX(-speed);
        }
        else if (direction == Direction.RIGHT) {
            moveX(speed);
        }
    }

    protected boolean isAnimationFinished() {
        Frame[] currentAnimation = animations.get(currentAnimationName);
        int lastFrameIndex = currentAnimation.length - 1;
        return getCurrentFrameIndex() == lastFrameIndex && hasAnimationLooped;
    }

    protected void resetAnimationToFirstFrame() {
        setCurrentFrameIndex(0);
    }

    protected Rectangle getAttackHitbox() {
        int hitboxWidth = 30; // width of the attack area
        int hitboxHeight = 20; // height of the attack area
        float hitboxX = x;
        float hitboxY = y + getBounds().getHeight() / 4; // adjust Y so it's roughly at waist level

        if (facingDirection == Direction.RIGHT) {
            hitboxX += getBounds().getWidth(); // in front of player
        } else if (facingDirection == Direction.LEFT) {
            hitboxX -= hitboxWidth; // in front of player
        }

        return new Rectangle(hitboxX, hitboxY, hitboxWidth, hitboxHeight);
    }
}