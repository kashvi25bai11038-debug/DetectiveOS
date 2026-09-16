# Viva Notes — Detective OS

## Why JavaFX?
It provides a desktop interface for a visually demonstrable investigation workflow.

## Why JPA/Hibernate?
To map Java domain objects to relational tables and demonstrate ORM, entity relationships and JPQL.

## Why JDBC as well?
JDBC is used for direct reporting queries, while JPA handles ordinary entity persistence.

## Why multithreading?
Evidence, timeline and statement analysis are independent tasks. A fixed thread pool demonstrates concurrent execution and synchronized access to shared case state.

## Why an interface?
`EvidenceAnalyzer` allows multiple analysis engines to follow the same contract.

## Why an abstract class?
`Person` captures common state while `Suspect`, `Witness` and `Victim` provide specialized behavior.

## How is suspicion calculated?
Motive +20, linked evidence up to +30, timeline involvement up to +20 and contradictions up to +30. The maximum is 100.

## Is this real forensic software?
No. It is a fictional educational simulation. The scores are explainable software rules, not real forensic conclusions.
