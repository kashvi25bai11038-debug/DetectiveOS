package com.detectiveos.engine;

import com.detectiveos.model.*;
import java.util.*;

/** Detects simple, explainable statement/timeline inconsistencies. */
public class StatementEngine implements EvidenceAnalyzer {
    private static final List<String> LOCATION_WORDS = List.of("library", "kitchen", "study", "garden", "hallway", "bedroom", "entrance", "office", "garage");

    @Override public AnalysisResult analyze(CaseFile c) {
        List<String> findings = new ArrayList<>();
        for (Statement st : c.getStatements()) {
            boolean contradiction = false;
            String statement = Optional.ofNullable(st.getContent()).orElse("").toLowerCase(Locale.ROOT);
            for (String location : LOCATION_WORDS) {
                if (statement.contains(location)) {
                    boolean conflictingEvent = c.getTimelineEvents().stream()
                            .filter(e -> Objects.equals(e.getRelatedPersonId(), st.getPersonId()))
                            .anyMatch(e -> !Optional.ofNullable(e.getLocation()).orElse("").toLowerCase(Locale.ROOT).contains(location));
                    if (conflictingEvent) { contradiction = true; break; }
                }
            }
            st.setContradictionDetected(contradiction);
            findings.add(st.getSpeakerName() + " → " + (contradiction ? "CONTRADICTION DETECTED" : "consistent with known timeline"));
        }
        return new AnalysisResult("Statement consistency analysis", findings);
    }
}
