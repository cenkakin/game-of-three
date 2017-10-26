package gameofthree.app.config;

import gameofthree.app.manager.ReactiveManager;
import gameofthree.core.service.GameOperatorServiceImpl;
import gameofthree.core.service.PlayerServiceImpl;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.EmitterProcessor;

import static gameofthree.core.service.GameOperatorService.PlayerService;

/**
 * @author cenk
 */
@Configuration
public class GameConfig {

  @Bean
  public ReactiveManager reactiveManager() {
    return new ReactiveManager(EmitterProcessor.create());
  }

  @Bean
  public GameOperatorServiceImpl gameService(ApplicationEventPublisher applicationEventPublisher) {
    PlayerService playerService = new PlayerServiceImpl();
    return new GameOperatorServiceImpl(playerService, applicationEventPublisher);
  }
}
