package Scripts.TestMap;

import java.util.ArrayList;

import Level.MapEntity;
import Level.Script;
import Level.Shrine;
import ScriptActions.*;
import Items.Item;

//SCript for interacting with an empty shrine
public class BoomerangShrineScript extends Script {

@Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> scriptActions = new ArrayList<>();

        MapEntity entity = getEntity();
        Shrine shrine = (Shrine) entity;
        Item itemToPickUp = shrine.currentItem;
        Item itemToPlace = getPlayer().currentItem;
        getPlayer().setItem(itemToPickUp);
        shrine.setItem(itemToPlace);
        if(itemToPlace!=null)itemToPlace.PlaceItem();
        if(itemToPickUp!=null) itemToPickUp.PickUpItem(getPlayer());
        scriptActions.add(new LockPlayerScriptAction());

        scriptActions.add(new ConditionalScriptAction() {{
            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addScriptAction(new TextboxScriptAction() {{
                    addText("Boomerang: Moves forward a short distance then back.\n Hits enemies and collects items along the way!");
                }});
            }});
        }});

        scriptActions.add(new UnlockPlayerScriptAction());

        return scriptActions;
    }
}
