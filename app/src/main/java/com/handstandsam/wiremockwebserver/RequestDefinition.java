package com.handstandsam.wiremockwebserver;

import java.util.HashMap;
import java.util.Map;

public class RequestDefinition {

    public ResponseDefinition getResponseDefinition() {
        return responseDefinition;
    }

    /**
     * NORMAL CLASS
     */
    String method;

    int priority;

    UrlMatcher urlMatcher;

    ValueMatcher requestBody;

    Map<String, ValueMatcher> headers = new HashMap();

    ResponseDefinition responseDefinition;

    RequestDefinition(String method) {
        this.method = method;
    }

    public RequestDefinition setUrlMatcher(UrlMatcher urlMatcher) {
        this.urlMatcher = urlMatcher;
        return this;
    }

    public RequestDefinition willReturn(ResponseDefinition responseDefinition) {
        this.responseDefinition = responseDefinition;
        return this;
    }

    public RequestDefinition withHeader(String key, String value) {
        this.headers.put(key, ValueMatcher.Builder.equalTo(value));
        return this;
    }

    public RequestDefinition withHeader(String key, ValueMatcher valueMatcher) {
        this.headers.put(key, valueMatcher);
        return this;
    }

    public RequestDefinition withRequestBody(ValueMatcher requestBody) {
        this.requestBody = requestBody;
        return this;
    }

    public RequestDefinition atPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public UrlMatcher getUrlMatcher() {
        return urlMatcher;
    }
}
