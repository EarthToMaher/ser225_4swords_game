package Scripts.TestMap;

import java.util.ArrayList;

import Level.*;
import ScriptActions.*;

public class DigitalSignScript extends Script {

    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> scriptActions = new ArrayList<>();

        scriptActions.add(new LockPlayerScriptAction());
        scriptActions.add(new NPCFacePlayerScriptAction());
        scriptActions.add(new TextboxScriptAction() {{
            addText("Researchers Note #2:");
            addText("We've managed to save a lot on security costs this \nyear.");
            addText("Making the keycards half the size of our doors \nmakes them harder to steal");
        }});
        scriptActions.add(new UnlockPlayerScriptAction());

        return scriptActions;
    }
}