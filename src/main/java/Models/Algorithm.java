package Models;

import Enums.*;
import java.util.*;
import java.util.stream.*;

public class Algorithm {
    public static List<GameObject> getObjectsByDistance(GameObject obj, ObjectTypes type, GameState gameState){
        if (!gameState.getGameObjects().isEmpty()) {
            var objectList = gameState.getGameObjects()
                    .stream().filter(item -> item.getGameObjectType() == type)
                    .sorted(Comparator
                            .comparing(item -> Algorithm.getDistanceBetween(obj, item)))
                    .collect(Collectors.toList());
            return objectList;
        }
        return null;
    }

    public static List<GameObject> getObjectsByBorderDistance(GameObject obj, ObjectTypes type, GameState gameState){
        if (!gameState.getGameObjects().isEmpty()) {
            var objectList = gameState.getGameObjects()
                    .stream().filter(item -> item.getGameObjectType() == type)
                    .sorted(Comparator
                            .comparing(item -> Algorithm.getBorderDistanceBetween(obj, item)))
                    .collect(Collectors.toList());
            return objectList;
        }
        return null;
    }

    public static List<GameObject> getObjectsBySize(ObjectTypes type, GameState gameState){
        if (!gameState.getGameObjects().isEmpty()) {
            var objectList = gameState.getGameObjects()
                    .stream().filter(item -> item.getGameObjectType() == type)
                    .sorted(Comparator
                            .comparing(item -> item.size))
                    .collect(Collectors.toList());
            return objectList;
        }
        return null;
    }

    public static double getDistanceBetween(GameObject object1, GameObject object2) {
        var triangleX = Math.abs(object1.getPosition().x - object2.getPosition().x);
        var triangleY = Math.abs(object1.getPosition().y - object2.getPosition().y);
        return Math.sqrt(triangleX * triangleX + triangleY * triangleY);
    }

    public static double getDistanceBetween(Position object1, Position object2){
        var triangleX = Math.abs(object1.x - object2.x);
        var triangleY = Math.abs(object1.y - object2.y);
        return Math.sqrt(triangleX * triangleX + triangleY * triangleY);
    }

    public static double getBorderDistanceBetween(GameObject object1, GameObject object2){
        var triangleX = Math.abs(object1.getPosition().x - object2.getPosition().x);
        var triangleY = Math.abs(object1.getPosition().y - object2.getPosition().y);
        return Math.sqrt(triangleX * triangleX + triangleY * triangleY)-object1.size-object2.size;
    }

    public static double predictCollisionTime(GameObject object1, GameObject object2){
        /**
         * Will predict by assuming target will move on the opposite direction,
         * t = d/(v2-v2)
         */
         var d = getBorderDistanceBetween(object1, object2);
         return Math.abs(d/(object1.speed-object2.speed));
    }

    public static double toTime(Integer speed, double distance){
        return distance/speed;
    }

    public static double calcualteCenterToBorderTime(GameObject object1){
        return Algorithm.toTime(object1.speed, object1.size);
    }

    public static int getHeadingBetween(GameObject object1,GameObject object2) {
        var direction = toDegrees(Math.atan2(object2.getPosition().y - object1.getPosition().y,
                object2.getPosition().x - object1.getPosition().x));
        return (direction + 360) % 360;
    }

    public static int toDegrees(double v) {
        return (int) (v * (180 / Math.PI));
    }

}
