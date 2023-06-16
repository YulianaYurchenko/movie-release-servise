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
    WebDriver mainDriver = new FirefoxDriver();
    try {
      mainDriver.get("https://simpsonsua.tv/");
      WebElement movieBlock = mainDriver.findElements(By.className("row")).get(3);  // 3 -- "Останні оновлення"

      List<WebElement> episodePosters = movieBlock.findElements(By.className("movie_item"));
      for (WebElement episodePoster : episodePosters) {

        String episode_href = episodePoster.findElement(By.tagName("a")).getAttribute("href");

        WebDriver movieDriver = new FirefoxDriver();
        try {
          movieDriver.get(episode_href);

          String episodeName = movieDriver.findElement(By.className("pinktext")).getText();

          List<WebElement> header = movieDriver.findElements(By.className("fullstory"));

          String[] metaWords = parseEpisodeMeta(header.get(0).getText());

          String movieName = parseMovieName(metaWords);

          int l = metaWords.length;
          Integer episodeNumber = Integer.parseInt(metaWords[l - 3]);
          Integer seasonNumber = Integer.parseInt(metaWords[l - 5]);

          String description = header.get(1).getText();

          Integer likes = parseRate(movieDriver, "rate-plus");
          Integer dislikes = parseRate(movieDriver, "rate-minus");

          MovieEpisode movieEpisode = new MovieEpisode(episodeName, movieName, episodeNumber, seasonNumber,
                  description, likes, dislikes);
          LOGGER.info("Sending: " + movieEpisode);
          rabbitTemplate.convertAndSend(rabbitConfig.getTopicExchangeName(), rabbitConfig.getRoutingKey(), movieEpisode);
        }
        finally {
          movieDriver.close();
        }
      }
    }
    finally {
      mainDriver.close();
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

  private static Integer parseRate(WebDriver driver, String rateKey) {
    try {

      String rateText = driver.findElement(By.className(rateKey)).findElement(By.tagName("span")).getText();
      return Integer.parseInt(rateText);

    } catch (NoSuchElementException ignored) {
      return 0;
    }
  }

  private static String[] parseEpisodeMeta(String meta) {
    String[] metaWords = meta.split(" ");

    int l = metaWords.length;
    assert metaWords[l - 1].equals("українскою");
    assert metaWords[l - 2].equals("серія");
    assert metaWords[l - 4].equals("сезон");
    assert isNumeric(metaWords[l - 3]);
    assert isNumeric(metaWords[l - 5]);

    return metaWords;
  }

  private static String parseMovieName(String[] metaWords) {
    String space = "";
    StringBuilder movieNameBuilder = new StringBuilder();

    for (int i = 0; i < metaWords.length - 5; i++) {
      movieNameBuilder.append(space);
      movieNameBuilder.append(metaWords[i]);
      space = " ";
    }
    return movieNameBuilder.toString();
  }

}
