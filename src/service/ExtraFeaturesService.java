package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import model.Equipment;
import model.Hero;

public class ExtraFeaturesService {

    private GameDataManager dataManager;
    private Random random;

    public ExtraFeaturesService(GameDataManager dataManager) {
        this.dataManager = dataManager;
        this.random = new Random();
    }

    public List<Equipment> recommendEquipment(Hero hero) {
        List<Equipment> allEquipment = new ArrayList<>(dataManager.getAllEquipment());
        List<Equipment> filtered = new ArrayList<>();
        String heroType = hero.getType().name();

        for (Equipment eq : allEquipment) {
            String eqType = eq.getType().toLowerCase();
            String eqBonus = eq.getStatBonus().toLowerCase();

            if (heroType.equals("MAGE")) {
                if (eqType.contains("magic") || eqBonus.contains("magic") || eqBonus.contains("mana")) {
                    filtered.add(eq);
                }
            } else if (heroType.equals("ASSASSIN") || heroType.equals("WARRIOR") || heroType.equals("MARKSMAN") || heroType.equals("JUNGLER")) {
                if (eqType.contains("weapon") || eqBonus.contains("attack") || eqBonus.contains("crit")) {
                    filtered.add(eq);
                }
            } else if (heroType.equals("TANK") || heroType.equals("SUPPORT")) {
                if (eqType.contains("armor") || eqType.contains("boots") || eqBonus.contains("armor") || eqBonus.contains("hp") || eqBonus.contains("defense")) {
                    filtered.add(eq);
                }
            }
        }

        if (filtered.size() > 3) {
            filtered = filtered.subList(0, 3);
        }

        return filtered;
    }

    public void simulateCombat(Hero h1, Hero h2) {
        System.out.println("\n========== HOK ARENA 1v1 ==========");
        System.out.println("Fighter 1: " + h1.getName() + " [" + h1.getType() + "]");
        System.out.println("Fighter 2: " + h2.getName() + " [" + h2.getType() + "]");
        System.out.println("====================================\n");

        Map<String, Integer> stats1 = h1.getBaseStats();
        Map<String, Integer> stats2 = h2.getBaseStats();

        int hp1 = stats1.getOrDefault("hp", 3000);
        int hp2 = stats2.getOrDefault("hp", 3000);
        int atk1 = stats1.getOrDefault("attack", 150);
        int atk2 = stats2.getOrDefault("attack", 150);
        int def1 = stats1.getOrDefault("defense", 100);
        int def2 = stats2.getOrDefault("defense", 100);

        int round = 1;

        while (hp1 > 0 && hp2 > 0) {
            System.out.println("--- Round " + round + " ---");
            System.out.println(h1.getName() + " HP: " + hp1 + " | " + h2.getName() + " HP: " + hp2);

            int dmg1 = atk1 + random.nextInt(20) - 10;
            boolean crit1 = random.nextDouble() < 0.20;
            boolean dodge2 = random.nextDouble() < 0.15;

            if (crit1) {
                dmg1 = (int)(dmg1 * 1.5);
            }

            if (dodge2) {
                System.out.println(h2.getName() + " dodged the attack!");
            } else {
                dmg1 = Math.max(dmg1 - def2 / 4, 5);
                hp2 -= dmg1;
                String critMsg = crit1 ? " (CRITICAL!)" : "";
                System.out.println(h1.getName() + " attacks" + critMsg + " for " + dmg1 + " damage!");
            }

            if (hp2 <= 0) {
                System.out.println("\n" + h2.getName() + " has been defeated!");
                break;
            }

            int dmg2 = atk2 + random.nextInt(20) - 10;
            boolean crit2 = random.nextDouble() < 0.20;
            boolean dodge1 = random.nextDouble() < 0.15;

            if (crit2) {
                dmg2 = (int)(dmg2 * 1.5);
            }

            if (dodge1) {
                System.out.println(h1.getName() + " dodged the attack!");
            } else {
                dmg2 = Math.max(dmg2 - def1 / 4, 5);
                hp1 -= dmg2;
                String critMsg = crit2 ? " (CRITICAL!)" : "";
                System.out.println(h2.getName() + " attacks" + critMsg + " for " + dmg2 + " damage!");
            }

            if (hp1 <= 0) {
                System.out.println("\n" + h1.getName() + " has been defeated!");
                break;
            }

            round++;
            System.out.println();
        }

        String winner = hp1 > 0 ? h1.getName() : h2.getName();
        System.out.println("\n========== WINNER: " + winner + " ==========\n");
    }
}
