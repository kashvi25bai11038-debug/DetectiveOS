package com.detectiveos.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "cases")
public class CaseFile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 120) private String title;
    @Column(length = 3000) private String description;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private CrimeType crimeType;
    @Column(nullable = false, length = 160) private String location;
    @Column(nullable = false) private LocalDate crimeDate;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private CaseStatus status = CaseStatus.UNSOLVED;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private CaseDifficulty difficulty = CaseDifficulty.MEDIUM;
    @Column(name = "solution_suspect_id") private Long solutionSuspectId;

    @ManyToOne(cascade = CascadeType.ALL) private Victim victim;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinTable(name = "case_suspects",
            joinColumns = @JoinColumn(name = "case_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "suspect_id", nullable = false))
    private List<Suspect> suspects = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinTable(name = "case_witnesses",
            joinColumns = @JoinColumn(name = "case_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "witness_id", nullable = false))
    private List<Witness> witnesses = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinTable(name = "case_evidence",
            joinColumns = @JoinColumn(name = "case_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "evidence_id", nullable = false))
    private List<Evidence> evidence = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinTable(name = "case_statements",
            joinColumns = @JoinColumn(name = "case_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "statement_id", nullable = false))
    private List<Statement> statements = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinTable(name = "case_timeline_events",
            joinColumns = @JoinColumn(name = "case_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "timeline_event_id", nullable = false))
    private List<TimelineEvent> timelineEvents = new ArrayList<>();

    public CaseFile() {}
    public CaseFile(String title, String description, CrimeType crimeType, String location,
                    LocalDate crimeDate, CaseDifficulty difficulty) {
        this.title = title; this.description = description; this.crimeType = crimeType;
        this.location = location; this.crimeDate = crimeDate; this.difficulty = difficulty;
    }

    public Long getId(){return id;}
    public String getTitle(){return title;} public void setTitle(String v){title=v;}
    public String getDescription(){return description;} public void setDescription(String v){description=v;}
    public CrimeType getCrimeType(){return crimeType;} public void setCrimeType(CrimeType v){crimeType=v;}
    public String getLocation(){return location;} public void setLocation(String v){location=v;}
    public LocalDate getCrimeDate(){return crimeDate;} public void setCrimeDate(LocalDate v){crimeDate=v;}
    public CaseStatus getStatus(){return status;} public void setStatus(CaseStatus v){status=v;}
    public CaseDifficulty getDifficulty(){return difficulty;} public void setDifficulty(CaseDifficulty v){difficulty=v;}
    public Long getSolutionSuspectId(){return solutionSuspectId;} public void setSolutionSuspectId(Long v){solutionSuspectId=v;}
    public Victim getVictim(){return victim;} public void setVictim(Victim v){victim=v;}
    public List<Suspect> getSuspects(){return suspects;} public List<Witness> getWitnesses(){return witnesses;}
    public List<Evidence> getEvidence(){return evidence;} public List<Statement> getStatements(){return statements;}
    public List<TimelineEvent> getTimelineEvents(){return timelineEvents;}
    public void addSuspect(Suspect s){suspects.add(s);} public void addWitness(Witness w){witnesses.add(w);}
    public void addEvidence(Evidence e){evidence.add(e);} public void addStatement(Statement s){statements.add(s);}
    public void addTimelineEvent(TimelineEvent e){timelineEvents.add(e);}
    @Override public String toString(){return "#" + id + "  •  " + title;}
}
