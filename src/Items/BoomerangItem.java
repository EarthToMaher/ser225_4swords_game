package Items;

import Level.MapEntity;
import Level.Player;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Utils.Direction;
import Utils.Point;

import java.util.HashMap;


public class BoomerangItem extends Item {

    private Direction direction;
    private int speed = 5;
    private double lifetime = 1000; 
    private double deltaTime = 16.67; //Credit to Zach Marlowe here, he already found this value

    public BoomerangItem(float x, float y, Frame frame){
        super(x,y,frame);
    }

    @Override
    public void ItemFunctionality(){
        super.ItemFunctionality();
        if (!isTraveling()){
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
                lifetime -= deltaTime;
            }
            if(isTraveling()){
                lifetime -= deltaTime;
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
            }
        if (lifetime < -1000){
            ItemFinished();
            lifetime = 1000;
        }
    }

    @Override
    public void UseItem(Player player)
    {
        super.UseItem(player);
        direction = player.getFacingDirection();
        Point playerLocation = player.getLocation();
        if (direction == Direction.UP) this.setLocation(playerLocation.x,playerLocation.y-35);
        else if (direction == Direction.DOWN) this.setLocation(playerLocation.x, playerLocation.y+35);
        else if (direction == Direction.LEFT) this.setLocation(playerLocation.x-35, playerLocation.y);
        else this.setLocation(playerLocation.x+70, playerLocation.y);
    }

    @Override
    public void update(Player player){
        //player.setItem(this);
        super.update(player);
    }

    public boolean isTraveling() {
        return lifetime <= 0;
    }
}

