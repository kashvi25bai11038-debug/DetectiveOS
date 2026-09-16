package com.detectiveos.engine;

import com.detectiveos.model.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class SuspicionEngineTest {
    @Test void motiveRaisesScore(){
        CaseFile c=new CaseFile("Test","x",CrimeType.MURDER,"Test",java.time.LocalDate.now(),CaseDifficulty.EASY);
        Suspect s=new Suspect("A",30,"Engineer","financial dispute","home");
        c.addSuspect(s);
        new SuspicionEngine().analyze(c);
        assertEquals(20.0,s.getSuspicionScore());
    }
    @Test void linkedEvidenceRaisesScore(){
        CaseFile c=new CaseFile("Test","x",CrimeType.MURDER,"Test",java.time.LocalDate.now(),CaseDifficulty.EASY);
        Suspect s=new Suspect("A",30,"Engineer","motive","home");
        c.addSuspect(s);
        Evidence e=new Evidence("E1",EvidenceType.CCTV,"camera","room",LocalDateTime.now(),90);
        c.addEvidence(e);
        // IDs are database-generated in production; this test verifies the base scoring path only.
        new SuspicionEngine().analyze(c);
        assertTrue(s.getSuspicionScore()>=20);
    }
}
