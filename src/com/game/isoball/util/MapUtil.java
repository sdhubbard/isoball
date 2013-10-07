package com.game.isoball.util;

import java.util.ArrayList;

import android.graphics.PointF;

import com.game.isoball.gameEntities.CircularLauncherMechanism;
import com.game.isoball.gameEntities.GameEntity;
import com.game.isoball.gameObjects.Ball;
import com.game.isoball.gameObjects.CircularLauncherCylinder;
import com.game.isoball.gameObjects.CircularLauncherPlate;
import com.game.isoball.gameObjects.FlatTile;
import com.game.isoball.gameObjects.GameObject;
import com.game.isoball.gameObjects.GameTile;
import com.game.isoball.gameObjects.RaisedTile;

/**
 * Created by Sean's Computer on 7/9/13.
 */
public class MapUtil {
    private static final int FLAT_TILE = 0;
    private static final int RAISED_TILE = 1;
    private static final int CIRCULAR_LAUNCHER_PLATE = 2;

    public static final int TILE_DEPTH = 22;
    public static final int TILE_WIDTH = 44;

    private static int sortDepth = 0;
    public static ArrayList<GameEntity> entities;

    public static ArrayList<GameObject> generateTileGrid(int[][] mapGrid) {
        GameTile[][] tileGrid = new GameTile[mapGrid.length][];
        ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();

        entities = new ArrayList<GameEntity>();

        for (int y = 0; y < mapGrid.length; y++) {
            tileGrid[y] = new GameTile[mapGrid[y].length];

            for (int x = 0; x < mapGrid[y].length; x++) {
                switch (mapGrid[y][x]) {
                    case FLAT_TILE:
                        tileGrid[y][x] = new FlatTile();
                        break;
                    case RAISED_TILE:
                        tileGrid[y][x] = new RaisedTile();
                        break;
                    case CIRCULAR_LAUNCHER_PLATE:
                        tileGrid[y][x] = getCircularLauncherPlate(gameObjects,x,y);
                        break;
                    default:
                        tileGrid[y][x] = new RaisedTile();
                        break;
                }
                if(!gameObjects.contains(tileGrid[y][x])) {
                    tileGrid[y][x].tileX = x;
                    tileGrid[y][x].tileY = y;

                    gameObjects.add(tileGrid[y][x]);
                }
            }
        }

        for (int y = 0; y < tileGrid.length; y++) {
            for (int x = 0; x < tileGrid[y].length; x++) {
                GameTile gameTile = tileGrid[y][x];

                if (y > 0) {
                    gameTile.rearTile = tileGrid[y-1][x];
                }

                if (y < tileGrid.length - 1) {
                    gameTile.frontTile = tileGrid[y + 1][x];
                }

                if ( x > 0) {
                    gameTile.leftTile = tileGrid[y][x - 1];
                }

                if ( x < tileGrid[y].length - 1) {
                    gameTile.rightTile = tileGrid[y][x + 1];
                }
            }
        }
        
        for(GameObject gameobject : gameObjects) {
        	gameobject.addToBox2DWorld();
        }

        return gameObjects;
    }

    private static CircularLauncherPlate getCircularLauncherPlate(ArrayList<GameObject> gameObjects,
                                                                  int x, int y) {
        CircularLauncherMechanism cLMech = null;

        for(GameObject gameObject : gameObjects) {

            if(!(gameObject instanceof CircularLauncherPlate)) {
                continue;
            }

            cLMech = ((CircularLauncherPlate)gameObject).circularLauncherMechanism;
            if( x >= cLMech.tileX &&
                    x < cLMech.tileWidth + cLMech.tileX &&
                    y >= cLMech.tileY && y < cLMech.tileDepth + cLMech.tileY) {
                return new CircularLauncherPlate(cLMech);
            }
        }

        cLMech = new CircularLauncherMechanism(x,y);
        entities.add(cLMech);
        for(int clY = 0; clY < cLMech.tileDepth; clY++) {
            for(int clX = 0; clX < cLMech.tileWidth; clX++) {
                CircularLauncherCylinder clCylinder = new CircularLauncherCylinder(cLMech,clX,clY);
            }
        }

        return new CircularLauncherPlate(cLMech);
    }

    public static void updateGameObjects(float centerX, float centerY,
    		ArrayList<GameObject> gameObjects) {
    	PointF screenPoint = null;
    	BallPositionHolder bPHolder = NativePhysicsUtil.GetBallPositions();
    	
    	for(GameObject gameObject : gameObjects) {
    		if(gameObject instanceof Ball) {
    			((Ball)gameObject).updateLocation(bPHolder);
    		}

    		screenPoint = getIsometricPoint(centerX, centerY,
    				gameObject.tileX, gameObject.tileY, gameObject.tileZ);
    		gameObject.screenX = screenPoint.x;
    		gameObject.screenY = screenPoint.y;

    		gameObject.minX = gameObject.tileX + gameObject.minXRelative;
    		gameObject.maxX = gameObject.tileX + gameObject.maxXRelative;
    		gameObject.minY = gameObject.tileY + gameObject.minYRelative;
    		gameObject.maxY = gameObject.tileY + gameObject.maxYRelative;
    		gameObject.minZ = gameObject.tileZ + gameObject.minZRelative;
    		gameObject.maxZ = gameObject.tileZ + gameObject.maxZRelative;
    	}
    }

    public static void DepthSort(ArrayList<GameObject> gameObjects) {
    	ArrayList<GameObject> ndkGameObjects = new ArrayList<GameObject>();
    	GameObject[] ndkGameObjectsArray = null;
    	final ArrayList<Long> idArray = new ArrayList<Long>();
    	
    	for(GameObject gameObject : gameObjects) {        
    		if (!(gameObject instanceof FlatTile)) {
    			ndkGameObjects.add(gameObject);
    		} else {
    			idArray.add(gameObject.id);
    		}
    	}

    	ndkGameObjectsArray = ndkGameObjects.toArray(new GameObject[ndkGameObjects.size()]);
    	long[] deptSortArray = NativeDrawingUtil.DepthSortGameObjects(ndkGameObjectsArray);

    	for(GameObject gameObject : ndkGameObjects) {
    		gameObjects.remove(gameObject);
    	}

    	for(int index = 0; index < deptSortArray.length; index++) {
    		long id = deptSortArray[index];

    		for(GameObject gameObject : ndkGameObjects) {
    			if(gameObject.id == id) {
    				gameObjects.add(gameObject);
    			}
    		}        	
    	}
    }

    private static PointF getIsometricPoint(float centerX, float centerY,
                                            float x, float y, float z) {
        float newX = 0f;
        float newY = 0f;
        float tileWidthMultiplicand = (TILE_WIDTH / 2);
        
        newX = (centerX) - (y * (TILE_WIDTH / 2)) +
                (x * (TILE_WIDTH / 2)) - tileWidthMultiplicand;
        newY = (centerY) + (y * (TILE_DEPTH / 2)) +
                (x * (TILE_DEPTH / 2));

        return new PointF(newX, newY);
    }
}
