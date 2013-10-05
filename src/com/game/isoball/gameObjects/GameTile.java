package com.game.isoball.gameObjects;

import android.graphics.Bitmap;

/**
 * Created by Sean's Computer on 7/8/13.
 */
public abstract class GameTile extends GameObject {
    public GameTile leftTile = null;
    public GameTile rightTile = null;
    public GameTile rearTile = null; //should have a tileY one less that this tile.
    public GameTile frontTile = null; //should have a tileY one more than this tile.



}
