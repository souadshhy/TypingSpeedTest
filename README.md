# Typing Speed Test (Java Project)

## Project Overview

Typing Speed Test is a Java-based desktop application developed as a group project for the Java Programming course (CENG360). The system allows users to practice typing, measure their typing performance, and track their progress through stored analytics and results.

The application follows an object-oriented architecture and separates the project into backend logic, models, and user interface components.

---

# Features

## Authentication System
- User signup
- User login
- Local file-based user storage

## Typing Test System
- Random typing text generation
- Multiple difficulty levels:
  - Easy
  - Medium
  - Hard
- Typing accuracy calculation
- WPM (Words Per Minute) calculation
- Mistake detection

## Analytics
- Average WPM
- Best WPM
- Average Accuracy
- Total Tests Taken
- User typing history

## Data Persistence
- Users stored in:
  ```text
  data/users.txt
  ```

- Test results stored in:
  ```text
  data/results.txt
  ```

---

# Technologies Used

- Java
- Apache NetBeans IDE
- Object-Oriented Programming
- File Handling
- Git & GitHub

---

# Project Structure

```text
TypingSpeedTest/
│
├── data/
│   ├── users.txt
│   └── results.txt
│
├── texts/
│   ├── easy/
│   ├── medium/
│   └── hard/
│
├── src/
│   │
│   ├── app/
│   │   └── Main.java
│   │
│   ├── logic/
│   │   ├── AnalyticsManager.java
│   │   ├── AuthManager.java
│   │   ├── DataManager.java
│   │   ├── EasyScoreStrategy.java
│   │   ├── HardScoreStrategy.java
│   │   ├── MediumScoreStrategy.java
│   │   ├── ScoreStrategy.java
│   │   ├── StatsCalculator.java
│   │   ├── TextSelector.java
│   │   ├── TypingEngine.java
│   │   └── TypingTestManager.java
│   │
│   ├── model/
│   │   ├── Difficulty.java
│   │   ├── TestResult.java
│   │   └── User.java
│   │
│   └── ui/
│       ├── GraphScreen.java
│       ├── LoginScreen.java
│       ├── MenuScreen.java
│       ├── ResultScreen.java
│       └── TypingScreen.java
│
├── BACKEND_USAGE.txt
├── build.xml
├── manifest.mf
└── README.md
```

---

# Object-Oriented Programming Concepts Used

## Encapsulation

Classes store data using private fields with getters and setters.

Example:

```java
private String username;
```

---

## Inheritance

Different score strategy classes inherit from the abstract `ScoreStrategy` class.

Example:

```java
public class EasyScoreStrategy extends ScoreStrategy
```

---

## Polymorphism

The application dynamically selects score calculation strategies depending on difficulty level.

Example:

```java
ScoreStrategy strategy;
```

---

## Abstraction

The `ScoreStrategy` abstract class defines a common scoring behavior for all difficulty levels.

---

# Mathematical Concepts

## WPM Calculation

Words Per Minute is calculated using:

```text
WPM = Number of Typed Words / Time in Minutes
```

---

## Accuracy Calculation

```text
Accuracy = (Correct Characters / Total Characters) × 100
```

---

## Final Score Calculation

Different difficulty levels use different formulas.

Example:

```text
Final Score = (WPM × Weight) + (Accuracy × Weight) − Mistake Penalty
```

---

# Difficulty Levels

## Easy

- Easier typing texts
- Lower mistake penalty

## Medium

- Balanced difficulty
- Standard scoring

## Hard

- More advanced texts
- Higher mistake penalty

---

# Backend Architecture

The backend is designed so the frontend only communicates with high-level manager classes.

## Main Backend APIs

### Authentication

```java
AuthManager auth = new AuthManager();

auth.login(username, password);
auth.signup(username, password);
```

### Typing Tests

```java
TypingTestManager manager = new TypingTestManager();

manager.generateTestText(Difficulty.MEDIUM);

manager.submitTypingTest(
    username,
    originalText,
    typedText,
    timeInSeconds,
    difficulty
);
```

### Analytics

```java
manager.getAverageWPM(username);
manager.getBestWPM(username);
manager.getAverageAccuracy(username);
manager.getTotalTests(username);
```

---

# How To Run

## Using Apache NetBeans IDE

1. Open Apache NetBeans IDE
2. Open the project folder
3. Run:

   ```text
   Main.java
   ```

---

# GitHub Collaboration Workflow

1. Clone repository
2. Pull latest changes before working
3. Commit changes
4. Push changes to GitHub

---

# Team Responsibilities

## Backend Developer

Responsible for:

- Authentication
- Typing logic
- Data management
- Analytics
- OOP architecture

## Frontend Developer

Responsible for:

- UI screens
- User interaction
- Navigation
- Displaying results and analytics

---

# Future Improvements

- Real-time typing timer
- Live mistake highlighting
- Graph visualization
- Database integration
- Online multiplayer typing tests
- Leaderboard system

---

# Course Information

Course: CENG360 – Java Programming  
Faculty of Engineering  
Hasan Kalyoncu University

---

# Authors

Group Project – Typing Speed Test System
