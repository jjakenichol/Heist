package com.jjakenichol.heist.android;

import android.graphics.Rect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

/**
 * Created by JakeNichol on 4/20/15.
 */
public class Player
{
  private Sprite sprite;
  private Vector3 position = new Vector3(0, 0, 0);
  private float size = 8;
  private Rect playerBox = new Rect((int) position.x - 8, (int) position.y + 8, (int) position.x + 8, (int) position.y - 8);

  private ShapeRenderer renderer = new ShapeRenderer();

  public Player(Sprite sprite, Vector3 position)
  {
    this.sprite = sprite;
    this.position = position;
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
    if (System.currentTimeMillis() % 25 == 0)
    {
      position = catmull.controlPoints[0];
      if (array.size > 1) array.removeIndex(0);
    }
  }

  public Sprite getSprite()
  {
    return sprite;
  }

  public Vector3 getPosition()
  {
    return position;
  }

  public float getSize()
  {
    return size;
  }

  public ShapeRenderer getRenderer()
  {
    return renderer;
  }

  public Rect getPlayerBox()
  {
    return playerBox;
  }

  public void setPlayerBox(int left, int top, int right, int bottom)
  {
    playerBox.set(left, top, right, bottom);
  }

}
