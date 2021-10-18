package com.evilkissyou.energycontractapp.specification;

import com.evilkissyou.energycontractapp.entity.Contract;
import com.evilkissyou.energycontractapp.entity.ContractType;
import com.evilkissyou.energycontractapp.entity.User;
import com.evilkissyou.energycontractapp.entity.UserType;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ContractSpecification implements Specification<Contract> {
    private String name;
    private String createdOn;
    private String contractType;
    private String userType;

    @Override
    public Predicate toPredicate(Root<Contract> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.isNotBlank(name)) {
            Join<Contract, User> user = root.join("user");
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(user.get("name")), "%"+name+"%".toLowerCase()));
        }

        if (StringUtils.isNotBlank(createdOn)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(createdOn, formatter);
            predicates.add(criteriaBuilder.equal(root.get("createdOn"), localDate));
        }

        if (StringUtils.isNotBlank(contractType)) {
            Join<Contract, ContractType> contractTypeJoin = root.join("contractType");
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(contractTypeJoin.get("name")), contractType.toLowerCase()));
        }

        if (StringUtils.isNotBlank(userType)) {
            Join<Contract, User> userJoin = root.join("user");
            Join<User, UserType> userTypeJoin = userJoin.join("userType");
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(userTypeJoin.get("name")), "%"+userType+"%".toLowerCase()));
        }

        return predicates.isEmpty() ? null : criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
