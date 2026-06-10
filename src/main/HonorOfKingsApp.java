package main;

import java.util.List;

import model.Equipment;
import model.Hero;
import model.HeroType;
import model.MatchRecord;
import model.Person;
import model.Player;
import model.Searchable;
import model.Team;
import service.AuthenticationService;
import service.ExtraFeaturesService;
import service.FileStorageService;
import service.GameDataManager;
import service.RankingService;
import service.SearchService;
import util.DataInitializer;
import util.InputHelper;

public class HonorOfKingsApp {
    public static void main(String[] args) {
        FileStorageService fileStorage = new FileStorageService();
        GameDataManager gdm = fileStorage.loadData();
        if (gdm == null) {
            gdm = new GameDataManager();
            DataInitializer.init(gdm);
            System.out.println("Initialized with default data.");
        }

        AuthenticationService authService = new AuthenticationService(gdm);
        RankingService rankingService = new RankingService(gdm);
        SearchService searchService = new SearchService(gdm);
        ExtraFeaturesService extraService = new ExtraFeaturesService(gdm);

        while (true) {
            System.out.println("\n=== Honor of Kings - Login ===");
            System.out.println("1. Login");
            System.out.println("2. Exit");

            int choice = InputHelper.readIntRange("Enter your choice: ", 1, 2);

            if (choice == 2) {
                fileStorage.saveData(gdm);
                System.out.println("Thank you for using the system. Goodbye!");
                break;
            }

            String id = InputHelper.readString("Enter user ID: ");
            String password = InputHelper.readString("Enter password: ");

            if (!authService.login(id, password)) {
                System.out.println("Invalid credentials. Please try again.");
                continue;
            }

            System.out.println("Welcome, " + authService.getCurrentUser().getName() + "!");

            if (authService.isAdmin()) {
                runAdminMenu(gdm, rankingService, searchService, authService, fileStorage, extraService);
            } else {
                runPlayerMenu(gdm, rankingService, authService, fileStorage, extraService);
            }

            authService.logout();
            System.out.println("Logged out successfully.");
        }
    }

    // ===================== PLAYER MENU =====================

    private static void runPlayerMenu(GameDataManager gdm, RankingService rankingService,
                                       AuthenticationService authService, FileStorageService fileStorage,
                                       ExtraFeaturesService extraService) {
        while (true) {
            System.out.println("\n=== Player Menu ===");
            System.out.println("1. View all heroes");
            System.out.println("2. View all equipment");
            System.out.println("3. View hero details");
            System.out.println("4. View player info");
            System.out.println("5. View player leaderboard");
            System.out.println("6. View equipment statistics");
            System.out.println("7. Edit my profile");
            System.out.println("8. [Bonus] HOK Arena & Recommendation");
            System.out.println("9. Logout");

            int choice = InputHelper.readIntRange("Enter your choice: ", 1, 9);

            switch (choice) {
                case 1:
                    viewAllHeroes(gdm);
                    break;
                case 2:
                    viewAllEquipment(gdm);
                    break;
                case 3:
                    viewHeroDetails(gdm);
                    break;
                case 4:
                    viewPlayerInfo(gdm);
                    break;
                case 5:
                    viewPlayerLeaderboard(rankingService);
                    break;
                case 6:
                    viewEquipmentStatistics(rankingService);
                    break;
                case 7:
                    editMyProfile(gdm, authService, fileStorage);
                    break;
                case 8:
                    runHokArena(gdm, extraService);
                    break;
                case 9:
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void editMyProfile(GameDataManager gdm, AuthenticationService authService,
                                       FileStorageService fileStorage) {
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
        String heroesStr = InputHelper.readString("Enter owned heroes (comma-separated, or press Enter to skip): ");
        if (!heroesStr.isEmpty()) {
            player.setOwnedHeroes(List.of(heroesStr.split(",")));
        }

        fileStorage.saveData(gdm);
        System.out.println("Profile updated successfully.");
    }

    private static void viewAllHeroes(GameDataManager gdm) {
        System.out.println("\n=== All Heroes ===");
        for (Hero hero : gdm.getAllHeroes()) {
            System.out.println("Name: " + hero.getName() + " | Type: " + hero.getType());
        }
    }

    private static void viewAllEquipment(GameDataManager gdm) {
        System.out.println("\n=== All Equipment ===");
        for (Equipment eq : gdm.getAllEquipment()) {
            System.out.println("Name: " + eq.getName() + " | Type: " + eq.getType() + " | Bonus: " + eq.getStatBonus());
        }
    }

    private static void viewHeroDetails(GameDataManager gdm) {
        String name = InputHelper.readString("Enter hero name: ");
        Hero hero = gdm.getHero(name);
        if (hero != null) {
            System.out.println("\nName: " + hero.getName());
            System.out.println("Type: " + hero.getType());
            System.out.println("Base Stats: " + hero.getBaseStats());
            System.out.println("Compatible Equipment: " + hero.getCompatibleEquipment());
        } else {
            System.out.println("Hero not found.");
        }
    }

    private static void viewPlayerInfo(GameDataManager gdm) {
        String id = InputHelper.readString("Enter player ID: ");
        Player player = gdm.getPlayer(id);
        if (player != null) {
            System.out.println("\nID: " + player.getId());
            System.out.println("Name: " + player.getName());
            System.out.println("Level: " + player.getLevel());
            System.out.println("Win Rate: " + (player.getWinRate() * 100) + "%");
            System.out.println("Team ID: " + player.getTeamId());
            System.out.println("Owned Heroes: " + player.getOwnedHeroes());
        } else {
            System.out.println("Player not found.");
        }
    }

    // ===================== ADMIN MENU =====================

    private static void runAdminMenu(GameDataManager gdm, RankingService rankingService,
                                      SearchService searchService, AuthenticationService authService,
                                      FileStorageService fileStorage, ExtraFeaturesService extraService) {
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
            System.out.println("9. View player leaderboard");
            System.out.println("10. View equipment statistics");
            System.out.println("11. Global search");
            System.out.println("12. [Bonus] HOK Arena & Recommendation");
            System.out.println("13. Save & Logout");

            int choice = InputHelper.readIntRange("Enter your choice: ", 1, 13);

            switch (choice) {
                case 1:
                    runHeroManagement(gdm, fileStorage);
                    break;
                case 2:
                    runEquipmentManagement(gdm, fileStorage);
                    break;
                case 3:
                    runPlayerManagement(gdm, fileStorage);
                    break;
                case 4:
                    runTeamManagement(gdm, fileStorage);
                    break;
                case 5:
                    runMatchManagement(gdm, fileStorage);
                    break;
                case 6:
                    viewAllHeroes(gdm);
                    break;
                case 7:
                    viewAllEquipment(gdm);
                    break;
                case 8:
                    viewAllMatchRecords(gdm);
                    break;
                case 9:
                    viewPlayerLeaderboard(rankingService);
                    break;
                case 10:
                    viewEquipmentStatistics(rankingService);
                    break;
                case 11:
                    globalSearch(searchService);
                    break;
                case 12:
                    runHokArena(gdm, extraService);
                    break;
                case 13:
                    fileStorage.saveData(gdm);
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    // ===================== HERO MANAGEMENT =====================

    private static void runHeroManagement(GameDataManager gdm, FileStorageService fileStorage) {
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
                    addHero(gdm, fileStorage);
                    break;
                case 2:
                    updateHero(gdm, fileStorage);
                    break;
                case 3:
                    deleteHero(gdm, fileStorage);
                    break;
                case 4:
                    viewHeroDetails(gdm);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void addHero(GameDataManager gdm, FileStorageService fileStorage) {
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

    private static void updateHero(GameDataManager gdm, FileStorageService fileStorage) {
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

    private static void deleteHero(GameDataManager gdm, FileStorageService fileStorage) {
        String name = InputHelper.readString("Enter hero name to delete: ");
        Hero removed = gdm.removeHero(name);
        if (removed != null) {
            fileStorage.saveData(gdm);
            System.out.println("Hero deleted successfully.");
        } else {
            System.out.println("Hero not found.");
        }
    }

    // ===================== EQUIPMENT MANAGEMENT =====================

    private static void runEquipmentManagement(GameDataManager gdm, FileStorageService fileStorage) {
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
                    addEquipment(gdm, fileStorage);
                    break;
                case 2:
                    updateEquipment(gdm, fileStorage);
                    break;
                case 3:
                    deleteEquipment(gdm, fileStorage);
                    break;
                case 4:
                    viewEquipmentDetails(gdm);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void addEquipment(GameDataManager gdm, FileStorageService fileStorage) {
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

    private static void updateEquipment(GameDataManager gdm, FileStorageService fileStorage) {
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

    private static void deleteEquipment(GameDataManager gdm, FileStorageService fileStorage) {
        String name = InputHelper.readString("Enter equipment name to delete: ");
        Equipment removed = gdm.removeEquipment(name);
        if (removed != null) {
            fileStorage.saveData(gdm);
            System.out.println("Equipment deleted successfully.");
        } else {
            System.out.println("Equipment not found.");
        }
    }

    private static void viewEquipmentDetails(GameDataManager gdm) {
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

    private static void runPlayerManagement(GameDataManager gdm, FileStorageService fileStorage) {
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
                    addPlayer(gdm, fileStorage);
                    break;
                case 2:
                    updatePlayer(gdm, fileStorage);
                    break;
                case 3:
                    deletePlayer(gdm, fileStorage);
                    break;
                case 4:
                    viewPlayerInfo(gdm);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void addPlayer(GameDataManager gdm, FileStorageService fileStorage) {
        String id = InputHelper.readString("Enter player ID: ");
        if (gdm.getPlayer(id) != null) {
            System.out.println("Player ID already exists.");
            return;
        }
        String name = InputHelper.readString("Enter player name: ");
        String password = InputHelper.readString("Enter password: ");
        int level = InputHelper.readInt("Enter level: ");
        double winRate = InputHelper.readInt("Enter win rate (0-100): ") / 100.0;
        String teamId = InputHelper.readString("Enter team ID: ");
        Player player = new Player(id, name, password, level, winRate, teamId);
        gdm.addPlayer(player);
        fileStorage.saveData(gdm);
        System.out.println("Player added successfully.");
    }

    private static void updatePlayer(GameDataManager gdm, FileStorageService fileStorage) {
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

    private static void deletePlayer(GameDataManager gdm, FileStorageService fileStorage) {
        String id = InputHelper.readString("Enter player ID to delete: ");
        Player removed = gdm.removePlayer(id);
        if (removed != null) {
            fileStorage.saveData(gdm);
            System.out.println("Player deleted successfully.");
        } else {
            System.out.println("Player not found.");
        }
    }

    // ===================== TEAM MANAGEMENT =====================

    private static void runTeamManagement(GameDataManager gdm, FileStorageService fileStorage) {
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
                    addTeam(gdm, fileStorage);
                    break;
                case 2:
                    updateTeam(gdm, fileStorage);
                    break;
                case 3:
                    deleteTeam(gdm, fileStorage);
                    break;
                case 4:
                    viewTeamDetails(gdm);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void addTeam(GameDataManager gdm, FileStorageService fileStorage) {
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

    private static void updateTeam(GameDataManager gdm, FileStorageService fileStorage) {
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

    private static void deleteTeam(GameDataManager gdm, FileStorageService fileStorage) {
        String teamId = InputHelper.readString("Enter team ID to delete: ");
        Team removed = gdm.removeTeam(teamId);
        if (removed != null) {
            fileStorage.saveData(gdm);
            System.out.println("Team deleted successfully.");
        } else {
            System.out.println("Team not found.");
        }
    }

    private static void viewTeamDetails(GameDataManager gdm) {
        String teamId = InputHelper.readString("Enter team ID: ");
        Team team = gdm.getTeam(teamId);
        if (team != null) {
            System.out.println("\nTeam ID: " + team.getTeamId());
            System.out.println("Name: " + team.getName());
            System.out.println("Members: " + team.getMemberIds());
            System.out.println("Total Matches: " + team.getTotalMatches());
            System.out.println("Wins: " + team.getWins());
        } else {
            System.out.println("Team not found.");
        }
    }

    // ===================== MATCH MANAGEMENT =====================

    private static void runMatchManagement(GameDataManager gdm, FileStorageService fileStorage) {
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
                    addMatchRecord(gdm, fileStorage);
                    break;
                case 2:
                    updateMatchRecord(gdm, fileStorage);
                    break;
                case 3:
                    deleteMatchRecord(gdm, fileStorage);
                    break;
                case 4:
                    viewMatchDetails(gdm);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void addMatchRecord(GameDataManager gdm, FileStorageService fileStorage) {
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
        gdm.addMatchRecord(match);
        fileStorage.saveData(gdm);
        System.out.println("Match record added successfully.");
    }

    private static void updateMatchRecord(GameDataManager gdm, FileStorageService fileStorage) {
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
        }
        fileStorage.saveData(gdm);
        System.out.println("Match record updated successfully.");
    }

    private static void deleteMatchRecord(GameDataManager gdm, FileStorageService fileStorage) {
        String matchId = InputHelper.readString("Enter match ID to delete: ");
        MatchRecord removed = gdm.removeMatchRecord(matchId);
        if (removed != null) {
            fileStorage.saveData(gdm);
            System.out.println("Match record deleted successfully.");
        } else {
            System.out.println("Match not found.");
        }
    }

    private static void viewMatchDetails(GameDataManager gdm) {
        String matchId = InputHelper.readString("Enter match ID: ");
        MatchRecord match = gdm.getMatchRecord(matchId);
        if (match != null) {
            System.out.println("\nMatch ID: " + match.getMatchId());
            System.out.println("Date: " + match.getDate());
            System.out.println("Team A: " + match.getTeamA());
            System.out.println("Team B: " + match.getTeamB());
            System.out.println("Winner: " + match.getWinner());
            System.out.println("Hero Picks: " + match.getHeroPicks());
        } else {
            System.out.println("Match not found.");
        }
    }

    // ===================== SHARED VIEW METHODS =====================

    private static void viewAllMatchRecords(GameDataManager gdm) {
        System.out.println("\n=== Match Records ===");
        if (gdm.getAllMatchRecords().isEmpty()) {
            System.out.println("No match records available.");
            return;
        }
        for (var match : gdm.getAllMatchRecords()) {
            System.out.println(match);
        }
    }

    private static void viewPlayerLeaderboard(RankingService rankingService) {
        System.out.println("\n=== Player Leaderboard ===");
        System.out.println("Rank | Name                  | Score | Win Rate  | Level");
        System.out.println("----------------------------------------------------------");
        int rank = 1;
        for (Player player : rankingService.getPlayerRanking()) {
            int score = (int)((player.getWinRate() * 100) + player.getLevel());
            String winRateStr = String.format("%.2f%%", player.getWinRate() * 100);
            System.out.printf("%-5d| %-21s| %-6d| %-10s| %d%n",
                    rank, player.getName(), score, winRateStr, player.getLevel());
            rank++;
        }
    }

    private static void viewEquipmentStatistics(RankingService rankingService) {
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

    private static void globalSearch(SearchService searchService) {
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

    // ===================== EXTRA FEATURES =====================

    private static void runHokArena(GameDataManager gdm, ExtraFeaturesService extraService) {
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

        System.out.println("\nPress Enter to start the 1v1 battle...");
        InputHelper.readString("");
        extraService.simulateCombat(h1, h2);
    }
}
