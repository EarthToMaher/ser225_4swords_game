package Level;

import java.awt.Point;

import Engine.GraphicsHandler;
//import GameObject.Frame;
//import GameObject.SpriteSheet;
//import Utils.Direction;
import GameObject.Frame;
//import java.util.HashMap;
import Level.MapEntity;
import Level.Player;
import Utils.Direction;

public class Boomerang extends MapEntity{
    private Direction direction;
    private int speed = 5;
    private double lifetime = 1000; // milliseconds
    private double deltaTime = 16.67; // milliseconds, approx 60 FPS

    public Boomerang(float x, float y, Frame frame, Player player) { 
        super(x, y, frame);
        direction = player.facingDirection;
    }

    public Boomerang(float x, float y) {
        super(x, y);
    }
    public void update(Player player) {
        //this.performAction(player);
        super.update();
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
        //System.out.println("Velocity X: " + velocityX + " Velocity Y: " + velocityY);
        //moveY(velocityY);
        //moveX(velocityX);
        lifetime -= deltaTime;
        if(isExpired()){
            lifetime += deltaTime;
            if (direction == Direction.UP) {
                moveY(speed);
            }
            else if (direction == Direction.DOWN) {
                moveY(-speed);
            }
            else if (direction == Direction.LEFT) {
                moveX(speed);
            }
            else if (direction == Direction.RIGHT) {
                moveX(-speed);
            }
            if (lifetime>=1000){
                this.isHidden = true;
                this.isUncollidable = true;
                this.setMapEntityStatus(MapEntityStatus.REMOVED);
            }

        }
    }

    protected void performAction(Player player) {}

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }

    public boolean isExpired() {
        return lifetime <= 0;
    }
}
/*if(GamePanel.isMouseClicked() && didBoomerangSpawn == false){ //Spawn projectile //Keyboard.isKeyDown(Key.E)
            Boomerang projectile = new Boomerang(x, y,new Frame(ImageUtils.createSolidImage(new Color(255, 0, 0), 20, 20), ImageEffect.NONE, 1, null), new Point(this.getCalibratedXLocation(),this.getCalibratedYLocation()),GamePanel.getMousePositionPoint());
            System.out.println("Player Position: " + new Point(this.getCalibratedXLocation(),this.getCalibratedYLocation()));
            System.out.println("Click Position: " + GamePanel.getMousePositionPoint());
            didBoomerangSpawn = true;
            map.addBoomerang(projectile);
            System.out.println("Spawned Boomerang");
        }
        if(!GamePanel.isMouseClicked() && didBoomerangSpawn == true){ //Reset projectile spawn //Keyboard.isKeyUp(Key.E)
            didBoomerangSpawn = false;
        }*/