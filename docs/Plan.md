# AI-Assisted Honor of Kings Information Management System

1. 项目目标：阐述系统功能及用户群体。2. 需求分析：列出所有必需功能，并说明系统如何实现各项功能。3. 使用的Java概念：说明继承、接口、多态性、集合、异常处理、文件输入输出以及枚举的具体应用场景。4. 类设计：描述每个主要类及其功能职责。5. UML草图：包含UML关系图或文字描述的UML方案图。6. 数据设计：说明初始数据集构成及数据存储方式。7. AI应用方案：明确使用哪些AI智能体及其具体应用场景。8. 提示策略：说明提示语编写规范及AI输出结果验证方法。9. 开发时间表：将项目划分为多个阶段。10. 测试计划：为各核心功能制定测试用例。11. 风险分析：说明潜在风险及应对措施。12. 最终反思部分：预留实施后的总结思考空间。

## 1. Project Goal

This system manages players, heroes, equipment, teams, and match records for Honor of Kings.  
**Users**:  (Player represents a game player).

**Admin**: (represents a user with data-management permission )
The application is console-based, with optional extra features (data persistence, combat simulation, GUI) after core requirements are stable.

## 2. Requirement Analysis

| Feature              | Implementation Approach                                      |
| -------------------- | ------------------------------------------------------------ |
| Player Lookup        | Search by name/ID; display team, level, owned heroes, equipped items. |
| Team Overview        | Search by ID/name; show members, average level, total matches, win rate, top player. |
| Hero Details         | Search by name; show type, base stats, compatible equipment, players who own hero. |
| Equipment Statistics | Rank by usage count and custom score (usage * winRateContribution). Formula documented. |
| Match History        | Last N matches for player/team: opponent, date, result, heroes picked, win/loss record, pick rate. |
| Leaderboard          | Top X players by win rate, level, matches, or custom score; ties broken alphabetically. |
| Data Management      | Admin: add/edit/delete players, heroes, equipment, teams, matches. Player: edit own info. |
| Authentication       | Login/logout with roles; role-based menu.                    |

## 3. Java Concepts Used

- Inheritance: Person abstract class, Player and Admin extend Person.
- Interfaces: Serializable for file I/O; custom Searchable interface.
- Polymorphism: Method overloading in search services; ArrayList<Person> for login.
- Collections: HashMap for fast lookup (player by ID, hero by name); ArrayList for lists.
- Exception handling: try-catch for file I/O, number format, invalid input.
- File I/O: ObjectInputStream/ObjectOutputStream for data persistence.
- Enums: HeroType (WARRIOR, MAGE, ASSASSIN, etc.), MatchResult (WIN, LOSS).

## 4. Class Design

- Person (abstract): id, name, password.
- Player extends Person: level, winRate, teamId, ownedHeroes (List<Hero>).
- Admin extends Person: role.
- Hero: name, type, baseStats, compatibleEquipment (List<Equipment>).
- Equipment: name, type, statBonus, usageCount, winRateContribution.
- Team: teamId, name, memberIds (List<String>), totalMatches, wins.
- MatchRecord: matchId, date, teamA, teamB, winner, heroPicks (Map<String, String> playerId->heroName).
- GameDataManager: central storage, CRUD operations.
- AuthenticationService: login, logout, session, role checking.
- SearchService: lookup methods.
- RankingService: leaderboard generation.
- FileStorageService: save/load data.
- InputHelper: safe input handling.
- DataInitializer: creates initial dataset (>=10 players, >=15 heroes, >=20 equipment, >=3 teams, >=10 match records).

## 5. UML Draft (Text-based)

=== UML Class Diagram (Simplified) ===

[Person] (abstract)
  - id: String
  - name: String
  - password: String
    △
    |
    +-- [Player]
    |     - level: int
    |     - winRate: double
    |     - teamId: String
    |     - ownedHeroes: List<Hero>
    |
    +-- [Admin]
          - role: String

[Hero]
  - name: String
  - type: HeroType (enum)
  - baseStats: Map<String, Integer>
  - compatibleEquipment: List<Equipment>

[Equipment]
  - name: String
  - type: String
  - usageCount: int
  - winRateContribution: double

[Team]
  - teamId: String
  - name: String
  - memberIds: List<String>
  - totalMatches: int
  - wins: int

[MatchRecord]
  - matchId: String
  - date: LocalDate
  - teamA: String
  - teamB: String
  - winner: String
  - heroPicks: Map<String, String>

[GameDataManager]
  - players: Map<String, Player>
  - heroes: Map<String, Hero>
  - equipment: Map<String, Equipment>
  - teams: Map<String, Team>
  - matches: List<MatchRecord>
  + add/delete/edit methods

[AuthenticationService]  [SearchService]  [RankingService]
[FileStorageService]     [InputHelper]    [DataInitializer]

## 6. Data Design

Initial dataset (hardcoded in DataInitializer):
- Players: 10 (e.g., Li Bai, Diao Chan, Cao Cao) with levels 10-50, win rates 40%-75%.
- Heroes: 15 (e.g., Li Bai (Assassin), Diao Chan (Mage), Lu Bu (Warrior)).
- Equipment: 20 items (e.g., Shadow Blade, Armor of Thorns).
- Teams: 3 (Team A, Team B, Team C) each with 3-4 players.
- Match records: 10 matches with dates, results, hero picks.

Storage: Use serialization (ObjectOutputStream) to save/load GameDataManager to/from data.ser. For extra credit, JSON may be used.

## 7. AI Usage Plan

Three AI agent roles (can be same tool, different roles):
- Architect Agent: class design, UML, module planning.
- Implementation Agent: generate specific methods (ranking formula, search filter, file I/O boilerplate).
- Testing/Reviewer Agent: find bugs, suggest test cases, review code quality.

AI will not write entire project. I will manually integrate, modify, and understand every line.

## 8. Prompt Strategy

Principles:
- Be specific: include existing class names and desired behaviour.
- Ask for explanations, not just code.
- Request edge case handling.

Example prompts:
- "Architect: I have Person abstract class with Player and Admin. Suggest a collection design for fast lookup by name and ID. Do not write full code."
- "Implementation: Using the Player class below (level, winRate), implement a method that returns top 5 players by custom score = (winRate * 100) + level. Handle ties by alphabetical name."
- "Reviewer: Review this RankingService class for null pointer risks, collection efficiency, and correct tie-breaking."

Verification: Compile and test every AI-generated snippet before committing. Modify to fit codebase.

## 9. Development Timeline

| Stage | Work                                                 | Commits |
| ----- | ---------------------------------------------------- | ------- |
| 1     | Read requirements, create repo, write plan.md        | 1-2     |
| 2     | Architect Agent feedback; design classes manually    | 2-3     |
| 3     | Implement model classes and DataInitializer          | 3-4     |
| 4     | Implement menu, InputHelper, basic search            | 2-3     |
| 5     | Implement authentication and role-based menu         | 2       |
| 6     | Implement ranking, equipment stats, match history    | 3-4     |
| 7     | Implement admin CRUD operations                      | 2-3     |
| 8     | Testing Agent to find bugs; fix and commit           | 2-3     |
| 9     | Implement file persistence                           | 2       |
| 10    | Documentation, reflection, git export, final testing | 2-3     |

Total planned commits: ~20 (minimum 12 required).

## 10. Testing Plan

| Test ID | Function                  | Input                | Expected Output                           |
| ------- | ------------------------- | -------------------- | ----------------------------------------- |
| T01     | Player Lookup             | "Li Bai"             | Display team, level, heroes, equipment    |
| T02     | Player Lookup (not found) | "Unknown"            | Error message                             |
| T03     | Team Overview             | "Team A"             | Members, avg level, win rate, top player  |
| T04     | Hero Details              | "Diao Chan"          | Type, stats, compatible equipment, owners |
| T05     | Equipment Statistics      | (menu)               | Ranked list by usage count                |
| T06     | Match History             | Player "Li Bai", N=3 | Last 3 matches with details               |
| T07     | Leaderboard               | Top 5 by win rate    | Sorted list, ties broken by name          |
| T08     | Admin add player          | New player data      | Player added, appears in search           |
| T09     | Authentication            | wrong password       | Login denied                              |
| T10     | Data persistence          | Exit and restart     | Data unchanged                            |

Record actual vs expected in docs/test-cases.md.

## 11. Risk Analysis

| Risk                      | Mitigation                                                   |
| ------------------------- | ------------------------------------------------------------ |
| AI generates buggy code   | Test immediately; ask AI to explain logic; debug manually.   |
| Over-reliance on AI       | Write key algorithms (ranking tie-breaking) myself. Use AI only for boilerplate. |
| Insufficient Git commits  | Commit after every small working change. Use prefixes ([Human], [AI-Architect], etc.). |
| Missing required classes  | Keep checklist from coursework Appendix A.                   |
| File I/O corruption       | Use backup; handle exceptions with user-friendly messages.   |
| Insufficient initial data | DataInitializer creates hardcoded data meeting minimum counts. |

## 12. Final Reflection Placeholder

(To be completed after implementation – see reflection.md for full answers)

Summary of key learning:
- Which AI prompts were most/least useful.
- Bugs I fixed myself vs. AI fixed.
- Java concepts I now understand better (e.g., polymorphism with collections).
- How AI affected development speed and code quality.
- Parts written by me vs. AI-assisted.

---

Date of plan: 2026-06-05
Student name: ZhaoZixuan
Git repository initialised: Yes

