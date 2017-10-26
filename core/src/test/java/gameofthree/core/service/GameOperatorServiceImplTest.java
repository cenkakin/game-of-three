package gameofthree.core.service;

import gameofthree.core.exception.GameNotFoundException;
import gameofthree.core.exception.GameOverException;
import gameofthree.core.exception.WrongPlayerException;
import gameofthree.core.model.Game;
import gameofthree.core.model.Move;
import gameofthree.core.model.Player;
import gameofthree.core.vo.RegisterGameVo;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationEventPublisher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

/**
 * @author cenk
 */
public class GameOperatorServiceImplTest {

  private GameOperatorService gameOperatorService;

  @Before
  public void init() {
    ApplicationEventPublisher mock = Mockito.mock(ApplicationEventPublisher.class);
    gameOperatorService = new GameOperatorServiceImpl(new PlayerServiceImpl(), mock);
  }

  @After
  public void after() throws NoSuchFieldException, IllegalAccessException {
    Field field = GameOperatorServiceImpl.class.getDeclaredField("GAME_REGISTRY");
    field.setAccessible(true);
    Field modifiersField = Field.class.getDeclaredField("modifiers");
    modifiersField.setAccessible(true);
    modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
    field.set(null, new ConcurrentHashMap<>());
  }

  @Test
  public void testFirstTimeRegister() {
    //when
    RegisterGameVo register = register();

    //then
    Game game = register.getGame();
    Player player = register.getPlayer();
    assertThat(game.getFirstPlayer()).isNotNull();
    assertThat(game.getFirstPlayer()).isEqualTo(player);
    assertThat(game.getLatestMove()).isNull();
    assertThat(game.getSecondPlayer()).isNull();
  }

  @Test
  public void testStartGameAndFirstMove() {
    //given
    register();

    //when
    RegisterGameVo secondRegister = register();

    //then
    Game game = secondRegister.getGame();
    Player player = secondRegister.getPlayer();
    assertThat(game.getFirstPlayer()).isNotNull();
    assertThat(game.getSecondPlayer()).isNotNull();
    assertThat(game.getSecondPlayer()).isEqualTo(player);
    Move latestMove = game.getLatestMove();
    assertThat(latestMove).isNotNull();
    assertThat(latestMove.getResult()).isNotZero();
    assertThat(latestMove.getAdded()).isNotNull();
    assertThat(latestMove.getPlayer()).isEqualTo(game.getFirstPlayer());
  }

  @Test
  public void testMakeMoveSuccessfully() throws GameNotFoundException, WrongPlayerException, GameOverException {
    //given
    register();
    RegisterGameVo game = register();
    Long gameId = game.getGame().getId();
    Long playerId = game.getPlayer().getId();

    //when
    Move move = gameOperatorService.makeMove(gameId, playerId);

    //then
    assertThat(move).isNotNull();
    assertThat(move.getPlayer().getId()).isEqualTo(playerId);
    assertThat(move.getResult()).isPositive();
  }

  @Test
  public void testWrongPlayerExceptionWhenWrongPlayerMakeMove()
      throws GameNotFoundException, WrongPlayerException, GameOverException {
    //given
    register();
    RegisterGameVo game = register();
    Long gameId = game.getGame().getId();
    Long playerId = game.getPlayer().getId();
    Long wrongPlayerId = playerId + 1L;

    //when
    Throwable thrown = catchThrowable(() -> gameOperatorService.makeMove(gameId, wrongPlayerId));

    //then
    assertThat(thrown).isInstanceOf(WrongPlayerException.class)
                      .hasMessageContaining("It is not your turn now!");
  }

  @Test
  public void testGameNotFoundWhenMakeMove() throws GameNotFoundException, WrongPlayerException, GameOverException {
    //given
    Long gameId = 100L;
    Long playerId = 100L;

    //when
    Throwable thrown = catchThrowable(() -> gameOperatorService.makeMove(gameId, playerId));

    //then
    assertThat(thrown).isInstanceOf(GameNotFoundException.class)
                      .hasMessageContaining("Game Not Found!");
  }

  private RegisterGameVo register() {
    return gameOperatorService.register();
  }
}
