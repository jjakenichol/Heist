package com.jjakenichol.heist;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import java.util.HashSet;

/**
 * Created by JakeNichol on 4/9/15.
 */
public class Map extends View
{
  private HashSet<Wall> walls = new HashSet<>();
  private Finish finish;

  public Map(Context context)
  {
    super(context);

    // #SA -- <wall group><side><adjacency (auxiliary walls)>
    // Sides: L = left, T = top, R = right, B = bottom
    // Adjacency: L = left, R = right, A = above, B = below // For auxiliary walls
    Wall wall1L = new Wall(context, Constants.WALL_WIDTH, Constants.WALL_WIDTH, Orientation.Vertical, (int) DrawInterface.displayHeight);
    Wall wall1TL = new Wall(context, Constants.WALL_WIDTH, Constants.WALL_WIDTH, Orientation.Horizontal, 30);
    Wall wall1TR = new Wall(context, Constants.WALL_WIDTH + (30 + Constants.DOOR_SIZE), Constants.WALL_WIDTH, Orientation.Horizontal,
            (int) DrawInterface.displayWidth - (30 + Constants.DOOR_SIZE));
    Wall wall1R = new Wall(context, (int) DrawInterface.displayWidth - Constants.WALL_WIDTH, Constants.WALL_WIDTH, Orientation.Vertical,
            (int) DrawInterface.displayHeight);
    Wall wall1BL = new Wall(context, Constants.WALL_WIDTH, (int) DrawInterface.displayHeight - Constants.WALL_WIDTH, Orientation.Horizontal,
            (int) DrawInterface.displayWidth - (30 + Constants.DOOR_SIZE));
    Wall wall1BR = new Wall(context, Constants.WALL_WIDTH + ((int) DrawInterface.displayWidth - 30), (int) DrawInterface.displayHeight - Constants.WALL_WIDTH,
            Orientation.Horizontal, 30);

    Wall wall2L = new Wall(context, Constants.WALL_WIDTH + (30 + Constants.DOOR_SIZE), Constants.WALL_WIDTH + (30 + Constants.DOOR_SIZE),
            Orientation.Vertical, (int) DrawInterface.displayHeight - 2 * (Constants.WALL_WIDTH + (30 + Constants.DOOR_SIZE)));
    Wall wall2T = new Wall(context, Constants.WALL_WIDTH + (30 + Constants.DOOR_SIZE), Constants.WALL_WIDTH + (30 + Constants.DOOR_SIZE),
            Orientation.Horizontal, 600);
    Wall wall2TR = new Wall(context, Constants.WALL_WIDTH + (30 + Constants.DOOR_SIZE) + 600, Constants.WALL_WIDTH,
            Orientation.Vertical, 2 * (Constants.WALL_WIDTH + (30 + Constants.DOOR_SIZE)));
    Wall wall2BL = new Wall(context, Constants.WALL_WIDTH + (30 + Constants.DOOR_SIZE), Constants.WALL_WIDTH + (30 + Constants.DOOR_SIZE) + (int)
            DrawInterface.displayHeight - 2 * (Constants.WALL_WIDTH + (30 + Constants.DOOR_SIZE)), Orientation.Horizontal,
            2 * (Constants.WALL_WIDTH + (30 + Constants.DOOR_SIZE)));
    Wall wall2BR = new Wall(context, wall2BL.getRect().right + Constants.DOOR_SIZE, Constants.WALL_WIDTH + (30 + Constants.DOOR_SIZE) + (int)
            DrawInterface.displayHeight - 2 * (Constants.WALL_WIDTH + (30
            + Constants.DOOR_SIZE)), Orientation.Horizontal, (int) DrawInterface.displayWidth - Constants.WALL_WIDTH - (Constants.WALL_WIDTH + (30
            + Constants.DOOR_SIZE)) - (wall2BL.getRect().right + Constants.DOOR_SIZE));
    Wall wall2R = new Wall(context, wall2BR.getRect().right, Constants.WALL_WIDTH + (30 + Constants.DOOR_SIZE), Orientation.Vertical,
            (int) DrawInterface.displayHeight - 2 * (Constants.WALL_WIDTH + (30 + Constants.DOOR_SIZE)) + Constants.WALL_WIDTH);

    Wall wall3LT = new Wall(context, wall2L.getRect().left + Constants.HALL_WIDTH, wall2L.getRect().left + Constants.HALL_WIDTH, Orientation.Vertical, 300);
    Wall wall3LB = new Wall(context, wall3LT.getRect().left, wall3LT.getRect().bottom + Constants.DOOR_SIZE + Constants.WALL_WIDTH, Orientation.Vertical,
            wall2BL.getRect().top - (wall3LT.getRect().bottom + Constants.DOOR_SIZE) - Constants.HALL_WIDTH);
    Wall wall3BL = new Wall(context, wall3LT.getRect().left, wall3LB.getRect().bottom, Orientation.Horizontal, 2 * wall3LT.getLength());
    Wall wall3BR = new Wall(context, wall3BL.getRect().right + Constants.WALL_WIDTH + Constants.DOOR_SIZE, wall3BL.getRect().top, Orientation.Horizontal,
            wall2R.getRect().left - (wall3BL.getRect().right + Constants.WALL_WIDTH + Constants.DOOR_SIZE) - Constants.HALL_WIDTH);
    Wall wall3RB = new Wall(context, wall3BR.getRect().right, wall3BR.getRect().top - Constants.HALL_WIDTH, Orientation.Vertical,
            Constants.HALL_WIDTH + Constants.WALL_WIDTH);
    Wall wall3RT = new Wall(context, wall3RB.getRect().left, wall3LT.getTopSide(), Orientation.Vertical, (int) DrawInterface.displayHeight - wall3RB.getRect()
            .top - Constants.DOOR_SIZE - 3 * Constants.WALL_WIDTH);
    Wall wall3T = new Wall(context, wall3LT.getLeftSide(), wall3LT.getTopSide(), Orientation.Horizontal, wall3RT.getRect().right - wall3LT.getLeftSide());

    Wall wall4LB = new Wall(context, wall3LB.getLeftSide() + Constants.HALL_WIDTH, wall3BL.getTopSide() - Constants.HALL_WIDTH, Orientation.Horizontal,
            Constants.HALL_WIDTH);
    Wall wall4LT = new Wall(context, wall3LT.getLeftSide() + Constants.HALL_WIDTH, wall3LT.getTopSide(), Orientation.Vertical,
            wall4LB.getTopSide() - wall3LT.getTopSide() - Constants.HALL_WIDTH);
    Wall wall4R = new Wall(context, wall4LT.getLeftSide() + Constants.HALL_WIDTH, wall3T.getTopSide() + Constants.HALL_WIDTH, Orientation.Vertical,
            wall3BL.getTopSide() - (wall3T.getTopSide() + Constants.HALL_WIDTH));

    Wall wall5L = new Wall(context, wall4R.getLeftSide() + Constants.HALL_WIDTH, wall3BL.getTopSide() - 2 * Constants.HALL_WIDTH, Orientation.Vertical,
            Constants.HALL_WIDTH);
    Wall wall5BL = new Wall(context, wall5L.getLeftSide(), wall5L.getRect().bottom, Orientation.Horizontal, 3 * Constants.HALL_WIDTH);
    Wall wall5BR = new Wall(context, wall5BL.getRect().right + Constants.DOOR_SIZE, wall5BL.getTopSide(), Orientation.Horizontal,
            wall3RB.getLeftSide() - (wall5BL.getRect().right + Constants.DOOR_SIZE) - Constants.HALL_WIDTH);
    Wall wall5R = new Wall(context, wall5BR.getRect().right - Constants.WALL_WIDTH, wall5L.getTopSide(), Orientation.Vertical, Constants.HALL_WIDTH);
    Wall wall5TL = new Wall(context, wall5L.getLeftSide(), wall5L.getTopSide(), Orientation.Horizontal, Constants.HALL_WIDTH);
    Wall wall5TR = new Wall(context, wall5TL.getRect().right - Constants.WALL_WIDTH + Constants.DOOR_SIZE, wall5TL.getTopSide(), Orientation.Horizontal,
            wall5R.getLeftSide() - (wall5TL.getRect().right - Constants.WALL_WIDTH + Constants.DOOR_SIZE));


    walls.add(wall1L);
    walls.add(wall1TL);
    walls.add(wall1TR);
    walls.add(wall1R);
    walls.add(wall1BL);
    walls.add(wall1BR);

    walls.add(wall2L);
    walls.add(wall2T);
    walls.add(wall2TR);
    walls.add(wall2BL);
    walls.add(wall2BR);
    walls.add(wall2R);

    walls.add(wall3LT);
    walls.add(wall3LB);
    walls.add(wall3BL);
    walls.add(wall3BR);
    walls.add(wall3RB);
    walls.add(wall3RT);
    walls.add(wall3T);

    walls.add(wall4LB);
    walls.add(wall4LT);
    walls.add(wall4R);

    walls.add(wall5L);
    walls.add(wall5BL);
    walls.add(wall5BR);
    walls.add(wall5R);
    walls.add(wall5TL);
    walls.add(wall5TR);

    finish = new Finish(context, wall1TL.getRect().right, -Constants.WALL_WIDTH, wall1TL.getRect().right + Constants.DOOR_SIZE, Constants.DOOR_SIZE / 2);
  }

  @Override
  public void onDraw(Canvas canvas)
  {
    for (Wall wall : walls)
    {
      wall.draw(canvas);
    }
    finish.draw(canvas);
  }

  public boolean isInWall(FloatPoint point)
  {
    for (Wall wall : walls)
    {
      if (wall.getBufferRect().contains((int) point.x, (int) point.y)) return true;
    }
    return false;
  }

  public HashSet<Wall> getWalls()
  {
    return this.walls;
  }

  public Finish getFinish()
  {
    return this.finish;
  }
}
