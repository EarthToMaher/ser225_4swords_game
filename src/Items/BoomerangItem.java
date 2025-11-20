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
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Utils.Direction;
import Utils.Point;
import Utils.SoundManager;
import Level.NPC;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Builders.FrameBuilder;
import EnhancedMapTiles.Key;


public class BoomerangItem extends Item {

    private Direction direction;
    private int speed = 5;
    private double lifetime = 1000; 
    private double deltaTime = 16.67; //Credit to Zach Marlowe here, he already found this value
    private int dmg = 20;
    private List<NPC> hitNPCs= new ArrayList<>();
    private SpriteSheet spriteSheet;

    public BoomerangItem(float x, float y, Frame frame){
        super(x,y,frame);
    }

    public BoomerangItem(Point location, Frame frame){
        super(location.x,location.y,frame);
    }

    public BoomerangItem(Point location, SpriteSheet spriteSheet, String startingAnimation){
        super(location.x,location.y,spriteSheet,startingAnimation);
        isUncollidable=true;
        //loadAnimations(spriteSheet);
    }

    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
    return new HashMap<String, Frame[]>() {{

    
        put("SPINNING", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0),8)
                        .withScale(3)
                        .withBounds(6, 12, 12, 7)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 1),8)
                        .withScale(3)
                        .withBounds(6, 12, 12, 7)
                        .build(),
                 new FrameBuilder(spriteSheet.getSprite(0, 2),8)
                        .withScale(3)
                        .withBounds(6, 12, 12, 7)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 3),8)
                        .withScale(3)
                        .withBounds(6, 12, 12, 7)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 4),8)
                        .withScale(3)
                        .withBounds(6, 12, 12, 7)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 5),8)
                        .withScale(3)
                        .withBounds(6, 12, 12, 7)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 6),8)
                        .withScale(3)
                        .withBounds(6, 12, 12, 7)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 7),8)
                        .withScale(3)
                        .withBounds(6, 12, 12, 7)
                        .build()
                
        });
    
            put("IDLE", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0),8)
                        .withScale(3)
                        .withBounds(6, 12, 12, 7)
                        .build()
        });
    }};
}

    @Override
    public void ItemFunctionality(){
        super.ItemFunctionality();
        EnemyDetection();
        //loadAnimations(spriteSheet);
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
            currentAnimationName = "IDLE";
            lifetime = 1000;
        }
    }

    public void EnemyDetection(){
        for (NPC npc : map.getNPCs()) {

            if (this.intersects(npc)&&(hitNPCs==null||!hitNPCs.contains(npc))) {
                System.out.println("Boomerang is Intersecting: " + npc.getClass().getSimpleName());

                if (npc instanceof Walrus2 walrus) {
                    SoundManager.playSoundEffect("BoomerangHit.wav");
                    walrus.takeDamage(dmg);
                    hitNPCs.add(npc);
                    break;
                }

                if (npc instanceof Dinosaur2 dino) {
                    SoundManager.playSoundEffect("BoomerangThrow.wav");
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

                SoundManager.playSoundEffect("Keycard.wav");
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
        currentAnimationName = "SPINNING";
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

