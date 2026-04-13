# CodeLobster - High-Level Overview

## 🎯 Project Vision

**CodeLobster** is a competitive programming practice platform that combines structured learning modules with competitive contests and a unique ranking system. Users practice coding problems across various categories and difficulty levels, participate in rated contests, and progress through a distinctive rank hierarchy inspired by fantasy/RPG progression systems.

---

## 🌟 Core Features

### 1. **Problem Practice System**
- **Problem Library**: Curated collection of coding problems organized by:
  - Categories (Arrays, Strings, Trees, Graphs, Dynamic Programming, etc.)
  - Tags (Binary Search, Two Pointers, DFS/BFS, etc.)
  - Difficulty levels (Easy, Medium, Hard, Expert)
  - Rating (400-3900+ scale matching user ratings)

- **Submission & Judging**:
  - Docker-based code execution environment
  - Support for multiple programming languages (Python, Java, C++, JavaScript, etc.)
  - Real-time verdict: Accepted (AC), Wrong Answer (WA), Time Limit Exceeded (TLE), etc.
  - Detailed test case results with execution time and memory usage
  - Public sample test cases + hidden test cases

- **Learning Features**:
  - Sample inputs/outputs with explanations
  - Time and memory constraints per problem
  - Solve statistics (acceptance rate, attempt count)
  - Editorial system (official + community-contributed)
  - Code submission history

### 2. **Structured Learning Modules**
- **Curated Learning Paths**: Organized collections of problems grouped by topic
- **Progressive Difficulty**: Problems ordered from beginner to advanced within each module
- **Progress Tracking**: Visual indicators showing completion percentage
- **Estimated Completion Time**: Help users plan their learning journey
- **Required vs Optional Problems**: Flexibility in module completion

### 3. **Contest System**
- **Contest Types**:
  - **Rated Contests**: Affect user rating, competitive environment
  - **Unrated Contests**: Practice without rating pressure, still counts for rank eligibility
  - **Practice Contests**: Past contests available anytime for practice
  - **Educational Contests**: Learning-focused with extended time or hints
  - **Virtual Contests**: User-initiated replays of past contests

- **Contest Features**:
  - Multiple problems per contest (typically 4-8 problems)
  - Timed events with countdown clock
  - Live leaderboard during contest
  - Division system (rating-based restrictions)
  - Registration system (open/closed phases)
  - Post-contest rating calculations
  - Contest-specific problem scoring

### 4. **Unique Ranking System**

#### Rank Requirements:
To achieve and maintain ranks, users must:
1. ✅ Solve a minimum of **5 problems**
2. ✅ Participate in at least **1 contest** (rated or unrated)

#### Rank Tiers:

**Low Ranks** (Entry Level)
- 🔰 **Initiate** (400-699) - Starting rank
- 📚 **Novice** (700-999) - Learning fundamentals
- 🎓 **Disciple** (1000-1299) - Building foundations

**Mid Ranks** (Intermediate)
- ⚔️ **Practitioner** (1300-1599) - Competent problem solver
- 🔮 **Acolyte** (1600-1899) - Advanced techniques
- 🏛️ **Mentor** (1900-2199) - Expert level
- 📖 **Preceptor** (2200-2499) - Master problem solver

**Advanced Ranks** (Expert)
- 👁️ **Seer** (2500-2799) - Exceptional skill
- 🌟 **Oracle** (2800-3099) - Elite problem solver

**Elite Ranks** (Top Tier)
- ⚡ **Kyrie I** (3100-3299) - World-class
- ⚡⚡ **Kyrie II** (3300-3499) - Grandmaster level
- ⚡⚡⚡ **Kyrie III** (3500-3699) - Legendary
- 👑 **Empyrean** (3700-3899) - Peak performance

**Prestige Ranks** (Elite of Elite)
- 🌌 **Ascended** (3900+) - Reserved for **Top 25 users globally**
- ♾️ **Eternal** (3900+) - Reserved for **Top 10 users globally**

*Note: Prestige ranks require both high rating AND global ranking position*

### 5. **Rating System**
- **Starting Rating**: 400 (Initiate rank)
- **Rating Changes**: Based on contest performance
- **Rating History**: Track progress over time with graphs
- **Transparent Calculations**: Users can see rating changes explained
- **Division System**: Contests may be restricted by rating range

### 6. **User Profiles & Progress**
- **Profile Dashboard**:
  - Current rating and rank
  - Problems solved count
  - Contest participation history
  - Submission history
  - Rating graph over time
  - Achievement badges
  - Personal bio and avatar

- **Statistics**:
  - Problems solved by category
  - Problems solved by difficulty
  - Submission accuracy rate
  - Average solve time
  - Favorite programming language
  - Contest performance metrics

### 7. **Editorial & Community**
- **Official Editorials**: Created by problem setters
- **Community Editorials**: User-contributed solutions and explanations
- **Voting System**: Upvote/downvote helpful editorials
- **Multi-Language Code Examples**: Solutions in different programming languages
- **Discussion Features** (Future): Comment system for collaboration

### 8. **Notification System**
- **Contest Reminders**: Upcoming contests you're registered for
- **Rank Achievements**: Celebrate when you reach a new rank
- **New Editorials**: Notifications for problems you've attempted
- **Milestones**: Problem count achievements (10, 50, 100, 500 solved)
- **System Announcements**: Platform updates and maintenance notices

---

## 🏗️ Technical Architecture

### **Technology Stack**

#### Backend:
- **Framework**: Spring Boot (Java)
- **Database**: PostgreSQL
- **Authentication**: Spring Security with JWT
- **Code Execution**: Docker containers (isolated sandboxed environment)
- **API**: RESTful API with JSON responses

#### Frontend (Planned):

**Next.js**

- Tailwind
- ShadCN/UI
- React Query
- Zustand
- Framer Motions

#### Infrastructure:
- **Docker**: For secure code execution
- **Queue System**: Handle submission judging asynchronously
- **Caching**: Redis for leaderboards and frequently accessed data
- **CDN**: Serve static assets efficiently

#### Deployment & Workflow:

- main -> production
- develop --> active development
- feature branches --> `feature/*`
- staging (optional)

Flow:
`feature/* --> develop --> staging (optional) --> main`

Migrations
Use:
- Flyway or Liquibase (Spring Boot friendly)

This ensures:
- schema consistency across all environments

Seeding Strategy
Dev
- Seed test data (users, problems)
- Reset frequently

Staging
- no  fake reseeding from dev

Production
- Seed only essential baseline data
Everything else = real users


Docker
- Dockerized PostgreSQL
- Docker Compose for:
Backend, Db, Redis (later)

Vercel
- Next.js

Add preview deployments - Vercel - every PR gets live URL

Render
- Spring Boot
- PostgreSQL Database

---

## 📊 Data Models Overview

### Core Entities:

1. **User**
   - Authentication credentials
   - Rating and rank information
   - Profile information
   - Problem-solving statistics
   - Contest participation history

2. **Problem**
   - Title, description, and constraints
   - Category and tags
   - Difficulty rating
   - Test cases (visible + hidden)
   - Time and memory limits
   - Author and publication status

3. **Submission**
   - User and problem reference
   - Source code and language
   - Verdict (AC, WA, TLE, etc.)
   - Execution metrics
   - Timestamp and contest linkage

4. **Contest**
   - Contest metadata (title, description, type)
   - Time schedule (start, end, duration)
   - Problems included
   - Participant list
   - Rating impact settings

5. **Module**
   - Learning path information
   - Problem collection
   - Difficulty level
   - User progress tracking

6. **TestCase**
   - Input and expected output
   - Visibility (sample vs hidden)
   - Point weight for scoring

7. **Editorial**
   - Problem explanation
   - Solution approach
   - Code examples
   - Community voting

### Supporting Entities:

- **ContestParticipation**: Links users to contests with scores and ranks
- **ModuleProgress**: Tracks user completion of learning modules
- **RatingHistory**: Audit trail of rating changes
- **UserProblemStatus**: Quick lookup of solved/attempted problems

---

## 👥 User Roles & Permissions

### Role Hierarchy:

1. **USER** (Default)
   - Solve problems
   - Participate in contests
   - Write editorials
   - View profile and statistics

2. **MODERATOR**
   - All USER permissions
   - Review and moderate content
   - Handle user reports
   - Manage editorials and comments

3. **PROBLEM_SETTER**
   - All MODERATOR permissions
   - Create and edit problems
   - Manage test cases
   - Write official editorials
   - Create learning modules

4. **CONTEST_MANAGER**
   - All MODERATOR permissions
   - Create and manage contests
   - Configure contest settings
   - Assign problems to contests
   - Manage contest participants

5. **ADMIN**
   - Full system access
   - Manage all users
   - Platform configuration
   - Database management
   - All lower-level permissions

6. **BANNED**
   - Account suspended
   - No access to platform

---

## 🔒 Security Features

### Authentication & Authorization:
- **Email Verification**: Confirm email before account activation
- **Secure Password Storage**: BCrypt hashing
- **JWT Tokens**: Stateless authentication
- **Role-Based Access Control**: Spring Security integration
- **Session Management**: Token expiration and refresh

### Code Execution Security:
- **Docker Sandboxing**: Isolated execution environments
- **Resource Limits**: CPU, memory, and time constraints
- **Network Isolation**: No external network access during execution
- **Malicious Code Detection**: Prevent system exploitation

### Anti-Cheating Measures:
- **Plagiarism Detection** (Future): Compare submissions for similarity
- **Contest Integrity**: Locked submissions during contests
- **Disqualification System**: Handle rule violations

---

## 🎮 User Journey

### New User Experience:
1. **Registration**: Sign up with email
2. **Email Verification**: Confirm account
3. **Welcome Dashboard**: Introduction to platform
4. **First Problem**: Solve an easy problem
5. **Learning Modules**: Explore structured learning paths
6. **First Contest**: Participate in unrated/educational contest
7. **Rank Achievement**: Unlock first rank after meeting requirements

### Experienced User Flow:
1. **Problem Practice**: Daily problem-solving
2. **Contest Participation**: Regular rated contests
3. **Rating Progression**: Climb the ranks
4. **Community Contribution**: Write editorials
5. **Achievement Unlocks**: Milestones and badges
6. **Prestige Ranks**: Compete for top 25/top 10

---

## 📈 Key Metrics & Analytics

### User Engagement:
- Daily active users (DAU)
- Problems solved per user
- Contest participation rate
- Average session duration
- User retention rate

### Platform Health:
- Total problems available
- Contest frequency
- Submission success rate
- Average problem difficulty
- User rating distribution

### Quality Metrics:
- Editorial coverage (% of problems with editorials)
- Test case quality (low false positive/negative rate)
- User satisfaction (upvote ratios on editorials)

---

## 🚀 Development Roadmap

### Phase 1: Core Platform (MVP)
- ✅ User authentication and authorization
- ✅ Problem creation and management
- ✅ Docker-based code execution
- ✅ Basic submission system
- ✅ Simple rating system
- ✅ Contest creation
- ✅ Rank system implementation

### Phase 2: Enhanced Features
- Learning modules with progress tracking
- Official editorial system
- Rating history and graphs
- User profile pages
- Notification system
- Advanced filtering and search

### Phase 3: Community Features
- Community editorial system
- Comment and discussion threads
- User achievements and badges
- Social features (followers, friends)
- Leaderboards and rankings

### Phase 4: Advanced Tools
- Plagiarism detection
- Virtual contests
- Team contests
- Code comparison tools
- Advanced analytics dashboard
- API for external integrations

### Phase 5: Scale & Optimize
- Performance optimization
- Caching strategies
- CDN integration
- Mobile app development
- Advanced anti-cheating measures
- Machine learning for problem recommendations

---

## 🎯 Success Criteria

### User Goals:
- **Skill Development**: Users improve problem-solving abilities
- **Contest Performance**: Users achieve higher ratings over time
- **Learning Engagement**: Users complete learning modules
- **Community Participation**: Users contribute editorials and discussions

### Platform Goals:
- **User Retention**: 60%+ monthly active user retention
- **Problem Quality**: 85%+ user satisfaction on problem quality
- **Contest Attendance**: Average 100+ participants per rated contest
- **Content Growth**: Regular addition of new problems and modules

---

## 🌐 Competitive Landscape

### Differentiation from Competitors:

| Feature | LobsterCode | LeetCode | Codeforces | HackerRank |
|---------|-------------|----------|------------|------------|
| Unique Rank System | ✅ Fantasy-themed | ❌ Generic | ❌ Color-based | ❌ Stars |
| Learning Modules | ✅ Structured paths | ✅ Study plans | ❌ | ✅ Tracks |
| Contest Variety | ✅ Multiple types | ⚠️ Weekly only | ✅ Frequent | ✅ Challenges |
| Rank Requirements | ✅ Solve + Contest | ❌ Just solve | ❌ Just rating | ❌ Just solve |
| Community Editorials | ✅ Planned | ✅ Yes | ✅ Yes | ⚠️ Limited |
| Docker Execution | ✅ Yes | ✅ Yes | ✅ Yes | ✅ Yes |

### Unique Value Propositions:
1. **Engaging Rank System**: Fantasy-themed progression creates emotional investment
2. **Balanced Requirements**: Must both practice AND compete to progress
3. **Structured Learning**: Modules guide users through concepts systematically
4. **Contest Variety**: Different contest types for different learning styles
5. **Prestige Ranks**: Elite tiers for top performers create aspirational goals

---

## 💡 Future Enhancements

### Potential Features:
- **Code Review**: Peer code review system
- **Problem Recommender**: ML-based personalized problem suggestions
- **Custom Contests**: Users can create private contests for friends

---

## 📝 Summary

**LobsterCode** is more than just a coding practice platform—it's a comprehensive learning ecosystem that combines:

✨ **Structured Learning**: Guided modules help users build skills progressively

🏆 **Competitive Spirit**: Rated contests and unique ranking system drive engagement

🎮 **Gamification**: Fantasy-themed ranks make progression feel like an RPG adventure

👥 **Community**: Editorial system and future discussion features foster collaboration

🔒 **Security**: Docker-based execution ensures safe code testing

📊 **Transparency**: Clear rating system and progress tracking

The platform targets competitive programmers of all levels, from complete beginners (Initiate) to world-class experts (Eternal), providing the tools, challenges, and community to support continuous improvement in algorithmic problem-solving.

