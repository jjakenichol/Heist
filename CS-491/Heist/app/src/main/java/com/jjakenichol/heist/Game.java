package com.jjakenichol.heist;

/**
 * Created by JakeNichol on 4/6/15.
 */
public class Game extends Thread
{
  private Player player;

  public Game(Map map)
  {
    player = new Player(map, DrawInterface.displayWidth - 75, DrawInterface.displayHeight - Constants.PLAYER_SIZE, Constants.PLAYER_SIZE,
            DrawInterface.getPoints());
    player.draw();
  }

  @Override
  public void run()
  {
    while (true)
    {
      player.draw();

      if (System.currentTimeMillis() % 50 == 0)
      {
        player.update();
        DrawInterface.repaint();
      }
    }
  }

  public Player getPlayer()
  {
    return player;
  }
}
