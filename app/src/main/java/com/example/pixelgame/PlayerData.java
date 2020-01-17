package com.example.pixelgame;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root
public class PlayerData {
    @Attribute
    String name;
    @ElementList
    private List<GameStatistic> statistics = new ArrayList<>();

    public PlayerData(String name) {
        this.name = name;
    }

    public GameStatistic statisticSummary(){
        int played=0;
        int won=0;
        for (GameStatistic gameStatistic : statistics) {
            played+=gameStatistic.allGames();
            won+=gameStatistic.wonGames();
        }
        return new GameStatistic("","Summary",played,won);
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
