package Scripts.TestMap;

import Level.Script;
import ScriptActions.*;

import java.util.ArrayList;

public class DigitalSignScript3 extends Script {

    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> scriptActions = new ArrayList<>();

        scriptActions.add(new LockPlayerScriptAction());
        scriptActions.add(new NPCFacePlayerScriptAction());
        scriptActions.add(new TextboxScriptAction() {{
            addText("Researchers Note #1:");
            addText("Reminder to staff that robots cannot share items \namongst themselves.");
            addText("This is to prevent robots from cheating their \nway through testing chambers");
            
        }});
        scriptActions.add(new UnlockPlayerScriptAction());

        return scriptActions;
    }
}