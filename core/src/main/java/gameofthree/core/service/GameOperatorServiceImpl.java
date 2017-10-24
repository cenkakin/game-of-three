package gameofthree.core.service;

import gameofthree.core.exception.GameNotFoundException;
import gameofthree.core.exception.GameOverException;
import gameofthree.core.exception.WrongPlayerException;
import gameofthree.core.model.Game;
import gameofthree.core.model.Move;
import gameofthree.core.model.Player;
import gameofthree.core.vo.RegisterGameVo;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import reactor.core.publisher.EmitterProcessor;

/**
 * @author cenk
 */
public class GameOperatorServiceImpl implements GameOperatorService {

  private final static Map<Long, Game> GAME_REGISTRY = new ConcurrentHashMap<>();

  private final PlayerService playerService;

  private final EmitterProcessor<Game> updatedGameEmitter;

  private AtomicLong gameIdSequence;

  public GameOperatorServiceImpl(PlayerService playerService,
                                 EmitterProcessor<Game> updatedGameEmitter) {
    this.playerService = playerService;
    this.updatedGameEmitter = updatedGameEmitter;
    this.gameIdSequence = new AtomicLong();
  }

  @Override
  public synchronized RegisterGameVo register() {
    Player player = playerService.newPlayer();
    Optional<Game> waitingGame = getWaitingGame();
    waitingGame.ifPresent(startGame(player));
    Game game = waitingGame.orElseGet(() -> newGame(player));
    return new RegisterGameVo(game, player);
  }

  @Override
  public Move makeMove(Long gameId, Long playerId)
      throws GameNotFoundException, GameOverException, WrongPlayerException {
    Game game = getGameOrThrow(gameId);
    checkGameContinues(game);
    Player nextPlayer = game.whoIsTurn();
    checkPlayerTurn(nextPlayer.getId(), playerId);
    Move move = nextPlayer.makeMove(game.getLatestMove().getResult());
    game.setLatestMove(move);
    emitUpdatedGameEvent(game);
    return move;
  }

  private Game getGameOrThrow(Long gameId) throws GameNotFoundException {
    Game game = GAME_REGISTRY.get(gameId);
    if (game == null) {
      throw new GameNotFoundException();
    }
    return game;
  }

  private void checkGameContinues(Game game) throws GameOverException {
    if (game.isFinished()) {
      throw new GameOverException();
    }
  }

  private void checkPlayerTurn(Long nextPlayerId, Long playerId) throws WrongPlayerException {
    if (!playerId.equals(nextPlayerId)) {
      throw new WrongPlayerException();
    }
  }

  private Consumer<Game> startGame(Player player) {
    return game -> {
      game.setSecondPlayer(player);
      Player firstPlayer = game.getFirstPlayer();
      Move move = firstPlayer.makeFirstMove();
      game.setLatestMove(move);
      emitUpdatedGameEvent(game);
    };
  }

  private void emitUpdatedGameEvent(Game game) {
    updatedGameEmitter.onNext(game);
  }

  private Game newGame(Player player) {
    Long gameId = gameIdSequence.incrementAndGet();
    Game game = new Game();
    game.setId(gameId);
    game.setFirstPlayer(player);
    GAME_REGISTRY.put(gameId, game);
    return game;
  }

  private Optional<Game> getWaitingGame() {
    return GAME_REGISTRY.values()
                        .stream()
                        .filter(game -> game.getSecondPlayer() == null)
                        .findFirst();
  }
}
