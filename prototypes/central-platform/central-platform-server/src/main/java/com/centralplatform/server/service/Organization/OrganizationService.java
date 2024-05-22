package com.centralplatform.server.service.Organization;

import com.centralplatform.server.dto.Organization.OrganizationDTO;
import com.centralplatform.server.model.Organization.Organization;
import com.centralplatform.server.payload.request.Organization.OrganizationRequest;

import java.util.List;

public interface OrganizationService<R extends OrganizationRequest> {

    List<Organization> getOrganizations(R request);

    List<OrganizationDTO> getOrganizationDTOs(R request);

    OrganizationDTO getOrganizationById(String id);

    OrganizationDTO uploadOrganization(R request);

    OrganizationDTO updateOrganization(R request, String id);

    List<OrganizationDTO> uploadOrganizations(R request);

    void deleteOrganizationById(String id);

    void deleteOrganizations(R request);

}
