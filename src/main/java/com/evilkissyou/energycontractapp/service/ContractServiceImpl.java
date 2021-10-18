package com.evilkissyou.energycontractapp.service;

import com.evilkissyou.energycontractapp.entity.Contract;
import com.evilkissyou.energycontractapp.entity.ContractType;
import com.evilkissyou.energycontractapp.repository.ContractRepository;
import com.evilkissyou.energycontractapp.repository.ContractTypeRepository;
import com.evilkissyou.energycontractapp.specification.ContractSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeParseException;
import java.util.Optional;

@Slf4j
@Service
public class ContractServiceImpl implements ContractService{
    private final ContractRepository contractRepository;
    private final ContractTypeRepository contractTypeRepository;

    @Autowired
    public ContractServiceImpl(ContractRepository contractRepository, ContractTypeRepository contractTypeRepository) {
        this.contractRepository = contractRepository;
        this.contractTypeRepository = contractTypeRepository;
    }

    @Override
    public Contract save(Contract contract) {
        log.info("IN ContractServiceImpl save()");

        // Check if contract id exists
        int contractTypeId = contract.getContractType().getId();
        Optional<ContractType> optionalContractType = contractTypeRepository.findById(contractTypeId);
        if (optionalContractType.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid contract type id");
        }

        // Save the new contract
        contractRepository.save(contract);

        // Manually set contract type name, otherwise, although it is saved correctly, it is not displayed in the return JSON when saving
        contract.getContractType().setName(optionalContractType.get().getName());

        return contract;
    }

    // Method for filtering through contracts using specification, sorting and pagination
    @Override
    public Page<Contract> findAll(ContractSpecification contractSpecification, String sort, int page, int pageSize) {
        log.info("IN ContractServiceImpl findAll(contractSpecification) user name: {}, contract type: {}, created on: {}, user type: {}, sort: {}, page: {}, pageSize {}",
                contractSpecification.getName(),
                contractSpecification.getContractType(),
                contractSpecification.getCreatedOn(),
                contractSpecification.getUserType(),
                sort,
                page,
                pageSize
        );

        try {
            return contractRepository.findAll(
                    contractSpecification,
                    PageRequest.of(
                            page,
                            pageSize,
                            Sort.Direction.ASC,
                            sort
                    ));
        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown error", e);
        }
    }


}
