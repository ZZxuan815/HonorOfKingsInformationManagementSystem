# Agent Division of Labour Log

## Overview
Three AI agent roles were used throughout the project, directed by me. I made all final decisions and reviewed every piece of code before using it.

---

## 1. Architect Agent

**Role:** Design the class hierarchy, choose collections, and plan the menu flow.

| Stage | Task | AI Output | My Decision |
|-------|------|-----------|-------------|
| 2 | Person -> Player / Admin inheritance | Suggested Person with id, name, password; Player adds level, winRate, etc. | Accepted. Made Person abstract. |
| 2 | Enum design | Generated 7 HeroType values and 3 MatchResult values | Seemed fine, kept it. |
| 3 | Collection design for GameDataManager | Suggested HashMap<String, Player>, HashMap<String, Hero>, etc. | Accepted. HashMap is faster than ArrayList for ID lookups. |
| 4 | Menu text flow | Drew a console login -> role dispatch -> menu structure | Mostly accepted but I changed some details and added more options. |

---

## 2. Implementation Agent

**Role:** Write specific Java methods and classes based on my instructions.

| Stage | Task | AI Output | My Decision |
|-------|------|-----------|-------------|
| 3 | InputHelper utility | Static readInt(), readIntRange(), readString() with retry loops | Accepted, worked fine. |
| 3 | GameDataManager CRUD | add/get/remove/getAll for all 6 entity types | Accepted but it missed match record methods. I added those myself. |
| 3 | SearchService | Find methods for player by name/ID, hero by name, team by name/ID | Accepted. |
| 4 | AuthenticationService | login(), logout(), isAdmin(), getCurrentUser() | Accepted. |
| 5 | RankingService | getPlayerRanking() and getEquipmentRanking() with Comparator | Accepted. I checked the tie-breaking logic manually. |
| 6 | FileStorageService | Save/load via ObjectOutputStream/ObjectInputStream to data.ser | Accepted. It didn't wire the save calls into menus though, so I did that. |
| 6 | Searchable implementations | matches() on all 5 model classes | Accepted. Checked edge cases. |
| 6 | Admin CRUD menus | Sub-menus for player, team, match management | Mostly accepted, adjusted some validation. |
| 6 | Player self-profile edit | Edit level, win rate, owned heroes | Accepted. Added win rate range check myself. |
| 7 | Combat simulation | Turn-based battle with damage, crit, dodge | Accepted. Tweaked the damage numbers. |
| 7 | Tournament engine | Random 1v1 matches, export to text file | Accepted, tested it and it worked. |

---

## 3. Testing / Reviewer Agent

**Role:** Find bugs, suggest edge cases, and review code quality.

| Stage | Task | AI Output | My Decision |
|-------|------|-----------|-------------|
| 5 | Ranking edge cases | Noted that usageCount and winRateContribution are zero | Expected, the dataset has no gameplay history so scores are 0. |
| 6 | File I/O validation | Checked that all model classes implement Serializable | Confirmed, they all do. |
| 6 | Compilation check | Verified the project compiles | It compiled. |
| 6 | Test case generation | Drafted 10 test cases | I expanded to 16 tests. |

---

## Human Decisions Log

I made all final architectural and design decisions. Key manual changes:

| Decision | Why I made it |
|----------|---------------|
| Made Person abstract | The AI had it as concrete. An abstract superclass makes more sense. |
| Admin lookup before Player in login | Admin IDs start with "A", players with "P". Checking admin first avoids collision. |
| Added getMatchRecord() and removeMatchRecord() | AI missed these in the first pass. |
| Win rate validation (0-100 range) | The AI didn't check boundaries. |
| Auto-save after every admin mutation | The AI wrote the save service but didn't call it anywhere. I wired it in. |
| Git commit prefixes | I felt like it. Makes the history clearer for the marker. |
| Menu option numbering | I wanted more features than the AI suggested, so I added extra options. |
| Added GUI combat | I felt like it. |
| Added automated tournament | I thought it would be cool. |
