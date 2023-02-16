package Services;

import Models.*;
import Enums.*;
import java.util.*;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.*;


public class Chase {
    /**
     * State Chase
     * initial state: ada prey, jika ada lebih dari 1 prey
     * final state: prey dimangsa(makan/torpedo) atau prey di luar jangkauan
     */
    private static GameObject bot;
    private static GameState gameState;

    public static PlayerActions playerAction;
    public static GameObject prey;
    public static int heading;

    private static final double MAX_CHASE_TIME = 5.0;
    private static final Integer TORPEDO_SPEED = 60;

    public static void update(GameObject bot, GameState gameState){
        Chase.bot = bot;
        Chase.gameState = gameState;
    }

    public static void setAction(){
        getPrey();
        if(Chase.prey!=null){
            Chase.heading = Algorithm.getHeadingBetween(Chase.bot, Chase.prey);
            System.out.println("at "+Chase.heading);
        }else{
            Chase.playerAction = PlayerActions.STOP;
        }
    }

    public static GameObject getPrey(){
        var prey = findShootable();
        if(prey != null){ // is any shootable?
            Chase.prey = prey;
            Chase.playerAction = PlayerActions.FIRETORPEDOES;
            System.out.println("Shooting");
        }else{
            prey = findChaseable();
            if(prey != null){ // is any chaseable?
                Chase.prey = prey;
                Chase.playerAction = PlayerActions.FORWARD;
                System.out.println("Chasing");
            }
        }
        return prey;
    }
    
    public static GameObject findChaseable(){
        var objListByBorderDistance = Algorithm.getObjectsByBorderDistance(bot, ObjectTypes.PLAYER, Chase.gameState);
        objListByBorderDistance.remove(0); // remove self
        
        for(GameObject bot : objListByBorderDistance){
            if(Algorithm.predictCollisionTime(Chase.bot, bot) <= MAX_CHASE_TIME && bot.size*2 <= Chase.bot.size)
            return bot;
        }
        return null;
    }

    public static GameObject findShootable(){
        var objListBySize = Algorithm.getObjectsBySize(ObjectTypes.PLAYER, gameState);
        objListBySize.remove(0); // remove self

        for(GameObject bot : objListBySize){
            var distance = Algorithm.getDistanceBetween(Chase.bot, bot);
            var torpedoTravelTime = Algorithm.toTime(TORPEDO_SPEED, distance);
            if(Algorithm.calcualteCenterToBorderTime(bot) >= torpedoTravelTime)
                return bot;
        }
        return null;
    }
    

}
