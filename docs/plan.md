# AI-Assisted Honor of Kings Information Management System

## 1. Project Goal
This system manages players, heroes, equipment, teams, and match records for Honor of Kings.
There are two roles: Player (view data, edit own info) and Admin (full control). It's a console app with some extra stuff like combat simulation and GUI dialogs.

---

## 2. Requirement Analysis

| Feature | How I plan to do it |
|---------|---------------------|
| Player Lookup | Search by name or ID, show team, level, owned heroes |
| Team Overview | Search by ID or name, show members, average level, win rate |
| Hero Details | Search by name, show type, stats, compatible equipment |
| Equipment Stats | Rank by usage count times win rate contribution |
| Match History | Last N matches for a player or team, aggregate stats |
| Leaderboard | Sort by custom score, win rate, level, or matches played |
| Data Management | Admin can add/edit/delete everything, Player edits own profile |
| Authentication | Login with ID/password, different menus for each role |

---

## 3. Java Concepts Used
Inheritance: Person is abstract, Player and Admin extend it.
Interfaces: Searchable interface with matches() method, plus Serializable.
Polymorphism: Using instanceof to check Admin vs Player, method overrides.
Collections: HashMap for fast lookups, ArrayList for ordered lists.
Exception handling: try-catch for file I/O, bad number input, invalid enum values.
File I/O: ObjectInputStream/ObjectOutputStream to save and load data.
Enums: HeroType has 7 types, MatchResult has WIN/LOSS/DRAW.

---

## 4. Class Design

### Model Classes
Person (abstract): id, name, password. Implements Serializable.
Player extends Person: level, winRate, teamId, ownedHeroes (List of hero names), equippedItems (Map of hero -> items).
Admin extends Person: role string.
Hero: name, type (HeroType), baseStats (Map), compatibleEquipment (List of equipment names).
Equipment: name, type, statBonus, usageCount, winRateContribution.
Team: teamId, name, memberIds (List of player IDs), totalMatches, wins.
MatchRecord: matchId, date, teamA, teamB, winner, result (MatchResult), heroPicks.

### Service Classes
GameDataManager stores everything in HashMaps and ArrayLists. Has all CRUD methods.
AuthenticationService handles login/logout, tracks current user, checks if admin.
SearchService finds players, heroes, teams by name or ID.
RankingService sorts players and equipment with custom comparators.
FileStorageService saves and loads GameDataManager via serialization to data.ser.
ExtraFeaturesService has combat simulation, equipment recommendations, tournament.

### Utility
InputHelper: safe input reading with validation loops.
DataInitializer: creates all the initial heroes, players, equipment, teams, matches.

### Main
HonorOfKingsApp: login loop, dispatches to AdminMenu or PlayerMenu based on role.

---

## 5. Class Hierarchy

Person is the superclass. Player and Admin extend it. Hero, Equipment, Team, MatchRecord are all separate model classes that implement Serializable and Searchable.

Main app -> AuthenticationService (login) -> then either AdminMenu or PlayerMenu. Those menus call SearchService, RankingService, ExtraFeaturesService, and FileStorageService.

---

## 6. Data Design
Initial dataset is hardcoded in DataInitializer:

15 players, 18 heroes, 22 equipment items, 3 teams, 11 match records.

I save data with Java serialization to data.ser using ObjectOutputStream/ObjectInputStream.

---

## 7. AI Usage Plan
I used three AI agent roles with the same tool (DeepSeek via opencode):
- Architect: helped with class design and planning
- Implementation: generated specific methods and boilerplate
- Reviewer: checked for bugs and suggested test cases

I didn't let AI write everything. Every line of generated code was reviewed, compiled, and tested before I kept it.

---

## 8. Prompt Strategy
I tried to be specific in my prompts, like including class names and the exact formula I wanted. I asked for explanations when I didn't understand something. I also asked AI to handle edge cases like null inputs and empty lists.

Some example prompts:
- "I have Person abstract class with Player and Admin. Suggest a collection design for fast lookup by name and ID."
- "Using the Player class below, implement a method that returns top 5 players by custom score = (winRate * 100) + level. Handle ties by alphabetical name."
- "Review this RankingService for null pointer risks and correct tie-breaking."

---

## 9. Development Timeline

| Stage | What I did |
|-------|------------|
| 1 | Read requirements, created repo, wrote this plan |
| 2 | Implemented enums, model classes, DataInitializer |
| 3 | InputHelper, main menu, basic search |
| 4 | Authentication and role-based menus |
| 5 | Ranking, equipment stats, match history |
| 6 | Admin CRUD operations |
| 7 | Testing, bug fixing |
| 8 | File persistence (save/load) |
| 9 | Documentation and final testing |

I did about 20 commits total.

---

## 10. Testing Plan
T01-T16 cover: login, search, ranking, equipment stats, CRUD operations, persistence, combat, tournament, match history, equipped items. See test-cases.md for detailed results.

---

## 11. Risk Analysis
AI might generate buggy code. I tested everything right away and debugged manually when needed.

I tried not to rely on AI too much. For important stuff like ranking tie-breaking I wrote it myself or at least traced through the logic.

Git commits were a problem early on because I kept forgetting. I started using commit prefixes to stay organized.

File I/O could corrupt if something goes wrong during save. I added exception handling so at least it prints an error instead of crashing.

Initial data might not be enough for testing. I made sure to add at least 15 players and 18 heroes so there's enough variety.

I hope AI doesn't mess up lol. But I checked every generated line so it should be fine.

---

## 12. Final Reflection
Full reflection with 10 Q&A answers is in ai/reflection.md.

I learned a lot about HashMap and Comparator during this project. It was actually pretty hard to figure out the sorting logic at first. The AI helped generate the comparator code but I still had to trace through it myself to make sure descending vs ascending was right. I also understand serialization better now, though I'm still not 100% sure what happens when class fields change after the file is saved.

---

* **Date of plan:** 2026-06-05
* **Student name:** ZhaoZixuan
* **Git repository initialised:** Yes
