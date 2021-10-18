package com.evilkissyou.energycontractapp.entity;

import com.evilkissyou.energycontractapp.serializer.UserSerializer;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * User class for user entity from DB
 */

// Ignore Hibernate properties for Jackson
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

// Prevent infinite recursions
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")

// Custom serializer to provide readable Json output
@JsonSerialize(using = UserSerializer.class)

@Entity
@Table(name = "users", schema="energy")
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private UserType userType;

    // Exclude the set from equals and hashcode to prevent infinite recursions
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "user")
    private Set<Contract> contracts;
}
