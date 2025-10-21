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
        itemIsActive=true;
        isUncollidable = false;
    }

    public void ItemFinished(){
        itemIsActive=false;
        isUncollidable = true;
    }

    public void ItemFunctionality(){

    }

    public void update(Player player){
        super.update();
        if(!itemIsActive && player.getIsThrowingBoomerang()){
            UseItem(player);
        }
        if (!itemIsActive) return;
        ItemFunctionality();
    }

    public void draw(GraphicsHandler graphicsHandler){
        if(itemIsActive) super.draw(graphicsHandler);
    }
    
}
