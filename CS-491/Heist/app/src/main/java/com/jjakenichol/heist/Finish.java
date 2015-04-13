package com.jjakenichol.heist;

import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by JakeNichol on 4/13/15.
 */
public class Finish
{
  private Rect rect;
  private int left, top = -1;

  public Finish(int left, int top, int right, int bottom)
  {
    this.left = left;
    this.top = top;

    rect = new Rect(left, top, right, bottom);

    // Adjust to scale
    rect.left = (int) (rect.left * Constants.SCALE);
    rect.top = (int) (rect.top * Constants.SCALE);
    rect.right = (int) (rect.right * Constants.SCALE);
    rect.bottom = (int) (rect.bottom * Constants.SCALE);
  }

  public void draw()
  {
    DrawInterface.paint.setStrokeWidth(Constants.WALL_WIDTH);
    DrawInterface.paint.setColor(Constants.FINISH_COLOR);
    DrawInterface.paint.setStyle(Paint.Style.STROKE);
    DrawInterface.canvas.drawRect(rect, DrawInterface.paint);
    DrawInterface.paint.reset();

    DrawInterface.paint.setColor(Constants.FINISH_COLOR);
    DrawInterface.paint.setTextSize(Constants.FINISH_TEXT_SIZE * Constants.SCALE);
    DrawInterface.canvas.drawText("END", rect.left, rect.bottom + DrawInterface.paint.getTextSize(), DrawInterface.paint);
    DrawInterface.paint.reset();
  }

  public Rect getRect()
  {
    return this.rect;
  }

  public int getLeft()
  {
    return this.left;
  }

  public int getTop()
  {
    return this.top;
  }
}
