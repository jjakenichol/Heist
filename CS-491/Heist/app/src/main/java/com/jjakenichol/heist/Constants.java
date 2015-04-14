package com.jjakenichol.heist;

import android.content.res.Resources;
import android.graphics.Color;
import android.util.DisplayMetrics;

/**
 * Created by JakeNichol on 4/9/15.
 */
public class Constants
{
  public static final DisplayMetrics METRICS = Resources.getSystem().getDisplayMetrics();
  public static final float SCALE = METRICS.densityDpi / 480; // 480 is the DPI used in design.

  public static final int TEXT_SIZE = 50;

  public static final int FINISH_COLOR = Color.YELLOW;

  public static final int DOOR_SIZE = 100;
  public static final int HALL_WIDTH = 140;

  public static final int WALL_COLOR = Color.WHITE;
  public static final int WALL_WIDTH = 10;
  public static final int WALL_BUFFER = 10;

  public static final int PLAYER_COLOR = Color.GREEN;
  public static final int PLAYER_SIZE = 20;

  public static final int PATH_COLOR = Color.GREEN;
  public static final int PATH_WIDTH = 5;
}
