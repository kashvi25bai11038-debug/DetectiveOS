package com.detectiveos.util;

import com.detectiveos.engine.ReasoningEngine;
import com.detectiveos.model.*;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Locale;

public final class ReportGenerator {
    private ReportGenerator() {}
    public static Path generate(CaseFile c) throws IOException {
        new ReasoningEngine().analyze(c);
        StringBuilder s=new StringBuilder();
        s.append("============================================================\n");
        s.append("                    DETECTIVE OS\n");
        s.append("             INVESTIGATION CASE REPORT\n");
        s.append("============================================================\n\n");
        s.append("CASE #").append(c.getId()).append("\nTITLE: ").append(c.getTitle()).append("\nCRIME: ").append(c.getCrimeType()).append("\nLOCATION: ").append(c.getLocation()).append("\nDATE: ").append(c.getCrimeDate()).append("\nSTATUS: ").append(c.getStatus()).append("\nDIFFICULTY: ").append(c.getDifficulty()).append("\n\n");
        s.append("SUSPECT INTELLIGENCE\n--------------------\n");
        c.getSuspects().stream().sorted(Comparator.comparingDouble(Suspect::getSuspicionScore).reversed()).forEach(x->s.append(String.format(Locale.ROOT,"%-22s score=%5.1f | motive=%s | alibi=%s%n",x.getName(),x.getSuspicionScore(),x.getMotive(),x.getAlibi())));
        s.append("\nEVIDENCE LOCKER\n---------------\n");
        c.getEvidence().forEach(e->s.append(e.getCode()).append(" | ").append(e.getType()).append(" | ").append(e.getDescription()).append(" | reliability=").append(e.getReliability()).append("%\n"));
        s.append("\nTIMELINE\n--------\n");
        c.getTimelineEvents().stream().sorted(Comparator.comparing(TimelineEvent::getEventTime)).forEach(e->s.append(e.getEventTime()).append(" | ").append(e.getDescription()).append(" | ").append(e.getLocation()).append("\n"));
        s.append("\nSTATEMENTS\n----------\n");
        c.getStatements().forEach(st->s.append(st.getSpeakerName()).append(" | ").append(st.isContradictionDetected()?"CONTRADICTION":"CONSISTENT").append(" | ").append(st.getContent()).append("\n"));
        s.append("\nSYSTEM NOTE\n-----------\nThis is an educational fictional simulation. Scores are rule-based indicators, not real forensic conclusions.\n");
        return FileManager.writeReport("Case_"+c.getId()+"_Report.txt",s.toString());
    }
}
