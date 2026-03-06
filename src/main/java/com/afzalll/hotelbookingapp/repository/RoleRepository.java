package com.afzalll.hotelbookingapp.repository;

import com.afzalll.hotelbookingapp.model.Role;
import com.afzalll.hotelbookingapp.model.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleType(RoleType roleType);
}
