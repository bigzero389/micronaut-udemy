package kr.co.bigzero.udemy.auth.jwt;

import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.authentication.AuthenticationResponse;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserDetails implements AuthenticationResponse {

  private String username;
  private Collection<String> roles;
  private Map<String, Object> attributes;

  @Override
  public Optional<Authentication> getAuthentication() {
    return Optional.empty();
  }

  public UserDetails(String username, Collection<String> roles) {
    this(username, roles, (Map)null);
  }

  public UserDetails(String username, Collection<String> roles, Map<String, Object> attributes) {
    if (username != null && roles != null) {
      this.username = username;
      this.roles = roles;
      this.attributes = attributes;
    } else {
      throw new IllegalArgumentException("Cannot construct a UserDetails with a null username or authorities");
    }
  }

  public Map<String, Object> getAttributes(String rolesKey, String usernameKey) {
    Map<String, Object> result = this.attributes == null ? new HashMap<>() : new HashMap<>(this.attributes);
    result.putIfAbsent(rolesKey, this.getRoles());
    result.putIfAbsent(usernameKey, this.getUsername());
    return result;
  }

  public String getUsername() {
    return this.username;
  }

  public Collection<String> getRoles() {
    return this.roles;
  }

}
