package Scripts.TestMap;

import java.util.ArrayList;

import Level.*;
import ScriptActions.*;

public class DigitalSignScript3 extends Script {

    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> scriptActions = new ArrayList<>();

        scriptActions.add(new LockPlayerScriptAction());
        scriptActions.add(new NPCFacePlayerScriptAction());
        scriptActions.add(new TextboxScriptAction() {{
            addText("WARNING:");
            addText("Dangerous test area up ahead.");
            addText("Ensure you have the proper safety defenses \n(Like Pressing 'E')");
            
        }});
        scriptActions.add(new UnlockPlayerScriptAction());

        return scriptActions;
    }
}