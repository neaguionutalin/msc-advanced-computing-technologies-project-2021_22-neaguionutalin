package com.example.eldoradoservice_client_gateway.repository.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.OffsetDateTime;
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
}
