package com.game.isoball.gameObjects;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.game.isoball.R;

import java.util.HashMap;

/**
 * Created by Sean's Computer on 7/8/13.
 */
public class ImageMap {
    private static HashMap<Integer, Bitmap> idBitmapMap = null;
    private static Resources resources;
    private static BitmapFactory.Options bitmapFactoryOptions;

    public static void generateMap(Context context) {
        resources = context.getResources();

        bitmapFactoryOptions = new BitmapFactory.Options();
        bitmapFactoryOptions.inPreferQualityOverSpeed = true;
        bitmapFactoryOptions.inScaled = false;
        idBitmapMap = new HashMap<Integer, Bitmap>();

        registerBitmap(R.drawable.blockraised);
        registerBitmap(R.drawable.blockflat);
        registerBitmap(R.drawable.blockraisedblue);
        registerBitmap(R.drawable.blockflatblue);
        registerBitmap(R.drawable.ball);
    }

    public static Bitmap getBitmap(int id) {
        Bitmap bitmap = idBitmapMap.get(id);

        if(bitmap == null) {
            bitmap = registerBitmap(id);
        }

        return bitmap;
    }

    private static Bitmap registerBitmap(int id) {
        Bitmap currentBitmap = BitmapFactory.decodeResource(resources, id, bitmapFactoryOptions);

        idBitmapMap.put(id, currentBitmap);
        return currentBitmap;
    }


}
