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

I just compiled everything right away after pasting AI output. Sometimes there were missing imports or small typos, so javac caught those. Then I ran the program with some print statements to see if data actually flowed through correctly. After that I tested edge cases like empty inputs, duplicate IDs, and wrong passwords. And I always read through the code before keeping it, just to make sure I understood what it was doing.

## Q5: What bugs did you fix yourself instead of asking AI to fix?

The AI's profile edit code didn't have any range check for win rate. I added a 0-100 validation myself.

The AI wrote `FileStorageService` but never actually connected it to the menus. I had to add `fileStorage.saveData(gdm)` after every admin operation by hand.

Another thing was the menu layout. The AI suggested 5 options for the Player Menu, but I wanted 8 including the bonus arena, so I redesigned the whole menu structure.

I also made `Person` abstract. The AI generated it as a concrete class, which didn't make sense since you should never instantiate a generic person directly.

## Q6: What Java concept did you understand better after using AI?

I finally got how Comparator works. Seeing the AI generate the ranking code, then manually checking if descending was actually descending and tie-breaking actually worked, it all clicked. The `compare()` return values (-1, 0, 1) made sense after I traced through a few examples. I also understood HashMap vs ArrayList better after the AI explained why HashMap is faster for ID lookups.

## Q7: What Java concept are you still unsure about?

**Serialization versioning.** I know `serialVersionUID` prevents errors when the class changes, but I am not fully clear on what happens if I add or remove a field and the old `data.ser` file still exists. I used `serialVersionUID = 1L` everywhere, which worked for this project, but I would need to read more about how Java handles class evolution during serialization.

## Q8: Did AI make the project easier, harder, or both? Explain.

Both. The AI saved a ton of time on boring stuff like getters, setters, toString(), and try-with-resources boilerplate. It also helped me figure out the menu loop structure when I was stuck.

On the other hand, I spent extra time reviewing AI output because it sometimes over-engineered things. A few times it suggested factories or builders, which were way too much for a coursework project. I had to strip it back to what the requirements actually asked for. So AI helped, but it wasn't a magic button. I still had to figure out most of the logic myself.

## Q9: Which parts of the final project were mainly written by you?

The overall menu structure and how features are organized. I decided what goes where, which menu has which options, and how the role routing works.

All the admin CRUD sub-menus for heroes, equipment, players, teams, and matches.

`DataInitializer` with all the hardcoded data. That took a while since there are 18 heroes, 22 equipment items, 15 players, 3 teams, and 11 match records, all cross-referenced.

Auto-save wiring. I had to connect `FileStorageService` to every mutation point manually because the AI just generated the service class and left it there.

All 16 test cases and their documentation.

Oh, and all the documentation files: Plan.md, prompts.md, agent-log.md, and this reflection. AI helped with formatting but the actual content is mine.

Core view methods like `viewMatchHistory`, `viewHeroDetails`, `viewPlayerLeaderboard` were refined with AI help but I integrated and tested everything manually.

## Q10: Which parts were mainly generated or heavily assisted by AI?

Model class skeletons like `Person`, `Player`, `Admin`, `Hero`, `Equipment`, `Team`, and `MatchRecord`. AI wrote the fields, getters, setters, and toString(). I went through and adjusted things where needed.

`GameDataManager` was mostly AI. All the add/get/remove/getAll methods using HashMap and ArrayList.

`InputHelper` came from AI with the retry loop logic.

`AuthenticationService` for login, logout, and role checking.

`RankingService` comparators, but I verified the formulas myself.

`FileStorageService` save and load code.

Also the `Searchable` interface and `SearchService` matches() methods.

For extra features, AI helped with the combat damage formula and tournament report formatting.

Most of the boring stuff was AI, the interesting parts were me.
