package com.movie_release.producer.component;

import com.movie_release.producer.config.RabbitConfig;
import com.movie_release.producer.model.MovieEpisode;
import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@AllArgsConstructor
@EnableScheduling
@Component
public class MovieReleaseProducer {

  private static final Logger LOGGER = Logger.getLogger(MovieReleaseProducer.class.getName());

  private final RabbitConfig rabbitConfig;
  private final RabbitTemplate rabbitTemplate;

  @Scheduled(fixedRateString = "${scheduler.interval}")
  public void fetchAndSendNews() {
    WebDriver main_driver = new FirefoxDriver();
    try {
      main_driver.get("https://simpsonsua.tv/");
      WebElement movie_block = main_driver.findElements(By.className("row")).get(3);  // 3 -- "Останні оновлення"
      List<WebElement> movie_posters = movie_block.findElements(By.className("movie_item"));
      for (WebElement movie_poster : movie_posters) {

        String href = movie_poster.findElement(By.tagName("a")).getAttribute("href");

        WebDriver movie_driver = new FirefoxDriver();
        try {
          movie_driver.get(href);

          String episode_name = movie_driver.findElement(By.className("pinktext")).getText();

          List<WebElement> header = movie_driver.findElements(By.className("fullstory"));
          String meta = header.get(0).getText();
          String[] c = meta.split(" ");

          assert c[c.length - 1].equals("українскою");
          assert c[c.length - 2].equals("серія");
          assert isNumeric(c[c.length - 3]);
          assert c[c.length - 4].equals("сезон");
          assert isNumeric(c[c.length - 5]);

          String space = "";
          StringBuilder movie_name_builder = new StringBuilder();
          for (int i = 0; i < c.length - 5; i++) {
            movie_name_builder.append(space);
            movie_name_builder.append(c[i]);
            space = " ";
          }
          String movie_name = movie_name_builder.toString();
          Integer episode_number = Integer.parseInt(c[c.length - 3]);
          Integer season_number = Integer.parseInt(c[c.length - 5]);

          String description = header.get(1).getText();

          int like_number = 0;
          try {
            like_number = Integer.parseInt(movie_driver.findElement(By.className("rate-plus")).findElement(By.tagName("span")).getText());

          } catch (NoSuchElementException ignored) {
          }
          int dislike_number = 0;
          try {

          dislike_number = Integer.parseInt(movie_driver.findElement(By.className("rate-minus")).findElement(By.tagName("span")).getText());

          } catch (NoSuchElementException ignored) {
          }

          MovieEpisode movie_episode = new MovieEpisode(episode_name, movie_name, episode_number, season_number,
                  description, like_number, dislike_number);
          LOGGER.info("Sending: " + movie_episode);
          rabbitTemplate.convertAndSend(rabbitConfig.getTopicExchangeName(), rabbitConfig.getRoutingKey(), movie_episode);
        }
        finally {
          movie_driver.close();
        }
      }
    }
    finally {
      main_driver.close();
    }
  }

  private static boolean isNumeric(String str) {
    try {
      Integer.parseInt(str);
      return true;
    }
    catch(NumberFormatException e) {
      return false;
    }
  }

}
