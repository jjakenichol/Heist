package com.jjakenichol.heist;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.LinkedList;

/**
 * Created by JakeNichol on 4/5/15.
 */
public class Player
{
  private float x, y = -1;
  private int size = -1;

  public Player(float x, float y, int size)
  {
    this.x = x;
    this.y = y;
    this.size = size;
  }

  public void update(LinkedList<FloatPoint> points)
  {
    if (points.peek() != null)
    {
      this.x = points.peek().x;
      this.y = points.peek().y;
    }
  }

  public void draw(Canvas canvas, Paint paint)
  {
    canvas.drawCircle(this.x, this.y, size, paint);
  }

  // Getters

  /**
   * Gets this Player's x location.
   *
   * @return this player's x
   */
  public float getX()
  {
    return this.x;
  }

  /**
   * Gets this Player's y location.
   *
   * @return this player's y
   */
  public float getY()
  {
    return this.y;
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
    this.x = x;
  }

  /**
   * Sets this Player's y location.
   *
   * @param y the value to set this Player's y to
   */
  public void setY(float y)
  {
    this.y = y;
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
