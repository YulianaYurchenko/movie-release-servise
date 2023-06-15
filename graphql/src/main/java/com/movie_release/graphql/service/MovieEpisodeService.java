package com.movie_release.graphql.service;

import com.movie_release.graphql.model.*;
import com.movie_release.graphql.repository.DislikeRepository;
import com.movie_release.graphql.repository.LikeRepository;
import com.movie_release.graphql.repository.MovieEpisodeRepository;
import com.movie_release.graphql.model.MovieEpisode;
import com.movie_release.graphql.repository.CommentRepository;
import com.movie_release.graphql.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@AllArgsConstructor
@Service
public class MovieEpisodeService {
  private final MovieEpisodeRepository movieEpisodeRepository;
  private final UserRepository userRepository;
  private final CommentRepository commentRepository;
  private final LikeRepository likeRepository;
  private final DislikeRepository dislikeRepository;

  @Cacheable("movieEpisodesCache")
  public List<MovieEpisode> getMovieEpisodes() {
    return movieEpisodeRepository.findAll();
  }

  public MovieEpisode getMovieEpisode(long id) {
    return getMovieEpisodes().stream().filter(movieEpisode -> movieEpisode.getId() == id).findFirst().orElse(null);
  }

  @Transactional
  public MovieEpisode addComment(long id, String text) {
    MovieEpisode movieEpisode = findById(id);
    Comment comment = Comment.builder().text(text).build();

    comment.setUser(getCurrentUser());
    comment.setMovieEpisode(movieEpisode);
    movieEpisode.addComment(comment);

    commentRepository.save(comment);
    return movieEpisodeRepository.save(movieEpisode);
  }

  @Transactional
  public MovieEpisode like(long id) {
    MovieEpisode movieEpisode = findById(id);
    Like like = Like.builder().build();

    like.setUser(getCurrentUser());
    like.setMovieEpisode(movieEpisode);
    movieEpisode.setLikes(movieEpisode.getLikes() + 1);

    likeRepository.save(like);
    return movieEpisodeRepository.save(movieEpisode);
  }

  @Transactional
  public MovieEpisode dislike(long id) {
    MovieEpisode movieEpisode = findById(id);
    Dislike dislike = Dislike.builder().build();

    dislike.setUser(getCurrentUser());
    dislike.setMovieEpisode(movieEpisode);
    movieEpisode.setDislikes(movieEpisode.getDislikes() + 1);

    dislikeRepository.save(dislike);
    return movieEpisodeRepository.save(movieEpisode);
  }

  private MovieEpisode findById(long id) {
    return movieEpisodeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Movie episode not found"));
  }

  private User getCurrentUser() {
    Principal principal = SecurityContextHolder.getContext().getAuthentication();
    return userRepository.findByUsername(principal.getName()).orElseThrow(() -> new EntityNotFoundException("User not found"));
  }
}
