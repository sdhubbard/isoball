package com.game.isoball.gameObjects;

import android.graphics.Bitmap;

import com.game.isoball.gameEntities.CircularLauncherMechanism;

/**
 * Created by Sean's Computer on 7/27/13.
 */
public class CircularLauncherCylinder extends GameTile {
    public CircularLauncherMechanism circularLauncherMechanism;

    public CircularLauncherCylinder(CircularLauncherMechanism circularLauncherMechanism,
                                    int x, int y) {
        super();
        level = 1;
        tileZ = .1f;
        maxZRelative = 1.1f;
        this.circularLauncherMechanism = circularLauncherMechanism;
        tileX = circularLauncherMechanism.tileX + x;
        tileY = circularLauncherMechanism.tileY + y;
        circularLauncherMechanism.raisedTiles.add(this);
    }

    @Override
    public Bitmap getBitmap() {
        return circularLauncherMechanism.getBitmap(this);
    }


}
