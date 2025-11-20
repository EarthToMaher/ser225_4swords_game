package Items;

import Level.Player;
import GameObject.Frame;
import Utils.Point;
import Utils.*;

public class JetpackItem extends Item {

    private long activeUntilMillis = 0;
    private final long DURATION_MS = 4000;

    public JetpackItem(Frame frame) {
        super(frame);
    }

    public JetpackItem(float x, float y, Frame frame) {
        super(x, y, frame);
    }

    public JetpackItem(Point location, Frame frame) {
        super(location.x, location.y, frame);
    }

    @Override
    public void UseItem(Player player) {
        itemIsActive = true;
        isUncollidable = false;
        activeUntilMillis = System.currentTimeMillis() + DURATION_MS;
        SoundManager.playSoundEffect("Jetpack.wav");
    }

    @Override
    public void update(Player player) {
        super.update(player);

        if (itemIsActive && System.currentTimeMillis() > activeUntilMillis) {
            ItemFinished();
        }
    }

    @Override
    public void ItemFinished() {
        super.ItemFinished();
        itemIsActive = false;
        activeUntilMillis = 0;
    }

    public boolean isJetpackActive() {
        return itemIsActive && System.currentTimeMillis() <= activeUntilMillis;
    }
}