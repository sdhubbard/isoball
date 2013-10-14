package com.game.isoball.gameEntities;

import java.util.Date;

import android.graphics.Bitmap;

import com.game.isoball.R;
import com.game.isoball.gameObjects.Ball;
import com.game.isoball.gameObjects.GameObject;
import com.game.isoball.gameObjects.ImageMap;
import com.game.isoball.util.NativePhysicsUtil;

/**
 * Created by Sean's Computer on 7/21/13.
 */
public class CircularLauncherMechanism extends GameEntity {
	private boolean bodySet = false;
    private Ball newBall = null;
    private Date lastLaunch = null;
    private int launchPeriod = 1000; //One second.
    private float launchMagnitude = 1000;

    private enum Direction {FORWARD,
            FORWARD_LEFT,
            LEFT,
            REARWARD_LEFT,
            REAR,
            REARWARD_RIGHT,
            RIGHT,
            FORWARD_RIGHT};

    private Direction currentDirection = Direction.FORWARD;

    private static final int[][] plateTileDrawables = {{R.drawable.circular_launcher_plate_0_0,
                R.drawable.circular_launcher_plate_0_1,
                R.drawable.circular_launcher_plate_0_2},
            {R.drawable.circular_launcher_plate_1_0,
                    R.drawable.circular_launcher_plate_1_1,
                    R.drawable.circular_launcher_plate_1_2},
            {R.drawable.circular_launcher_plate_2_0,
                    R.drawable.circular_launcher_plate_2_1,
                    R.drawable.circular_launcher_plate_2_2}};

    private static final int[][] launcherDrawables = {
            {R.drawable.circular_launcher_0_0,
            R.drawable.circular_launcher_0_1,
            R.drawable.circular_launcher_0_2},
            {R.drawable.circular_launcher_1_0,
            R.drawable.circular_launcher_1_1,
            R.drawable.circular_launcher_1_2},
            {R.drawable.circular_launcher_2_0,
            R.drawable.circular_launcher_2_1,
            R.drawable.circular_launcher_2_2}};

    public CircularLauncherMechanism(int tileX, int tileY) {
        super();

        this.tileX = tileX;
        this.tileY = tileY;
        tileDepth = 3;
        tileWidth = 3;
        lastLaunch = new Date();
    }

    @Override
    public Bitmap getBitmap(GameObject gameObject) {
        boolean isPlate = false;
        boolean isRaised = false;

        if(plateTiles.contains(gameObject)) {
            isPlate = true;
        }

        if(raisedTiles.contains(gameObject)) {
            isRaised = true;
        }

        int deltaX = (int)gameObject.tileX - tileX;
        int deltaY = (int)gameObject.tileY - tileY;

        if(isPlate) {
            return ImageMap.getBitmap(plateTileDrawables[deltaY][deltaX]);
        }

        //worry about this later.
        if(isRaised) {
            return ImageMap.getBitmap(launcherDrawables[deltaY][deltaX]);
        }

        return  null;
    }

    @Override
    public void addToBox2DWorld() {
        if(bodySet) {
            return;
        }

        NativePhysicsUtil.AddCircularLauncher(tileX, tileY);
        bodySet = true;
    }

    @Override
    public boolean updateState() {
        Date currentDate = new Date();


        if(currentDate.getTime() - lastLaunch.getTime() < launchPeriod) {
            return false;
        }

        lastLaunch = currentDate;
        createBall();        
        return true;
    }

    private void createBall() {
        float startTileX = tileX + 1f;
        float startTileY = tileY + 1f;
        float launchDirectionX = 0;
        float launchDirectionY = 0;
        Double ballType = Math.floor(Math.random() * Ball.BALL_TYPES);
        
        newBall = new Ball();
        switch (currentDirection) {
            case FORWARD:
                startTileY = startTileY + 1f;
                launchDirectionX = 0;
                launchDirectionY = launchMagnitude;
                currentDirection = Direction.FORWARD_LEFT;
                break;
            case FORWARD_LEFT:
                startTileX = startTileX - 1f;
                startTileY = startTileY + 1f;
                launchDirectionX = launchMagnitude * -(float)Math.sqrt(2);
                launchDirectionY = launchMagnitude * (float)Math.sqrt(2);                
                currentDirection = Direction.LEFT;
                break;
            case LEFT:
                startTileX = startTileX - 1f;
                launchDirectionX = -launchMagnitude;
                launchDirectionY = 0;
                currentDirection = Direction.REARWARD_LEFT;
                break;
            case REARWARD_LEFT:
                startTileX = startTileX - 1f;
                startTileY = startTileY - 1f;
                launchDirectionX = launchMagnitude * -(float)Math.sqrt(2);
                launchDirectionY = launchMagnitude * -(float)Math.sqrt(2);
                currentDirection = Direction.REAR;
                break;
            case REAR:
                startTileY = startTileY - 1f;
                launchDirectionX = 0;
                launchDirectionY = -launchMagnitude;
                currentDirection = Direction.REARWARD_RIGHT;
                break;
            case REARWARD_RIGHT:
                startTileX = startTileX + 1f;
                startTileY = startTileY - 1f;
                launchDirectionX = launchMagnitude * (float)Math.sqrt(2);
                launchDirectionY = launchMagnitude * -(float)Math.sqrt(2);
                currentDirection = Direction.RIGHT;
                break;
            case RIGHT:
                startTileX = startTileX + 1f;
                launchDirectionX = launchMagnitude;
                launchDirectionY = 0;
                currentDirection = Direction.FORWARD_RIGHT;
                break;
            case FORWARD_RIGHT:
                startTileX = startTileX + 1f;
                startTileY = startTileY + 1f;
                launchDirectionX = launchMagnitude * (float)Math.sqrt(2);
                launchDirectionY = launchMagnitude * (float)Math.sqrt(2);
                currentDirection = Direction.FORWARD;
                break;
            default:
                startTileX = startTileX + 1;
                startTileY = startTileY + 3;
                launchDirectionX = 0;
                launchDirectionY = launchMagnitude;
                break;
        }

        newBall.tileX = startTileX;
        newBall.tileY = startTileY;
        newBall.ballType = ballType.intValue();
        newBall.addToBox2DWorld();
        NativePhysicsUtil.SetBallVelocity(newBall.id, launchDirectionX, launchDirectionY);
    }

    public Ball getNewBall() {
        Ball localBall = newBall;

        newBall = null;

        return localBall;
    }
}
