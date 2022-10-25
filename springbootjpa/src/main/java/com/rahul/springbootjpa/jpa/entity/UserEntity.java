package com.rahul.springbootjpa.jpa.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userId")
    private Set<UserCommentEntity> comment;

    @ManyToMany(targetEntity = StoryEntity.class, fetch = FetchType.EAGER )
    @JoinTable(
            name = "user_stories",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name ="story_id", referencedColumnName = "id")}
    )
    private Set<StoryEntity> stories;

    public Set<StoryEntity> getStories() {
        return stories;
    }

    public Set<UserCommentEntity> getComment() {
        return comment;
    }



    public UserEntity() {
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
