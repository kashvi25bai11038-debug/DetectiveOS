# Detective OS — Project Statement

## 1. Problem Statement

Crime-investigation workflows require information from multiple sources to be collected, organized, compared, and documented systematically. In an educational setting, students often learn individual programming concepts separately without seeing how those concepts can work together inside a complete application.

Detective OS addresses this problem by providing a **fictional, interactive investigation environment** where users can manage cases, examine suspects and evidence, reconstruct timelines, record statements, perform rule-based reasoning, and generate reports.

The project demonstrates how Java programming concepts can be integrated into a practical software system rather than functioning as isolated examples.

> Detective OS is an educational simulation. Its reasoning and suspicion scoring are rule-based programming demonstrations and are not intended for real-world forensic, legal, or law-enforcement decisions.

## 2. Scope of the Project

### In Scope

- User authentication.
- Fictional case creation and management.
- Suspect, witness, and victim management.
- Evidence entry and validation.
- Timeline creation and chronological reconstruction.
- Interview and statement recording.
- Rule-based contradiction detection.
- Concurrent evidence, timeline, and statement analysis.
- Rule-based suspicion scoring.
- Explanation of suspicion-score components.
- Accusation and case-status handling.
- Investigation history.
- JDBC-based analytics.
- Report generation and file logging.
- MySQL persistence using JPA/Hibernate.
- Unit and validation testing.

### Out of Scope

- Real-world criminal investigations.
- Real forensic evidence processing.
- Facial recognition or biometric identification.
- Real law-enforcement databases.
- Legal decision-making.
- Prediction of actual criminal behaviour.
- Production-grade forensic deployment.

## 3. Target Users

### Students

Students can use the project to understand how Java concepts such as OOP, inheritance, interfaces, collections, exception handling, multithreading, I/O, JDBC, and JPA can be integrated into one application.

### Faculty / Evaluators

Faculty members can use the application to evaluate Java programming concepts, database integration, modular design, testing, and documentation.

### Demonstration Users

Users can explore the fictional investigation workflow through the graphical interface and interact with generated cases, evidence, timelines, statements, and reasoning results.

## 4. High-Level Features

### Authentication

- Login and authentication validation.
- Logout functionality.

### Case Management

- View investigation cases.
- Generate fictional cases.
- Start/resume investigations.
- Close cases.
- Track case status and readiness.

### Suspect Management

- Search and view suspects.
- Add suspects.
- Calculate suspicion scores.
- Explain score components.
- Record accusations.

### Evidence Management

- Add evidence.
- Categorize evidence.
- Store reliability and location.
- Link evidence to suspects.
- Validate required fields and duplicate evidence codes.

### Timeline Management

- Add investigation events.
- Associate events with people.
- Sort events chronologically.
- Use timeline information during reasoning.

### Interview & Statement Analysis

- Record statements.
- Associate statements with persons.
- Compare statements against timeline information.
- Detect rule-based contradictions.

### Concurrent Reasoning

- Analyze evidence concurrently.
- Analyze timeline information concurrently.
- Analyze statements concurrently.
- Synchronize shared-state updates where required.
- Produce rule-based suspicion scores.

### Reports & Analytics

- Generate investigation reports.
- Maintain investigation logs.
- Run JDBC analytics.
- View investigation history.

## 5. Expected Workflow

```text
Authenticate
     ↓
Select / Generate Case
     ↓
Start Investigation
     ↓
Collect & Review Evidence
     ↓
Manage Suspects
     ↓
Reconstruct Timeline
     ↓
Record / Analyze Statements
     ↓
Run Concurrent Reasoning
     ↓
Calculate Suspicion Scores
     ↓
Inspect Explanation
     ↓
Record Accusation
     ↓
Generate Report & Review Analytics
```

## 6. Expected Outcome

The completed system provides a functional educational investigation simulation while demonstrating how multiple Java programming concepts can be combined into a modular desktop application.

The project also provides supporting documentation, testing, database integration, version control, and design artefacts for academic project evaluation.
