package Scripts.TestMap;

import java.util.ArrayList;

import Level.Script;
import ScriptActions.*;

// script for talking to MrToon Placeholder NPC
public class MrToonScript extends Script {

    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> scriptActions = new ArrayList<>();
        scriptActions.add(new LockPlayerScriptAction());

        scriptActions.add(new NPCFacePlayerScriptAction());

        scriptActions.add(new ConditionalScriptAction() {{
            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("hasTalkedToToon", false));
                addScriptAction(new TextboxScriptAction() {{
                    addText("Stay away!");
                    addText("Oh, sorry, I thought you were that purple monkey");
                    addText("He always seems to have it out for me.\nHe calls me names.");
                    addText("I think I'm going to make a video about him.\nAnd his wretched game.");
                }});
                addScriptAction(new ChangeFlagScriptAction("hasTalkedToToon", true));
            }});

            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("hasTalkedToToon", true));
                addScriptAction(new TextboxScriptAction("Before we start this video, I would like to make it clear...\n*You decide to stop listening*"));
            }});
        }});

        scriptActions.add(new UnlockPlayerScriptAction());

        return scriptActions;
    }
}
