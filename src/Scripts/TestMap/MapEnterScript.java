package Scripts.TestMap;

import java.util.ArrayList;

import Level.*;
import ScriptActions.*;

public class MapEnterScript extends Script {

    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> scriptActions = new ArrayList<>();
        scriptActions.add(new LockPlayerScriptAction());
        scriptActions.add(new TextboxScriptAction() {{
            addText("Incoming Transmission...");
            addText("You two are some of the only uncompromised \nbots left in the facility.");
            addText("Your task is to reach the server room and upload a \npatch to bring the other robots back under control.");
            addText("Remember you can press E to fire a charge \nto knock out other robots.");
            addText("You can dash with R at the cost of two charges.");
            addText("You can also press SPACE to interact with Item \nShrines and friendly robots.");
            addText("Good luck on your mission!");

        }});
        scriptActions.add(new UnlockPlayerScriptAction());
        return scriptActions;
    }
}
