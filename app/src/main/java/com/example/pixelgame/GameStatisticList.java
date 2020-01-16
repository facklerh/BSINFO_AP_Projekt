package com.example.pixelgame;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root
public class GameStatisticList {
    @ElementList
    List<GameStatistic> list= new ArrayList<>();

    public GameStatistic summary(){
        int played=0;
        int won=0;
        for (GameStatistic gameStatistic : list) {
            played+=gameStatistic.allGames();
            won+=gameStatistic.wonGames();
        }
        return new GameStatistic("All",played,won);
    }

    public GameStatistic get(String enemy){
        GameStatistic result=null;
        for (GameStatistic gameStatistic : list) {
            if (gameStatistic.enemy.equals(enemy)) {
                result=gameStatistic;
                break;
            }
        }
        return result;
    }
}
