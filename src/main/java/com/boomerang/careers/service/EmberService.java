package com.boomerang.careers.service;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.NameBinding;
import javax.ws.rs.core.Response;
import java.lang.annotation.*;

// Java Rest API for Ember.js DS.RESTAdapter
public interface EmberService {
    String JSON_API = "application/vnd.api+json";

    // GET
    Response findAll();

    // GET
    Response findRecord(String id);

    // POST
    Response save(String request);

    // PATCH
    Response save(String id, String request);

    // DELETE
    Response destroyRecord(String id);

    @Target({ ElementType.METHOD, ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @HttpMethod("PATCH")
    @Documented
    @NameBinding
    @interface PATCH {}

}
