package com.sparta.hanghaejwt.repository;

import com.sparta.hanghaejwt.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
