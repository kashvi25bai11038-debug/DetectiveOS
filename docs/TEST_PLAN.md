# Detective OS — Test Plan

| Test area | Expected result |
|---|---|
| Correct login | Dashboard opens |
| Wrong login | AuthenticationException / error message |
| Blank evidence code | Validation failure |
| Duplicate evidence code | InvalidEvidenceException |
| Reliability outside 0–100 | Validation failure |
| Timeline sorting | Events appear chronologically |
| Statement contradiction | Contradiction flag becomes true when location conflicts |
| Suspicion scoring | Score remains 0–100 |
| Concurrent analysis | Three tasks complete and shared state remains consistent |
| Correct accusation | Case becomes SOLVED and accusation is stored |
| Wrong accusation | Case remains UNSOLVED |
| Closed case modification | InvestigationClosedException |
| Report generation | TXT report appears under reports/generated |
| JDBC analytics | Aggregated case/suspect information is returned |
