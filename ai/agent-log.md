# Agent Division of Labour Log

## Overview
Three AI agent roles were used throughout the project, directed by the Human developer who retained full architectural control. All AI output was manually reviewed before integration.

---

## 1. Architect Agent

**Role:** OOP structure design, class hierarchy, data flow, menu layout.

| Stage | Task | AI Output | Human Decision |
|-------|------|-----------|----------------|
| 2 | Person → Player / Admin inheritance hierarchy | Abstract `Person` with `id`, `name`, `password`; `Player` adds `level`, `winRate`, `teamId`, `ownedHeroes`; `Admin` adds `role` | Accepted. Adjusted `Person` to abstract. |
| 2 | Enum design for HeroType and MatchResult | Generated 7 HeroType values (WARRIOR, MAGE, ASSASSIN, TANK, SUPPORT, MARKSMAN, JUNGLER) and 3 MatchResult values (WIN, LOSS, DRAW) | Accepted as-is. |
| 3 | Collection design for GameDataManager | Suggested `HashMap<String, Player>`, `HashMap<String, Hero>`, `HashMap<String, Equipment>`, `HashMap<String, Team>`, `List<MatchRecord>` | Accepted. Matched the requirement for O(1) ID lookups. |
| 4 | Login + role-based menu text flow | Full console text-flow: Login → Role Dispatch → Player Menu (5 read-only options) → Admin Menu (Hero/Equipment sub-menus with CRUD) | Accepted as blueprint. Implemented exactly as designed. |

---

## 2. Implementation Agent

**Role:** Code generation for CRUD operations, search, sorting, file I/O, menu handlers.

| Stage | Task | AI Output | Human Decision |
|-------|------|-----------|----------------|
| 3 | InputHelper utility | Static `readInt()`, `readIntRange()`, `readString()` with retry loops and validation | Accepted. |
| 3 | GameDataManager CRUD | All `add/get/remove/getAll` methods for 6 entity types | Accepted. |
| 3 | SearchService | 5 find methods (player by name/ID, hero by name, team by name/ID) | Accepted. |
| 4 | AuthenticationService | `login()`, `logout()`, `isAdmin()`, `getCurrentUser()` | Accepted. |
| 5 | RankingService | `getPlayerRanking()` and `getEquipmentRanking()` with custom Comparators | Accepted. Tie-breaking verified manually. |
| 6 | FileStorageService | Save/load via `ObjectOutputStream`/`ObjectInputStream` to `data.ser` | Accepted. Human added auto-save integration. |
| 6 | Searchable implementations | `matches()` on all 5 model classes | Accepted. |
| 6 | Full Admin CRUD menus | Player Management, Team Management, Match Management sub-menus | Accepted. Human adjusted input validation. |
| 6 | Player self-profile edit | Edit level, winRate, ownedHeroes | Accepted. Added range validation for winRate. |

---

## 3. Testing / Reviewer Agent

**Role:** Bug finding, edge case verification, file persistence validation.

| Stage | Task | AI Output | Human Decision |
|-------|------|-----------|----------------|
| 5 | Ranking edge cases | Identified that Equipment `usageCount` and `winRateContribution` are always 0 in initial data | Noted as expected — data would be populated in real usage. |
| 6 | File I/O validation | Validated that serialization works with all model classes implementing `Serializable` | Confirmed `Person`, `Hero`, `Equipment`, `Team`, `MatchRecord` all implement `Serializable`. |
| 6 | Compilation check | Full project compiles with zero errors | Verified by running `javac -d bin -sourcepath src`. |
| 6 | Test case generation | Drafted 10 test cases (T01-T10) covering login, sorting, search, profile edit, CRUD, persistence | Human expanded edge cases. |

---

## Human Decisions Log

The Human developer made all final decisions. Key manual interventions:

| Decision | Rationale |
|----------|-----------|
| Made `Person` abstract | AI initially did not mark it abstract. Human enforced OOP correctness. |
| Admin lookup before Player in login | Prevents ID collision; admin IDs use "A00x" prefix. |
| Added `getMatchRecord()` and `removeMatchRecord()` | AI initially missed match CRUD methods — Human prompted for them in Stage 6 plan. |
| WinRate validation (0-100 range) | Added boundary check in profile edit; AI had no validation. |
| Auto-save after every admin mutation | Human added `fileStorage.saveData(gdm)` calls manually after each CRUD handler. |
| Git history prefixes | Human directed the exact prefix mapping for all 18 commits. |
| Menu option numbering | Human chose 8 options for Player Menu and 12 for Admin Menu. |
| Section 10 Extra Credits | Human ordered the deployment of Section 10 Extra Credits (Combat Simulation + Recommendation Engine) to maximize grading potential. |
