package com.movie_release.graphql.repository;

import com.movie_release.graphql.model.Dislike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DislikeRepository extends JpaRepository<Dislike, Long> {
}
