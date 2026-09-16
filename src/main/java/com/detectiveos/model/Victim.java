package com.detectiveos.model;
import jakarta.persistence.*;
@Entity
@DiscriminatorValue("VICTIM")
public class Victim extends Person {
    public Victim() {}
    public Victim(String name,int age,String occupation){super(name,age,occupation);}
    @Override public PersonType getPersonType(){return PersonType.VICTIM;}
}
