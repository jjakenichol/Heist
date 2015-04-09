package com.jjakenichol.heist;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.LinkedList;

/**
 * Created by JakeNichol on 4/5/15.
 */
public class Player
{
  private FloatPoint position;
  private int size = -1;
  private LinkedList<FloatPoint> points;

  public Player(float x, float y, int size, LinkedList<FloatPoint> points)
  {
    this.position = new FloatPoint(x, y);
    this.size = size;
    this.points = points;
  }

  public void update()
  {
    if (!points.isEmpty())
    {
      position = points.pop();
      DrawInterface.clear();
      DrawInterface.drawPath();
    }
  }

  public void draw()
  {
    DrawInterface.paint.setStyle(Paint.Style.FILL);
    DrawInterface.paint.setColor(Color.GREEN);
    DrawInterface.canvas.drawCircle(position.x, position.y, size, DrawInterface.paint);
    DrawInterface.paint.setColor(Color.BLACK);
    DrawInterface.canvas.drawCircle(position.x, position.y, size - (int)(size * 0.3), DrawInterface.paint);
  }

  /**
   * Gets this Player's x y position.
   *
   * @return this player's position
   */
  public FloatPoint getPosition()
  {
    return this.position;
  }

  /**
   * Gets this Player's size.
   *
   * @return this player's size
   */
  public int getSize()
  {
    return this.size;
  }

  /**
   * Sets this Player's x location.
   *
   * @param x the value to set this Player's x to
   */
  public void setX(float x)
  {
    this.position.x = x;
  }

  /**
   * Sets this Player's y location.
   *
   * @param y the value to set this Player's y to
   */
  public void setY(float y)
  {
    this.position.y = y;
  }

  /**
   * Sets this Player's size.
   *
   * @param size the size to set this Player's size to
   */
  public void getSize(int size)
  {
    this.size = size;
  }

}
