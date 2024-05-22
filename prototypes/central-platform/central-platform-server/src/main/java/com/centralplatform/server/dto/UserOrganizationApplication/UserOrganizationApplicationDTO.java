package com.centralplatform.server.dto.UserOrganizationApplication;

import com.centralplatform.server.dto.User.UserDTO;
import com.centralplatform.server.model.Organization.OrganizationType;
import com.centralplatform.server.model.UserOrganizationApplication.StatusType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserOrganizationApplicationDTO {

     UUID id;
     String email;

     String organizationId;
     StatusType status;

     LocalDateTime createdAt;

     LocalDateTime updatedAt;

}
