package com.movie_release.graphql.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movie_episodes")
public class MovieEpisode implements Serializable {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private long id;
  private String episodeName;
  private String movieName;
  private int episodeNumber;
  private int seasonNumber;
  private String description;
  private int likes;
  private int dislikes;

  @OneToMany(mappedBy = "movieEpisode", fetch = EAGER)
  private List<Comment> comments;

  public void addComment(Comment comment) {
    comments.add(comment);
  }
}
