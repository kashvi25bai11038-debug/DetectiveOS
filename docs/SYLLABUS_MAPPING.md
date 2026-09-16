# CSE2006 Syllabus Mapping

| Syllabus concept | Detective OS implementation |
|---|---|
| Variables / flow control | validation, scoring and UI logic |
| Classes & objects | CaseFile, Suspect, Evidence, Statement, TimelineEvent |
| Constructors | domain object construction |
| Encapsulation | private fields + accessors |
| Inheritance | abstract Person → Suspect/Witness/Victim |
| Method overriding | describe() and getPersonType() |
| Abstract class | Person |
| Interfaces | EvidenceAnalyzer |
| Polymorphism | interchangeable reasoning engines |
| Enum | CrimeType, EvidenceType, CaseStatus, CaseDifficulty |
| Singleton-style resource manager | JPAUtil |
| Reflection | ReflectionInspector |
| Exception handling | custom domain exceptions + try/catch |
| Multithreading | EvidenceAnalysisTask, TimelineAnalysisTask, StatementAnalysisTask |
| Synchronization | synchronized shared CaseFile analysis |
| Collections | ArrayList, List, Stack and collection processing |
| I/O streams | report/log generation with Files.writeString |
| JDBC | JDBCAnalyticsService |
| JPA / ORM | JPA entities + Hibernate |
| JPQL | repository queries |
| Testing | JUnit 5 |
