package com.jjakenichol.heist;

/**
 * Created by JakeNichol on 4/6/15.
 */
public class Game extends Thread
{
  private Player player;

  public Game()
  {
    player = new Player(DrawInterface.displayWidth / 2, DrawInterface.displayHeight / 2, 50, DrawInterface.getPoints());
    player.draw();
  }

  @Override
  public void run()
  {
    while (true)
    {
      player.draw();

      if (System.currentTimeMillis() % 500 == 0)
      {
        player.update();
      }
    }
  }
}
