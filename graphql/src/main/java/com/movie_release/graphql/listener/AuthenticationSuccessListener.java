package com.movie_release.graphql.listener;

import com.movie_release.graphql.model.User;
import com.movie_release.graphql.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {
  private final UserRepository userRepository;

  @Override
  public void onApplicationEvent(AuthenticationSuccessEvent event) {
    String email = event.getAuthentication().getName();
    if (userRepository.findByUsername(email).isEmpty()) {
      User user = User.builder()
        .username(email)
        .build();
      userRepository.save(user);
    }
  }
}
