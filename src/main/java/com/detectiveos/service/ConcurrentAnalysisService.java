package com.detectiveos.service;

import com.detectiveos.model.CaseFile;
import com.detectiveos.thread.*;
import com.detectiveos.engine.SuspicionEngine;
import java.util.*;
import java.util.concurrent.*;

public class ConcurrentAnalysisService {
    public List<String> analyze(CaseFile c) throws InterruptedException, ExecutionException {
        ExecutorService pool=Executors.newFixedThreadPool(3);
        try {
            List<Future<String>> futures=pool.invokeAll(List.of(new EvidenceAnalysisTask(c),new TimelineAnalysisTask(c),new StatementAnalysisTask(c)));
            List<String> results=new ArrayList<>();
            for(Future<String> f:futures) results.add(f.get());
            synchronized(c){ new SuspicionEngine().analyze(c); }
            return results;
        } finally { pool.shutdown(); }
    }
}
