package com.jjakenichol.heist;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

/**
 * Created by JakeNichol on 4/13/15.
 */
public class Finish extends View
{
  private Rect rect;
  private int left, top = -1;

  public Finish(Context context, int left, int top, int right, int bottom)
  {
    super(context);
    this.left = left;
    this.top = top;

    rect = new Rect(left, top, right, bottom);

    // Adjust to scale
    rect.left = (int) (rect.left * Constants.SCALE);
    rect.top = (int) (rect.top * Constants.SCALE);
    rect.right = (int) (rect.right * Constants.SCALE);
    rect.bottom = (int) (rect.bottom * Constants.SCALE);
  }

  @Override
  public void draw(Canvas canvas)
  {
    DrawInterface.paint.setStrokeWidth(Constants.WALL_WIDTH);
    DrawInterface.paint.setColor(Constants.FINISH_COLOR);
    DrawInterface.paint.setStyle(Paint.Style.STROKE);
    canvas.drawRect(rect, DrawInterface.paint);
    DrawInterface.paint.reset();

    DrawInterface.paint.setColor(Constants.FINISH_COLOR);
    DrawInterface.paint.setTextSize(Constants.TEXT_SIZE * Constants.SCALE);
    canvas.drawText("END", rect.left, rect.bottom + DrawInterface.paint.getTextSize(), DrawInterface.paint);
    DrawInterface.paint.reset();
  }

  public Rect getRect()
  {
    return this.rect;
  }

//  public int getLeftSide()
//  {
//    return this.left;
//  }
//
//  public int getTopSide()
//  {
//    return this.top;
//  }
}
