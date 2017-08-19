package com.boomerang.careers.service;

import com.boomerang.careers.binding.AuthRequest;
import com.boomerang.careers.binding.AuthResponse;
import com.boomerang.careers.data.UserDao;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/auth")
@Component
public class AuthService {
  private static final Logger LOG = Logger.getLogger(AuthService.class);
  private static final Gson GSON = new Gson();

  @Autowired
  UserDao userDao;

  public AuthService() {
    LOG.info("authService instantiated");
  }

  @POST
  @Path("/login")
  @Produces({MediaType.APPLICATION_JSON, "application/vnd.api+json"})
  @Consumes({MediaType.APPLICATION_JSON, "application/vnd.api+json"})
  public String login(String request) {
    AuthResponse authResponse = new AuthResponse();
    try {
      AuthRequest authRequest = GSON.fromJson(request, AuthRequest.class);
      boolean v = userDao.authenticate(authRequest);
      authResponse.setValid(v);
      authResponse.setUsername(authRequest.getUsername());
      // TODO: GET => API for user info
      LOG.info(String.format("Logging in %s", authResponse.getUsername()));
    } catch (JsonSyntaxException e) {
      LOG.warn(e);
    }
    return GSON.toJson(authResponse);
  }
}
