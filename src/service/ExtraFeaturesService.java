package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.JOptionPane;

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

    public void simulateGraphicalCombat(Hero h1, Hero h2) {
        JOptionPane.showMessageDialog(null,
                "=== HOK ARENA 1v1 ===\n\nFighter 1: " + h1.getName() + " [" + h1.getType() + "]\nFighter 2: " + h2.getName() + " [" + h2.getType() + "]",
                "HOK Arena", JOptionPane.INFORMATION_MESSAGE);

        Map<String, Integer> stats1 = h1.getBaseStats();
        Map<String, Integer> stats2 = h2.getBaseStats();

        int maxHp1 = stats1.getOrDefault("hp", 3000);
        int maxHp2 = stats2.getOrDefault("hp", 3000);
        int hp1 = maxHp1;
        int hp2 = maxHp2;
        int atk1 = stats1.getOrDefault("attack", 150);
        int atk2 = stats2.getOrDefault("attack", 150);
        int def1 = stats1.getOrDefault("defense", 100);
        int def2 = stats2.getOrDefault("defense", 100);

        int round = 1;

        while (hp1 > 0 && hp2 > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("=== Round ").append(round).append(" ===\n\n");
            sb.append(h1.getName()).append(" HP: ").append(hp1).append("/").append(maxHp1).append("\n");
            sb.append(h2.getName()).append(" HP: ").append(hp2).append("/").append(maxHp2).append("\n\n");

            sb.append(h1.getName()).append(" HP: [");
            int bars1 = (int)((double)hp1 / maxHp1 * 20);
            for (int i = 0; i < 20; i++) {
                sb.append(i < bars1 ? '|' : ' ');
            }
            sb.append("]\n");

            sb.append(h2.getName()).append(" HP: [");
            int bars2 = (int)((double)hp2 / maxHp2 * 20);
            for (int i = 0; i < 20; i++) {
                sb.append(i < bars2 ? '|' : ' ');
            }
            sb.append("]\n\n");

            String msg1 = "";
            int dmg1 = atk1 + random.nextInt(20) - 10;
            boolean crit1 = random.nextDouble() < 0.20;
            boolean dodge2 = random.nextDouble() < 0.15;

            if (crit1) {
                dmg1 = (int)(dmg1 * 1.5);
            }

            if (dodge2) {
                msg1 = h2.getName() + " DODGED the attack!";
            } else {
                dmg1 = Math.max(dmg1 - def2 / 4, 5);
                hp2 -= dmg1;
                msg1 = h1.getName() + (crit1 ? " CRITICAL STRIKE! " : " attacks ") + "for " + dmg1 + " damage!";
            }

            sb.append(msg1).append("\n");

            if (hp2 <= 0) {
                sb.append("\n").append(h2.getName()).append(" has been DEFEATED!");
                JOptionPane.showMessageDialog(null, sb.toString(), "Round " + round, JOptionPane.INFORMATION_MESSAGE);
                break;
            }

            String msg2 = "";
            int dmg2 = atk2 + random.nextInt(20) - 10;
            boolean crit2 = random.nextDouble() < 0.20;
            boolean dodge1 = random.nextDouble() < 0.15;

            if (crit2) {
                dmg2 = (int)(dmg2 * 1.5);
            }

            if (dodge1) {
                msg2 = h1.getName() + " DODGED the attack!";
            } else {
                dmg2 = Math.max(dmg2 - def1 / 4, 5);
                hp1 -= dmg2;
                msg2 = h2.getName() + (crit2 ? " CRITICAL STRIKE! " : " attacks ") + "for " + dmg2 + " damage!";
            }

            sb.append(msg2).append("\n");

            if (hp1 <= 0) {
                sb.append("\n").append(h1.getName()).append(" has been DEFEATED!");
                JOptionPane.showMessageDialog(null, sb.toString(), "Round " + round, JOptionPane.INFORMATION_MESSAGE);
                break;
            }

            JOptionPane.showMessageDialog(null, sb.toString(), "Round " + round, JOptionPane.INFORMATION_MESSAGE);
            round++;
        }

        String winner = hp1 > 0 ? h1.getName() : h2.getName();
        JOptionPane.showMessageDialog(null,
                "====================================\n"
              + "      THE CHAMPION: " + winner + "!\n"
              + "====================================",
                "Battle Over!", JOptionPane.INFORMATION_MESSAGE);
    }
}
