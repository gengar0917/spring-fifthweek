package com.sparta.hanghaejwt.repository;

import com.sparta.hanghaejwt.entity.Board;
import com.sparta.hanghaejwt.entity.BoardLike;
import com.sparta.hanghaejwt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
    Optional<BoardLike> findByBoardAndUser(Board board, User user);
}
