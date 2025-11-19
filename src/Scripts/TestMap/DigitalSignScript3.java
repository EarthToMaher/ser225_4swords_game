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
            addText("Researchers Note #3:");
            addText("The chargers and shooters are good defense,");
            addText("But sometimes the best solution to protecting assets is\n the simplest one.");
            addText("A massive bottomless pit!");
            
        }});
        scriptActions.add(new UnlockPlayerScriptAction());

        return scriptActions;
    }
}