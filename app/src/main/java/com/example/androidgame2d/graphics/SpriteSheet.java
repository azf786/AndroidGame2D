package com.example.androidgame2d.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;

import com.example.androidgame2d.R;

public class SpriteSheet {
    private Bitmap bitmap;

    public SpriteSheet(Context context) {
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ball_blue_large, bitmapOptions);
        bitmap = getResizedBitmap(bitmap, 128, 128);
        System.out.println(bitmap.getHeight());
    }

    public Sprite getPlayerSprite() {
        return new Sprite(this, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()));
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
