package com.evilkissyou.energycontractapp.service;

import com.evilkissyou.energycontractapp.entity.Contract;
import com.evilkissyou.energycontractapp.specification.ContractSpecification;
import org.springframework.data.domain.Page;

public interface ContractService {
    Contract save(Contract contract);
    // Method for filtering through contracts using specification, sorting and pagination
    Page<Contract> findAll(ContractSpecification contractSpecification, String sort, int page, int pageSize);
}
