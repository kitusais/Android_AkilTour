package com.kitusais.akiltourv2.model;

public class Player {

    private int id;
    private String pseudo;
    private Boolean passe = false;
    private Boolean logedIn = false;
    private int totTournee;
    private int totPasse;
    private long gameTimer;

    public Player() {
    }

    public Player(int id, String pseudo) {
        this.id = id;
        this.pseudo = pseudo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public Boolean getPasse() {
        return passe;
    }

    public void setPasse(Boolean passe) {
        this.passe = passe;
    }

    public Boolean getLogedIn() {
        return logedIn;
    }

    public void setLogedIn(Boolean logedIn) {
        this.logedIn = logedIn;
    }

    public int getTotTournee() {
        return totTournee;
    }

    public void setTotTournee(int totTournee) {
        this.totTournee = totTournee;
    }

    public int getTotPasse() {
        return totPasse;
    }

    public void setTotPasse(int totPasse) {
        this.totPasse = totPasse;
    }

    public long getGameTimer() {
        return gameTimer;
    }

    public void setGameTimer(long gameTimer) {
        this.gameTimer = gameTimer;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", pseudo='" + pseudo + '\'' +
                ", passe=" + passe +
                ", logedIn=" + logedIn +
                ", totTournee=" + totTournee +
                ", totPasse=" + totPasse +
                ", gameTimer=" + gameTimer +
                '}';
    }
}
