package com.jjakenichol.heist;

import java.util.HashSet;

/**
 * Created by JakeNichol on 4/9/15.
 */
public class Map
{
  private HashSet<Wall> walls = new HashSet<>();
  private float offset = Constants.wallWidth * 10;

  public Map()
  {
    Wall borderWall1 = new Wall(0, 0, Orientation.Horizontal, DrawInterface.displayWidth);
    Wall borderWall2 = new Wall(0, 0, Orientation.Vertical, DrawInterface.displayHeight);
    Wall borderWall3 = new Wall(DrawInterface.displayWidth, 0, Orientation.Vertical, DrawInterface.displayHeight - 1);
    Wall borderWall4 = new Wall(0, DrawInterface.displayHeight, Orientation.Horizontal, DrawInterface.displayWidth);

    Wall wall1a = new Wall(0 + offset, 0 + offset, Orientation.Horizontal, DrawInterface.displayWidth - 2 * offset);
    Wall wall2a = new Wall(0 + offset, 0 + offset, Orientation.Vertical, DrawInterface.displayHeight - 2 * offset);
    Wall wall3a = new Wall(DrawInterface.displayWidth - offset, 0 + offset, Orientation.Vertical, DrawInterface.displayHeight - 1 - 2 * offset);
    Wall wall4a = new Wall(0 + offset, DrawInterface.displayHeight - offset, Orientation.Horizontal, DrawInterface.displayWidth - 2 * offset);

    Wall wall1b = new Wall(0 + 2 * offset, 0 + 2 * offset, Orientation.Horizontal, DrawInterface.displayWidth - 4 * offset);
    Wall wall2b = new Wall(0 + 2 * offset, 0 + 2 * offset, Orientation.Vertical, DrawInterface.displayHeight - 4 * offset);
    Wall wall3b = new Wall(DrawInterface.displayWidth - 2 * offset, 0 + 2 * offset, Orientation.Vertical, DrawInterface.displayHeight - 1 - 4 * offset);
    Wall wall4b = new Wall(0 + 2 * offset, DrawInterface.displayHeight - 2 * offset, Orientation.Horizontal, DrawInterface.displayWidth - 4 * offset);

    Wall wall1c = new Wall(1050, 650, Orientation.Horizontal, DrawInterface.displayWidth - 1400);
    Wall wall2c = new Wall(1050, 775, Orientation.Horizontal, DrawInterface.displayWidth - 1400);
    Wall wall3c = new Wall(1050, 650, Orientation.Vertical, 125);
    Wall wall4c = new Wall(DrawInterface.displayWidth - 4 * offset, 800, Orientation.Vertical, DrawInterface.displayHeight - 1 - 1600);

    walls.add(borderWall1);
    walls.add(borderWall2);
    walls.add(borderWall3);
    walls.add(borderWall4);

    walls.add(wall1a);
    walls.add(wall2a);
    walls.add(wall3a);
    walls.add(wall4a);

    walls.add(wall1b);
    walls.add(wall2b);
    walls.add(wall3b);
    walls.add(wall4b);

    walls.add(wall1c);
    walls.add(wall2c);
    walls.add(wall3c);
    walls.add(wall4c);
  }

  public void draw()
  {
    for (Wall wall : walls)
    {
      wall.draw();
    }
  }
}
