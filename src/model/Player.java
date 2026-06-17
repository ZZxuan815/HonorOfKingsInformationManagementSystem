package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Player extends Person implements Searchable {
    private static final long serialVersionUID = 1L;

    private int level;
    private double winRate;
    private String teamId;
    private List<String> ownedHeroes;
    private Map<String, List<String>> equippedItems;

    public Player() {
        super();
        this.ownedHeroes = new ArrayList<>();
        this.equippedItems = new HashMap<>();
    }

    public Player(String id, String name, String password, int level, double winRate, String teamId) {
        super(id, name, password);
        this.level = level;
        this.winRate = winRate;
        this.teamId = teamId;
        this.ownedHeroes = new ArrayList<>();
        this.equippedItems = new HashMap<>();
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
        this.ownedHeroes = (ownedHeroes != null) ? new ArrayList<>(ownedHeroes) : new ArrayList<>();
    }

    public Map<String, List<String>> getEquippedItems() {
        return equippedItems;
    }

    public void setEquippedItems(Map<String, List<String>> equippedItems) {
        this.equippedItems = equippedItems;
    }

    @Override
    public boolean matches(String keyword) {
        String kw = keyword.toLowerCase();
        if (getId() != null && getId().toLowerCase().contains(kw)) return true;
        if (getName() != null && getName().toLowerCase().contains(kw)) return true;
        if (ownedHeroes != null) {
            for (String hero : ownedHeroes) {
                if (hero != null && hero.toLowerCase().contains(kw)) return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Player{id='" + getId() + "', name='" + getName() + "', level=" + level + ", winRate=" + winRate + "}";
    }
}
