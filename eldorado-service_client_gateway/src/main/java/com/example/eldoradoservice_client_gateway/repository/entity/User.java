package com.example.eldoradoservice_client_gateway.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static javax.persistence.FetchType.EAGER;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements UserDetails {
  @Id private UUID id;
  @NonNull private String fullName;
  @NonNull private String email;
  @NonNull @JsonIgnore private String password;
  @NonNull @JsonIgnore private String salt;
  private OffsetDateTime createdOn;
  private OffsetDateTime modifiedOn;

  @ManyToMany(cascade = CascadeType.ALL, fetch = EAGER)
  @JoinTable(
      name = "user_roles_link",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private List<Role> roles;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    for (Role role : roles) {
      authorities.add(new SimpleGrantedAuthority(String.format("ROLE_%s", role.getName())));
    }
    return authorities;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
