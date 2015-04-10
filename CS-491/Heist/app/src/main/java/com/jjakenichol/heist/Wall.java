package com.jjakenichol.heist;

/**
 * Created by JakeNichol on 4/9/15.
 */
public class Wall
{
  private FloatPoint position;
  private Orientation orientation;
  private float length = -1;

  public Wall(float x, float y, Orientation orientation, float length)
  {
    this.position = new FloatPoint(x, y);
    this.orientation = orientation;
    this.length = length;
  }

  public void draw()
  {
    DrawInterface.paint.setStrokeWidth(Constants.wallWidth);
    DrawInterface.paint.setColor(Constants.wallColor);
    switch (orientation)
    {
      case Horizontal:
        DrawInterface.canvas.drawLine(position.x, position.y, position.x + length, position.y, DrawInterface.paint);
        break;
      case Vertical:
        DrawInterface.canvas.drawLine(position.x, position.y, position.x, position.y + length, DrawInterface.paint);
        break;
    }
    DrawInterface.paint.reset();
  }
}
