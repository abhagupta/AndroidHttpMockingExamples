package com.handstandsam.wiremockwebserver;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AssetReaderUtil {

    public static String assetAsString(Context context, String assetPath) {
        try {
            StringBuilder buf = new StringBuilder();
            InputStream inputStream = context.getAssets().open(assetPath);
            return inputStreamToString(inputStream, "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final int BUFFER_SIZE = 4 * 1024;

    public static String inputStreamToString(InputStream inputStream, String charsetName)
            throws IOException {
        StringBuilder builder = new StringBuilder();
        InputStreamReader reader = new InputStreamReader(inputStream, charsetName);
        char[] buffer = new char[BUFFER_SIZE];
        int length;
        while ((length = reader.read(buffer)) != -1) {
            builder.append(buffer, 0, length);
        }
        return builder.toString();
    }

    public static String readRawTextFile(Context ctx, int resId) {
        try {
            InputStream inputStream = ctx.getResources().openRawResource(resId);

            String line;
            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder total = new StringBuilder();
            while ((line = r.readLine()) != null) {
                total.append(line);
            }
            return total.toString();
        } catch (IOException e) {
            return null;
        }
    }
}
