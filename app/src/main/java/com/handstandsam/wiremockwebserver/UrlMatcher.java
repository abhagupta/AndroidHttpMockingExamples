package com.handstandsam.wiremockwebserver;

public class UrlMatcher {
    private String url;
    private String urlPattern;
//    private String urlPath;
//    private String urlPathPattern;

    public UrlMatcher() {
    }

    public UrlMatcher setUrl(String url) {
        this.url = url;
        return this;
    }

    public UrlMatcher setUrlPattern(String urlPattern) {
        this.urlPattern = urlPattern;
        return this;
    }

    public boolean matches(String requestUrl) {
        if (requestUrl != null) {
            if (url != null && requestUrl.equals(url)) {
                return true;
            }

            if (urlPattern != null && requestUrl.matches(urlPattern)) {
                return true;
            }
        }

        return false;
    }
}
