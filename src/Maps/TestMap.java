package Maps;

import EnhancedMapTiles.PushableRock;
import EnhancedMapTiles.Projectile;
import Level.*;
import NPCs.*;
import Scripts.SimpleTextScript;
import Scripts.TestMap.*;
import Tilesets.CommonTileset;
import Shrines.EmptyShrine;
import NPCs.Bolt;

import java.util.ArrayList;

// Represents a test map to be used in a level
public class TestMap extends Map {

    public static ArrayList<NPC> npcs = new ArrayList<>();
    Projectile projectile;


    public TestMap() {
        super("test_map.txt", new CommonTileset());
        this.playerStartPosition = getMapTile(17, 20).getLocation();
    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();

        PushableRock pushableRock = new PushableRock(getMapTile(2, 7).getLocation());
        enhancedMapTiles.add(pushableRock);

        projectile = new Projectile(getMapTile(5, 7).getLocation());
        enhancedMapTiles.add(projectile);

        return enhancedMapTiles;
    }

    @Override
    public ArrayList<NPC> loadNPCs() {


        TestNPC npc = new TestNPC(0, getMapTile(8, 20).getLocation().subtractY(40));
        npc.setInteractScript(new TestScript());
        npcs.add(npc);

        inactiveRobotStatic = new InactiveRobot(5, getMapTile(8, 20).getLocation().subtractY(50));
        npcs.add(inactiveRobotStatic);

        Walrus walrus = new Walrus(1, getMapTile(10, 28).getLocation().subtractY(50));
        walrus.setInteractScript(new WalrusScript());
        npcs.add(walrus);

        Walrus2 walrus2 = new Walrus2(4, getMapTile(11, 20).getLocation().subtractY(50));
        walrus2.setInteractScript(new WalrusScript());
        npcs.add(walrus2);
        

        Dinosaur dinosaur = new Dinosaur(2, getMapTile(13, 4).getLocation());
        dinosaur.setExistenceFlag("hasTalkedToDinosaur");
        dinosaur.setInteractScript(new DinoScript());
        npcs.add(dinosaur);
        
        Bug bug = new Bug(3, getMapTile(7, 12).getLocation().subtractX(20));
        bug.setInteractScript(new BugScript());
        npcs.add(bug);
        MrToon mrToon = new MrToon(4, getMapTile(8, 11).getLocation());
        mrToon.setInteractScript(new MrToonScript());
        npcs.add(mrToon);

        return npcs;
    }
    

    public ArrayList<Shrine> loadShrines()
    {
        ArrayList<Shrine> shrines = new ArrayList<>();

        //EmptyShrine emptyShrine = new EmptyShrine(5, getMapTile(13, 16).getLocation());
        //emptyShrine.setInteractScript(new EmptyShrineScript());
        //shrines.add(emptyShrine);

        return shrines;
    }

    public ArrayList<Collectible> loadCollectibles()
    {
        ArrayList<Collectible> collectibles = new ArrayList<>();
        Bolt bolt = new Bolt( 6, getMapTile(13,16).getLocation());
        collectibles.add(bolt);

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
        getMapTile(21, 19).setInteractScript(new SimpleTextScript("Cat's house"));

        getMapTile(7, 26).setInteractScript(new SimpleTextScript("Walrus's house"));

        getMapTile(20, 4).setInteractScript(new SimpleTextScript("Dino's house"));

        getMapTile(2, 6).setInteractScript(new TreeScript());
    }
}

