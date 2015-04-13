package com.jjakenichol.heist;

import android.graphics.Paint;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by JakeNichol on 4/5/15.
 */
public class Player
{
  private int size = -1;
  private Map map;
  private FloatPoint position;
  private ConcurrentLinkedQueue<FloatPoint> points;

  public Player(Map map, float x, float y, int size, ConcurrentLinkedQueue<FloatPoint> points)
  {
    this.map = map;
    this.position = new FloatPoint(x, y);
    this.size = size;
    this.points = points;
  }

  public void update()
  {
    if (!points.isEmpty())
    {
      position = points.poll();
      DrawInterface.clear();
      DrawInterface.drawPath();
      if (map.getFinish().getRect().contains((int) position.x, (int) position.y))
      {
        System.out.println("YAYAYAYAY");
      }
    }
  }

  public void draw()
  {
    DrawInterface.paint.setStyle(Paint.Style.FILL);
    DrawInterface.paint.setColor(Constants.PLAYER_COLOR);
    DrawInterface.canvas.drawCircle(position.x, position.y, size * Constants.SCALE, DrawInterface.paint);
    DrawInterface.paint.reset();
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
