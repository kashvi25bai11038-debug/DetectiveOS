package com.detectiveos.thread;
import com.detectiveos.model.*;
import java.util.*;
import java.util.concurrent.Callable;
public class TimelineAnalysisTask implements Callable<String>{
    private final CaseFile c; public TimelineAnalysisTask(CaseFile c){this.c=c;}
    @Override public String call(){ List<TimelineEvent> ordered=new ArrayList<>(c.getTimelineEvents()); ordered.sort(Comparator.comparing(TimelineEvent::getEventTime)); return "Timeline analysis completed by "+Thread.currentThread().getName()+" ("+ordered.size()+" events ordered)"; }
}
