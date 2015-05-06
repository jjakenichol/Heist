package com.jjakenichol.heist;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

/**
 * Created by JakeNichol on 4/9/15.
 */
public class Wall extends View
{
  private Rect rect;
  private Rect bufferRect;
  private int left, top, length = -1;

  public Wall(Context context, int left, int top, Orientation orientation, int length)
  {
    super(context);
    this.left = left;
    this.top = top;
    this.length = length;

    rect = new Rect(left, top, left + Constants.WALL_WIDTH, top + Constants.WALL_WIDTH);
    bufferRect = new Rect(left + Constants.WALL_BUFFER, top + Constants.WALL_BUFFER, left + Constants.WALL_WIDTH + Constants.WALL_BUFFER,
            top + Constants.WALL_WIDTH + Constants.WALL_BUFFER);

    if (orientation == Orientation.Horizontal) rect.right = left + length;
    else rect.bottom = top + length;

    // Adjust to scale
    rect.left = (int) (rect.left * Constants.SCALE);
    rect.top = (int) (rect.top * Constants.SCALE);
    rect.right = (int) (rect.right * Constants.SCALE);
    rect.bottom = (int) (rect.bottom * Constants.SCALE);

    bufferRect.left = (int) (bufferRect.left * Constants.SCALE);
    bufferRect.top = (int) (bufferRect.top * Constants.SCALE);
    bufferRect.right = (int) (bufferRect.right * Constants.SCALE);
    bufferRect.bottom = (int) (bufferRect.bottom * Constants.SCALE);
  }

  @Override
  public void draw(Canvas canvas)
  {
    DrawInterface.paint.setStrokeWidth(Constants.WALL_WIDTH);
    DrawInterface.paint.setColor(Constants.WALL_COLOR);
    canvas.drawRect(rect, DrawInterface.paint);
    DrawInterface.paint.reset();
  }

  public Rect getRect()
  {
    return this.rect;
  }

  public Rect getBufferRect()
  {
    return this.bufferRect;
  }

  public int getLeftSide()
  {
    return this.left;
  }

  public int getTopSide()
  {
    return this.top;
  }

  public int getLength()
  {
    return this.length;
  }
}
