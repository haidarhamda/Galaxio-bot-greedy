package Models;

import Enums.*;
import java.util.*;
import java.util.stream.*;

public class Algorithm {
    public static List<GameObject> getObjectsByDistance(GameObject obj, ObjectTypes type, GameState gameState, boolean isPlayer){
        if (!gameState.getGameObjects().isEmpty()) {
            var objectList = Algorithm.getGameObjects(type, gameState)
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
            var objectList = Algorithm.getGameObjects(type, gameState)
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
            var objectList = Algorithm.getGameObjects(type, gameState)
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

    private static List<GameObject> getGameObjects(ObjectTypes type, GameState gameState){
        List<GameObject> gameObjects;
        if(type==ObjectTypes.PLAYER){
            gameObjects = gameState.getPlayerGameObjects();
        }else{
            gameObjects = gameState.getGameObjects();
        }
        return gameObjects;
    }

    public static boolean checkCollision(GameObject object1,GameObject object2, List<GameObject> debuff){
        var x1=object1.getPosition().getX();
        var y1=object1.getPosition().getX();
        var x2=object2.getPosition().getX();
        var y2=object2.getPosition().getY();
//        var cx=debuff.getPosition().getX();
//        var cy=debuff.getPosition().getY();
        if (x2 - x1 != 0) {
            var m = (y2 - y1) / (x2 - x1);
            var c = (((y2 - y1) * x1) / (x2 - x1)) + y1;
            for (GameObject debuf:debuff) {
                double distance=(Math.abs(-m*debuf.getPosition().getX()+1*debuf.getPosition().getY()+c))/
                        Math.sqrt(Math.pow(-m,2)+Math.pow(1,2));
                var radius = debuf.getSize();
                if (radius>=distance){
                    return true;
                } else {
                    return false;
                }
            }
            return false;
//            var descriminant = Math.pow((2 * m * c), 2) - 4 * (1 + Math.pow(m, 2)) * (Math.pow(c, 2) - Math.pow(radius, 2));
//            if (descriminant > 0) {
//                return false;
//            } else {
//                return true;
//            }
        } else {return false;}
    }
}
