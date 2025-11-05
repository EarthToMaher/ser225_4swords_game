package Scripts.TestMap;

import java.util.ArrayList;

import Level.*;
import ScriptActions.*;

public class MouseDroidScript extends Script {

    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> scriptActions = new ArrayList<>();

        scriptActions.add(new LockPlayerScriptAction());
        scriptActions.add(new NPCFacePlayerScriptAction());
        scriptActions.add(new TextboxScriptAction() {{
            addText("I was unplugged during the code upload.");
            addText("It was because I kept crashing.");
            addText("Into walls...");
        }});
        scriptActions.add(new UnlockPlayerScriptAction());

        return scriptActions;
    }
}
