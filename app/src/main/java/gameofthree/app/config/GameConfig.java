package gameofthree.app.config;

import gameofthree.core.model.Game;
import gameofthree.core.service.GameOperatorServiceImpl;
import gameofthree.core.service.PlayerServiceImpl;
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
  public EmitterProcessor<Game> updatedGameEmitter() {
    return EmitterProcessor.create();
  }

  @Bean
  public GameOperatorServiceImpl gameService(EmitterProcessor<Game> updatedGameEmitter) {
    PlayerService playerService = new PlayerServiceImpl();
    return new GameOperatorServiceImpl(playerService, updatedGameEmitter);
  }
}
