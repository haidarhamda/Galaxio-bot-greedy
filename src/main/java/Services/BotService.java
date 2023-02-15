package Services;

import Enums.*;
import Models.*;
import Services.Fetch;

import java.util.*;
import java.util.stream.*;

import static java.lang.Math.*;
import static java.lang.Math.pow;

public class BotService {
    private GameObject bot;
    private PlayerAction playerAction;
    private GameState gameState;
    private GameObject target;
    private boolean headingBound=false;
    private GameObject center=new GameObject(UUID.randomUUID(),1,0,0,new Position(),ObjectTypes.CENTER);

    public BotService() {
        this.playerAction = new PlayerAction();
        this.gameState = new GameState();
    }


    public GameObject getBot() {
        return this.bot;
    }

    public void setBot(GameObject bot) {
        this.bot = bot;
    }

    public PlayerAction getPlayerAction() {
        return this.playerAction;
    }

    public void setPlayerAction(PlayerAction playerAction) {
        this.playerAction = playerAction;
    }

    public void computeNextPlayerAction(PlayerAction playerAction) {
        playerAction.action = PlayerActions.FORWARD;
        playerAction.heading = new Random().nextInt(360);

        if (!gameState.getGameObjects().isEmpty()) {
            if (cekBound()) {
                updateHeadingToCenter();
                headingBound = true;
            } else {
                if (getDistancetoRearBound()>bot.getSize()+50) {
                    headingBound = false;
                }
            }
            if (!headingBound) {
//                target = nearestObject(ObjectTypes.FOOD);

                var objectInside = getGameObjectInside();
                var objectInFront = getGameObjectInsideFront();

//                if (cekInsideFront(objectInFront, ObjectTypes.SUPERFOOD)) {
//                    target = getNearestInFront(objectInFront, ObjectTypes.SUPERFOOD);
//                }

                Fetch fetch=new Fetch();
                fetch.setAction(bot, gameState);
                playerAction.heading=fetch.getHeading();
                System.out.println(playerAction.heading);

//                if (getDistanceBetween(bot, getNearestOtherBot()) < bot.getRadius()) {
//                    runOrFight();
//                }
//                playerAction.heading = getHeadingBetween(target);
//                if (cekTorpedo(objectInside)) {
//                    updateHeading(45);
//                }


//            if (getDistanceBetween(bot,getNearestOtherBot())<bot.getRadius()){
//                playerAction.heading=getHeadingBetween(target);
//            }

            }
        }
//        System.out.println(target.gameObjectType);
        this.playerAction = playerAction;
    }

    public GameState getGameState() {
        return this.gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
        updateSelfState();
    }

    private void updateSelfState() {
        Optional<GameObject> optionalBot = gameState.getPlayerGameObjects().stream().filter(gameObject -> gameObject.id.equals(bot.id)).findAny();
        optionalBot.ifPresent(bot -> this.bot = bot);
    }

    private double getDistanceBetween(GameObject object1, GameObject object2) {
        var triangleX = Math.abs(object1.getPosition().x - object2.getPosition().x);
        var triangleY = Math.abs(object1.getPosition().y - object2.getPosition().y);
        return Math.sqrt(triangleX * triangleX + triangleY * triangleY);
    }

    private int getHeadingBetween(GameObject otherObject) {
        var direction = toDegrees(Math.atan2(otherObject.getPosition().y - bot.getPosition().y,
                otherObject.getPosition().x - bot.getPosition().x));
        return (direction + 360) % 360;
    }
    private int getHeadingBetween(GameObject object1,GameObject object2) {
        var direction = toDegrees(Math.atan2(object2.getPosition().y - object1.getPosition().y,
                object2.getPosition().x - object1.getPosition().x));
        return (direction + 360) % 360;
    }

    private int toDegrees(double v) {
        return (int) (v * (180 / Math.PI));
    }

    public GameObject nearestObject(ObjectTypes objectTypes){
        if (!gameState.getGameObjects().isEmpty()){
            var foodList = gameState.getGameObjects()
                    .stream().filter(item -> item.getGameObjectType() == objectTypes)
                    .sorted(Comparator
                            .comparing(item -> getDistanceBetween(bot, item)))
                    .collect(Collectors.toList());
            return foodList.get(0);
        } else {return null;}
    }
    public void runOrFight(){
        if (getNearestOtherBot()!=null&&getBot().getSize()>getNearestOtherBot().getSize()){
            //pewpew
            piwpiw();
        } else {
            run();
        }
    }
    public void run(){
        target=nearestObject(ObjectTypes.FOOD);
        updateHeading(45);
        if (bot.getSize()>10){
            playerAction.setAction(PlayerActions.START_AFTERBURNER);
            System.out.println(playerAction.getAction());
        } else {
            playerAction.setAction(PlayerActions.STOP_AFTERBURNER);
            System.out.println(playerAction.getAction());
        }
    }
    public void piwpiw(){
        target=getNearestOtherBot();
        if (target!=null){
        playerAction.heading=getHeadingBetween(target);
        playerAction.setAction(PlayerActions.FIRETORPEDOES);}
    }
    public double getDistancetoRearBound(){
        double boundX=gameState.getWorld().getRadius()*cos(playerAction.getHeading()+180);
        double boundY=gameState.getWorld().getRadius()*sin(playerAction.getHeading()+180);
        double distance=sqrt(pow(boundX-bot.getPosition().getX(),2)+pow(boundY-bot.getPosition().getY(),2));
        return distance;
    }
    public double getDistancetoBound(){
        double boundX=gameState.getWorld().getRadius()*cos(playerAction.getHeading());
        double boundY=gameState.getWorld().getRadius()*sin(playerAction.getHeading());
        double distance=sqrt(pow(boundX-bot.getPosition().getX(),2)+pow(boundY-bot.getPosition().getY(),2));
        return distance;
    }
    public boolean cekBound(){
        return getDistancetoBound()<bot.getSize()+50;
    }
    public List<GameObject> getGameObjectInside(){
        List<GameObject> objects=gameState.gameObjects.stream()
                .filter(item->getDistanceBetween(bot,item)<bot.getRadius()).collect(Collectors.toList());
        return objects;
    }
    private boolean cekInside(List<GameObject> gameObjectList,ObjectTypes objectTypes){
        for (GameObject object:gameObjectList) {
            if (object.getGameObjectType()==objectTypes){
                return true;
            }
        }
        return false;
    }
    private GameObject getNearestOtherBot(){
        return gameState.getPlayerGameObjects()
                .stream().filter(item -> item.id != this.bot.id)
                .sorted(Comparator
                        .comparing(item -> getDistanceBetween(bot, item)))
                .collect(Collectors.toList()).get(0);
    }
    private boolean cekFront(GameObject gameObject){
        return getHeadingBetween(gameObject)<30 &&getHeadingBetween(gameObject)>-30;
    }
    private List<GameObject> getGameObjectInsideFront(){
        List<GameObject> objects=gameState.gameObjects.stream()
                .filter(item->cekFront(item)&&getDistanceBetween(bot,item)<bot.radius/2).collect(Collectors.toList());
        return objects;
    }
    private boolean cekInsideFront(List<GameObject> gameObjectList,ObjectTypes objectTypes) {
//        System.out.println(gameObjectList.size());
        for (GameObject object : gameObjectList) {
            if (object.getGameObjectType() == objectTypes) {
                return true;
            }
        }
        return false;
    }
    private GameObject getNearestInFront(List<GameObject> gameObjectList, ObjectTypes objectTypes){
        if (!gameObjectList.isEmpty()){
            var foodList = gameObjectList
                    .stream().filter(item -> item.getGameObjectType() == objectTypes)
                    .sorted(Comparator
                            .comparing(item -> getDistanceBetween(bot, item)))
                    .collect(Collectors.toList());
            return foodList.get(0);
        } else {return null;}
    }
    private boolean cekTorpedo(List<GameObject> gameObjectList){
        if (cekInside(gameObjectList,ObjectTypes.TORPEDO_SALVO)){
            System.out.println(nearestObject(ObjectTypes.TORPEDO_SALVO).currentHeading);
            System.out.println(getHeadingBetween(nearestObject(ObjectTypes.TORPEDO_SALVO),bot));
            return getHeadingBetween(nearestObject(ObjectTypes.TORPEDO_SALVO),bot)<30 &&
                    getHeadingBetween(nearestObject(ObjectTypes.TORPEDO_SALVO),bot)>-30;
        } else {
            return false;
        }
    }
    private void updateHeading(int degree){
        if (playerAction.getHeading()%90<=45){
            playerAction.heading+=degree;
        }else {
            playerAction.heading-=degree;
        }
    }
    private void updateHeadingToCenter(){
        playerAction.heading=getHeadingBetween(bot,center);
    }
}
