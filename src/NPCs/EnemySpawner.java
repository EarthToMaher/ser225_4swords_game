package NPCs;

import java.util.Random;

import Level.NPC;
import Utils.Point;
import Level.Map; 
import NPCs.Dinosaur2;
import NPCs.Walrus2;

public class EnemySpawner {
    private Random random; 
    private static int nextEnemyId = 0; 

    public EnemySpawner() {
        this.random = new Random(); 
    }

    // Random 2-4 enemies
    public void spawnRandomEnemies(float areaX, float areaY, float areaWidth, float areaHeight, Map map) {
        int numEnemies = random.nextInt(3) + 2;  

        for (int i = 0; i < numEnemies; i++) {
          //Spawn point
            float spawnX = areaX + random.nextFloat() * areaWidth;
            float spawnY = areaY + random.nextFloat() * areaHeight;

            // Randomize enemy type
            boolean isDinosaur = random.nextBoolean();
            NPC enemy;
            if (isDinosaur) {
                enemy = new Dinosaur2(nextEnemyId++, new Point(spawnX, spawnY));
            } else {
                enemy = new Walrus2(nextEnemyId++, new Point(spawnX, spawnY));
            }

            // Add to map
            map.addNPC(enemy);
            System.out.println("Spawned " + (isDinosaur ? "Dinosaur2" : "Walrus2") + " at (" + spawnX + ", " + spawnY + ")");
        }
    }

    // Spawn Area
    public void spawnInDefaultArea(Map map) {
        spawnRandomEnemies(100, 100, 300, 200, map);
    }
}