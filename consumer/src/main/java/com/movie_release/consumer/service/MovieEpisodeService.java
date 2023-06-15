package com.movie_release.consumer.service;

import com.movie_release.consumer.model.dto.MovieEpisodeDTO;
import com.movie_release.consumer.model.entity.MovieEpisode;
import com.movie_release.consumer.repository.MovieEpisodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MovieEpisodeService {
  private final MovieEpisodeRepository repository;

  public void save(MovieEpisodeDTO movieEpisodeDTO) {
    if (repository.findByEpisodeName(movieEpisodeDTO.getEpisodeName()).isEmpty()) {
      MovieEpisode movieEpisode = movieEpisodeDTO.toMovieEpisode();
      repository.save(movieEpisode);
    }
  }
}
