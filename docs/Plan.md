# AI-Assisted Honor of Kings Information Management System

## 1. Project Goal
This system manages players, heroes, equipment, teams, and match records for Honor of Kings.
* **Users:** Two roles — **Player** (regular user who can view data and edit own info) and **Admin** (full data-management privileges with full control).
* **Type:** The application is console-based, with optional extra features (data persistence, combat simulation, GUI) after core requirements are stable.

---

## 2. Requirement Analysis
| Feature                  | Implementation Approach                                      |
| :----------------------- | :----------------------------------------------------------- |
| **Player Lookup**        | Search by name/ID; display team, level, owned heroes, equipped items. |
| **Team Overview**        | Search by ID/name; show members, average level, total matches, win rate, top player. |
| **Hero Details**         | Search by name; show type, base stats, compatible equipment, players who own hero. |
| **Equipment Statistics** | Rank by usage count and custom score (usage * winRateContribution). Formula documented. |
| **Match History**        | Last N matches for player/team: opponent, date, result, heroes picked, win/loss record, pick rate. |
| **Leaderboard**          | Top X players by win rate, level, matches, or custom score; ties broken alphabetically. |
| **Data Management**      | Admin: add/edit/delete players, heroes, equipment, teams, matches. Player: edit own info. |
| **Authentication**       | Login/logout with roles; role-based menu.                    |

---

## 3. Java Concepts Used
* **Inheritance:** `Person` abstract class, `Player` and `Admin` extend `Person`.
* **Interfaces:** `Serializable` for file I/O; custom `Searchable` interface with `matches(String keyword)`.
* **Polymorphism:** Method overloading in search services; `ArrayList<Person>` for login list.
* **Collections:** `HashMap<String, Player>` for fast lookup by ID; `HashMap<String, Hero>` by name; `ArrayList` for ordered lists.
* **Exception handling:** `try-catch` for file I/O, number format, invalid input.
* **File I/O:** `ObjectInputStream`/`ObjectOutputStream` for data persistence (`.ser` file).
* **Enums:** `HeroType` (WARRIOR, MAGE, ASSASSIN, TANK, SUPPORT, MARKSMAN, JUNGLER), `MatchResult` (WIN, LOSS, DRAW).

---

## 4. Class Design
### Enums
* **`HeroType`**: WARRIOR, MAGE, ASSASSIN, TANK, SUPPORT, MARKSMAN, JUNGLER.
* **`MatchResult`**: WIN, LOSS, DRAW.

### Interfaces
* **`Searchable`**: defines `boolean matches(String keyword)`.

### Model Classes
* **`Person` (abstract, implements Serializable):** id, name, password.
* **`Player` extends Person:** level, winRate, teamId, ownedHeroes (List of hero name strings for safer serialization).
* **`Admin` extends Person:** role.
* **`Hero` (implements Serializable):** name, type (HeroType), baseStats (Map), compatibleEquipment (List of equipment name strings instead of raw object references).
* **`Equipment` (implements Serializable):** name, type, statBonus, usageCount, winRateContribution.
* **`Team` (implements Serializable):** teamId, name, memberIds (List of player ID strings), totalMatches, wins.
* **`MatchRecord` (implements Serializable):** matchId, date (String), teamA, teamB, winner, result (MatchResult), heroPicks (Map of playerId→heroName).

### Service Classes
* **`GameDataManager`:** central in-memory storage (`Map<String, Player>`, `Map<String, Hero>`, etc.) + CRUD operations.
* **`AuthenticationService`:** login, logout, current session, role checking.
* **`SearchService`:** lookup methods for players, heroes, teams, equipment.
* **`RankingService`:** leaderboard generation with tie-breaking.
* **`FileStorageService`:** save/load `GameDataManager` to/from `data.ser` via serialization.

### Utility Classes
* **`InputHelper`:** safe integer/string input with validation and retry.
* **`DataInitializer`:** creates initial hardcoded dataset.

### Entry Point
* **`HonorOfKingsApp` (Main):** main menu loop, dispatches to services based on authenticated role.

---

## 5. UML Draft Specification

### Class Hierarchy (ASCII)

```
                                <<Serializable>>
                                    Person (abstract)
                                  /                  \
                              Player               Admin
  +--------------------------+   |                     |
  | level: int               |   |               +------------+
  | winRate: double          |   |               | role: String|
  | teamId: String           |   |               +------------+
  | ownedHeroes: List<String>|   |
  | equippedItems: Map       |   |
  +--------------------------+   |

  <<Serializable>> + <<Searchable>>
  +--------+  +-----------+  +-------+  +-------------+
  |  Hero  |  | Equipment |  | Team  |  | MatchRecord |
  +--------+  +-----------+  +-------+  +-------------+
  | name   |  | name      |  |teamId |  | matchId     |
  | type   |  | type      |  | name  |  | date        |
  |baseStats| | statBonus |  |memberIds| | teamA       |
  |compatible| | usageCount|  |totalM |  | teamB       |
  |Equipment| | winRateC..|  | wins  |  | winner      |
  +--------+  +-----------+  +-------+  | result      |
                                         | heroPicks   |
                                         +-------------+
```

### Associations

```
Team "1" ──aggregates──> "*" Player  (via memberIds list of player ID strings)
MatchRecord ──references──> Team      (via teamA / teamB fields)
Player "1" ──owns──> "*" Hero        (via ownedHeroes list of hero name strings)
Player "1" ──equips──> "*" Equipment (via equippedItems map: heroName → List<Equipment>)
Hero "1" ──compatible──> "*" Equipment (via compatibleEquipment list)
```

### Service Layer Architecture

```
HonorOfKingsApp (Main Controller)
         │
         ├──► AuthenticationService
         │       ├── login(id, password)
         │       ├── logout()
         │       ├── isAdmin()
         │       └── getCurrentUser()
         │
         ├──► SearchService
         │       ├── findPlayerByName(name)
         │       ├── findPlayerById(id)
         │       ├── findHeroByName(name)
         │       ├── findTeamByName(name)
         │       ├── findTeamById(id)
         │       └── search(keyword)
         │
         ├──► RankingService
         │       ├── getPlayerRanking(sortMode)
         │       ├── countMatchesForPlayer(id)
         │       └── getEquipmentRanking()
         │
         ├──► ExtraFeaturesService
         │       ├── recommendEquipment(hero)
         │       ├── simulateCombat(h1, h2)
         │       ├── simulateGraphicalCombat(h1, h2)
         │       └── runAutomatedTournament()
         │
         ├──► FileStorageService
         │       ├── saveData(GameDataManager)
         │       └── loadData()
         │
         └──► GameDataManager (Central Data Store)
                 ├── Map<String, Player>   players
                 ├── Map<String, Admin>    admins
                 ├── Map<String, Hero>     heroes
                 ├── Map<String, Equipment> equipment
                 ├── Map<String, Team>     teams
                 └── List<MatchRecord>     matches
```

---

## 6. Data Design
Initial dataset (hardcoded in `DataInitializer`):

| Entity            | Count |
| :---------------- | :---: |
| **Players**       |  15   |
| **Heroes**        |  18   |
| **Equipment**     |  22   |
| **Teams**         |   3   |
| **Match records** |  11   |

* **Storage:** `FileStorageService` uses `ObjectOutputStream`/`ObjectInputStream` to serialize `GameDataManager` to/from `data.ser`. For extra credit, JSON may be used.

---

## 7. AI Usage Plan
Three AI agent roles (can be same tool, different roles):
* **Architect Agent:** class design, UML, module planning.
* **Implementation Agent:** generate specific methods (ranking formula, search filter, file I/O boilerplate).
* **Testing/Reviewer Agent:** find bugs, suggest test cases, review code quality.
* **Notice:** AI will not write entire project. I will manually integrate, modify, and understand every line.

---

## 8. Prompt Strategy
### Principles
* **Be specific:** include existing class names and desired behaviour.
* **Ask for explanations:** not just code.
* **Request edge case handling:** handle null inputs or exceptions.

### Example prompts
* **Architect:** *"I have Person abstract class with Player and Admin. Suggest a collection design for fast lookup by name and ID. Do not write full code."*
* **Implementation:** *"Using the Player class below (level, winRate), implement a method that returns top 5 players by custom score = (winRate * 100) + level. Handle ties by alphabetical name."*
* **Reviewer:** *"Review this RankingService class for null pointer risks, collection efficiency, and correct tie-breaking."*

* **Verification:** Compile and test every AI-generated snippet before committing. Modify to fit codebase.

---

## 9. Development Timeline
| Stage | Work                                                         |
| :---: | :----------------------------------------------------------- |
| **1** | ✅ **Done** — Read requirements, create repo, write plan.md   |
| **2** | Architect Agent feedback; implement enums, model classes, DataInitializer |
| **3** | Implement InputHelper, main menu, basic search (Player/Team/Hero lookup) |
| **4** | Implement authentication and role-based menu                 |
| **5** | Implement ranking, equipment stats, match history            |
| **6** | Implement admin CRUD operations (add/edit/delete)            |
| **7** | Testing Agent to find bugs; fix and commit                   |
| **8** | Implement file persistence (save/load)                       |
| **9** | Documentation (test-cases.md, reflection.md), final testing  |

* **Total planned commits:** ~20 (minimum 12 required).

---

## 10. Testing Plan
| Test ID | Function                                |
| :-----: | :-------------------------------------- |
| **T01** | Player Lookup                           |
| **T02** | Player Lookup (not found)               |
| **T03** | Team Overview                           |
| **T04** | Hero Details                            |
| **T05** | Equipment Statistics                    |
| **T06** | Match History                           |
| **T07** | Leaderboard                             |
| **T08** | Admin add player                        |
| **T09** | Admin delete player                     |
| **T10** | Admin edit hero                         |
| **T11** | Authentication & Role-based Menu Access |
| **T12** | Data persistence (Save/Load .ser File)  |
| **T13** | Graphical GUI Dialog Combat            |
| **T14** | Automated Tournament and TXT Export     |
| **T15** | Match History Aggregate Stats           |
| **T16** | Player Equipped Items Display           |

* *Record actual vs expected in `docs/test-cases.md`.*

---

## 11. Risk Analysis
| Risk                          | Mitigation                                                   |
| :---------------------------- | :----------------------------------------------------------- |
| **AI generates buggy code**   | Test immediately; ask AI to explain logic; debug manually.   |
| **Over-reliance on AI**       | Write key algorithms (ranking tie-breaking) myself. Use AI only for boilerplate. |
| **Insufficient Git commits**  | Commit after every small working change. Use prefixes (`[Human]`, `[AI-Architect]`, etc.). |
| **Missing required classes**  | Keep checklist from coursework Appendix A.                   |
| **File I/O corruption**       | Use backup; handle exceptions with user-friendly messages.   |
| **Insufficient initial data** | `DataInitializer` creates hardcoded data meeting minimum counts. |

---

## 12. Final Reflection
Full reflection with all 10 Q&A answers is documented in `ai/reflection.md`.

Key learnings from this project:
* Custom Comparators and sorting — understanding how `compare()` return values work.
* HashMap vs ArrayList — why HashMap is better for ID-based lookups.
* Serialization — how Java object serialization works with `serialVersionUID`.
* AI as a tool — AI saved time on boilerplate but required careful review of every generated line.

---

* **Date of plan:** 2026-06-05
* **Student name:** ZhaoZixuan
* **Git repository initialised:** Yes



