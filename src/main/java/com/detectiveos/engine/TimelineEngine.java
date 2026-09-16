package com.detectiveos.engine;
import com.detectiveos.model.*;
import java.util.*;
public class TimelineEngine implements EvidenceAnalyzer {
    @Override public AnalysisResult analyze(CaseFile c){
        c.getTimelineEvents().sort(Comparator.comparing(TimelineEvent::getEventTime));
        List<String> findings=new ArrayList<>();
        for(TimelineEvent e:c.getTimelineEvents()) findings.add(e.getEventTime()+" | "+e.getDescription());
        return new AnalysisResult("Timeline reconstructed",findings);
    }
}
