package com.movie_release.graphql.repository;

import com.movie_release.graphql.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
