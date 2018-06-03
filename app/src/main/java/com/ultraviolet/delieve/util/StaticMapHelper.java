package com.ultraviolet.delieve.util;

import com.ultraviolet.delieve.R;

public class StaticMapHelper {
    public static String buildURL(double lat, double lng, String key) {
        String url;
        url = "https://maps.googleapis.com/maps/api/staticmap?";
        url += "&zoom=14";
        url += "&size=900x600";
        url += "&maptype=roadmap";
        url += "&markers=color:green%7Clabel:G%7C" + lat + "," + lng;
        url += "&key=" + key;
        return url;
    }
}
