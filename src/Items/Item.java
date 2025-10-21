package Items;

import Level.MapEntity;
import Level.Player;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Utils.Direction;

import java.util.HashMap;

public class Item extends MapEntity{

    protected boolean itemIsActive = false;

    public Item(Frame frame){
        super(0,0,frame);
    }

    public Item(float x, float y, Frame frame){
        super(x, y,frame);
    }
    
    public void UseItem(Player player){
        if(itemIsActive) return;
        itemIsActive=true;
        isUncollidable = false;
        //System.out.println("I was used");

    }

    public void ItemFinished(){
        itemIsActive=false;
        isUncollidable = true;
    }

    public void ItemFunctionality(){

    }

    public void update(Player player){
        super.update();
        //System.out.println("Im updating");
        if(!itemIsActive && player.getIsThrowingBoomerang()){
            //System.out.println("Player attempted to use item");
            UseItem(player);
        }
        if (!itemIsActive) return;
        //System.out.println("Got past the return statement");
        ItemFunctionality();
    }

    public void draw(GraphicsHandler graphicsHandler){
        if(itemIsActive) super.draw(graphicsHandler);
    }
    
}
