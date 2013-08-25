package com.game.isoball.gameObjects;

import java.util.ArrayList;

import android.graphics.Bitmap;

import com.game.isoball.R;
import com.game.isoball.util.NativePhysicsUtil;

/**
 * Created by Sean's Computer on 7/8/13.
 */
public class RaisedTile extends GameTile {
	public boolean rearSet = false;
	public boolean frontSet = false;
	public boolean leftSet = false;
	public boolean rightSet = false;

    private ArrayList<RaisedTile> tilesToAdd = null;

    public RaisedTile() {
    	level = 1;
        tileZ = .1f;
        maxZRelative = 1.1f;
    }

    @Override
    public Bitmap getBitmap() {
        if((tileX + tileY) % 2 == 0) {
            return ImageMap.getBitmap(R.drawable.blockraised);
        }

        return ImageMap.getBitmap(R.drawable.blockraisedblue);
    }

    @Override
    public void addToBox2DWorld() {
        try {

            generateRear();
            generateFront();
            generateLeft();
            generateRight();
        } catch (OutOfMemoryError error) {
            error.printStackTrace();
        }
    }

    private void generateRear() {
        if(rearSet) {
            return;
        }

        float startPointX = tileX;
        float endPointX = tileX + 1;
        float verticalPointY = tileY;

        GameTile currentLeft = leftTile;
        GameTile currentRight = rightTile;

        tilesToAdd = new ArrayList<RaisedTile>();
        tilesToAdd.add(this);

        while(currentLeft != null &&
                currentLeft instanceof RaisedTile &&
                !(currentLeft.rearTile instanceof RaisedTile)) {
            startPointX = currentLeft.tileX;
            tilesToAdd.add((RaisedTile)currentLeft);
            currentLeft = currentLeft.leftTile;
        }

        while(currentRight != null &&
                currentRight instanceof RaisedTile &&
                !(currentRight.rearTile instanceof RaisedTile)) {
            endPointX = currentRight.tileX + 1;
            tilesToAdd.add((RaisedTile)currentRight);
            currentRight = currentRight.rightTile;
        }

        NativePhysicsUtil.AddEdge(startPointX, verticalPointY, endPointX, verticalPointY);
        
        for(RaisedTile currentTile : tilesToAdd) {
            currentTile.rearSet = true;
        }
    }

    private void generateFront() {
        if(frontSet) {
            return;
        }

        float startPointX = tileX;
        float endPointX = tileX + 1;
        float verticalPointY = tileY + 1;
        GameTile currentLeft = leftTile;
        GameTile currentRight = rightTile;

        tilesToAdd = new ArrayList<RaisedTile>();
        tilesToAdd.add(this);

        while(currentLeft != null &&
                currentLeft instanceof RaisedTile &&
                !(currentLeft.frontTile instanceof RaisedTile)) {
            startPointX = currentLeft.tileX;
            tilesToAdd.add((RaisedTile)currentLeft);
            currentLeft = currentLeft.leftTile;
        }

        while(currentRight != null &&
                currentRight instanceof RaisedTile &&
                !(currentRight.frontTile instanceof RaisedTile)) {
            endPointX = currentRight.tileX + 1;
            tilesToAdd.add((RaisedTile)currentRight);
            currentRight = currentRight.rightTile;
        }

        NativePhysicsUtil.AddEdge(startPointX, verticalPointY, endPointX, verticalPointY);
        
        for(RaisedTile currentTile : tilesToAdd) {
            currentTile.frontSet = true;
        }
    }

    private void generateLeft() {
        if(leftSet) {
            return;
        }

        float startPointY = tileY;
        float endPointY = tileY + 1;
        float horizontalPointX = tileX;
        GameTile currentFront = frontTile;
        GameTile currentRear = rearTile;

        tilesToAdd = new ArrayList<RaisedTile>();
        tilesToAdd.add(this);

        while(currentRear != null &&
                currentRear instanceof RaisedTile &&
                !(currentRear.leftTile instanceof RaisedTile)) {
            startPointY = currentRear.tileY;
            tilesToAdd.add((RaisedTile)currentRear);
            currentRear = currentRear.rearTile;
        }

        while(currentFront != null &&
                currentFront instanceof RaisedTile &&
                !(currentFront.leftTile instanceof RaisedTile)) {
            endPointY = currentFront.tileY + 1;
            tilesToAdd.add((RaisedTile)currentFront);
            currentFront = currentFront.frontTile;
        }

        NativePhysicsUtil.AddEdge(horizontalPointX, startPointY, horizontalPointX, endPointY);
        
        for(RaisedTile currentTile : tilesToAdd) {
            currentTile.leftSet = true;
        }
    }

    private void generateRight() {
        if(rightSet) {
            return;
        }

        float startPointY = tileY;
        float endPointY = tileY + 1;
        float horizontalPointX = tileX + 1;
        GameTile currentFront = frontTile;
        GameTile currentRear = rearTile;

        tilesToAdd = new ArrayList<RaisedTile>();
        tilesToAdd.add(this);

        while(currentRear != null &&
                currentRear instanceof RaisedTile &&
                !(currentRear.rightTile instanceof RaisedTile)) {
            startPointY = currentRear.tileY;
            tilesToAdd.add((RaisedTile)currentRear);
            currentRear = currentRear.rearTile;
        }

        while(currentFront != null &&
                currentFront instanceof RaisedTile &&
                !(currentFront.rightTile instanceof RaisedTile)) {
            endPointY = currentFront.tileY + 1;
            tilesToAdd.add((RaisedTile)currentFront);
            currentFront = currentFront.frontTile;
        }

        NativePhysicsUtil.AddEdge(horizontalPointX, startPointY, horizontalPointX, endPointY);
        
        for(RaisedTile currentTile : tilesToAdd) {
            currentTile.rightSet = true;
        }
    }

/*    private Body addEdge(World world, Vec2 startVec, Vec2 endVec) {
        BodyDef bodyDef = new BodyDef();
        Body borderBody = null;
        EdgeShape borderLine = new EdgeShape();

        bodyDef.position.set(0,0);
        borderBody = world.createBody(bodyDef);
        borderLine.set(startVec, endVec);
        borderBody.createFixture(borderLine, 0);

        return borderBody;
    }*/
}
