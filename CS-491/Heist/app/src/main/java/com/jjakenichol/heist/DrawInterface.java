package com.jjakenichol.heist;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
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
  private Bitmap bitmap;
  private Rect grid[][];
  private int gridX;
  private int gridY;
  private boolean isTouchingScreen = false;

  private static LinkedList<FloatPoint> points = new LinkedList<>();
  private static ImageView imageView;
  private static Path path = new Path();
  private static Map map;

  public static Canvas canvas;
  public static Paint paint;
  public static float displayWidth;
  public static float displayHeight;
  public static boolean isDrawing = false;

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
    imageView.setImageBitmap(bitmap);

//    final Button button = (Button) findViewById(R.id.clearButton);
//    button.setOnClickListener(this);

    imageView.setOnTouchListener(this);

    // Draw Map
    map = new Map();
    map.draw();

    // Start Game
    new Game().start();
  }

  @Override
  public boolean onTouch(View v, MotionEvent event)
  {
    switch (event.getAction())
    {
      case MotionEvent.ACTION_DOWN:
        isTouchingScreen = true;
        break;
      case MotionEvent.ACTION_MOVE:
        points.add(new FloatPoint(event.getRawX(), event.getRawY()));
        isTouchingScreen = true;
        break;
      case MotionEvent.ACTION_UP:
        isTouchingScreen = false;
        break;
    }
    drawPath();

    return isTouchingScreen;
  }

  @Override
  public void onClick(View v)
  {
    clear();
  }

  public static void clear()
  {
    path = new Path();
    canvas.drawColor(Color.parseColor("#050490"));
    map.draw();
  }

  public static void drawPath()
  {
    boolean isFirst = true;
    isDrawing = true;
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
    DrawInterface.paint.setColor(Constants.pathColor);
    DrawInterface.paint.setStrokeWidth(Constants.pathWidth);
    DrawInterface.paint.setStyle(Paint.Style.STROKE);
    canvas.drawPath(path, paint);
    DrawInterface.paint.reset();

    imageView.postInvalidate();
    isDrawing = false;
  }

  public void drawGrid()
  {
    int gridStrokeWidth = Constants.wallWidth;
    int gridSize = 20;

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
    paint.setStrokeWidth(gridStrokeWidth);
    for (int i = 0; i < gridX; i++)
    {
      for (int j = 0; j < gridY; j++)
      {
        canvas.drawRect(grid[i][j], paint);
      }
    }
    DrawInterface.paint.reset();
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
