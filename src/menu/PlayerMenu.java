package menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Equipment;
import model.Hero;
import model.MatchRecord;
import model.Person;
import model.Player;
import service.AuthenticationService;
import service.ExtraFeaturesService;
import service.FileStorageService;
import service.GameDataManager;
import service.RankingService;
import util.InputHelper;

public class PlayerMenu {

    private GameDataManager gdm;
    private RankingService rankingService;
    private AuthenticationService authService;
    private FileStorageService fileStorage;
    private ExtraFeaturesService extraService;

    public PlayerMenu(GameDataManager gdm, RankingService rankingService,
                      AuthenticationService authService, FileStorageService fileStorage,
                      ExtraFeaturesService extraService) {
        this.gdm = gdm;
        this.rankingService = rankingService;
        this.authService = authService;
        this.fileStorage = fileStorage;
        this.extraService = extraService;
    }

    public void run() {
        while (true) {
            System.out.println("\n=== Player Menu ===");
            System.out.println("1. View all heroes");
            System.out.println("2. View all equipment");
            System.out.println("3. View hero details");
            System.out.println("4. View player info");
            System.out.println("5. View match history");
            System.out.println("6. View player leaderboard");
            System.out.println("7. View equipment statistics");
            System.out.println("8. Edit my profile");
            System.out.println("9. [Bonus] HOK Arena & Recommendation");
            System.out.println("10. Logout");

            int choice = InputHelper.readIntRange("Enter your choice: ", 1, 10);

            // Player menu: read-only access plus self-profile editing
            switch (choice) {
                case 1:
                    viewAllHeroes();
                    break;
                case 2:
                    viewAllEquipment();
                    break;
                case 3:
                    viewHeroDetails();
                    break;
                case 4:
                    viewPlayerInfo();
                    break;
                case 5:
                    viewMatchHistory();
                    break;
                case 6:
                    viewPlayerLeaderboard();
                    break;
                case 7:
                    viewEquipmentStatistics();
                    break;
                case 8:
                    editMyProfile();
                    break;
                case 9:
                    runHokArena();
                    break;
                case 10:
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

    // Allows the logged-in player to edit their level, win rate, owned heroes, and equipment
    private void editMyProfile() {
        Person currentUser = authService.getCurrentUser();
        if (!(currentUser instanceof Player)) {
            System.out.println("Only players can edit their profile.");
            return;
        }
        Player player = (Player) currentUser;
        System.out.println("\n--- Edit My Profile ---");
        System.out.println("Current Level: " + player.getLevel());
        String levelStr = InputHelper.readString("Enter new level (or press Enter to skip): ");
        if (!levelStr.isEmpty()) {
            try {
                player.setLevel(Integer.parseInt(levelStr));
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Keeping original.");
            }
        }

        System.out.println("Current Win Rate: " + (player.getWinRate() * 100) + "%");
        String wrStr = InputHelper.readString("Enter new win rate (0-100, or press Enter to skip): ");
        if (!wrStr.isEmpty()) {
            try {
                double wr = Double.parseDouble(wrStr) / 100.0;
                if (wr >= 0.0 && wr <= 1.0) {
                    player.setWinRate(wr);
                } else {
                    System.out.println("Win rate must be between 0 and 100. Keeping original.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Keeping original.");
            }
        }

        System.out.println("Current Owned Heroes: " + player.getOwnedHeroes());
        String heroesStr = InputHelper.readStringOptional("Enter owned heroes (comma-separated, or press Enter to skip): ");
        if (!heroesStr.isEmpty()) {
            List<String> trimmed = new ArrayList<>();
            for (String h : heroesStr.split(",")) {
                String t = h.trim();
                if (!t.isEmpty()) trimmed.add(t);
            }
            if (!trimmed.isEmpty()) {
                player.setOwnedHeroes(trimmed);
            }
        }

        String equipHero = InputHelper.readStringOptional("Enter hero name to equip (or press Enter to skip): ");
        if (!equipHero.isEmpty()) {
            if (!player.getOwnedHeroes().contains(equipHero)) {
                System.out.println("You don't own this hero.");
            } else {
                String equipStr = InputHelper.readStringOptional("Enter equipment names (comma-separated): ");
                if (!equipStr.isEmpty()) {
                    List<String> trimmedEquip = new ArrayList<>();
                    for (String e : equipStr.split(",")) {
                        String t = e.trim();
                        if (!t.isEmpty()) trimmedEquip.add(t);
                    }
                    if (!trimmedEquip.isEmpty()) {
                        player.getEquippedItems().put(equipHero, trimmedEquip);
                        System.out.println("Equipment updated for " + equipHero + ".");
                    }
                }
            }
        }

        fileStorage.saveData(gdm);
        System.out.println("Profile updated successfully.");
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
