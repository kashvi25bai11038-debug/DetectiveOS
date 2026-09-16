package com.detectiveos.engine;

import com.detectiveos.model.*;
import java.util.*;

/** Explainable weighted rule engine. Maximum score is 100. */
public class SuspicionEngine implements EvidenceAnalyzer {
    @Override public AnalysisResult analyze(CaseFile c) {
        List<String> findings = new ArrayList<>();
        for (Suspect s : c.getSuspects()) {
            double score = 0;
            if (s.getMotive() != null && !s.getMotive().isBlank()) score += 20;
            long linked = c.getEvidence().stream().filter(e -> Objects.equals(e.getLinkedSuspectId(), s.getId())).count();
            score += Math.min(30, linked * 10);
            long events = c.getTimelineEvents().stream().filter(e -> Objects.equals(e.getRelatedPersonId(), s.getId())).count();
            score += Math.min(20, events * 10);
            long contradictions = c.getStatements().stream().filter(st -> Objects.equals(st.getPersonId(), s.getId()) && st.isContradictionDetected()).count();
            score += Math.min(30, contradictions * 30);
            s.setSuspicionScore(Math.min(100, score));
            findings.add(String.format(Locale.ROOT, "%s → %.1f/100", s.getName(), s.getSuspicionScore()));
        }
        c.getSuspects().sort(Comparator.comparingDouble(Suspect::getSuspicionScore).reversed());
        return new AnalysisResult("Suspicion scoring", findings);
    }
}
