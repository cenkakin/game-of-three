package gameofthree.core.model;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * @author cenk
 */
public class PlayerTest {

  @Test
  public void testMove() {
    //given
    Player player = new Player(1L);
    Integer currentResult = 13;

    //when
    Move move = player.makeMove(currentResult);

    //then
    Assertions.assertThat(move.getAdded()).isEqualTo(-1);
    Assertions.assertThat(move.getPlayer()).isEqualTo(player);
  }
}
