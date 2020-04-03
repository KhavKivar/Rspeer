import org.rspeer.script.Script;
import org.rspeer.script.ScriptMeta;
import org.rspeer.script.task.TaskScript;
import org.rspeer.ui.Log;

@ScriptMeta(developer = "Khav Kivar", name = "PowerMinining", desc = "rock")
public class PowerMining extends Script {
    @Override
    public void onStart() {

    }

    @Override
    public int loop() {
        Log.fine("Hello");
        return 1000;
    }
}
