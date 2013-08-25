package com.game.isoball.gameEntities;

import android.graphics.Bitmap;

import com.game.isoball.gameObjects.FlatTile;
import com.game.isoball.gameObjects.GameObject;
import com.game.isoball.gameObjects.GameTile;
import com.game.isoball.gameObjects.RaisedTile;

import java.util.ArrayList;

/**
 * Created by Sean's Computer on 7/21/13.
 */
public abstract class GameEntity {
    public int tileX = 0;
    public int tileY = 0;
    public int tileWidth = 1;
    public int tileDepth = 1;

    public ArrayList<FlatTile> plateTiles = new ArrayList<FlatTile>();
    public ArrayList<GameTile> raisedTiles = new ArrayList<GameTile>();

    public abstract Bitmap getBitmap(GameObject gameObject);
    public abstract void addToBox2DWorld();
    public abstract boolean updateState();

}
