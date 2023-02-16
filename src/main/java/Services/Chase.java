package Services;

import Models.*;
import Enums.*;
import java.util.*;
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
    public static GameObject aimPrey;
    public static GameObject chasePrey;
    public static int heading;
    public static boolean aiming = true;

    private static final double MAX_CHASE_TIME = 5.0;
    private static final Integer TORPEDO_SPEED = 60;

    public static void update(GameObject bot, GameState gameState){
        Chase.bot = bot;
        Chase.gameState = gameState;
    }

    public static void setAction(){
        Chase.getPrey();
        if(Chase.chasePrey != null){
            Chase.heading = Algorithm.getHeadingBetween(Chase.bot, Chase.chasePrey);
            Chase.playerAction = PlayerActions.FORWARD;
        }
        if(Chase.aimPrey != null){
            if(Chase.aiming){
                Chase.heading = Algorithm.getHeadingBetween(Chase.bot, Chase.aimPrey);
                Chase.playerAction = PlayerActions.FIRETORPEDOES;
                Chase.aiming = false;
            }else if(Chase.chasePrey == null){
                Chase.heading = Algorithm.getHeadingBetween(Chase.bot, Chase.aimPrey);
                Chase.playerAction = PlayerActions.FORWARD;
                Chase.aiming = true;
            }else{
                Chase.aiming = true;
            }

        }
        // if(Chase.aiming){

        //     Chase.aiming = false;
        // }
        // System.out.println("shooter "+Chase.bot.id);
        // System.out.println("prey "+Chase.prey.id);
        // System.out.println("at "+Chase.heading);
    }

    public static void getPrey(){
        Chase.aimPrey = findShootable();
        if(aimPrey != null){ // is any shootable?            
            
            System.out.println("Shooting");
        }
        Chase.chasePrey = findChaseable();
        if(chasePrey != null){ // is any chaseable?
            
            System.out.println("Chasing");
        }
    }
    
    public static GameObject findChaseable(){
        var objListByBorderDistance = Algorithm.getObjectsByBorderDistance(bot, ObjectTypes.PLAYER, Chase.gameState);
        objListByBorderDistance.remove(Chase.bot); // remove self

        var gasClouds = Algorithm.getObjectsByDistance(bot, ObjectTypes.GAS_CLOUD, gameState, false);
        var wormholes = Algorithm.getObjectsByDistance(bot, ObjectTypes.WORMHOLE, gameState, false);
        
        for(GameObject bot : objListByBorderDistance){
            if(Algorithm.predictCollisionTime(Chase.bot, bot) <= MAX_CHASE_TIME && bot.size*2 <= Chase.bot.size){
                if(!Algorithm.checkCollision(Chase.bot, bot, gasClouds) && 
                !Algorithm.checkCollision(Chase.bot, bot, wormholes))
                return bot;
            }
        }
        return null;
    }

    public static GameObject findShootable(){
        var objListBySize = Algorithm.getObjectsBySize(ObjectTypes.PLAYER, gameState);
        objListBySize.remove(Chase.bot); // remove self

        var gasClouds = Algorithm.getObjectsByDistance(bot, ObjectTypes.GAS_CLOUD, gameState, false);
        var asteroids = Algorithm.getObjectsByDistance(bot, ObjectTypes.ASTEROID_FIELD, gameState, false);
        var wormholes = Algorithm.getObjectsByDistance(bot, ObjectTypes.WORMHOLE, gameState, false);

        for(GameObject bot : objListBySize){
            var distance = Algorithm.getDistanceBetween(Chase.bot, bot);
            var torpedoTravelTime = Algorithm.toTime(TORPEDO_SPEED, distance);
            if(Algorithm.calcualteCenterToBorderTime(bot) >= torpedoTravelTime){
                if(!Algorithm.checkCollision(Chase.bot, bot, gasClouds) && 
                !Algorithm.checkCollision(Chase.bot, bot, asteroids) &&
                !Algorithm.checkCollision(Chase.bot, bot, wormholes))
                    return bot;
            }
        }
        return null;
    }
    

}
