package service;

import model.Admin;
import model.Person;
import model.Player;

public class AuthenticationService {

    private Person currentUser;
    private GameDataManager dataManager;

    public AuthenticationService(GameDataManager dataManager) {
        this.dataManager = dataManager;
    }

    public boolean login(String id, String password) {
        Person user = dataManager.getAdmin(id);
        if (user == null) {
            user = dataManager.getPlayer(id);
        }
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            return true;
        }
        return false;
    }

    public void logout() {
        currentUser = null;
    }

    public boolean isAdmin() {
        return currentUser instanceof Admin;
    }

    public Person getCurrentUser() {
        return currentUser;
    }
}
