# LobsterCode

New Name?

- CodeCouncil

My own customized coding practice website.
Features **modules** of **problems** that have **categories** and **tags** to practice for coding.

In order to attempt problems, and submit solutions --> via DockerExecution

- User must register by email
- User must login

---

Ranks
In order to get a rank, you must satefy two things:
- Solve a total of 5 problems
- Participate in a contest

Low Ranks
Initiate 		400-699
Novice 			700-999
Disciple		1000-1299

Mid Ranks
Practitioner 	1300-1599
Acolyte			1600-1899
Mentor			1900-2199
Preceptor 		2200-2499

Advanced Ranks
Seer			2500-2799
Oracle 			2800-3099

Elite Ranks
Kyrie I			3100-3299
Kyrie II 		3300-3499
Kyrie III		3500-3699
Empyrean 		3700-3899

Prestige Ranks
Ascended 		3900+ (top 25 players)
Eternal			3900+ (reserved for top 10 players)



## Models


User
id, role, username, email, password, createdAt, submissions, enabled

Problem
id, title, description, category, tag, rating, testCases(list), 

Submission
id, user, problem, code, status, resultJson, submittedAt, 

TestCase
id, problem, input, expectedOutput, visible

Module
id, title, description, problemCategory, problemTag, difficultyLevel

ModuleProblem
id, module, problem

Editorial
id, problem, title, markdownContent, official, author, createdAt, updatedAt

