package service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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

    private String silentSimulateCombat(Hero h1, Hero h2) {
        Map<String, Integer> stats1 = h1.getBaseStats();
        Map<String, Integer> stats2 = h2.getBaseStats();

        int hp1 = stats1.getOrDefault("hp", 3000);
        int hp2 = stats2.getOrDefault("hp", 3000);
        int atk1 = stats1.getOrDefault("attack", 150);
        int atk2 = stats2.getOrDefault("attack", 150);
        int def1 = stats1.getOrDefault("defense", 100);
        int def2 = stats2.getOrDefault("defense", 100);

        while (hp1 > 0 && hp2 > 0) {
            int dmg1 = atk1 + random.nextInt(20) - 10;
            boolean crit1 = random.nextDouble() < 0.20;
            boolean dodge2 = random.nextDouble() < 0.15;

            if (crit1) dmg1 = (int)(dmg1 * 1.5);

            if (!dodge2) {
                dmg1 = Math.max(dmg1 - def2 / 4, 5);
                hp2 -= dmg1;
            }

            if (hp2 <= 0) break;

            int dmg2 = atk2 + random.nextInt(20) - 10;
            boolean crit2 = random.nextDouble() < 0.20;
            boolean dodge1 = random.nextDouble() < 0.15;

            if (crit2) dmg2 = (int)(dmg2 * 1.5);

            if (!dodge1) {
                dmg2 = Math.max(dmg2 - def1 / 4, 5);
                hp1 -= dmg2;
            }
        }

        return hp1 > 0 ? h1.getName() : h2.getName();
    }

    public void runAutomatedTournament() {
        List<Hero> allHeroes = new ArrayList<>(dataManager.getAllHeroes());

        if (allHeroes.size() < 2) {
            System.out.println("Not enough heroes to run a tournament (need at least 2).");
            return;
        }

        int totalMatches = Math.max(10, allHeroes.size() * (allHeroes.size() - 1));
        System.out.println("\nRunning automated tournament with " + allHeroes.size()
                + " heroes across " + totalMatches + " matches...");

        Map<String, Integer> winCount = new HashMap<>();
        Map<String, Integer> matchCount = new HashMap<>();
        for (Hero h : allHeroes) {
            winCount.put(h.getName(), 0);
            matchCount.put(h.getName(), 0);
        }

        List<String> matchLog = new ArrayList<>();

        for (int i = 0; i < totalMatches; i++) {
            Hero h1 = allHeroes.get(random.nextInt(allHeroes.size()));
            Hero h2 = allHeroes.get(random.nextInt(allHeroes.size()));

            while (h1.getName().equals(h2.getName())) {
                h2 = allHeroes.get(random.nextInt(allHeroes.size()));
            }

            String winner = silentSimulateCombat(h1, h2);
            winCount.put(winner, winCount.get(winner) + 1);
            matchCount.put(h1.getName(), matchCount.get(h1.getName()) + 1);
            matchCount.put(h2.getName(), matchCount.get(h2.getName()) + 1);

            matchLog.add(String.format("Match %3d: %s vs %s -> Winner: %s", (i + 1), h1.getName(), h2.getName(), winner));
        }

        List<String> sortedHeroes = new ArrayList<>(allHeroes.size());
        for (Hero h : allHeroes) {
            sortedHeroes.add(h.getName());
        }
        Collections.sort(sortedHeroes, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                double wrA = matchCount.get(a) > 0 ? (double) winCount.get(a) / matchCount.get(a) : 0.0;
                double wrB = matchCount.get(b) > 0 ? (double) winCount.get(b) / matchCount.get(b) : 0.0;
                if (wrA > wrB) return -1;
                if (wrA < wrB) return 1;
                return a.compareTo(b);
            }
        });

        try (PrintWriter pw = new PrintWriter(new FileWriter("./tournament_report.txt"))) {
            pw.println("========================================================================");
            pw.println("  HONOR OF KINGS — AUTOMATED TOURNAMENT REPORT");
            pw.println("  Date: June 2026 | Coursework Submission");
            pw.println("========================================================================");
            pw.println();
            pw.println("Total Heroes: " + allHeroes.size());
            pw.println("Total Matches Simulated: " + totalMatches);
            pw.println();

            pw.println("------------------------------------------------------------------------");
            pw.println("  MATCH OUTCOME LOG");
            pw.println("------------------------------------------------------------------------");
            for (String logLine : matchLog) {
                pw.println("  " + logLine);
            }
            pw.println();

            pw.println("------------------------------------------------------------------------");
            pw.println("  HOK KING OF THE HILL — FINAL RANKINGS");
            pw.println("------------------------------------------------------------------------");
            pw.printf("%-4s %-22s %-8s %-8s %-12s%n", "Rank", "Hero Name", "Wins", "Matches", "Win Rate");
            pw.println("------------------------------------------------------------------------");
            int rank = 1;
            for (String name : sortedHeroes) {
                int w = winCount.get(name);
                int m = matchCount.get(name);
                double wr = m > 0 ? (double) w / m * 100.0 : 0.0;
                pw.printf("%-4d %-22s %-8d %-8d %-10.2f%%%n", rank, name, w, m, wr);
                rank++;
            }
            pw.println();
            pw.println("========================================================================");
            pw.println("  CHAMPION: " + sortedHeroes.get(0) + " with "
                    + String.format("%.2f%%", matchCount.get(sortedHeroes.get(0)) > 0
                            ? (double) winCount.get(sortedHeroes.get(0)) / matchCount.get(sortedHeroes.get(0)) * 100.0 : 0.0)
                    + " win rate!");
            pw.println("========================================================================");

            System.out.println("SUCCESS: Global tournament simulation complete! Full analytics exported to 'tournament_report.txt'.");
        } catch (IOException e) {
            System.out.println("Error writing tournament report: " + e.getMessage());
        }
    }
}
