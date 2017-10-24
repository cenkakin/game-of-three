package gameofthree.app.dto;

import gameofthree.core.model.Move;

/**
 * @author cenk
 */
public class RegisterGameDto {

  private final Long gameId;

  private final Long playerId;

  private final Move firstMove;

  public RegisterGameDto(Long gameId, Long playerId, Move firstMove) {
    this.gameId = gameId;
    this.playerId = playerId;
    this.firstMove = firstMove;
  }

  public Long getGameId() {
    return gameId;
  }

  public Long getPlayerId() {
    return playerId;
  }

  public Move getFirstMove() {
    return firstMove;
  }
}
