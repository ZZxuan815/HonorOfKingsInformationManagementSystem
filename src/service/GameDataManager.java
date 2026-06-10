package service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Admin;
import model.Equipment;
import model.Hero;
import model.MatchRecord;
import model.Player;
import model.Team;

public class GameDataManager {
    private Map<String, Player> players = new HashMap<>();
    private Map<String, Admin> admins = new HashMap<>();
    private Map<String, Hero> heroes = new HashMap<>();
    private Map<String, Equipment> equipment = new HashMap<>();
    private Map<String, Team> teams = new HashMap<>();
    private List<MatchRecord> matches = new ArrayList<>();

    public void addPlayer(Player player) {
        players.put(player.getId(), player);
    }

    public Player getPlayer(String id) {
        return players.get(id);
    }

    // Removes player and removes their ID from their team's member list
    public Player removePlayer(String id) {
        Player player = players.remove(id);
        if (player != null) {
            String teamId = player.getTeamId();
            if (teamId != null && !teamId.isEmpty()) {
                Team team = teams.get(teamId);
                if (team != null) {
                    team.getMemberIds().remove(id);
                }
            }
        }
        return player;
    }

    public Collection<Player> getAllPlayers() {
        return players.values();
    }

    public void addHero(Hero hero) {
        heroes.put(hero.getName(), hero);
    }

    public Hero getHero(String name) {
        return heroes.get(name);
    }

    // Removes hero and cascades deletion to all players' ownedHeroes and equippedItems
    public Hero removeHero(String name) {
        Hero hero = heroes.remove(name);
        if (hero != null) {
            for (Player p : players.values()) {
                p.getOwnedHeroes().remove(name);
                p.getEquippedItems().remove(name);
            }
        }
        return hero;
    }

    public Collection<Hero> getAllHeroes() {
        return heroes.values();
    }

    public void addEquipment(Equipment item) {
        equipment.put(item.getName(), item);
    }

    public Equipment getEquipment(String name) {
        return equipment.get(name);
    }

    public Equipment removeEquipment(String name) {
        return equipment.remove(name);
    }

    public Collection<Equipment> getAllEquipment() {
        return equipment.values();
    }

    public void addTeam(Team team) {
        teams.put(team.getTeamId(), team);
    }

    public Team getTeam(String teamId) {
        return teams.get(teamId);
    }

    // Removes team and resets teamId for all former members to empty string
    public Team removeTeam(String teamId) {
        Team team = teams.remove(teamId);
        if (team != null) {
            for (String memberId : team.getMemberIds()) {
                Player p = players.get(memberId);
                if (p != null) {
                    p.setTeamId("");
                }
            }
        }
        return team;
    }

    public Collection<Team> getAllTeams() {
        return teams.values();
    }

    public void addAdmin(Admin admin) {
        admins.put(admin.getId(), admin);
    }

    public Admin getAdmin(String id) {
        return admins.get(id);
    }

    public Collection<Admin> getAllAdmins() {
        return admins.values();
    }

    public void addMatchRecord(MatchRecord record) {
        matches.add(record);
    }

    public MatchRecord getMatchRecord(String matchId) {
        for (MatchRecord m : matches) {
            if (m.getMatchId().equals(matchId)) {
                return m;
            }
        }
        return null;
    }

    public MatchRecord removeMatchRecord(String matchId) {
        for (int i = 0; i < matches.size(); i++) {
            if (matches.get(i).getMatchId().equals(matchId)) {
                return matches.remove(i);
            }
        }
        return null;
    }

    public List<MatchRecord> getAllMatchRecords() {
        return matches;
    }
}
