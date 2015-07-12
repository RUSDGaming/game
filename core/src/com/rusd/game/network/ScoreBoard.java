package com.rusd.game.network;

import com.rusd.game.entity.ScoreComponent;

import java.util.ArrayList;

/**
 * Created by shane on 7/11/15.
 */
public class ScoreBoard {
    public static final String tag = ScoreBoard.class.getSimpleName();

    private ArrayList<Score> scores = new ArrayList<>();


    public ArrayList<Score> getScores() {
        return scores;
    }

    public void setScores(ArrayList<Score> scores) {
        this.scores = scores;
    }


    public void setScoresComponents(ArrayList<ScoreComponent> scoresComponents) {
        scores = new ArrayList<>();
        for (ScoreComponent sc : scoresComponents) {
            Score score = new Score();
            score.setDeaths(sc.getDeaths());
            score.setKills(sc.getKills());
            if (!sc.getEntity().getName().equals(""))
                score.setPlayerName(sc.getEntity().getName());

            scores.add(score);
        }

    }
}
