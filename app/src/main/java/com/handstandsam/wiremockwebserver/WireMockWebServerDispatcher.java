package com.handstandsam.wiremockwebserver;

import com.google.gson.GsonBuilder;
import com.squareup.okhttp.mockwebserver.Dispatcher;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.RecordedRequest;

import java.util.ArrayList;
import java.util.List;

public class WireMockWebServerDispatcher extends Dispatcher {

    private final List<RequestDefinition> requestDefinitions = new ArrayList<>();

    public WireMockWebServerDispatcher() {
        super();
    }

    public void stubFor(RequestDefinition requestDefinition) {
        this.requestDefinitions.add(requestDefinition);
    }

    @Override
    public MockResponse dispatch(RecordedRequest recordedRequest) throws InterruptedException {

        System.out.println(new GsonBuilder().setPrettyPrinting().serializeNulls().create().toJson(recordedRequest));

        MockResponse mockResponse = serve(recordedRequest);
        System.out.println("Response Status: " + mockResponse.getStatus());
        return mockResponse;
    }

    private MockResponse serve(RecordedRequest recordedRequest) {
        System.out.println("Request Method: " + recordedRequest.getMethod());
        System.out.println("Request Path: " + recordedRequest.getPath());
        for (String key : recordedRequest.getHeaders()) {
            String value = recordedRequest.getHeader(key);
            System.out.println(key + ": " + value);
        }
        System.out.println("Request Body: " + new String(recordedRequest.getBody()));

        ResponseDefinition responseDefinition = findMatchingDefinition(recordedRequest);
        if (responseDefinition != null) {
            return mockResponse(responseDefinition);
        }

        return new MockResponse().setResponseCode(404);
    }

    private MockResponse mockResponse(ResponseDefinition responseDefinition) {
        MockResponse mockResponse = new MockResponse();
        mockResponse.setResponseCode(responseDefinition.status);
        if (responseDefinition.stringBody != null) {
            mockResponse.setBody(responseDefinition.stringBody);
        } else if (responseDefinition.binaryBody != null) {
            mockResponse.setBody(responseDefinition.binaryBody);
        }
        return mockResponse;
    }

    private ResponseDefinition findMatchingDefinition(RecordedRequest recordedRequest) {
        final String requestUrl = recordedRequest.getPath();
        for (RequestDefinition requestDefinition : requestDefinitions) {
            UrlMatcher urlMatcher = requestDefinition.getUrlMatcher();
            if (urlMatcher.matches(requestUrl)) {
                return requestDefinition.getResponseDefinition();
            }
        }
        return null;
    }
}
