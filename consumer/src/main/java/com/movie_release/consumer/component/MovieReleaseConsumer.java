package com.movie_release.consumer.component;

import com.movie_release.consumer.model.dto.MovieEpisodeDTO;
import com.movie_release.consumer.service.MovieEpisodeService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@AllArgsConstructor
@EnableRabbit
@Component
public class MovieReleaseConsumer {
  private static final Logger LOGGER = Logger.getLogger(MovieReleaseConsumer.class.getName());

  private final MovieEpisodeService movieEpisodeService;

  @RabbitListener(queues = "${rabbit.movies.queue}")
  public void receiveMessage(MovieEpisodeDTO movieEpisodeDTO) {
    LOGGER.info("Received: " + movieEpisodeDTO);
    movieEpisodeService.save(movieEpisodeDTO);
  }
}
