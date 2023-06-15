package com.movie_release.consumer.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.movie_release.consumer.model.entity.MovieEpisode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MovieEpisodeDTO {
  private String episodeName;
  private String movieName;
  private int episodeNumber;
  private int seasonNumber;
  private String description;
  private int likes;
  private int dislikes;

  public MovieEpisode toMovieEpisode() {
    return MovieEpisode.builder()
            .episodeName(episodeName)
            .movieName(movieName)
            .episodeNumber(episodeNumber)
            .seasonNumber(seasonNumber)
            .description(description)
            .likes(likes)
            .dislikes(dislikes)
            .build();
  }
}
