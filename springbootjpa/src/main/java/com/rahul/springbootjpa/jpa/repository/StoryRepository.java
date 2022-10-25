package com.rahul.springbootjpa.jpa.repository;

import com.rahul.springbootjpa.jpa.entity.StoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StoryRepository extends JpaRepository<StoryEntity, Long> {

    @Query("select ")
    List<StoryEntity> findWithStoriesBy();

}
