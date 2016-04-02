package com.handstandsam.wiremockwebserver;

public class ValueMatcher {

    /**
     * STATIC
     */
    public static class Builder {
        public static ValueMatcher matching(String matching) {
            return new ValueMatcher().setMatches(matching);
        }

        public static ValueMatcher equalTo(String value) {
            return new ValueMatcher().setEqualTo(value);
        }

        public static ValueMatcher containing(String contains) {
            return new ValueMatcher().setContains(contains);
        }

        public static ValueMatcher notMatching(String doesNotMatch) {
            return new ValueMatcher().setDoesNotMatch(doesNotMatch);
        }
    }

    /**
     * NORMAL
     */
    private String matches;
    private String doesNotMatch;
    private String equalTo;
    private String contains;

    private ValueMatcher() {
    }

    public ValueMatcher setEqualTo(String equalTo) {
        this.equalTo = equalTo;
        return this;
    }

    public ValueMatcher setMatches(String matches) {
        this.matches = matches;
        return this;
    }

    public ValueMatcher setDoesNotMatch(String doesNotMatch) {
        this.doesNotMatch = doesNotMatch;
        return this;
    }

    public ValueMatcher setContains(String contains) {
        this.contains = contains;
        return this;
    }
}
