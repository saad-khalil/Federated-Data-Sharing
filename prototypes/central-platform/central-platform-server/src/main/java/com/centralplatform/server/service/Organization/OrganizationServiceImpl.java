package com.centralplatform.server.service.Organization;

import com.centralplatform.server.dto.Organization.OrganizationDTO;
import com.centralplatform.server.dto.Organization.OrganizationDTOConverter;
import com.centralplatform.server.model.Inventory.Inventory;
import com.centralplatform.server.model.Location.Location;
import com.centralplatform.server.model.Organization.Organization;
import com.centralplatform.server.model.OrganizationAccess.OrganizationAccess;
import com.centralplatform.server.model.User.User;
import com.centralplatform.server.payload.request.Organization.OrganizationRequest;
import com.centralplatform.server.repository.Organization.OrganizationRepository;
import com.centralplatform.server.repository.OrganizationAccess.OrganizationAccessRepository;
import com.centralplatform.server.repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final OrganizationDTOConverter converter;
    private final OrganizationAccessRepository organizationAccessRepository;
    private final UserRepository userRepository;

    @Override
    public List<Organization> getOrganizations(OrganizationRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Organization> resultList = new ArrayList<>();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<User> user = ((userRepository.findByEmail(userEmail)));

            if(user.isPresent()){
                OrganizationAccess organizationAccess = organizationAccessRepository.findOrganizationAccessByOrganizationId(UUID.fromString(user.get().getOrganizationId()));

                for (Organization organization : organizationRepository.findAll()) {
                    if (organization.getId().equals(organizationAccess.getOrganization().getId()) || organizationAccess.getAllowedOrganizations().contains(organization)) {
                        if(organizationAccess.getAllowedOrganizations().contains(organization)){
                           for(Location location :  organization.getLocations()){
                               List<Inventory> inventoriesToRemove = new ArrayList<>();

                               for (Inventory inventory : location.getInventory()) {
                                   if (!organizationAccess.getAllowedOrganizations().contains(inventory.getOrganization()) && inventory.getOrganization() != organizationAccess.getOrganization()) {
                                       inventoriesToRemove.add(inventory);
                                   }
                               }

                               location.getInventory().removeAll(inventoriesToRemove);
                           }
                        }
                        resultList.add(organization);
                    }
                }
            }
        }

        return resultList;
    }

    @Override
    public List<OrganizationDTO> getOrganizationDTOs(OrganizationRequest request) {
        return converter.convert(organizationRepository.findAll());
    }

    @Override
    public OrganizationDTO getOrganizationById(String id) {
        return converter.convert(organizationRepository.findById(UUID.fromString(id)).orElseThrow());
    }

    @Override
    public OrganizationDTO uploadOrganization(OrganizationRequest request) {
        Organization organization = Organization.builder().build();
        return converter.convert(organizationRepository.save(organization));
    }

    @Override
    public OrganizationDTO updateOrganization(OrganizationRequest request, String id) {
        return null;
    }

    @Override
    public List<OrganizationDTO> uploadOrganizations(OrganizationRequest request) {
        List<Organization> organizationList = new ArrayList<>();
        return converter.convert(organizationList);
    }

    @Override
    public void deleteOrganizationById(String id) {
        organizationRepository.delete(organizationRepository.findById(UUID.fromString(id)).orElseThrow());
    }

    @Override
    public void deleteOrganizations(OrganizationRequest request) {
        List<UUID> organizationIdList = new ArrayList<>();
        organizationRepository.deleteAll(organizationRepository.findAllById(organizationIdList));
    }
}
