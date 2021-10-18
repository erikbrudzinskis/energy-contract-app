package com.evilkissyou.energycontractapp.rest;

import com.evilkissyou.energycontractapp.dto.ContractDto;
import com.evilkissyou.energycontractapp.entity.Contract;
import com.evilkissyou.energycontractapp.entity.ContractType;
import com.evilkissyou.energycontractapp.entity.User;
import com.evilkissyou.energycontractapp.service.ContractService;
import com.evilkissyou.energycontractapp.service.UserService;
import com.evilkissyou.energycontractapp.specification.ContractSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ContractRestController {
    private final ContractService contractService;
    private final UserService userService;

    @Autowired
    public ContractRestController(ContractService contractService, UserService userService) {
        this.contractService = contractService;
        this.userService = userService;
    }

    // Mapping for retrieving all contracts of one user
    @GetMapping("/user/{userId}/contracts")
    public User getContractsForUser(@PathVariable("userId") int userId) {
        return userService.getById(userId);
    }

    // Mapping for adding a new contract
    @PostMapping("/contracts")
    public Contract addContract(@RequestBody ContractDto contractDto, Authentication authentication) {

        // Create the contract with contract type
        Contract contract = new Contract();
        ContractType contractType = new ContractType();
        contractType.setId(contractDto.getContractTypeId());
        contract.setContractType(contractType);

        // Find and assign user to contract
        User user = userService.getByEmail(authentication.getName());
        contract.setUser(user);

        return contractService.save(contract);
    }

    // Mapping for filtering through contracts using specification, sorting and pagination
    @GetMapping("/contracts")
    public Page<Contract> findContracts(
            ContractSpecification contractSpecification,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "id") String sort,
            @RequestParam(required = false, defaultValue = "5") int pageSize) {
        return contractService.findAll(contractSpecification, sort, page, pageSize);
    }
}
