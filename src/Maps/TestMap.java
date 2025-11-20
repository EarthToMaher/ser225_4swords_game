package Maps;

import EnhancedMapTiles.PushableRock;
import EnhancedMapTiles.WinTile;
import Game.ScreenCoordinator;
import EnhancedMapTiles.Key;
import EnhancedMapTiles.Portal;
import EnhancedMapTiles.PressurePlate;
import EnhancedMapTiles.PressurePlateTileOff;
import EnhancedMapTiles.PressurePlateTileOn;
import EnhancedMapTiles.NewPressurePlate;
import EnhancedMapTiles.NewPressurePlateTileOff;
import EnhancedMapTiles.NewPressurePlateTileOn;
import EnhancedMapTiles.BottomlessPitTile;
import EnhancedMapTiles.BreakableWall;
import EnhancedMapTiles.Door;
import EnhancedMapTiles.WinTile;
import GameObject.Frame;
import GameObject.SpriteSheet;
import EnhancedMapTiles.Projectile;
import Level.*;
import NPCs.*;
import Scripts.SimpleTextScript;
import Scripts.TestMap.*;
import Tilesets.CommonTileset;
import Utils.Point;
import Shrines.EmptyShrine;
import Items.*;

import java.awt.Image;
import java.lang.System.Logger.Level;
import java.util.ArrayList;

import Engine.ImageLoader;
import Scripts.*;

// Represents a test map to be used in a level
public class TestMap extends Map {

    public static ArrayList<NPC> npcs = new ArrayList<>();
    Projectile projectile;
    protected Player player2;


    public Item boomerangItem;
    public Item jetpackItem;
    public Item landMineItem;


    public Shrine boomerangShrine;
    public boolean boomerangSet = false;
    public Item emptyItem = new Item(new Frame(ImageLoader.load("arrow.png")));


    public TestMap() {
        super("test_map.txt", new CommonTileset());
        this.playerStartPosition = getMapTile(3, 17).getLocation();
//         spawnRandomEnemies(100.0f, 100.0f, 200.0f, 200.0f);
    }

    @Override
    public void setUpInactivePlayer(Player active, Player inactive) {
        active.setLocation(getMapTile(3, 17).getLocation().x, getMapTile(3, 17).getLocation().y);
        inactive.setLocation(getMapTile(2, 17).getLocation().x, getMapTile(2, 17).getLocation().y);
    }

    @Override
    public void update(Player player) {
        super.update(player);
        boomerangShrine.setInteractScript(new BoomerangShrineScript());
                if(!boomerangSet) {
                    boomerangItem.setLocation(getMapTile(32,40).getX(), getMapTile(32,40).getY());
                    boomerangShrine.setItem(boomerangItem);
                    boomerangSet = true;
                }
        //if(PressurePlate.isTouched) {
        //        boomerangShrine.setInteractScript(new BoomerangShrineScript());
        //        if(!boomerangSet) {
        //            boomerangItem.setLocation(getMapTile(32,40).getX(), getMapTile(32,40).getY());
        //            boomerangShrine.setItem(boomerangItem);
        //            boomerangSet = true;
        //        }
        //        System.out.println(boomerangSet);

        //}
    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();
        Key key1 = new Key(getMapTile(10, 14).getLocation());
        enhancedMapTiles.add(key1);

        Key key2 = new Key(getMapTile(77, 18).getLocation());
        enhancedMapTiles.add(key2);

        Key key3 = new Key(getMapTile(71, 53).getLocation());
        enhancedMapTiles.add(key3);

        Door door1 = new Door(getMapTile(15, 17).getLocation());
        enhancedMapTiles.add(door1);

        Door door2 = new Door(getMapTile(65, 26).getLocation());
        enhancedMapTiles.add(door2);

        Door door3 = new Door(getMapTile(57, 48).getLocation());
        enhancedMapTiles.add(door3);

        Portal portal = new Portal(getMapTile(0,48).getLocation().subtractY(50), "SecondMap", new Point(700,800));
        enhancedMapTiles.add(portal);

        Portal portal2 = new Portal(getMapTile(0,49).getLocation().subtractY(50), "SecondMap", new Point(700,800));
        enhancedMapTiles.add(portal2);

        NewPressurePlate plate1 = new NewPressurePlate(getMapTile(32,14).getLocation());
        enhancedMapTiles.add(plate1);

        NewPressurePlateTileOff plateTile1 = new NewPressurePlateTileOff(getMapTile(36,13).getLocation());
        enhancedMapTiles.add(plateTile1);

        NewPressurePlateTileOff plateTile2 = new NewPressurePlateTileOff(getMapTile(36,14).getLocation());
        enhancedMapTiles.add(plateTile2);

        NewPressurePlateTileOff plateTile3 = new NewPressurePlateTileOff(getMapTile(36,15).getLocation());
        enhancedMapTiles.add(plateTile3);

        NewPressurePlateTileOff plateTile4 = new NewPressurePlateTileOff(getMapTile(36,16).getLocation());
        enhancedMapTiles.add(plateTile4);

        NewPressurePlateTileOff plateTile5 = new NewPressurePlateTileOff(getMapTile(36,17).getLocation());
        enhancedMapTiles.add(plateTile5);

        NewPressurePlateTileOff plateTile6 = new NewPressurePlateTileOff(getMapTile(36,18).getLocation());
        enhancedMapTiles.add(plateTile6);

        NewPressurePlateTileOff plateTile7 = new NewPressurePlateTileOff(getMapTile(36,19).getLocation());
        enhancedMapTiles.add(plateTile7);

        NewPressurePlateTileOff plateTile8 = new NewPressurePlateTileOff(getMapTile(36,20).getLocation());
        enhancedMapTiles.add(plateTile8);

        NewPressurePlateTileOff plateTile9 = new NewPressurePlateTileOff(getMapTile(36,21).getLocation());
        enhancedMapTiles.add(plateTile9);

        NewPressurePlateTileOff plateTile10 = new NewPressurePlateTileOff(getMapTile(36,22).getLocation());
        enhancedMapTiles.add(plateTile10);

        NewPressurePlateTileOff plateTile11 = new NewPressurePlateTileOff(getMapTile(36,23).getLocation());
        enhancedMapTiles.add(plateTile11);

        BottomlessPitTile bottomless2PitTile = new BottomlessPitTile(getMapTile(59,51).getLocation());
        enhancedMapTiles.add(bottomless2PitTile);

        BottomlessPitTile bottomless2PitTile2 = new BottomlessPitTile(getMapTile(60,51).getLocation());
        enhancedMapTiles.add(bottomless2PitTile2);

        BottomlessPitTile bottomless2PitTile3 = new BottomlessPitTile(getMapTile(61,51).getLocation());
        enhancedMapTiles.add(bottomless2PitTile3);

        BottomlessPitTile bottomless2PitTile4 = new BottomlessPitTile(getMapTile(62,51).getLocation());
        enhancedMapTiles.add(bottomless2PitTile4);

        BottomlessPitTile bottomless2PitTile5 = new BottomlessPitTile(getMapTile(63,51).getLocation());
        enhancedMapTiles.add(bottomless2PitTile5);

        BottomlessPitTile bottomless2PitTile6 = new BottomlessPitTile(getMapTile(64,51).getLocation());
        enhancedMapTiles.add(bottomless2PitTile6);

        BottomlessPitTile bottomless2PitTile7 = new BottomlessPitTile(getMapTile(65,51).getLocation());
        enhancedMapTiles.add(bottomless2PitTile7);

        BottomlessPitTile bottomless2PitTile8 = new BottomlessPitTile(getMapTile(66,51).getLocation());
        enhancedMapTiles.add(bottomless2PitTile8);

        BottomlessPitTile bottomless2PitTile9 = new BottomlessPitTile(getMapTile(67,51).getLocation());
        enhancedMapTiles.add(bottomless2PitTile9);

        BottomlessPitTile bottomless2PitTile10 = new BottomlessPitTile(getMapTile(68,51).getLocation());
        enhancedMapTiles.add(bottomless2PitTile10);

        BottomlessPitTile bottomless2PitTile11 = new BottomlessPitTile(getMapTile(69,51).getLocation());
        enhancedMapTiles.add(bottomless2PitTile11);

        BottomlessPitTile bottomless2PitTile12 = new BottomlessPitTile(getMapTile(70,51).getLocation());
        enhancedMapTiles.add(bottomless2PitTile12);

        BottomlessPitTile bottomless2PitTile13 = new BottomlessPitTile(getMapTile(71,51).getLocation());
        enhancedMapTiles.add(bottomless2PitTile13);

        BottomlessPitTile bottomless2PitTile14 = new BottomlessPitTile(getMapTile(72,51).getLocation());
        enhancedMapTiles.add(bottomless2PitTile14);

        BreakableWall bWall1 = new BreakableWall(getMapTile(28, 48).getLocation());
        enhancedMapTiles.add(bWall1);

        BreakableWall bWall2 = new BreakableWall(getMapTile(28, 49).getLocation());
        enhancedMapTiles.add(bWall2);



        BreakableWall secBWall1 = new BreakableWall(getMapTile(1, 6).getLocation());
        enhancedMapTiles.add(secBWall1);

        BreakableWall secBWall2 = new BreakableWall(getMapTile(2, 6).getLocation());
        enhancedMapTiles.add(secBWall2);

        BreakableWall secBWall3 = new BreakableWall(getMapTile(1, 7).getLocation());
        enhancedMapTiles.add(secBWall3);

        BreakableWall secBWall4 = new BreakableWall(getMapTile(2, 7).getLocation());
        enhancedMapTiles.add(secBWall4);

        BreakableWall secBWall5 = new BreakableWall(getMapTile(1, 8).getLocation());
        enhancedMapTiles.add(secBWall5);

        BreakableWall secBWall6 = new BreakableWall(getMapTile(2, 8).getLocation());
        enhancedMapTiles.add(secBWall6);

        BreakableWall secBWall7 = new BreakableWall(getMapTile(1, 9).getLocation());
        enhancedMapTiles.add(secBWall7);

        BreakableWall secBWall8 = new BreakableWall(getMapTile(2, 9).getLocation());
        enhancedMapTiles.add(secBWall8);

        BreakableWall secBWall9 = new BreakableWall(getMapTile(1, 10).getLocation());
        enhancedMapTiles.add(secBWall9);

        BreakableWall secBWall10 = new BreakableWall(getMapTile(2, 10).getLocation());
        enhancedMapTiles.add(secBWall10);

        BreakableWall secBWall11 = new BreakableWall(getMapTile(1, 11).getLocation());
        enhancedMapTiles.add(secBWall11);

        BreakableWall secBWall12 = new BreakableWall(getMapTile(2, 11).getLocation());
        enhancedMapTiles.add(secBWall12);

        BreakableWall secBWall13 = new BreakableWall(getMapTile(1, 12).getLocation());
        enhancedMapTiles.add(secBWall13);

        BreakableWall secBWall14 = new BreakableWall(getMapTile(2, 12).getLocation());
        enhancedMapTiles.add(secBWall14);

        WinTile winTile = new WinTile(getMapTile(13, 4).getLocation());
        enhancedMapTiles.add(winTile);

        return enhancedMapTiles;
    }

    @Override
    public ArrayList<NPC> loadNPCs() {
        npcs.clear();

        ArrayList<NPC> npcs = new ArrayList<>();
        inactiveRobotStatic = new InactiveRobot(5, getMapTile(7, 18).getLocation().subtractY(50));
        npcs.add(inactiveRobotStatic);

        Dinosaur2 dinosaur2 = new Dinosaur2(5, getMapTile(4,46).getLocation().subtractY(50));
        npcs.add(dinosaur2);

        Dinosaur2 dinosaur22 = new Dinosaur2(5, getMapTile(4,51).getLocation().subtractY(50));
        npcs.add(dinosaur22);

        MouseDroid mouseDroid = new MouseDroid(6, getMapTile(11,15).getLocation().subtractY(50));
        mouseDroid.setInteractScript(new MouseDroidScript());
        npcs.add(mouseDroid);

        DigitalSign digitalSign2 = new DigitalSign(7, getMapTile(13,20).getLocation());
        digitalSign2.setInteractScript(new DigitalSignScript2());
        npcs.add(digitalSign2);

        DigitalSign digitalSign3 = new DigitalSign(7, getMapTile(23,47).getLocation());
        digitalSign3.setInteractScript(new DigitalSignScript3());
        npcs.add(digitalSign3);

        DigitalSign2 digitalSign = new DigitalSign2(7, getMapTile(3,2).getLocation());
        digitalSign.setInteractScript(new DigitalSignScript4());
        npcs.add(digitalSign);


        return npcs;
    }

    public ArrayList<Item> loadItems(){
        ArrayList<Item> items = new ArrayList<>();

        jetpackItem = new JetpackItem(getMapTile(1, 63).getLocation(), new Frame(ImageLoader.load("Jetpack.png")));
        items.add(jetpackItem);

        boomerangItem = new BoomerangItem(getMapTile(1, 63).getLocation(), new SpriteSheet(ImageLoader.load("BoomerangItem.png"),23,24),"IDLE");
        items.add(boomerangItem);

        landMineItem = new LandMineItem(getMapTile(1, 63).getLocation(), new Frame(ImageLoader.load("Landmine.png")));
        items.add(landMineItem);

        return items;
    }


    public ArrayList<Shrine> loadShrines()
    {
        ArrayList<Shrine> shrines = new ArrayList<>();

        Shrine jetpackShrine = new EmptyShrine(1,getMapTile(60, 44).getLocation(), jetpackItem);
        jetpackShrine.setInteractScript(new JetpackShrineScript());
        shrines.add(jetpackShrine);

        boomerangShrine = new EmptyShrine(2, getMapTile(65, 18).getLocation(),emptyItem);
        boomerangShrine.setInteractScript(new EmptyShrineScript());
        shrines.add(boomerangShrine);

        Shrine landMineShrine = new EmptyShrine(1,getMapTile(36, 48).getLocation(),landMineItem);
        landMineShrine.setInteractScript(new LandMineShrineScript());
        shrines.add(landMineShrine);

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

