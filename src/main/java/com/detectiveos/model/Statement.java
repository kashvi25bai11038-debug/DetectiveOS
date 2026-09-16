package com.detectiveos.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name="statements")
public class Statement {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    private Long personId;
    private String speakerName;
    @Column(length=3000) private String content;
    private LocalDateTime recordedAt;
    private boolean contradictionDetected;
    public Statement(){}
    public Statement(Long personId,String speakerName,String content,LocalDateTime recordedAt){this.personId=personId;this.speakerName=speakerName;this.content=content;this.recordedAt=recordedAt;}
    public Long getId(){return id;} public Long getPersonId(){return personId;} public void setPersonId(Long v){personId=v;} public String getSpeakerName(){return speakerName;}
    public String getContent(){return content;} public LocalDateTime getRecordedAt(){return recordedAt;}
    public boolean isContradictionDetected(){return contradictionDetected;} public void setContradictionDetected(boolean v){contradictionDetected=v;}
}
