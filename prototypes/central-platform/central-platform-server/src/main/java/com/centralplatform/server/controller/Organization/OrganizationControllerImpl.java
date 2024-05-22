package com.centralplatform.server.controller.Organization;

import com.centralplatform.server.dto.Organization.OrganizationDTO;
import com.centralplatform.server.model.Organization.Organization;
import com.centralplatform.server.payload.request.Organization.OrganizationRequest;
import com.centralplatform.server.service.Organization.OrganizationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/prod")
@RequiredArgsConstructor
//TODO creating/deleting orgs cascade and users
public class OrganizationControllerImpl implements OrganizationController<OrganizationRequest> {

    private final OrganizationServiceImpl organizationService;

    @Override
    public ResponseEntity<? extends OrganizationDTO> getOrganizationById(String id) {
        return ResponseEntity.ok(organizationService.getOrganizationById(id));
    }

    @Override
    public <T extends OrganizationRequest> ResponseEntity< List<Organization>> getOrganizations(T request) {
        return ResponseEntity.ok(organizationService.getOrganizations(request));
    }

    @Override
    public <T extends OrganizationRequest> ResponseEntity< List<OrganizationDTO>> getOrganizationDTOs(T request) {
        return ResponseEntity.ok(organizationService.getOrganizationDTOs(request));
    }

    @Override
    public <T extends OrganizationRequest> ResponseEntity<? extends OrganizationDTO> uploadOrganization(T request) {
        return ResponseEntity.ok(organizationService.uploadOrganization(request));
    }

    @Override
    public <T extends OrganizationRequest> ResponseEntity<? extends OrganizationDTO> updateOrganization(T request, String id) {
        return null;
    }


    @Override
    public <T extends OrganizationRequest> ResponseEntity<? extends List<OrganizationDTO>> uploadOrganizations(T request) {
        return ResponseEntity.ok(organizationService.uploadOrganizations(request));
    }

    @Override
    public ResponseEntity<?> deleteOrganizationById(String id) {
        organizationService.deleteOrganizationById(id);
        return ResponseEntity.ok("Successfully deleted");
    }

    @Override
    public <T extends OrganizationRequest> ResponseEntity<?> deleteOrganizations(T request) {
        organizationService.deleteOrganizations(request);
        return ResponseEntity.ok("Successfully deleted");
    }
}
