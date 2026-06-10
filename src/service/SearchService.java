package service;

import java.util.ArrayList;
import java.util.List;

import model.Equipment;
import model.Hero;
import model.MatchRecord;
import model.Player;
import model.Searchable;
import model.Team;

public class SearchService {
    private GameDataManager dataManager;

    public SearchService(GameDataManager dataManager) {
        this.dataManager = dataManager;
    }

    public Player findPlayerByName(String name) {
        String lowerName = name.toLowerCase();
        for (Player p : dataManager.getAllPlayers()) {
            if (p.getName().toLowerCase().contains(lowerName)) {
                return p;
            }
        }
        return null;
    }

    public Player findPlayerById(String id) {
        String lowerId = id.toLowerCase();
        for (Player p : dataManager.getAllPlayers()) {
            if (p.getId().toLowerCase().equals(lowerId)) {
                return p;
            }
        }
        return null;
    }

    public Hero findHeroByName(String name) {
        String lowerName = name.toLowerCase();
        for (Hero h : dataManager.getAllHeroes()) {
            if (h.getName().toLowerCase().contains(lowerName)) {
                return h;
            }
        }
        return null;
    }

    public Team findTeamByName(String name) {
        String lowerName = name.toLowerCase();
        for (Team t : dataManager.getAllTeams()) {
            if (t.getName().toLowerCase().contains(lowerName)) {
                return t;
            }
        }
        return null;
    }

    public Team findTeamById(String id) {
        String lowerId = id.toLowerCase();
        for (Team t : dataManager.getAllTeams()) {
            if (t.getTeamId().toLowerCase().equals(lowerId)) {
                return t;
            }
        }
        return null;
    }

    public List<Searchable> search(String keyword) {
        List<Searchable> results = new ArrayList<>();
        String lowerKw = keyword.toLowerCase();
        for (Player p : dataManager.getAllPlayers()) {
            if (p.matches(lowerKw)) results.add(p);
        }
        for (Hero h : dataManager.getAllHeroes()) {
            if (h.matches(lowerKw)) results.add(h);
        }
        for (Equipment e : dataManager.getAllEquipment()) {
            if (e.matches(lowerKw)) results.add(e);
        }
        for (Team t : dataManager.getAllTeams()) {
            if (t.matches(lowerKw)) results.add(t);
        }
        for (MatchRecord m : dataManager.getAllMatchRecords()) {
            if (m.matches(lowerKw)) results.add(m);
        }
        return results;
    }
}
