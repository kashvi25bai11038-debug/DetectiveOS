package com.detectiveos.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name="timeline_events")
public class TimelineEvent {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private LocalDateTime eventTime;
    @Column(nullable=false,length=2000) private String description;
    private String location;
    private Long relatedPersonId;
    public TimelineEvent(){}
    public TimelineEvent(LocalDateTime eventTime,String description,String location,Long relatedPersonId){this.eventTime=eventTime;this.description=description;this.location=location;this.relatedPersonId=relatedPersonId;}
    public Long getId(){return id;} public LocalDateTime getEventTime(){return eventTime;} public String getDescription(){return description;}
    public String getLocation(){return location;} public Long getRelatedPersonId(){return relatedPersonId;} public void setRelatedPersonId(Long v){relatedPersonId=v;}
}
