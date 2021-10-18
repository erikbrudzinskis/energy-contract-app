package com.evilkissyou.energycontractapp.repository;

import com.evilkissyou.energycontractapp.entity.ContractType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractTypeRepository extends JpaRepository<ContractType, Integer> {
}
