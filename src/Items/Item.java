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

    public boolean itemIsActive = false;
    protected boolean onShrine = true;

    public Item(Frame frame){
        super(0,0,frame);
        isUncollidable = true;
    }

    public Item(float x, float y, Frame frame){
        super(x, y,frame);
        isUncollidable = true;
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

    public void PickUpItem(Player player){
        onShrine = false;
    }

    public void PlaceItem(){
        onShrine = true;
        itemIsActive = false;
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
        if(itemIsActive||onShrine) super.draw(graphicsHandler);
    }
    
}
