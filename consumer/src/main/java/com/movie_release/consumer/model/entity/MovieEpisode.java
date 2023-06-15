package com.movie_release.consumer.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movie_episodes")
public class MovieEpisode {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;
  private String episodeName;
  private String movieName;
  private int episodeNumber;
  private int seasonNumber;
  private String description;
  private int likes;
  private int dislikes;
}
