package Maps;

import EnhancedMapTiles.PushableRock;
import EnhancedMapTiles.Key;
import EnhancedMapTiles.Portal;
import EnhancedMapTiles.BreakableWall;
import EnhancedMapTiles.Door;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Items.BoomerangItem;
import Items.Item;
import Items.LandMineItem;
import EnhancedMapTiles.Projectile;
import Level.*;
import NPCs.*;
import Scripts.SimpleTextScript;
import Scripts.TestMap.*;
import Tilesets.CommonTileset;
import Utils.Point;
import Shrines.EmptyShrine;

import java.awt.Image;
import java.util.ArrayList;

import Engine.ImageLoader;
import Scripts.*;

// Represents a test map to be used in a level
public class BoomerangTestMap extends Map {

    public static ArrayList<NPC> npcs = new ArrayList<>();
    Projectile projectile;
    

    public Item boomerangItem;


    public BoomerangTestMap() {
        super("test_map.txt", new CommonTileset());
        this.playerStartPosition = getMapTile(15, 7).getLocation();

//         spawnRandomEnemies(100.0f, 100.0f, 200.0f, 200.0f);
    }

    public void setUpInactivePlayer(Player active, Player inactive) {
        active.setLocation(getMapTile(14, 7).getLocation().x, getMapTile(14, 4).getLocation().y);
        inactive.setLocation(getMapTile(14, 7).getLocation().x, getMapTile(14, 4).getLocation().y);
    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();
        Key key1 = new Key(getMapTile(5, 10).getLocation());
        enhancedMapTiles.add(key1);

        BreakableWall door1 = new BreakableWall(getMapTile(8, 3).getLocation());
        enhancedMapTiles.add(door1);

        BreakableWall door2 = new BreakableWall(getMapTile(9, 3).getLocation());
        enhancedMapTiles.add(door2);

        Portal portal = new Portal(getMapTile(8,1).getLocation().subtractY(50), "SecondMap", new Point(700,800));
        enhancedMapTiles.add(portal);
        Portal portal2 = new Portal(getMapTile(9,1).getLocation().subtractY(50), "SecondMap", new Point(700,800));
        enhancedMapTiles.add(portal2);


        return enhancedMapTiles;
    }

    @Override
    public ArrayList<NPC> loadNPCs() {
        npcs.clear();

        ArrayList<NPC> npcs = new ArrayList<>();
        inactiveRobotStatic = new InactiveRobot(5, getMapTile(4, 4).getLocation().subtractY(50));
        npcs.add(inactiveRobotStatic);

        Walrus2 dinosaur2 = new Walrus2(5, getMapTile(5,5).getLocation().subtractY(50));
        npcs.add(dinosaur2);



        return npcs;
    }

    public ArrayList<Item> loadItems(){
        ArrayList<Item> items = new ArrayList<>();

        boomerangItem = new LandMineItem(getMapTile(6, 4).getLocation(), new Frame(ImageLoader.load("Landmine.png")));
        items.add(boomerangItem);

        return items;
    }
    

    public ArrayList<Shrine> loadShrines()
    {
        ArrayList<Shrine> shrines = new ArrayList<>();

        Shrine boomerangShrine = new EmptyShrine(1,getMapTile(6, 4).getLocation(),boomerangItem);
        boomerangShrine.setInteractScript(new LandMineShrineScript());
        shrines.add(boomerangShrine);

        return shrines;
    }

    public ArrayList<Collectible> loadCollectibles()
    {
        ArrayList<Collectible> collectibles = new ArrayList<>();
        return collectibles;
    }
    @Override
    public ArrayList<Trigger> loadTriggers() {
        ArrayList<Trigger> triggers = new ArrayList<>();
        // triggers.add(new Trigger(790, 1030, 100, 10, new LostBallScript(), "hasLostBall"));
        // triggers.add(new Trigger(790, 960, 10, 80, new LostBallScript(), "hasLostBall"));
        // triggers.add(new Trigger(890, 960, 10, 80, new LostBallScript(), "hasLostBall"));
        return triggers;
    }

    @Override
    public void loadScripts() {
        //getMapTile(21, 19).setInteractScript(new SimpleTextScript("Cat's house"));

        //getMapTile(7, 26).setInteractScript(new SimpleTextScript("Walrus's house"));

        //getMapTile(20, 4).setInteractScript(new SimpleTextScript("Dino's house"));

        //getMapTile(2, 6).setInteractScript(new TreeScript());


    }
}

