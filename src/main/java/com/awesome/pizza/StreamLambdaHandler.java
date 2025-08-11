package com.awesome.pizza;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
// import com.amazonaws.serverless.proxy.internal.testutils.Timer;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
// import com.amazonaws.serverless.sample.springboot3.filter.CognitoIdentityFilter;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.EnumSet;

public class StreamLambdaHandler implements RequestStreamHandler {
    private static SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;
    static {
        try {
            handler = SpringBootLambdaContainerHandler.getAwsProxyHandler(PizzaApplication.class);
            // If you are using HTTP APIs with the version 2.0 of the proxy model, use the
            // getHttpApiV2ProxyHandler
            // method: handler =
            // SpringBootLambdaContainerHandler.getHttpApiV2ProxyHandler(Application.class);
        } catch (ContainerInitializationException e) {
            // if we fail here. We re-throw the exception to force another cold start
            e.printStackTrace();
            throw new RuntimeException("Could not initialize Spring Boot application", e);
        }
    }

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context)
            throws IOException {
        // handler.proxyStream(inputStream, outputStream, context);
        
        // Copia lo stream in una stringa per loggarlo
        String eventJson = new String(inputStream.readAllBytes(), java.nio.charset.StandardCharsets.UTF_8);
        System.out.println("Incoming Event: " + eventJson);

        // Riapri un InputStream per passarlo all'handler
        try (InputStream freshStream = new java.io.ByteArrayInputStream(eventJson.getBytes())) {
            handler.proxyStream(freshStream, outputStream, context);
        }
    }
}