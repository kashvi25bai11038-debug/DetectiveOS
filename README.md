# 🕵️ Detective OS — Interactive Crime Investigation System

**Version 2.0.1**  
**Course:** CSE2006 — Programming in Java  
**Type:** Desktop Java application / fictional investigation simulator

> Detective OS is an educational fictional simulation. It does not perform real-world forensic analysis or make real criminal-investigation conclusions.

## What is it?
Detective OS is a JavaFX investigation console in which a detective can open a case, inspect suspects and evidence, record statements, reconstruct a timeline, run an explainable reasoning engine, perform concurrent analysis, generate reports and make a final accusation.

## Key features
- 🔐 Login with SHA-256 password hashing
- 📁 Case archive + procedural case generation
- 👤 Suspect, witness and victim domain model
- 🔎 Evidence locker with validation and duplicate protection
- 🗣️ Interview/statement log
- 🕐 Chronological timeline reconstruction
- 🧠 Explainable suspicion scoring
- 🧵 Concurrent evidence/timeline/statement analysis
- 🗄️ JPA + Hibernate persistence
- 🔌 JDBC analytics
- 📄 TXT case reports + investigation log
- 🧪 JUnit tests
- 🧩 OOP, inheritance, polymorphism, interfaces, enums, collections, exceptions, I/O and synchronization

## Tech stack
- Java 21
- JavaFX 21
- Maven 3.9+
- MySQL 8+
- Jakarta Persistence API / Hibernate ORM 6
- JDBC
- JUnit 5
- Git/GitHub

## Run locally
1. Install JDK 21, Maven and MySQL.
2. Run `database_setup.sql` in MySQL Workbench.
3. Open the project folder in IntelliJ.
4. Run `mvn clean test`.
5. Run `mvn javafx:run`.
6. Login with `detective / detective123`.

Detailed instructions are in `docs/SETUP.md`.

## Project structure
```text
src/main/java/com/detectiveos
├── config       # JPA/config/database bootstrap
├── engine       # reasoning and case generation
├── exception    # custom exceptions
├── model        # JPA entities + enums
├── repository   # persistence layer
├── service      # business/application services
├── thread       # concurrent analysis tasks
├── ui           # JavaFX screens
└── util         # validation, hashing, reports, reflection
```

## Assessment alignment
The design intentionally maps the CSE2006 topics to meaningful features. See `docs/SYLLABUS_MAPPING.md` and `docs/PROJECT_SPEC.md`.

## Important folders
- `docs/` — architecture, diagrams, setup, testing and viva notes
- `reports/generated/` — generated case reports after first run
- `database_setup.sql` — MySQL database/user setup
