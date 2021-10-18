package com.evilkissyou.energycontractapp.rest;

import com.evilkissyou.energycontractapp.entity.Contract;
import com.evilkissyou.energycontractapp.entity.ContractType;
import com.evilkissyou.energycontractapp.entity.User;
import com.evilkissyou.energycontractapp.entity.UserType;
import com.evilkissyou.energycontractapp.service.ContractService;
import com.evilkissyou.energycontractapp.service.UserService;
import com.evilkissyou.energycontractapp.specification.ContractSpecification;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;




@AutoConfigureMockMvc
@SpringBootTest
@EnableAutoConfiguration(exclude = {
        SecurityAutoConfiguration.class,
        SecurityFilterAutoConfiguration.class
})
class ContractRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private ContractService contractService;

    @MockBean
    private UserService userService;


    UserType businessUser = new UserType();
    UserType privateUser = new UserType();

    ContractType gasType = new ContractType();
    ContractType electricityType = new ContractType();
    ContractType gasAndElectricityType = new ContractType();

    User gordon = new User();
    User pixie = new User();

    Contract contract1 = new Contract();
    Contract contract2 = new Contract();
    Contract contract3 = new Contract();
    Contract contract4 = new Contract();

    Set<Contract> set1 = new HashSet<>();

    List<Contract> contractList = new ArrayList<>();
    @BeforeEach
    void before() {
        businessUser.setName("Business");
        privateUser.setName("Private");

        gasType.setName("Gas");
        electricityType.setName("Electricity");
        gasAndElectricityType.setName("Gas and electricity");

        gordon.setName("Gordon Morris");
        gordon.setUserType(privateUser);
        gordon.setId(1);

        pixie.setName("Pixie Ball Ltd");
        gordon.setUserType(businessUser);
        pixie.setId(2);

        contract1.setId(1);
        contract1.setUser(gordon);
        contract1.setContractType(gasType);
        contract2.setId(2);
        contract2.setUser(gordon);
        contract2.setContractType(electricityType);
        contract3.setId(3);
        contract3.setUser(gordon);
        contract3.setContractType(gasAndElectricityType);
        contract4.setId(4);
        contract4.setUser(pixie);
        contract4.setContractType(electricityType);

        set1.add(contract1);
        set1.add(contract2);
        set1.add(contract3);
        gordon.setContracts(set1);

        contractList.add(contract1);
    }

    @Test
    void getContractsForUser() throws Exception {
        Mockito.when(userService.getById(gordon.getId())).thenReturn(gordon);
        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/v1/user/{userId}/contracts", 1)
                .contentType(MediaType.APPLICATION_JSON);
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.userName", is("Gordon Morris")));
    }

    @Test
    void addContract() throws Exception {
        Mockito.when(contractService.save(contract1)).thenReturn(contract1);
        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/v1/contracts")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"contractTypeId\" : 1}");

        mvc.perform(request)
                .andExpect(status().isUnauthorized());
    }

    @Test
    void findContracts() throws Exception {
        Page<Contract> contractPage = new PageImpl(contractList);
        ContractSpecification contractSpecification = new ContractSpecification();
        Mockito.when(contractService.findAll(contractSpecification, "id", 0, 10)).thenReturn(contractPage);
        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/v1/contracts/?name=gordon")
                .contentType(MediaType.APPLICATION_JSON);
        mvc.perform(request)
                .andExpect(status().isOk());
    }
}