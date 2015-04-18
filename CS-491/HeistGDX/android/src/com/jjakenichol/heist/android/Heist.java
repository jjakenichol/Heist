package com.jjakenichol.heist.android;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by JakeNichol on 4/18/15.
 */
public class Heist extends Game
{
  public SpriteBatch batch;
  public BitmapFont font;

  public void create()
  {
    batch = new SpriteBatch();
    //Use LibGDX's default Arial font.
    font = new BitmapFont();
    this.setScreen(new MainMenuScreen(this));
  }

  public void render()
  {
    super.render();
  }

  public void dispose()
  {
    batch.dispose();
    font.dispose();
  }
}
