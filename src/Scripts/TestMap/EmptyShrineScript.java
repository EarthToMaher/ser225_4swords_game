package Scripts.TestMap;

import java.util.ArrayList;

import Level.MapEntity;
import Level.Script;
import Level.Shrine;
import ScriptActions.*;

//SCript for interacting with an empty shrine
public class EmptyShrineScript extends Script {

@Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> scriptActions = new ArrayList<>();
        scriptActions.add(new LockPlayerScriptAction());

        scriptActions.add(new ConditionalScriptAction() {{
            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addScriptAction(new TextboxScriptAction() {{
                    MapEntity entity = getEntity();
                    Shrine shrine = (Shrine) entity;
                    getPlayer().setItem(shrine.currentItem);
                    shrine.currentItem.PickUpItem(getPlayer());
                    addText("Nothing: Just empty space");
                }});
            }});
        }});

        scriptActions.add(new UnlockPlayerScriptAction());

        return scriptActions;
    }
}
