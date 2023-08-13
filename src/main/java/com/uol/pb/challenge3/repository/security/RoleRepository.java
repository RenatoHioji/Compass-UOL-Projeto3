package com.uol.pb.challenge3.repository.security;

import com.uol.pb.challenge3.entity.security.Role;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);

    @Query("SELECT COUNT(r) FROM Role r WHERE r.name = :name")
    Long countByName(@Param("name") String name);

    default Role saveIfNotExistsName(Role role) {
        if (countByName(role.getName()) == 0) {
            return save(role);
        }
        return role;
    }
}
