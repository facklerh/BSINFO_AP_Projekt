package com.example.pixelgame;

import java.util.Collection;

public class SummaryStatistic {
    final int playedGames;
    final int wonGames;
    final int lostGames;
    final float winRatio;
    final float loseRatio;
    final GameStatistic maxLosses;
    final GameStatistic maxVictories;
    final GameStatistic longestWinningStreak;
    final GameStatistic longestLosingStreak;

    public SummaryStatistic(Collection<GameStatistic> statistics) {

        //modifiable variables to calculate values
        int playedGames = 0;
        int wonGames = 0;

        GameStatistic minStatistic = new GameStatistic("", "Nobody");

        GameStatistic maxLosses = minStatistic;
        GameStatistic maxVictories = minStatistic;
        GameStatistic longestWinningStreak = minStatistic;
        GameStatistic longestLosingStreak = minStatistic;

        //calculate variables
        for (GameStatistic statistic : statistics) {

            playedGames += statistic.allGames();
            wonGames += statistic.wonGames();

            if (maxLosses.lostGames() < statistic.lostGames()) {
                maxLosses = statistic;
            }
            if (maxVictories.wonGames() < statistic.wonGames()) {
                maxVictories = statistic;
            }
            if (longestWinningStreak.getTopWinningStreak() < statistic.getTopWinningStreak()) {
                longestWinningStreak = statistic;
            }
            if (longestLosingStreak.getTopLosingStreak() < statistic.getTopLosingStreak()) {
                longestLosingStreak = statistic;
            }
        }

        //assign variables
        this.playedGames = playedGames;
        this.wonGames = wonGames;
        this.lostGames = playedGames - wonGames;
        this.winRatio = ((float) wonGames) / playedGames;
        this.loseRatio = 1f - winRatio;
        this.maxLosses = maxLosses;
        this.maxVictories = maxVictories;
        this.longestWinningStreak = longestWinningStreak;
        this.longestLosingStreak = longestLosingStreak;
    }
}
