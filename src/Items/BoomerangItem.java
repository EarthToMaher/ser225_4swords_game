package Items;

import Level.EnhancedMapTile;
import Level.MapEntity;
import Level.MapEntityStatus;
import Level.NPC;
import Level.Player;
import NPCs.Dinosaur2;
import NPCs.Walrus2;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Utils.Direction;
import Utils.Point;
import Level.NPC;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import EnhancedMapTiles.Key;


public class BoomerangItem extends Item {

    private Direction direction;
    private int speed = 5;
    private double lifetime = 1000; 
    private double deltaTime = 16.67; //Credit to Zach Marlowe here, he already found this value
    private int dmg = 20;
    private List<NPC> hitNPCs= new ArrayList<>();

    public BoomerangItem(float x, float y, Frame frame){
        super(x,y,frame);
    }

    public BoomerangItem(Point location, Frame frame){
        super(location.x,location.y,frame);
    }

    @Override
    public void ItemFunctionality(){
        super.ItemFunctionality();
        EnemyDetection();
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

    public void EnemyDetection(){
        for (NPC npc : map.getNPCs()) {

            if (this.intersects(npc)&&(hitNPCs==null||!hitNPCs.contains(npc))) {
                System.out.println("Boomerang is Intersecting: " + npc.getClass().getSimpleName());

                if (npc instanceof Walrus2 walrus) {
                    walrus.takeDamage(dmg);
                    hitNPCs.add(npc);
                    break;
                }

                if (npc instanceof Dinosaur2 dino) {
                    dino.takeDamage(dmg);
                    hitNPCs.add(npc);
                    break;
                }
            }
        }
    }

    public boolean KeyCollector(){
        for(EnhancedMapTile tile: map.getActiveEnhancedMapTiles()){
            if(this.touching(tile)&& tile instanceof Key key){
                key.setMapEntityStatus(MapEntityStatus.REMOVED);
                return true;
            }
        }
        return false;
    }



    @Override
    public void UseItem(Player player)
    {
        super.UseItem(player);
        this.isUncollidable=true;
        if(hitNPCs!=null)hitNPCs.clear();
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
        if(KeyCollector())player.setHasKey(true);
    }

    public boolean isTraveling() {
        return lifetime <= 0;
    }
}

