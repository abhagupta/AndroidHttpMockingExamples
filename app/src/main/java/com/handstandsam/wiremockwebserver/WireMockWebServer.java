package com.handstandsam.wiremockwebserver;


public class WireMockWebServer {

    public static ResponseDefinition aResponse() {
        return new ResponseDefinition();
    }

    public static RequestDefinition get(UrlMatcher urlMatcher) {
        return new RequestDefinition("GET").setUrlMatcher(urlMatcher);
    }

    public static RequestDefinition post(UrlMatcher urlMatcher) {
        return new RequestDefinition("POST").setUrlMatcher(urlMatcher);
    }

    public static RequestDefinition put(UrlMatcher urlMatcher) {
        return new RequestDefinition("PUT").setUrlMatcher(urlMatcher);
    }

    public static RequestDefinition delete(UrlMatcher urlMatcher) {
        return new RequestDefinition("DELETE").setUrlMatcher(urlMatcher);
    }

    public static UrlMatcher urlMatching(String url) {
        return new UrlMatcher().setUrlPattern(url);
    }


    public static UrlMatcher urlEqualTo(String url) {
        return new UrlMatcher().setUrl(url);
    }

}
