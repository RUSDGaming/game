package com.rusd.game.network;

/**
 * Created by shane on 7/11/15.
 */
public class Score {

    private int kills = 0;
    private int deaths = 0;
    private String playerName = "noob that didnt type in his name";


    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public String toString() {
        //return "why wont this work";
        String string = "Name: " + playerName + " Deaths: " + deaths + " Kills: " + kills;
        return string;
    }
}
