package com.jjakenichol.heist.android;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

/**
 * Author: J. Jake Nichol
 *
 * Date: 4/20/15
 */
public class Player
{
  private Sprite sprite;
  private Vector3 position = new Vector3(0, 0, 0);
  private Vector3 linePosition = new Vector3(0, 0, 0);
  private float size = 8;
  private Rectangle playerBox = new Rectangle(position.x - size / 2, position.y - size / 2, size, size);
  private Rectangle lineBox = new Rectangle(linePosition.x - size / 2, linePosition.y - size / 2, size, size);

  private ShapeRenderer renderer = new ShapeRenderer();

  private int keys = 0;
  private int treasure = 0;
  private boolean hasWon = false;

  public Player(Sprite sprite, Vector3 position, Vector3 linePosition)
  {
    this.sprite = sprite;
    this.position = position;
    this.linePosition = linePosition;
  }

  public void draw(Batch batch, CatmullRomSpline<Vector3> catmull, Array<Vector3> array)
  {
    update(Gdx.graphics.getDeltaTime(), catmull, array);

    renderer.begin(ShapeRenderer.ShapeType.Filled);
    renderer.setColor(Color.OLIVE);
    renderer.circle(position.x, position.y, size);
    renderer.end();
  }

  public void update(float delta, CatmullRomSpline<Vector3> catmull, Array<Vector3> array)
  {
    if (System.currentTimeMillis() % 2 * delta == 0)
    {
      position = catmull.controlPoints[0];
      if (array.size > 1) array.removeIndex(0);
    }
    this.linePosition = array.peek();

    playerBox.set(position.x - 30 / 2, position.y - 30 / 2, 30, 30);
    lineBox.set(linePosition.x - size / 2, linePosition.y - size / 2, size, size);
  }

  public void dispose()
  {
    renderer.dispose();
  }

  public void addKey()
  {
    this.keys++;
  }

  public void removeKey()
  {
    this.keys--;
  }

  public int getTreasure()
  {
    return this.treasure;
  }

  public void addTreasure()
  {
    this.treasure++;
  }

  public void win()
  {
    this.hasWon = true;
  }

  public boolean hasWon()
  {
    return this.hasWon;
  }

  public Sprite getSprite()
  {
    return sprite;
  }

  public Vector3 getPosition()
  {
    return position;
  }

  public Vector3 getLinePosition()
  {
    return this.linePosition;
  }

  public float getSize()
  {
    return size;
  }

  public ShapeRenderer getRenderer()
  {
    return renderer;
  }

  public Rectangle getPlayerBox()
  {
    return this.playerBox;
  }

  public Rectangle getLineBox()
  {
    return this.lineBox;
  }

  public int getKeys()
  {
    return this.keys;
  }

  public void setPlayerBox(float left, float top, float width, float height)
  {
    playerBox.set(left, top, width, height);
  }

}
