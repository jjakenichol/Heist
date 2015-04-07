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
  private Canvas canvas;
  private Paint paint;
  private Path path = new Path();
  private LinkedList<FloatPoint> points = new LinkedList<>();
  private Rect grid[][];
  private int gridX;
  private int gridY;
  private float displayWidth;
  private float displayHeight;
  private int defaultColor = Color.GREEN;
  private int defaultStrokeWidth = 5;
  private int gridStrokeWidth = 3;
  private int gridSize = 20;

  private Paint.Style defaultStyle = Paint.Style.STROKE;

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

    bitmap = Bitmap.createBitmap((int) displayWidth, (int) displayHeight, Bitmap.Config.ARGB_8888);
    canvas = new Canvas(bitmap);
    paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    paint.setStyle(defaultStyle);
    paint.setStrokeWidth(gridStrokeWidth);
    paint.setColor(defaultColor);
    imageView.setImageBitmap(bitmap);

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
  }

  @Override
  public boolean onTouch(View v, MotionEvent event)
  {
    switch (event.getAction())
    {
      case MotionEvent.ACTION_DOWN:
        points.add(new FloatPoint(event.getX(), event.getY()));
        draw();
        break;
      case MotionEvent.ACTION_MOVE:
        points.add(new FloatPoint(event.getX(), event.getY()));
        draw();
        break;
      case MotionEvent.ACTION_UP:
        points.clear();
        break;
    }

    return true;
  }

  private void draw()
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


  class FloatPoint
  {
    public float x, y;

    public FloatPoint(float x, float y)
    {
      this.x = x;
      this.y = y;
    }
  }
}
