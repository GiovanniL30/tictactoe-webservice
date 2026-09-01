package com.svi.tictactoewebservice;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

@javax.ws.rs.Path("/hello-world")
public class HelloResource {

    @Context
    private ServletContext servletContext;

    @GET
    @Produces("text/plain")
    public String hello() {

        java.nio.file.Path recordsPath =
                (java.nio.file.Path) servletContext.getAttribute("recordsPath");

        return "Records path: " + recordsPath;
    }
}