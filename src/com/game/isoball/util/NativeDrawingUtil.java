package com.game.isoball.util;

import com.game.isoball.gameObjects.GameObject;

public class NativeDrawingUtil {
	static {
		System.loadLibrary("IsoBall");
	}
	
	static public native long[] DepthSortGameObjects(GameObject[] gameObjects);
	
	static public native int GetAnInt(int testInt);
		
}
