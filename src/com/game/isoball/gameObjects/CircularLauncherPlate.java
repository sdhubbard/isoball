package com.game.isoball.gameObjects;

import android.graphics.Bitmap;

import com.game.isoball.R;
import com.game.isoball.gameEntities.CircularLauncherMechanism;

/**
 * Created by Sean's Computer on 7/15/13.
 */
public class CircularLauncherPlate extends FlatTile {
    public CircularLauncherMechanism circularLauncherMechanism;

    public CircularLauncherPlate(CircularLauncherMechanism circularLauncherMechanism) {
        super();
        maxXRelative = 1;
        maxYRelative = 1;
        level = 1;
        this.circularLauncherMechanism = circularLauncherMechanism;
        circularLauncherMechanism.plateTiles.add(this);
    }

    @Override
    public Bitmap getBitmap() {
        return circularLauncherMechanism.getBitmap(this);
    }

    @Override
    public void addToBox2DWorld() {
        circularLauncherMechanism.addToBox2DWorld();
    }
}
