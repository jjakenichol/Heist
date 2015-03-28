package com.jjakenichol.cs491.interfacecomponent;

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


public class DrawComponent extends Activity implements OnTouchListener, View.OnClickListener
{
  ImageView imageView;
  Bitmap bitmap;
  Canvas canvas;
  Paint paint;
  Path path = new Path();
  LinkedList<FloatPoint> points = new LinkedList<>();
  Rect grid[][];
  int gridX;
  int gridY;
  float displayWidth;
  float displayHeight;
  int defaultColor = Color.GREEN;
  int blockColor = Color.BLUE;
  int defaultStrokeWidth = 5;
  int gridStrokeWidth = 3;
  int gridSizeSmall = 20;
  int gridSizeMedium = 10;
  int gridSizeLarge = 5;
  int gridSizeCurrent = gridSizeSmall;

  Paint.Style defaultStyle = Paint.Style.STROKE;

  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_draw);

    imageView = (ImageView) this.findViewById(R.id.backgroundImageView);

    Point imageViewSize = new Point();
    this.getWindowManager().getDefaultDisplay().getSize(imageViewSize);
    displayWidth = imageViewSize.x;
    displayHeight = imageViewSize.y;

    gridX = (int) displayWidth / gridSizeCurrent;
    gridY = (int) displayHeight / gridSizeCurrent;
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
        break;
      case MotionEvent.ACTION_MOVE:
        points.add(new FloatPoint(event.getX(), event.getY()));
        draw();
        break;
      case MotionEvent.ACTION_UP:
        points.clear();
        break;
    }

    for (int i = 0; i < gridX; i++)
    {
      for (int j = 0; j < gridY; j++)
      {
        if (grid[i][j].contains((int) event.getX(), (int) event.getY()))
        {
          paint.setColor(blockColor);
          paint.setStyle(Paint.Style.FILL);
          canvas.drawRect(grid[i][j], paint);
        }
      }
    }

    paint.setColor(defaultColor);
    paint.setStyle(defaultStyle);

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
