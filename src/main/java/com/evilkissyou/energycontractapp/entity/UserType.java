package com.evilkissyou.energycontractapp.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * User type class for user type entity from DB
 */

// Prevent infinite recursions
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")

@Entity
@Table(name = "users_types", schema="energy")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserType extends BaseEntity {

    @Column(name = "name")
    private String name;

    // Exclude the set from equals and hashcode to prevent infinite recursions
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "userType")
    private Set<User> users;
}
