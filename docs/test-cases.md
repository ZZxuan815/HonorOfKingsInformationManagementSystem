# Test Cases — Honor of Kings Information Management System

---

## T01: Login Authentication (Admin)

| Field | Value |
|-------|-------|
| **ID** | T01 |
| **Function** | Login Authentication — Admin user |
| **Input** | ID: `A001`, Password: `admin123` |
| **Expected** | "Welcome, AdminZhang!" displayed, Admin Menu shown |
| **Actual** | "Welcome, AdminZhang!" followed by Admin Menu with 14 options |
| **Result** | PASS |
| **Bug Found** | None |

---

## T02: Login Authentication (Invalid Credentials)

| Field | Value |
|-------|-------|
| **ID** | T02 |
| **Function** | Login Authentication — Invalid credentials |
| **Input** | ID: `WRONG`, Password: `wrongpass` |
| **Expected** | "Invalid credentials. Please try again." and stay on login screen |
| **Actual** | "Invalid credentials. Please try again." — returns to login prompt |
| **Result** | PASS |
| **Bug Found** | None |

---

## T03: Player Leaderboard Sorting

| Field | Value |
|-------|-------|
| **ID** | T03 |
| **Function** | Player Leaderboard — verify descending score with alphabetical tie-break |
| **Input** | Login as Player (ID: `P001`, Password: `pass123`) → Option 6 (View player leaderboard) |
| **Expected** | Sorted by `(winRate*100)+level` descending. Ties (e.g., Li Bai score=103 vs Mo Ye score=103) sorted alphabetically by name |
| **Actual** | Han Xin (127), Lu Bu (126), Cao Cao (114), Li Bai (103), Mo Ye (103) — correct order |
| **Result** | PASS |
| **Bug Found** | None |

---

## T04: Equipment Statistics (Empty Data)

| Field | Value |
|-------|-------|
| **ID** | T04 |
| **Function** | Equipment Statistics — all scores are 0.0 due to unpopulated usage data |
| **Input** | Login as Admin → Option 11 (View equipment statistics) |
| **Expected** | All equipment displayed with Score = 0.00, sorted alphabetically (all ties) |
| **Actual** | 20 equipment items listed, all scores 0.00, sorted alphabetically by name |
| **Result** | PASS |
| **Bug Found** | None — `usageCount` and `winRateContribution` are 0 by default; no data update mechanism exists yet. |

---

## T05: Global Search (Case-Insensitive)

| Field | Value |
|-------|-------|
| **ID** | T05 |
| **Function** | Global Search — case-insensitive keyword matching across entities |
| **Input** | Login as Admin → Option 12 → Keyword: `li` |
| **Expected** | Returns Players/Heroes/Equipment/Teams/MatchRecords whose fields contain "li" (case-insensitive) |
| **Actual** | Found Li Bai (Player), Li Bai (Hero), Bai Li Shou Yue (Hero), plus relevant Equipment and MatchRecords |
| **Result** | PASS |
| **Bug Found** | None |

---

## T06: Player Self-Profile Edit

| Field | Value |
|-------|-------|
| **ID** | T06 |
| **Function** | Player self-profile edit — update level, win rate, owned heroes |
| **Input** | Login as Player `P001` (Li Bai) → Option 8 → New level: `50` → New win rate: `85` → Owned heroes: `Li Bai,Zhao Yun` |
| **Expected** | "Profile updated successfully." Level changes from 35 to 50, win rate from 68% to 85%, owned heroes updated |
| **Actual** | Profile updated. Re-login and view player info confirms Level=50, Win Rate=85%, Owned Heroes=[Li Bai, Zhao Yun] |
| **Result** | PASS |
| **Bug Found** | None |

---

## T07: Admin Add Hero

| Field | Value |
|-------|-------|
| **ID** | T07 |
| **Function** | Admin — add new hero |
| **Input** | Login as Admin → Hero Management → Add new hero → Name: `Ying Zheng` → Type: `MAGE` |
| **Expected** | "Hero added successfully." Hero appears in "View all heroes" list. Data persisted to `data.ser`. |
| **Actual** | Hero added. Visible in View all heroes. File created/updated. |
| **Result** | PASS |
| **Bug Found** | None |

---

## T08: Admin Delete Player

| Field | Value |
|-------|-------|
| **ID** | T08 |
| **Function** | Admin — delete existing player |
| **Input** | Login as Admin → Player Management → Delete player → ID: `P001` |
| **Expected** | "Player deleted successfully." Player P001 no longer appears in view/list. Data persisted. |
| **Actual** | Player deleted. View player info for P001 returns "Player not found." |
| **Result** | PASS |
| **Bug Found** | None |

---

## T09: Admin Add Duplicate Team

| Field | Value |
|-------|-------|
| **ID** | T09 |
| **Function** | Admin — duplicate team ID detection |
| **Input** | Login as Admin → Team Management → Add new team → ID: `T01` → Name: `Duplicate` |
| **Expected** | "Team ID already exists." No duplicate added. |
| **Actual** | "Team ID already exists." T01 remains unchanged. |
| **Result** | PASS |
| **Bug Found** | None |

---

## T10: File Persistence (Save/Load)

| Field | Value |
|-------|-------|
| **ID** | T10 |
| **Function** | File persistence — data survives application restart |
| **Input** | 1. Login as Admin → Add hero "TestHero" → Logout → Exit (2) 2. Restart app 3. Login as Admin → View all heroes |
| **Expected** | "TestHero" still appears in the hero list after restart. `data.ser` file exists on disk. |
| **Actual** | Hero list includes "TestHero" after restart. `data.ser` file present and non-empty. |
| **Result** | PASS |
| **Bug Found** | None |

---

## T11: Equipment Recommendation Verification

| Field | Value |
|-------|-------|
| **ID** | T11 |
| **Function** | Equipment Recommendation — verify MAGE hero gets magic-type equipment recommended |
| **Input** | Login as Player → Option 9 (HOK Arena) → First hero: `Diao Chan` (MAGE) → Second hero: `Lu Bu` (WARRIOR) |
| **Expected** | Diao Chan receives magic/mana equipment (e.g., Book of Sages). Lu Bu receives attack/crit weapons. Both see top 3 recommendations. |
| **Actual** | Diao Chan recommended: Book of Sages, Boots of Resistance, Frozen Staff. Lu Bu recommended: Shadow Blade, Endless Blade, Bloodthirsty Blade. |
| **Result** | PASS |
| **Bug Found** | None |

---

## T12: 1v1 Combat Simulation Loop Verification

| Field | Value |
|-------|-------|
| **ID** | T12 |
| **Function** | 1v1 Combat Simulation — verify turn-based battle loop runs, critical strikes (20%) and dodges (15%) trigger, and a winner is declared |
| **Input** | Login as Admin → Option 13 (HOK Arena) → First hero: `Li Bai` → Second hero: `Han Xin` → Press Enter to start battle |
| **Expected** | Multi-round battle log printed. Each round shows damage dealt, critical strike messages ("CRITICAL!"), dodge messages ("dodged the attack!"). One hero's HP reaches zero and a winner is declared. |
| **Actual** | Battle ran for 6 rounds with critical hits and dodges triggering. Winner: Han Xin declared. Full step-by-step action log printed. |
| **Result** | PASS |
| **Bug Found** | None |

---

## T13: Graphical GUI Dialog Combat Verification

| Field | Value |
|-------|-------|
| **ID** | T13 |
| **Function** | Graphical GUI Dialog Combat — verify `JOptionPane.showMessageDialog()` pop-ups render correctly for each battle round, showing ASCII health bars, damage counters, critical hit alerts, and a final champion declaration |
| **Input** | Login as Player → Option 9 (HOK Arena) → First hero: `Li Bai` → Second hero: `Han Xin` → Mode: [2] Dynamic Windows GUI Mode |
| **Expected** | A series of graphical dialog windows appear. Each shows an ASCII health bar, damage logs, and "CRITICAL STRIKE!" or "DODGED" messages. Final pop-up declares "THE CHAMPION: [name]!". All windows are dismissible with OK. |
 | **Actual** | 6 round dialogs appeared with health bars and damage text. Final champion dialog displayed "THE CHAMPION: Li Bai!". No exceptions thrown. Windows rendered correctly on screen. |
| **Result** | PASS |
| **Bug Found** | None |

---

## T14: Automated Tournament and TXT Export Verification

| Field | Value |
|-------|-------|
| **ID** | T14 |
| **Function** | Automated Tournament Simulation — verify at least 10 random 1v1 matches run silently, win analytics are computed, and a formatted `tournament_report.txt` is written to disk with header, match log, and ranked leaderboard |
| **Input** | Login as Admin → Option 13 (HOK Arena) → Any names → Mode: [3] Trigger Automated Global Tournament & Export Report |
| **Expected** | Console prints "Global tournament simulation complete! Full analytics exported to 'tournament_report.txt'." File exists on disk containing timestamped header, 10+ match logs, and a "King of the Hill" leaderboard sorted by win rate descending. |
 | **Actual** | 86 matches simulated across 15 heroes (round-robin pairs). `tournament_report.txt` created with title header "HONOR OF KINGS — AUTOMATED TOURNAMENT REPORT", match outcome log, and ranked table with Champion crowned. |
| **Result** | PASS |
| **Bug Found** | None |

---

## T15: Match History by Player ID

| Field | Value |
|-------|-------|
| **ID** | T15 |
| **Function** | Match History by Player ID — verify aggregate stats display win/loss record and hero pick rates |
| **Input** | Login as Player → Option 5 (View match history) → Search: [1] Player ID → ID: `P001` → Recent matches: `5` |
| **Expected** | List of matches shown with opponent and result. Followed by "Aggregate Statistics" section showing "Total: X wins, Y losses" and "Hero Pick Rate" listing each hero's percentage and count. |
| **Actual** | 5 matches listed with correct opponent/result. Aggregate stats showed Total: 3 wins, 2 losses. Hero Pick Rate included Li Bai: 40.0%, Han Xin: 20.0%, etc. |
| **Result** | PASS |
| **Bug Found** | None |

---

## T16: Player Equipped Items Display

| Field | Value |
|-------|-------|
| **ID** | T16 |
| **Function** | Player Equipped Items Display — verify owned heroes show associated equipment under "Equipped Items" |
| **Input** | Login as Player → Option 4 (View player info) → ID: `P001` (Li Bai) |
| **Expected** | After "Owned Heroes", the "Equipped Items:" section lists each hero with their equipment: "Li Bai -> [Shadow Blade, Boots of Resistance]", "Han Xin -> [Endless Blade, Guardian Angel]", "Zhao Yun -> [Shadow Blade, Armor of Thorns]". |
| **Actual** | Equipped Items displayed correctly for all 3 owned heroes with full equipment lists. |
| **Result** | PASS |
| **Bug Found** | None |
