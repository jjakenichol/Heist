package com.jjakenichol.heist;

import android.content.Context;
import android.graphics.Canvas;

/**
 * Created by JakeNichol on 4/6/15.
 */
public class Game extends Thread
{
  private Player player;
  private Canvas canvas;

  public Game(Context context, Canvas canvas, Map map)
  {
    this.canvas = canvas;
    player = new Player(context, map, DrawInterface.displayWidth - 75, DrawInterface.displayHeight - Constants.PLAYER_SIZE, Constants.PLAYER_SIZE,
            DrawInterface.getPoints());
//    player.draw();
  }

  @Override
  public void run()
  {
    while (true)
    {
      player.draw(canvas);

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
