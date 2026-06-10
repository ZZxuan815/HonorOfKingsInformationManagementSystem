package service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import model.Equipment;
import model.Player;

public class RankingService {

    private GameDataManager dataManager;

    public RankingService(GameDataManager dataManager) {
        this.dataManager = dataManager;
    }

    public List<Player> getPlayerRanking() {
        return getPlayerRanking(1);
    }

    public List<Player> getPlayerRanking(int sortMode) {
        List<Player> players = new ArrayList<>(dataManager.getAllPlayers());
        Collections.sort(players, new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                int cmp = 0;
                if (sortMode == 1) {
                    double score1 = (p1.getWinRate() * 100) + p1.getLevel();
                    double score2 = (p2.getWinRate() * 100) + p2.getLevel();
                    if (score1 > score2) cmp = -1;
                    else if (score1 < score2) cmp = 1;
                } else if (sortMode == 2) {
                    if (p1.getWinRate() > p2.getWinRate()) cmp = -1;
                    else if (p1.getWinRate() < p2.getWinRate()) cmp = 1;
                } else if (sortMode == 3) {
                    if (p1.getLevel() > p2.getLevel()) cmp = -1;
                    else if (p1.getLevel() < p2.getLevel()) cmp = 1;
                } else if (sortMode == 4) {
                    int matches1 = countMatchesForPlayer(p1.getId());
                    int matches2 = countMatchesForPlayer(p2.getId());
                    if (matches1 > matches2) cmp = -1;
                    else if (matches1 < matches2) cmp = 1;
                }
                if (cmp != 0) return cmp;
                return p1.getName().compareTo(p2.getName());
            }
        });
        return players;
    }

    public int countMatchesForPlayer(String playerId) {
        int count = 0;
        for (var m : dataManager.getAllMatchRecords()) {
            if (m.getHeroPicks().containsKey(playerId)) {
                count++;
            }
        }
        return count;
    }

    public List<Equipment> getEquipmentRanking() {
        List<Equipment> equipment = new ArrayList<>(dataManager.getAllEquipment());
        Collections.sort(equipment, new Comparator<Equipment>() {
            @Override
            public int compare(Equipment e1, Equipment e2) {
                double score1 = e1.getUsageCount() * e1.getWinRateContribution();
                double score2 = e2.getUsageCount() * e2.getWinRateContribution();
                if (score1 > score2) return -1;
                if (score1 < score2) return 1;
                return e1.getName().compareTo(e2.getName());
            }
        });
        return equipment;
    }
}
