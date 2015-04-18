package com.jjakenichol.heist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by JakeNichol on 4/5/15.
 */
public class Player extends View
{
  public boolean hasWon = false;
  public boolean hasLost = false;
  private int size = -1;
  private Map map;
  private FloatPoint position;
  private ConcurrentLinkedQueue<FloatPoint> points;

  public Player(Context context, Map map, float x, float y, int size, ConcurrentLinkedQueue<FloatPoint> points)
  {
    super(context);
    this.map = map;
    this.position = new FloatPoint(x, y);
    this.size = size;
    this.points = points;
  }

  public void update()
  {
    if (points.size() > 0)
    {
      position = points.poll();
//      position = points.poll();
//      position = points.poll();
//      position = points.poll();
//      position = points.poll();
    }
  }

  @Override
  public void onDraw(Canvas canvas)
  {
    if (map.getFinish().getRect().contains((int) position.x, (int) position.y))
    {
      this.hasWon = true;
    }
    DrawInterface.paint.setStyle(Paint.Style.FILL);
    DrawInterface.paint.setColor(Constants.PLAYER_COLOR);
    canvas.drawCircle(position.x, position.y, size * Constants.SCALE, DrawInterface.paint);
    DrawInterface.paint.reset();
  }

  public static void fillOutPoints(ConcurrentLinkedQueue<FloatPoint> points)
  {
    ConcurrentLinkedQueue<FloatPoint> newPoints = new ConcurrentLinkedQueue<>();
    FloatPoint[] pointsArray = new FloatPoint[points.size()];
    points.toArray(pointsArray);
    int index;

    for (FloatPoint floatPoint : pointsArray)
    {
      newPoints.add(floatPoint);
      index = Arrays.asList(pointsArray).indexOf(floatPoint);
      if (index == pointsArray.length) break;

      FloatPoint newPoint = new FloatPoint((pointsArray[index].x + pointsArray[index].x) / 2,(pointsArray[index].y + pointsArray[index].y)
            / 2);

      newPoints.add(newPoint);
    }
    points.clear();
    points.addAll(newPoints);
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
