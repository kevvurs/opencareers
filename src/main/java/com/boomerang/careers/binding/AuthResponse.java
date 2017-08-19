package com.boomerang.careers.binding;

public class AuthResponse {
  private Boolean valid;
  private String username;
  // TODO: data

  public AuthResponse() {
    this(false,"");
  }

  public AuthResponse(Boolean valid, String username) {
    this.valid = valid;
    this.username = username;
  }

  public Boolean getValid() {
    return valid;
  }

  public void setValid(Boolean valid) {
    this.valid = valid;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
