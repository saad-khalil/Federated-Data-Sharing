package com.centralplatform.server.repository.Organization;

import com.centralplatform.server.model.Organization.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, UUID> {


    @Modifying
    @Query(nativeQuery = true, value = "update organization set name = ?2 where id = ?1")
    Optional<Organization> updateOrganization(UUID id, String name);
}
