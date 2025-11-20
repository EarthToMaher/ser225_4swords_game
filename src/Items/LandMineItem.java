package Items;

import Level.EnhancedMapTile;
import Level.MapEntity;
import Level.MapEntityStatus;
import Level.NPC;
import Level.Player;
import NPCs.Boss;
import NPCs.Dinosaur2;
import NPCs.Walrus2;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import Utils.Direction;
import Utils.Point;
import Utils.SoundManager;
import Level.NPC;
import GameObject.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Builders.FrameBuilder;
import EnhancedMapTiles.BreakableWall;
import EnhancedMapTiles.Key;


public class LandMineItem extends Item {

    private double lifetime = 10000; 
    private double deltaTime = 16.67; //Credit to Zach Marlowe here, he already found this value
    private int dmg = 1000;
    private int explosionRadius = 100;
    private Rectangle collider;
    private boolean exploding = false;
    private boolean isPlanted = false;
    private NPC npcThatActivated;
    private List<Walrus2> walrusToRemove = new ArrayList<>();
    private List<Dinosaur2> dinoToRemove = new ArrayList<>();

    public LandMineItem(float x, float y, Frame frame){
        super(x,y,frame);
    }

    public LandMineItem(Point location, Frame frame){
        super(location.x,location.y,frame);
    }

    public LandMineItem(Point location, SpriteSheet spriteSheet, String startingAnimation){
        super(location.x,location.y,spriteSheet,startingAnimation);
        isUncollidable=true;
        //loadAnimations(spriteSheet);
    }

    /*public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
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
}*/

    @Override
    public void ItemFunctionality(){
        super.ItemFunctionality();
        EnemyDetection();
        lifetime -= deltaTime;
        if(lifetime<=0)Detonate();
    }



    public void EnemyDetection(){
        for (NPC npc : map.getNPCs()) {

            if (this.intersects(npc)) {

                if (npc instanceof Walrus2 walrus) {
                    //walrus.takeDamage(dmg);
                    if(!exploding){
                        Detonate();
                    }
                    walrusToRemove.add(walrus);
                }
                if (npc instanceof Dinosaur2 dino) {
                    //dino.takeDamage(dmg);
                    if(!exploding) Detonate();
                    dinoToRemove.add(dino);
                }
                if (npc instanceof Boss boss) {
                    if(!exploding) Detonate();
                    boss.takeDamage(100);
                }
            }
        }

        for (Dinosaur2 dino : dinoToRemove){
            dino.takeDamage(dmg);
        }
        dinoToRemove.clear();

        for (Walrus2 walrus : walrusToRemove){
            walrus.takeDamage(dmg);
        }
        walrusToRemove.clear();
    }



    @Override
    public void UseItem(Player player)
    {
        if(this.itemIsActive) Detonate();
        if(isPlanted) return;
        if(this.collider == null) collider = this.getBounds();
        //isUncollidable = false;
        super.UseItem(player);
        this.isHidden = false;
        this.isUncollidable = false;
        this.setLocation(player.getLocation().x,player.getLocation().y);
        this.isUncollidable = true;
        this.isPlanted = true;
    }

    public void Detonate(){
        SoundManager.playSoundEffect("Landmine.wav");
        exploding = true;
        //this.setBounds(new Rectangle(this.getLocation().x,this.getLocation().y,explosionRadius,explosionRadius));
        //EnemyDetection();
        BreakWall();
        //this.setBounds(collider);
        this.isHidden = true;
        this.isPlanted = false;
        this.itemIsActive = false;
        isUncollidable = true;
        this.itemIsActive = false;
        lifetime = 10000;
    }

    public void BreakWall(){
        for (EnhancedMapTile tile : map.getEnhancedMapTiles()){
            /*if(this.intersects(tile)){
                if(tile instanceof BreakableWall breakableWall){
                    breakableWall.setMapEntityStatus(mapEntityStatus.REMOVED);
                }
            }*/

            if(tile instanceof BreakableWall breakableWall){
                if((this.getLocation().x-(explosionRadius/2)< tile.getLocation().x && tile.getLocation().x< this.getLocation().x+(explosionRadius/2))||((this.getLocation().y-(explosionRadius/2)< tile.getLocation().y && tile.getLocation().y< this.getLocation().y+(explosionRadius/2)))){
                    breakableWall.setMapEntityStatus(mapEntityStatus.REMOVED);
                }
            } 
        }
    }

    @Override
    public void update(Player player){
        //player.setItem(this);
        super.update(player);
        if(isPlanted&&!exploding) ItemFunctionality();
        if(exploding) System.out.println("BOOM");
    }
}

