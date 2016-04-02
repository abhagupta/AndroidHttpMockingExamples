package com.handstandsam.wiremockwebserver;

import java.util.HashMap;
import java.util.Map;

public class ResponseDefinition {

    int status = 200;

    String stringBody;

    byte[] binaryBody;

    Map<String, String> headers = new HashMap();

    ResponseDefinition() {

    }

    public ResponseDefinition withStatus(int status) {
        this.status = status;
        return this;
    }

    public ResponseDefinition withBody(String body) {
        this.stringBody = body;
        return this;
    }

    public ResponseDefinition withHeader(String key, String value) {
        this.headers.put(key, value);
        return this;
    }

    public ResponseDefinition withBody(byte[] binaryBody) {
        this.binaryBody = binaryBody;
        return this;
    }
}
