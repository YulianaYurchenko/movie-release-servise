package com.movie_release.graphql.repository;

import com.movie_release.graphql.model.MovieEpisode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieEpisodeRepository extends JpaRepository<MovieEpisode, Long> {
}
