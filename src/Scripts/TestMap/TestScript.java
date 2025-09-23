package Scripts.TestMap;

import Level.Script;
import ScriptActions.*;

import java.util.ArrayList;

public class TestScript extends Script {

    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> actions = new ArrayList<>();

        actions.add(new LockPlayerScriptAction());

        actions.add(new NPCLockScriptAction());

        actions.add(new NPCFacePlayerScriptAction());

        actions.add(new TextboxScriptAction() {{
            addText("Hi! I'm a test NPC!", new String[] { "Cool", "Neat" });
        }});

        actions.add(new TextboxScriptAction() {{
            addText("Yep!");
        }});

        actions.add(new UnlockPlayerScriptAction());
        return actions;
    }
}
