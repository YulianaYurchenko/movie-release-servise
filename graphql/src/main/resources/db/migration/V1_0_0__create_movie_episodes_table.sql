CREATE TABLE movie_episodes (
  id BIGSERIAL,
  episode_name VARCHAR(255),
  movie_name VARCHAR(255),
  episode_number INT,
  season_number INT,
  description VARCHAR(1023),
  likes INT,
  dislikes INT,
  CONSTRAINT movie_episodes_pk PRIMARY KEY (id)
);