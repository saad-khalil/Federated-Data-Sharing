package com.centralplatform.server.controller.Organization;

import com.centralplatform.server.dto.Organization.OrganizationDTO;
import com.centralplatform.server.model.Organization.Organization;
import com.centralplatform.server.payload.request.Organization.OrganizationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
//TODO pre auths
public interface OrganizationController<R extends OrganizationRequest> {
    // POST
    @RequestMapping(value = "/organization", method = RequestMethod.POST)
    <T extends R> ResponseEntity<? extends List<Organization>>getOrganizations(@RequestBody T request);

    @RequestMapping(value = "/organizationdto", method = RequestMethod.POST)
    <T extends R> ResponseEntity<? extends List<OrganizationDTO>>getOrganizationDTOs(@RequestBody T request);

    @RequestMapping(value = "/organization/{id}", method = RequestMethod.POST)
    ResponseEntity<? extends OrganizationDTO> getOrganizationById(@PathVariable("id") String id);

    @RequestMapping(value = "/organization/new", method = RequestMethod.POST)
    <T extends R>ResponseEntity<? extends OrganizationDTO> uploadOrganization(@RequestBody T request);

    @RequestMapping(value = "/organization/update/{id}", method = RequestMethod.POST)
    <T extends R >ResponseEntity<? extends OrganizationDTO> updateOrganization(@RequestBody T request, @PathVariable("id") String id);

    @RequestMapping(value = "/organization/update", method = RequestMethod.POST)
    <T extends R>ResponseEntity<? extends List<OrganizationDTO>> uploadOrganizations(@RequestBody T request);

    // DELETE
    @RequestMapping(value = "/organization/delete/{id}", method = RequestMethod.GET)
    ResponseEntity<?> deleteOrganizationById(@PathVariable("id") String id);

    @RequestMapping(value = "/organization/delete", method = RequestMethod.GET)
    <T extends R>ResponseEntity<?> deleteOrganizations(@RequestBody T request);
}
