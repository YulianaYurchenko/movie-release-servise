package com.movie_release.graphql.repository;

import com.movie_release.graphql.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
