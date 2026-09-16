# Detective OS — Final Project Specification

## 1. Problem Statement
Traditional CRUD-style crime demos store case information but do not help an investigator connect evidence, statements and events. Detective OS provides a fictional, explainable investigation environment where a detective can collect evidence, record interviews, reconstruct a timeline and run a rule-based reasoning engine before making an accusation.

## 2. Major Functional Modules
1. Authentication and role-aware access
2. Case management and procedural case generation
3. Suspect/witness management
4. Evidence locker and validation
5. Interview/statement management
6. Timeline reconstruction
7. Explainable reasoning and suspicion scoring
8. Concurrent analysis using multiple worker tasks
9. JDBC analytics
10. Report generation and investigation history

## 3. Non-functional Requirements
- Usability: tab-based JavaFX interface with clear status feedback.
- Reliability: validation, custom exceptions and transaction rollback.
- Maintainability: layered packages and small service/engine classes.
- Performance: concurrent analysis uses a fixed thread pool for independent tasks.
- Security: password hashes are stored using SHA-256 for the educational demo.
- Resource efficiency: JPA EntityManager instances are short-lived and ExecutorService is shut down after analysis.

## 4. Reasoning Model
The suspicion score is deliberately explainable:
- documented motive: +20
- linked evidence: up to +30
- timeline involvement: up to +20
- statement contradictions: up to +30
- maximum: 100

The score is an educational rule-based indicator and is not a real forensic conclusion.
