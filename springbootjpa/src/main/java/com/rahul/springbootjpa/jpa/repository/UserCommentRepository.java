package com.rahul.springbootjpa.jpa.repository;

import com.rahul.springbootjpa.jpa.entity.UserCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCommentRepository extends JpaRepository<UserCommentEntity, Long> {
}
