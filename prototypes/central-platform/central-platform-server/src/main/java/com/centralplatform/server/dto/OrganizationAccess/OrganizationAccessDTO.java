package com.centralplatform.server.dto.OrganizationAccess;

import com.centralplatform.server.dto.Organization.OrganizationDTO;
import com.centralplatform.server.model.Organization.OrganizationType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class OrganizationAccessDTO {

     UUID id;

     OrganizationDTO organization;
     List<OrganizationDTO> allowedOrganizations;


}
