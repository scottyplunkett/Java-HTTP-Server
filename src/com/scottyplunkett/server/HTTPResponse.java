package com.scottyplunkett.server;

import java.io.IOException;

class HTTPResponse {
    private final Deducer deducer = new Deducer();
    private Producer producer;
    private String date;
    private HTTPRequest httpRequest;

    HTTPResponse(HTTPRequest request) throws IOException {
        this(request, Date.getDate());
    }

    HTTPResponse(HTTPRequest request, String _date) throws IOException {
        httpRequest = request;
        date = _date;
        producer = deducer.deduceProducer(request.getRequestLine());
        setUpProducer();
        produceContent();
    }

    private void produceContent() throws IOException {
        producer.produceContent();
    }

    private void setUpProducer() {
        producer.setHttpRequest(httpRequest);
        producer.setDate(date);
    }


    byte[] get() throws IOException {
        return producer.get();
    }
}