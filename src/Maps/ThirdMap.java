package Maps;

import Engine.ImageLoader;
import EnhancedMapTiles.*;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Items.BoomerangItem;
import Items.Item;
import Items.JetpackItem;
import Level.*;
import NPCs.Dinosaur2;
import NPCs.InactiveRobot;
import NPCs.Walrus2;
import Scripts.TestMap.BoomerangShrineScript;
import Scripts.TestMap.EmptyShrineScript;
import Scripts.TestMap.JetpackShrineScript;
import Shrines.EmptyShrine;
import Tilesets.CommonTileset;
import Utils.Point;

import java.util.ArrayList;

public class ThirdMap extends Map {

      public Item boomerangItem;
      public Shrine boomerangShrine;
      public boolean boomerangSet = false;
      public Item emptyItem = new Item(new Frame(ImageLoader.load("arrow.png")));
      public static ArrayList<NPC> npcs = new ArrayList<>();

    public ThirdMap() {
        super("third_map.txt", new CommonTileset());
    }

    @Override
    public void update(Player player) {
        super.update(player);
        if(PressurePlate.isTouched) {
                boomerangShrine.setInteractScript(new BoomerangShrineScript());
                if(!boomerangSet) {
                    boomerangItem.setLocation(getMapTile(32,40).getX(), getMapTile(32,40).getY());
                    boomerangShrine.setItem(boomerangItem);
                    boomerangSet = true;
                }
                System.out.println(boomerangSet);

        }
    }

    @Override
    public void setUpInactivePlayer(Player active, Player inactive) {
        active.setLocation(getMapTile(32, 48).getLocation().x, getMapTile(32, 48).getLocation().y);
        inactive.setLocation(getMapTile(31, 48).getLocation().x, getMapTile(31, 48).getLocation().y);
    }

    public ArrayList<NPC> loadNPCs() {

        npcs.clear();
        inactiveRobotStatic = new InactiveRobot(5, getMapTile(33, 49).getLocation().subtractY(50));
        npcs.add(inactiveRobotStatic);

        Dinosaur2 shooter1 = new Dinosaur2(6, getMapTile(44, 32).getLocation().subtractY(50));
        npcs.add(shooter1);

        Dinosaur2 shooter2 = new Dinosaur2(6, getMapTile(44, 24).getLocation().subtractY(50));
        npcs.add(shooter2);

        Dinosaur2 shooter3 = new Dinosaur2(6, getMapTile(52, 32).getLocation().subtractY(50));
        npcs.add(shooter3);

        Dinosaur2 shooter4 = new Dinosaur2(6, getMapTile(52, 24).getLocation().subtractY(50));
        npcs.add(shooter4);

        Dinosaur2 shooter5 = new Dinosaur2(6, getMapTile(32, 9).getLocation().subtractY(50));
        npcs.add(shooter5);

        Walrus2 charger = new Walrus2(8, getMapTile(60, 28).getLocation().subtractY(50));
        npcs.add(charger);



        return npcs;
    }

    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();


        PressurePlate plate = new PressurePlate(getMapTile(62,28).getLocation());

        Key keycard = new Key(getMapTile(11,28).getLocation());
        enhancedMapTiles.add(keycard);

        Door door = new Door(getMapTile(35,5).getLocation());
        enhancedMapTiles.add(door);


        Portal portal2 = new Portal(getMapTile(35,1).getLocation(), "FourthMap", new Point(800,800));
        Portal portal3 = new Portal(getMapTile(36,1).getLocation(), "FourthMap", new Point(800,800));
        enhancedMapTiles.add(portal2);
        enhancedMapTiles.add(portal3);

        //FAKEWALLS
        //______________________________________________________________
        FakeWall wall1 = new FakeWall(getMapTile(23,27).getLocation());
        FakeWall wall2 = new FakeWall(getMapTile(24,27).getLocation());
        FakeWall wall3 = new FakeWall(getMapTile(25,27).getLocation());
        FakeWall wall4 = new FakeWall(getMapTile(26,27).getLocation());
        FakeWall wall5 = new FakeWall(getMapTile(27,27).getLocation());
        FakeWall wall6 = new FakeWall(getMapTile(28,27).getLocation());
        FakeWall wall7 = new FakeWall(getMapTile(29,27).getLocation());
        enhancedMapTiles.add(wall1);
        enhancedMapTiles.add(wall2);
        enhancedMapTiles.add(wall3);
        enhancedMapTiles.add(wall4);
        enhancedMapTiles.add(wall5);
        enhancedMapTiles.add(wall6);
        enhancedMapTiles.add(wall7);


        FakeWall wall8 = new FakeWall(getMapTile(23,28).getLocation());
        FakeWall wall9 = new FakeWall(getMapTile(24,28).getLocation());
        FakeWall wall10 = new FakeWall(getMapTile(25,28).getLocation());
        FakeWall wall11 = new FakeWall(getMapTile(26,28).getLocation());
        FakeWall wall12 = new FakeWall(getMapTile(27,28).getLocation());
        FakeWall wall13 = new FakeWall(getMapTile(28,28).getLocation());
        FakeWall wall14 = new FakeWall(getMapTile(29,28).getLocation());
        enhancedMapTiles.add(wall8);
        enhancedMapTiles.add(wall9);
        enhancedMapTiles.add(wall10);
        enhancedMapTiles.add(wall11);
        enhancedMapTiles.add(wall12);
        enhancedMapTiles.add(wall13);
        enhancedMapTiles.add(wall14);

        FakeWall wall15 = new FakeWall(getMapTile(23,29).getLocation());
        FakeWall wall16 = new FakeWall(getMapTile(24,29).getLocation());
        FakeWall wall17 = new FakeWall(getMapTile(25,29).getLocation());
        FakeWall wall18 = new FakeWall(getMapTile(26,29).getLocation());
        FakeWall wall19 = new FakeWall(getMapTile(27,29).getLocation());
        FakeWall wall20 = new FakeWall(getMapTile(28,29).getLocation());
        FakeWall wall21 = new FakeWall(getMapTile(29,29).getLocation());
        enhancedMapTiles.add(wall15);
        enhancedMapTiles.add(wall16);
        enhancedMapTiles.add(wall17);
        enhancedMapTiles.add(wall18);
        enhancedMapTiles.add(wall19);
        enhancedMapTiles.add(wall20);
        enhancedMapTiles.add(wall21);
        enhancedMapTiles.add(plate);
        //______________________________________________________________

        //BOTTOMLESS PITS
        //______________________________________________________________

        BottomlessPitTile tile1 = new BottomlessPitTile(getMapTile(13,27).getLocation());
        enhancedMapTiles.add(tile1);
        BottomlessPitTile tile2 = new BottomlessPitTile(getMapTile(14,27).getLocation());
        enhancedMapTiles.add(tile2);
        BottomlessPitTile tile3 = new BottomlessPitTile(getMapTile(15,27).getLocation());
        enhancedMapTiles.add(tile3);
        BottomlessPitTile tile4 = new BottomlessPitTile(getMapTile(16,27).getLocation());
        enhancedMapTiles.add(tile4);

        BottomlessPitTile tile5 = new BottomlessPitTile(getMapTile(13,28).getLocation());
        enhancedMapTiles.add(tile5);
        BottomlessPitTile tile6 = new BottomlessPitTile(getMapTile(14,28).getLocation());
        enhancedMapTiles.add(tile6);
        BottomlessPitTile tile7 = new BottomlessPitTile(getMapTile(15,28).getLocation());
        enhancedMapTiles.add(tile7);
        BottomlessPitTile tile8 = new BottomlessPitTile(getMapTile(16,28).getLocation());
        enhancedMapTiles.add(tile8);

        BottomlessPitTile tile9 = new BottomlessPitTile(getMapTile(13,29).getLocation());
        enhancedMapTiles.add(tile9);
        BottomlessPitTile tile10 = new BottomlessPitTile(getMapTile(14,29).getLocation());
        enhancedMapTiles.add(tile10);
        BottomlessPitTile tile11 = new BottomlessPitTile(getMapTile(15,29).getLocation());
        enhancedMapTiles.add(tile11);
        BottomlessPitTile tile12 = new BottomlessPitTile(getMapTile(16,29).getLocation());
        enhancedMapTiles.add(tile12);

        //______________________________________________________________

        return enhancedMapTiles;
    }

    public ArrayList<Item> loadItems(){
        ArrayList<Item> items = new ArrayList<>();
        boomerangItem = new BoomerangItem(getMapTile(6, 4).getLocation(), new SpriteSheet(ImageLoader.load("BoomerangItem.png"),23,24),"IDLE");
        items.add(boomerangItem);

        return items;
    }

    public ArrayList<Shrine> loadShrines() {
        ArrayList<Shrine> shrines = new ArrayList<>();
        boomerangShrine = new EmptyShrine(2, getMapTile(32, 40).getLocation(),emptyItem);
        boomerangShrine.setInteractScript(new EmptyShrineScript());
        shrines.add(boomerangShrine);

        return shrines;
    }


}
