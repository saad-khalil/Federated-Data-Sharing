package com.centralplatform.server.service.OrganizationAccess;

import com.centralplatform.server.dto.OrganizationAccess.OrganizationAccessDTO;
import com.centralplatform.server.dto.OrganizationAccess.OrganizationAccessDTOConverter;
import com.centralplatform.server.model.Organization.Organization;
import com.centralplatform.server.model.OrganizationAccess.OrganizationAccess;
import com.centralplatform.server.model.User.User;
import com.centralplatform.server.payload.request.OrganizationAccess.OrganizationAccessCreateRequest;
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

//TODO Refactor methods/use maps instead of nested ifs and optionals
@Service
@RequiredArgsConstructor
public class OrganizationAccessService {

    private final OrganizationAccessRepository organizationAccessRepository;
    private final UserRepository userRepository;

    private final OrganizationAccessDTOConverter converter;
    private final OrganizationRepository organizationRepository;

    public OrganizationAccessDTO getOwnOrganizationAccess() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OrganizationAccess result = OrganizationAccess.builder().build();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<User> user = ((userRepository.findByEmail(userEmail)));

            if(user.isPresent()){
                result = organizationAccessRepository.findOrganizationAccessByOrganizationId(UUID.fromString(user.get().getOrganizationId()));
            }
        }

        return converter.convert(result);
    }

    public List<OrganizationAccessDTO> getExternalOrganizationAccesses() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<OrganizationAccess> resultList = new ArrayList<>();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<User> user = ((userRepository.findByEmail(userEmail)));

            if(user.isPresent()){
                resultList = organizationAccessRepository.findOrganizationAccessByAllowedOrganizationId(UUID.fromString(user.get().getOrganizationId()));
                //TODO properly hide access relations of others
                for (OrganizationAccess access : resultList) {
                    access.setAllowedOrganizations(new ArrayList<>());
                }
            }
        }

        return converter.convert(resultList);
    }

    public void removeOrganizationAccessById(String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<User> user = ((userRepository.findByEmail(userEmail)));

            if(user.isPresent()){
                OrganizationAccess organizationAccess = organizationAccessRepository.findOrganizationAccessByOrganizationId(UUID.fromString(id));
                Optional<Organization> organization = organizationRepository.findById((UUID.fromString(user.get().getOrganizationId())));
                if(organization.isPresent()){
                    organizationAccess.getAllowedOrganizations().remove(organization.get());
                    organizationAccessRepository.save(organizationAccess);
                }else{
                    System.out.println("Organization not found when removing organization access.");
                }
            }
        }
    }

    public void addOrganizationAccess(OrganizationAccessCreateRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<User> user = ((userRepository.findByEmail(userEmail)));

            if(user.isPresent()){
                OrganizationAccess organizationAccess = organizationAccessRepository.findOrganizationAccessByOrganizationId(UUID.fromString(request.getOrganizationId()));
                Optional<Organization> organizationGivingAccess = organizationRepository.findById(UUID.fromString(user.get().getOrganizationId()));
                if(organizationGivingAccess.isPresent()){
                    organizationAccess.getAllowedOrganizations().add(organizationGivingAccess.get());
                    organizationAccessRepository.save(organizationAccess);

                }else{
                    System.out.println("Organization not found when adding organization access.");
                }
            }
        }
    }
}

