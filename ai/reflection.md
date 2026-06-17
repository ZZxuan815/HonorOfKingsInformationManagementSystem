# AI-Assisted Development Reflection

**Student:** ZhaoZixuan  
**Project:** Honor of Kings Information Management System  
**Date:** 2026-06-10

---

## Q1: Which AI tools or models did you use?

I used **DeepSeek via opencode** for the entire project. I did not switch between different AI tools; instead, I assigned different roles to the same model (Architect, Implementation, and Reviewer) to get different types of help.

## Q2: Which prompt was the most useful? Why?

The most useful prompt was when I asked for the **ranking comparator** with a specific formula: *"sorted by (winRate*100)+level descending, ties broken alphabetically by name."* The AI gave me exactly the `Comparator<Player>` I needed, and it compiled on the first try. It was useful because I provided the exact formula and the existing class structure, so the AI had enough context to generate correct code.

## Q3: Which AI-generated suggestion was wrong, incomplete, or misleading?

The AI once suggested using `List.of()` to create immutable lists for `ownedHeroes`. This works in Java 9+, but my course environment might be Java 8. I caught this during compilation and switched to `Arrays.asList()` instead. The AI also initially forgot to add `getMatchRecord()` and `removeMatchRecord()` methods to `GameDataManager` when I asked for "CRUD for all entities." I had to prompt again specifically for match record operations.

## Q4: How did you check whether AI-generated code was correct?

My process was:
1. **Compile first** — I ran `javac` immediately after adding AI-generated code. About 10% of the time there were missing imports or typos.
2. **Run and print debug** — I added `System.out.println()` to check data flow, like verifying that `GameDataManager` had the right number of players after initialization.
3. **Edge case testing** — I manually tested empty inputs, duplicate IDs, and invalid passwords to see if the code handled them gracefully.
4. **Read the code line by line** — I made sure I understood what every line did before keeping it.

## Q5: What bugs did you fix yourself instead of asking AI to fix?

- **Win rate validation:** The AI-generated profile edit code accepted any number for win rate. I added a 0–100 range check myself.
- **Auto-save integration:** The AI generated `FileStorageService` but did not connect it to the menu handlers. I manually added `fileStorage.saveData(gdm)` after every admin CRUD operation.
- **Menu option numbering:** The AI suggested 5 options for the Player Menu, but I wanted 8 (including the bonus arena feature). I redesigned the menu structure myself.
- **Person abstract class:** The AI initially generated `Person` as a concrete class. I changed it to `abstract` myself to enforce better OOP design.

## Q6: What Java concept did you understand better after using AI?

**Custom Comparators and sorting.** Before this project, I knew `Collections.sort()` existed but I was not confident writing my own `Comparator`. Seeing the AI generate the player ranking comparator — and then manually verifying the descending vs ascending logic — helped me understand how `compare()` return values (-1, 0, 1) actually work. I also understood `HashMap` vs `ArrayList` better because the AI explained why `HashMap<String, Player>` is better for ID lookups than looping through a list.

## Q7: What Java concept are you still unsure about?

**Serialization versioning.** I know `serialVersionUID` prevents errors when the class changes, but I am not fully clear on what happens if I add or remove a field and the old `data.ser` file still exists. I used `serialVersionUID = 1L` everywhere, which worked for this project, but I would need to read more about how Java handles class evolution during serialization.

## Q8: Did AI make the project easier, harder, or both? Explain.

**Both.**

- **Easier:** AI saved me a lot of time on boilerplate code — getters, setters, `toString()`, basic CRUD methods, and file I/O try-with-resources blocks. It also helped me structure the menu flow when I was stuck on how to organize the login loop.
- **Harder:** I spent extra time reviewing AI output to make sure it was not over-engineered. Sometimes the AI suggested design patterns (like factories or builders) that were unnecessary for a coursework project. I had to simplify the code back to what the requirements actually asked for.

Overall, AI was a useful assistant, but it did not replace thinking. The time saved on typing was partly spent on reviewing and correcting.

## Q9: Which parts of the final project were mainly written by you?

- **The overall architecture and menu flow** — I designed the menu structure, option numbering, role routing, and feature layout.
- **All admin CRUD sub-menus** (hero management, equipment management, player management, team management, match management).
- **The `DataInitializer`** with all the hardcoded game data — heroes, equipment, players, teams, and match records.
- **Auto-save logic** — connecting `FileStorageService` to every mutation point.
- **Test cases** — I wrote all 16 test cases based on what I actually tested.
- **Plan.md, prompts.md, agent-log.md, and this reflection** — AI helped format, but the content and decisions are mine.
- **Core view/menu methods** — `viewMatchHistory`, `viewHeroDetails`, `viewPlayerLeaderboard`, and the leaderboard sorting submenu were refined with AI assistance, and I manually integrated and verified their output.

## Q10: Which parts were mainly generated or heavily assisted by AI?

- **Model class skeletons** — `Person`, `Player`, `Admin`, `Hero`, `Equipment`, `Team`, `MatchRecord`. AI generated the fields, getters, setters, and `toString()`. I reviewed and adjusted (e.g., made `Person` abstract).
- **`GameDataManager`** — AI generated all the `add/get/remove/getAll` methods using `HashMap` and `ArrayList`.
- **`InputHelper`** — AI generated the static input validation methods with retry loops.
- **`AuthenticationService`** — AI generated the login/logout/role-check logic.
- **`RankingService`** — AI generated the comparators. I verified the formulas.
- **`FileStorageService`** — AI generated the serialization save/load code.
- **`Searchable` interface and `SearchService`** — AI generated the `matches()` methods and the global search loop.
- **Extra features** — AI helped with the combat simulation damage formula and the tournament report formatting.

I estimate about **70% of the model and service layer** was AI-generated (with my review), while **100% of the application layer** (menus, user interaction, data initialization) was written by me.
