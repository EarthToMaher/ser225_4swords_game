package NPCs;

import Utils.Point;

import Engine.GraphicsHandler;
import Engine.ImageLoader;
//import GameObject.Frame;
//import GameObject.SpriteSheet;
//import Utils.Direction;
import GameObject.Frame;
import GameObject.SpriteSheet;
//import java.util.HashMap;
import Level.MapEntity;
import Level.MapEntityStatus;
import Level.NPC;
import Level.Player;
import Utils.Direction;

public class Boomerang extends NPC{
    private Direction direction;
    private int speed = 5;
    private double lifetime = 1000; 
    private double deltaTime = 16.67; //Credit to Zach Marlowe here, he already found this value
    private boolean boomerangTraveling = false;

    public Boomerang(int id, Point location) {
        super(id, location.x, location.y, new Frame(ImageLoader.load("Boomerang.png")));
    }

    public void update(Player player) {
        //this.performAction(player);
        super.update();
        //System.out.println("Traveling: " + boomerangTraveling + " Throwing: " + player.getIsThrowingBoomerang());
        if(!boomerangTraveling && player.getIsThrowingBoomerang()){

            isUncollidable = false;
            boomerangTraveling = true;
            direction = player.getFacingDirection();
            Point playerLocation = player.getLocation();
            if (direction == Direction.UP) this.setLocation(playerLocation.x,playerLocation.y-35);
            else if (direction == Direction.DOWN) this.setLocation(playerLocation.x, playerLocation.y+35);
            else if (direction == Direction.LEFT) this.setLocation(playerLocation.x-35, playerLocation.y);
            else this.setLocation(playerLocation.x+70, playerLocation.y);
        }
        if(boomerangTraveling){
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
                //System.out.println("Velocity X: " + velocityX + " Velocity Y: " + velocityY);
                //moveY(velocityY);
                //moveX(velocityX);
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
                if (lifetime<-1000){
                    //this.isHidden = true;
                    //this.setLocation(0, 0);
                    this.isUncollidable = true;
                    boomerangTraveling = false;
                    lifetime = 1000;
                }
            }
        }
    }

    protected void performAction(Player player) {}

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        if (boomerangTraveling) super.draw(graphicsHandler);
    }

    public boolean isTraveling() {
        return lifetime <= 0;
    }
}