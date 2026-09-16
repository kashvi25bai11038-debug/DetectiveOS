# Detective OS — Final Setup Guide

## Required
- JDK 21
- Maven 3.9+
- MySQL Server 8+
- IntelliJ IDEA Community/Ultimate (recommended)

## 1. Database
Option A: open `database_setup.sql` in MySQL Workbench and run it.

Option B: use an existing MySQL account and edit `src/main/resources/application.properties`.

Default demo account:
- MySQL user: `detective`
- MySQL password: `detective123`
- Database: `detective_os`

## 2. Open the project
Open the folder containing `pom.xml` in IntelliJ. Set Project SDK to JDK 21 and allow Maven to import dependencies.

## 3. Verify
Run:

```bash
mvn clean test
```

Expected ending:

```text
BUILD SUCCESS
```

## 4. Run

```bash
mvn javafx:run
```

Application login:

```text
Username: detective
Password: detective123
```

## 5. Demo order
1. Login.
2. Select `The Blackwood Mansion Affair`.
3. Start/resume the investigation.
4. Inspect suspects, evidence and timeline.
5. Record a statement or add evidence.
6. Run concurrent analysis.
7. Explain a suspect.
8. Generate the report.
9. Run JDBC analytics.
10. Make an accusation.

## 6. GitHub
Use several meaningful commits:

```bash
git init
git add .
git commit -m "feat: create Detective OS domain model"
git add .
git commit -m "feat: add JPA persistence and repositories"
git add .
git commit -m "feat: implement explainable reasoning engine"
git add .
git commit -m "feat: add concurrent analysis and JavaFX console"
git add .
git commit -m "test: add JUnit validation and reasoning tests"
git add .
git commit -m "docs: add assessment documentation"
```
