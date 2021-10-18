package com.evilkissyou.energycontractapp.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * Contract type class for contract type entity from DB
 */

@Entity
@Table(name = "contracts_types", schema="energy")
@Data
@EqualsAndHashCode(callSuper = true)
public class ContractType extends BaseEntity {

    @Column(name = "name")
    private String name;

    // Exclude the set from equals and hashcode to prevent infinite recursions
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "contractType")
    private Set<Contract> contracts;
}
