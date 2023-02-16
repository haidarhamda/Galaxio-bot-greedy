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

    }

    private static void escape_now() {
        var torpedo = detectTorpedo();
        if (torpedo != null) {
            Escape.avoidedObjects = torpedo;
            if (bot.getSize() > 10 && bot.getSize() < 30) {
                Escape.heading = Algorithm.getHeadingBetween(Escape.bot, Escape.avoidedObjects) + 130;
                Escape.playerAction = PlayerActions.START_AFTERBURNER;
            } else if (bot.getRadius() > 30 && bot.getSize() > 50 && bot.getSize() < 70) {
                Escape.heading = Algorithm.getHeadingBetween(Escape.bot, Escape.avoidedObjects) + 130;
                Escape.playerAction = PlayerActions.FIRETELEPORT;
                Escape.playerAction = PlayerActions.TELEPORT;
            } else {
                Escape.heading = Algorithm.getHeadingBetween(Escape.bot, Escape.avoidedObjects) + 130;
                Escape.playerAction = PlayerActions.FORWARD;
            }
        } else {
            var other_ship = detectShip();
            if (other_ship != null) {
                    Escape.avoidedObjects = other_ship;
                if (bot.getSize() > 10 && bot.getSize() < 30) {
                    Escape.heading = Algorithm.getHeadingBetween(Escape.bot, Escape.avoidedObjects) + 130;
                    Escape.playerAction = PlayerActions.START_AFTERBURNER;
                } else if (bot.getRadius() > 30 && bot.getSize() > 50 && bot.getSize() < 70) {
                    Escape.heading = Algorithm.getHeadingBetween(Escape.bot, Escape.avoidedObjects) + 130;
                    Escape.playerAction = PlayerActions.FIRETELEPORT;
                    Escape.playerAction = PlayerActions.TELEPORT;
                } else {
                    Escape.heading = Algorithm.getHeadingBetween(Escape.bot, Escape.avoidedObjects) + 130;
                    Escape.playerAction = PlayerActions.FORWARD;
                }
            }
        }
    }

    private static GameObject detectTorpedo() {
        List<GameObject> objects=gameState.gameObjects;
        if (Algorithm.cekTorpedo(objects)) {
            return bot;
        }
        return null;
    }

    private static GameObject getToOtherBot(){
        return gameState.getPlayerGameObjects()
                .stream().filter(item -> item.id != Escape.bot.id)
                .sorted(Comparator
                        .comparing(item -> Algorithm.getDistanceBetween(bot, item)))
                .collect(Collectors.toList()).get(0);
    }

    private static GameObject detectShip() {
        List<GameObject> objects=gameState.gameObjects;
        if (getToOtherBot().getSize() <= bot.getSize() - 10) {
            return bot;
        }
        return null;
    }

    
}