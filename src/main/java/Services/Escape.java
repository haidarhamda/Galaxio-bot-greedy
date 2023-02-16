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

        escape_now();
        Escape.heading = Algorithm.getHeadingBetween(Escape.bot, Escape.avoidedObjects) + 180;

    }

    private static void escape_now() {
        var radius = detectDistanceToRadius();
        if (radius != -9999 && radius < 10) {
            Escape.avoidedObjects = radius;
            if (bot.getSize() > 10 && bot.getSize() < 15) {
                Escape.playerAction = PlayerAction.START_AFTERBURNER;
            } else if (getRadius() > 30) {
                Escape.playerAction = PlayerAction.FIRE_TELEPORTER;
                Escape.playerAction = PlayerAction.TELEPORT;
            } else {
                Escape.playerAction = PlayerAction.FORWARD;
            }
        } else {
            var other_ship = detectShip();
            if (other_ship != null && other_ship.getSize() <= bot.getSize() - 10) {
                Escape.avoidedObjects = other_ship;
                if (bot.getSize() > 10 && bot.getSize() < 20) {
                    Escape.playerAction = PlayerAction.START_AFTERBURNER;
                } else if (getRadius() > 30) {
                    Escape.playerAction = PlayerAction.FIRE_TELEPORTER;
                    Escape.playerAction = PlayerAction.TELEPORT;
                } else {
                    Escape.playerAction = PlayerAction.FORWARD;
                }
            }
        }
    }

    private static int detectDistanceToRadius() {
        // apa pake boolean, integer, gameobject?
    }

    private static GameObject detectShip() {

    }

    
}