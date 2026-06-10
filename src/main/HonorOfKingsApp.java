package main;

import model.Equipment;
import model.Hero;
import model.HeroType;
import model.Player;
import service.AuthenticationService;
import service.GameDataManager;
import service.RankingService;
import util.DataInitializer;
import util.InputHelper;

public class HonorOfKingsApp {
    public static void main(String[] args) {
        GameDataManager gdm = new GameDataManager();
        DataInitializer.init(gdm);
        AuthenticationService authService = new AuthenticationService(gdm);
        RankingService rankingService = new RankingService(gdm);

        while (true) {
            System.out.println("\n=== Honor of Kings - Login ===");
            System.out.println("1. Login");
            System.out.println("2. Exit");

            int choice = InputHelper.readIntRange("Enter your choice: ", 1, 2);

            if (choice == 2) {
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
                runAdminMenu(gdm, rankingService, authService);
            } else {
                runPlayerMenu(gdm, rankingService);
            }

            authService.logout();
            System.out.println("Logged out successfully.");
        }
    }

    // ===================== PLAYER MENU =====================

    private static void runPlayerMenu(GameDataManager gdm, RankingService rankingService) {
        while (true) {
            System.out.println("\n=== Player Menu ===");
            System.out.println("1. View all heroes");
            System.out.println("2. View all equipment");
            System.out.println("3. View hero details");
            System.out.println("4. View player info");
            System.out.println("5. View player leaderboard");
            System.out.println("6. View equipment statistics");
            System.out.println("7. Logout");

            int choice = InputHelper.readIntRange("Enter your choice: ", 1, 7);

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
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
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

    private static void runAdminMenu(GameDataManager gdm, RankingService rankingService, AuthenticationService authService) {
        while (true) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. Hero Management");
            System.out.println("2. Equipment Management");
            System.out.println("3. View all heroes");
            System.out.println("4. View all equipment");
            System.out.println("5. View match records");
            System.out.println("6. View player leaderboard");
            System.out.println("7. View equipment statistics");
            System.out.println("8. Logout");

            int choice = InputHelper.readIntRange("Enter your choice: ", 1, 8);

            switch (choice) {
                case 1:
                    runHeroManagement(gdm);
                    break;
                case 2:
                    runEquipmentManagement(gdm);
                    break;
                case 3:
                    viewAllHeroes(gdm);
                    break;
                case 4:
                    viewAllEquipment(gdm);
                    break;
                case 5:
                    viewAllMatchRecords(gdm);
                    break;
                case 6:
                    viewPlayerLeaderboard(rankingService);
                    break;
                case 7:
                    viewEquipmentStatistics(rankingService);
                    break;
                case 8:
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void runHeroManagement(GameDataManager gdm) {
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
                    addHero(gdm);
                    break;
                case 2:
                    updateHero(gdm);
                    break;
                case 3:
                    deleteHero(gdm);
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

    private static void addHero(GameDataManager gdm) {
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
        System.out.println("Hero added successfully.");
    }

    private static void updateHero(GameDataManager gdm) {
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
        System.out.println("Hero updated successfully.");
    }

    private static void deleteHero(GameDataManager gdm) {
        String name = InputHelper.readString("Enter hero name to delete: ");
        Hero removed = gdm.removeHero(name);
        if (removed != null) {
            System.out.println("Hero deleted successfully.");
        } else {
            System.out.println("Hero not found.");
        }
    }

    private static void runEquipmentManagement(GameDataManager gdm) {
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
                    addEquipment(gdm);
                    break;
                case 2:
                    updateEquipment(gdm);
                    break;
                case 3:
                    deleteEquipment(gdm);
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

    private static void addEquipment(GameDataManager gdm) {
        String name = InputHelper.readString("Enter equipment name: ");
        if (gdm.getEquipment(name) != null) {
            System.out.println("Equipment already exists.");
            return;
        }
        String type = InputHelper.readString("Enter equipment type: ");
        String statBonus = InputHelper.readString("Enter stat bonus: ");
        Equipment eq = new Equipment(name, type, statBonus);
        gdm.addEquipment(eq);
        System.out.println("Equipment added successfully.");
    }

    private static void updateEquipment(GameDataManager gdm) {
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
        System.out.println("Equipment updated successfully.");
    }

    private static void deleteEquipment(GameDataManager gdm) {
        String name = InputHelper.readString("Enter equipment name to delete: ");
        Equipment removed = gdm.removeEquipment(name);
        if (removed != null) {
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
}
