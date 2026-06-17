# Agent Division of Labour Log

## Overview
Three AI agent roles were used throughout the project, directed by me (the human developer). I made all final decisions and reviewed every piece of code before using it.

---

## 1. Architect Agent

**Role:** Design the class hierarchy, choose collections, and plan the menu flow.

| Stage | Task | AI Output | My Decision |
|-------|------|-----------|-------------|
| 2 | Person → Player / Admin inheritance | Suggested `Person` with `id`, `name`, `password`; `Player` adds `level`, `winRate`, `teamId`, `ownedHeroes`; `Admin` adds `role` | Accepted. I changed `Person` to `abstract` because it should not be instantiated directly. |
| 2 | Enum design | Generated 7 `HeroType` values and 3 `MatchResult` values | Accepted as-is. |
| 3 | Collection design for GameDataManager | Suggested `HashMap<String, Player>`, `HashMap<String, Hero>`, etc. | Accepted. `HashMap` gives O(1) lookup by ID, which is better than looping through a list. |
| 4 | Menu text flow | Drew a console flow: Login → Role Dispatch → Player Menu (read-only) → Admin Menu (CRUD sub-menus) | Accepted as a blueprint. I implemented the exact structure but added more menu options than the AI suggested. |

---

## 2. Implementation Agent

**Role:** Write specific Java methods and classes based on my instructions.

| Stage | Task | AI Output | My Decision |
|-------|------|-----------|-------------|
| 3 | `InputHelper` utility | Static `readInt()`, `readIntRange()`, `readString()` with retry loops | Accepted. Worked well for all user input. |
| 3 | `GameDataManager` CRUD | `add/get/remove/getAll` for all 6 entity types | Accepted. I later added `getMatchRecord()` and `removeMatchRecord()` because the AI missed them in the first pass. |
| 3 | `SearchService` | Find methods for player by name/ID, hero by name, team by name/ID | Accepted. |
| 4 | `AuthenticationService` | `login()`, `logout()`, `isAdmin()`, `getCurrentUser()` | Accepted. I kept the `import model.Player` even though only `Admin` is checked, for clarity. |
| 5 | `RankingService` | `getPlayerRanking()` and `getEquipmentRanking()` with custom `Comparator` | Accepted. I manually verified the tie-breaking logic with edge cases. |
| 6 | `FileStorageService` | Save/load via `ObjectOutputStream`/`ObjectInputStream` to `data.ser` | Accepted. I connected it to the app startup and every admin CRUD handler for auto-save. |
| 6 | `Searchable` implementations | `matches()` on all 5 model classes | Accepted. I checked edge cases like empty lists and null collections. |
| 6 | Admin CRUD menus | Sub-menus for player, team, match management | Accepted. I adjusted input validation and added range checks. |
| 6 | Player self-profile edit | Edit level, win rate, owned heroes | Accepted. I added the 0–100 win rate validation myself. |
| 7 | Combat simulation | Turn-based battle with damage, crit, dodge | Accepted. I adjusted the damage formula and added the GUI option. |
| 7 | Tournament engine | Random 1v1 matches across all heroes, export to text file | Accepted. I tested it and confirmed the report file is generated correctly. |

---

## 3. Testing / Reviewer Agent

**Role:** Find bugs, suggest edge cases, and review code quality.

| Stage | Task | AI Output | My Decision |
|-------|------|-----------|-------------|
| 5 | Ranking edge cases | Noted that `usageCount` and `winRateContribution` are zero in initial data | Expected — the initial dataset has no gameplay history. The ranking still sorts alphabetically when all scores are 0. |
| 6 | File I/O validation | Checked that all model classes implement `Serializable` | Confirmed. `Person`, `Hero`, `Equipment`, `Team`, `MatchRecord` all implement it. |
| 6 | Compilation check | Verified the project compiles with zero errors | I ran `javac -d bin -sourcepath src` and confirmed. |
| 6 | Test case generation | Drafted 10 test cases covering login, sorting, search, CRUD, persistence | I expanded this to 16 tests and added more edge cases. |

---

## Human Decisions Log

I made all final architectural and design decisions. Key manual changes:

| Decision | Why I made it |
|----------|---------------|
| Made `Person` abstract | The AI generated it as concrete. An abstract superclass is better OOP because `Person` itself should not be instantiated. |
| Admin lookup before Player in login | Admin IDs use "A00x" prefix and Player IDs use "P00x". Checking admin first prevents any accidental collision. |
| Added `getMatchRecord()` and `removeMatchRecord()` | The AI missed match CRUD in the first pass. I noticed the gap during Stage 6 and added them. |
| Win rate validation (0–100 range) | The AI had no boundary check. I added it so users cannot enter negative or >100 win rates. |
| Auto-save after every admin mutation | The AI generated the save/load service but did not wire it into the menus. I added `fileStorage.saveData(gdm)` manually after every add/update/delete. |
| Git commit prefixes | I chose the prefix system (`[Human]`, `[AI-Architect]`, etc.) to make the history clear for the marker. |
| Menu option numbering | I chose 10 options for Player Menu and 14 for Admin Menu to fit all required features plus the bonus arena. |
| Added GUI combat option | I wanted to try the extra credit GUI feature, so I added a `JOptionPane` version of the combat simulator. |
| Added automated tournament | I thought it would be a good way to demonstrate file writing and random simulation together. |
