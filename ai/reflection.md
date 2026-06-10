# AI-Assisted Development Reflection

**Student:** ZhaoZixuan  
**Project:** Honor of Kings Information Management System  
**Date:** 2026-06-10  

---

## Q1: How well did you understand the project requirements before starting?

I understood the core requirements thoroughly before beginning. The project specification (Plan.md) clearly defined two user roles (Player and Admin), the data entities (Heroes, Equipment, Teams, Match Records), and the required features (CRUD, search, ranking, file persistence). The key challenge was translating these abstract requirements into a working console-based Java application. I spent time mapping out the class hierarchy and data flow before writing any code, which prevented major rework later.

---

## Q2: What role did AI tools play in your development process?

AI tools (opencode with GPT-4o) served as assistants for code generation, architecture suggestions, and debugging. They were never used to make design decisions — those remained mine. Specifically:

- **Architecture:** AI suggested class structures and collection designs, which I reviewed and adjusted.
- **Implementation:** AI generated boilerplate code (getters/setters, CRUD methods, sorting comparators) that I then manually integrated.
- **Testing:** AI helped identify edge cases and draft test cases.
- **Documentation:** AI formatted the test case and reflection documents.

The project would have taken significantly longer without AI assistance for repetitive code generation, but the core logic and architecture decisions were mine.

---

## Q3: Which AI prompts were most effective?

The most effective prompts were those where I provided:
1. **Existing code context** — e.g., "Here is my Person class and GameDataManager. Write AuthenticationService that validates credentials against them." This gave the AI enough context to generate code that actually compiled.
2. **Specific formulas** — e.g., "sorted by (winRate*100)+level descending, ties broken alphabetically by name." The AI generated the exact Comparator I needed.
3. **Text-flow only requests** — e.g., "Show me the text layout for the main menu loop." This helped me visualize the UX before coding.

---

## Q4: Which AI prompts were least effective?

Prompts that were too vague or abstract produced unusable output:
- "Design the system" — gave generic Java advice without understanding my entity structure.
- "Fix bugs" without specifying the error — the AI guessed at problems that didn't exist.
- Prompts asking for "optimal" solutions — the AI over-engineered simple tasks with unnecessary design patterns.

The most important lesson: **be specific, provide context, and ask for explanations, not just code.**

---

## Q5: How would you rate the quality of AI-generated code?

Generally **good for boilerplate, requires review for logic**.

- **Good:** Getter/setter methods, constructor overloads, `toString()` overrides, basic CRUD operations, file I/O try-with-resources patterns. These are formulaic and the AI handled them perfectly.
- **Needs review:** Sorting comparators with tie-breaking (had to verify the descending vs ascending direction), input validation (AI often omitted range checks), and edge case handling (null checks on collections).

I estimate about **80% of AI-generated code was used as-is** — the remaining 20% required manual adjustment after review.

---

## Q6: What parts of the project did you write manually versus with AI assistance?

| Component | Written By |
|-----------|-----------|
| Project plan (Plan.md) | Human (me) |
| Class hierarchy (Person, Player, Admin) | AI + Human review |
| Enums (HeroType, MatchResult) | AI |
| GameDataManager CRUD | AI |
| InputHelper validation logic | AI |
| AuthenticationService login flow | AI |
| RankingService comparators | AI (Human verified formula) |
| FileStorageService serialization | AI |
| Searchable + SearchService | AI |
| **HonorOfKingsApp (main + menus)** | **Human (me)** — 787 lines of manual code |
| **Admin CRUD sub-menus** | **Human (me)** — all handlers written manually |
| **Menu structure & routing** | **Human (me)** — designed and implemented |
| Auto-save integration | Human (me) |
| Test cases | Human (me), AI-assisted formatting |
| Git history management | Human (me) |

The main menu loop and all user-facing logic were written manually. AI was used for data-layer and utility code.

---

## Q7: How did you debug issues during development?

My debugging process:
1. **Compilation errors first** — I always compiled before testing. The AI's code sometimes had missing imports or typos.
2. **Console print debugging** — I added `System.out.println()` statements to verify data flow (e.g., checking that GameDataManager had the right number of players after initialization).
3. **Serialization testing** — I ran the app, added data as Admin, exited, restarted, and verified the data persisted from `data.ser`.
4. **Edge case testing** — I manually tested: invalid login (wrong password), duplicate entity creation, empty list views, and tie-breaking in rankings.

AI helped identify edge cases I hadn't considered (e.g., what happens when Equipment `usageCount` and `winRateContribution` are both zero — the ranking still works, just all scores are 0).

---

## Q8: How did you use Git for version control?

I made **18 commits** following a structured prefix system:

| Prefix | Purpose | Count |
|--------|---------|-------|
| `[Human]` | Manual code, setup, routing | 5 |
| `[AI-Architect]` | AI-designed structures | 2 |
| `[AI-Implementation]` | AI-generated code | 8 |
| `[AI-Review]` | AI review/verification | 1 |
| `[Fix]` | Bug fixes | 1 |
| `[Docs]` | Documentation | 1 |

Each commit represents a single working change. Commits were made after every successfully compiled stage. The history was rewritten at the end to ensure clean, consistent prefixes for submission.

---

## Q9: What lessons did you learn from this project?

1. **Context is everything for AI prompts.** Providing existing class signatures and method names dramatically improved AI code quality.
2. **AI is good at patterns, bad at judgment.** The AI could generate CRUD methods perfectly but didn't know when to add validation or security checks.
3. **Plan before coding saves time.** The text-flow planning for menus took 15 minutes but saved hours of refactoring later.
4. **Test persistence early.** I tested file saving/loading early in Stage 6 and caught serialization issues (missing `Serializable` on `Person`) immediately.
5. **Git discipline matters.** Using consistent commit prefixes made it easy to track which work was AI-assisted vs manual.

---

## Q10: What would you do differently next time?

1. **Write test cases earlier.** I created test cases at the end; writing them alongside each stage would have caught edge cases sooner.
2. **Use AI for test generation.** I manually wrote test cases — AI could have generated test input data more efficiently.
3. **Add more input validation from the start.** The `InputHelper` is robust for integers but could use more string validation (e.g., rejecting special characters in names).
4. **Implement file persistence in Stage 5 instead of Stage 6.** Earlier integration would have made CRUD testing easier since data would persist between test runs.
5. **Make commit messages more descriptive.** While the prefixes are clear, the messages themselves could include more detail about what was implemented in each commit.
