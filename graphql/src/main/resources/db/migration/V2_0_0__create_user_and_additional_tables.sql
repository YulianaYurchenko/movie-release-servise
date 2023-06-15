CREATE TABLE users (
  id BIGSERIAL,
  username VARCHAR(50) NOT NULL,
  CONSTRAINT users_pk PRIMARY KEY (id),
  CONSTRAINT username_key UNIQUE (username)
);

CREATE TABLE likes (
  id BIGSERIAL,
  user_id BIGINT,
  movie_episode_id BIGINT,
  CONSTRAINT likes_pk PRIMARY KEY (id),
  CONSTRAINT likes_users_fk FOREIGN KEY (user_id) REFERENCES users(id),
  CONSTRAINT likes_movie_episodes_fk FOREIGN KEY (movie_episode_id) REFERENCES movie_episodes(id),
  CONSTRAINT likes_user_id_movie_episode_id_key UNIQUE (user_id, movie_episode_id)
);

CREATE TABLE dislikes (
  id BIGSERIAL,
  user_id BIGINT,
  movie_episode_id BIGINT,
  CONSTRAINT dislikes_pk PRIMARY KEY (id),
  CONSTRAINT dislikes_users_fk FOREIGN KEY (user_id) REFERENCES users(id),
  CONSTRAINT dislikes_movie_episodes_fk FOREIGN KEY (movie_episode_id) REFERENCES movie_episodes(id),
  CONSTRAINT dislikes_user_id_movie_episode_id_key UNIQUE (user_id, movie_episode_id)
);

CREATE TABLE comments (
  id BIGSERIAL,
  text VARCHAR(255) NOT NULL,
  user_id BIGINT,
  movie_episode_id BIGINT,
  CONSTRAINT comments_pk PRIMARY KEY (id),
  CONSTRAINT comments_users_fk FOREIGN KEY (user_id) REFERENCES users(id),
  CONSTRAINT comments_movie_episodes_fk FOREIGN KEY (movie_episode_id) REFERENCES movie_episodes(id)
);
