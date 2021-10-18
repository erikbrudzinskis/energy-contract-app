package com.evilkissyou.energycontractapp.entity;

import com.evilkissyou.energycontractapp.serializer.ContractSerializer;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Contract class for contract entity from DB
 */

// Custom serializer to provide readable Json output
@JsonSerialize(using = ContractSerializer.class)

@Entity
@Table(name = "contracts", schema="energy")
@Data
@EqualsAndHashCode(callSuper = true)
public class Contract extends BaseEntity {

    // No-arg constructor creates the createdOn date
    public Contract() {
        this.createdOn = LocalDate.now();
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private ContractType contractType;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "created_on")
    private LocalDate createdOn;
}
