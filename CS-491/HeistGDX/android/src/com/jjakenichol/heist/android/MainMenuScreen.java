package com.jjakenichol.heist.android;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by JakeNichol on 4/18/15.
 */
public class MainMenuScreen implements Screen
{
  private final Heist game;

  private OrthographicCamera camera;

  private Texture titleScreen;

  public MainMenuScreen(Heist game)
  {
    this.game = game;

    titleScreen = new Texture(Gdx.files.internal("img/titleScreen.png"));

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
    game.batch.draw(titleScreen, 0, 0);
    game.batch.end();

    if (Gdx.input.isTouched())
    {
      game.setScreen(new GameScreen(game));
      dispose();
    }
  }

  @Override
  public void show()
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
