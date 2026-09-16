package com.detectiveos.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="evidence")
public class Evidence {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false,length=100) private String code;
    @Enumerated(EnumType.STRING) private EvidenceType type;
    @Column(nullable=false,length=2000) private String description;
    private String location;
    private LocalDateTime discoveredAt;
    private int reliability = 50;
    private Long linkedSuspectId;

    public Evidence() {}
    public Evidence(String code,EvidenceType type,String description,String location,LocalDateTime discoveredAt,int reliability){
        this.code=code;this.type=type;this.description=description;this.location=location;this.discoveredAt=discoveredAt;this.reliability=reliability;
    }
    public Long getId(){return id;} public String getCode(){return code;} public void setCode(String v){code=v;}
    public EvidenceType getType(){return type;} public void setType(EvidenceType v){type=v;}
    public String getDescription(){return description;} public void setDescription(String v){description=v;}
    public String getLocation(){return location;} public void setLocation(String v){location=v;}
    public LocalDateTime getDiscoveredAt(){return discoveredAt;} public void setDiscoveredAt(LocalDateTime v){discoveredAt=v;}
    public int getReliability(){return reliability;} public void setReliability(int v){reliability=v;}
    public Long getLinkedSuspectId(){return linkedSuspectId;} public void setLinkedSuspectId(Long v){linkedSuspectId=v;}
    @Override public String toString(){return code+" | "+type+" | "+description+" | reliability="+reliability;}
}
