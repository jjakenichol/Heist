package com.jjakenichol.heist.android;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by JakeNichol on 4/18/15.
 */
public class GameScreen implements Screen
{
  private Heist game;
  private OrthographicCamera camera;

  public GameScreen(final Heist game)
  {
    this.game = game;

    camera = new OrthographicCamera();
    camera.setToOrtho(false, Constants.gameWidth, Constants.gameHeight);
  }

  @Override
  public void show()
  {

  }

  @Override
  public void render(float delta)
  {

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

  }
}
