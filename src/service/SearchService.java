package service;

import model.Hero;
import model.Player;
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
}
