package com.example.pixelgame;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

public class GameStatistic {
    @Attribute
    public final String enemy;
    @Element
    private int played;
    @Element
    private int won;

    public GameStatistic(String enemy){
        this(enemy,0,0);
    }

    public GameStatistic(String enemy, int playedGames, int wonGames) {
        this.enemy = enemy;
        this.played = playedGames;
        this.won = wonGames;
    }

    public int wonGames(){
        return won;
    }

    public int lostGames(){
        return played-won;
    }

    public int allGames(){
        return played;
    }

    public void addResult(boolean won){
        played++;
        if (won){
            this.won++;
        }
    }

    public float winRatio(){
        return ((float)won)/played;
    }

    public float loseRatio(){
        return ((float) lostGames())/played;
    }
}
