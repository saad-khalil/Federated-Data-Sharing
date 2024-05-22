package com.centralplatform.server.service.UserOrganizationApplication;

import com.centralplatform.server.dto.UserOrganizationApplication.UserOrganizationApplicationDTO;
import com.centralplatform.server.dto.UserOrganizationApplication.UserOrganizationApplicationDTOConverter;
import com.centralplatform.server.model.User.User;
import com.centralplatform.server.model.UserOrganizationApplication.StatusType;
import com.centralplatform.server.model.UserOrganizationApplication.UserOrganizationApplication;
import com.centralplatform.server.payload.request.UserOrganizationApplication.UserOrganizationApplicationCreateRequest;
import com.centralplatform.server.payload.request.UserOrganizationApplication.UserOrganizationApplicationUpdateRequest;
import com.centralplatform.server.repository.User.UserRepository;
import com.centralplatform.server.repository.UserOrganizationApplicationRepository.UserOrganizationApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

//TODO Refactor methods/use maps instead of nested ifs and optionals
@Service
@RequiredArgsConstructor
public class UserOrganizationApplicationService {

    private final UserOrganizationApplicationRepository organizationApplicationRepository;
    private final UserRepository userRepository;

    private final UserOrganizationApplicationDTOConverter converter;

    public List<UserOrganizationApplicationDTO> getUserOrganizationApplications() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<UserOrganizationApplication> resultList = new ArrayList<>();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<User> user = ((userRepository.findByEmail(userEmail)));

            if(user.isPresent()){
                resultList = organizationApplicationRepository.findByOrganizationIdAndStatus(user.get().getOrganizationId(), StatusType.IN_PROGRESS);
            }
        }

        return converter.convert(resultList);
    }

    public UserOrganizationApplication uploadUserOrganizationApplication(UserOrganizationApplicationCreateRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserOrganizationApplication userOrganizationApplication = UserOrganizationApplication.builder().build();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            String userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
            Optional<User> user = ((userRepository.findByEmail(userEmail)));

            if(user.isPresent()){
                userOrganizationApplication.setOrganizationId(request.getOrganizationId());
                userOrganizationApplication.setUser(user.get());
                userOrganizationApplication.setStatus(StatusType.IN_PROGRESS);
                userOrganizationApplication.setCreatedAt(LocalDateTime.now());
                organizationApplicationRepository.save(userOrganizationApplication);
            }
        }
        return null;
    }

    public UserOrganizationApplication updateUserOrganizationApplication(UserOrganizationApplicationUpdateRequest request, String id) {
        Optional<UserOrganizationApplication> foundUserOrganizationApplication = organizationApplicationRepository.findById(UUID.fromString(id));

        if(foundUserOrganizationApplication.isPresent()){
            UserOrganizationApplication userOrganizationApplication =  foundUserOrganizationApplication.get();
            userOrganizationApplication.setStatus(request.getUserOrganizationApplication().getStatus());
            organizationApplicationRepository.save(userOrganizationApplication);
            if(userOrganizationApplication.getStatus() == StatusType.APPROVED){
                String userEmail = userOrganizationApplication.getUser().getEmail();
                Optional<User> foundUser = ((userRepository.findByEmail(userEmail)));
                if(foundUser.isPresent()){
                    User user = foundUser.get();
                    user.setOrganizationId(userOrganizationApplication.getOrganizationId());
                    userRepository.save(user);
                }
            }
        }

        return null;
    }
}

