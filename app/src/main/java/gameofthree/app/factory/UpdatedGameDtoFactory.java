package gameofthree.app.factory;

import gameofthree.app.dto.UpdatedGameDto;
import gameofthree.core.model.Game;
import gameofthree.core.model.Move;

/**
 * @author cenk
 */
public class UpdatedGameDtoFactory {

  public static UpdatedGameDto create(Game updatedGame) {
    Move latestMove = updatedGame.getLatestMove();
    Long latestMovePlayerId = latestMove.getPlayer().getId();
    Long nextMovePlayerId = updatedGame.getFirstPlayer().getId();
    if (latestMovePlayerId.equals(updatedGame.getFirstPlayer().getId())) {
      nextMovePlayerId = updatedGame.getSecondPlayer().getId();
    }
    return new UpdatedGameDto(updatedGame.isFinished(),
                              latestMovePlayerId,
                              nextMovePlayerId,
                              latestMove.getResult(),
                              latestMove.getAdded()
    );
  }
}
