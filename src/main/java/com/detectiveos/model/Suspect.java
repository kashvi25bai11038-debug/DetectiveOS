package com.detectiveos.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("SUSPECT")
public class Suspect extends Person {
    private String motive;
    private String alibi;
    private double suspicionScore;

    public Suspect() {}
    public Suspect(String name, int age, String occupation, String motive, String alibi) {
        super(name, age, occupation); this.motive=motive; this.alibi=alibi;
    }
    @Override public PersonType getPersonType(){return PersonType.SUSPECT;}
    @Override public String describe(){ return "Suspect: " + super.describe(); }
    public String getMotive(){return motive;} public void setMotive(String v){motive=v;}
    public String getAlibi(){return alibi;} public void setAlibi(String v){alibi=v;}
    public double getSuspicionScore(){return suspicionScore;} public void setSuspicionScore(double v){suspicionScore=v;}
    @Override public String toString(){return name;}
}
