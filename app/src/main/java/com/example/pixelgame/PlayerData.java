package com.example.pixelgame;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

import java.util.HashMap;

@Root
public class PlayerData {
    @Attribute(name = "name")
    String playerName;
    @ElementMap
    private HashMap<String, GameStatistic> statisticMap;

    public PlayerData(String name) {
        this(name, new HashMap());
    }

    public PlayerData(String playerName, HashMap statisticMap) {
        this.playerName = playerName;
        this.statisticMap = statisticMap;
    }

    public SummaryStatistic summary() {
        return new SummaryStatistic(statisticMap.values());
    }

    public void addStatistic(String deviceID, String name) {
        if (deviceNotIncluded(deviceID)) {
            statisticMap.put(deviceID, new GameStatistic(deviceID, name));
        }
    }

    private boolean deviceNotIncluded(String deviceID) {
        return !statisticMap.containsKey(deviceID);
    }

    public GameStatistic get(String deviceID) {
        return statisticMap.get(deviceID);
    }
}
