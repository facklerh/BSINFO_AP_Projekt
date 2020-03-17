package com.example.pixelgame.Views;

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

import com.example.pixelgame.Data.AppData;
import com.example.pixelgame.Data.GameStatistic;
import com.example.pixelgame.Data.PlayerData;
import com.example.pixelgame.Data.SummaryStatistic;
import com.example.pixelgame.R;

public class StatisticActivity extends BaseView {

    PlayerData data;
    SummaryStatistic summary;
    GameStatistic[] statistics;

    int index = -1;
    private float swipeStartX = 0f;
    private float swipeEndX = 0f;

    TextView title;
    TextView playedGames;
    TextView wonGames;
    TextView winStreak;
    TextView loseStreak;
    TextView curStreak;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        findViews();
        initData();
        updateView();

    }

    private void findViews() {
        title = findViewById(R.id.statisticTitle);
        playedGames = findViewById(R.id.statisticPlayedGames);
        wonGames = findViewById(R.id.statisticWonGames);
        winStreak = findViewById(R.id.statisticTopWinStreak);
        loseStreak = findViewById(R.id.statisticTopLoseStreak);
        curStreak = findViewById(R.id.textViewCurStreak);
    }

    private void initData() {
        data = AppData.getPlayerData();
        summary = data.summary();
        statistics = new GameStatistic[data.getStatisticMap().size()];
        data.getStatisticMap().values().toArray(statistics);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                swipeStartX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                swipeEndX = event.getX();
                if (rightSwipe()) {
                    index = Math.max(-1, --index);
                }
                if (leftSwipe()) {
                    index = Math.min(statistics.length - 1, ++index);
                }
                updateView();
        }
        return true;
    }

    private boolean leftSwipe() {
        return swipeEndX < swipeStartX;
    }

    private boolean rightSwipe() {
        return swipeEndX > swipeStartX;
    }

    private void updateView() {
        if (index < 0) {
            viewSummary();
        } else {
            view(statistics[index]);
        }
    }

    private void viewSummary() {
        title.setText(getString(R.string.summary, data.getPlayerName()));
        playedGames.setText(getString(R.string.playedGames, summary.playedGames));
        wonGames.setText(getString(R.string.wonGames, summary.wonGames));
        winStreak.setText(getString(R.string.winStreak, summary.longestWinningStreak.getTopWinningStreak()));
        loseStreak.setText(getString(R.string.loseStreak, summary.longestLosingStreak.getTopLosingStreak()));
        curStreak.setText(getString(R.string.winRatio, summary.winRatio));
    }

    private void view(GameStatistic statistic) {
        title.setText(getString(R.string.summary, statistic.name));
        playedGames.setText(getString(R.string.playedGames, statistic.getPlayed()));
        wonGames.setText(getString(R.string.wonGames, statistic.getWon()));
        winStreak.setText(getString(R.string.winStreak, statistic.getTopWinningStreak()));
        loseStreak.setText(getString(R.string.loseStreak, statistic.getTopLosingStreak()));
        curStreak.setText(getString(R.string.curStreak, statistic.getCurStreak(), statistic.isCurStreakIsWinningStreak() ? "W" : "L"));
    }
}
