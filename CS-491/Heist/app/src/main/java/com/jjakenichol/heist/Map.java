package com.jjakenichol.heist;

import java.util.HashSet;

/**
 * Created by JakeNichol on 4/9/15.
 */
public class Map
{
  private HashSet<Wall> walls = new HashSet<>();
  private Finish finish;

  public Map()
  {
    // #SA -- <wall group><side><adjacency (auxiliary walls)>
    // Sides: L = left, T = top, R = right, B = bottom
    // Adjacency: L = left, R = right, A = above, B = below // For auxiliary walls
    Wall wall1L = new Wall(Constants.WALL_WIDTH, Constants.WALL_WIDTH, Orientation.Vertical, (int) DrawInterface.displayHeight);
    Wall wall1TL = new Wall(Constants.WALL_WIDTH, Constants.WALL_WIDTH, Orientation.Horizontal, 30);
    Wall wall1TR = new Wall(Constants.WALL_WIDTH + (30 + Constants.DOOR_SIZE), Constants.WALL_WIDTH, Orientation.Horizontal,
            (int) DrawInterface.displayWidth - (30 + Constants.DOOR_SIZE));
    Wall wall1R = new Wall((int) DrawInterface.displayWidth - Constants.WALL_WIDTH, Constants.WALL_WIDTH, Orientation.Vertical,
            (int) DrawInterface.displayHeight);
    Wall wall1BL = new Wall(Constants.WALL_WIDTH, (int) DrawInterface.displayHeight - Constants.WALL_WIDTH, Orientation.Horizontal,
            (int) DrawInterface.displayWidth - (30 + Constants.DOOR_SIZE));
    Wall wall1BR = new Wall(Constants.WALL_WIDTH + ((int) DrawInterface.displayWidth - 30), (int) DrawInterface.displayHeight - Constants.WALL_WIDTH,
            Orientation.Horizontal, 30);

    Wall wall2L = new Wall(Constants.WALL_WIDTH + (30 + Constants.DOOR_SIZE), Constants.WALL_WIDTH + (30 + Constants.DOOR_SIZE),
            Orientation.Vertical, (int) DrawInterface.displayHeight - 2 * (Constants.WALL_WIDTH + (30 + Constants.DOOR_SIZE)));
    Wall wall2T = new Wall(Constants.WALL_WIDTH + (30 + Constants.DOOR_SIZE), Constants.WALL_WIDTH + (30 + Constants.DOOR_SIZE),
            Orientation.Horizontal, 600);
    Wall wall2TR = new Wall(Constants.WALL_WIDTH + (30 + Constants.DOOR_SIZE) + 600, Constants.WALL_WIDTH,
            Orientation.Vertical, 2 * (Constants.WALL_WIDTH + (30 + Constants.DOOR_SIZE)));
    Wall wall2BL = new Wall(Constants.WALL_WIDTH + (30 + Constants.DOOR_SIZE), Constants.WALL_WIDTH + (30 + Constants.DOOR_SIZE) + (int)
            DrawInterface.displayHeight - 2 * (Constants.WALL_WIDTH + (30 + Constants.DOOR_SIZE)), Orientation.Horizontal,
            2 * (Constants.WALL_WIDTH + (30 + Constants.DOOR_SIZE)));
    Wall wall2BR = new Wall(wall2BL.getRect().right + Constants.DOOR_SIZE, Constants.WALL_WIDTH + (30 + Constants.DOOR_SIZE) + (int)
            DrawInterface.displayHeight - 2 * (Constants.WALL_WIDTH + (30
            + Constants.DOOR_SIZE)), Orientation.Horizontal, (int) DrawInterface.displayWidth - Constants.WALL_WIDTH - (Constants.WALL_WIDTH + (30
            + Constants.DOOR_SIZE)) - (wall2BL.getRect().right + Constants.DOOR_SIZE));
    Wall wall2R = new Wall(wall2BR.getRect().right, Constants.WALL_WIDTH + (30 + Constants.DOOR_SIZE), Orientation.Vertical,
            (int) DrawInterface.displayHeight - 2 * (Constants.WALL_WIDTH + (30 + Constants.DOOR_SIZE)) + Constants.WALL_WIDTH);

    Wall wall3LT = new Wall(wall2L.getRect().left + Constants.HALL_WIDTH, wall2L.getRect().left + Constants.HALL_WIDTH, Orientation.Vertical, 300);
    Wall wall3LB = new Wall(wall3LT.getRect().left, wall3LT.getRect().bottom + Constants.DOOR_SIZE + Constants.WALL_WIDTH, Orientation.Vertical,
            wall2BL.getRect().top - (wall3LT.getRect().bottom + Constants.DOOR_SIZE) - Constants.HALL_WIDTH);
    Wall wall3BL = new Wall(wall3LT.getRect().left, wall3LB.getRect().bottom, Orientation.Horizontal, 2 * wall3LT.getLength());
    Wall wall3BR = new Wall(wall3BL.getRect().right + Constants.WALL_WIDTH + Constants.DOOR_SIZE, wall3BL.getRect().top, Orientation.Horizontal,
            wall2R.getRect().left - (wall3BL.getRect().right + Constants.WALL_WIDTH + Constants.DOOR_SIZE) - Constants.HALL_WIDTH);
    Wall wall3RB = new Wall(wall3BR.getRect().right, wall3BR.getRect().top - Constants.HALL_WIDTH, Orientation.Vertical,
            Constants.HALL_WIDTH + Constants.WALL_WIDTH);
    Wall wall3RT = new Wall(wall3RB.getRect().left, wall3LT.getTop(), Orientation.Vertical, (int) DrawInterface.displayHeight - wall3RB.getRect()
            .top - Constants.DOOR_SIZE - 3 * Constants.WALL_WIDTH);
    Wall wall3T = new Wall(wall3LT.getLeft(), wall3LT.getTop(), Orientation.Horizontal, wall3RT.getRect().right - wall3LT.getLeft());

    Wall wall4LB = new Wall(wall3LB.getLeft() + Constants.HALL_WIDTH, wall3BL.getTop() - Constants.HALL_WIDTH, Orientation.Horizontal,
            Constants.HALL_WIDTH);
    Wall wall4LT = new Wall(wall3LT.getLeft() + Constants.HALL_WIDTH, wall3LT.getTop(), Orientation.Vertical,
            wall4LB.getTop() - wall3LT.getTop() - Constants.HALL_WIDTH);
    Wall wall4R = new Wall(wall4LT.getLeft() + Constants.HALL_WIDTH, wall3T.getTop() + Constants.HALL_WIDTH, Orientation.Vertical,
            wall3BL.getTop() - (wall3T.getTop() + Constants.HALL_WIDTH));

    Wall wall5L = new Wall(wall4R.getLeft() + Constants.HALL_WIDTH, wall3BL.getTop() - 2 * Constants.HALL_WIDTH, Orientation.Vertical,
            Constants.HALL_WIDTH);
    Wall wall5BL = new Wall(wall5L.getLeft(), wall5L.getRect().bottom, Orientation.Horizontal, 3 * Constants.HALL_WIDTH);
    Wall wall5BR = new Wall(wall5BL.getRect().right + Constants.DOOR_SIZE, wall5BL.getTop(), Orientation.Horizontal,
            wall3RB.getLeft() - (wall5BL.getRect().right + Constants.DOOR_SIZE) - Constants.HALL_WIDTH);
    Wall wall5R = new Wall(wall5BR.getRect().right - Constants.WALL_WIDTH, wall5L.getTop(), Orientation.Vertical, Constants.HALL_WIDTH);
    Wall wall5TL = new Wall(wall5L.getLeft(), wall5L.getTop(), Orientation.Horizontal, Constants.HALL_WIDTH);
    Wall wall5TR = new Wall(wall5TL.getRect().right - Constants.WALL_WIDTH + Constants.DOOR_SIZE, wall5TL.getTop(), Orientation.Horizontal,
            wall5R.getLeft() - (wall5TL.getRect().right - Constants.WALL_WIDTH + Constants.DOOR_SIZE));


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

    finish = new Finish(wall1TL.getRect().right, -Constants.WALL_WIDTH, wall1TL.getRect().right + Constants.DOOR_SIZE, Constants.DOOR_SIZE / 2);
  }

  public void draw()
  {
    for (Wall wall : walls)
    {
      wall.draw();
    }
    finish.draw();
  }

  public boolean isInWall(FloatPoint point)
  {
    for (Wall wall : walls)
    {
      if (wall.getRect().contains((int) point.x, (int) point.y)) return true;
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
