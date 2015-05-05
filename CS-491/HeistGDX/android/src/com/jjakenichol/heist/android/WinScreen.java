package com.jjakenichol.heist.android;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by JakeNichol on 5/4/15.
 */
public class WinScreen implements Screen
{
  private final Heist game;

  private OrthographicCamera camera;

  private Texture winScreen;

  public WinScreen(final Heist game)
  {
    this.game = game;

    winScreen = new Texture(Gdx.files.internal("img/winScreen.png"));

    camera = new OrthographicCamera();
    camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
  }

  @Override
  public void render(float delta)
  {
    Gdx.gl.glClearColor(0, 0, 0.2f, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    camera.update();

    game.batch.setProjectionMatrix(camera.combined);

    game.batch.begin();
    game.batch.draw(winScreen, 0, 0);
    game.batch.end();
  }

  @Override
  public void show()
  {

  }

  @Override
  public void resize(int width, int height)
  {
    camera.viewportWidth = width;
    camera.viewportHeight = height;
    camera.update();
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
    winScreen.dispose();
  }
}
