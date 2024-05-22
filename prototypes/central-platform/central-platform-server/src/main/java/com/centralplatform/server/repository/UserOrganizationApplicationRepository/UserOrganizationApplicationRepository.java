package com.centralplatform.server.repository.UserOrganizationApplicationRepository;

import com.centralplatform.server.model.UserOrganizationApplication.StatusType;
import com.centralplatform.server.model.UserOrganizationApplication.UserOrganizationApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserOrganizationApplicationRepository extends JpaRepository<UserOrganizationApplication, UUID> {
    List<UserOrganizationApplication> findByOrganizationIdAndStatus(String organizationId, StatusType status);
}
