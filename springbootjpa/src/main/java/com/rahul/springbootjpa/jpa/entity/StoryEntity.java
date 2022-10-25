package com.rahul.springbootjpa.jpa.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "story")
public class StoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "stories", fetch = FetchType.EAGER)
    // creating a infinite loop and stack overflow
    private Set<UserEntity> users;

    public Set<UserEntity> getUsers() {
        return users;
    }

    public StoryEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String userName) {
        this.name = userName;
    }
}
