package com.centralplatform.server.repository.OrganizationAccess;

import com.centralplatform.server.model.OrganizationAccess.OrganizationAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrganizationAccessRepository extends JpaRepository<OrganizationAccess, UUID> {
    
    OrganizationAccess findOrganizationAccessByOrganizationId(UUID organization_id);

    @Query("SELECT oa FROM OrganizationAccess oa JOIN oa.allowedOrganizations ao WHERE ao.id = :organizationId")
    List<OrganizationAccess> findOrganizationAccessByAllowedOrganizationId(UUID organizationId);
}
