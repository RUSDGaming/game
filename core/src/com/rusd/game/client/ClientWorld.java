package com.rusd.game.client;

import com.rusd.game.network.ScoreBoard;

import java.util.ArrayList;

/**
 * Created by shane on 7/4/15.
 */
public class ClientWorld {
    ArrayList<ClientEntity> entities = new ArrayList<>();
    ScoreBoard scoreBoard = new ScoreBoard();

    public ScoreBoard getScoreBoard() {
        return scoreBoard;
    }

    public void setScoreBoard(ScoreBoard scoreBoard) {
        this.scoreBoard = scoreBoard;
    }

    public ArrayList<ClientEntity> getEntities() {
        return entities;
    }

    public void setEntities(ArrayList<ClientEntity> entities) {
        this.entities = entities;
    }
}
