package com.jjakenichol.heist;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.view.View;

/**
 * Created by JakeNichol on 4/14/15.
 */
public class Time extends View
{
  private Rect rect;
  private String time = new String("30");
  public CountDownTimer timer;

  public Time(Context context, final Player player, int left, int top, int right, int bottom)
  {
    super(context);
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

  @Override
  public void onDraw(Canvas canvas)
  {
    DrawInterface.paint.setStrokeWidth(Constants.WALL_WIDTH);
    DrawInterface.paint.setColor(Constants.WALL_COLOR);
    DrawInterface.paint.setStyle(Paint.Style.STROKE);
    canvas.drawRect(rect, DrawInterface.paint);
    DrawInterface.paint.reset();

    DrawInterface.paint.setColor(Constants.WALL_COLOR);
    DrawInterface.paint.setTextSize(Constants.TEXT_SIZE * Constants.SCALE);
    canvas.drawText(time, rect.left + 5, rect.top + DrawInterface.paint.getTextSize(), DrawInterface.paint);
    DrawInterface.paint.reset();
  }

  public Rect getRect()
  {
    return this.rect;
  }
}
