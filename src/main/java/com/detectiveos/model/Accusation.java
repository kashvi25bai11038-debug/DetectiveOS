package com.detectiveos.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name="accusations")
public class Accusation {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    private Long caseId; private Long suspectId; private boolean correct; private double scoreAtAccusation; private LocalDateTime createdAt=LocalDateTime.now();
    public Accusation(){}
    public Accusation(Long caseId,Long suspectId,boolean correct,double score){this.caseId=caseId;this.suspectId=suspectId;this.correct=correct;this.scoreAtAccusation=score;}
    public Long getId(){return id;} public boolean isCorrect(){return correct;} public double getScoreAtAccusation(){return scoreAtAccusation;}
}
