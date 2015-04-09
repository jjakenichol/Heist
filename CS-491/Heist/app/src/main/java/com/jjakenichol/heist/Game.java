package com.jjakenichol.heist;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by JakeNichol on 4/6/15.
 */
public class Game extends Thread
{
  private Player player;
  private Canvas canvas;
  private Paint paint;

  public Game(Canvas canvas, Paint paint)
  {
    this.canvas = canvas;
    this.paint = paint;

    player = new Player(DrawInterface.displayWidth / 2, DrawInterface.displayHeight / 2, 10);
    player.draw(canvas, paint);
  }

  @Override
  public void run()
  {
    System.out.println("HERE");

    while (true)
    {
      player.update(DrawInterface.getPoints());

      if (System.currentTimeMillis() % 5 == 0)
      {
        player.draw(canvas, paint);
      }
    }
  }
}
