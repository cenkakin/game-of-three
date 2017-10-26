package gameofthree.core.event;

import gameofthree.core.model.Game;

/**
 * @author cenk
 */
public class GameUpdatedEvent {

  private final Game updatedGame;

  public GameUpdatedEvent(Game updatedGame) {
    this.updatedGame = updatedGame;
  }

  public Game getUpdatedGame() {
    return updatedGame;
  }
}
