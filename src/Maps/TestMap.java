package Maps;

import EnhancedMapTiles.*;
import GameObject.Frame;
import Items.BoomerangItem;
import Items.Item;
import Items.JetpackItem;
import Level.*;
import NPCs.*;
import Scripts.SimpleTextScript;
import Scripts.TestMap.*;
import Tilesets.CommonTileset;
import Utils.Point;
import Shrines.EmptyShrine;
import Items.*;

import java.awt.Image;
import java.util.ArrayList;

import Engine.ImageLoader;
import Scripts.*;

// Represents a test map to be used in a level
public class TestMap extends Map {

    public static ArrayList<NPC> npcs = new ArrayList<>();
    Projectile projectile;
    

    public Item boomerangItem;
    public Item jetpackItem;


    public TestMap() {
        super("test_map.txt", new CommonTileset());
        this.playerStartPosition = getMapTile(15, 7).getLocation();

//         spawnRandomEnemies(100.0f, 100.0f, 200.0f, 200.0f);
    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();
        Key key1 = new Key(getMapTile(5, 10).getLocation());
        enhancedMapTiles.add(key1);

        Door door1 = new Door(getMapTile(8, 1).getLocation());
        enhancedMapTiles.add(door1);

        Portal portal = new Portal(getMapTile(8,1).getLocation().subtractY(50), "SecondMap", new Point(700,800));
        enhancedMapTiles.add(portal);

        Portal portal2 = new Portal(getMapTile(9,1).getLocation().subtractY(50), "SecondMap", new Point(700,800));
        enhancedMapTiles.add(portal2);

        BottomlessPitTile bottomlessPitTile = new BottomlessPitTile(getMapTile(12,10).getLocation());
        enhancedMapTiles.add(bottomlessPitTile);

        BottomlessPitTile bottomlessPitTile2 = new BottomlessPitTile(getMapTile(13,10).getLocation());
        enhancedMapTiles.add(bottomlessPitTile2);

        FakeWall faketest = new FakeWall(getMapTile(10,10).getLocation());
        enhancedMapTiles.add(faketest);

        return enhancedMapTiles;
    }

    @Override
    public ArrayList<NPC> loadNPCs() {
        npcs.clear();

        ArrayList<NPC> npcs = new ArrayList<>();
        inactiveRobotStatic = new InactiveRobot(5, getMapTile(15, 4).getLocation().subtractY(50));
        npcs.add(inactiveRobotStatic);

        Dinosaur2 dinosaur2 = new Dinosaur2(5, getMapTile(5,5).getLocation().subtractY(50));
        npcs.add(dinosaur2);



        return npcs;
    }

    public ArrayList<Item> loadItems(){
        ArrayList<Item> items = new ArrayList<>();

        boomerangItem = new BoomerangItem(getMapTile(12,4).getLocation(), new Frame(ImageLoader.load("Boomerang.png")));
        items.add(boomerangItem);

        return items;
    }
    

    public ArrayList<Shrine> loadShrines()
    {
        ArrayList<Shrine> shrines = new ArrayList<>();

        Shrine boomerangShrine = new EmptyShrine(2, getMapTile(12, 4).getLocation(),boomerangItem);
        boomerangShrine.setInteractScript(new BoomerangShrineScript());
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

