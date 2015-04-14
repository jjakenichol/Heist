package com.jjakenichol.heist;

import android.graphics.Paint;
import android.graphics.Rect;
import android.os.CountDownTimer;

/**
 * Created by JakeNichol on 4/14/15.
 */
public class Time
{
  private Rect rect;
  private String time = new String("");
  public CountDownTimer timer;

  public Time(final Player player, int left, int top, int right, int bottom)
  {
    rect = new Rect(left, top, right, bottom);

    // Adjust to scale
    rect.left = (int) (rect.left * Constants.SCALE);
    rect.top = (int) (rect.top * Constants.SCALE);
    rect.right = (int) (rect.right * Constants.SCALE);
    rect.bottom = (int) (rect.bottom * Constants.SCALE);

    timer = new CountDownTimer(30000, 1000)
    {
      public void onTick(long millisUntilFinished)
      {
        time = "" + millisUntilFinished / 1000;
      }

      public void onFinish()
      {
        player.hasLost = true;
      }
    };
  }

  public void draw()
  {
    DrawInterface.paint.setStrokeWidth(Constants.WALL_WIDTH);
    DrawInterface.paint.setColor(Constants.WALL_COLOR);
    DrawInterface.paint.setStyle(Paint.Style.STROKE);
    DrawInterface.canvas.drawRect(rect, DrawInterface.paint);
    DrawInterface.paint.reset();

    DrawInterface.paint.setColor(Constants.WALL_COLOR);
    DrawInterface.paint.setTextSize(Constants.TEXT_SIZE * Constants.SCALE);
    DrawInterface.canvas.drawText(time, rect.left + 5, rect.top + DrawInterface.paint.getTextSize(), DrawInterface.paint);
    DrawInterface.paint.reset();
  }

  public Rect getRect()
  {
    return this.rect;
  }
}
