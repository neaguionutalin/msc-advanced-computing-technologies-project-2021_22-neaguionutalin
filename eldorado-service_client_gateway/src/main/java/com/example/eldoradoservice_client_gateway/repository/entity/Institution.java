package com.example.eldoradoservice_client_gateway.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Table(name = "institutions")
public class Institution {

    @Id private UUID id;
    private String name;
    private String friendlyCompId;
    private OffsetDateTime createdOn;
    private OffsetDateTime modifiedOn;
}
