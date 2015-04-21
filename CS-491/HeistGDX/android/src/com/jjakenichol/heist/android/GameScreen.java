package com.jjakenichol.heist.android;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

/**
 * Created by JakeNichol on 4/18/15.
 */
public class GameScreen implements Screen
{
  private Heist game;
  private OrthographicCamera camera;

  private TiledMap map;
  private OrthogonalTiledMapRenderer renderer;
  private Texture sprite;
  private Player player;

  private Array<Vector3> points = new Array<>();
  private ShapeRenderer shapeRenderer = new ShapeRenderer();

  public GameScreen(final Heist game)
  {
    this.game = game;

    sprite = new Texture(Gdx.files.internal("img/droplet.png"));

    camera = new OrthographicCamera();
    camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    points.add(new Vector3(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0));
  }

  @Override
  public void show()
  {
    map = new TmxMapLoader().load("maps/map.tmx");

    renderer = new OrthogonalTiledMapRenderer(map);

    player = new Player(new Sprite(new Texture("img/droplet.png")), new Vector3(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0));
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

//    renderer.getBatch().begin();
//    player.draw(renderer.getBatch());
//    renderer.getBatch().end();


    /*members*/
    if (Gdx.input.isTouched())
    {
      Vector3 touchPos = new Vector3();
      touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
      camera.unproject(touchPos);

//      player.setPlayerBox((int) (touchPos.x - player.getSize()), (int) (touchPos.y + player.getSize()), (int) (touchPos.x + player.getSize()),
//            (int) (touchPos.y - player.getSize()));
//      TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers().get("Walls");
//      MapObjects objects = collisionLayer.getObjects();
//
//      // there are several other types, Rectangle is probably the most common one
//      for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class))
//      {
//
//        Rectangle rectangle = rectangleObject.getRectangle();
//        if (Intersector.overlaps(rectangle, new Rectangle(player.getPlayerBox().left, player.getPlayerBox().top, player.getSize() * 2,
//                player.getSize() * 2)))
//        {
//          // collision happened
//          if (points.size > 0 && Math.abs(touchPos.x - points.peek().x) <= 50 && Math.abs(touchPos.y - points.peek().y) <= 50) points.add(touchPos);
//        }
//      }


      if (points.size > 0 && Math.abs(touchPos.x - points.peek().x) <= 50 && Math.abs(touchPos.y - points.peek().y) <= 50) points.add(touchPos);
    }
    /*render()*/
    if (points.size > 0)
    {
      Vector3[] pointsArray = points.toArray(Vector3.class);

      CatmullRomSpline<Vector3> catmull = new CatmullRomSpline<>(pointsArray, false);

      shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
      shapeRenderer.setColor(Color.OLIVE);
      for (int i = 0; i < catmull.spanCount - 1; ++i)
      {
        shapeRenderer.line(catmull.controlPoints[i], catmull.controlPoints[i + 1]);
      }
      shapeRenderer.end();

      player.draw(renderer.getBatch(), catmull, points);

//      game.batch.begin();
//      game.batch.draw(sprite, points.peek().x, points.poll().y);
//      game.batch.end();
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
    sprite.dispose();
    map.dispose();
    renderer.dispose();
    player.getRenderer().dispose();
  }
}
