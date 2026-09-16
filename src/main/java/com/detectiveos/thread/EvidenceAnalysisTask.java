package com.detectiveos.thread;
import com.detectiveos.model.CaseFile;
import java.util.concurrent.Callable;
public class EvidenceAnalysisTask implements Callable<String>{
    private final CaseFile c; public EvidenceAnalysisTask(CaseFile c){this.c=c;}
    @Override public String call(){ long reliable=c.getEvidence().stream().filter(e->e.getReliability()>=80).count(); return "Evidence analysis completed by "+Thread.currentThread().getName()+" ("+reliable+" high-reliability items)"; }
}
