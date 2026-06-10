package main;

import menu.AdminMenu;
import menu.PlayerMenu;
import service.AuthenticationService;
import service.ExtraFeaturesService;
import service.FileStorageService;
import service.GameDataManager;
import service.RankingService;
import service.SearchService;
import util.DataInitializer;
import util.InputHelper;

public class HonorOfKingsApp {
    // Main application entry point with login loop and role dispatch
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
                new AdminMenu(gdm, rankingService, searchService, authService, fileStorage, extraService).run();
            } else {
                new PlayerMenu(gdm, rankingService, authService, fileStorage, extraService).run();
            }

            authService.logout();
            System.out.println("Logged out successfully.");
        }
    }
}
