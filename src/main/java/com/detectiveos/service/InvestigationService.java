package com.detectiveos.service;

import com.detectiveos.exception.*;
import com.detectiveos.model.*;
import com.detectiveos.repository.*;
import com.detectiveos.util.ValidationUtil;
import java.util.Objects;

public class InvestigationService {
    private final CaseRepository caseRepo = new CaseRepository();
    private final InvestigationRepository investigationRepo = new InvestigationRepository();
    private final GenericRepository<Accusation> accusationRepo = new GenericRepository<Accusation>(Accusation.class) {};
    private final InvestigationHistory history = new InvestigationHistory();

    public void addEvidence(CaseFile c, Evidence e) throws InvestigationClosedException, InvalidEvidenceException {
        ensureEditable(c);
        ValidationUtil.required(e.getCode(), "Evidence code");
        ValidationUtil.required(e.getDescription(), "Evidence description");
        ValidationUtil.reliability(e.getReliability());
        if (c.getEvidence().stream().anyMatch(x -> x.getCode().equalsIgnoreCase(e.getCode())))
            throw new InvalidEvidenceException("Duplicate evidence code: " + e.getCode());
        c.addEvidence(e); caseRepo.update(c); history.push("Added evidence " + e.getCode());
    }

    public void addEvent(CaseFile c, TimelineEvent e) throws InvestigationClosedException {
        ensureEditable(c); c.addTimelineEvent(e); caseRepo.update(c); history.push("Added timeline event: " + e.getDescription());
    }

    public void addStatement(CaseFile c, Statement s) throws InvestigationClosedException {
        ensureEditable(c); ValidationUtil.required(s.getContent(), "Statement");
        c.addStatement(s); caseRepo.update(c); history.push("Recorded statement by " + s.getSpeakerName());
    }

    public Investigation start(CaseFile c, User u) {
        return investigationRepo.save(new Investigation(c.getId(), u.getId()));
    }

    public boolean accuse(CaseFile c, Suspect suspect) {
        if (c.getStatus() == CaseStatus.CLOSED) throw new IllegalStateException("This case is closed.");
        boolean correct = Objects.equals(c.getSolutionSuspectId(), suspect.getId());
        if (correct) c.setStatus(CaseStatus.SOLVED);
        caseRepo.update(c);
        accusationRepo.save(new Accusation(c.getId(), suspect.getId(), correct, suspect.getSuspicionScore()));
        history.push("Accusation against " + suspect.getName() + " → " + (correct ? "CORRECT" : "INCORRECT"));
        return correct;
    }

    public InvestigationHistory history(){return history;}
    public void close(CaseFile c){c.setStatus(CaseStatus.CLOSED);caseRepo.update(c);}

    private void ensureEditable(CaseFile c) throws InvestigationClosedException {
        if (c == null) throw new InvestigationClosedException("No active case selected.");
        if (c.getStatus() == CaseStatus.CLOSED || c.getStatus() == CaseStatus.SOLVED)
            throw new InvestigationClosedException("This case is no longer editable.");
    }
}
