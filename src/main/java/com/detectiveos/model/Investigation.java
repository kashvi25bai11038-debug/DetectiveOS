package com.detectiveos.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name="investigations")
public class Investigation {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    private Long caseId;
    private Long detectiveId;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private String result;
    public Investigation(){}
    public Investigation(Long caseId,Long detectiveId){this.caseId=caseId;this.detectiveId=detectiveId;this.startedAt=LocalDateTime.now();}
    public Long getId(){return id;} public Long getCaseId(){return caseId;} public Long getDetectiveId(){return detectiveId;}
    public LocalDateTime getStartedAt(){return startedAt;} public LocalDateTime getEndedAt(){return endedAt;} public String getResult(){return result;}
    public void close(String result){this.result=result;this.endedAt=LocalDateTime.now();}
}
