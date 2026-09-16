package com.detectiveos.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 60)
    private String username;
    @Column(nullable = false, length = 128)
    private String passwordHash;
    @Column(nullable = false, length = 30)
    private String role;

    public User() {}
    public User(String username, String passwordHash, String role) { this.username=username; this.passwordHash=passwordHash; this.role=role; }
    public Long getId(){return id;} public String getUsername(){return username;} public void setUsername(String v){username=v;}
    public String getPasswordHash(){return passwordHash;} public void setPasswordHash(String v){passwordHash=v;}
    public String getRole(){return role;} public void setRole(String v){role=v;}
}
