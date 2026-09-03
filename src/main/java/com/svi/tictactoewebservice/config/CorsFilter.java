package com.svi.tictactoewebservice.config;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Arrays;

@Provider
@Priority(Priorities.HEADER_DECORATOR)
public class CorsFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {

        String origin = requestContext.getHeaderString("Origin");

        String allowedOrigins = Config.get(Config.Key.ALLOWED_ORIGINS.value());

        if (Arrays.asList(allowedOrigins.split(",")).contains(origin)) {
            responseContext.getHeaders().add("Access-Control-Allow-Origin", origin);
        }

        responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, PATCH, DELETE, OPTIONS");

        responseContext.getHeaders().add("Access-Control-Allow-Headers", "Content-Type, Accept, Authorization");

        responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
    }
}