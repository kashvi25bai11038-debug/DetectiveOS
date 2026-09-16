package com.detectiveos.model;
import jakarta.persistence.*;
@Entity
@DiscriminatorValue("WITNESS")
public class Witness extends Person {
    @Column(length=2000) private String notes;
    public Witness() {}
    public Witness(String name,int age,String occupation,String notes){super(name,age,occupation);this.notes=notes;}
    @Override public PersonType getPersonType(){return PersonType.WITNESS;}
    public String getNotes(){return notes;} public void setNotes(String v){notes=v;}
}
