package com.movie_release.graphql.controller;

import com.movie_release.graphql.model.MovieEpisode;
import com.movie_release.graphql.service.MovieEpisodeService;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@AllArgsConstructor
@Controller
public class MovieEpisodeController {

  private final MovieEpisodeService service;

  @QueryMapping
  public List<MovieEpisode> getMovieEpisodes() {
    return service.getMovieEpisodes();
  }

  @QueryMapping
  public MovieEpisode getMovieEpisode(@Argument long id) {
    return service.getMovieEpisode(id);
  }

  @MutationMapping
  public MovieEpisode addComment(@Argument long id, @Argument String text) {
    return service.addComment(id, text);
  }

  @MutationMapping
  public MovieEpisode like(@Argument long id) {
    return service.like(id);
  }

  @MutationMapping
  public MovieEpisode dislike(@Argument long id) {
    return service.dislike(id);
  }
}
