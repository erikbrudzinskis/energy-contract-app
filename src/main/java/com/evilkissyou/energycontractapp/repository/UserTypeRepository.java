package com.evilkissyou.energycontractapp.repository;

import com.evilkissyou.energycontractapp.entity.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTypeRepository extends JpaRepository<UserType, Integer> {
}
