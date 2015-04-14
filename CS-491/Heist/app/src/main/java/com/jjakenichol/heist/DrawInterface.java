package com.jjakenichol.heist;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.ImageView;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;


public class DrawInterface extends Activity implements OnTouchListener, View.OnClickListener, Runnable
{
  private Bitmap bitmap;
  private Rect grid[][];
  private int gridX;
  private int gridY;
  private boolean gameStarted = false;
  private boolean isTouchingScreen = false;

  private static ConcurrentLinkedQueue<FloatPoint> points = new ConcurrentLinkedQueue<>();
  private static ConcurrentLinkedQueue<FloatPoint> newPoints = new ConcurrentLinkedQueue<>();
  private static ImageView imageView;
  private static Path path = new Path();
  private static Map map;
  private static Time time;
  private static Game game;
  private static Thread thread;

  public static Canvas canvas;
  public static Paint paint;
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
    imageView.setImageBitmap(bitmap);

//    final Button button = (Button) findViewById(R.id.clearButton);
//    button.setOnClickListener(this);

    imageView.setOnTouchListener(this);

    // Draw Map
    map = new Map();
    map.draw();

    // Start Game
    game = new Game(map);
    game.start();

    time = new Time(game.getPlayer(), 800, 20, 800 + Constants.TEXT_SIZE + 20, Constants.TEXT_SIZE + 30);

    thread = new Thread(this);
    thread.start();
  }

  @Override
  public boolean onTouch(View v, MotionEvent event)
  {
    if (!gameStarted)
    {
      time.timer.start();
      gameStarted = true;
    }

    switch (event.getAction())
    {
      case MotionEvent.ACTION_DOWN:
        isTouchingScreen = true;
        break;
      case MotionEvent.ACTION_MOVE:
        FloatPoint point = new FloatPoint(event.getRawX(), event.getRawY());
        if (!map.isInWall(point))
        {
          points.add(point);
          newPoints.add(point);
        }
//        if (Math.abs(points.peek().x - point.x) > 500 && Math.abs(points.peek().y - point.y) > 500 && points.size() < 500)
//        {
//          System.out.println(points.size());
//          Player.fillOutPoints(DrawInterface.getPoints());
//        }
        isTouchingScreen = true;
        break;
      case MotionEvent.ACTION_UP:
        isTouchingScreen = false;
        break;
    }

    return isTouchingScreen;
  }

  @Override
  public void onClick(View v)
  {
    repaint();
  }

  public static void repaint()
  {
    path = new Path();
    canvas.drawColor(Color.parseColor("#050490"));
    map.draw();
    drawPath();
    time.draw();

    imageView.postInvalidate(time.getRect().left, time.getRect().top, time.getRect().right,
            time.getRect().bottom);
    for (FloatPoint point : newPoints)
    {
      imageView.postInvalidate((int) point.x - Constants.PATH_WIDTH, (int) point.y - Constants.PATH_WIDTH, (int) point.x + Constants.PATH_WIDTH,
              (int) point.y + Constants.PATH_WIDTH);
    }
    newPoints.clear();
    imageView.postInvalidate((int) game.getPlayer().getPosition().x - Constants.PLAYER_SIZE, (int) game.getPlayer().getPosition().y -
                    Constants.PLAYER_SIZE, (int) game.getPlayer().getPosition().x + Constants.PLAYER_SIZE,
            (int) game.getPlayer().getPosition().y + Constants.PLAYER_SIZE);
  }

  @Override
  public void run()
  {
    while (true)
    {
      if (game.getPlayer().hasWon)
      {
        this.youWin();

        try
        {
          game.join();
          thread.join();
        } catch (InterruptedException e)
        {
          e.printStackTrace();
        }
      }
      else if (game.getPlayer().hasLost)
      {
        this.youLose();

        try
        {
          game.join();
          thread.join();
        } catch (InterruptedException e)
        {
          e.printStackTrace();
        }
      }

      if (System.currentTimeMillis() % 10 == 0)
      {
//        imageView.postInvalidate(time.getRect().left, time.getRect().top, time.getRect().right,
//                time.getRect().bottom);
//        for (FloatPoint point : newPoints)
//        {
//          imageView.postInvalidate((int) point.x - Constants.PATH_WIDTH, (int) point.y - Constants.PATH_WIDTH, (int) point.x + Constants.PATH_WIDTH,
//                  (int) point.y + Constants.PATH_WIDTH);
//        }
//        newPoints.clear();
//        imageView.postInvalidate((int) game.getPlayer().getPosition().x - Constants.PLAYER_SIZE, (int) game.getPlayer().getPosition().y -
//                        Constants.PLAYER_SIZE, (int) game.getPlayer().getPosition().x + Constants.PLAYER_SIZE,
//                (int) game.getPlayer().getPosition().y + Constants.PLAYER_SIZE);
      }
    }
  }

  public static void drawPath()
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
    DrawInterface.paint.setColor(Constants.PATH_COLOR);
    DrawInterface.paint.setStrokeWidth(Constants.PATH_WIDTH);
    DrawInterface.paint.setStyle(Paint.Style.STROKE);
    canvas.drawPath(path, paint);
    DrawInterface.paint.reset();
  }

  public void youWin()
  {
    startActivity(new Intent(this, WinActivity.class));
  }

  public void youLose()
  {
    startActivity(new Intent(this, LoseActivity.class));
  }

  public void drawGrid()
  {
    int gridStrokeWidth = Constants.WALL_WIDTH;
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
  public static ConcurrentLinkedQueue<FloatPoint> getPoints()
  {
    return points;
  }

}
