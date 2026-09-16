# Detective OS — Final Design Diagram Sources

These Mermaid diagrams can be rendered in Mermaid-compatible Markdown or recreated in draw.io/StarUML for the final report.

## 1. System Architecture

```mermaid
flowchart TB
    UI[JavaFX Presentation Layer]
    S[Service Layer]
    E[Reasoning / Case Engine]
    R[Repository Layer]
    JPA[JPA + Hibernate]
    JDBC[JDBC Analytics]
    DB[(MySQL Database)]
    IO[Java I/O / Reports]

    UI --> S
    S --> E
    S --> R
    E --> S
    R --> JPA
    R --> JDBC
    JPA --> DB
    JDBC --> DB
    S --> IO
```

## 2. Use Case Diagram

```mermaid
flowchart LR
    D[Detective]
    D --> A[Login]
    D --> B[Select Case]
    D --> C[Generate Case]
    D --> E[Inspect Evidence]
    D --> F[Review Suspects]
    D --> G[Record Statement]
    D --> H[Reconstruct Timeline]
    D --> I[Run Concurrent Analysis]
    D --> J[Explain Suspicion]
    D --> K[Accuse Suspect]
    D --> L[Generate Report]
    D --> M[Run JDBC Analytics]
```

## 3. Investigation Workflow

```mermaid
flowchart TD
    S[Start] --> L[Authenticate]
    L --> C[Select / Generate Case]
    C --> O[Observe Case Dossier]
    O --> E[Collect Evidence]
    O --> Q[Question Suspects]
    O --> T[Reconstruct Timeline]
    E --> A[Concurrent Analysis]
    Q --> A
    T --> A
    A --> R[Explainable Reasoning Engine]
    R --> P[Rank Suspects]
    P --> V[Form Theory]
    V --> X[Accuse]
    X -->|Correct| Z[Case Solved]
    X -->|Incorrect| O
    Z --> G[Generate Report]
```

## 4. Concurrent Analysis

```mermaid
flowchart LR
    C[Active Case] --> P[Fixed Thread Pool]
    P --> E[EvidenceAnalysisTask]
    P --> T[TimelineAnalysisTask]
    P --> S[StatementAnalysisTask]
    E --> J[Join Results]
    T --> J
    S --> J
    J --> R[Synchronized Suspicion Scoring]
```

## 5. Sequence Diagram

```mermaid
sequenceDiagram
    actor Detective
    participant UI as JavaFX UI
    participant Service as InvestigationService
    participant Engine as ReasoningEngine
    participant DB as JPA/Hibernate
    participant JDBC as JDBCAnalytics

    Detective->>UI: Select active case
    UI->>Service: Load case
    Service->>DB: Query entities
    DB-->>Service: Case graph
    Service-->>UI: Display dossier
    Detective->>UI: Run analysis
    UI->>Engine: Analyze case
    Engine-->>UI: Findings + scores
    UI->>DB: Persist updated state
    Detective->>UI: Run analytics
    UI->>JDBC: Execute prepared query
    JDBC-->>UI: Aggregated results
    Detective->>UI: Accuse suspect
    UI->>Service: Record accusation
    Service->>DB: Persist accusation/status
```

## 6. Class Diagram

```mermaid
classDiagram
    class Person {
      <<abstract>>
      -Long id
      -String name
      -int age
      -String occupation
      +getPersonType()
      +describe()
    }
    class Suspect {
      -String motive
      -String alibi
      -double suspicionScore
    }
    class Witness
    class Victim
    Person <|-- Suspect
    Person <|-- Witness
    Person <|-- Victim

    class CaseFile {
      -Long id
      -String title
      -CrimeType crimeType
      -CaseStatus status
      -CaseDifficulty difficulty
      -Long solutionSuspectId
    }
    CaseFile o-- Suspect
    CaseFile o-- Evidence
    CaseFile o-- Statement
    CaseFile o-- TimelineEvent
    CaseFile o-- Victim

    class Evidence
    class Statement
    class TimelineEvent
    class Investigation
    class Accusation

    class EvidenceAnalyzer {
      <<interface>>
      +analyze(CaseFile)
    }
    class StatementEngine
    class TimelineEngine
    class SuspicionEngine
    EvidenceAnalyzer <|.. StatementEngine
    EvidenceAnalyzer <|.. TimelineEngine
    EvidenceAnalyzer <|.. SuspicionEngine

    class ReasoningEngine
    class ConcurrentAnalysisService
    class InvestigationService
    ReasoningEngine --> EvidenceAnalyzer
    ConcurrentAnalysisService --> ReasoningEngine
    InvestigationService --> CaseFile
    InvestigationService --> Investigation
    InvestigationService --> Accusation
```

## 7. ER Diagram — Conceptual

```mermaid
erDiagram
    USERS ||--o{ INVESTIGATIONS : conducts
    CASES ||--o{ EVIDENCE : contains
    CASES ||--o{ STATEMENTS : contains
    CASES ||--o{ TIMELINE_EVENTS : contains
    CASES ||--o{ SUSPECTS : includes
    CASES ||--|| VICTIMS : has
    SUSPECTS ||--o{ STATEMENTS : gives
    CASES ||--o{ ACCUSATIONS : records
```
