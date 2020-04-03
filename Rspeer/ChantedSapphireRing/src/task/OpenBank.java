package task;

import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.commons.BankLocation;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.task.Task;

public class OpenBank extends Task {
    @Override
    public boolean validate() {
        return finishRecoils();
    }

    @Override
    public int execute() {
        if(Bank.isOpen()){
            Bank.depositAllExcept(x->x.getName().equals("Cosmic rune"));
            Time.sleep(Random.high(600,800));
            Bank.withdrawAll(1637);
            Time.sleep(Random.high(800,1100));
            Bank.close();
            Time.sleepUntil(()->!Bank.isOpen(),Random.low(500,800),3000);
        }
        else{
           BankLocation bankLocation=BankLocation.CATHERBY;
           Bank.open(bankLocation);
        }
        return Random.high(400,650);
    }


    private boolean finishRecoils(){
        Boolean isx = Inventory.containsOnly(x->x.getId()==2550 ||x.getName().equals("Cosmic rune"));
        if(isx)return true;
        return false;
    }

}
