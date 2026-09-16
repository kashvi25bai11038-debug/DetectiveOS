package com.detectiveos.model;

import jakarta.persistence.*;

@Entity
@Table(name="persons")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="person_type")
public abstract class Person {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Column(nullable=false, length=100)
    protected String name;
    protected int age;
    @Column(length=100)
    protected String occupation;

    protected Person() {}
    protected Person(String name, int age, String occupation) { this.name=name; this.age=age; this.occupation=occupation; }
    public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;}
    public int getAge(){return age;} public void setAge(int v){age=v;} public String getOccupation(){return occupation;} public void setOccupation(String v){occupation=v;}
    public abstract PersonType getPersonType();
    public String describe(){ return name + " (" + occupation + ")"; }
}
