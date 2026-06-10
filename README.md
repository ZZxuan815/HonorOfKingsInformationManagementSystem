# AI-Assisted Honor of Kings Information Management System

## 1. Project Overview
This is a console-based Java application that manages game data for Honor of Kings. It stores information about players, heroes, equipment, teams, and match records. The system supports two user roles: **Admin** (full data management) and **Player** (view data and edit own profile). I built this for my Java OOP coursework and used AI tools to help with some coding tasks, which I documented throughout the project.

## 2. How to Run
You need **Java 8 or higher**. No external libraries are required.

### Compile
```bash
javac -d bin -sourcepath src src/main/HonorOfKingsApp.java
```

### Run
```bash
java -cp bin main.HonorOfKingsApp
```

## 3. Default Login Accounts

| ID | Password | Role | Name |
|:---|:---------|:-----|:-----|
| `A001` | `admin123` | Admin | AdminZhang |
| `A002` | `admin456` | Admin | AdminLi |
| `P001` | `pass123` | Player | Li Bai |
| `P002` | `pass456` | Player | Diao Chan |
| `P003` | `pass789` | Player | Cao Cao |
| `P004` | `pass111` | Player | Hou Yi |
| `P005` | `pass222` | Player | Lu Bu |
| `P006` | `pass333` | Player | Zhao Yun |
| `P007` | `pass444` | Player | Guan Yu |
| `P008` | `pass555` | Player | Han Xin |
| `P009` | `pass666` | Player | Ya Se |
| `P010` | `pass777` | Player | Mo Ye |

## 4. Implemented Features

### Core Features
- **Player Lookup:** Search by ID or name, view team, level, win rate, and owned heroes.
- **Team Overview:** Search by ID or name, view members, average level, total matches, win rate, and top player.
- **Hero Details:** Search by name, view type, base stats, compatible equipment, and players who own the hero.
- **Equipment Statistics:** Rank equipment by a custom score (usage count × win rate contribution).
- **Match History:** View match records with opponent, date, winner, and hero picks.
- **Leaderboard:** Top players sorted by a custom score (win rate × 100 + level), with alphabetical tie-breaking.
- **Data Management:** Admin can add, edit, and delete players, heroes, equipment, teams, and match records. Players can edit their own level, win rate, and owned heroes.
- **Authentication:** Login/logout with role-based menus.
- **Data Persistence:** All data is saved to `data.ser` using Java serialization and reloaded on restart.

### Extra Features (Bonus)
- **Combat Simulation:** Turn-based 1v1 battle between two heroes with random damage, critical hits (20% chance), and dodges (15% chance).
- **GUI Combat Mode:** Optional `JOptionPane` dialog version of the combat simulator with ASCII health bars.
- **Equipment Recommendation:** Suggests equipment for a hero based on its type (e.g., MAGE gets magic items).
- **Automated Tournament:** Simulates random 1v1 matches across all heroes and exports a ranked report to `tournament_report.txt`.

## 5. Java Concepts Used
- **Inheritance:** `Player` and `Admin` extend the abstract `Person` class.
- **Interfaces:** `Searchable` interface with `matches(String keyword)` implemented by all model classes.
- **Polymorphism:** `AuthenticationService` stores the current user as a `Person` and checks role with `instanceof`.
- **Encapsulation:** All fields are private with getters and setters.
- **Collections:** `HashMap` for fast ID/name lookups, `ArrayList` for ordered lists, custom `Comparator` for sorting.
- **Exception Handling:** `try-catch` for file I/O errors, `NumberFormatException` for invalid input, and input range validation.
- **File I/O:** `ObjectInputStream` and `ObjectOutputStream` for saving and loading `GameDataManager`.
- **Enums:** `HeroType` (WARRIOR, MAGE, ASSASSIN, TANK, SUPPORT, MARKSMAN, JUNGLER).

## 6. AI Usage Summary
I used **GPT-4o via opencode** as my AI assistant. I followed a multi-agent approach with three roles:
- **Architect Agent:** Helped design the class hierarchy and menu flow.
- **Implementation Agent:** Generated specific methods like CRUD operations, comparators, and file I/O code.
- **Testing/Reviewer Agent:** Helped find edge cases and draft test cases.

I never accepted AI output blindly. Every piece of code was reviewed, compiled, and tested before committing. All prompts, decisions, and reflections are recorded in the `ai/` folder.

## 7. Testing Summary
I tested the system manually with 14 test cases covering:
- Login authentication (valid and invalid)
- Player leaderboard sorting and tie-breaking
- Equipment statistics with empty data
- Global search (case-insensitive)
- Player self-profile editing
- Admin CRUD operations (add hero, delete player, duplicate detection)
- File persistence (save and reload after restart)
- Equipment recommendation
- Combat simulation (console and GUI modes)
- Automated tournament and report export

All 14 tests passed. Details are in `docs/test-cases.md`.

## 8. Known Limitations
- The GUI combat mode uses `JOptionPane`, which requires a desktop environment. It will not work in headless environments, but the console mode works everywhere.
- Equipment `usageCount` and `winRateContribution` start at zero in the initial dataset because there is no gameplay data feed. The ranking still works but all scores are 0.00 until admin manually updates these values.
- The match history feature currently shows all match records. Filtering by "last N matches for a specific player or team" is not yet implemented.
