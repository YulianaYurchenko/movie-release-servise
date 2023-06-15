package com.movie_release.consumer.repository;

import com.movie_release.consumer.model.entity.MovieEpisode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieEpisodeRepository extends JpaRepository<MovieEpisode, Long> {
  Optional<MovieEpisode> findByEpisodeName(String episodeName);
}
