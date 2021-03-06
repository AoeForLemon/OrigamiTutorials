package io.github.veroz.origamitutorials.utils;

import android.content.Context;
import android.util.Log;

import java.io.File;

/**
 * Created by VeroZ on 2018/1/10.
 */

public class PicassoUtils {

    public static void clearCache(Context context) {

        File file = new File(context.getCacheDir() + "/picasso-cache");
        Log.d("cachePath -->> ", file.getPath());
        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            Log.d("cacheLength -->> ", String.valueOf(files.length));
            for (File cache : files) {
                if (cache.isFile()) {
                    Log.d("cacheDelete -->> ", cache.getName());
                    cache.delete();
                }
            }
        }

    }

}
