package main;

import util.DataInitializer;
import util.InputHelper;
import service.GameDataManager;
import service.SearchService;
import model.Hero;
import model.Player;
import model.Team;

public class HonorOfKingsApp {
    public static void main(String[] args) {
        GameDataManager gdm = new GameDataManager();
        DataInitializer.init(gdm);
        SearchService searchService = new SearchService(gdm);

        System.out.println("=== Honor of Kings Information Management System ===");

        while (true) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Player Lookup");
            System.out.println("2. Team Overview");
            System.out.println("3. Hero Details");
            System.out.println("4. Exit");
            System.out.println("------------------");

            int choice = InputHelper.readIntRange("Please select an option (1-4): ", 1, 4);

            switch (choice) {
                case 1:
                    handlePlayerLookup(searchService);
                    break;
                case 2:
                    handleTeamOverview(gdm);
                    break;
                case 3:
                    handleHeroDetails(searchService);
                    break;
                case 4:
                    System.out.println("Thank you for using the system. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void handlePlayerLookup(SearchService searchService) {
        String input = InputHelper.readString("Enter player name or ID: ");
        Player player = searchService.findPlayerByName(input);
        if (player == null) {
            player = searchService.findPlayerById(input);
        }
        if (player != null) {
            System.out.println("\n=== Player Found ===");
            System.out.println("ID: " + player.getId());
            System.out.println("Name: " + player.getName());
            System.out.println("Level: " + player.getLevel());
            System.out.println("Win Rate: " + (player.getWinRate() * 100) + "%");
            System.out.println("Team ID: " + player.getTeamId());
            System.out.println("Owned Heroes: " + player.getOwnedHeroes());
        } else {
            System.out.println("Player not found.");
        }
    }

    private static void handleTeamOverview(GameDataManager gdm) {
        System.out.println("\n=== Team Overview ===");
        if (gdm.getAllTeams().isEmpty()) {
            System.out.println("No teams available.");
            return;
        }
        for (Team team : gdm.getAllTeams()) {
            System.out.println("ID: " + team.getTeamId() + " | Name: " + team.getName()
                    + " | Total Matches: " + team.getTotalMatches() + " | Wins: " + team.getWins());
        }
    }

    private static void handleHeroDetails(SearchService searchService) {
        String name = InputHelper.readString("Enter hero name: ");
        Hero hero = searchService.findHeroByName(name);
        if (hero != null) {
            System.out.println("\n=== Hero Details ===");
            System.out.println("Name: " + hero.getName());
            System.out.println("Type: " + hero.getType());
            System.out.println("Base Stats: " + hero.getBaseStats());
            System.out.println("Compatible Equipment: " + hero.getCompatibleEquipment());
        } else {
            System.out.println("Hero not found.");
        }
    }
}
