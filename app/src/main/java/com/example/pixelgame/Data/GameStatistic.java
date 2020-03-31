package com.example.pixelgame.Data;

import android.bluetooth.BluetoothDevice;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

public class GameStatistic {
    @Attribute
    public String deviceID;
    @Element
    public String name;
    @Element
    private int played;
    @Element
    private int won;
    @Element
    private int topWinningStreak;
    @Element
    private int topLosingStreak;
    @Element
    private boolean curStreakIsWinningStreak;
    @Element
    private int curStreak;

    public int getTopWinningStreak() {
        return topWinningStreak;
    }

    public int getTopLosingStreak() {
        return topLosingStreak;
    }

    public int getCurStreak() {
        return curStreak;
    }

    public GameStatistic(BluetoothDevice device) {
        this(device.getAddress(), device.getName());
    }

    public GameStatistic(String deviceID, String name) {
        this(deviceID, name, 0, 0, 0, 0, 0, false);
    }

    public GameStatistic(String deviceID, String name, int played, int won, int topWinningStreak, int topLosingStreak, int curStreak, boolean curStreakIsWinningStreak) {
        if (deviceID == null || deviceID.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.deviceID = deviceID;
        this.name = name;
        this.played = played;
        this.won = won;
        this.topWinningStreak = topWinningStreak;
        this.topLosingStreak = topLosingStreak;
        this.curStreak = curStreak;
        this.curStreakIsWinningStreak = curStreakIsWinningStreak;
    }

    public int wonGames() {
        return won;
    }

    public int lostGames() {
        return played - won;
    }

    public int allGames() {
        return played;
    }

    public void addResult(boolean won) {
        played++;
        checkStreaks(won);
        if (won) {
            this.won++;
        }
    }

    private void checkStreaks(boolean won) {
        if (won != curStreakIsWinningStreak) {
            if (curStreakIsWinningStreak) {
                if (curStreak > topWinningStreak) {
                    topWinningStreak = curStreak;
                }
            } else {
                if (curStreak > topLosingStreak) {
                    topLosingStreak = curStreak;
                }
            }
            curStreak = 1;
            curStreakIsWinningStreak = !curStreakIsWinningStreak;
        } else {
            curStreak++;
        }
    }

    public float winRatio() {
        return ((float) won) / played;
    }

    public float loseRatio() {
        return ((float) lostGames()) / played;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlayed() {
        return played;
    }

    public void setPlayed(int played) {
        this.played = played;
    }

    public int getWon() {
        return won;
    }

    public void setWon(int won) {
        this.won = won;
    }

    public void setTopWinningStreak(int topWinningStreak) {
        this.topWinningStreak = topWinningStreak;
    }

    public void setTopLosingStreak(int topLosingStreak) {
        this.topLosingStreak = topLosingStreak;
    }

    public void setCurStreak(int curStreak) {
        this.curStreak = curStreak;
    }

    public boolean isCurStreakIsWinningStreak() {
        return curStreakIsWinningStreak;
    }

    public void setCurStreakIsWinningStreak(boolean curStreakIsWinningStreak) {
        this.curStreakIsWinningStreak = curStreakIsWinningStreak;
    }
}
