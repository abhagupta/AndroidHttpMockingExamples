package com.handstandsam.wiremockwebserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class TestUtils {
    public static String readFully(InputStream inputStream, String encoding)
            throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = 0;
        while ((length = inputStream.read(buffer)) != -1) {
            baos.write(buffer, 0, length);
        }
        return baos.toString(encoding);
    }
}
