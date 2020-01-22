package com.example.pixelgame;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root
public class PlayerData {
    @Attribute
    String playerName;
    @ElementList
    private List<GameStatistic> statistics;

    public PlayerData(String name){
        this(name,new ArrayList<GameStatistic>());
    }

    public PlayerData(String playerName, ArrayList<GameStatistic> statistics){
        this.playerName=playerName;
        this.statistics=statistics;
    }

    public SummaryStatistic summary(){
        return new SummaryStatistic(statistics);
    }

    public void addStatistic(String deviceID,String name){
        if(!containsStatisticTo(deviceID)){
            statistics.add(new GameStatistic(deviceID,name));
        }
    }

    public boolean containsStatisticTo(String deviceID){
        for (GameStatistic statistic : statistics) {
            if(statistic.deviceID.equals(deviceID)){
                return true;
            }
        }
        return false;
    }

    public GameStatistic get(String deviceID){
        GameStatistic result=null;
        for (GameStatistic gameStatistic : statistics) {
            if (gameStatistic.deviceID.equals(deviceID)) {
                result=gameStatistic;
                break;
            }
        }
        return result;
    }
}
