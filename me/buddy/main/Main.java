package me.buddy.main;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.Category;
import org.parabot.environment.scripts.Script;
import org.parabot.environment.scripts.ScriptManifest;
import org.parabot.environment.scripts.framework.LoopTask;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.rev317.min.api.methods.Inventory;
import org.rev317.min.api.methods.Players;
import org.rev317.min.api.methods.SceneObjects;
import org.rev317.min.api.methods.Skill;
import org.rev317.min.api.wrappers.SceneObject;



/**
 * Created by Jakob on 9/22/15.
 *
 *1  Check if we're in ardy
 *2      Yes: Move to the table and thieve until next level set
 *3      No: Teleport to ardy
 *4          Yes: Goto 2
 *5          No: RIP
 */


//script info
@ScriptManifest(
        author = "buddyrich",
        name = "buddyThieve",
        category = Category.THIEVING,
        version = 0.2,
        description = "Part one in a series for 1-99 skillin on pkHonor",
        servers = {"PKHonor"})


public class Main extends Script implements LoopTask {

    private int TABLES = 2561;
    private final int LOG_ID = 996; //coins my dude

    @Override
    public boolean onExecute() {
        System.out.println("buddyThieve Executed");
        return true;

    }

    @Override
    public void onFinish() {
        System.out.println("buddyThieve Disabled");
        //fin
    }


    @Override
    public int loop() {
        SceneObject table = table();

        if(table != null){
            //check if we got room for coins
            if(!Inventory.isFull()) {
                if(Skill.THIEVING.getLevel() <= 19){
                    TABLES = 2561;
                }else if (Skill.THIEVING.getLevel() >= 75 ) {
                    TABLES = 2562;
                    System.out.println("75");
                }else if (Skill.THIEVING.getLevel() >= 65) {
                    TABLES = 2564;
                    System.out.println("65");
                }else if (Skill.THIEVING.getLevel() >= 50) {
                    TABLES = 2565;
                    System.out.println("50");
                }else if (Skill.THIEVING.getLevel() >= 35) {
                    TABLES = 2563;
                    System.out.println("35");
                }else if (Skill.THIEVING.getLevel() >= 20) {
                    TABLES = 2560;
                    System.out.println("20");
                }
                //check if we aint doin shit
                if(Players.getMyPlayer().getAnimation() == -1){
                    table.interact(SceneObjects.Option.STEAL_FROM);

                    Time.sleep(new SleepCondition() {
                        @Override
                        public boolean isValid() {
                            return Players.getMyPlayer().getAnimation() != -1;
                        }
                    }, 3000);

                }
            }
        }
        return 200;
    }

    private SceneObject table(){
        for(SceneObject table : SceneObjects.getNearest(TABLES)){
            if(table != null){
                return table;

            }

        }

        return null;
    }


}
