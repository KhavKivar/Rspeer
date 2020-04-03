import org.rspeer.script.Script;
import org.rspeer.script.ScriptMeta;
import org.rspeer.script.task.Task;
import org.rspeer.script.task.TaskScript;
import org.rspeer.ui.Log;
import task.Enchanted;
import task.OpenBank;


@ScriptMeta(developer = "Khav Kivar",name ="SapphireRing",desc = "Enchanted Sapphire Ring")
public class SapphireChanted extends TaskScript{
    private static final Task[] TASKS = {
       new Enchanted(), new OpenBank()
    };


    @Override
    public void onStart() {
        submit(TASKS);

    }

}
