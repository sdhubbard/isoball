package com.game.isoball.gameObjects;

import android.graphics.Bitmap;

import com.game.isoball.R;
import com.game.isoball.util.BallPositionHolder;
import com.game.isoball.util.NativePhysicsUtil;

/**
 * Created by Sean's Computer on 7/13/13.
 */
public class Ball extends GameObject implements MovingGameObject {
	public static final int RED = 0;
	public static final int BLUE = 1;
	public static final int GREEN = 2;
	public static final int MAGENTA = 3;
	public static final int YELLOW = 4;
	public static final int CYAN = 5;

	public static final int BALL_TYPES = 6;

	public int ballType = 0;
	public boolean bodySet = false;

	public Ball() {
		level = 1;
	}

	public void addToBox2DWorld() {
		if(bodySet) {
			return;
		}

		NativePhysicsUtil.AddBall(id, tileX + .5f, tileY + .5f);
		bodySet = true;
	}

	public Bitmap getBitmap() {
		switch(ballType) {
			case RED:
				return ImageMap.getBitmap(R.drawable.ball);
			case BLUE:
				return ImageMap.getBitmap(R.drawable.blue_ball);
			case GREEN:
				return ImageMap.getBitmap(R.drawable.green_ball);
			case MAGENTA:
				return ImageMap.getBitmap(R.drawable.magenta_ball);
			case YELLOW:
				return ImageMap.getBitmap(R.drawable.yellow_ball);
			case CYAN:
				return ImageMap.getBitmap(R.drawable.cyan_ball);    	
		}

		return ImageMap.getBitmap(R.drawable.ball);
	}

	public void updateLocation(BallPositionHolder bpHolder) {
		for(int index = 0; index < bpHolder.ids.length; index++) {
			if(bpHolder.ids[index] == id) {
				tileX = bpHolder.xValues[index] - .5f;
				tileY = bpHolder.yValues[index] - .5f;
				return;
			}
		}
	}
}
