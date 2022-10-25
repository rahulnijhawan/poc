package com.rahul.springbootjpa.jpa.repository;

import com.rahul.springbootjpa.jpa.entity.StoryEntity;
import com.rahul.springbootjpa.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    List<StoryEntity> findWithStoriesBy();
}
