package com.detectiveos.engine;

import com.detectiveos.model.*;
import java.util.*;

public class ReasoningEngine {
    private final List<EvidenceAnalyzer> analyzers = List.of(new StatementEngine(), new TimelineEngine(), new SuspicionEngine());

    public List<AnalysisResult> analyze(CaseFile c) {
        List<AnalysisResult> results = new ArrayList<>();
        // Statement analysis must happen before suspicion scoring because contradictions affect the score.
        results.add(new StatementEngine().analyze(c));
        results.add(new TimelineEngine().analyze(c));
        results.add(new SuspicionEngine().analyze(c));
        return results;
    }

    public List<Suspect> rankSuspects(CaseFile c) {
        analyze(c);
        return new ArrayList<>(c.getSuspects());
    }

    public String explain(Suspect s, CaseFile c) {
        List<String> reasons = new ArrayList<>();
        if (s.getMotive() != null && !s.getMotive().isBlank()) reasons.add("Documented motive: +20");
        long linked = c.getEvidence().stream().filter(e -> Objects.equals(e.getLinkedSuspectId(), s.getId())).count();
        if (linked > 0) reasons.add("Linked evidence: " + linked + " item(s), +" + Math.min(30, linked * 10));
        long events = c.getTimelineEvents().stream().filter(e -> Objects.equals(e.getRelatedPersonId(), s.getId())).count();
        if (events > 0) reasons.add("Timeline involvement: " + events + " event(s), +" + Math.min(20, events * 10));
        long contradictions = c.getStatements().stream().filter(st -> Objects.equals(st.getPersonId(), s.getId()) && st.isContradictionDetected()).count();
        if (contradictions > 0) reasons.add("Statement contradictions: " + contradictions + ", +" + Math.min(30, contradictions * 30));
        if (reasons.isEmpty()) reasons.add("No significant indicators have been established yet.");
        return String.join("\n• ", reasons);
    }

    public double averageScore(CaseFile c) {
        return c.getSuspects().stream().mapToDouble(Suspect::getSuspicionScore).average().orElse(0);
    }
}
