package menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Equipment;
import model.Hero;
import model.HeroType;
import model.MatchRecord;
import model.MatchResult;
import model.Player;
import model.Searchable;
import model.Team;
import service.AuthenticationService;
import service.ExtraFeaturesService;
import service.FileStorageService;
import service.GameDataManager;
import service.RankingService;
import service.SearchService;
import util.InputHelper;

public class AdminMenu {

    private GameDataManager gdm;
    private RankingService rankingService;
    private SearchService searchService;
    private AuthenticationService authService;
    private FileStorageService fileStorage;
    private ExtraFeaturesService extraService;

    public AdminMenu(GameDataManager gdm, RankingService rankingService,
                     SearchService searchService, AuthenticationService authService,
                     FileStorageService fileStorage, ExtraFeaturesService extraService) {
        this.gdm = gdm;
        this.rankingService = rankingService;
        this.searchService = searchService;
        this.authService = authService;
        this.fileStorage = fileStorage;
        this.extraService = extraService;
    }

    public void run() {
        while (true) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. Hero Management");
            System.out.println("2. Equipment Management");
            System.out.println("3. Player Management");
            System.out.println("4. Team Management");
            System.out.println("5. Match Management");
            System.out.println("6. View all heroes");
            System.out.println("7. View all equipment");
            System.out.println("8. View match records");
            System.out.println("9. View match history");
            System.out.println("10. View player leaderboard");
            System.out.println("11. View equipment statistics");
            System.out.println("12. Global search");
            System.out.println("13. [Bonus] HOK Arena & Recommendation");
            System.out.println("14. Save & Logout");

            int choice = InputHelper.readIntRange("Enter your choice: ", 1, 14);

            // Admin menu: full CRUD operations for all entities
            switch (choice) {
                case 1:
                    runHeroManagement();
                    break;
                case 2:
                    runEquipmentManagement();
                    break;
                case 3:
                    runPlayerManagement();
                    break;
                case 4:
                    runTeamManagement();
                    break;
                case 5:
                    runMatchManagement();
                    break;
                case 6:
                    viewAllHeroes();
                    break;
                case 7:
                    viewAllEquipment();
                    break;
                case 8:
                    viewAllMatchRecords();
                    break;
                case 9:
                    viewMatchHistory();
                    break;
                case 10:
                    viewPlayerLeaderboard();
                    break;
                case 11:
                    viewEquipmentStatistics();
                    break;
                case 12:
                    globalSearch();
                    break;
                case 13:
                    runHokArena();
                    break;
                case 14:
                    fileStorage.saveData(gdm);
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    // ===================== HERO MANAGEMENT =====================

    private void runHeroManagement() {
        while (true) {
            System.out.println("\n--- Hero Management ---");
            System.out.println("1. Add new hero");
            System.out.println("2. Update hero");
            System.out.println("3. Delete hero");
            System.out.println("4. View hero details");
            System.out.println("5. Back to admin menu");

            int choice = InputHelper.readIntRange("Enter your choice: ", 1, 5);

            switch (choice) {
                case 1:
                    addHero();
                    break;
                case 2:
                    updateHero();
                    break;
                case 3:
                    deleteHero();
                    break;
                case 4:
                    viewHeroDetails();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void addHero() {
        String name = InputHelper.readString("Enter hero name: ");
        if (gdm.getHero(name) != null) {
            System.out.println("Hero already exists.");
            return;
        }
        String typeStr = InputHelper.readString("Enter hero type (WARRIOR, MAGE, ASSASSIN, MARKSMAN, TANK, SUPPORT, JUNGLER): ");
        HeroType type;
        try {
            type = HeroType.valueOf(typeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid hero type.");
            return;
        }
        Hero hero = new Hero(name, type);
        gdm.addHero(hero);
        fileStorage.saveData(gdm);
        System.out.println("Hero added successfully.");
    }

    private void updateHero() {
        String name = InputHelper.readString("Enter hero name to update: ");
        Hero hero = gdm.getHero(name);
        if (hero == null) {
            System.out.println("Hero not found.");
            return;
        }
        String newTypeStr = InputHelper.readString("Enter new type (or press Enter to skip): ");
        if (!newTypeStr.isEmpty()) {
            try {
                hero.setType(HeroType.valueOf(newTypeStr.toUpperCase()));
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid type. Keeping original.");
            }
        }
        fileStorage.saveData(gdm);
        System.out.println("Hero updated successfully.");
    }

    private void deleteHero() {
        String name = InputHelper.readString("Enter hero name to delete: ");
        Hero removed = gdm.removeHero(name);
        if (removed != null) {
            fileStorage.saveData(gdm);
            System.out.println("Hero deleted successfully.");
        } else {
            System.out.println("Hero not found.");
        }
    }

    private void viewHeroDetails() {
        String name = InputHelper.readString("Enter hero name: ");
        Hero hero = gdm.getHero(name);
        if (hero != null) {
            System.out.println("\nName: " + hero.getName());
            System.out.println("Type: " + hero.getType());
            System.out.println("Base Stats: " + hero.getBaseStats());
            System.out.println("Compatible Equipment: " + hero.getCompatibleEquipment());
            System.out.println("Players who own this hero:");
            boolean found = false;
            for (Player p : gdm.getAllPlayers()) {
                if (p.getOwnedHeroes().contains(hero.getName())) {
                    System.out.println("  - " + p.getName() + " (ID: " + p.getId() + ")");
                    found = true;
                }
            }
            if (!found) {
                System.out.println("  (none)");
            }
        } else {
            System.out.println("Hero not found.");
        }
    }

    // ===================== EQUIPMENT MANAGEMENT =====================

    private void runEquipmentManagement() {
        while (true) {
            System.out.println("\n--- Equipment Management ---");
            System.out.println("1. Add new equipment");
            System.out.println("2. Update equipment");
            System.out.println("3. Delete equipment");
            System.out.println("4. View equipment details");
            System.out.println("5. Back to admin menu");

            int choice = InputHelper.readIntRange("Enter your choice: ", 1, 5);

            switch (choice) {
                case 1:
                    addEquipment();
                    break;
                case 2:
                    updateEquipment();
                    break;
                case 3:
                    deleteEquipment();
                    break;
                case 4:
                    viewEquipmentDetails();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void viewAllHeroes() {
        System.out.println("\n=== All Heroes ===");
        for (Hero hero : gdm.getAllHeroes()) {
            System.out.println("Name: " + hero.getName() + " | Type: " + hero.getType());
        }
    }

    private void viewAllEquipment() {
        System.out.println("\n=== All Equipment ===");
        for (Equipment eq : gdm.getAllEquipment()) {
            System.out.println("Name: " + eq.getName() + " | Type: " + eq.getType() + " | Bonus: " + eq.getStatBonus());
        }
    }

    private void addEquipment() {
        String name = InputHelper.readString("Enter equipment name: ");
        if (gdm.getEquipment(name) != null) {
            System.out.println("Equipment already exists.");
            return;
        }
        String type = InputHelper.readString("Enter equipment type: ");
        String statBonus = InputHelper.readString("Enter stat bonus: ");
        Equipment eq = new Equipment(name, type, statBonus);
        gdm.addEquipment(eq);
        fileStorage.saveData(gdm);
        System.out.println("Equipment added successfully.");
    }

    private void updateEquipment() {
        String name = InputHelper.readString("Enter equipment name to update: ");
        Equipment eq = gdm.getEquipment(name);
        if (eq == null) {
            System.out.println("Equipment not found.");
            return;
        }
        String newType = InputHelper.readString("Enter new type (or press Enter to skip): ");
        if (!newType.isEmpty()) {
            eq.setType(newType);
        }
        String newBonus = InputHelper.readString("Enter new stat bonus (or press Enter to skip): ");
        if (!newBonus.isEmpty()) {
            eq.setStatBonus(newBonus);
        }
        fileStorage.saveData(gdm);
        System.out.println("Equipment updated successfully.");
    }

    private void deleteEquipment() {
        String name = InputHelper.readString("Enter equipment name to delete: ");
        Equipment removed = gdm.removeEquipment(name);
        if (removed != null) {
            fileStorage.saveData(gdm);
            System.out.println("Equipment deleted successfully.");
        } else {
            System.out.println("Equipment not found.");
        }
    }

    private void viewEquipmentDetails() {
        String name = InputHelper.readString("Enter equipment name: ");
        Equipment eq = gdm.getEquipment(name);
        if (eq != null) {
            System.out.println("\nName: " + eq.getName());
            System.out.println("Type: " + eq.getType());
            System.out.println("Stat Bonus: " + eq.getStatBonus());
            System.out.println("Usage Count: " + eq.getUsageCount());
            System.out.println("Win Rate Contribution: " + eq.getWinRateContribution());
        } else {
            System.out.println("Equipment not found.");
        }
    }

    // ===================== PLAYER MANAGEMENT =====================

    private void runPlayerManagement() {
        while (true) {
            System.out.println("\n--- Player Management ---");
            System.out.println("1. Add new player");
            System.out.println("2. Update player");
            System.out.println("3. Delete player");
            System.out.println("4. View player details");
            System.out.println("5. Back to admin menu");

            int choice = InputHelper.readIntRange("Enter your choice: ", 1, 5);

            switch (choice) {
                case 1:
                    addPlayer();
                    break;
                case 2:
                    updatePlayer();
                    break;
                case 3:
                    deletePlayer();
                    break;
                case 4:
                    viewPlayerInfo();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void addPlayer() {
        String id = InputHelper.readString("Enter player ID: ");
        if (gdm.getPlayer(id) != null) {
            System.out.println("Player ID already exists.");
            return;
        }
        String name = InputHelper.readString("Enter player name: ");
        String password = InputHelper.readString("Enter password: ");
        int level = InputHelper.readInt("Enter level: ");
        double winRate = InputHelper.readDoubleRange("Enter win rate (0-100): ", 0, 100) / 100.0;
        String teamId = InputHelper.readString("Enter team ID: ");
        Player player = new Player(id, name, password, level, winRate, teamId);
        gdm.addPlayer(player);
        fileStorage.saveData(gdm);
        System.out.println("Player added successfully.");
    }

    private void updatePlayer() {
        String id = InputHelper.readString("Enter player ID to update: ");
        Player player = gdm.getPlayer(id);
        if (player == null) {
            System.out.println("Player not found.");
            return;
        }
        String newName = InputHelper.readString("Enter new name (or press Enter to skip): ");
        if (!newName.isEmpty()) {
            player.setName(newName);
        }
        String newLevel = InputHelper.readString("Enter new level (or press Enter to skip): ");
        if (!newLevel.isEmpty()) {
            try {
                player.setLevel(Integer.parseInt(newLevel));
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Keeping original.");
            }
        }
        String newWR = InputHelper.readString("Enter new win rate 0-100 (or press Enter to skip): ");
        if (!newWR.isEmpty()) {
            try {
                player.setWinRate(Double.parseDouble(newWR) / 100.0);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Keeping original.");
            }
        }
        fileStorage.saveData(gdm);
        System.out.println("Player updated successfully.");
    }

    private void deletePlayer() {
        String id = InputHelper.readString("Enter player ID to delete: ");
        Player removed = gdm.removePlayer(id);
        if (removed != null) {
            fileStorage.saveData(gdm);
            System.out.println("Player deleted successfully.");
        } else {
            System.out.println("Player not found.");
        }
    }

    private void viewPlayerInfo() {
        String id = InputHelper.readString("Enter player ID: ");
        Player player = gdm.getPlayer(id);
        if (player != null) {
            System.out.println("\nID: " + player.getId());
            System.out.println("Name: " + player.getName());
            System.out.println("Level: " + player.getLevel());
            System.out.println("Win Rate: " + (player.getWinRate() * 100) + "%");
            System.out.println("Team ID: " + player.getTeamId());
            System.out.println("Owned Heroes: " + player.getOwnedHeroes());
            System.out.println("Equipped Items:");
            for (String hero : player.getOwnedHeroes()) {
                List<String> items = player.getEquippedItems().get(hero);
                if (items != null && !items.isEmpty()) {
                    System.out.println("  " + hero + " -> " + items);
                } else {
                    System.out.println("  " + hero + " -> (none)");
                }
            }
        } else {
            System.out.println("Player not found.");
        }
    }

    // ===================== TEAM MANAGEMENT =====================

    private void runTeamManagement() {
        while (true) {
            System.out.println("\n--- Team Management ---");
            System.out.println("1. Add new team");
            System.out.println("2. Update team");
            System.out.println("3. Delete team");
            System.out.println("4. View team details");
            System.out.println("5. Back to admin menu");

            int choice = InputHelper.readIntRange("Enter your choice: ", 1, 5);

            switch (choice) {
                case 1:
                    addTeam();
                    break;
                case 2:
                    updateTeam();
                    break;
                case 3:
                    deleteTeam();
                    break;
                case 4:
                    viewTeamDetails();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void addTeam() {
        String teamId = InputHelper.readString("Enter team ID: ");
        if (gdm.getTeam(teamId) != null) {
            System.out.println("Team ID already exists.");
            return;
        }
        String name = InputHelper.readString("Enter team name: ");
        Team team = new Team(teamId, name);
        gdm.addTeam(team);
        fileStorage.saveData(gdm);
        System.out.println("Team added successfully.");
    }

    private void updateTeam() {
        String teamId = InputHelper.readString("Enter team ID to update: ");
        Team team = gdm.getTeam(teamId);
        if (team == null) {
            System.out.println("Team not found.");
            return;
        }
        String newName = InputHelper.readString("Enter new name (or press Enter to skip): ");
        if (!newName.isEmpty()) {
            team.setName(newName);
        }
        String newMatches = InputHelper.readString("Enter new total matches (or press Enter to skip): ");
        if (!newMatches.isEmpty()) {
            try {
                team.setTotalMatches(Integer.parseInt(newMatches));
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Keeping original.");
            }
        }
        String newWins = InputHelper.readString("Enter new wins (or press Enter to skip): ");
        if (!newWins.isEmpty()) {
            try {
                team.setWins(Integer.parseInt(newWins));
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Keeping original.");
            }
        }
        fileStorage.saveData(gdm);
        System.out.println("Team updated successfully.");
    }

    private void deleteTeam() {
        String teamId = InputHelper.readString("Enter team ID to delete: ");
        Team removed = gdm.removeTeam(teamId);
        if (removed != null) {
            fileStorage.saveData(gdm);
            System.out.println("Team deleted successfully.");
        } else {
            System.out.println("Team not found.");
        }
    }

    private void viewTeamDetails() {
        String teamId = InputHelper.readString("Enter team ID: ");
        Team team = gdm.getTeam(teamId);
        if (team != null) {
            System.out.println("\nTeam ID: " + team.getTeamId());
            System.out.println("Name: " + team.getName());
            System.out.println("Members: " + team.getMemberIds());
            System.out.println("Total Matches: " + team.getTotalMatches());
            System.out.println("Wins: " + team.getWins());

            int totalLevel = 0;
            int validMembers = 0;
            Player topPlayer = null;
            double topScore = -1;

            for (String memberId : team.getMemberIds()) {
                Player p = gdm.getPlayer(memberId);
                if (p != null) {
                    totalLevel += p.getLevel();
                    validMembers++;
                    double score = (p.getWinRate() * 100) + p.getLevel();
                    if (score > topScore || (score == topScore && topPlayer != null && p.getName().compareTo(topPlayer.getName()) < 0)) {
                        topScore = score;
                        topPlayer = p;
                    }
                }
            }

            if (validMembers > 0) {
                System.out.printf("Average Level: %.2f%n", (double) totalLevel / validMembers);
            } else {
                System.out.println("Average Level: N/A");
            }

            if (team.getTotalMatches() > 0) {
                System.out.printf("Win Rate: %.2f%%%n", (double) team.getWins() / team.getTotalMatches() * 100);
            } else {
                System.out.println("Win Rate: N/A");
            }

            if (topPlayer != null) {
                System.out.println("Top Player: " + topPlayer.getName() + " (Score: " + (int) topScore + ")");
            } else {
                System.out.println("Top Player: N/A");
            }
        } else {
            System.out.println("Team not found.");
        }
    }

    // ===================== MATCH MANAGEMENT =====================

    private void runMatchManagement() {
        while (true) {
            System.out.println("\n--- Match Management ---");
            System.out.println("1. Add new match record");
            System.out.println("2. Update match record");
            System.out.println("3. Delete match record");
            System.out.println("4. View match details");
            System.out.println("5. Back to admin menu");

            int choice = InputHelper.readIntRange("Enter your choice: ", 1, 5);

            switch (choice) {
                case 1:
                    addMatchRecord();
                    break;
                case 2:
                    updateMatchRecord();
                    break;
                case 3:
                    deleteMatchRecord();
                    break;
                case 4:
                    viewMatchDetails();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void addMatchRecord() {
        String matchId = InputHelper.readString("Enter match ID: ");
        if (gdm.getMatchRecord(matchId) != null) {
            System.out.println("Match ID already exists.");
            return;
        }
        String date = InputHelper.readString("Enter date (e.g. 2026-06-10): ");
        String teamA = InputHelper.readString("Enter team A ID: ");
        String teamB = InputHelper.readString("Enter team B ID: ");
        String winner = InputHelper.readString("Enter winner team ID: ");
        MatchRecord match = new MatchRecord(matchId, date, teamA, teamB, winner);
        match.setResult(determineMatchResult(teamA, teamB, winner));
        gdm.addMatchRecord(match);
        fileStorage.saveData(gdm);
        System.out.println("Match record added successfully.");
    }

    // Determines MatchResult (WIN/LOSS/DRAW) based on winner vs teamA and teamB
    private MatchResult determineMatchResult(String teamA, String teamB, String winner) {
        if (winner.equals(teamA)) return MatchResult.WIN;
        if (winner.equals(teamB)) return MatchResult.LOSS;
        return MatchResult.DRAW;
    }

    private void updateMatchRecord() {
        String matchId = InputHelper.readString("Enter match ID to update: ");
        MatchRecord match = gdm.getMatchRecord(matchId);
        if (match == null) {
            System.out.println("Match not found.");
            return;
        }
        String newDate = InputHelper.readString("Enter new date (or press Enter to skip): ");
        if (!newDate.isEmpty()) {
            match.setDate(newDate);
        }
        String newWinner = InputHelper.readString("Enter new winner team ID (or press Enter to skip): ");
        if (!newWinner.isEmpty()) {
            match.setWinner(newWinner);
            match.setResult(determineMatchResult(match.getTeamA(), match.getTeamB(), newWinner));
        }
        fileStorage.saveData(gdm);
        System.out.println("Match record updated successfully.");
    }

    private void deleteMatchRecord() {
        String matchId = InputHelper.readString("Enter match ID to delete: ");
        MatchRecord removed = gdm.removeMatchRecord(matchId);
        if (removed != null) {
            fileStorage.saveData(gdm);
            System.out.println("Match record deleted successfully.");
        } else {
            System.out.println("Match not found.");
        }
    }

    // Shows full details and MatchResult for a single match by ID
    private void viewMatchDetails() {
        String matchId = InputHelper.readString("Enter match ID: ");
        MatchRecord match = gdm.getMatchRecord(matchId);
        if (match != null) {
            System.out.println("\nMatch ID: " + match.getMatchId());
            System.out.println("Date: " + match.getDate());
            System.out.println("Team A: " + match.getTeamA());
            System.out.println("Team B: " + match.getTeamB());
            System.out.println("Winner: " + match.getWinner());
            String resultStr = (match.getResult() != null) ? match.getResult().name() : "Unknown";
            System.out.println("Result: " + resultStr);
            System.out.println("Hero Picks: " + match.getHeroPicks());
        } else {
            System.out.println("Match not found.");
        }
    }

    // ===================== SHARED VIEW METHODS =====================

    private void viewAllMatchRecords() {
        System.out.println("\n=== Match Records ===");
        if (gdm.getAllMatchRecords().isEmpty()) {
            System.out.println("No match records available.");
            return;
        }
        for (var match : gdm.getAllMatchRecords()) {
            System.out.println(match);
        }
    }

    // Shows last N matches with aggregate win/loss and hero pick rate stats
    private void viewMatchHistory() {
        System.out.println("\n--- Match History ---");
        System.out.println("Search by [1] Player ID or [2] Team ID?");
        int searchType = InputHelper.readIntRange("Enter choice: ", 1, 2);
        String id = InputHelper.readString("Enter ID: ");
        int n = InputHelper.readInt("How many recent matches to show? ");

        List<MatchRecord> matches = gdm.getAllMatchRecords();
        List<MatchRecord> results = new ArrayList<>();

        for (int i = matches.size() - 1; i >= 0; i--) {
            MatchRecord m = matches.get(i);
            boolean include = false;
            if (searchType == 1) {
                if (m.getHeroPicks().containsKey(id)) {
                    include = true;
                }
            } else {
                if (m.getTeamA().equals(id) || m.getTeamB().equals(id)) {
                    include = true;
                }
            }
            if (include) {
                results.add(m);
                if (results.size() >= n) break;
            }
        }

        if (results.isEmpty()) {
            System.out.println("No matches found.");
            return;
        }

        int wins = 0;
        int losses = 0;
        Map<String, Integer> heroPickCount = new HashMap<>();
        int totalPicks = 0;

        System.out.println("\n=== Match History ===");
        for (MatchRecord m : results) {
            String opponent;
            String matchResult;
            if (searchType == 1) {
                Player p = gdm.getPlayer(id);
                String playerTeam = (p != null) ? p.getTeamId() : "Unknown";
                opponent = m.getTeamA().equals(playerTeam) ? m.getTeamB() : m.getTeamA();
                matchResult = m.getWinner().equals(playerTeam) ? "WIN" : "LOSS";
            } else {
                opponent = m.getTeamA().equals(id) ? m.getTeamB() : m.getTeamA();
                matchResult = m.getWinner().equals(id) ? "WIN" : "LOSS";
            }
            if (matchResult.equals("WIN")) wins++;
            else losses++;

            for (String heroName : m.getHeroPicks().values()) {
                heroPickCount.put(heroName, heroPickCount.getOrDefault(heroName, 0) + 1);
                totalPicks++;
            }

            System.out.println("Match ID: " + m.getMatchId());
            System.out.println("Date: " + m.getDate());
            System.out.println("Opponent: " + opponent);
            System.out.println("Result: " + matchResult);
            System.out.println("Hero Picks: " + m.getHeroPicks());
            System.out.println();
        }

        System.out.println("--- Aggregate Statistics ---");
        System.out.println("Total: " + wins + " wins, " + losses + " losses");
        if (totalPicks > 0) {
            System.out.println("Hero Pick Rate:");
            List<String> sortedHeroes = new ArrayList<>(heroPickCount.keySet());
            Collections.sort(sortedHeroes);
            for (String hero : sortedHeroes) {
                int count = heroPickCount.get(hero);
                double rate = (double) count / totalPicks * 100.0;
                System.out.printf("  %s: %.1f%% (%d/%d)%n", hero, rate, count, totalPicks);
            }
        }
    }

    // Leaderboard with 4 sorting options: custom score, win rate, level, match count
    private void viewPlayerLeaderboard() {
        System.out.println("\n=== Player Leaderboard ===");
        System.out.println("Sort by: [1] Custom Score [2] Win Rate [3] Level [4] Number of Matches");
        int sortMode = InputHelper.readIntRange("Enter your choice: ", 1, 4);

        String header;
        if (sortMode == 1) header = "Custom Score";
        else if (sortMode == 2) header = "Win Rate  ";
        else if (sortMode == 3) header = "Level     ";
        else header = "Matches   ";

        System.out.printf("Rank | Name                  | %s | Win Rate  | Level%n", header);
        System.out.println("--------------------------------------------------------------");
        int rank = 1;
        for (Player player : rankingService.getPlayerRanking(sortMode)) {
            String sortValue;
            if (sortMode == 1) {
                sortValue = String.format("%d", (int)((player.getWinRate() * 100) + player.getLevel()));
            } else if (sortMode == 2) {
                sortValue = String.format("%.2f%%", player.getWinRate() * 100);
            } else if (sortMode == 3) {
                sortValue = String.format("%d", player.getLevel());
            } else {
                sortValue = String.format("%d", rankingService.countMatchesForPlayer(player.getId()));
            }
            String winRateStr = String.format("%.2f%%", player.getWinRate() * 100);
            System.out.printf("%-5d| %-21s| %-11s| %-10s| %d%n",
                    rank, player.getName(), sortValue, winRateStr, player.getLevel());
            rank++;
        }
    }

    private void viewEquipmentStatistics() {
        System.out.println("\n=== Equipment Statistics ===");
        System.out.println("Rank | Name                    | Score     | Usage Count | Win Rate Contribution");
        System.out.println("---------------------------------------------------------------------------");
        int rank = 1;
        for (Equipment eq : rankingService.getEquipmentRanking()) {
            double score = eq.getUsageCount() * eq.getWinRateContribution();
            System.out.printf("%-5d| %-24s| %-10.2f| %-12d| %.2f%n",
                    rank, eq.getName(), score, eq.getUsageCount(), eq.getWinRateContribution());
            rank++;
        }
    }

    private void globalSearch() {
        String keyword = InputHelper.readString("Enter search keyword: ");
        List<Searchable> results = searchService.search(keyword);
        if (results.isEmpty()) {
            System.out.println("No results found for '" + keyword + "'.");
            return;
        }
        System.out.println("\n=== Search Results for '" + keyword + "' ===");
        for (Searchable item : results) {
            System.out.println("  - " + item);
        }
        System.out.println("Total: " + results.size() + " result(s).");
    }

    private void runHokArena() {
        System.out.println("\n=== HOK Arena & Recommendation System ===");

        String name1 = InputHelper.readString("Enter first hero name: ");
        Hero h1 = gdm.getHero(name1);
        if (h1 == null) {
            System.out.println("Hero '" + name1 + "' not found.");
            return;
        }

        String name2 = InputHelper.readString("Enter second hero name: ");
        Hero h2 = gdm.getHero(name2);
        if (h2 == null) {
            System.out.println("Hero '" + name2 + "' not found.");
            return;
        }

        System.out.println("\n--- Equipment Recommendations for " + h1.getName() + " ---");
        List<Equipment> rec1 = extraService.recommendEquipment(h1);
        if (rec1.isEmpty()) {
            System.out.println("No matching equipment recommendations.");
        } else {
            for (int i = 0; i < rec1.size(); i++) {
                Equipment eq = rec1.get(i);
                System.out.println((i + 1) + ". " + eq.getName() + " (" + eq.getType() + ") - " + eq.getStatBonus());
            }
        }

        System.out.println("\n--- Equipment Recommendations for " + h2.getName() + " ---");
        List<Equipment> rec2 = extraService.recommendEquipment(h2);
        if (rec2.isEmpty()) {
            System.out.println("No matching equipment recommendations.");
        } else {
            for (int i = 0; i < rec2.size(); i++) {
                Equipment eq = rec2.get(i);
                System.out.println((i + 1) + ". " + eq.getName() + " (" + eq.getType() + ") - " + eq.getStatBonus());
            }
        }

        System.out.println("\nDo you want to run the combat in:");
        System.out.println("[1] Classic Terminal Mode");
        System.out.println("[2] Dynamic Windows GUI Mode");
        System.out.println("[3] Trigger Automated Global Tournament & Export Report");
        int mode = InputHelper.readIntRange("Enter your choice: ", 1, 3);

        if (mode == 3) {
            extraService.runAutomatedTournament();
        } else if (mode == 2) {
            extraService.simulateGraphicalCombat(h1, h2);
        } else {
            System.out.println("\nPress Enter to start the 1v1 battle...");
            InputHelper.readString("");
            extraService.simulateCombat(h1, h2);
        }
    }
}
