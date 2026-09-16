package com.detectiveos.thread;
import com.detectiveos.engine.StatementEngine;
import com.detectiveos.model.CaseFile;
import java.util.concurrent.Callable;
public class StatementAnalysisTask implements Callable<String>{
    private final CaseFile c; public StatementAnalysisTask(CaseFile c){this.c=c;}
    @Override public String call(){ synchronized(c){ new StatementEngine().analyze(c); } return "Statement analysis completed by "+Thread.currentThread().getName(); }
}
