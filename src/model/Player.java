package model;

import java.util.ArrayList;
import java.util.List;

public class Player extends Person implements Searchable {
    private static final long serialVersionUID = 1L;

    private int level;
    private double winRate;
    private String teamId;
    private List<String> ownedHeroes;
    private java.util.Map<String, java.util.List<String>> equippedItems;

    public Player() {
        super();
        this.ownedHeroes = new ArrayList<>();
        this.equippedItems = new java.util.HashMap<>();
    }

    public Player(String id, String name, String password, int level, double winRate, String teamId) {
        super(id, name, password);
        this.level = level;
        this.winRate = winRate;
        this.teamId = teamId;
        this.ownedHeroes = new ArrayList<>();
        this.equippedItems = new java.util.HashMap<>();
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getWinRate() {
        return winRate;
    }

    public void setWinRate(double winRate) {
        this.winRate = winRate;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public List<String> getOwnedHeroes() {
        return ownedHeroes;
    }

    public void setOwnedHeroes(List<String> ownedHeroes) {
        this.ownedHeroes = ownedHeroes;
    }

    public java.util.Map<String, java.util.List<String>> getEquippedItems() {
        return equippedItems;
    }

    public void setEquippedItems(java.util.Map<String, java.util.List<String>> equippedItems) {
        this.equippedItems = equippedItems;
    }

    @Override
    public boolean matches(String keyword) {
        String kw = keyword.toLowerCase();
        if (getId().toLowerCase().contains(kw)) return true;
        if (getName().toLowerCase().contains(kw)) return true;
        for (String hero : ownedHeroes) {
            if (hero.toLowerCase().contains(kw)) return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Player{id='" + getId() + "', name='" + getName() + "', level=" + level + ", winRate=" + winRate + "}";
    }
}
