package com.jjakenichol.heist.android;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

/**
 * Created by JakeNichol on 4/18/15.
 */
public class GameScreen implements Screen
{
  private Heist game;
  private OrthographicCamera camera;

  private Texture sprite;

  private Array<Vector3> points = new Array<>();
  private CatmullRomSpline<Vector3> myCatmull;
  private ShapeRenderer shapeRenderer = new ShapeRenderer();

  public GameScreen(final Heist game)
  {
    this.game = game;

    sprite = new Texture(Gdx.files.internal("droplet.png"));

    camera = new OrthographicCamera();
    camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    points.add(new Vector3(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0));
  }

  @Override
  public void show()
  {

  }

  @Override
  public void render(float delta)
  {
    Gdx.gl.glClearColor(0, 0, 0.2f, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    camera.update();

    game.batch.setProjectionMatrix(camera.combined);


    /*members*/
    if (Gdx.input.isTouched())
    {
      Vector3 touchPos = new Vector3();
      touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
      camera.unproject(touchPos);
      if (points.size > 0 && Math.abs(touchPos.x - points.peek().x) <= 50 && Math.abs(touchPos.y - points.peek().y) <= 50) points.add(touchPos);
    }
    /*render()*/
    if (points.size > 0)
    {
      Vector3[] pointsArray = points.toArray(Vector3.class);
      myCatmull = new CatmullRomSpline<>(pointsArray, false);

      shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
      for(int i = 0; i < myCatmull.spanCount-1; ++i)
      {
        shapeRenderer.line(myCatmull.controlPoints[i],  myCatmull.controlPoints[i+1]);
      }
      shapeRenderer.end();

//      game.batch.begin();
//      game.batch.draw(sprite, points.peek().x, points.poll().y);
//      game.batch.end();
    }
  }

  @Override
  public void resize(int width, int height)
  {

  }

  @Override
  public void pause()
  {

  }

  @Override
  public void resume()
  {

  }

  @Override
  public void hide()
  {

  }

  @Override
  public void dispose()
  {
    sprite.dispose();
  }
}
