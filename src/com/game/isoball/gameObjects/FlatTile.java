package com.game.isoball.gameObjects;

import android.graphics.Bitmap;

import com.game.isoball.R;

/**
 * Created by Sean's Computer on 7/8/13.
 */
public class FlatTile extends GameTile {
    public FlatTile() {
        maxZRelative = 0f;
    }

    @Override
    public Bitmap getBitmap() {
        if((tileX + tileY) % 2 == 0) {
            return ImageMap.getBitmap(R.drawable.blockflat);
        }

        return ImageMap.getBitmap(R.drawable.blockflatblue);
    }
}
