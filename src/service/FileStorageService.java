package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileStorageService {

    private static final String FILE_NAME = "data.ser";

    public void saveData(GameDataManager dataManager) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(dataManager);
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    public GameDataManager loadData() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("No save file found. Starting with default data.");
            return null;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (GameDataManager) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No save file found. Starting with default data.");
            return null;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
            return null;
        }
    }
}
