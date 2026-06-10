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
        List<Player> players = new ArrayList<>(dataManager.getAllPlayers());
        Collections.sort(players, new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                double score1 = (p1.getWinRate() * 100) + p1.getLevel();
                double score2 = (p2.getWinRate() * 100) + p2.getLevel();
                if (score1 > score2) return -1;
                if (score1 < score2) return 1;
                return p1.getName().compareTo(p2.getName());
            }
        });
        return players;
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
