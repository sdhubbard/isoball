package com.game.isoball.gameObjects;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Sean's Computer on 7/14/13.
 */
public abstract class GameObject {
	public long id = (long) (Math.random() * Long.MAX_VALUE);
    public float tileX = 0;
    public float tileY = 0;
    public float tileZ = 0;
    public int level = 0;
    public float tileHeight = 0;
    public float tileWidth = 1;
    public float tileDepth = 1;

    // Based on tutorial from:
    // http://mazebert.com/2013/04/18/isometric-depth-sorting/

    // Axis aligned bounding box in world space
    public float minX = 0;
    public float maxX = 0;
    public float minY = 0;
    public float maxY = 0;
    public float minZ = 0;
    public float maxZ = 0;

    // Axis aligned bounding box in iso model space
    public float minXRelative = 0;
    public float maxXRelative = 1;
    public float minYRelative = 0;
    public float maxYRelative = 1;
    public float minZRelative = 0;
    public float maxZRelative = 1;

    // Internal variables for sorting in the renderer.
    public float isoDepth = 0;
    public ArrayList<GameObject> gameObjectsBehind = null;
    public int isoVisitedFlag = 0;

    public float screenX = 0;
    public float screenY = 0;

    //Default behavior, do nothing.
    public void addToBox2DWorld() {
        return;
    }

    public abstract Bitmap getBitmap();
}
