package gameofthree.app.manager;

import gameofthree.app.dto.UpdatedGameDto;
import gameofthree.app.factory.UpdatedGameDtoFactory;
import gameofthree.core.event.GameUpdatedEvent;
import gameofthree.core.model.Game;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;

/**
 * @author cenk
 */
public class ReactiveManager {

  private final EmitterProcessor<Game> emitter;

  public ReactiveManager(EmitterProcessor<Game> emitter) {
    this.emitter = emitter;
  }

  @Async
  @EventListener
  public void emitGameUpdatedEvent(GameUpdatedEvent event) {
    emitter.onNext(event.getUpdatedGame());
  }

  public Flux<UpdatedGameDto> logGameUpdates(Long gameId) {
    return emitter.log()
                  .filter(e -> gameId.equals(e.getId()))
                  .map(UpdatedGameDtoFactory::create);
  }
}
