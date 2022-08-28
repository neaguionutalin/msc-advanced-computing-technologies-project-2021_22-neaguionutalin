package com.example.eldoradoservice_client_gateway.repository.entity;

import lombok.*;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class Role {
  @Id private UUID id;
  @NonNull private String name;
  private OffsetDateTime createdOn;
  private OffsetDateTime modifiedOn;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
      name = "role_permissions_link",
      joinColumns = @JoinColumn(name = "role_id"),
      inverseJoinColumns = @JoinColumn(name = "permission_id"))
  private List<Permission> permissions;
}
