package com.ultraviolet.delieve.util;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.gms.common.images.ImageRequest;
import com.ultraviolet.delieve.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ImageLoadHelper {

    private static String buildMapRequestURL(double beginLatitude, double beginLongitude,
                                             double finishLatitude, double finishLongitude,
                                             String key) {
        String url;
        url = "https://maps.googleapis.com/maps/api/staticmap?";
        url += "&size=500x250";
        url += "&maptype=roadmap";
        url += "&markers=color:green%7Clabel:G%7C" + beginLatitude + "," + beginLongitude;
        url += "&markers=color:red7Clabel:G%7C" + finishLatitude + ", " + finishLongitude;
        url += "&key=" + key;
        return url;
    }

    public static void loadProfileImage(ImageView imageView, String URL){

        if (URL == null)
            return;
        Log.d("credt", "image url : " + URL);
        //start a background thread for networking
        new Thread(() -> {
            try {
                //download the drawable
                final Drawable drawable =
                        Drawable.createFromStream(
                                (InputStream) new URL(URL).getContent(), "kakao_profile_src");
                //edit the view in the UI thread
                imageView.post(() -> imageView.setImageDrawable(drawable));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();
    }

    public static void loadMapImage(ImageView imageView, double beginLatitude, double beginLongitude,
                             double finishLatitude, double finishLongitude, String key){
        String mapURL = buildMapRequestURL(beginLatitude, beginLongitude,
                finishLatitude, finishLongitude, key);
        new Thread(() -> {
            try {
                //download the drawable
                final Drawable drawable =
                        Drawable.createFromStream(
                                (InputStream) new URL(mapURL).getContent(), "map_image_src");
                //edit the view in the UI thread
                imageView.post(() -> imageView.setImageDrawable(drawable));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
