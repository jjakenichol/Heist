package com.jjakenichol.heist;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;

import java.util.LinkedList;


public class DrawInterface extends Activity implements OnTouchListener, View.OnClickListener
{
  private ImageView imageView;
  private Bitmap bitmap;
  public static Canvas canvas;
  public static Paint paint;
  private Path path = new Path();
  private Rect grid[][];
  private Paint.Style defaultStyle = Paint.Style.STROKE;
  private int gridX;
  private int gridY;
  private int defaultColor = Color.GREEN;
  private int defaultStrokeWidth = 5;
  private int gridStrokeWidth = 3;
  private int gridSize = 20;

  private static LinkedList<FloatPoint> points = new LinkedList<>();

  public static float displayWidth;
  public static float displayHeight;

  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_draw_interface);

    imageView = (ImageView) this.findViewById(R.id.backgroundImageView);

    Point imageViewSize = new Point();
    this.getWindowManager().getDefaultDisplay().getSize(imageViewSize);
    displayWidth = imageViewSize.x;
    displayHeight = imageViewSize.y;

    bitmap = Bitmap.createBitmap((int) displayWidth, (int) displayHeight, Bitmap.Config.ARGB_8888);
    canvas = new Canvas(bitmap);
    paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    paint.setStyle(defaultStyle);
    paint.setStrokeWidth(gridStrokeWidth);
    paint.setColor(defaultColor);
    imageView.setImageBitmap(bitmap);

    // Create Grid
    gridX = (int) displayWidth / gridSize;
    gridY = (int) displayHeight / gridSize;
    grid = new Rect[gridX][gridY];
    for (int i = 0; i < gridX; i++)
    {
      for (int j = 0; j < gridY; j++)
      {
        int left = i * gridX;
        int top = j * gridY;
        int right = left + gridX;
        int bottom = top + gridY;
        grid[i][j] = new Rect(left, top, right, bottom);
      }
    }

    // Draw Grid
    for (int i = 0; i < gridX; i++)
    {
      for (int j = 0; j < gridY; j++)
      {
        canvas.drawRect(grid[i][j], paint);
      }
    }
    paint.setStrokeWidth(defaultStrokeWidth);

    final Button button = (Button) findViewById(R.id.clearButton);
    button.setOnClickListener(this);

    imageView.setOnTouchListener(this);

    // Create Game
    new Game(DrawInterface.canvas, DrawInterface.paint).start();
  }

  @Override
  public boolean onTouch(View v, MotionEvent event)
  {
    switch (event.getAction())
    {
      case MotionEvent.ACTION_DOWN:
        points.add(new FloatPoint(event.getRawX(), event.getRawY()));
        drawPath();
        break;
      case MotionEvent.ACTION_MOVE:
        points.add(new FloatPoint(event.getRawX(), event.getRawY()));
        drawPath();
        break;
      case MotionEvent.ACTION_UP:
        points.clear();
        break;
    }

    return true;
  }

  @Override
  public void onClick(View v)
  {
    clear();
  }

  private void clear()
  {
    path = new Path();
    canvas.drawColor(0, PorterDuff.Mode.CLEAR);

    paint.setStrokeWidth(gridStrokeWidth);
    for (int i = 0; i < gridX; i++)
    {
      for (int j = 0; j < gridY; j++)
      {
        canvas.drawRect(grid[i][j], paint);
      }
    }
    paint.setStrokeWidth(defaultStrokeWidth);

    imageView.invalidate();
  }

  private void drawPath()
  {
    boolean isFirst = true;
    for (FloatPoint point : points)
    {
      if (isFirst)
      {
        isFirst = false;
        path.moveTo(point.x, point.y);
      }
      else
      {
        path.lineTo(point.x, point.y);
      }
    }
    canvas.drawPath(path, paint);
    imageView.invalidate();
  }

  /**
   * Gets the path points.
   *
   * @return a linked list of points
   */
  public static LinkedList<FloatPoint> getPoints()
  {
    return points;
  }

}
