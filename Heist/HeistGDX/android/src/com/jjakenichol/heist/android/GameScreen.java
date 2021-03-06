package com.jjakenichol.heist.android;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

/**
 * Created by JakeNichol on 4/18/15.
 */
public class GameScreen implements Screen
{
  private final Heist game;
  private OrthographicCamera camera;

  private TiledMap map;
  private OrthogonalTiledMapRenderer renderer;
  private Player player;

  private Array<Vector3> points = new Array<>();
  private ShapeRenderer shapeRenderer = new ShapeRenderer();
  private BitmapFont font;

  private Texture keySprite;
  private Vector2 keyPos = new Vector2(400, 280);
  private Rectangle keyBox = new Rectangle(keyPos.x, keyPos.y, 24, 24);
  private boolean keyTaken = false;
  private boolean keyAdded = false;

  private Texture treasureSprite;
  private Vector2 treasurePos = new Vector2(830, 620);
  private Rectangle treasureBox = new Rectangle(treasurePos.x, treasurePos.y, 32, 24);
  private boolean treasureTaken = false;
  private boolean treasureAdded = false;

  private Rectangle winBox = new Rectangle(4 * 16 * Constants.UNIT_SCALE, Gdx.graphics.getHeight() - (48 * Constants.UNIT_SCALE),
          32 * Constants.UNIT_SCALE, 48 * Constants.UNIT_SCALE);

  public GameScreen(Heist game)
  {
    this.game = game;

    keySprite = new Texture(Gdx.files.internal("img/Key_Sprite.png"));
    treasureSprite = new Texture(Gdx.files.internal("img/ALttP_Treasure_Chest_2.png"));
    font = new BitmapFont();
    font.setColor(Color.OLIVE);

    camera = new OrthographicCamera();
    camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
  }

  @Override
  public void show()
  {
    map = new TmxMapLoader().load("maps/map.tmx");

    renderer = new OrthogonalTiledMapRenderer(map, Constants.UNIT_SCALE);

    points.add(new Vector3(840, 30, 0));

    player = new Player(new Sprite(new Texture("img/droplet.png")), points.peek(),
            points.peek());
  }

  @Override
  public void render(float delta)
  {
    Gdx.gl.glClearColor(0, 0, 0.2f, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    renderer.setView(camera);
    renderer.render();

    camera.update();

    game.batch.setProjectionMatrix(camera.combined);

    game.playTime += delta;
    game.batch.begin();
    font.draw(game.batch, Integer.toString((int) game.playTime), 32, 32);
    game.batch.end();

    if (!keyTaken)
    {
      game.batch.begin();
      game.batch.draw(keySprite, keyPos.x, keyPos.y);
      game.batch.end();
    }

    if (!treasureTaken)
    {
      game.batch.begin();
      game.batch.draw(treasureSprite, treasurePos.x, treasurePos.y);
      game.batch.end();
    }

//    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//    shapeRenderer.rect(player.getPlayerBox().getX(), player.getPlayerBox().getY(), player.getPlayerBox().getWidth(),
//            player.getPlayerBox().getHeight());
//    shapeRenderer.end();

//    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//    shapeRenderer.rect(winBox.getX(), winBox.getY(), winBox.getWidth(), winBox.getHeight());
//    shapeRenderer.end();

    /*input*/
    if (Gdx.input.isTouched())
    {
      Vector3 touchPos = new Vector3();
      touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
      camera.unproject(touchPos);

      TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers().get("Walls");
      TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();

      try
      {
        cell = collisionLayer.getCell((int) (touchPos.x / Constants.UNIT_SCALE / collisionLayer.getTileWidth()),
                (int) (touchPos.y / Constants.UNIT_SCALE / collisionLayer.getTileHeight()));
      } catch (Exception e)
      {
        e.printStackTrace();
      }

      if (!cell.getTile().getProperties().containsKey("blocked"))
      {
        if (cell.getTile().getProperties().containsKey("door") && player.getKeys() > 0)
        {
//          player.removeKey();
          cell.getTile().getProperties().put("unlocked", null);
          if (points.size > 0 && Math.abs(touchPos.x - points.peek().x) <= Constants.MOVE_DISTANCE && Math.abs(touchPos.y - points.peek().y) <=
                  Constants.MOVE_DISTANCE) points.add(touchPos);
        }
        else if (!cell.getTile().getProperties().containsKey("door") && points.size > 0 && Math.abs(touchPos.x - points.peek().x) <= Constants
                .MOVE_DISTANCE && Math.abs(touchPos.y - points.peek().y) <= Constants.MOVE_DISTANCE) points.add(touchPos);
      }
    }

    // Key Collision
    if (player.getLineBox().overlaps(keyBox) && !keyAdded)
    {
      player.addKey();
      keyAdded = true;
    }
    if (player.getPlayerBox().overlaps(keyBox) && !keyTaken) keyTaken = true;

    // Treasure Collision
    if (player.getLineBox().overlaps(treasureBox) && !treasureAdded)
    {
      player.addTreasure();
      treasureAdded = true;
    }
    if (player.getPlayerBox().overlaps(treasureBox) && !treasureTaken) treasureTaken = true;

    // Win Collision
    if (player.getPlayerBox().overlaps(winBox) && !player.hasWon() && player.getTreasure() > 0)
    {
      game.setScreen(new WinScreen(game));
      dispose();
    }

    // Render Line
    if (points.size > 0)
    {
      Vector3[] pointsArray = points.toArray(Vector3.class);

      CatmullRomSpline<Vector3> catmull = new CatmullRomSpline<>(pointsArray, false);

      shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
      shapeRenderer.setColor(Color.OLIVE);
      for (int i = 0; i < catmull.spanCount; i++)
      {
        shapeRenderer.line(catmull.controlPoints[i], catmull.controlPoints[i + 1]);
      }
      shapeRenderer.end();

      player.draw(renderer.getBatch(), catmull, points);
    }
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
    font.dispose();
    shapeRenderer.dispose();
    keySprite.dispose();
    treasureSprite.dispose();
    map.dispose();
    renderer.dispose();
    player.dispose();
  }
}
