package com.centralplatform.server.controller.User.Admin;

import com.centralplatform.server.dto.OrganizationAccess.OrganizationAccessDTO;
import com.centralplatform.server.dto.UserOrganizationApplication.UserOrganizationApplicationDTO;
import com.centralplatform.server.model.UserOrganizationApplication.UserOrganizationApplication;
import com.centralplatform.server.payload.request.OrganizationAccess.OrganizationAccessCreateRequest;
import com.centralplatform.server.payload.request.UserOrganizationApplication.UserOrganizationApplicationUpdateRequest;
import com.centralplatform.server.service.OrganizationAccess.OrganizationAccessService;
import com.centralplatform.server.service.UserOrganizationApplication.UserOrganizationApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final UserOrganizationApplicationService userOrganizationApplicationService;
    private final OrganizationAccessService organizationAccessService;

    public AdminController(UserOrganizationApplicationService userOrganizationApplicationService,OrganizationAccessService organizationAccessService ) {
        this.userOrganizationApplicationService = userOrganizationApplicationService;
        this.organizationAccessService = organizationAccessService;
    }

    @RequestMapping(value = "/user-assign-request", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<? extends List<UserOrganizationApplicationDTO>> getUserOrganizationApplications() {
        return ResponseEntity.ok(userOrganizationApplicationService.getUserOrganizationApplications());
    }

    @RequestMapping(value = "/user-assign-request/update/{id}", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<UserOrganizationApplication> updateUserOrganizationApplication(@RequestBody UserOrganizationApplicationUpdateRequest request, @PathVariable("id") String id) {
        return ResponseEntity.ok(userOrganizationApplicationService.updateUserOrganizationApplication(request, id));
    }

    @RequestMapping(value = "/organization-access/external", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<? extends List<OrganizationAccessDTO>> getExternalOrganizationAccesses() {
        return ResponseEntity.ok(organizationAccessService.getExternalOrganizationAccesses());
    }

    @RequestMapping(value = "/organization-access/own", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<? extends OrganizationAccessDTO> getOwnOrganizationAccess() {
        return ResponseEntity.ok(organizationAccessService.getOwnOrganizationAccess());
    }

    @RequestMapping(value = "/organization-access/remove/{id}", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<?> removeOrganizationAccess(@PathVariable("id") String organizationId) {
        organizationAccessService.removeOrganizationAccessById(organizationId);
        return ResponseEntity.ok("Successfully removed");
    }

    @RequestMapping(value = "/organization-access/add", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<?> createUserOrganizationApplication(@RequestBody OrganizationAccessCreateRequest request) {
        organizationAccessService.addOrganizationAccess(request);
        return ResponseEntity.ok("Successfully added");
    }

}
