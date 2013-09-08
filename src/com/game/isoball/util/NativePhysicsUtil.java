package com.game.isoball.util;

public class NativePhysicsUtil {
	static {
		System.loadLibrary("IsoBall");
	}
	
	static public native void InitializeWorld();
	
	static public native void UpdateWorld();
	
	static public native void AddBall(long id, float tileX, float tileY);
	
	static public native void SetBallVelocity(long id, float velocityX, float velocityY);
	
	static public native float[] GetBallPosition(long id);
	
	static public native BallPositionHolder GetBallPositions();
	
	static public native void AddEdge(float startX, float startY, float endX, float endY);
	
	static public native void AddCircularLauncher(float tileX, float tileY);

	static public native long[] GetTouching(long id);
}
