package Services;
import Enums.*;
import Models.*;
import java.util.*;
import java.util.stream.*;

import static java.lang.Math.*;
import static java.lang.Math.pow;

public class Escape {
    private static GameObject bot;
    private static GameState gameState;
    private static GameObject avoidedObjects;

    public static PlayerActions playerAction;
    public static int heading;
    
    public static void setAction(GameObject bot, GameState gameState) {
        Escape.bot = bot;
        Escape.gameState = gameState;

        // escape_now();
        Escape.heading = Algorithm.getHeadingBetween(Escape.bot, Escape.avoidedObjects);

    }

    public static void escape_radius() {
        
    }
    
}