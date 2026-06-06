package service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Equipment;
import model.Hero;
import model.MatchRecord;
import model.Player;
import model.Team;

public class GameDataManager {
    private Map<String, Player> players = new HashMap<>();
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

    public Player removePlayer(String id) {
        return players.remove(id);
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

    public Hero removeHero(String name) {
        return heroes.remove(name);
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

    public Team removeTeam(String teamId) {
        return teams.remove(teamId);
    }

    public Collection<Team> getAllTeams() {
        return teams.values();
    }

    public void addMatchRecord(MatchRecord record) {
        matches.add(record);
    }

    public List<MatchRecord> getAllMatchRecords() {
        return matches;
    }
}
