package com.movie_release.producer.model;

public record MovieEpisode(
  String episodeName,
  String movieName,
  Integer episodeNumber,
  Integer seasonNumber,
  String description,
  Integer likes,
  Integer dislikes
) {}
